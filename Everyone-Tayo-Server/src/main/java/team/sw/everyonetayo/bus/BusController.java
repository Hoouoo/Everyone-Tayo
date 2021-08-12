package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.sw.everyonetayo.util.PasswordEncoder;

@Controller
public class BusController {

    @Autowired
    BusService busService;

    @GetMapping("/auth/signup")
    public String signUp(Model model){
        model.addAttribute("busDto", new BusDto());
        return "/signup";
    }

    @PostMapping("/auth/signup")
    public String signUpForm(@Validated @ModelAttribute BusDto busDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            String errormessage = "It is not verfied";
            redirectAttributes.addFlashAttribute("errors", errormessage);
        }
        Bus bus =
        if(busService.signup_check( busDto.getPassword()){

        }
    }


}

