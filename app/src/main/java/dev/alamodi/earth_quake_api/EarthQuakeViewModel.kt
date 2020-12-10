package dev.alamodi.earth_quake_api

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.alamodi.earth_quake_api.api.EarthQuakeFetcher
import dev.alamodi.earth_quake_api.models.Features

class EarthQuakeViewModel : ViewModel() {
    val quakesLiveData: LiveData<List<Features>>

    init {
        quakesLiveData = EarthQuakeFetcher().fitchQuakers()
    }
}