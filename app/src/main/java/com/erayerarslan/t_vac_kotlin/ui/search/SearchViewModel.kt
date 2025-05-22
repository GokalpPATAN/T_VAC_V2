package com.farukayata.t_vac_kotlin.ui.search

import com.farukayata.t_vac_kotlin.model.Plant
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.erayerarslan.t_vac_kotlin.repository.GeminiRepository
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    val isAIActive = MutableStateFlow(false)
    private val _filteredPlantList = MutableLiveData<List<Plant>>(emptyList())
    val filteredPlantList: LiveData<List<Plant>> get() = _filteredPlantList
    private val _suggestedPlants = MutableStateFlow<List<Plant>>(emptyList())
    val suggestedPlants: StateFlow<List<Plant>> = _suggestedPlants
    private var localPlantList: List<Plant> = emptyList()

    init {
        Plant.fetchAll(
            onResult = { list ->
                localPlantList = list
                _filteredPlantList.value = list
                Log.d("SearchViewModel", "Veri çekildi: ${list}")
            },
            onError = { error: DatabaseError ->
                Log.e("SearchViewModel", "Veri çekilemedi: ${error.message}")
            }
        )
    }

    fun fetchPlantList(query: String) {
        val source = if (isAIActive.value) {
            _suggestedPlants.value
        } else {
            localPlantList
        }

        val filtered = if (query.isBlank()) {
            source
        } else {
            source.filter { it.nameFilter(query) }
        }

        _filteredPlantList.value = filtered
    }


    fun fetchPlantSuggestions(sensorData: SensorData) {
        viewModelScope.launch {
            try {
                Log.d("SearchViewModel", "AI önerisi için kullanılan sensör verileri: $sensorData")
                val suggestions = geminiRepository.getSuggestions(sensorData)
                _suggestedPlants.value = suggestions
                _filteredPlantList.value = suggestions
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Bitki önerileri alınırken hata: ${e.message}")
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
            }
        }
    }

    /*
    fun fetchPlantSuggestions() {
        viewModelScope.launch {
            try {
                val sensorData = SensorDataManager.getLatestSensorData()
                if (sensorData == null) {
                    Log.e("SearchViewModel", "Sensör verisi bulunamadı veya süresi dolmuş")
                    _suggestedPlants.value = emptyList()
                    _filteredPlantList.value = emptyList()
                    return@launch
                }

                Log.d("SearchViewModel", "AI önerisi için kullanılan sensör verileri: $sensorData")
                val suggestions = geminiRepository.getSuggestions(sensorData)
                Log.d("SearchViewModel", "AI önerileri alındı: ${suggestions.size} bitki")
                _suggestedPlants.value = suggestions
                _filteredPlantList.value = suggestions
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Bitki önerileri alınırken hata: ${e.message}")
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
            }
        }
    }

     */

    fun loadInitialList() {
        isAIActive.value = false
        _filteredPlantList.value = localPlantList
    }
}








/*
package com.farukayata.t_vac_kotlin.ui.search

import com.farukayata.t_vac_kotlin.model.Plant
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.erayerarslan.t_vac_kotlin.repository.GeminiRepository
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    val isAIActive = MutableStateFlow(false)
    private val _filteredPlantList = MutableLiveData<List<Plant>>(emptyList())
    val filteredPlantList: LiveData<List<Plant>> get() = _filteredPlantList
    private val _suggestedPlants = MutableStateFlow<List<Plant>>(emptyList())
    val suggestedPlants: StateFlow<List<Plant>> = _suggestedPlants
    private var localPlantList: List<Plant> = emptyList()

    init {
        Plant.fetchAll(
            onResult = { list ->
                localPlantList = list
                _filteredPlantList.value = list
                Log.d("SearchViewModel", "Veri çekildi: ${list}")
            },
            onError = { error: DatabaseError ->
                Log.e("SearchViewModel", "Veri çekilemedi: ${error.message}")
            }
        )
    }

    fun fetchPlantList(query: String) {
        val source = if (isAIActive.value) {
            _suggestedPlants.value
        } else {
            localPlantList
        }

        val filtered = if (query.isBlank()) {
            source
        } else {
            source.filter { it.nameFilter(query) }
        }

        _filteredPlantList.value = filtered
    }

    fun fetchPlantSuggestions() {
        viewModelScope.launch {
            try {
                val currentSensorData = SensorDataManager.getLatestSensorData()
                if (currentSensorData == null) {
                    Log.e("SearchViewModel", "Sensör verisi bulunamadı")
                    _suggestedPlants.value = emptyList()
                    _filteredPlantList.value = emptyList()
                    return@launch
                }

                Log.d("SearchViewModel", "AI önerisi için kullanılan sensör verileri: $currentSensorData")
                val suggestions = geminiRepository.getSuggestions(currentSensorData)
                _suggestedPlants.value = suggestions
                isAIActive.value = true
                _filteredPlantList.value = suggestions
                Log.d("SearchViewModel", "AI önerileri alındı: ${suggestions.size} bitki")
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Öneri alınamadı", e)
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
            }
        }
    }

    fun loadInitialList() {
        isAIActive.value = false
        _filteredPlantList.value = localPlantList
    }
}



 */








