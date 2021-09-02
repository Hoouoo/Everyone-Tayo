package team.sw.everyonetayo.http;

import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.http.*
import team.sw.everyonetayo.domain.GetOffResponse
import team.sw.everyonetayo.http.domain.GetOffRequest
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.http.domain.ReservationRequest
import team.sw.everyonetayo.http.domain.ReservationResponse
import java.sql.SQLOutput

public interface HttpService {

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/reservation-app-user")
    fun reservation(
        @Body body: ReservationRequest
//        @Field("busNumber") busNumber:String,
//        @Field("latitude") latitude:String,
//        @Field("longitude") longitude:String,
//        @Field("token") token:String
    ) : Call<ReservationResponse>

    @FormUrlEncoded
    @POST("/start-app-user")
    fun login(
        @Field("state") state:String,
    ) : Call<LoginResponse>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/off-app-bus")
    fun getOff(
        @Body body: GetOffRequest
    ) : Call<GetOffResponse>
}
