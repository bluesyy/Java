import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class Project01_C {
    public static void main(String[] args) {

        // json 형식을 출력해보자
        String src = "info.json";
        // IO -> Stream 데이터를 읽고 쓰기 위한
        InputStream is = Project01_C.class.getResourceAsStream(src);    // 같은 디렉토리에 있어서 가능. Project01_C.class 가 있는 곳에서 갖고 오라는 뜻
        if(is == null) {
            throw new NullPointerException("Cannot find resource file"); // 예외처리
        }
        JSONTokener tokener = new JSONTokener(is);  // 문자열을 Json 형태로 변환시켜 메모리에 올라가게 한다.
        JSONObject object = new JSONObject(tokener);
        JSONArray students = object.getJSONArray("students");
        for(int i = 0; i < students.length(); i++) {
            JSONObject student = (JSONObject) students.get(i);    // 0번째
            System.out.print(student.get("name") + "\t");         // 각각 출력
            System.out.print(student.get("phone") + "\t");
            System.out.println(student.get("address"));
            // 김두한	010-222-3333	서울
            // 김두한2	010-222-3333	경기

        }


    }
}
