package team.sw.everyonetayo.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResponseBusLocationDto {

    private String uuid;     // 차량번호
    private String nodeId;   // 정류소 Id
    private String nodeOrd;  // 정류소 순서
    private String routeNo;  // 버스 번호 (수정)
    private String routeId;  // 버스 Id (수정)

    @Builder
    public ResponseBusLocationDto(String uuid, String nodeId, String nodeOrd, String routeNo, String routeId) {
        this.uuid = uuid;
        this.nodeId = nodeId;
        this.nodeOrd = nodeOrd;
        this.routeNo = routeNo;
        this.routeId = routeId;
    }
}
