package com.example.ejemplonetworkingtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ejemplonetworkingtest.data.OperationCallback
import com.example.ejemplonetworkingtest.model.PersonajeModel
import com.example.ejemplonetworkingtest.model.PersonajeDataSource
import com.example.ejemplonetworkingtest.model.PersonajeRepository
import com.example.ejemplonetworkingtest.viewmodel.PersonajeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PersonajeViewModelTest {

    @Mock
    private lateinit var personajeDataSource: PersonajeDataSource

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<PersonajeModel>>

    private lateinit var viewModel: PersonajeViewModel
    private lateinit var repository: PersonajeRepository

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderPersonajesObserver: Observer<List<PersonajeModel>>

    private lateinit var personajeEmptyList: List<PersonajeModel>
    private lateinit var personajeList: List<PersonajeModel>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = PersonajeRepository(personajeDataSource)
        viewModel = PersonajeViewModel(repository)

        mockData()
        setupObservers()
    }



    @Test
    fun `obtener los datos de los personajes con resultado vacío`() {
        with(viewModel) {
            loadPersonajesCallback()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            personaje.observeForever(onRenderPersonajesObserver)
        }

        verify(personajeDataSource, times(1)).obtenerPersonajesCallback(
            capture(
                operationCallbackCaptor
            )
        )
        operationCallbackCaptor.value.onSuccess(personajeEmptyList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.isEmptyList.value == true)
        Assert.assertTrue(viewModel.personaje.value?.size == 0)


    }

    @Test
    fun `obtener los datos de los personajes de manera correcta`() {
        repository = PersonajeRepository(personajeDataSource)

        with(viewModel) {
            loadPersonajesCallback()
            isViewLoading.observeForever(isViewLoadingObserver)
            personaje.observeForever(onRenderPersonajesObserver)


            verify(personajeDataSource, times(1)).obtenerPersonajesCallback(capture(operationCallbackCaptor))
            operationCallbackCaptor.value.onSuccess(personajeList)

            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(viewModel.personaje.value?.size == personajeList.size)


        }
    }

    @Test
    fun `obtener los datos de los personajes de manera errónea`() {

        with(viewModel) {
            loadPersonajesCallback()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(personajeDataSource, times(1)).obtenerPersonajesCallback(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)


        Assert.assertNotNull(viewModel.onMessageError.value)
        Assert.assertEquals("An error occurred", viewModel.onMessageError.value)

    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderPersonajesObserver = mock(Observer::class.java) as Observer<List<PersonajeModel>>
    }

    private fun mockData() {
        personajeEmptyList = emptyList()
        val mockList: MutableList<PersonajeModel> = mutableListOf()
        mockList.add(PersonajeModel(1,"Harry James Potter", "https://raw.githubusercontent.com/fedeperin/harry-potter-api/main/imagenes/harry_potter.png"))
        mockList.add(PersonajeModel(2, "Hermione Jean Granger", ""))
        mockList.add(PersonajeModel(3, "Ron Weasley", ""))

        personajeList = mockList.toList()
    }
}