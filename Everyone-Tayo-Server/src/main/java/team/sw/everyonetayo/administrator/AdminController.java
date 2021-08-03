package team.sw.everyonetayo.administrator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@Controller
public class AdminController {

    @GetMapping("/table")
    public String table(HttpSession session) {
        log.info("table url 접속");
        session.getAttribute("member");
        System.out.println(session.getAttribute("member"));
        if(Objects.isNull(session.getAttribute("member"))){
            return "redirect:/login";
        }
        return "/table";
    }

    @GetMapping("/bus_status")
    public String busStatus() {

        return "bus_status";
    }
}
