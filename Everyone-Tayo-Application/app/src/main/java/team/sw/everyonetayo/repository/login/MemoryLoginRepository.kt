package team.sw.everyonetayo.repository.login

import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.domain.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class MemoryLoginRepository() : LoginRepository{

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null


    override fun logout() {
        user = null
    }

    override fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        return Result.Success(LoggedInUser("hi","hi","hi"));
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}