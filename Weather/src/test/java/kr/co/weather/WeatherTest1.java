package kr.co.weather;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.bytecode.opencsv.CSVReader;
import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.Grid;
import kr.co.weather.domain.Record;
import kr.co.weather.service.CsvService;
import kr.co.weather.service.WeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class WeatherTest1 {
	@Autowired
	private WeatherService service;

	//@Test
	public void translateWarning() {
		//상한은 본인 Record 테이블에 있는 데이터 개수를 사용하거나 그 이하의 작은 수를 넣어서 테스트
		for(int i=1; i<=34674; i++) {
			service.insertWarning(i);
		}
	}

	
	@Test
	public void testSearch() throws ParseException {
		//System.out.println(service.searchRecord(90));
		service.get_Tmp(90);
		service.get_rain_humid_snow(90);
	}
	
	
	/*
	@Autowired
	private Mapper mapper;

	//@Test
	public void testCalendar() {
		Record record = mapper.selectRecord(59597);
		Calendar cal = new GregorianCalendar();
		cal.setTime(record.getRecord_date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(cal.getTime()));
		cal.add(cal.DAY_OF_MONTH, +1);
		String date = sdf.format(cal.getTime());
		System.out.println(date);
		cal.add(cal.DAY_OF_MONTH, -2);
		date = new String(sdf.format(cal.getTime()));
		System.out.println(date);
		//System.out.println(record.getRecord_date().getMonth());
		//System.out.println(cal.MONTH);
		//System.out.println(record.toString());
	}

	@Autowired
	private CsvService csvService;

	@Test
	public void testZero() {
		String path = "C:\\lecture\\testfile\\160101-161231.csv";
		List<String []> list = new ArrayList<>();
		try {
			CSVReader cs = new CSVReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			list = cs.readAll();
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		//속성명 받아오기
		Map<String, Object> map  = new HashMap<>();
		String [] attr = list.get(0);
		int len = attr.length;
		for(int i=0; i<len; i++) {
			map.put(attr[i], i);
		}

		System.out.println(map.toString());
		Record record = new Record();
		String [] data = list.get(1);
		System.out.println(data[0]);
		System.out.println(map.get("﻿avgWs"));
		//평균 풍속
		double avg_windspeed = 0.0;
		try {
			System.out.println("avgWs 인덱스 : " + (int)map.get("﻿avgWs"));
			System.out.println(data[(int)map.get("﻿avgWs")]);
			avg_windspeed = Double.parseDouble(data[(int)map.get("avgWs")]);
			record.setAvg_windspeed(avg_windspeed);
		} catch (Exception e) {}

	}
	*/


}

