package team.sw.everyonetayo.token;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestBusLoginDto {

    String id;
    String password;

    @Builder
    public RequestBusLoginDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
