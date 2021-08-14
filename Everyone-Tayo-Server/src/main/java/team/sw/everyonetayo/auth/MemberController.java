package team.sw.everyonetayo.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.sw.everyonetayo.auth.dto.LoginDto;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j  // log를 쓰기위해 사용
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginForm(@Validated @ModelAttribute LoginDto loginDto,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {
        if (result.hasErrors()) {
            String errormessage = "It is not verified.";
            redirectAttributes.addFlashAttribute("errors", errormessage);
            System.out.println("loginDto = " + loginDto.getUsername());
            System.out.println("error");
            return "redirect:/login";
        }
        Member member = memberService.login_check(loginDto.getUsername(), loginDto.getPassword());
        if (Objects.nonNull(member)) {
            session.setAttribute("member", member);
            System.out.println("member = " + member);
            return "redirect:/table";
        } else {
            String errormessage = "No Member Information";
            redirectAttributes.addFlashAttribute("errors", errormessage);
            System.out.println("No member Information");
            return "redirect:/login";
        }
    }



}
