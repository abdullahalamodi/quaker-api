package dev.alamodi.earth_quake_api.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.alamodi.earth_quake_api.models.Features
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthQuakeFetcher {
    private val earthQuakeApi: EarthQuakeApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        earthQuakeApi = retrofit.create(EarthQuakeApi::class.java)
    }

    fun fitchQuakers(): LiveData<List<Features>> {
        val responseLiveData: MutableLiveData<List<Features>> = MutableLiveData()
        val quakeRequest: Call<EarthQuakeResponse> = earthQuakeApi.fitchQuakes()
        quakeRequest.enqueue(object : Callback<EarthQuakeResponse> {
            override fun onFailure(call: Call<EarthQuakeResponse>, t: Throwable) {
                Log.e("Failure", "Failed to fetch quakers", t)
            }

            override fun onResponse(
                call: Call<EarthQuakeResponse>,
                response: Response<EarthQuakeResponse>
            ) {
                Log.e("sucess", response.code().toString())
                val quakerResponse: EarthQuakeResponse? = response.body()
                var featuresList: List<Features> = quakerResponse?.features
                    ?: mutableListOf()

                responseLiveData.value = featuresList
            }
        })
        return responseLiveData
    }
}