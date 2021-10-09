package team.sw.everyonetayo.api.userlocation;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.exception.NoSuchItemsException;
import team.sw.everyonetayo.reservation.ResponseNearBusDto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@RestController
public class CheckUserLocationController {
    private UserLocationResponseDto userLocationResponseDto;

    @GetMapping("user-location")

    @SneakyThrows
    public ResponseEntity<UserLocationResponseDto> checkUserLocation(@RequestBody UserLocationRequestDto userLocationRequestDto) {
        StringBuffer result = new StringBuffer();

        boolean next = true;
        String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCrdntPrxmtSttnList?"
                + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                + "&gpsLati=" + userLocationRequestDto.getLatitude()
                + "&gpsLong=" + userLocationRequestDto.getLongitude();


        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
        String returnLine;
        while ((returnLine = bufferedReader.readLine()) != null) {
            result.append(returnLine);
        }
        JSONObject jsonObject = XML.toJSONObject(result.toString());
        result.setLength(0);
        JSONObject responseObject = (JSONObject) jsonObject.get("response");
        JSONObject bodyObject = (JSONObject) responseObject.get("body");
        JSONObject itemObject = (JSONObject) bodyObject.get("items");

        JSONArray item = (JSONArray) itemObject.get("item");
        JSONObject targetItem = (JSONObject) item.get(0);
        userLocationResponseDto = new UserLocationResponseDto.UserLocationResponseDtoBuilder()
                .name(String.valueOf(targetItem.get("nodenm")))  // 버스 정류소 이름
                .latitude(String.valueOf(targetItem.get("gpslati")))  // 버스 정류소 위도
                .longitude(String.valueOf(targetItem.get("gpslong")))  // 버스 정류소 경도
                .build();


        if (Objects.isNull(userLocationResponseDto)) {
            throw new NoSuchItemsException("Can not find Bus stop");
        }

        return new ResponseEntity<>(this.userLocationResponseDto, HttpStatus.OK);
    }

    public UserLocationResponseDto getUserLocationResponseDto() {
        return userLocationResponseDto;
    }

    public void setUserLocationResponseDto(UserLocationResponseDto userLocationResponseDto) {
        this.userLocationResponseDto = userLocationResponseDto;
    }
}
