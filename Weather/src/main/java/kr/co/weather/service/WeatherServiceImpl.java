package kr.co.weather.service;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

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
import kr.co.weather.domain.*;

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

	@Transactional
	@Override //날짜와 지점으로 수정해야 하는 부분 존재(건조) -> 현재는 테스트 용
	public void insertWarning(int record_id) {
		Record record = mapper.selectRecord(record_id);
		int loc = record.getLocation_id();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = new GregorianCalendar();
		cal.setTime(record.getRecord_date());
		cal.add(cal.DAY_OF_MONTH, 1);
		String date = sdf.format(cal.getTime()); //기억하기
		Record nextrecord = new Record();
		//일단 빈 record는 현 레코드와 같은 데이터값으로 처리,,생각해봐야 할 듯
		try {
			nextrecord = mapper.recordLocDate(loc, date).get(0); //데이터 중복 고려
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			nextrecord = record;
		}
		cal.add(cal.DAY_OF_MONTH, -2);
		date = new String(sdf.format(cal.getTime()));
		Record prevrecord = new Record();
		try {
			prevrecord = mapper.recordLocDate(loc, date).get(0); //데이터 중복 고려
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			prevrecord = record;
		}
		//System.out.println(nextrecord.toString());
		Warning warn = new Warning();
		warn.setRecord_id(record_id);
		
		//강풍
		if(record.getMax_insta_windspeed() >= 20 || record.getMax_windspeed() >= 14) {
			if(record.getMax_insta_windspeed() >= 26 || record.getMax_windspeed() >= 21) {
				warn.setAlert_wind(true); //경보
			}else {
				warn.setWarn_wind(true); //주의보
			}
		}
		
		//호우
		if(record.getDay_rain()/8 >= 60) {
			if(record.getDay_rain()/8 >= 90) {
				warn.setAlert_rain(true); //경보
			}else {
				warn.setWarn_rain(true); //주의보
			}
		}

		//대설
		if(record.getDay_snow() >= 5) {
			if(record.getDay_snow() >= 20) {
				warn.setAlert_snow(true); //경보
			}else {
				warn.setWarn_snow(true); //주의보
			}
		}

		//건조 -> 수정 필요 (Mapper 인터페이스에 SQL문 추가)
		if(record.getAvg_humid() <= 35 && nextrecord.getAvg_humid() <=35) {
			if(record.getAvg_humid() <= 25 && nextrecord.getAvg_humid() <=25) {
				warn.setAlert_dry(true); //경보
			}else {
				warn.setWarn_dry(true); //주의보
			}
		}

		//한파(10~4월) -> 수정 -> 기준 생략했음
		sdf = new SimpleDateFormat("MM");
		int month = record.getRecord_date().getMonth()+1; //1월==0부터 시작
		//System.out.println("month = "+ month);
		if((month >= 10 || month <= 4) && record.getMin_tmp()<=-12 && nextrecord.getMin_tmp()<=-12) {
			if(record.getMin_tmp()<=-15 && nextrecord.getMin_tmp()<=-15) {
				warn.setAlert_cold(true); //경보
			}else {
				warn.setWarn_cold(true); //주의보
			}
		}
		if(record.getMin_tmp()<=3 && prevrecord.getMin_tmp()-record.getMin_tmp()>=10) {
			if(prevrecord.getMin_tmp()-record.getMin_tmp()>=15) {
				warn.setAlert_cold(true);
			}else {
				warn.setWarn_cold(true);
			}
		}

		//폭염
		if(record.getMax_tmp() >= 33 && nextrecord.getMax_tmp() >= 33) {
			if(record.getMax_tmp() >= 35 && nextrecord.getMax_tmp() >= 35) {
				warn.setAlert_hot(true); //경보
			}else {
				warn.setWarn_hot(true); //주의보
			}
		}
		
		int result = mapper.insertWarning(warn);
		//System.out.println("결과 : " + result);
		System.out.println("record_id : " + record_id + "\n");

	}

	@Transactional
	@Override
	public List<Record> searchRecord(Integer location_id) {
		//List<Record> list = mapper.searchRecord(location_id);
		return mapper.searchRecord(location_id);
	}

	///////////////////////////// Data processing functions ///////////////////////////
	@Autowired
	private WeatherService service;
	
	public void get_Tmp(Integer location_id){
		List<Record> list = service.searchRecord(location_id);
		List<Integer> loc_list = new ArrayList<Integer>(list.size());
		List<Date> date_list = new ArrayList<Date>(list.size());
		List<Double> avgTmp_list = new ArrayList<Double>(list.size());
		List<Double> minTmp_list = new ArrayList<Double>(list.size());
		List<Double> maxTmp_list = new ArrayList<Double>(list.size());
		List<Double> rainHr_list = new ArrayList<Double>(list.size());
		List<Double> dayRain_list = new ArrayList<Double>(list.size());
		List<Double> avgHumid_list = new ArrayList<Double>(list.size());
		List<Double> daySnow_list = new ArrayList<Double>(list.size());

		for(Record record:list) {
			loc_list.add(record.getLocation_id());
			date_list.add(record.getRecord_date());
			avgTmp_list.add(record.getAvg_tmp());
			minTmp_list.add(record.getMin_tmp());
			maxTmp_list.add(record.getMax_tmp());
			rainHr_list.add(record.getRain_hours());
			dayRain_list.add(record.getDay_rain());
			avgHumid_list.add(record.getAvg_humid());
			daySnow_list.add(record.getDay_snow());
			}

		//Find overall Avg of temp
		Double sum_avgtmp = 0.0;
		Double avg_tmp = 0.0;
		
		for(Double tmp:avgTmp_list) {
			sum_avgtmp += tmp;
		}
		
		//round up to 1st decimal 
		avg_tmp = (double) Math.round((sum_avgtmp / avgTmp_list.size())*10)/10;
		
		//Find min && max temp
		Double min_tmp = minTmp_list.stream().mapToDouble(v->v).min().orElseThrow(NoSuchElementException::new);
		Double max_tmp = maxTmp_list.stream().mapToDouble(v->v).max().orElseThrow(NoSuchElementException::new);

		System.out.println("AVG:" +avgTmp_list);
		System.out.println("Min:" +minTmp_list);
		System.out.println("Max:" +maxTmp_list);
		
		System.out.println(avg_tmp);
		System.out.println(min_tmp);
		System.out.println(max_tmp);
	}

	public void get_rain_humid_snow(Integer location_id){
		List<Record> list = service.searchRecord(location_id);

		List<Double> rainHr_list = new ArrayList<Double>(list.size());
		List<Double> dayRain_list = new ArrayList<Double>(list.size());
		List<Double> avgHumid_list = new ArrayList<Double>(list.size());
		List<Double> daySnow_list = new ArrayList<Double>(list.size());

		for(Record record:list) {
			rainHr_list.add(record.getRain_hours());
			dayRain_list.add(record.getDay_rain());
			avgHumid_list.add(record.getAvg_humid());
			daySnow_list.add(record.getDay_snow());
			}
		
		//Find overall avg of rain drop per hour
		Double sum_rainHr = 0.0;
		Double sum_rain = 0.0;
		
		for(Double hour:rainHr_list) {
			sum_rainHr += hour;
		}
		
		for(Double drop:dayRain_list) {
			sum_rain += drop;
		}
		
		//get avg of rain per hour and round up to 1st decimal 
		//id 90 + 5월 데이터에 강수량 계측은 있지만, 강수시간에 대한 데이터가 없다는 것이 확인됌.
		Double avg_rain = (double) Math.round((sum_rain / sum_rainHr)*10)/10;
							
		//Find overall avg of humid
		Double sum_humid=0.0;
		for(Double humid :avgHumid_list) {
			sum_humid += humid;
		}
		Double avg_humid = (double) Math.round((sum_humid / avgHumid_list.size())*10)/10;
		
		//Find overall avg of snow per hour
		Double sum_snow = 0.0;
		
		for(Double flake:daySnow_list) {
			sum_snow += flake;
		}
		//get avg of rain per hour and round up to 1st decimal
		//since snowing hours shares data with rain hour, used sum_rainHr
		Double avg_snow = (double) Math.round((sum_snow / sum_rainHr)*10)/10;
		
		
		System.out.println("HR:" +rainHr_list);
		System.out.println("Rain:" +dayRain_list);
		System.out.println("Humid:"+avgHumid_list);
		System.out.println("Snow:" +daySnow_list);
		System.out.println("avg rain::"+avg_rain);
		System.out.println("avg humid:"+avg_humid);
		System.out.println("avg snow:"+avg_snow);
	}

}
