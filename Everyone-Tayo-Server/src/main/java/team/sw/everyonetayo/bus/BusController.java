package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.sw.everyonetayo.util.CustomPasswordEncoder;

@Controller
public class BusController {

    @Autowired
    BusService busService;
    @Autowired
    CustomPasswordEncoder customPasswordEncoder;

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
            return "redirect:signup";
        }
        else if(busService.isExitsUsername(busDto.getUsername())){
            String errormessage = "This ID already exists.";
            redirectAttributes.addFlashAttribute("errors", errormessage);
            return "redirect:signup";
        }
        else {

            System.out.println("busDto.getUsername() = " + busDto.getUsername());
            busService.createUser(busDto);
        }
        return "redirect:login";
    }

    @GetMapping("/delete_bus_driver")
    public ModelAndView getDeleteBus() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("delete_bus_driver");
        mv.addObject("bus", busService.getAllBus());
        return mv;
    }

    @GetMapping("/read_bus_driver")
    public ModelAndView getReadBus() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("read_bus_driver");
        mv.addObject("bus", busService.getAllBus());
        return mv;
    }
}

