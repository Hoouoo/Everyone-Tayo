package team.sw.everyonetayo.api.busuuid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team.sw.everyonetayo.api.busroute.BusRouteRepository;
import team.sw.everyonetayo.api.busroute.Route;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class BusUuidController {

    @Autowired
    BusUuidRepository busUuidRepository;

    @Autowired
    BusRouteRepository busRouteRepository;

    @GetMapping("/toJsonBusUuid/{cityCode}")
    public List<BusUuidDto> callApiWithJson(@PathVariable String cityCode, String routeId){

        List<BusUuidDto> busUuidDtoList = new ArrayList<>();
        List<Route> routeIdList = busRouteRepository.findByCityCode(cityCode);
        StringBuffer result = new StringBuffer();
        StringBuilder jsonPrintString = new StringBuilder();
        for(int routeIndex=0 ; routeIndex < routeIdList.size() ; routeIndex++) {
            int page = 0;
            try {
                boolean next = true;
                while (next) {
//                    AtomicInteger routeIndex = new AtomicInteger();
                    page++;
                    String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList?"
                            + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                            + "&cityCode=" + cityCode
                            + "&routeId=" + routeIdList.get(routeIndex).getRouteId()
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

                    Object count = bodyObject.get("totalCount");
                    if ((10 * page) >= (int) count) {
                        next = false;
                    }

                    JSONObject itemObject = (JSONObject) bodyObject.get("items");
                    jsonPrintString.append(itemObject.toString());
                    System.out.println(jsonPrintString);

                    JSONArray item = (JSONArray) itemObject.get("item");
                    for (int i = 0; i < item.length(); i++) {
                        JSONObject targetItem = (JSONObject) item.get(i);
                        BusUuidDto busUuidDto;

                        busUuidDto = new BusUuidDto.BusUuidDtoBuilder()
                                .uuid(String.valueOf(targetItem.get("vehicleno")))
                                .routenm(String.valueOf(targetItem.get("routenm")))
                                .build();

                        String routenm = String.valueOf(busUuidDto.getRoutenm());
                        char checkRouteNm = routenm.charAt(0);

                        if (checkRouteNm >= '0' && checkRouteNm <= '9') {
                            Pattern pattern = Pattern.compile("([0-9a-zA-z-])+");
                            Matcher match = pattern.matcher(String.valueOf(busUuidDto.getRoutenm()));
                            if (match.find()) routenm = match.group();
                        }

                        BusUuid busUuid = new BusUuid.BusUuidBuilder()
                                .uuid(String.valueOf(busUuidDto.getUuid()))
                                .routenm(routenm)
                                .cityCode(cityCode)
                                .build();

                        busUuidDtoList.add(busUuidDto);

                        if(!busUuidRepository.existsByUuid(busUuid.getUuid())) {
                            busUuidRepository.save(busUuid);
                        }else{
                            System.out.println("\"중복 !!\" = " + "중복 !!");
                        }
                    }

                    System.out.println("count = " + count);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return busUuidDtoList;
    }
}
