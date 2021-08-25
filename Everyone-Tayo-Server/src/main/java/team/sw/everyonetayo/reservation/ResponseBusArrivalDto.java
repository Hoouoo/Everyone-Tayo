package team.sw.everyonetayo.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ResponseBusArrivalDto {

    private String state;     // 차량번호
    private String routeId;   // 정류소 Id

    @Builder
    public ResponseBusArrivalDto(String state, String routeId) {
        this.state = state;
        this.routeId = routeId;
    }
}
