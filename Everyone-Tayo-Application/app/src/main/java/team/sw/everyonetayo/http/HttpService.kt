package team.sw.everyonetayo.http;

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import team.sw.everyonetayo.http.domain.ReservationResponse

public interface HttpService {
    @FormUrlEncoded
    @POST("/reservation-app-user")
    fun reservation(
        @Field("busNumber") state:String,
        @Field("latitude") latitude:String,
        @Field("longitude") longitude:String
    ) : Call<ReservationResponse>
}
