package team.sw.everyonetayo.model.getoff

import retrofit2.Call
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.domain.GetOffResponse
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.GetOffRequest
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import java.lang.Exception

class GetOffService {
    val loginRepository:LoginRepository

    constructor(){
        loginRepository = LoginContainer.instance.loginRepository()
    }

    fun getOff():Result<GetOffResponse>{

        var result:Result<GetOffResponse>? = null

        val getOffThread:Thread = Thread(Runnable {
                try {
                    val token:String = loginRepository.getLoggedInUser()!!.token
                    val httpService: HttpService = HttpClient.getApiService();
                    val postGetOff: Call<GetOffResponse> = httpService.getOff(GetOffRequest(token))

                    val response:GetOffResponse? = postGetOff.execute().body()

                    if(response!!.state=="OK"){
                        throw Exception()
                    }

                    result = Result.Success(response)
                }catch (e:Exception){
                    e.printStackTrace()
                    result = Result.Error(e)
                }
            }
        )
        getOffThread.start()
        getOffThread.join()

        return result!!
    }
}