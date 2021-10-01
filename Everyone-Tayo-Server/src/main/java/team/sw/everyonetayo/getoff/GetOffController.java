package team.sw.everyonetayo.getoff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pusha2.container.ServerContainer;
import pusha2.domain.SockDto;
import pusha2.server.ServerManager;
import team.sw.everyonetayo.reservation.table.Reservation;
import team.sw.everyonetayo.reservation.table.ReservationService;

import java.util.List;

@RestController
public class GetOffController {

    private ReservationService reservationService;

    @Autowired
    GetOffController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping(value = "/off-app-bus")
    public ResponseEntity callGetOff(@RequestBody RequestGetOffDto requestGetOffDto){

        List<Reservation> reservationList = reservationService.searchReservationByToken(requestGetOffDto.token);
        Reservation reservation = reservationList.get(reservationList.size() - 1);
        String uuid = reservation.getUuid();

        //Push message by Pusha2
        SockDto sockDto = new SockDto("Server",
                "GET_OFF",
                "#",
                "",
                null);
        ServerManager serverManager = ServerContainer.Companion.serverManager();
        serverManager.sendData(uuid, sockDto);

        ResponseGetOffDto responseGetOffDto = new ResponseGetOffDto.ResponseGetOffDtoBuilder()
                .state("OK").build();

        return ResponseEntity.ok(responseGetOffDto);
    }

}
