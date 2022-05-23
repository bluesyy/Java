import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.inflearn.BookDTO;

import java.util.ArrayList;
import java.util.List;

public class Project01_A {
    public static void main(String[] args) {

        // Gson 라이브러리를 이용해서
        // Object(BookDTO) -> JSON(String)
        BookDTO dto = new BookDTO("자바", 21000, "능남출판", 600);    // 객체를 만들어 놔야 변환이 가능하다
        Gson g = new Gson();
        String json = g.toJson(dto);
        System.out.println(json);       // 출력: {"title":"자바","price":21000,"company":"능남출판","page":600}

        // JSON(String) -> Object(BookDTO)
        BookDTO dto1 = g.fromJson(json, BookDTO.class);
        System.out.println(dto1);       // 출력: BookDTO{title='자바', price=21000, company='능남출판', page=600}
        System.out.println(dto1.getTitle() + "\t" + dto1.getPrice());   // 출력: 자바	21000  -> 각각 호출

        
        // Object(List<BookDTO>) -> JSON(String) 여러 개의 데이터를 json 으로
        List<BookDTO> lst = new ArrayList<BookDTO>();
        lst.add(new BookDTO("자바1", 21000, "능남출판", 600));
        lst.add(new BookDTO("자바2", 31000, "능남출판", 500));
        lst.add(new BookDTO("자바3", 41000, "능남출판", 1300));

        String lstJson = g.toJson(lst);
        System.out.println(lstJson);
        // [{"title":"자바1","price":21000,"company":"능남출판","page":600},{"title":"자바2","price":31000,"company":"능남출판","page":500},{"title":"자바3","price":41000,"company":"능남출판","page":1300}] 객체 3개

        // JSON(String) -> Object(List<BookDTO>)
        List<BookDTO> lst1 = g.fromJson(lstJson, new TypeToken<List<BookDTO>>(){}.getType());  // getType() 이라는 함수로 타입 정보를 가져 와서 리스트로 받는다.
        for(BookDTO vo : lst1) {
            System.out.println(vo);
            /*
                    BookDTO{title='자바1', price=21000, company='능남출판', page=600}
                    BookDTO{title='자바2', price=31000, company='능남출판', page=500}
                    BookDTO{title='자바3', price=41000, company='능남출판', page=1300}
            */
        }
    }
}
