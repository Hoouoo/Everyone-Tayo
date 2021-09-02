package team.sw.everyonetayo.model.login

import pusha.client.manager.ClientManager
import retrofit2.Call
import retrofit2.Response
import team.sw.everyonetayo.configuration.Config
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.exception.LoginException
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.LoginRequest
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import java.io.IOException
import java.lang.Exception
import java.net.SocketAddress
import java.net.SocketTimeoutException
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
            // TODO: handle loggedInUser authentication
        var result:Result<LoggedInUser>? = null
        val httpService : HttpService = HttpClient.getApiService()
        val postLogin:Call<LoginResponse> = httpService.login(LoginRequest(id, password))
        val thread:Thread = Thread(Runnable {
            try {
                /**
                 * login processing
                 */
                val loginResponse: LoginResponse? = postLogin.execute().body()

                val uuid = loginResponse!!.uuid
                val displayName = loginResponse!!.busNumber
                val token = loginResponse!!.token

                //throw error
                if (uuid.equals("nope")) {
                    LoginException("Login failed")
                }

                println("uuid = ${uuid}")
                println("displayName = ${displayName}")
                println("token = ${token}")

                //add to login repository
                val loggedInUser: LoggedInUser = LoggedInUser(uuid, displayName, token)
                loginRepository.login(loggedInUser)

                println("loggedInUser.displayName = ${loggedInUser.displayName}")
                println("loggedInUser.uuid = ${loggedInUser.uuid}")
                /**
                 * connect push server
                 */

                println("Config.IP = ${Config.IP}")
                println("Config.PORT = ${Config.PORT}")
                println("loginRepository = ${loginRepository.getLoggedInUser()!!.uuid}")


                ClientManager.instance.connect(
                    Config.IP, Config.PORT, loginRepository.getLoggedInUser()!!.uuid
                );

                result = Result.Success(loggedInUser)

            } catch (e: SocketTimeoutException) {
                result = Result.Error(e)
                e.printStackTrace()
            } catch (e : Exception){
                e.printStackTrace()
            }
        })

        thread.start() // 통신시작
        thread.join() // 통신종료 까지 대기

        ClientManager.instance.process();


        return result!!
    }



    fun logout() {
        // TODO: revoke authentication
        loginRepository.logout()
        ClientManager.instance.socket.close();
        ClientManager.instance.clientProcessingThread.interrupt();
    }
}