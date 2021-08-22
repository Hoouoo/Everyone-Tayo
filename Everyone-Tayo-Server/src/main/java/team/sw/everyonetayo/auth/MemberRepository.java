package team.sw.everyonetayo.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.sw.everyonetayo.util.busstop.BusStop;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

     Member findByUsername(String username);
}
