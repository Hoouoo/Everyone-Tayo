package team.sw.everyonetayo.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.sw.everyonetayo.exception.NoSuchBusArriverStatusExecption;
import team.sw.everyonetayo.exception.NoSuchItemsException;
import team.sw.everyonetayo.exception.PasswordWrongException;
import team.sw.everyonetayo.token.ResponseBusLoginDto;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(NoSuchItemsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseReservationDto> noSuchItemsExceptionHandler(RuntimeException e){
        log.debug("존재하지 않는 버스 정보", e);
        System.out.println("존재하지 않는 버스입니다.");
        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
        return ResponseEntity.ok().body(responseReservationDto);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ResponseReservationDto> exceptionHandler(Exception e){
//        log.debug("존재하지 않는 버스 정보", e);
//        System.out.println("Exception?");
//        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
//                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
//        return ResponseEntity.ok().body(responseReservationDto);
//    }

    @ExceptionHandler(NoSuchBusArriverStatusExecption.class)
    public ResponseEntity<ResponseReservationDto> busArriverStatusExceptionHandler(NoSuchBusArriverStatusExecption e){
        log.debug("존재하지 않는 버스 정보", e);
        System.out.println("Exception?");
        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
        return ResponseEntity.ok().body(responseReservationDto);
    }

    @ExceptionHandler(PasswordWrongException.class)
    public ResponseEntity<ResponseBusLoginDto> passwordWrongExceptionHandler(PasswordWrongException e){
        log.debug("패스워드 검증 오류" ,e);
        System.out.println("패스워드 검증 오류");
        ResponseBusLoginDto responseBusLoginDto = new ResponseBusLoginDto("nope", "nope", "nope");
        return ResponseEntity.ok().body(responseBusLoginDto);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseBusLoginDto> UsernameNotFoundExceptionHandler(UsernameNotFoundException e){
        log.debug("아이디 검증 오류" ,e);
        System.out.println("아이디 검증 오류");
        ResponseBusLoginDto responseBusLoginDto = new ResponseBusLoginDto("nope", "nope", "nope");
        return ResponseEntity.ok().body(responseBusLoginDto);
    }
//    @ExceptionHandler(ClassCastException.class)
//    public ResponseEntity<ResponseReservationDto> classCastExceptionHandler(Exception e){
//        log.debug("존재하지 않는 버스 정보", e);
//        System.out.println("오잉?" +
//                "");
//        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
//                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
//        return ResponseEntity.badRequest().body(responseReservationDto);
//    }
}
