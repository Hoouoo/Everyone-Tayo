package team.sw.everyonetayo.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
//
//    public Member login_check(String username, String password){
//        Member member = memberRepository.findByUsername(username);
//        if(Objects.nonNull(member))
//    }
}
