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

    private val _suggestedTrees = MutableStateFlow<List<Tree>>(emptyList())
    val suggestedTrees: StateFlow<List<Tree>> = _suggestedTrees

    private val localTreeList = treeList.toList()


    // Bu fonksiyon artık aktif listeye göre çalışacak
    fun fetchTreeList(query: String) {
        val activeList = if (isAIActive.value) {
            suggestedTrees.value
        } else {
            localTreeList
        }

        val filtered = if (query.isBlank()) {
            activeList
            //arama kutusunun boşluk olma olayında etkin
        } else {
            activeList.filter { it.nameFilter(query) }
        }

        _filteredTreeListSearch.value = filtered
    }


    //AI önerileri için yeni yapı
    fun fetchTreeSuggestions(sensorData: SensorData) {
        viewModelScope.launch {
            try {
                val trees = geminiRepository.getSuggestions(sensorData)
                _suggestedTrees.value = trees
                isAIActive.value = true
                _filteredTreeListSearch.value = trees // Ekranda hemen gözüksün
            } catch (e: Exception) {
                e.printStackTrace()
                _suggestedTrees.value = emptyList()
                _filteredTreeListSearch.value = emptyList()
            }
        }
    }

    //search sayfasına tıkladık lokal listemiz gözüktü
    fun loadInitialList() {
        _filteredTreeListSearch.value = localTreeList
        isAIActive.value = false
    }

}