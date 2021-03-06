package team.sw.everyonetayo.token;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.bus.Bus;
import team.sw.everyonetayo.bus.BusDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class TokenController {
    @Autowired
    TokenService userService;

    @Autowired
    TokenUtil tokenUtil;

    /**
     * @param resource 요청 받을 값
     * @return Token 반환
     * @author Sungho Park
     */

    @SneakyThrows
    @PostMapping("/login-app-bus") // 버스 계정 애플리케이션
    public ResponseEntity<ResponseBusLoginDto> create(@RequestBody RequestBusLoginDto resource) throws URISyntaxException {

        Bus user = userService.authenticate(resource.getId(), resource.getPassword());
        String accessToken = tokenUtil.createToken(user.getId(), user.getUsername());
        String url = "/login-app-bus";

        ResponseBusLoginDto responseBusLoginDto = new ResponseBusLoginDto.ResponseBusLoginDtoBuilder()
                .token(accessToken)
                .busNumber(user.getBusNumber())
                .uuid(user.getUuid())
                .build();

        return ResponseEntity.ok(responseBusLoginDto);

    }

    @PostMapping("/start-app-user")  // 일반 사용자 계정 애플리케이션
    public ResponseEntity<TokenResponseDto> createGuestToken() throws URISyntaxException {

        AtomicLong atomicLong = new AtomicLong();
        String accessToken = tokenUtil.createToken(atomicLong.get(), "Guest");
        String url = "/start-app-user";

        return ResponseEntity.created(new URI(url)).body(TokenResponseDto.builder().token(accessToken).build());
    }
}