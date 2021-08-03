package team.sw.everyonetayo.http.domain;

data class LoginResponse (
    val uuid: String,
    val displayName: String,
    val token: String,
)
