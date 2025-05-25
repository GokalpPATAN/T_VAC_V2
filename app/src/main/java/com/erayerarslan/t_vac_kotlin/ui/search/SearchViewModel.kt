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
                isAIActive.value = true
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Bitki önerileri alınırken hata: ${e.message}")
                _suggestedPlants.value = emptyList()
                _filteredPlantList.value = emptyList()
                isAIActive.value = false
            }
        }
    }


    fun loadInitialList() {
        isAIActive.value = false
        _filteredPlantList.value = localPlantList
    }

    fun updateSuggestedPlantImage(plantName: String, imageUrl: String) {
        val updatedList = _suggestedPlants.value.map {
            if (it.name == plantName) it.copy(img = imageUrl) else it
        }
        _suggestedPlants.value = updatedList
        if (isAIActive.value) {
            _filteredPlantList.value = updatedList
        }
    }
}