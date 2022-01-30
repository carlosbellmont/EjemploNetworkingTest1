package com.example.ejemplonetworkingtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ejemplonetworkingtest.model.PersonajeRepository

class ViewModelFactory(private val repository: PersonajeRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonajeViewModel(repository) as T
    }
}