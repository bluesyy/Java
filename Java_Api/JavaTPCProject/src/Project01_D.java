import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
public class Project01_D {

    public static void main(String[] args) {

        String apiURL="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
        String client_id = "7e6wr6raal";
        String client_secret = "XAhfN4tYMu2V4SiYu5EfCO6fWq0W6EWbgVX1sJqc";

        BufferedReader io = new BufferedReader(new InputStreamReader(System.in));   // system.in 은 바이트 스트림이고 BufferedReader 스트림은 문자 스트림이라 바로 연결할 수 없다. 그래서 InputStreamReader 를 이용해서 연결해준다.
        try {
            System.out.print("주소를 입력하세요: ");
            String address = io.readLine();
            String addr = URLEncoder.encode(address, "UTF-8"); // 공백 처리
            String reqUrl = apiURL + addr;  // 요청Url = apiUrl + 키보드로 받은 addr

            URL url = new URL(reqUrl);      // 정확한 url 인지 판단. -> 아니면 MalformedUrlException
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
            
            BufferedReader br;  // json 으로 받을 때 하나씩 받기 위해 미리 준비
            
            // 응답이 정상적인지 알아보기 위해서
            int responseCode = con.getResponseCode();
            if(responseCode == 200) {
                // 성공
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));   // InputStreamReader 를 이용해서 연결해준다. , 한글 깨지지 않게 처리
            } else {
                // 실패
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            // 한 줄씩 읽게끔 처리하기 위해
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            System.out.println(object.toString());
            
            JSONArray arr = object.getJSONArray("addresses");    // 배열이니까 addresses 를 key 값으로
            for(int i = 0; i<arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                System.out.println("address: " + temp.get("roadAddress"));
                System.out.println("jibunAddress: " + temp.get("jibunAddress"));
                System.out.println("englishAddress: " + temp.get("englishAddress"));
                System.out.println("경도: " + temp.get("x"));
                System.out.println("위도: " + temp.get("y"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
