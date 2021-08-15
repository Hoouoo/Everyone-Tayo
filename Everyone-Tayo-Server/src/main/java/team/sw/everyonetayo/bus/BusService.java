package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.exception.AlreadyExistsBusAccountException;

import java.util.List;
import java.util.Objects;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public boolean diffUser(String username){
        Bus bus = busRepository.findByUsername(username);
        return !Objects.nonNull(bus.getUsername());
    }

    public Long createUser(BusDto busDto){
        Bus bus = busDto.toEntity();
        if(diffUser(bus.getUsername())) {
            return busRepository.save(bus).getId();
        }else{
            throw new AlreadyExistsBusAccountException("이미 존재하는 계정 정보입니다.");
        }
    }

    public void deleteUser(String username){
        Bus bus = busRepository.findByUsername(username);
        busRepository.delete(bus);
    }

    public List<Bus> getAllBus() {
        return busRepository.findAll();
    }
}
