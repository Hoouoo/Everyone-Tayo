package team.sw.everyonetayo.configuration;

import pusha.client.manager.ClientManager
import pusha.service.ClientPacketRecieveService

public class PushaConfig {
    companion object{
        val pushaConfig = PushaConfig()
    }

    constructor(){
        if(ClientManager.instance==null) ClientManager.use();
    }
}
