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

    public BusDto login_check(String username, String password){
        Bus bus = busRepository.findByUsername(username);
        if(Objects.nonNull(bus) && passwordEncoder.matches(password, bus.getPassword())){
            return new BusDto(bus.getUuid(), bus.getToken(), bus.getBusNumber());
        }else{
            return null;
        }
    }


}
