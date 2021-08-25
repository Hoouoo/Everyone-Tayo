package team.sw.everyonetayo.reservation.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByToken(String token);

    boolean existsByToken(String token);
}
