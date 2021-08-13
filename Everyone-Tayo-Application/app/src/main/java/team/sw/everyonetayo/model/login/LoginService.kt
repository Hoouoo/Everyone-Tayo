package team.sw.everyonetayo.model.login

import retrofit2.Call
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.http.domain.ReservationResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */

class LoginService {

    val loginRepository:LoginRepository;

    constructor(loginRepository: LoginRepository){
        this.loginRepository = loginRepository;
    }


    fun login(): Result<LoggedInUser> {
        val state:String = "user-start";

        val httpService: HttpService = HttpClient.getApiService();
        val postLogin: Call<LoginResponse> = httpService.login(state);

        try {
            if(!loginRepository.isLogin()){
                var result:Result<LoggedInUser>? = null
                val thread:Thread = Thread(Runnable {
                    try {
                        val loginResponse: LoginResponse? = postLogin.execute().body();

                        val token = loginResponse!!.token;

                        val loggedInUser: LoggedInUser = LoggedInUser(token)
                        loginRepository.login(loggedInUser)
                        result = Result.Success(loggedInUser)
                    }catch (e:SocketTimeoutException){
                        result = Result.Error(e)
                    }catch (e: Exception){
                        result = Result.Error(e)
                    }
                })

                thread.start() // 통신시작
                thread.join()  // 통신종료까지 대기

                return result!!
            }else{
                return Result.Success(loginRepository.getLoggedInUser()!!)
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
        loginRepository.logout();
    }
}