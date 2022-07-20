package com.company.yandexmapstest.viewmodel

import androidx.lifecycle.*
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.model.MapModelState
import com.company.yandexmapstest.repository.MarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MarkerViewModel @Inject constructor(private val repository: MarkerRepository) : ViewModel() {

    val data = repository.data.asLiveData(Dispatchers.Default)

    private var _dataState = MutableLiveData<MapModelState>()
    val dataState: LiveData<MapModelState>
        get() = _dataState

    fun save(marker: MarkerEntity) = viewModelScope.launch {
        try {
            repository.save(marker)
        } catch (e: Exception) {
            _dataState.value = MapModelState(error = true)
        }
    }

    fun editById(id: Long, content: String) = viewModelScope.launch {
        try {
            repository.updateDescriptionById(id, content)
        } catch (e: Exception) {
            _dataState.value = MapModelState(error = true)
        }
    }


    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
        } catch (e: Exception) {
            _dataState.value = MapModelState(error = true)
        }
    }
}