package team.sw.everyonetayo.orders

import pusha.packet.Packet
import pusha.service.default_order.Order
import pusha.socket.WrappedSocket

class Reservation : Order {
    override fun excute(wrappedSocket: WrappedSocket?, data: Any?) {
        val packet : Packet = data as Packet;
        //TODO:Call adding reservation to list view to controller
    }

}