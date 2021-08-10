package team.sw.everyonetayo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.util.PasswordEncoder;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member getMemberByUsername(String username){
        return memberRepository.findByUsername(username);
    }

    public Member login_check(String username, String password){
        Member member = memberRepository.findByUsername(username);
        if(Objects.nonNull(member) && passwordEncoder.matches(password, member.getPassword())){
            return member;
        }else{
            return null;
        }
    }
}
