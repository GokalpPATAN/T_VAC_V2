package com.farukayata.t_vac_kotlin.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.farukayata.t_vac_kotlin.model.SensorData
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.model.treeList
import com.farukayata.t_vac_kotlin.repository.GeminiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    //ai dan öncesi ve sonrası liste farkını anlamak için flag niyetine attık
    val isAIActive = MutableStateFlow(false)

    // Arama kutusu için (mevcut yapı korunur)
    private val _filteredTreeListSearch = MutableLiveData<List<Tree>>()
    val filteredTreeListSearch: LiveData<List<Tree>> get() = _filteredTreeListSearch

    val searchFilterList = treeList.toList()

    fun fetchTreeList(name: String) {
        val filteredList = searchFilterList.filter { tree ->
            tree.nameFilter(name)
        }
        Log.d("SearchViewModel", "Filtered tree list: $filteredList")
        _filteredTreeListSearch.value = filteredList
    }

    //AI önerileri için yeni yapı
    private val _suggestedTrees = MutableStateFlow<List<Tree>>(emptyList())
    val suggestedTrees: StateFlow<List<Tree>> = _suggestedTrees

    fun fetchTreeSuggestions(sensorData: SensorData) {
        viewModelScope.launch {
            try {
                val trees = geminiRepository.getSuggestions(sensorData)
                _suggestedTrees.value = trees
                isAIActive.value = true // <-- Aİ MOD AKTİF 🧠
            } catch (e: Exception) {
                e.printStackTrace()
                _suggestedTrees.value = emptyList()
            }
        }
    }

}






/*
package com.farukayata.t_vac_kotlin.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farukayata.t_vac_kotlin.model.Tree
import com.farukayata.t_vac_kotlin.model.treeList


class SearchViewModel : ViewModel() {
    private val _filteredTreeListSearch = MutableLiveData<List<Tree>>()
    val filteredTreeListSearch: LiveData<List<Tree>> get() = _filteredTreeListSearch

   val searchFilterList = treeList.toList()

    fun fetchTreeList(name: String) {
        val filteredList = searchFilterList.filter { tree ->
            tree.nameFilter(name)

        }
        Log.d("HomeViewModel", "Filtered tree list: $filteredList") // Filtrelenen listeyi logla
        _filteredTreeListSearch.value = filteredList
    }


}
*/