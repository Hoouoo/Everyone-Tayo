package team.sw.everyonetayo.api.userlocation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLocationResponseDto {

    String name;  // 버스 정류소 이름
    String latitude; // 버스 정류소 위도
    String longitude; // 버스 정류소 경도

    @Builder
    public UserLocationResponseDto(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
