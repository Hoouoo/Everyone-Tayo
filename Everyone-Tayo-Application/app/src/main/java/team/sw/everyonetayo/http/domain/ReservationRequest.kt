package team.sw.everyonetayo.http.domain

data class ReservationRequest (
    var busNumber:String,
    var latitude:String,
    var longitude:String,
    var token:String
)