package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.reservation.ReservationController
import team.sw.everyonetayo.model.reservation.ReservationService
import team.sw.everyonetayo.repository.reservation.MemoryReservationRepository
import team.sw.everyonetayo.repository.reservation.ReservationRepository

class ReservationContainer {

    companion object {
        val instance: ReservationContainer = ReservationContainer();
    }

    private var reservationController:ReservationController;
    private var reservationService:ReservationService;
    private var reservationRepository:ReservationRepository;

    constructor(){
        this.reservationController = reservationController();
        this.reservationService = reservationService();
        this.reservationRepository = reservationRepository();
    }

    fun reservationRepository():ReservationRepository{
        if(this.reservationRepository==null){
            reservationRepository = MemoryReservationRepository();
            return reservationRepository
        }else{
            return reservationRepository
        }
    }

    fun reservationService():ReservationService {
        if(this.reservationService==null){
            reservationService = ReservationService(reservationRepository());
            return reservationService
        }else{
            return reservationService
        }
    }

    fun reservationController():ReservationController {
        if(this.reservationController==null){
            reservationController =  ReservationController(reservationService());
            return reservationController
        }else{
            return reservationController
        }
    }
}