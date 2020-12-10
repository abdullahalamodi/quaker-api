package dev.alamodi.earth_quake_api.models

import com.google.gson.annotations.SerializedName

data class Features(

//    @SerializedName("type") var type : String,
    @SerializedName("properties") var properties: Properties,
    @SerializedName("geometry") var geometry: Geometry,
    @SerializedName("id") var id: String

)

data class Properties(

    @SerializedName("mag") var mag: Double,
    @SerializedName("place") var place: String,
    @SerializedName("time") var time: Long
//    @SerializedName("updated") var updated : Int,
//    @SerializedName("tz") var tz : String,
//    @SerializedName("url") var url : String,
//    @SerializedName("detail") var detail : String,
//    @SerializedName("felt") var felt : String,
//    @SerializedName("cdi") var cdi : String,
//    @SerializedName("mmi") var mmi : String,
//    @SerializedName("alert") var alert : String,
//    @SerializedName("status") var status : String,
//    @SerializedName("tsunami") var tsunami : Int,
//    @SerializedName("sig") var sig : Int,
//    @SerializedName("net") var net : String,
//    @SerializedName("code") var code : String,
//    @SerializedName("ids") var ids : String,
//    @SerializedName("sources") var sources : String,
//    @SerializedName("types") var types : String,
//    @SerializedName("nst") var nst : String,
//    @SerializedName("dmin") var dmin : String,
//    @SerializedName("rms") var rms : Double,
//    @SerializedName("gap") var gap : String,
//    @SerializedName("magType") var magType : String,
//    @SerializedName("type") var type : String,
//    @SerializedName("title") var title : String

)

data class Geometry(

//    @SerializedName("type") var type : String,
    @SerializedName("coordinates") var coordinates: List<Double>

)