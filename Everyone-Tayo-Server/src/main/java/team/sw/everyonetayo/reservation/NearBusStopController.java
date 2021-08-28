package team.sw.everyonetayo.reservation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.sw.everyonetayo.api.busroute.BusRouteRepository;
import team.sw.everyonetayo.exception.NoSuchItemsException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestController
public class NearBusStopController {

    @Autowired
    BusRouteRepository busRouteRepository;

    private ResponseNearBusDto responseNearBusDto;
    private ResponseRouteWayPointDto busWayPoint;
    private ResponseReservationDto targetBus;


    //        35.284041, 129.095096
    @SneakyThrows
    @PostMapping(value = "/reservation-app-user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity callApiWithJson(@RequestBody RequestNearBusDto requestNearBusDto) {
        responseNearBusDto = new ResponseNearBusDto();
        StringBuffer result = new StringBuffer();
        StringBuilder jsonPrintString1 = new StringBuilder();
        StringBuilder jsonPrintString2 = new StringBuilder();
        StringBuilder jsonPrintString3 = new StringBuilder();

        // Step 1. 가장 가까운 버스 정류소 추출
        try {
            boolean next = true;
            String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCrdntPrxmtSttnList?"
                    + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                    + "&gpsLati=" + requestNearBusDto.getLatitude()
                    + "&gpsLong=" + requestNearBusDto.getLongitude();

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
            jsonPrintString1.append(itemObject.toString());
            System.out.println(jsonPrintString1);
            JSONArray item = (JSONArray) itemObject.get("item");
            JSONObject targetItem = (JSONObject) item.get(0);
            responseNearBusDto = new ResponseNearBusDto.ResponseNearBusDtoBuilder()
                    .nodeId(String.valueOf(targetItem.get("nodeid")))  // 버스정류소 id
                    .nodeNm(String.valueOf(targetItem.get("nodenm")))  // 버스 정류소 이름
                    .cityCode(String.valueOf(targetItem.get("citycode")))  // 도시 코드
                    .build();

            System.out.println("가장 가까이 있는 버스 정류소의 이름 = " + responseNearBusDto.getNodeNm());
            System.out.println("가장 가까이 있는 버스 정류소의 ID = " + responseNearBusDto.getNodeId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 가장 가까운 버스 정류소 추출

        // 사용자가 입력한 버스 번호를 토대로 DB 조회
        List<String> targetRoutes = new ArrayList<>();

        if (busRouteRepository.existsByRouteNo(requestNearBusDto.getBusNumber())) {

            busRouteRepository.findAllByRouteNo(requestNearBusDto.getBusNumber()).stream().filter(
                    item -> item.getCityCode().equals(responseNearBusDto.getCityCode())
            ).forEach(
                    item -> targetRoutes.add(item.getRouteId()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 버스 번호 입니다.");
        }
        for (String targetRoute : targetRoutes) {
            System.out.println("dklsfjklasdjkfljadsklfj" + targetRoute);
        }
//        String targetRouteId = busRouteRepository.findByRouteNo(requestNearBusDto.getBusNumber())
//                .orElseThrow(() -> new NoSuchRouteIdException("존재하지 않는 RouteId 입니다.")).getRouteId();
//        System.out.println("targetRouteId = " + targetRouteId);
        String targetRouteId = null;
        // 동일한 노선 Id를 가지는 버스 집합
        List<ResponseBusLocationDto> busList = new ArrayList<>();
        // Step2. 가장 가까운 버스 정류소의 버스 정류소 Id 추츨

//        targetRoutes.removeIf(targetRoute -> !targetRoute.equals(responseNearBusDto.getCityCode()));
        for (String targetRoute : targetRoutes) {
            System.out.println("현재 버스 Route는 = " + targetRoute);
        }

        for (String targetRoute : targetRoutes) {
            System.out.println("추출한 노선 ID " + targetRoute);
            String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusRouteInfoInqireService/getRouteAcctoThrghSttnList?"
                    + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                    + "&numOfRows=100"
                    + "&pageNo=1"
                    + "&cityCode=" + responseNearBusDto.getCityCode()
                    + "&routeId=" + targetRoute;

            System.out.println("추출한 도시 코드 = " + responseNearBusDto.getCityCode());
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
            System.out.println("#1 가장 가까운 버스의 버스 정류소 Id 추출 = " + jsonObject);
            result.setLength(0);
            JSONObject responseObject = (JSONObject) jsonObject.get("response");
            JSONObject bodyObject = (JSONObject) responseObject.get("body");
            JSONObject itemObject = (JSONObject) bodyObject.get("items");
            jsonPrintString2.append(itemObject.toString());
            JSONArray item = (JSONArray) itemObject.get("item");
            for (int i = 0; i < item.length(); i++) {
                JSONObject targetItem = (JSONObject) item.get(i);
                if (responseNearBusDto.getNodeId().equals(targetItem.get("nodeid"))) {
                    targetRouteId = targetRoute;

                    // busWayPoint : 사용자와 가장 가까운 거리에 위치 한 버스 정류소의 순번과 id 저장
                    busWayPoint = new ResponseRouteWayPointDto.ResponseRouteWayPointDtoBuilder()
                            .nodeId(String.valueOf(targetItem.get("nodeid")))
                            .nodeOrd(String.valueOf(targetItem.get("nodeord")))
                            .build();
                    System.out.println("현재 버스의 버스 정류소 순서 = " + busWayPoint.getNodeOrd());
                    System.out.println("현재 버스의 버스 정류소 ID  = " + busWayPoint.getNodeId());
                }
            }
        }
        // 가장 가까운 버스 정류소의 버스 정류소 Id 추츨

        // Step3. 해당 노선 Id를 가지는 모든 차량 정보 추출
//        try {
//            int page = 0;
//            boolean next = true;
//            while (next) {
//                page++;
        String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList?"
                + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                + "&cityCode=" + responseNearBusDto.getCityCode()
                + "&routeId=" + targetRouteId;

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
//                Object count = bodyObject.get("totalCount");
//                if ((10 * page) >= (int) count) {
//                    next = false;
//                }
//            JSONObject itemObject = itemObject = (JSONObject) bodyObject.get("items");
        JSONObject itemObject = null;
        if (bodyObject.get("items") != "") {
            itemObject = (JSONObject) bodyObject.get("items");
        } else {
            throw new NoSuchItemsException("현재 도착 예정인 버스가 없습니다.");
        }
        jsonPrintString3.append(itemObject.toString());
        if (itemObject.has("item")) {
            JSONObject targetItems = itemObject.optJSONObject("item");
            if (targetItems != null) {
                JSONObject item = (JSONObject) itemObject.get("item");
                if (Integer.parseInt(busWayPoint.getNodeOrd()) > Integer.parseInt(String.valueOf(item.get("nodeord")))) {
                    ResponseBusLocationDto targetBusLocationDto = new ResponseBusLocationDto.ResponseBusLocationDtoBuilder()
                            .uuid(String.valueOf(item.get("vehicleno")))
                            .nodeId(String.valueOf(item.get("nodeid")))
                            .nodeOrd(String.valueOf(item.get("nodeord")))
                            .routeNo(String.valueOf(item.get("routenm")))
                            .routeId(targetRouteId)
                            .build();

                    targetBus = new ResponseReservationDto.ResponseReservationDtoBuilder()
                            .uuid(targetBusLocationDto.getUuid())
                            .state("custom")
                            .nodeId(responseNearBusDto.getNodeId())
                            .time(LocalDateTime.now())
                            .busNumber(targetBusLocationDto.getRouteNo())
                            .build();

                    System.out.println("나는 오브젝트" + targetBusLocationDto.getUuid());
                }
            } else {
                JSONArray item = (JSONArray) itemObject.get("item");
                for (int i = 0; i < item.length(); i++) {
                    JSONObject targetItem = (JSONObject) item.get(i);
                    if (Integer.parseInt(busWayPoint.getNodeOrd()) > Integer.parseInt(String.valueOf(targetItem.get("nodeord")))) {
                        ResponseBusLocationDto targetBusLocationDto = new ResponseBusLocationDto.ResponseBusLocationDtoBuilder()
                                .uuid(String.valueOf(targetItem.get("vehicleno")))
                                .nodeId(String.valueOf(targetItem.get("nodeid")))
                                .nodeOrd(String.valueOf(targetItem.get("nodeord")))
                                .routeNo(String.valueOf(targetItem.get("routenm")))
                                .routeId(targetRouteId)
                                .build();
                        System.out.println("버스의 차량 번호 = " + targetBusLocationDto.getUuid());
                        busList.add(targetBusLocationDto);
                        final Comparator<ResponseBusLocationDto> comp = (p1, p2) -> Integer.compare(Integer.parseInt(p1.getNodeOrd()), Integer.parseInt(p2.getNodeOrd()));
                        Optional<ResponseBusLocationDto> largest = busList.stream().max(comp);
                        largest.ifPresent(
                                target -> targetBus = new ResponseReservationDto.ResponseReservationDtoBuilder()
                                        .uuid(target.getUuid())
                                        .state("custom")
                                        .nodeId(responseNearBusDto.getNodeId())
                                        .time(LocalDateTime.now())
                                        .busNumber(targetBusLocationDto.getRouteNo())
                                        .build()
                        );

                        System.out.println("나는 배열" + targetBus.getUuid());
                    }

                }
            }

//                JSONArray item = (JSONArray) itemObject.get("item");
        }

//            }
//        } catch (NoSuchItemsException me) {
//            ResponseReservationDto responseReservationDto = new ResponseReservationDto.ResponseReservationDtoBuilder()
//                    .uuid("nope").busNumber("nope").state("nope").nodeId("nope").time(LocalDateTime.now()).build();
//
//            return ResponseEntity.badRequest().body(responseReservationDto);
//            return ResponseEntity.badRequest().build();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

        return ResponseEntity.ok(targetBus);
    }

    /**
     * 1. 버스 정류소의 현재 순번
     * 2. busList의 순서 중 가장 가까운 순번인 버스를 추출
     */
}

