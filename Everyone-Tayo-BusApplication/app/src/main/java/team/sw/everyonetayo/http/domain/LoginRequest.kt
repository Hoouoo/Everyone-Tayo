package team.sw.everyonetayo.http.domain

import retrofit2.http.Field

data class LoginRequest (
    var id:String,
    var password:String
)