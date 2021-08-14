package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
