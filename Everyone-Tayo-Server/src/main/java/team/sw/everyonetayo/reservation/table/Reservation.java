package team.sw.everyonetayo.reservation.table;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation")
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
