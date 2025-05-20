package com.farukayata.t_vac_kotlin.domain.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.farukayata.t_vac_kotlin.domain.repository.BluetoothRepository
import com.farukayata.t_vac_kotlin.model.Device
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
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
        val socket = device.bluetoothDevice
            .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        socket.connect()
        val buffer = StringBuilder()
        val input = socket.inputStream
        val dataMap = mutableMapOf<String, String>()
        while (dataMap.size < 7) {
            val byteArray = ByteArray(128)
            val bytes = input.read(byteArray)
            buffer.append(String(byteArray, 0, bytes))
            if (buffer.contains("\n")) {
                val msg = buffer.toString().also { buffer.clear() }
                extract("pH", msg)?.let { dataMap["ph"] = it }
                extract("Sicaklik", msg)?.let { dataMap["temp"] = it }
                extract("Iletkenlik", msg)?.let { dataMap["cond"] = it }
                extract("Fosfor", msg)?.let { dataMap["fos"] = it }
                extract("Nem", msg)?.let { dataMap.putIfAbsent("hum", it) }
                extract("Potasyum", msg)?.let { dataMap["pot"] = it }
                extract("Azot", msg)?.let { dataMap["azot"] = it }
            }
        }
        socket.close()
        val sd = SensorData(
            phValue = dataMap["ph"]?.toFloat(),
            temperatureValue = dataMap["temp"]?.toFloat(),
            conductibilityValue = dataMap["cond"]?.toFloat(),
            fosforValue = dataMap["fos"]?.toFloat(),
            humidityValue = dataMap["hum"]?.toFloat(),
            potasyumValue = dataMap["pot"]?.toFloat(),
            azotValue = dataMap["azot"]?.toFloat()
        )
        SensorDataManager.sensorData = sd
        sd
    }

    private fun extract(key: String, msg: String): String? {
        val pattern = Pattern.compile("$key:\\s*([\\d.]+)")
        return pattern.matcher(msg).takeIf { it.find() }?.group(1)
    }
}