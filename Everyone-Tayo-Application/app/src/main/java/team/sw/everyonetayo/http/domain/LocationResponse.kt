package team.sw.everyonetayo.http.domain

import com.google.gson.annotations.SerializedName

data class LocationResponse (
        @SerializedName("name")
        val name:String,
        @SerializedName("latitude")
        val latitude:String,
        @SerializedName("longitude")
        val longitude:String
)
