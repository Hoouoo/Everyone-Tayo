package team.sw.everyonetayo.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j  // log를 쓰기위해 사용
@Controller
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
