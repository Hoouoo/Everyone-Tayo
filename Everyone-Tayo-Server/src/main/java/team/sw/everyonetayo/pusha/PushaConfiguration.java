package team.sw.everyonetayo.pusha;

import org.apache.catalina.Server;
import org.springframework.stereotype.Component;
import pusha.server.manager.ServerManager;
import pusha.server.repository.MemorySocketRepository;

@Component
public class PushaConfiguration {

    int port = 9090;

    PushaConfiguration(){
        ServerManager.use();
        ServerManager.instance.bound(port);
        ServerManager.instance.listen();
        ServerManager.instance.setRepository(new MemorySocketRepository());
        ServerManager.instance.process();
    }
}
