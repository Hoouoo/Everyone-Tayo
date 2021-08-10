package team.sw.everyonetayo.util.busstop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusStopService {

    @Autowired
    BusStopRepository busStopRepository;

    public List<BusStop> getAllBusStop() {
        System.out.println("DB 안사라진다헤헤헤헤헤");
        return busStopRepository.findAll();
    }
}
