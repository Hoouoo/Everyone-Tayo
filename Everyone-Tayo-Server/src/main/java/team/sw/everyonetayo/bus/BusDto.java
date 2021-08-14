package team.sw.everyonetayo.bus;

import lombok.*;
import team.sw.everyonetayo.util.PasswordEncoder;

import java.util.UUID;

@NoArgsConstructor
@Data
public class BusDto {

    private String uuid; // uuid : HTTP 통신 고유 식별자 = 버스 번호판
    private String username; // 계정 id
    private String password; // 계정 password
    private String busNumber; // 버스 번호
    private String token;   // 버스의 로그인 Token 값

    @Builder
    public BusDto(String uuid, String username, String password, String busNumber, String token) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.busNumber = busNumber;
        this.token = token;
    }

    public Bus toEntity() {
        return Bus.builder()
                .uuid(uuid)
                .username(username)
                .password(new PasswordEncoder().encryptSHA256(password))
                .busNumber(busNumber)
                .token(token).build();
    }
}

