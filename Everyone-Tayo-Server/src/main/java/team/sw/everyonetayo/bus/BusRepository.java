package team.sw.everyonetayo.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.sw.everyonetayo.auth.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByUsername(String username);

    List<Bus> findAll();
}
