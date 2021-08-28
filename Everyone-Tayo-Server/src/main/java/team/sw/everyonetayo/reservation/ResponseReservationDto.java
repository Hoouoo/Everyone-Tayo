package team.sw.everyonetayo.reservation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseReservationDto {

    private String state;  // custom
    private String uuid;  // 차량 번호
    private String nodeId;  // 버스 정류소 Id
    private String busNumber;  // 버스 번호
    private LocalDateTime time;  // 저장된 현재 시간

}
