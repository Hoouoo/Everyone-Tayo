package team.sw.everyonetayo.domain


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userName: String,
    val displayName: String,
    val token: String
)