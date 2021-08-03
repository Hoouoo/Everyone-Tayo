package team.sw.everyonetayo.util.toJson;

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
            String apiUrl = "http://openapi.tago.go.kr/openapi/service/BusRouteInfoInqireService/getRouteNoList?"
                    + "ServiceKey=CgnbAYE4XUYPkQC7evWWGhuYlYW7NBsnpm7PFhbqBEpuM1Hoe8XQq6xRQRt%2BleOg1IQD4WulnDCRDueEMao%2FCA%3D%3D"
                    + "&cityCode=21]"
                    + "&pageNo=2";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonPrintString;
    }
}
