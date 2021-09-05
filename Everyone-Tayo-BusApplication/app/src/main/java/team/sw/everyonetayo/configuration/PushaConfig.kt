package team.sw.everyonetayo.configuration;

import android.util.Log
import pusha.client.manager.ClientManager
import pusha.packet.Packet
import pusha.service.ClientPacketRecieveService
import pusha.service.default_order.Order
import pusha.socket.WrappedSocket
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

    constructor(){
        Log.d("inittest", "pusha init")
        if(ClientManager.instance==null) ClientManager.use();
        ClientPacketRecieveService.instance.addOrder("RESERVATION_NOTICE" ,Reservation())
        ClientPacketRecieveService.instance.addOrder("GET_OFF", GetOff())
    }

    /**
     *
     */

    inner class Reservation: Order {
        override fun excute(wrappedSocket: WrappedSocket, data: Any?) {
            val packet: Packet = data as Packet;
            val busStop:String = packet.data.toString().split("#")[0]
            val latitude:String = packet.data.toString().split("#")[1]
            val longitude:String = packet.data.toString().split("#")[2]

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

    inner class GetOff: Order {
        override fun excute(wrappedSocket: WrappedSocket, data: Any?) {
            val packet: Packet = data as Packet;
            val busStop:String = packet.data.toString();

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
