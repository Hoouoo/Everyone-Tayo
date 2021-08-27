package team.sw.everyonetayo.model.reservation

import team.sw.everyonetayo.repository.heap.ReservationsRepository

class ReservationManagement {

    private val reservationsRepository:ReservationsRepository
    private lateinit var managementThread:Thread

    constructor(reservationsRepository: ReservationsRepository){
        this.reservationsRepository = reservationsRepository
    }

    fun start(){
        managementThread = Thread(ManagementRunnable(reservationsRepository))
        managementThread.start()
    }

    fun stop(){
        managementThread.interrupt()
    }
}