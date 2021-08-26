package team.sw.everyonetayo.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.sw.everyonetayo.reservation.table.Reservation;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDto {

    private String token;     // 토큰
    private String uuid;      // 차량 번호
    private LocalDateTime timeStamp; // 시간
    private String state;     // 상태(버스 존재 x: nope, 버스 존재 o: 분 단위 추출)

    @Builder
    public ReservationDto(String token, String uuid, String state) {
        this.token = token;
        this.uuid = uuid;
        this.state = state;
    }

    public Reservation ToEntity(){
        return Reservation.builder()
                .uuid(this.uuid)
                .state(this.state)
                .timeStamp(LocalDateTime.now())
                .token(this.token)
                .build();
    }
}
