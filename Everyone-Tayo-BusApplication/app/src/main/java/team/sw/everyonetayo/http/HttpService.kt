package team.sw.everyonetayo.http;

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import team.sw.everyonetayo.http.domain.LoginResponse

public interface HttpService {
    @FormUrlEncoded
    @POST("/login-app-bus")
    fun login(
        @Field("id") id:String,
        @Field("password") password:String
    ) : Call<LoginResponse>
}
