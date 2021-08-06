package team.sw.everyonetayo.http.domain

data class ReservationResponse (
    val state:String, //상태
    val busStop:String, //버스정류소 이름
    val timeStamp:String //예약 처리 시의 시간 ex) 2021-08-04
)