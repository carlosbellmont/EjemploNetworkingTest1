package com.example.ejemplonetworkingtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplonetworkingtest.data.OperationCallback
import com.example.ejemplonetworkingtest.model.PersonajeModel
import com.example.ejemplonetworkingtest.model.PersonajeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonajeViewModel(private val repository: PersonajeRepository) : ViewModel() {

    private val _personajes = MutableLiveData<List<PersonajeModel>>().apply { value = emptyList() }
    val personaje: LiveData<List<PersonajeModel>> = _personajes

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadPersonajesCallback() {
        _isViewLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchPersonajesCallback(object : OperationCallback<PersonajeModel> {
                override fun onError(error: String?) {
                    _isViewLoading.value = false
                    _onMessageError.value = error ?: ""

                }

                override fun onSuccess(data: List<PersonajeModel>?) {
                    _isViewLoading.value = false
                    if (data.isNullOrEmpty()) {
                        _isEmptyList.value = true

                    } else {
                        _personajes.value = data
                    }
                }
            })
        }
    }

}