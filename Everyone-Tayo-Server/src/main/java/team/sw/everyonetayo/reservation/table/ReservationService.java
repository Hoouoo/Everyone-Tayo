package team.sw.everyonetayo.reservation.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.sw.everyonetayo.api.busstop.BusStop;
import team.sw.everyonetayo.api.busstop.BusStopRepository;
import team.sw.everyonetayo.exception.NoSuchReservationException;
import team.sw.everyonetayo.reservation.ReservationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    // 조회
    public List<Reservation> searchReservationByToken(String token) {
        return reservationRepository.findByToken(token);
    }

    // 추가
    public void addReservation(ReservationDto reservationDto) {
        Reservation targetReservation = reservationDto.ToEntity();
        reservationRepository.save(targetReservation);
    }

    @Transactional
    public List<ReservationDto> getAllReservation() {
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            ReservationDto reservationDto = ReservationDto.builder()
                    .uuid(reservation.getUuid())
                    .state(reservation.getState())
                    .timeStamp(reservation.getTimeStamp())
                    .build();
            reservationDtoList.add(reservationDto);
        }
        System.out.println("reservationDtoList = " + reservationDtoList);
        return reservationDtoList;
    }

    // 삭제
    public void deleteReservation(String token) throws NoSuchReservationException {
        List<Reservation> targetReservation = new ArrayList<>();
        if (reservationRepository.existsByToken(token)) {
            targetReservation = reservationRepository.findByToken(token);

            if (Objects.nonNull(targetReservation)) reservationRepository.delete(
                    targetReservation.get(targetReservation.size() - 1)
            );
        } else {
            throw new NoSuchReservationException("존재하지 않는 예약내역 입니다.");
        }


    }
}
