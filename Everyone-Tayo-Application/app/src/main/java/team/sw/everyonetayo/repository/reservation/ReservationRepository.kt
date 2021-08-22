package team.sw.everyonetayo.repository.reservation

import team.sw.everyonetayo.domain.Reservation

interface ReservationRepository {

    fun updateReservation(busNumber:String, busStop:String, timeStamp:String)
    fun getReservation() : Reservation?
}