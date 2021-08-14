package team.sw.everyonetayo.zebal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.bus.Bus;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findByUsername(String username);

}
