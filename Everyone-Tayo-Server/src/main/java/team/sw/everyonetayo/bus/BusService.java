package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.sw.everyonetayo.auth.Member;
import team.sw.everyonetayo.util.PasswordEncoder;

import java.util.List;
import java.util.Objects;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Transactional
    public Long createUser(BusDto busDto){
        Bus bus = busDto.toEntity();
        return busRepository.save(bus).getId();
    }

    public void deleteUser(String username){
        Bus bus = busRepository.findByUsername(username);
        busRepository.delete(bus);
    }

    public List<Bus> getAllBus() {
        return busRepository.findAll();
    }
}
