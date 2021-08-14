//package team.sw.everyonetayo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import team.sw.everyonetayo.bus.Bus;
//import team.sw.everyonetayo.bus.BusRepository;
//import java.util.ArrayList;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private BusRepository busRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Bus user = busRepository.findByUsername(username);
//        System.out.println("busRepository.findByUsername(username) = " + busRepository.findByUsername("{noop}"+username));
//        System.out.println("busRepository.findByUsername(username) = " + busRepository.findByUsername("11111" +
//                ""));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
//    }
//}