/*
package com.farukayata.t_vac_kotlin.ui.search

import com.farukayata.t_vac_kotlin.model.Plant
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.SensorDataManager
import com.erayerarslan.t_vac_kotlin.repository.GeminiRepository
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    val isAIActive = MutableStateFlow(false)
    private val _filteredPlantList = MutableLiveData<List<Plant>>(emptyList())
    val filteredPlantList: LiveData<List<Plant>> get() = _filteredPlantList
    private val _suggestedPlants = MutableStateFlow<List<Plant>>(emptyList())
    val suggestedPlants: StateFlow<List<Plant>> = _suggestedPlants
    private var localPlantList: List<Plant> = emptyList()

    init {
        Plant.fetchAll(
            onResult = { list ->
                localPlantList = list
                _filteredPlantList.value = list
                Log.d("SearchViewModel", "Veri çekildi: ${list}")
            },
            onError = { error: DatabaseError ->
                Log.e("SearchViewModel", "Veri çekilemedi: ${error.message}")
            }
        )
    }

    fun fetchPlantList(query: String) {
        val source = if (isAIActive.value) {
            _suggestedPlants.value
        } else {
            localPlantList
        }

        val filtered = if (query.isBlank()) {
            source
        } else {
            source.filter { it.nameFilter(query) }
        }

        _filteredPlantList.value = filtered
    }

    fun fetchPlantSuggestions(sensorData: SensorData) {
        viewModelScope.launch {
            try {
                // Sensör verilerini doğrudan kullan
                val currentSensorData = SensorDataManager.getLatestSensorData() ?: sensorData
                Log.d("SearchViewModel", "Kullanılan sensör verileri: $currentSensorData")

                val suggestions = geminiRepository.getSuggestions(currentSensorData)
                _suggestedPlants.value = suggestions
                isAIActive.value = true
                _filteredPlantList.value = suggestions
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Öneri alınamadı", e)
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
            }
        }
    }

    fun loadInitialList() {
        isAIActive.value = false
        _filteredPlantList.value = localPlantList
    }
}



 */









/*
package com.farukayata.t_vac_kotlin.ui.search

import com.farukayata.t_vac_kotlin.model.Plant
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farukayata.t_vac_kotlin.model.SensorData
import com.erayerarslan.t_vac_kotlin.repository.GeminiRepository
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    // AI önerileri aktif mi?
    val isAIActive = MutableStateFlow(false)

    // Ekrana yansıyacak, arama ve öneri sonrası filtrelenmiş liste
    private val _filteredPlantList = MutableLiveData<List<Plant>>(emptyList())
    val filteredPlantList: LiveData<List<Plant>> get() = _filteredPlantList

    // AI’den gelen öneri listesi
    private val _suggestedPlants = MutableStateFlow<List<Plant>>(emptyList())
    val suggestedPlants: StateFlow<List<Plant>> = _suggestedPlants

    // Firebase’den çektiğimiz tüm bitkileri tutan lokal değişken
    private var localPlantList: List<Plant> = emptyList()

    init {
        // Uygulama açılır açılmaz Firebase’den veriyi çek
        Plant.fetchAll(
            onResult = { list ->
                localPlantList = list                // Lokal listeyi güncelle
                _filteredPlantList.value = list      // UI’da da göster
                Log.d("SearchViewModel", "Veri çekildi: ${list} adet veri")
            },
            onError = { error: DatabaseError ->
                Log.e("SearchViewModel", "Veri çekilemedi: ${error.message}")
            }
        )
    }

    /**
     * Arama kutusuna her karakter girildiğinde çağrılır.
     * AI aktifse öneri listesini, değilse lokal listeyi filtreler.
     */
    fun fetchPlantList(query: String) {
        val source = if (isAIActive.value) {
            _suggestedPlants.value
        } else {
            localPlantList
        }

        val filtered = if (query.isBlank()) {
            source
        } else {
            source.filter { it.nameFilter(query) }
        }

        _filteredPlantList.value = filtered
    }

    /**
     * Sensör verisine göre Gemini API’dan öneri alır.
     * Başarılıysa AI modunu açar ve önerileri gösterir.
     */
    fun fetchPlantSuggestions(sensorData: SensorData) {
        viewModelScope.launch {
            try {
                val suggestions = geminiRepository.getSuggestions(sensorData)
                _suggestedPlants.value = suggestions
                isAIActive.value = true
                _filteredPlantList.value = suggestions
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Öneri alınamadı", e)
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
            }
        }
    }

    /**
     * Search ekranına her döndüğünüzde lokal listeyi (AI kapalı) tekrar yüklersiniz.
     */
    fun loadInitialList() {
        isAIActive.value = false
        _filteredPlantList.value = localPlantList
    }
}


 */