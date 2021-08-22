package team.sw.everyonetayo.util.busroute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByCityCode(String cityCode);

    Optional<Route> findByRouteNo(String routeNo);  // 버스 번호 추출

    List<Route> findAllByRouteNo(String routeNo);
}
