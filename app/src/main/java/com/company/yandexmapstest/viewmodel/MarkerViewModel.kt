package com.company.yandexmapstest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.company.yandexmapstest.dto.Marker
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

    fun editById(id: Long, content: String) = viewModelScope.launch {
        repository.updateDescriptionById(id, content)
    }
//
//    fun changeDescription(description: String) {
//        val text = description.trim()
//        if (edited.value?.description == text) {
//            return
//        }
//        edited.value = edited.value?.copy(description = text)
//    }
//
//    fun saveMarker() {
//        edited.value?.let {
//            viewModelScope.launch {
//                try {
//                    repository.save(MarkerEntity.fromDto(it))
////                    _dataState.value = FeedModelState()
////                    _postCreated.value = Unit
//                } catch (e: Exception) {
////            _dataState.value = FeedModelState(error = true)
//                }
//            }
//            edited.value = empty
//        }
//    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            repository.removeById(id)
        } catch (e: Exception) {
//            _dataState.value = FeedModelState(error = true)
        }
    }
}