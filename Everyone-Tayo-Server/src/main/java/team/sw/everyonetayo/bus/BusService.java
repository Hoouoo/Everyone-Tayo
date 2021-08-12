package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.sw.everyonetayo.util.PasswordEncoder;

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
}
