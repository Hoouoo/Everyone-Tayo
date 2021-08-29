package team.sw.everyonetayo.repository.heap;

import team.sw.everyonetayo.domain.ReservationDto

class ReservationsRepository {
    private val reservations:ArrayList<ReservationDto> = ArrayList()

    fun getReservationList():ArrayList<ReservationDto>{
        return reservations
    }
}
