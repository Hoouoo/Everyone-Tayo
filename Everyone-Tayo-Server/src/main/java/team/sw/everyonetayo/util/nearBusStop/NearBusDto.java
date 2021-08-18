package team.sw.everyonetayo.util.nearBusStop;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NearBusDto {

    private String nodeId;
    private String nodeNm;
    private String cityCode;

    @Builder
    public NearBusDto(String nodeId, String nodeNm, String cityCode) {
        this.nodeId = nodeId;
        this.nodeNm = nodeNm;
        this.cityCode = cityCode;
    }
}
