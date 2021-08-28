package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.api.busuuid.BusUuid;
import team.sw.everyonetayo.api.busuuid.BusUuidDto;
import team.sw.everyonetayo.api.busuuid.BusUuidRepository;
import team.sw.everyonetayo.bus.Bus;
import team.sw.everyonetayo.bus.BusDto;
import team.sw.everyonetayo.bus.BusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class BusAccountController {

    @Autowired
    BusUuidRepository busUuidRepository;

    @Autowired
    BusRepository busRepository;

    @GetMapping("bus_account_auto_save")
    public ResponseEntity<List<BusDto>> saveBusAccount(){
        List<BusUuid> busList = busUuidRepository.findAll();
        List<BusDto> targetList = new ArrayList<>();
        for (BusUuid busUuid : busList) {

            if (!busRepository.existsByUuid(busUuid.getUuid())) {

                BusDto busAccountDto = new BusDto.BusDtoBuilder()
                        .busNumber(busUuid.getRoutenm())
                        .password(String.valueOf(busUuid.getId()))
                        .uuid(busUuid.getUuid())
                        .username(String.valueOf(busUuid.getId()))
                        .build();

                Bus targetBusAccount = busAccountDto.toEntity();
                busRepository.save(targetBusAccount);
                targetList.add(busAccountDto);

            }
        }
        return ResponseEntity.ok(targetList);
    }
}
