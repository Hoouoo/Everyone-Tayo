package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.model.login.LoginService
import team.sw.everyonetayo.model.reservation.ReservationManagement
import team.sw.everyonetayo.repository.heap.ReservationsRepository
import team.sw.everyonetayo.repository.login.LoginRepository

class ReservationContainer {
    companion object {
        val instance: ReservationContainer = ReservationContainer();
    }

    private var reservationsRepository: ReservationsRepository? = null
    private var reservationManagement: ReservationManagement? = null

    constructor() {
        reservationsRepository = reservationsRepository()
        reservationManagement = reservationManagement()
    }

    fun reservationsRepository(): ReservationsRepository {
        if(reservationsRepository==null)
            reservationsRepository = ReservationsRepository()
        return reservationsRepository!!
    }

    fun reservationManagement(): ReservationManagement {
        if(reservationManagement==null)
            reservationManagement = ReservationManagement(reservationsRepository())
        return reservationManagement!!
    }
}