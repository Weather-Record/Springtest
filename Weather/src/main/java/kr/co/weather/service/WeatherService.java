package kr.co.weather.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;

import kr.co.weather.domain.Grid;
import kr.co.weather.domain.Warning;
import kr.co.weather.domain.Weather;

public interface WeatherService {
	//Grid 테이블의 데이터 불러오기
	public List<Grid> selectGrid();

	//api로 특보 데이터 불러오기
	public List<Warning> getWarning();

	//api로 초단기 현황 불러오기 -> 현재 위치는 종로구로 고정되어 있음 수정 가능
	public Weather getultrasrtncst(String grid_x, String grid_y) throws IOException, ParseException;

}
