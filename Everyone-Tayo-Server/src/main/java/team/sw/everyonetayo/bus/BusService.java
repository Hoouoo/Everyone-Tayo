package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.util.PasswordEncoder;

import java.util.Objects;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signup_check(Bus bus, String password){
        Bus targetBus = busRepository.findByUsername(bus.getUsername());
        if(Objects.isNull(targetBus) && passwordEncoder.matches(password, bus.getPassword())){
            busRepository.save(bus);
            return true;
        }
        return false;
    }
}
