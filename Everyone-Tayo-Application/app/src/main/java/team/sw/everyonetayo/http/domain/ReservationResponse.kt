package team.sw.everyonetayo.http.domain

import com.google.gson.annotations.SerializedName

data class ReservationResponse (
    @SerializedName("state")
    val state:String, //상태
    @SerializedName("uuid")
    val uuid:String,
    @SerializedName("nodeId")
    val nodeId:String, //버스정류소 이름
    @SerializedName("busNumber")
    val busNumber:String,
    @SerializedName("time")
    val time:String //예약 처리 시의 시간 ex) 2021-08-04
)