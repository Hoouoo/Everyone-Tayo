package team.sw.everyonetayo.http.domain

import com.google.gson.annotations.SerializedName

data class ReservationResponse (
    @SerializedName("state")
    val state:String, //상태
    @SerializedName("busStop")
    val busStop:String, //버스정류소 이름
    @SerializedName("timeStamp")
    val timeStamp:String //예약 처리 시의 시간 ex) 2021-08-04
)