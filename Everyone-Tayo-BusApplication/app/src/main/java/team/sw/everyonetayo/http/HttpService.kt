package team.sw.everyonetayo.http;

import retrofit2.Call
import retrofit2.http.*
import team.sw.everyonetayo.http.domain.LoginRequest
import team.sw.everyonetayo.http.domain.LoginResponse

public interface HttpService {

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/login-app-bus")
    fun login(
        @Body body : LoginRequest
//        @Field("id") id:String,
//        @Field("password") password:String
    ) : Call<LoginResponse>
}
