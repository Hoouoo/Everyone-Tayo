package team.sw.everyonetayo.util.busuuid;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusUuidDto {

    private Long id;
    private String uuid; // 노선 유형
    private String routenm; //노선 번호

    @Builder
    public BusUuidDto(Long id, String uuid, String routenm) {
        this.id = id;
        this.uuid = uuid;
        this.routenm = routenm;
    }
}
