package team.sw.everyonetayo.zebal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.sw.everyonetayo.bus.Bus;

@Service
public class JwtService {

    private JwtRepository jwtRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public JwtService(JwtRepository jwtRepository, PasswordEncoder passwordEncoder) {
        this.jwtRepository = jwtRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Bus authenticate(String username, String password) {

        Bus user = jwtRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(username));

        // ***** 패스워드값 확인 부분 ****
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException();
        }
        return user;
    }
}
