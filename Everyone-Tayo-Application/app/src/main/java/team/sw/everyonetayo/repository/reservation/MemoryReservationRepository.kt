package team.sw.everyonetayo.repository.reservation

import team.sw.everyonetayo.domain.Reservation

class MemoryReservationRepository : ReservationRepository {

    private var reservation:Reservation? = null;

    override fun updateReservation(busNumber: String, busStop: String, timeStamp: String) {
        reservation = Reservation(busNumber, busStop, timeStamp)
    }

    override fun getReservation() : Reservation? {
        return this.reservation;
    }

}