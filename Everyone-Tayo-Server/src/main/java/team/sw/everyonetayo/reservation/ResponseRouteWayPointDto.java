package team.sw.everyonetayo.reservation;

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
