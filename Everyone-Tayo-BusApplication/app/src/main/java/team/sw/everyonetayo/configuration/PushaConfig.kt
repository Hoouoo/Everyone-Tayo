package team.sw.everyonetayo.configuration;

import pusha.client.manager.ClientManager
import pusha.packet.Packet
import pusha.service.ClientPacketRecieveService
import pusha.service.default_order.Order
import pusha.socket.WrappedSocket

public class PushaConfig {
    companion object{
        val pushaConfig = PushaConfig()
    }

    constructor(){
        if(ClientManager.instance==null) ClientManager.use();
        ClientPacketRecieveService.instance.addOrder("RESERVATION_NOTICE" ,ReceptionNotice())
    }

    /**
     *
     */

    inner class ReceptionNotice: Order {
        override fun excute(wrappedSocket: WrappedSocket, data: Any?) {
            val packet: Packet = data as Packet;
            val busStop:String = packet.data.toString();

            //TODO Implement yet
        }
    }
}
