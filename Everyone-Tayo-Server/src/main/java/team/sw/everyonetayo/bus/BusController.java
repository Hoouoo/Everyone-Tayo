package team.sw.everyonetayo.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.sw.everyonetayo.api.busstop.BusStopService;
import team.sw.everyonetayo.auth.dto.LoginDto;
import team.sw.everyonetayo.util.CustomPasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class BusController {

    @Autowired
    BusService busService;
    @Autowired
    CustomPasswordEncoder customPasswordEncoder;
    @Autowired
    BusStopService busStopService;

    @GetMapping("/")
    public String main(HttpSession session){
        if(Objects.nonNull(session.getAttribute("member"))){
            return "redirect:table";
        }
        return "redirect:login";
    }

    @GetMapping("/signup")
    public String signUp(Model model, HttpSession session) {
        if (Objects.nonNull(session.getAttribute("member"))) {
            model.addAttribute("busDto", new BusDto());
            return "signup";
        }

        return "redirect:login";
    }

    @PostMapping("/signup")
    public String signUpForm(BusDto busDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if (result.hasErrors()) {
            String errormessage = "It is not verfied";
            redirectAttributes.addFlashAttribute("errors", errormessage);
            return "redirect:signup";
        } else if (busService.isExitsUsername(busDto.getUsername())) {
            String errormessage = "This ID already exists.";
            redirectAttributes.addFlashAttribute("errors", errormessage);
            return "redirect:signup";
        } else {

            System.out.println("busDto.getUsername() = " + busDto.getUsername());
            busService.createUser(busDto);
        }
        return "redirect:table";
    }

    @GetMapping("/delete_bus_driver")
    public ModelAndView getDeleteBus(HttpSession session) {
        ModelAndView mv = new ModelAndView();

        if (Objects.nonNull(session.getAttribute("member"))) {
            mv.setViewName("delete_bus_driver");
            mv.addObject("bus", busService.getAllBus());
            return mv;
        } else {
            mv.setViewName("redirect:/login");
            return mv;
        }
    }

    @GetMapping("/delete_bus_driver/{uuid}")
    public String delete(@PathVariable("uuid") String uuid, Model model, HttpSession session) {
        if (Objects.isNull(session.getAttribute("member"))) {
            busService.deleteUser(uuid);
            return "redirect:/delete_bus_driver";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/read_bus_driver")
    public ModelAndView getReadBus(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (Objects.nonNull(session.getAttribute("member"))) {
            mv.setViewName("read_bus_driver");
            mv.addObject("bus", busService.getAllBus());
            return mv;
        } else {
            mv.setViewName("redirect:/login");
            return mv;
        }
    }

    @GetMapping("/bus_status")
    public ModelAndView getBusStop(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        if (Objects.nonNull(session.getAttribute("member"))) {
            mv.setViewName("bus_status");
            mv.addObject("bus_stop", busStopService.getAllBusStop());
            return mv;
        } else {
            mv.setViewName("redirect:/login");
            return mv;
        }
    }

    @PostMapping("/logout.do")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        ModelAndView mv = new ModelAndView("redirect:login");
        return mv;
    }

    @GetMapping("team_info")
    public String teamInfo(){
        return "/team_info";
    }
}

