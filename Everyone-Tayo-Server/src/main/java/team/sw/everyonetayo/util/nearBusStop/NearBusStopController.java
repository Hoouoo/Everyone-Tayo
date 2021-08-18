package team.sw.everyonetayo.util.nearBusStop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NearBusStopController {
//        35.284041, 129.095096
    @PostMapping("/reservation-app-user")
    public ResponseEntity<NearBusDto> callApiWithJson(@RequestBody NearBusRequestDto nearBusRequestDto) {
        NearBusDto targetNearBusDto = new NearBusDto();
        StringBuffer result = new StringBuffer();
        StringBuilder jsonPrintString = new StringBuilder();
        try {
            boolean next = true;
                String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCrdntPrxmtSttnList?"
                        + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                        + "&gpsLati=" + nearBusRequestDto.getLatitude()
                        + "&gpsLong=" + nearBusRequestDto.getLongitude();

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
//                result.setLength(0);
                JSONObject responseObject = (JSONObject) jsonObject.get("response");
                JSONObject bodyObject = (JSONObject) responseObject.get("body");
                JSONObject itemObject = (JSONObject) bodyObject.get("items");
                jsonPrintString.append(itemObject.toString());
                System.out.println(jsonPrintString);
                JSONArray item = (JSONArray) itemObject.get("item");
                    JSONObject targetItem = (JSONObject) item.get(0);
                    targetNearBusDto = new NearBusDto.NearBusDtoBuilder()
                            .nodeId(String.valueOf(targetItem.get("nodeid")))
                            .nodeNm(String.valueOf(targetItem.get("nodenm")))
                            .cityCode(String.valueOf(targetItem.get("citycode")))
                            .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<NearBusDto>(targetNearBusDto, HttpStatus.OK);
    }
}
