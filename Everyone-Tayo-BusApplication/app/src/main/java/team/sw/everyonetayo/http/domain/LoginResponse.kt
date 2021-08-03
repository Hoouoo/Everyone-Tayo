package team.sw.everyonetayo.http.domain;

data class LoginResponse (
    val userId: String,
    val displayName: String,
    val token: String,
)
