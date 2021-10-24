package team.sw.everyonetayo.api.userlocation;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLocationRequestDto {

    String latitude;
    String longitude;

    @Builder
    public UserLocationRequestDto(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
