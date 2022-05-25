package com.test.kr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenApiServiceImpl implements OpenApiService {

	@Override
	public String boxOffice(String targetDt) {
		
				// key
				String key = "55976345438eae387ac5f63afeb6d218";
				
				// API URL with Parameter
				String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
				apiURL += "?key=" + key + "&targetDt=" + targetDt;
				
				// API URL Connection
				URL url = null;
				HttpURLConnection con = null;
				try {
					url = new URL(apiURL);
					con = (HttpURLConnection)url.openConnection();
					con.setRequestMethod("GET");  // 반드시 "GET" 대문자로 지정
				} catch (MalformedURLException e) {
					e.printStackTrace();  // 잘못된 apiURL
				} catch (IOException e) {
					e.printStackTrace();  // apiURL 연결 실패
				}
				
				// API 응답
				StringBuilder sb = new StringBuilder();
				try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
					String line = null;
					while((line = br.readLine()) != null) {
						sb.append(line);
					}
				} catch(IOException e) {
					e.printStackTrace();  // API 응답 실패
				}
				
				// JSON 문자열 그대로 반환
				/* {
					 "boxOfficeResult":{
					    "boxofficeType":"일별 박스오피스",
					    "showRange":"20220516~20220516",
					    "dailyBoxOfficeList":[
					       {
					         "rnum":"1",
					         "rank":"1",  // 순위
					         "rankInten":"0",  // 전일대비 순위 증감
					         "rankOldAndNew":"OLD",  // 신규진입여부
					         "movieCd":"20212855", 
				 	         "movieNm":"닥터 스트레인지: 대혼돈의 멀티버스",  // 영화명
					         "openDt":"2022-05-04",  // 개봉일
					         "salesAmt":"3188418100",
					         "salesShare":"64.2",
					         "salesInten":"-862252720",
					         "salesChange":"-21.3",
					         "salesAcc":"52379307360",
					         "audiCnt":"293994",  // 일일관객수
					         "audiInten":"-75443",
					         "audiChange":"-20.4",
					         "audiAcc":"4906586",  // 누적관객수
					         "scrnCnt":"2477",
					         "showCnt":"10999"
					      },
					      ...
					    ]
					  }
					}
				*/
				return sb.toString();
		
		
	}

}
