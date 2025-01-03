package com.cloudestudio.hosgetserver.webTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

/**
 * @Class HttpClientUtil
 * @Author Create By Matrix·张
 * @Date 2024/12/14 下午3:50
 * 网络配置类
 */
public class HttpClientUtil {

    public static PushResponse pushDataInfo(String apiUrl,String jsonData) throws IOException {
        //String emrPatientInfoUrl ="http://192.168.0.205:8881/hclient/emr/receive/emrPatientInfo";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject jsonObject = new JSONObject(response.toString());
            boolean result = jsonObject.getBoolean("result");
            String desc = jsonObject.getString("desc");
            String errorCode = jsonObject.optString("errorCode", null); // 使用 optString 以防字段不存在
            String errorName = jsonObject.optString("errorName", null);
            String id = jsonObject.getString("id");

            if(result){
                return new PushResponse(true, desc, errorCode, errorName, id);
            }else{
                return new PushResponse(false, desc, errorCode, errorName, id);
            }
        }
    }
}
