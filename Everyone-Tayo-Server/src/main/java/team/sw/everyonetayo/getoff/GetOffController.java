package team.sw.everyonetayo.getoff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pusha.packet.Packet;
import pusha.packet.StringPacket;
import pusha.server.manager.ServerManager;
import team.sw.everyonetayo.reservation.table.Reservation;
import team.sw.everyonetayo.reservation.table.ReservationService;

@RestController
public class GetOffController {

    private ReservationService reservationService;

    @Autowired
    GetOffController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping(value = "off-app-bus")
    public ResponseEntity callGetOff(@RequestBody RequestGetOffDto requestGetOffDto){

        Reservation reservation = reservationService.searchReservationByToken(requestGetOffDto.token);
        String uuid = reservation.getUuid();

        //Push message by Pusha
        Packet packet = new StringPacket(
                "GET_OFF",
                "#",
                ""
        );
        ServerManager.instance.sendTarget(uuid, packet);

        ResponseGetOffDto responseGetOffDto = ResponseGetOffDto.builder()
                .state("OK").build();

        return ResponseEntity.ok(responseGetOffDto);
    }

}
