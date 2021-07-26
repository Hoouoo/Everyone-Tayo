package team.sw.everyonetayo.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.sw.everyonetayo.util.PasswordEncoder;

@Slf4j  // log를 쓰기위해 사용
@Controller
public class MemberController {

    @GetMapping("/login")
    public String login() {

        System.out.println("sha" + PasswordEncoder.encryptSHA256("test"));
        return "login";
    }

    @GetMapping("/table")
    public String table() {

        System.out.println("sha" + PasswordEncoder.encryptSHA256("test"));
        return "table";
    }
}
