package team.sw.everyonetayo.util.busuuid;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusUuidRepository extends JpaRepository<BusUuid, Long>{

    boolean existsByUuid(String uuid);
}
