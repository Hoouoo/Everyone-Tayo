package team.sw.everyonetayo.api.busuuid;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusUuidRepository extends JpaRepository<BusUuid, Long>{

    List<BusUuid> findAll();

    boolean existsByUuid(String uuid);
}
