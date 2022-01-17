package kr.co.weather.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.Grid;
import kr.co.weather.domain.Location;
import kr.co.weather.domain.Record;
import kr.co.weather.domain.Warning;
import kr.co.weather.domain.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {
	@Autowired
	private Mapper mapper;

	@Transactional
	@Override
	public List<Grid> selectGrid() {
		List<Grid> list = mapper.selectGrid();
		return list;
	}

	@Transactional //특보데이터 -> 테스트용
	@Override
	public List<Warning> getWarning() {
		List<Warning> list = new ArrayList<>();
		String serviceKey = "vr5hSgL05Y3LUdkM9%2FNzpSNCNwrzpoM8TRKqpgfv5biKw0xTeqOTKiV8TbDOJXnYRlFYzpTsPY0kDwg%2BDloRIA%3D%3D";
		String pageNo = "1"; //페이지 번호
		String numOfRows = "100"; //한 페이지 결과 수
		String stnId = "184"; //지점코드
		String fromTmFc = "20170105"; //시간(년월일)
		String toTmFc = "20170111"; //시간(년월일)

		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnMsg"); /*URL*/
		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
			urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
			urlBuilder.append("&" + URLEncoder.encode("stnId","UTF-8") + "=" + URLEncoder.encode(stnId, "UTF-8")); /*지점코드 *하단 지점코드 자료 참조*/
			urlBuilder.append("&" + URLEncoder.encode("fromTmFc","UTF-8") + "=" + URLEncoder.encode(fromTmFc, "UTF-8")); /*시간(년월일)(데이터 생성주기 : 시간단위로 생성)*/
			urlBuilder.append("&" + URLEncoder.encode("toTmFc","UTF-8") + "=" + URLEncoder.encode(toTmFc, "UTF-8")); /*시간(년월일) (데이터 생성주기 : 시간단위로 생성)*/

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;

			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			System.out.println(sb.toString());

			//파싱하기
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		return list;
	}

	//초단기실황
	@Override
	public Weather getultrasrtncst(String grid_x, String grid_y) throws IOException, ParseException {
		Weather w1 = new Weather();

		Date sysdate = new Date();
		DateFormat datesdf = new SimpleDateFormat("yyyyMMdd");
		DateFormat timesdf = new SimpleDateFormat("kkmm");
		String date = datesdf.format(sysdate);
		String time = timesdf.format(sysdate);
		System.out.println("일시 : "+date +" 시간 : "+time);

		//JSON 데이터를 요청하는 URL 문자열을 작성
		String apiurl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
		String serviceKey="vr5hSgL05Y3LUdkM9%2FNzpSNCNwrzpoM8TRKqpgfv5biKw0xTeqOTKiV8TbDOJXnYRlFYzpTsPY0kDwg%2BDloRIA%3D%3D";
		String pageNo ="1";
		String numOfRows = "10"; // 한 페이지 결과 수
		String data_type = "JSON"; // 타입 xml, json 등등 ..
		String baseDate = date;
		String baseTime = time; // API 제공 시간을 입력하면 됨
		String nx = grid_x; // 위도
		String ny = grid_y; // 경도

		StringBuilder urlBuilder = new StringBuilder(apiurl); /*URL*/
		//serviceKey는 이미 인코딩된 값임
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(data_type, "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
		urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
		urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

		/* GET방식으로 전송해서 파라미터 받아오기*/
		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();
		System.out.println("결과 : " + result);

		//문자열을 JSON으로 파싱함. 마지막 배열 형태로 저장된 데이터까지 파싱해 냄 
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParser.parse(result);
		JSONObject parse_response = (JSONObject) jsonObj.get("response");
		JSONObject parse_body = (JSONObject) parse_response.get("body");// response 로 부터 body 찾아오기
		JSONObject parse_items = (JSONObject) parse_body.get("items");// body 로 부터 items 받아오기
		JSONArray parse_item = (JSONArray) parse_items.get("item");//itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.

		JSONObject obj;

		//순회하면서 각각의 category를 가져옴
		for (int i = 0; i < parse_item.size(); i++) {
			obj = (JSONObject) parse_item.get(i); 
			Object obsrValue = obj.get("obsrValue");
			String category = (String) obj.get("category");
			
			if(i==0) {
				w1.setBaseDate((String)obj.get("baseDate"));
				w1.setBaseTime((String)obj.get("baseTime"));
			}
			
			switch (category) {
			case "REH": //습도
				w1.setReh(obsrValue.toString());
				break;
			case "T1H": //온도
				w1.setT1h(obsrValue.toString());
				break;
			case "RN1": //1시간 강수량 - 범주 (1 mm)
				w1.setRn1(obsrValue.toString());
				break;
			case "UUU": //동서바람성분 m/s
				w1.setUuu(obsrValue.toString());
				break;
			case "VVV": //납북바람성분 m/s
				w1.setVvv(obsrValue.toString());
				break;
			case "PTY": //강수형태 - 코드값
				w1.setPty(obsrValue.toString());
				break;
			case "VEC": //풍향 deg
				w1.setVec(obsrValue.toString());
				break;
			case "WSD": //풍속 m/s
				w1.setWsd(obsrValue.toString());
				break;
			}
		
			//각 카테고리별로 값을 보여줌
			System.out.print("category : " + category);
			System.out.print(", obsrValue : " + obsrValue);
			System.out.print(", obsrDate : " + w1.getBaseDate());
			System.out.println(", obsrTime : " + w1.getBaseTime());		

		}	
		return w1;
	}



}
