package team.sw.everyonetayo.http.domain

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("token")
    val token:String
)