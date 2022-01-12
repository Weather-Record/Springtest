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

	@Override //초단기실황 -> 온도만 출력
	public List<String> getTemperature() {
		List<String> temp = new ArrayList<String>();
		List<Grid> list = mapper.selectGrid();
		Date sysdate = new Date();
		DateFormat datesdf = new SimpleDateFormat("yyyyMMdd");
		DateFormat timesdf = new SimpleDateFormat("kkmm");
		String date = datesdf.format(sysdate);
		String time = timesdf.format(sysdate);
		System.out.println("일시 : "+date +" 시간 : "+time);
		//JSON 데이터를 요청하는 URL 문자열을 작성

		String apiurl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
		String serviceKey="vr5hSgL05Y3LUdkM9%2FNzpSNCNwrzpoM8TRKqpgfv5biKw0xTeqOTKiV8TbDOJXnYRlFYzpTsPY0kDwg%2BDloRIA%3D%3D";

		try {
			for(int j=0; j<list.size(); j++) {
				String nx = list.get(j).getGrid_x()+"";
				String ny = list.get(j).getGrid_y()+"";

				StringBuilder urlBuilder = new StringBuilder(apiurl); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
				urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /*‘21년 6월 28일 발표*/
				urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*06시 발표(정시단위) */
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
				// items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.
				JSONArray parse_item = (JSONArray) parse_items.get("item");

				JSONObject obj;

				//순회하면서 각각의 category를 가져옴
				for (int i = 0; i < parse_item.size(); i++) {
					obj = (JSONObject) parse_item.get(i); // 해당 item을 가져옵니다.

					Object fcstValue = obj.get("obsrValue"); //카테고리에 해당하는 값
					Object fcstDate = obj.get("baseDate");
					Object fcstTime = obj.get("baseTime");
					Object category = obj.get("category");

					if(category.toString().equals("T1H")) { //온도
						temp.add(fcstValue.toString());
						break;
					}	
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		} 

		System.out.println("온도 : " + temp);

		return temp;
	}

	@Override
	@Transactional
	public boolean insertLocation(HttpServletRequest request, String filename) {
		boolean result = true;
		//excel 파일 경로 설정
		File file = new File(request.getServletContext().getRealPath("/excel/") + filename);
		//엑셀 파일 오픈
		HSSFWorkbook wb;
		try {
			Location location = new Location();
			wb = new HSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			//속성명 받아오기
			Row tmprow = sheet.getRow(0);
			Map<String, Object> map  = new HashMap<>();
			map.put(tmprow.getCell(0).getStringCellValue(), 0);
			map.put(tmprow.getCell(1).getStringCellValue(), 1);
			map.put(tmprow.getCell(2).getStringCellValue(), 2);

			int num = sheet.getPhysicalNumberOfRows();
			for (int i=1; i<num; i++) {
				Row row = sheet.getRow(i);
				//숫자의 경우 없는 경우도 있으므로 일단 0을 넣어놓고 확인
				int location_id = 0;
				try {
					location_id = (int)row.getCell((Integer)map.get("지점")).getNumericCellValue();
					location.setLocation_id(location_id);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}

				//문자열
				String location_name = "";
				try {
					location_name = row.getCell((Integer)map.get("지점명")).getStringCellValue();
					location.setLocation_name(location_name);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}

				String location_state = "";
				try {
					location_state = row.getCell((Integer)map.get("도")).getStringCellValue();
					location.setLocation_state(location_state);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}
				mapper.insertLocation(location);
			}

		} catch (Exception e) {
			result=false;
			System.out.println(e.getLocalizedMessage());
		}
		return result;
	}

	//초단기 실황
	@Override
	public Weather getultrasrtncst(String querydate, String querytime) throws IOException, ParseException {
		Weather w1 = new Weather();

		String tempDate = querydate;
		String tempTime = querytime;

		//JSON 데이터를 요청하는 URL 문자열을 작성
		//-> 사용하는 기능에 따라 마지막 / 이후가 다름 -> 현재는 초단기 실황 조회
		String apiurl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
		//홈페이지에서 받은 키
		String serviceKey="vr5hSgL05Y3LUdkM9%2FNzpSNCNwrzpoM8TRKqpgfv5biKw0xTeqOTKiV8TbDOJXnYRlFYzpTsPY0kDwg%2BDloRIA%3D%3D";
		String pageNo ="1";
		String numOfRows = "10"; // 한 페이지 결과 수
		String data_type = "JSON"; // 타입 xml, json 등등 ..
		String baseDate = tempDate; // 조회하고싶은 날짜 이 예제는 어제 날짜 입력해 주면 됨
		String baseTime = tempTime; // API 제공 시간을 입력하면 됨
		String nx = "60"; // 위도
		String ny = "127"; // 경도

		//전날 23시 부터 153개의 데이터를 조회하면 오늘과 내일의 날씨를 알 수 있음
		StringBuilder urlBuilder = new StringBuilder(apiurl); /*URL*/
		//serviceKey는 이미 인코딩죔 값임
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
		// items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.
		JSONArray parse_item = (JSONArray) parse_items.get("item");

		JSONObject obj;
		String category; // 기준 날짜와 기준시간을 VillageWeather 객체에 저장합니다.

		String day = "";
		String time = "";

		//순회하면서 각각의 category를 가져옴
		for (int i = 0; i < parse_item.size(); i++) {
			obj = (JSONObject) parse_item.get(i); // 해당 item을 가져옵니다.

			Object fcstValue = obj.get("obsrValue");
			Object fcstDate = obj.get("baseDate");
			Object fcstTime = obj.get("baseTime");
			// item에서 카테고리를 검색해옵니다.
			category = (String) obj.get("category");
			// 검색한 카테고리와 일치하는 변수에 문자형으로 데이터를 저장합니다.
			// 데이터들이 형태가 달라 문자열로 통일해야 편합니다. 꺼내서 사용할때 다시변환하는게 좋습니다.

			switch (category) {
			case "REH": //습도
				w1.setReh((obj.get("obsrValue")).toString());
				break;
			case "T1H": //온도
				w1.setT1h((obj.get("obsrValue")).toString());
				break;
			}
			if (!day.equals(fcstDate.toString())) {
				day = fcstDate.toString();
			}
			if (!time.equals(fcstTime.toString())) {
				time = fcstTime.toString();
				//day와 time이 공란일 때 최초 1회만 수행됨
				System.out.println("날짜 : "+ day + "  " +"시간 : "+ time);
				//삽입 시간
				w1.setBaseDate((obj.get("baseDate")).toString());
				w1.setBaseTime((obj.get("baseTime")).toString());
			}		

			//각 카테고리별로 값을 보여줌
			System.out.print("category : " + category);
			System.out.print(", fcst_Value : " + fcstValue);
			System.out.print(", fcstDate : " + w1.getBaseDate());
			System.out.print(", fcstTime : " + w1.getBaseTime());		

			//Reh가 먼저 조회되고 난 뒤 T1h가 조회되므로 category가 T1h이면 필요한 값들은 이미 다 찾아온 셈 -> list에 삽입 
			if(category.equals("REH")) {
				System.out.println(" : 습도");
			}else if(category.equals("T1H")) {
				System.out.println(" : 온도");
			}else {
				System.out.println(" : 기타");
			}
		}	
		return w1;
	}


}
