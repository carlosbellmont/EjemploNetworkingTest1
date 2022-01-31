package com.example.ejemplonetworkingtest.data

import com.example.ejemplonetworkingtest.model.PersonajeDataSource
import com.example.ejemplonetworkingtest.model.PersonajeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonajeRemoteDataSource(apiClient: ApiClient) : PersonajeDataSource {

    private var call: Call<List<PersonajeModel>>? = null
    private val service = apiClient.build()

    override fun obtenerPersonajesCallback(callback: OperationCallback<PersonajeModel>) {

        call = service.obtenerPersonajes()
        call?.enqueue(object : Callback<List<PersonajeModel>> {
            override fun onFailure(call: Call<List<PersonajeModel>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<PersonajeModel>>, response: Response<List<PersonajeModel>>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onError("error")
                }

            }

    })
}

override fun cancel() {
    call?.cancel()
}
}