package team.sw.everyonetayo.model.login

import retrofit2.Call
import retrofit2.Response
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.exception.LoginException
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import java.io.IOException
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.math.log

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginService {

    val loginRepository:LoginRepository;

    constructor(loginRepository: LoginRepository){
        this.loginRepository = loginRepository;
    }


    fun login(id: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val httpService : HttpService = HttpClient.getApiService()
            val postLogin:Call<LoginResponse> = httpService.login(id, password)
            try {
                val loginResponse:LoginResponse? = postLogin.execute().body()

                val userId = loginResponse!!.userId
                val displayName = loginResponse!!.displayName
                val token = loginResponse!!.token

                val loggedInUser : LoggedInUser = LoggedInUser(userId, displayName, token)
                loginRepository.login(loggedInUser)
                return Result.Success(loggedInUser)
            }catch (e: IOException){
                return Result.Error(e)
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        loginRepository.logout()
    }
}