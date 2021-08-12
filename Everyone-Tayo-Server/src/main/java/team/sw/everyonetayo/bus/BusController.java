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
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("busDto", new BusDto());
        return "/signup";
    }

    @PostMapping("/signup")
    public String signUpForm(BusDto busDto, Model model,
                             BindingResult result,
                             RedirectAttributes redirectAttributes){
        System.out.println("busDto.getClass() = " + busDto.getUsername());
        if(result.hasErrors()){
            String errormessage = "It is not verfied";
            redirectAttributes.addFlashAttribute("errors", errormessage);

        }else {
            busService.createUser(busDto);
        }
//        Bus bus = Bus.builder()
//                .uuid(busDto.getUuid())
//                .username(busDto.getUsername())
//                .password(passwordEncoder.encryptSHA256(busDto.getPassword()))
//                .busNumber(busDto.getBusNumber())
//                .token("1").build();
        return "redirect:login";
    }


}

