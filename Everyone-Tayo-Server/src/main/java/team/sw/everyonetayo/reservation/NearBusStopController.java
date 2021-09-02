package team.sw.everyonetayo.reservation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pusha.packet.Packet;
import pusha.packet.StringPacket;
import pusha.server.manager.ServerManager;
import team.sw.everyonetayo.api.busroute.BusRouteRepository;
import team.sw.everyonetayo.exception.NoSuchBusArriverStatusExecption;
import team.sw.everyonetayo.exception.NoSuchItemsException;
import team.sw.everyonetayo.pusha.PushaConfiguration;
import team.sw.everyonetayo.reservation.table.ReservationService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class NearBusStopController {

    @Autowired
    BusRouteRepository busRouteRepository;
    @Autowired
    ReservationService reservationService;


    private ResponseNearBusDto responseNearBusDto;
    private ResponseRouteWayPointDto busWayPoint;
    private ResponseBusArrivalDto busArriverStatus;
    private ResponseReservationDto targetBus;

    private String busstopLatitude;
    private String busstopLongtitude;


    @SneakyThrows
    @PostMapping(value = "/reservation-app-user")
    public ResponseEntity callApiWithJson(@RequestBody RequestNearBusDto requestNearBusDto)  {
        busArriverStatus = null;
        responseNearBusDto = new ResponseNearBusDto();
        StringBuffer result = new StringBuffer();

        System.out.println("requestNearBusDto = " + requestNearBusDto.getBusNumber() + " "+ requestNearBusDto.getLongitude() + " "+requestNearBusDto.getLatitude() + " "+ requestNearBusDto.getToken());
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

            JSONArray item = (JSONArray) itemObject.get("item");
            JSONObject targetItem = (JSONObject) item.get(0);
            responseNearBusDto = new ResponseNearBusDto.ResponseNearBusDtoBuilder()
                    .nodeId(String.valueOf(targetItem.get("nodeid")))  // 버스정류소 id
                    .nodeNm(String.valueOf(targetItem.get("nodenm")))  // 버스 정류소 이름
                    .cityCode(String.valueOf(targetItem.get("citycode")))  // 도시 코드
                    .build();
            busstopLatitude = String.valueOf(targetItem.get("gpslati"));
            busstopLongtitude =  String.valueOf(targetItem.get("gpslong"));

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
            throw new NoSuchBusArriverStatusExecption("버스 도착 정보가 존재하지 않습니다.");
        }
        // step2. 사용자가 입력한 버스 정류장에 이동하는 버스의 routeId를 들고 옴
        int page = 0;
        boolean next = true;
        while (next) {
            page++;
            String apiUrl = "http://openapi.tago.go.kr/openapi/service/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList?"
                    + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                    + "&cityCode=" + responseNearBusDto.getCityCode()
                    + "&nodeId=" + responseNearBusDto.getNodeId();

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
            System.out.println("jsonObject.toString() = " + jsonObject.toString());
            result.setLength(0);
            JSONObject responseObject = (JSONObject) jsonObject.get("response");
            JSONObject bodyObject = (JSONObject) responseObject.get("body");
            JSONObject itemObject = null;
            if (bodyObject.get("items") != "") {
                itemObject = (JSONObject) bodyObject.get("items");
            } else {
                throw new NoSuchItemsException("정류소가 존재하지 않습니다.");
            }
            if (itemObject.has("item")) {
                JSONObject targetItems = itemObject.optJSONObject("item");
                if (targetItems != null) {
                    JSONObject item = (JSONObject) itemObject.get("item");

                    String routenm = String.valueOf(item.get("routeno"));
                    char checkRouteNm = routenm.charAt(0);

                    if (checkRouteNm >= '0' && checkRouteNm <= '9') {
                        Pattern pattern = Pattern.compile("([0-9a-zA-z-])+");
                        Matcher match = pattern.matcher(String.valueOf(item.get("routeno")));
                        if (match.find()) routenm = match.group();
                    }

                    if (requestNearBusDto.getBusNumber().equals(routenm)) {
                        int arrtime = (int) item.get("arrtime")/60;
                        busArriverStatus = new ResponseBusArrivalDto.ResponseBusArrivalDtoBuilder()
                                .state(String.valueOf(arrtime))
                                .routeId(String.valueOf(item.get("routeid")))
                                .build();
                    }
                } else {
                    JSONArray item = (JSONArray) itemObject.get("item");
                    for (int i = 0; i < item.length(); i++) {
                        JSONObject targetItem = (JSONObject) item.get(i);

                        String routenm = String.valueOf(targetItem.get("routeno"));
                        char checkRouteNm = routenm.charAt(0);

                        if (checkRouteNm >= '0' && checkRouteNm <= '9') {
                            Pattern pattern = Pattern.compile("([0-9a-zA-z-])+");
                            Matcher match = pattern.matcher(String.valueOf(targetItem.get("routeno")));
                            if (match.find()) routenm = match.group();
                        }

                        if (requestNearBusDto.getBusNumber().equals(routenm)) {
                            System.out.println("byte");
                            int targetArrtime = (int) targetItem.get("arrtime")/60;
                            System.out.println(targetArrtime);
                            busArriverStatus = new ResponseBusArrivalDto.ResponseBusArrivalDtoBuilder()
                                    .state(String.valueOf(targetArrtime))
                                    .routeId(String.valueOf(targetItem.get("routeid")))
                                    .build();
                        }

                    }
                }
                Object count = bodyObject.get("totalCount");
                if ((10 * page) >= (int) count) {
                    next = false;
                }

            }
        }
        // step2. 사용자가 입력한 버스 정류장에 이동하는 버스의 routeId를 들고 옴

        List<ResponseBusLocationDto> busList = new ArrayList<>(); //추출한 버스 정보를 담는 List


        // Step3. 가장 가까운 버스 정류소의 버스 정류소 Id 추츨
        String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusRouteInfoInqireService/getRouteAcctoThrghSttnList?"
                + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                + "&numOfRows=100"
                + "&pageNo=1"
                + "&cityCode=" + responseNearBusDto.getCityCode()
                + "&routeId=" + busArriverStatus.getRouteId();

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
        JSONArray item = (JSONArray) itemObject.get("item");
        for (int i = 0; i < item.length(); i++) {
            JSONObject targetItem = (JSONObject) item.get(i);
            if (responseNearBusDto.getNodeId().equals(targetItem.get("nodeid"))) {
                // busWayPoint : 사용자와 가장 가까운 거리에 위치 한 버스 정류소의 순번과 id 저장
                busWayPoint = new ResponseRouteWayPointDto.ResponseRouteWayPointDtoBuilder()
                        .nodeId(String.valueOf(targetItem.get("nodeid")))
                        .nodeOrd(String.valueOf(targetItem.get("nodeord")))
                        .build();

                System.out.println("현재 버스의 버스 정류소 순서 = " + busWayPoint.getNodeOrd());
                System.out.println("현재 버스의 버스 정류소 ID  = " + busWayPoint.getNodeId());
            }
        }

        // Step3. 가장 가까운 버스 정류소의 버스 정류소 Id 추츨

        // Step4. 해당 노선 Id를 가지는 모든 차량 정보 추출 -> 국토교통부_버스위치정보
        page = 0;
        next = true;
        while (next) {
            page++;
            apiUrl = "http://openapi.tago.go.kr/openapi/service/BusLcInfoInqireService/getRouteAcctoBusLcList?"
                    + "ServiceKey=tO6fJs7AxOJ%2Bf9N5nWEgSE16%2BuOewB1LlIMM%2Fs5NB6bHtZ%2B3iO%2BcOIKgzK4QrYfZmIzh0iwJ1XKdbhxKEK2FtA%3D%3D"
                    + "&cityCode=" + responseNearBusDto.getCityCode()
                    + "&routeId=" + busArriverStatus.getRouteId();

            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
            jsonObject = XML.toJSONObject(result.toString());
            result.setLength(0);
            responseObject = (JSONObject) jsonObject.get("response");
            bodyObject = (JSONObject) responseObject.get("body");
            Object count = bodyObject.get("totalCount");
            if ((10 * page) >= (int) count) {
                next = false;
            }
            itemObject = null;
            if (bodyObject.get("items") != "") {
                itemObject = (JSONObject) bodyObject.get("items");
            } else {
                throw new NoSuchItemsException("현재 도착 예정인 버스가 없습니다.");
            }

            if (itemObject.has("item")) {
                JSONObject targetItems = itemObject.optJSONObject("item");
                if (targetItems != null) {
                    JSONObject item2 = (JSONObject) itemObject.get("item");
                    if (Integer.parseInt(busWayPoint.getNodeOrd()) > Integer.parseInt(String.valueOf(item2.get("nodeord")))) {
                        ResponseBusLocationDto targetBusLocationDto = new ResponseBusLocationDto.ResponseBusLocationDtoBuilder()
                                .uuid(String.valueOf(item2.get("vehicleno")))
                                .nodeId(String.valueOf(item2.get("nodeid")))
                                .nodeOrd(String.valueOf(item2.get("nodeord")))
                                .routeNo(String.valueOf(item2.get("routenm")))
                                .routeId(busArriverStatus.getRouteId())
                                .build();

                        targetBus = new ResponseReservationDto.ResponseReservationDtoBuilder()
                                .uuid(targetBusLocationDto.getUuid())
                                .state(busArriverStatus.getState())
                                .nodeId(responseNearBusDto.getNodeId())
                                .time(LocalDateTime.now())
                                .busNumber(targetBusLocationDto.getRouteNo())
                                .build();

                    }
                } else {
                    JSONArray item2 = (JSONArray) itemObject.get("item");
                    for (int i = 0; i < item2.length(); i++) {
                        JSONObject targetItem = (JSONObject) item2.get(i);
                        if (Integer.parseInt(busWayPoint.getNodeOrd()) > Integer.parseInt(String.valueOf(targetItem.get("nodeord")))) {
                            ResponseBusLocationDto targetBusLocationDto = new ResponseBusLocationDto.ResponseBusLocationDtoBuilder()
                                    .uuid(String.valueOf(targetItem.get("vehicleno")))
                                    .nodeId(String.valueOf(targetItem.get("nodeid")))
                                    .nodeOrd(String.valueOf(targetItem.get("nodeord")))
                                    .routeNo(String.valueOf(targetItem.get("routenm")))
                                    .routeId(busArriverStatus.getRouteId())
                                    .build();
                            System.out.println("버스의 차량 번호 = " + targetBusLocationDto.getUuid());
                            busList.add(targetBusLocationDto);
                            final Comparator<ResponseBusLocationDto> comp = (p1, p2) -> Integer.compare(Integer.parseInt(p1.getNodeOrd()), Integer.parseInt(p2.getNodeOrd()));
                            Optional<ResponseBusLocationDto> largest = busList.stream().max(comp);
                            largest.ifPresent(
                                    target -> targetBus = new ResponseReservationDto.ResponseReservationDtoBuilder()
                                            .uuid(target.getUuid())
                                            .state(busArriverStatus.getState())
                                            .nodeId(responseNearBusDto.getNodeId())
                                            .time(LocalDateTime.now())
                                            .busNumber(targetBusLocationDto.getRouteNo())
                                            .build());
                        }

                    }
                }

            }
        }
        // Step4. 해당 노선 Id를 가지는 모든 차량 정보 추출 -> 국토교통부_버스위치정보
        if(Objects.nonNull((targetBus))){
            ReservationDto reservationDto = new ReservationDto.ReservationDtoBuilder()
                    .uuid(targetBus.getUuid())
                    .token(requestNearBusDto.getToken())
                    .state(targetBus.getState())
                    .build();
            System.out.println("reservationDto = " + reservationDto);

            reservationService.addReservation(reservationDto);

            //Push message by Pusha
            Packet packet = new StringPacket(
                    "RESERVATION_NOTICE",
                    "#",
                    responseNearBusDto.getNodeNm()+"#"+busstopLatitude+"#"+busstopLongtitude
            );
            ServerManager.instance.sendTarget(targetBus.getUuid(), packet);
        }
        return ResponseEntity.ok(targetBus);
    }
}

