package team.sw.everyonetayo.reservation.table;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String uuid;
    private LocalDateTime timeStamp;
    private String state;

    @Builder
    public Reservation(String token, String uuid, LocalDateTime timeStamp, String state) {
        this.token = token;
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.state = state;
    }
}
