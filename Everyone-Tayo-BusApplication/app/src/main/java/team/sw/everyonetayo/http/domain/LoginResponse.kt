package team.sw.everyonetayo.http.domain;

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("busNumber")
    val busNumber: String,
    @SerializedName("token")
    val token: String,
)
