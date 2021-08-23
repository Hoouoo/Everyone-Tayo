package team.sw.everyonetayo.api.busstop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopService {

    @Autowired
    BusStopRepository busStopRepository;

    public List<BusStop> getAllBusStop() {
        return busStopRepository.findAll();
    }
}
