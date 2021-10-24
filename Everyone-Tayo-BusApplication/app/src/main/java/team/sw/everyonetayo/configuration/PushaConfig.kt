package team.sw.everyonetayo.configuration;

import android.util.Log
import pusha2.client.ClientManager
import pusha2.client.handler.recieve.ClientRecieveHandler
import pusha2.client.handler.recieve.excutor.ClientRecieveExcutor
import pusha2.container.ClientContainer
import pusha2.domain.SockDto
import pusha2.server.ServerManager

import team.sw.everyonetayo.container.ReservationContainer
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.ReservationDto
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.heap.ReservationsRepository
import team.sw.everyonetayo.view.BusDriver

public class PushaConfig {
    companion object{
        val pushaConfig = PushaConfig()
    }

    val clientManager:ClientManager = ClientContainer.clientManager()
    val clientRecieveHandler:ClientRecieveHandler = ClientContainer.clientRecieveHandler()

    constructor(){
        Log.d("inittest", "pusha init")
        clientRecieveHandler.addCommand("RESERVATION_NOTICE" ,Reservation())
        clientRecieveHandler.addCommand("GET_OFF", GetOff())
    }

    /**
     *
     */

    inner class Reservation: ClientRecieveExcutor {
        override fun excute(sockDto: SockDto) {
            val busStop:String = sockDto.data.split("#")[0]
            val latitude:String = sockDto.data.split("#")[1]
            val longitude:String = sockDto.data.split("#")[2]

            val reservationsRepository:ReservationsRepository = ReservationContainer.instance.reservationsRepository()

            val reservationDto:ReservationDto = ReservationDto(
                busStop,
                latitude,
                longitude,
                false,
                false
            )
            reservationsRepository.getReservationList().add(reservationDto)

            val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")

            if(viewResult is Result.Success){
                //busDriver 뷰 가져오기
                val busDriverView:BusDriver  = viewResult.data as BusDriver
                //TODO 예약들어올 때 액션 추가
                busDriverView.speakGreenBell()
                busDriverView.lightOnOfGreenBlink()
                busDriverView.additems(busStop)
            }
        }
    }

    inner class GetOff: ClientRecieveExcutor {
        override fun excute(sockDto: SockDto) {
            val busStop:String = sockDto.data

            val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")


            if(viewResult is Result.Success){
                //busDriver 뷰 가져오기
                val busDriverView:BusDriver  = viewResult.data as BusDriver
                //TODO 하차할 때 액션 추가

                busDriverView.speekReadBell()
                busDriverView.lightOnOfRed()
            }
        }
    }
}
