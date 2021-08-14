package team.sw.everyonetayo.zebal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.bus.Bus;
import team.sw.everyonetayo.bus.BusDto;
import team.sw.everyonetayo.bus.BusService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {
    @Autowired
    JwtService userService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/session")
    public ResponseEntity<BusDto> create(@RequestBody BusDto resource) throws URISyntaxException {


        Bus user = userService.authenticate(resource.getUsername(), resource.getPassword());
        String accessToken = jwtUtil.createToken(user.getId(), user.getUsername());
        String url ="/session";
        return ResponseEntity.created(new URI(url)).body(BusDto
                .builder()
                .token(accessToken)
                .build());
    }
}