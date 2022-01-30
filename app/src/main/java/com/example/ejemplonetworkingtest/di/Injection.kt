package com.example.ejemplonetworkingtest.di

import com.example.ejemplonetworkingtest.viewmodel.ViewModelFactory
import com.example.ejemplonetworkingtest.data.ApiClient
import com.example.ejemplonetworkingtest.data.PersonajeRemoteDataSource
import com.example.ejemplonetworkingtest.model.PersonajeDataSource
import com.example.ejemplonetworkingtest.model.PersonajeRepository

object Injection {

    private var personajeDataSource: PersonajeDataSource? = null
    private var personajeRepository: PersonajeRepository? = null
    private var personajeViewModelFactory: ViewModelFactory? = null

    private fun createPersonajeDataSource(): PersonajeDataSource {
        val dataSource = PersonajeRemoteDataSource(ApiClient)
        personajeDataSource = dataSource
        return dataSource
    }

    private fun createPersonajeRepository(): PersonajeRepository {
        val repository = PersonajeRepository(provideDataSource())
        personajeRepository = repository
        return repository
    }

    private fun createFactory(): ViewModelFactory {
        val factory = ViewModelFactory(providerRepository())
        personajeViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = personajeDataSource ?: createPersonajeDataSource()
    private fun providerRepository() = personajeRepository ?: createPersonajeRepository()

    fun provideViewModelFactory() = personajeViewModelFactory ?: createFactory()

    fun destroy() {
        personajeDataSource = null
        personajeRepository = null
        personajeViewModelFactory = null
    }
}