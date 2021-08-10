package team.sw.everyonetayo.controller.reservation

import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.domain.ReservationResponse
import team.sw.everyonetayo.model.reservation.ReservationService

class ReservationController {

    val reservationService:ReservationService;

    constructor(reservationService:ReservationService){
        this.reservationService = reservationService;
    }

    //TODO:Implement

    fun reservation(busNumber:String, latitude:String, longitude:String):Result<ReservationResponse>{
        return reservationService.reservation(busNumber, latitude, longitude)
    }
}