package team.sw.everyonetayo.util.busstop;

import org.json.JSONArray;
import org.json.XML;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.util.busroute.BusRouteRepository;
import team.sw.everyonetayo.util.busroute.Route;
import team.sw.everyonetayo.util.busroute.RouteDto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BusStopController {

    @Autowired
    BusStopRepository busRouteRepository;

    @GetMapping("/toJsonBusStop/{cityCode}")
    public List<BusStopDto> callApiWithJson(@PathVariable String cityCode) {

        List<BusStopDto> busStopDtoList = new ArrayList<>();
        StringBuffer result = new StringBuffer();
        StringBuilder jsonPrintString = new StringBuilder();
        int page = 0;
        try {
            boolean next = true;
            while (next) {
                page++;
                String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getSttnNoList?"
                        + "ServiceKey=CgnbAYE4XUYPkQC7evWWGhuYlYW7NBsnpm7PFhbqBEpuM1Hoe8XQq6xRQRt%2BleOg1IQD4WulnDCRDueEMao%2FCA%3D%3D"
                        + "&cityCode=" + cityCode
                        + "&pageNo=" + page;
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

                Object count = bodyObject.get("numOfRows");
                if (count.equals(0)) {
                    next = false;
                }
                JSONObject itemObject = (JSONObject) bodyObject.get("items");
                jsonPrintString.append(itemObject.toString());
                System.out.println(jsonPrintString);
                try {
                    JSONArray item = (JSONArray) itemObject.get("item");
                    for (int i = 0; i < item.length(); i++) {
                        JSONObject targetItem = (JSONObject) item.get(i);
                        BusStopDto busStopDto = new BusStopDto();

                        if (!targetItem.isNull("nodeno")) {
                            busStopDto = new BusStopDto.BusStopDtoBuilder()
                                    .gpsLati(String.valueOf(targetItem.get("gpslati")))
                                    .gpsLong(String.valueOf(targetItem.get("gpslong")))
                                    .nodeid(String.valueOf(targetItem.get("nodeid")))
                                    .nodenm(String.valueOf(targetItem.get("nodenm")))
                                    .nodeno(String.valueOf(targetItem.get("nodeno")))
                                    .build();
                        } else {
                            busStopDto = new BusStopDto.BusStopDtoBuilder()
                                    .gpsLati(String.valueOf(targetItem.get("gpslati")))
                                    .gpsLong(String.valueOf(targetItem.get("gpslong")))
                                    .nodeid(String.valueOf(targetItem.get("nodeid")))
                                    .nodenm(String.valueOf(targetItem.get("nodenm")))
                                    .build();
                        }

                        BusStop busStop = new BusStop.BusStopBuilder()
                                .gpsLati(String.valueOf(busStopDto.getGpsLati()))
                                .gpsLong(String.valueOf(busStopDto.getGpsLong()))
                                .nodeid(String.valueOf(busStopDto.getNodeid()))
                                .nodenm(String.valueOf(busStopDto.getNodenm()))
                                .nodeno(String.valueOf(busStopDto.getNodeno()))
                                .citycode(String.valueOf(cityCode))
                                .build();

                        busStopDtoList.add(busStopDto);
                        busRouteRepository.save(busStop);
                    }

                    System.out.println("count = " + count);
                } catch (ClassCastException classCastException) {
                    System.out.println("끝!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return busStopDtoList;
    }
}
