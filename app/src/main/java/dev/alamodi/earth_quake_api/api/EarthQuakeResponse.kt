package dev.alamodi.earth_quake_api.api

import com.google.gson.annotations.SerializedName
import dev.alamodi.earth_quake_api.models.Features

class EarthQuakeResponse {
    //    @SerializedName("features")
    lateinit var features: List<Features>
}