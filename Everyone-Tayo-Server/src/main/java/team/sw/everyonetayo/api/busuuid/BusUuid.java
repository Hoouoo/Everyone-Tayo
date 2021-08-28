package team.sw.everyonetayo.api.busuuid;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter // 나중에 지우기
public class BusUuid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid; // 노선 유형
    private String routenm; //노선 번호
    private String cityCode; // 도시 코드
}
