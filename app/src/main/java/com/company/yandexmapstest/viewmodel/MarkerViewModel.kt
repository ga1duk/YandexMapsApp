package com.company.yandexmapstest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.company.yandexmapstest.entity.MarkerEntity
import com.company.yandexmapstest.repository.MarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkerViewModel @Inject constructor(private val repository: MarkerRepository) : ViewModel() {

    val data = repository.data.asLiveData(Dispatchers.Default)

    fun save(marker: MarkerEntity) = viewModelScope.launch {
        try {
            repository.save(marker)
        } catch (e: Exception) {
//            _dataState.value = FeedModelState(error = true)
        }
    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
        } catch (e: Exception) {
//            _dataState.value = FeedModelState(error = true)
        }
    }

}