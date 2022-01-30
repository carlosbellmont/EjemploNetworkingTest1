package com.example.ejemplonetworkingtest.data

import com.example.ejemplonetworkingtest.model.PersonajeModel


data class PersonajesResponse(val status: Int?, val msg: String?, val data: List<PersonajeModel>?) {
    fun isSuccess(): Boolean = (status == 200)
}