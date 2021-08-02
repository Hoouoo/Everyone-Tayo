package team.sw.everyonetayo.repository.login

import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result

interface LoginRepository {
    fun login(username: String, password: String): Result<LoggedInUser>;
    fun logout();
}