package team.sw.everyonetayo.token;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseBusLoginDto {

    String token;
    String uuid;
    String busNumber;

    @Builder
    public ResponseBusLoginDto(String token, String uuid, String busNumber) {
        this.token = token;
        this.uuid = uuid;
        this.busNumber = busNumber;
    }
}
