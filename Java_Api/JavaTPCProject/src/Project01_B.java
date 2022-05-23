import org.json.*;      // 이렇게 하면 json. 이하 다 import

public class Project01_B {
    public static void main(String[] args) {
        // JSON-JAVA(org.json)

        JSONArray students = new JSONArray();

        JSONObject student = new JSONObject();
        student.put("name", "김두한");
        student.put("phone", "010-222-3333");
        student.put("address", "서울");
        System.out.println(student);     // {"address":"서울","phone":"010-222-3333","name":"김두한"}

        students.put(student);

        student = new JSONObject();
        student.put("name", "김두한2");
        student.put("phone", "010-222-3333");
        student.put("address", "경기");

        students.put(student);
        
        JSONObject object = new JSONObject();
        
        object.put("students", students);

        System.out.println("object = " + object.toString(2));   // toString 처리 안하면 한 줄로 쭉 나온다
        /*
                object = {"students": [
                {
                    "address": "서울",
                        "phone": "010-222-3333",
                        "name": "김두한"
                },
                {
                    "address": "경기",
                        "phone": "010-222-3333",
                        "name": "김두한2"
                }
        ]}
        */




    }
}
