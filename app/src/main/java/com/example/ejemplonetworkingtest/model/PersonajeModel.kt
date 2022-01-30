package com.example.ejemplonetworkingtest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class PersonajeModel(@PrimaryKey val id: Int, val personaje: String, val imagen: String) : Serializable {
    fun getNombreCorto() = run { personaje.substringBefore(" ") }
}