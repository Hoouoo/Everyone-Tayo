package team.sw.everyonetayo.domain

data class ReservationDto(
    val busStop:String,
    val latitude:String,
    val longitude:String,
    var isArrived:Boolean,
    var isGone:Boolean
)
