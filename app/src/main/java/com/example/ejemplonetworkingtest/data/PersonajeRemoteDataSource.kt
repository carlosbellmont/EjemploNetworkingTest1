package com.example.ejemplonetworkingtest.data

import com.example.ejemplonetworkingtest.app.App
import com.example.ejemplonetworkingtest.model.PersonajeModel
import com.example.ejemplonetworkingtest.model.PersonajeDataSource
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class PersonajeRemoteDataSource(apiClient: ApiClient) : PersonajeDataSource {

    private var call: Call<List<PersonajeModel>>? = null
    private val service = apiClient.build()

    override fun obtenerPersonajesCallback(callback: OperationCallback<PersonajeModel>) {

        call = service.obtenerPersonajes()
        call?.enqueue(object : Callback<List<PersonajeModel>> {
            override fun onFailure(call: Call<List<PersonajeModel>>, t: Throwable) {
                CoroutineScope(Dispatchers.IO).launch {
                    callback.onError(t.message)
                }
            }

            override fun onResponse(call: Call<List<PersonajeModel>>, response: Response<List<PersonajeModel>>) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let {
                        if (response.isSuccessful) {
                            callback.onSuccess(it)
                        } else {
                            callback.onError("error")
                        }
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}