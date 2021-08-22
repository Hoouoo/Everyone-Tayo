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

    override fun logout() {
        user = null
    }

    override fun getLoggedInUser(): LoggedInUser? {
        return user!!;
    }

    override fun isLogin(): Boolean {
        return user != null
    }

    override fun login(loggedInUser:LoggedInUser): Result<LoggedInUser> {
        // handle login
        this.user = loggedInUser;
        return Result.Success(loggedInUser);
    }
}