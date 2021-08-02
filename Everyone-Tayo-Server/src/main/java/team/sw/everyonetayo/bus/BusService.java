package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;


    /**
     *
     * 버스 계정 추가
     * @param bus : Bus Entity 클래스
     * @return Bus 계정을 생성한 후 Id 값을 저장
     */
    public Long createBusAccount(Bus bus){
        return busRepository.save(bus).getId();
    }

    // 버스 계정 삭제
    public void deleteBusAccount(String username){
        Bus bus = busRepository.findByUsername(username);
        busRepository.delete(bus);
    }


}
