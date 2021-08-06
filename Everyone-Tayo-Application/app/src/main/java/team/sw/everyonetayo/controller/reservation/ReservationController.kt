package team.sw.everyonetayo.controller.reservation

import team.sw.everyonetayo.model.reservation.ReservationService

class ReservationController {

    val reservationService:ReservationService;

    constructor(reservationService:ReservationService){
        this.reservationService = reservationService;
    }

    //TODO:Implement

    fun reservation(busNumber:String, latitude:String, longitude:String){
        reservationService.reservation(busNumber, latitude, longitude);
    }
}