package team.sw.everyonetayo.util.busstop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusStopDto {

    private String gpsLati; // GPS WGS84 위도 자표
    private String gpsLong; // GPS WGS84 경도 좌표
    private String nodeid; // 정류소 ID
    private String nodenm; // 정류소 명
    private String nodeno; // 정류소 번호 (Option)

    @Builder
    public BusStopDto(String gpsLati, String gpsLong, String nodeid, String nodenm, String nodeno) {
        this.gpsLati = gpsLati;
        this.gpsLong = gpsLong;
        this.nodeid = nodeid;
        this.nodenm = nodenm;
        this.nodeno = nodeno;
    }

    @Builder

    public BusStopDto(String gpsLati, String gpsLong, String nodeid, String nodenm) {
        this.gpsLati = gpsLati;
        this.gpsLong = gpsLong;
        this.nodeid = nodeid;
        this.nodenm = nodenm;
    }
}
