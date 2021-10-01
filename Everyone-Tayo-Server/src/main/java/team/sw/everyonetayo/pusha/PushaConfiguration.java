package team.sw.everyonetayo.pusha;

import org.apache.catalina.Server;
import org.springframework.stereotype.Component;
import pusha2.container.ServerContainer;
import pusha2.server.ServerManager;


@Component
public class PushaConfiguration {

    int port = 9090;

    PushaConfiguration(){
        ServerManager serverManager = ServerContainer.Companion.serverManager();
        ServerManager.Companion.setPort(port);
        serverManager.bind();
        serverManager.accept();
        serverManager.processing();
    }
}
