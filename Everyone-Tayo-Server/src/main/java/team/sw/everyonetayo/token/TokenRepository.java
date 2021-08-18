package team.sw.everyonetayo.token;

import org.springframework.data.jpa.repository.JpaRepository;
import team.sw.everyonetayo.bus.Bus;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByUsername(String username);
}
