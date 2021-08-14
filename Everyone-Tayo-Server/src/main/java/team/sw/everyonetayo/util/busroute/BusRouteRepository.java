package team.sw.everyonetayo.util.busroute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByCityCode(String cityCode);
}
