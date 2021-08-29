package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public boolean isExitsUsername(String username){
        return busRepository.existsByUsername(username);
    }

    public void createUser(BusDto busDto) {
        Bus bus = busDto.toEntity();
        busRepository.save(bus);
    }

    public void deleteUser(String uuid) {
        Bus bus = busRepository.findByUuid(uuid);
        busRepository.delete(bus);
    }

    public List<Bus> getAllBus() {
        return busRepository.findAll();
    }
}
