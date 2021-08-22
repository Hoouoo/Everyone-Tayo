package team.sw.everyonetayo.domain

import java.io.Serializable


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class LoggedInUser : Serializable{
    val token: String

    constructor(token:String){
        this.token = token
    }
}

