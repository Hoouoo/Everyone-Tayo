package team.sw.everyonetayo.model.reservation

import team.sw.everyonetayo.repository.heap.ReservationsRepository

class ReservationManagement {

    private val reservationsRepository:ReservationsRepository
    lateinit var managementThread:Thread
    var managementRunnable:ManagementRunnable;

    constructor(reservationsRepository: ReservationsRepository){
        this.reservationsRepository = reservationsRepository
        this.managementRunnable = ManagementRunnable(reservationsRepository)
    }

    fun start(){
        managementThread = Thread(managementRunnable)
        managementThread.start()
    }

    fun stop(){
        managementThread.interrupt()
    }
}