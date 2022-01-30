package com.example.ejemplonetworkingtest.model

import com.example.ejemplonetworkingtest.data.OperationCallback

interface PersonajeDataSource {

    fun obtenerPersonajesCallback(callback: OperationCallback<PersonajeModel>)
    fun cancel()
}