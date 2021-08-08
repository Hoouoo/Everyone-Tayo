package team.sw.everyonetayo.util.busstop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gpsLati; // GPS WGS84 위도 자표
    private String gpsLong; // GPS WGS84 경도 좌표
    private String nodeid; // 정류소 ID
    private String nodenm; // 정류소 명
    private String nodeno; // 정류소 번호
    private String citycode; // 도시 번호
}
