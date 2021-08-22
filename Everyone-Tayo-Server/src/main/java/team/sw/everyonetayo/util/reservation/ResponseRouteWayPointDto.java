package team.sw.everyonetayo.util.reservation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseRouteWayPointDto {

    private String nodeId; // 정류소 id
    private String nodeOrd; // 정류소 순번
}
