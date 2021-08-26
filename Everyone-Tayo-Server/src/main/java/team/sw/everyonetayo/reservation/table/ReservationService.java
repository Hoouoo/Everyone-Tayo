package team.sw.everyonetayo.reservation.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.exception.NoSuchReservationException;
import team.sw.everyonetayo.reservation.ReservationDto;

import java.util.Objects;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;


    // 조회
    public Reservation searchReservationByToken(String token){
        return reservationRepository.findByToken(token);
    }

    // 추가
    public void addReservation(ReservationDto reservationDto){
        Reservation targetReservation = reservationDto.ToEntity();
        reservationRepository.save(targetReservation);
    }

    // 삭제
    public void deleteReservation(String token) throws NoSuchReservationException {
        Reservation targetReservation = null;
        if(reservationRepository.existsByToken(token)){
            targetReservation = reservationRepository.findByToken(token);

            if(Objects.nonNull(targetReservation)) reservationRepository.delete(targetReservation);
        }else{
            throw new NoSuchReservationException("존재하지 않는 예약내역 입니다.");
        }


    }
}
