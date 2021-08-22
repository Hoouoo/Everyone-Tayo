package team.sw.everyonetayo.configuration;

import android.support.v7.app.AppCompatActivity
import pusha.client.manager.ClientManager
import pusha.packet.Packet
import pusha.service.ClientPacketRecieveService
import pusha.service.default_order.Order
import pusha.socket.WrappedSocket
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.orders.Reservation
import team.sw.everyonetayo.view.BusDriver

public class PushaConfig {
    companion object{
        val pushaConfig = PushaConfig()
    }

    constructor(){
        if(ClientManager.instance==null) ClientManager.use();
        ClientPacketRecieveService.instance.addOrder("RESERVATION_NOTICE" ,Reservation())
    }

    /**
     *
     */

    inner class Reservation: Order {
        override fun excute(wrappedSocket: WrappedSocket, data: Any?) {
            val packet: Packet = data as Packet;
            val busStop:String = packet.data.toString();

            val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")


            if(viewResult is Result.Success){
                //busDriver 뷰 가져오기
                val busDriverView:BusDriver  = viewResult.data as BusDriver
                //TODO 예약들어올 때 액션 추가
            }
        }
    }

    inner class Quit: Order {
        override fun excute(wrappedSocket: WrappedSocket, data: Any?) {
            val packet: Packet = data as Packet;
            val busStop:String = packet.data.toString();

            val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")


            if(viewResult is Result.Success){
                //busDriver 뷰 가져오기
                val busDriverView:BusDriver  = viewResult.data as BusDriver
                //TODO 하차할 때 액션 추가
            }
        }
    }
}
