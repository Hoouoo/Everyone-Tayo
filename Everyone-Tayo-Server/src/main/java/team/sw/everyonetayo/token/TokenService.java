package team.sw.everyonetayo.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.bus.Bus;
import team.sw.everyonetayo.exception.PasswordWrongException;
import team.sw.everyonetayo.util.CustomPasswordEncoder;


@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    /**
     * 요청할 버스 계정에 대한 검증
     * @author Sungho Park
     * @param username 요청할 계정 이름
     * @param password 요청할 계정 비밀번호
     * @return 요청하는 Bus 계정 정보
     */
    public Bus authenticate(String username, String password){

        Bus user = tokenRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(username));

        // ***** 패스워드값 확인 부분 ****
        if(!passwordEncoder.matches(password, user.getPassword())){
            System.out.println("password = " + password);
            throw new PasswordWrongException("패스워드 검증 오류");
        }
        return user;
    }

}
