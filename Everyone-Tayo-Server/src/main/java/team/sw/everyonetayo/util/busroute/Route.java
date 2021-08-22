package team.sw.everyonetayo.util.busroute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeTp; // 노선 유형
    private String routeId; //노선 id
//    private String startVehicleTime;  // 첫차 시간
//    private String endVehicleTime; // 막차 시간
//    private String startNodeNm; // 기점
//    private String endNodeNm; // 종점
    private String routeNo; // 노선 번호
    private String cityCode; // 도시 코드

}
