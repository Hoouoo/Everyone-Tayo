package team.sw.everyonetayo.util.busroute;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteDto {

    private String routeTp; // 노선 유형
    private String routeId; //노선 id
//    private String startVehicleTime;  // 첫차 시간
//    private String endVehicleTime; // 막차 시간
//    private String startNodeNm; // 기점
//    private String endNodeNm; // 종점
    private String routeNo; // 노선 번호

    @Builder
    public RouteDto(String routeTp, String routeId, String routeNo) {
        this.routeTp = routeTp;
        this.routeId = routeId;
        this.routeNo = routeNo;
    }
}
