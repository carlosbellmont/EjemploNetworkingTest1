package com.example.ejemplonetworkingtest

import com.example.ejemplonetworkingtest.model.PersonajeModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.junit.Assert
import org.junit.Test

class PersonajeModelTest {

    private val gson = Gson()

    @Test
    fun `crear personaje a partir de un json correcto`(){
        val value = gson.fromJson(harryPotterJson, PersonajeModel::class.java)
        Assert.assertEquals(value, harryPotter)
    }

    @Test
    fun `crear personaje a partir de un json sin id`(){
        val value = gson.fromJson(harryPotterJsonSinId, PersonajeModel::class.java)
        Assert.assertNotEquals(value, harryPotter)
    }

    @Test
    fun `obtener nombre corto para Harry Potter esperando Harry`(){
        val value = gson.fromJson(harryPotterJson, PersonajeModel::class.java)
        Assert.assertEquals(value.getNombreCorto(), "Harry")
    }

    @Test
    fun `crear personaje a partir de un json con id erroneo`(){
        Assert.assertThrows(JsonSyntaxException::class.java) {
            gson.fromJson(harryPotterJsonIdEnString, PersonajeModel::class.java)
        }
    }

    private val harryPotter = PersonajeModel(1,"Harry James Potter", "https://raw.githubusercontent.com/fedeperin/harry-potter-api/main/imagenes/harry_potter.png")
    private val harryPotterJson = " {\n" +
            " \"id\": 1,\n" +
            " \"personaje\": \"Harry James Potter\",\n" +
            " \"apodo\": \"Harry\",\n" +
            " \"estudianteDeHogwarts\": true,\n" +
            " \"casaDeHogwarts\": \"Gryffindor\",\n" +
            " \"interpretado_por\": \"Daniel Radcliffe\",\n" +
            " \"hijos\": [\n" +
            " \"James Sirius Potter\",\n" +
            " \"Albus Severus Potter\",\n" +
            " \"Lily Luna Potter\"\n" +
            " ],\n" +
            " \"imagen\": \"https://raw.githubusercontent.com/fedeperin/harry-potter-api/main/imagenes/harry_potter.png\"\n" +
            "  }"

    private val harryPotterJsonIdEnString = " {\n" +
            " \"id\": \"error\",\n" + // El error está aquí.
            " \"personaje\": \"Harry James Potter\",\n" +
            " \"apodo\": \"Harry\",\n" +
            " \"estudianteDeHogwarts\": true,\n" +
            " \"casaDeHogwarts\": \"Gryffindor\",\n" +
            " \"interpretado_por\": \"Daniel Radcliffe\",\n" +
            " \"hijos\": [\n" +
            " \"James Sirius Potter\",\n" +
            " \"Albus Severus Potter\",\n" +
            " \"Lily Luna Potter\"\n" +
            " ],\n" +
            " \"imagen\": \"https://raw.githubusercontent.com/fedeperin/harry-potter-api/main/imagenes/harry_potter.png\"\n" +
            "  }"
    private val harryPotterJsonSinId = " {\n" +
            //" \"id\": 1,\n" +
            " \"personaje\": \"Harry James Potter\",\n" +
            " \"apodo\": \"Harry\",\n" +
            " \"estudianteDeHogwarts\": true,\n" +
            " \"casaDeHogwarts\": \"Gryffindor\",\n" +
            " \"interpretado_por\": \"Daniel Radcliffe\",\n" +
            " \"hijos\": [\n" +
            " \"James Sirius Potter\",\n" +
            " \"Albus Severus Potter\",\n" +
            " \"Lily Luna Potter\"\n" +
            " ],\n" +
            " \"imagen\": \"https://raw.githubusercontent.com/fedeperin/harry-potter-api/main/imagenes/harry_potter.png\"\n" +
            "  }"
}