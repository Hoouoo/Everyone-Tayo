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
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import java.io.IOException
import java.net.SocketAddress
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
                /**
                 * login processing
                 */
                val loginResponse:LoginResponse? = postLogin.execute().body()

                val uuid = loginResponse!!.uuid
                val displayName = loginResponse!!.displayName
                val token = loginResponse!!.token

                //throw error
                if(uuid.equals("LOGIN_FAILD")){
                    LoginException("Login failed")
                }

                //add to login repository
                val loggedInUser : LoggedInUser = LoggedInUser(uuid, displayName, token)
                loginRepository.login(loggedInUser)

                /**
                 * connect push server
                 */
                ClientManager.instance.connect(Config.IP, Config.PORT
                , loginRepository.getLoggedInUser()!!.uuid);
                ClientManager.instance.process();

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
        ClientManager.instance.socket.close();
        ClientManager.instance.clientProcessingThread.interrupt();
    }
}