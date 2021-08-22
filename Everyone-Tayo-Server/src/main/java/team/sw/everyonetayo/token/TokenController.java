package team.sw.everyonetayo.token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.bus.Bus;
import team.sw.everyonetayo.bus.BusDto;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class TokenController {
    @Autowired
    TokenService userService;

    @Autowired
    TokenUtil tokenUtil;

    /**
     *
     * @author Sungho Park
     * @param resource 요청 받을 값
     * @return Token 반환
     */
    @PostMapping("/session")
    public ResponseEntity<TokenResponseDto> create(@RequestBody BusDto resource) throws URISyntaxException {


        Bus user = userService.authenticate(resource.getUsername(), resource.getPassword());
        String accessToken = tokenUtil.createToken(user.getId(), user.getUsername());
        String url ="/session";
        return ResponseEntity.created(new URI(url))
                .body(TokenResponseDto.builder().token(accessToken).build());
    }
}