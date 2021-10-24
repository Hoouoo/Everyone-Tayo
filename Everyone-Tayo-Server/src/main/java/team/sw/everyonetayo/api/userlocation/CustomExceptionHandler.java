package team.sw.everyonetayo.api.userlocation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.sw.everyonetayo.exception.NoSuchBusStopException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(NoSuchBusStopException.class)
    public ResponseEntity<UserLocationResponseDto> noSuchBusStop(RuntimeException e){
        log.debug("존재하지 않는 버스 정류소 정보");

        UserLocationResponseDto userLocationResponseDto = new UserLocationResponseDto.UserLocationResponseDtoBuilder()
                .latitude("nope").longitude("nope").name("nope").build();

        return ResponseEntity.ok().body(userLocationResponseDto);
    }
}
