package team.sw.everyonetayo.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseNearBusDto {

    private String nodeId;
    private String nodeNm;
    private String cityCode;

    @Builder
    public ResponseNearBusDto(String nodeId, String nodeNm, String cityCode) {
        this.nodeId = nodeId;
        this.nodeNm = nodeNm;
        this.cityCode = cityCode;
    }
}
