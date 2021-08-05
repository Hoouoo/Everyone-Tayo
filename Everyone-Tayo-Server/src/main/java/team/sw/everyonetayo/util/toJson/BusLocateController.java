package team.sw.everyonetayo.util.toJson;

import org.json.JSONArray;
import org.json.XML;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class BusLocateController {

    @GetMapping("/toJsonLocate")
    public String callApiWithJson(){
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        try{
            String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusSttnInfoInqireService/getCtyCodeList?"
                    + "ServiceKey=CgnbAYE4XUYPkQC7evWWGhuYlYW7NBsnpm7PFhbqBEpuM1Hoe8XQq6xRQRt%2BleOg1IQD4WulnDCRDueEMao%2FCA%3D%3D";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            org.json.JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();

//            JSONParser parser = new JSONParser();
//            JSONObject obj = null;
//            obj = (JSONObject) parser.parse(jsonObject.toString());
            JSONObject responseObject = (JSONObject)jsonObject.get("response");
            JSONObject bodyObject = (JSONObject)responseObject.get("body");
            JSONObject itemsObject = (JSONObject)bodyObject.get("items");
            JSONArray item = (JSONArray) itemsObject.get("item");

            System.out.println(item.get(1));
            for(int i = 0; i < item.length(); i++){
                JSONObject tmp = (JSONObject) item.get(i);
                int city = (int) tmp.get("citycode");
                System.out.println("도시:" + city);
            }
//            System.out.println(city);
//            org.json.JSONArray citycode = (org.json.JSONArray) itemsObject.get("citycode");
//            JSONObject jObject2 = (JSONObject) parser.parse (itemObject.get("citycode").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;
    }
}
