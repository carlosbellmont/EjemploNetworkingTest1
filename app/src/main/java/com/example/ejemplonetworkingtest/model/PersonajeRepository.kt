package com.example.ejemplonetworkingtest.model

import com.example.ejemplonetworkingtest.data.OperationCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PersonajeRepository(private val personajeDataSource: PersonajeDataSource) {

    fun fetchPersonajesCallback(callback: OperationCallback<PersonajeModel>) {
        personajeDataSource.obtenerPersonajesCallback(callback)
    }

    fun cancel() {
        personajeDataSource.cancel()
    }

}