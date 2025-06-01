package com.erayerarslan.t_vac_kotlin.domain.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.erayerarslan.t_vac_kotlin.domain.repository.BluetoothRepository
import com.erayerarslan.t_vac_kotlin.model.Device
import com.erayerarslan.t_vac_kotlin.model.SensorData
import com.erayerarslan.t_vac_kotlin.model.SensorDataManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BluetoothRepository {
    private val adapter = BluetoothAdapter.getDefaultAdapter()

    override suspend fun discoverDevices(): List<Device> = withContext(Dispatchers.IO) {
        Log.d("BluetoothDiscovery", "Cihaz tarama başlatılıyor...")
        if (adapter == null) {
            Log.e("BluetoothDiscovery", "Bluetooth adaptörü bulunamadı!")
            return@withContext emptyList()
        }

        if (!adapter.isEnabled) {
            val enableBt = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(enableBt)
            // Kullanıcı yanıtını beklemek için 3 sn kadar uyku, sonra durum kontrolü
            kotlinx.coroutines.delay(3000)
            if (!adapter.isEnabled) {
                Log.e("BluetoothDiscovery", "Bluetooth hâlâ kapalı!")
                return@withContext emptyList<Device>()
            }
        }

        callbackFlow {
            val found = mutableListOf<Device>()
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(ctx: Context, intent: Intent) {
                    when (intent.action) {
                        BluetoothDevice.ACTION_FOUND -> {
                            val device = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
                            } else {
                                @Suppress("DEPRECATION")
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                            }

                            device?.let { bt ->
                                val name = bt.name ?: "Unknown"
                                val address = bt.address
                                Log.d("BluetoothDiscovery", "Cihaz bulundu: $name ($address)")

                                val dev = Device(name, address, bt)
                                if (found.none { it.address == dev.address }) {
                                    found.add(dev)
                                    trySend(found)
                                }
                            }
                        }
                        BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                            Log.d("BluetoothDiscovery", "Tarama tamamlandı. Bulunan cihaz sayısı: ${found.size}")
                        }
                    }
                }
            }

            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }

            context.registerReceiver(receiver, filter)

            // Önceki taramayı iptal et
            if (adapter.isDiscovering) {
                adapter.cancelDiscovery()
            }

            // Yeni taramayı başlat
            adapter.startDiscovery()
            Log.d("BluetoothDiscovery", "Tarama başlatıldı")

            awaitClose {
                if (adapter.isDiscovering) {
                    adapter.cancelDiscovery()
                }
                try {
                    context.unregisterReceiver(receiver)
                } catch (e: Exception) {
                    Log.e("BluetoothDiscovery", "Receiver kapatılırken hata: ${e.message}")
                }
                Log.d("BluetoothDiscovery", "Tarama durduruldu")
            }
        }.take(10).toList().lastOrNull() ?: emptyList()
    }

    override suspend fun pairDevice(device: Device): Boolean = withContext(Dispatchers.IO) {
        val bt = device.bluetoothDevice
        if (bt.bondState != BluetoothDevice.BOND_BONDED) {
            val receiver = object : BroadcastReceiver() {
                var success = false
                override fun onReceive(ctx: Context, intent: Intent) {
                    if (intent.action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                        val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1)
                        if (state == BluetoothDevice.BOND_BONDED) success = true
                    }
                }
            }
            context.registerReceiver(
                receiver,
                IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
            )
            bt.createBond()
            kotlinx.coroutines.delay(3000)
            context.unregisterReceiver(receiver)
            return@withContext bt.bondState == BluetoothDevice.BOND_BONDED
        }
        true
    }

    override suspend fun fetchSensorData(device: Device): SensorData = withContext(Dispatchers.IO) {
        try {
            Log.d("BluetoothData", "Bağlantı başlatılıyor: ${device.name}")
            val socket = device.bluetoothDevice
                .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

            try {
                socket.connect()
                Log.d("BluetoothData", "Bluetooth bağlantısı başarılı")

                val buffer = StringBuilder()
                val input = socket.inputStream
                val dataMap = mutableMapOf<String, String>()

                while (dataMap.size < 7) {
                    val byteArray = ByteArray(128)
                    val bytes = input.read(byteArray)
                    val rawData = String(byteArray, 0, bytes)
                    Log.d("BluetoothData", "Ham veri alındı: $rawData")
                    buffer.append(rawData)

                    if (buffer.contains("\n")) {
                        val msg = buffer.toString().also { buffer.clear() }
                        Log.d("BluetoothData", "İşlenecek mesaj: $msg")

                        extract("pH", msg)?.let { dataMap["ph"] = it }
                        extract("Sicaklik", msg)?.let { dataMap["temp"] = it }
                        extract("Iletkenlik", msg)?.let { dataMap["cond"] = it }
                        extract("Fosfor", msg)?.let { dataMap["fos"] = it }
                        extract("Nem", msg)?.let { dataMap.putIfAbsent("hum", it) }
                        extract("Potasyum", msg)?.let { dataMap["pot"] = it }
                        extract("Azot", msg)?.let { dataMap["azot"] = it }

                        Log.d("BluetoothData", "Güncel sensör verileri: $dataMap")
                    }
                }

                Log.d("BluetoothData", "Tüm sensör verileri alındı: $dataMap")

                val sd = SensorData(
                    phValue = dataMap["ph"]?.toFloat(),
                    temperatureValue = dataMap["temp"]?.toFloat(),
                    conductibilityValue = dataMap["cond"]?.toFloat(),
                    fosforValue = dataMap["fos"]?.toFloat(),
                    humidityValue = dataMap["hum"]?.toFloat(),
                    potasyumValue = dataMap["pot"]?.toFloat(),
                    azotValue = dataMap["azot"]?.toFloat()
                )

                Log.d("BluetoothData", "Oluşturulan SensorData objesi: $sd")
                //SensorDataManager.sensorData = sd
                //sd
                SensorDataManager.addSensorData(sd)

                sd

            } catch (e: Exception) {
                Log.e("BluetoothData", "Veri okuma hatası: ${e.message}")
                throw e
            } finally {
                try {
                    socket.close()
                    Log.d("BluetoothData", "Bluetooth bağlantısı kapatıldı")
                } catch (e: Exception) {
                    Log.e("BluetoothData", "Socket kapatma hatası: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("BluetoothData", "Genel hata: ${e.message}")
            throw e
        }
    }

    private fun extract(key: String, msg: String): String? {
        val pattern = Pattern.compile("$key:\\s*([\\d.]+)")
        return pattern.matcher(msg).takeIf { it.find() }?.group(1)
    }
}










/*
package com.erayerarslan.t_vac_kotlin.domain.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.erayerarslan.t_vac_kotlin.domain.repository.BluetoothRepository
import com.erayerarslan.t_vac_kotlin.model.Device
import com.erayerarslan.t_vac_kotlin.model.SensorData
import com.erayerarslan.t_vac_kotlin.model.SensorDataManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BluetoothRepository {
    private val adapter = BluetoothAdapter.getDefaultAdapter()

    override suspend fun discoverDevices(): List<Device> = withContext(Dispatchers.IO) {
        callbackFlow {
            val found = mutableListOf<Device>()
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(ctx: Context, intent: Intent) {
                    if (intent.action == BluetoothDevice.ACTION_FOUND) {
                        intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                            ?.let { bt ->
                                val dev = Device(bt.name ?: "Unknown", bt.address, bt)
                                if (found.none { it.address == dev.address }) {
                                    found.add(dev)
                                    trySend(found)
                                }
                            }
                    }
                }
            }
            context.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
            adapter.startDiscovery()
            awaitClose {
                adapter.cancelDiscovery()
                context.unregisterReceiver(receiver)
            }
        }.take(5).toList().last()
    }

    override suspend fun pairDevice(device: Device): Boolean = withContext(Dispatchers.IO) {
        val bt = device.bluetoothDevice
        if (bt.bondState != BluetoothDevice.BOND_BONDED) {
            val receiver = object : BroadcastReceiver() {
                var success = false
                override fun onReceive(ctx: Context, intent: Intent) {
                    if (intent.action == BluetoothDevice.ACTION_BOND_STATE_CHANGED) {
                        val state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1)
                        if (state == BluetoothDevice.BOND_BONDED) success = true
                    }
                }
            }
            context.registerReceiver(
                receiver,
                IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
            )
            bt.createBond()
            kotlinx.coroutines.delay(3000)
            context.unregisterReceiver(receiver)
            return@withContext bt.bondState == BluetoothDevice.BOND_BONDED
        }
        true
    }

    override suspend fun fetchSensorData(device: Device): SensorData = withContext(Dispatchers.IO) {
        Log.d("BluetoothData", "Bağlantı başlatılıyor: ${device.name}")
        val socket = device.bluetoothDevice
            .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        socket.connect()
        Log.d("BluetoothData", "Bluetooth bağlantısı başarılı")
        val buffer = StringBuilder()
        val input = socket.inputStream
        val dataMap = mutableMapOf<String, String>()
        while (dataMap.size < 7) {
            val byteArray = ByteArray(128)
            val bytes = input.read(byteArray)
            val rawData = String(byteArray, 0, bytes)
            Log.d("BluetoothData", "Ham veri alındı: $rawData")
            buffer.append(String(byteArray, 0, bytes))
            if (buffer.contains("\n")) {
                val msg = buffer.toString().also { buffer.clear() }
                Log.d("BluetoothData", "İşlenecek mesaj: $msg")
                extract("pH", msg)?.let { dataMap["ph"] = it }
                extract("Sicaklik", msg)?.let { dataMap["temp"] = it }
                extract("Iletkenlik", msg)?.let { dataMap["cond"] = it }
                extract("Fosfor", msg)?.let { dataMap["fos"] = it }
                extract("Nem", msg)?.let { dataMap.putIfAbsent("hum", it) }
                extract("Potasyum", msg)?.let { dataMap["pot"] = it }
                extract("Azot", msg)?.let { dataMap["azot"] = it }
                Log.d("BluetoothData", "Güncel sensör verileri: $dataMap")
            }
        }
        socket.close()
        Log.d("BluetoothData", "Tüm sensör verileri alındı: $dataMap")
        val sd = SensorData(
            phValue = dataMap["ph"]?.toFloat(),
            temperatureValue = dataMap["temp"]?.toFloat(),
            conductibilityValue = dataMap["cond"]?.toFloat(),
            fosforValue = dataMap["fos"]?.toFloat(),
            humidityValue = dataMap["hum"]?.toFloat(),
            potasyumValue = dataMap["pot"]?.toFloat(),
            azotValue = dataMap["azot"]?.toFloat()
        )
        Log.d("BluetoothData", "Oluşturulan SensorData objesi: $sd")
        SensorDataManager.sensorData = sd
        sd
    }

    private fun extract(key: String, msg: String): String? {
        val pattern = Pattern.compile("$key:\\s*([\\d.]+)")
        return pattern.matcher(msg).takeIf { it.find() }?.group(1)
    }
}

 */