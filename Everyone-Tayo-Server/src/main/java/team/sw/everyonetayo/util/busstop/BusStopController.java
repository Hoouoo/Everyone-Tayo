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
import java.util.Arrays;
import java.util.List;

@RestController
public class BusStopController {

    @Autowired
    BusStopRepository busRouteRepository;

    @GetMapping("/toJsonBusStopAll")
    public List<BusStopDto> callApiWithJson() {

        List<BusStopDto> busStopDtoList = new ArrayList<>();
        List<String> cityCode = Arrays.asList("12", "22", "23", "24", "25", "26", "39", "31010", "31020", "31030", "31040", "31050", "31060"
                , "31070", "31080", "31090", "31100", "31110", "31120", "31130", "31140", "31150", "31160", "31170", "31180", "31190", "31200"
                , "31210", "31220", "31230", "31240", "31250", "31260", "31270", "31320", "31350", "31370", "31380", "32010", "32020", "32040"
                , "32050", "32060", "32070", "32310", "32330", "32340", "32350", "32360", "32370", "32380", "32390", "32400", "32410", "33010"
                , "33020", "33030", "33320", "33330", "33340", "33350", "33360", "33370", "33380", "34010", "34020", "34030", "34040", "34050"
                , "34060", "34330", "34390", "35010", "35020", "35030", "35040", "35050", "35060", "35320", "35330", "35340", "35350", "35360"
                , "35370", "35380", "36010", "36020", "36030", "36060", "36330", "36350", "36400", "36410", "36420", "36430", "36440", "36480"
                , "37010", "37020", "37030", "37050", "37060", "37070", "37080", "37090", "37100", "37310", "37320", "37330", "37350", "37360"
                , "37370", "37390", "37410", "37430", "38010", "38030", "38050", "38070", "38080", "38090", "38100", "38310", "38320", "38330"
                , "38340", "38350", "38360", "38370", "38380", "38390", "38400");
        for (String cityCodeIndex : cityCode) {

            StringBuffer result = new StringBuffer();
            StringBuilder jsonPrintString = new StringBuilder();
            int page = 0;
            try {
                boolean next = true;
                while (next) {
                    page++;
                    String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getSttnNoList?"
                            + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                            + "&cityCode=" + cityCodeIndex
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
                    System.out.println(jsonObject.toString());
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return busStopDtoList;
    }

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
                        + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
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
                System.out.println(jsonObject.toString());
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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return busStopDtoList;
    }
}
