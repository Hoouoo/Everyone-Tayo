package team.sw.everyonetayo.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByUuid(String uuid);
    List<Bus> findAll();

    boolean existsByUsername(String username);

    boolean existsByUuid(String Uuid);
}
