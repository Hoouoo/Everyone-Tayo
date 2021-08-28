package team.sw.everyonetayo.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.sw.everyonetayo.exception.NoSuchItemsException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(NoSuchItemsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseReservationDto> noSuchItemsExceptionHandler(RuntimeException e){
        log.debug("존재하지 않는 버스 정보", e);
        System.out.println("존재 안함쓰");
        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
        return ResponseEntity.badRequest().body(responseReservationDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseReservationDto> exceptionHandler(Exception e){
        log.debug("존재하지 않는 버스 정보", e);
        System.out.println("Exception?" +
                "");
        ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
                .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
        return ResponseEntity.badRequest().body(responseReservationDto);
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
