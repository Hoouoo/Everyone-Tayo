package team.sw.everyonetayo.api.busuuid;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusUuidRepository extends JpaRepository<BusUuid, Long>{

    boolean existsByUuid(String uuid);
}
