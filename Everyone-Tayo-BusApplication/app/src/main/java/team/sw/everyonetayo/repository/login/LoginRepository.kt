package team.sw.everyonetayo.repository.login

import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result

interface LoginRepository {
    fun login(loggedInUser: LoggedInUser): Result<LoggedInUser>;
    fun logout();
    fun getLoggedInUser():LoggedInUser?
    fun isLogin():Boolean
}