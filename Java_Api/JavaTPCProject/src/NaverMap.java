import kr.inflearn.AddressVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class NaverMap implements ActionListener {

    Project01_F naverMap;
    public NaverMap(Project01_F naverMap) {
        this.naverMap = naverMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String client_id = "7e6wr6raal";
        String client_secret = "XAhfN4tYMu2V4SiYu5EfCO6fWq0W6EWbgVX1sJqc";
        AddressVO vo = null;
        try {
            String address = naverMap.address.getText();
            String addr = URLEncoder.encode(address, "UTF-8"); // 공백 처리
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;  // 요청Url = apiUrl + 키보드로 받은 addr

            URL url = new URL(apiURL);      // 정확한 url 인지 판단. -> 아니면 MalformedUrlException
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
            String inputLine;
            StringBuffer response = new StringBuffer();
            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            System.out.println(object);

            JSONArray arr = object.getJSONArray("addresses");    // 배열이니까 addresses 를 key 값으로
            for(int i = 0; i<arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                // AddressVO 객체에 주소 정보를 저장
                vo = new AddressVO();
                vo.setRoadAddress((String) temp.get("roadAddress"));
                vo.setJibunAddress((String) temp.get("jibunAddress"));
                vo.setX((String) temp.get("x"));
                vo.setY((String) temp.get("y"));
                System.out.println(vo);
            }
            map_service(vo);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void map_service(AddressVO vo) {
        String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
        try {
            String pos = URLEncoder.encode(vo.getX() + " " + vo.getY(), "UTF-8");
            URL_STATICMAP += "center=" + vo.getX() + "," + vo.getY();
            URL_STATICMAP += "&level=16&w=700&h=500";
            URL_STATICMAP += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(vo.getRoadAddress(), "UTF-8");
            URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "7e6wr6raal");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "XAhfN4tYMu2V4SiYu5EfCO6fWq0W6EWbgVX1sJqc");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode == 200) {
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                String tempname = Long.valueOf(new Date().getTime()).toString(); // 랜덤한 이름으로 파일 생성
                File f = new File(tempname + ".jpg");
                f.createNewFile();  // 이때 생성은 됐지만 현재 0 byte
                OutputStream outputStream = new FileOutputStream(f);
                while ((read = is.read(bytes)) != -1) {     // 1024 만큼 읽어서 read 에 저장
                    outputStream.write(bytes, 0, read);
                }
                is.close();
                ImageIcon img = new ImageIcon(f.getName());
                naverMap.imageLabel.setIcon(img);
                naverMap.resAddress.setText(vo.getRoadAddress());
                naverMap.jibunAddress.setText(vo.getJibunAddress());
                naverMap.resX.setText(vo.getX());
                naverMap.resY.setText(vo.getY());

            } else {    // 에러 발생
                System.out.println(responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
