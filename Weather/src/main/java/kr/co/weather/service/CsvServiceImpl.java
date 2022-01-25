package kr.co.weather.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;
import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.Record;

@Service
public class CsvServiceImpl implements CsvService{
	@Autowired
	private Mapper mapper;
	
	@Override
	public String getRealPath(HttpServletRequest request, MultipartFile file) {
		//파일을 업로드 할 경로를 생성
		//프로젝트 내의 webapp 내의 upload 디렉터리의 절대 경로 생성
		String uploadPath = request.getServletContext().getRealPath("/csv/");

		//랜덤한 파일명 만들기
		//추후엔 userid를 함께 받아서 업로드한 사용자명과 올린 파일이름을 함께 알게 수정
		String filename = UUID.randomUUID() + file.getOriginalFilename();

		//전송할 파일 Path 만들기 -> 역슬래시 주의
		String path = uploadPath+filename;
		File filepath = new File(path);
		try {
			file.transferTo(filepath);
			System.out.println("전송 성공");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return path;
	}

	@Override
	public void insertRecord(String path) {
		//csv 파일 읽어오기
		List<String []> list = new ArrayList<>();
		try {
			CSVReader cs = new CSVReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			list = cs.readAll();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		//db에 csv 파일 넣기
		//속성명 받아오기
		Map<String, Integer> map  = new HashMap<>();
		String [] attr = list.get(0);
		int len = attr.length;
		for(int i=0; i<len; i++) {
			map.put(attr[i], i);
		}
		//System.out.println(map.toString());
		//데이터 매핑 -> 첫 번째 줄은 속성명
		for (int i=1; i<list.size(); i++) {
			Record record = new Record();
			String [] data = list.get(i);
			//지점번호 -> 어차피 순서 아니깐 번호로 그냥 수정해도 될 듯 
			int location_id = 0;
			try {
				location_id = Integer.parseInt(data[map.get("stnId")]);
				record.setLocation_id(location_id); 
			}catch(Exception e) {}

			//시간
			Date record_date = null;
			try {
				record_date =  new Date(data[map.get("tm")]);
				record_date.setDate(record_date.getDate()+1);
				record.setRecord_date(record_date); 
			} catch (Exception e) {}

			//평균기온
			double avg_tmp = 0.0;
			try {
				avg_tmp = Double.parseDouble(data[map.get("avgTa")]);
				record.setAvg_tmp(avg_tmp);
			} catch (Exception e) {}

			//최저기온
			double min_tmp = 0.0;
			try {
				min_tmp = Double.parseDouble(data[map.get("minTa")]);
				record.setMin_tmp(min_tmp);
			} catch (Exception e) {}

			//최고기온
			double max_tmp = 0.0;
			try {
				max_tmp = Double.parseDouble(data[map.get("maxTa")]);
				record.setMax_tmp(max_tmp);
			} catch (Exception e) {}

			//강수 계속시간
			double rain_hours = 0.0;
			try {
				rain_hours = Double.parseDouble(data[map.get("sumRnDur")]);
				record.setRain_hours(rain_hours);
			} catch (Exception e) {}

			//일강수량
			double day_rain = 0.0;
			try {
				day_rain = Double.parseDouble(data[map.get("sumRn")]);
				record.setDay_rain(day_rain);
			} catch (Exception e) {}

			//최대 순간풍속
			double max_insta_windspeed = 0.0;
			try {
				max_insta_windspeed = Double.parseDouble(data[map.get("maxInsWs")]);
				record.setMax_insta_windspeed(max_insta_windspeed);
			} catch (Exception e) {}

			//최대 풍속
			double max_windspeed = 0.0;
			try {
				max_windspeed = Double.parseDouble(data[map.get("maxWs")]);
				record.setMax_windspeed(max_windspeed);
			} catch (Exception e) {}

			//평균 풍속 -> 인식이 잘 안 됨
			double avg_windspeed = 0.0;
			try {
				//avg_windspeed = Double.parseDouble(data[map.get("﻿﻿﻿avgWs")]);
				avg_windspeed = Double.parseDouble(data[0]);
				record.setAvg_windspeed(avg_windspeed);
			} catch (Exception e) {}

			//평균 상대습도
			double avg_humid = 0.0;
			try {
				avg_humid = Double.parseDouble(data[map.get("avgRhm")]);
				record.setAvg_humid(avg_humid);
			} catch (Exception e) {}

			//일 최심신적설
			double day_snow = 0.0;
			try {
				day_snow = Double.parseDouble(data[map.get("ddMefs")]);
				record.setDay_snow(day_snow);
			} catch (Exception e) {}

			//일 최심적설
			double accumul_snow = 0.0;
			try {
				accumul_snow = Double.parseDouble(data[map.get("ddMes")]);
				record.setAccumul_snow(accumul_snow);
			} catch (Exception e) {}

			mapper.insertRecordCsv(record);
			System.out.println(i+" 번 데이터 삽입 성공");
		}

	}

}
