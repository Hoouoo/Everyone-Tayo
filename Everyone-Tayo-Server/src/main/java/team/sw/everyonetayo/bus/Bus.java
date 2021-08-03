package team.sw.everyonetayo.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // db의 primary key : 식별자

    private UUID uuid; // uuid : HTTP 통신 고유 식별자
    private String username; // 계정 id
    private String password; // 계정 password
    private String busNumber; // 버스 번호
    private String Token;   // 버스의 로그인 Token 값

}
