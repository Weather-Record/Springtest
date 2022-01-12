package kr.co.weather.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.weather.domain.Grid;
import kr.co.weather.service.WeatherService;

@Component
public class BasicInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private WeatherService wservice;
	
	@Override
	//Controller에게 요청을 하기 전에 호출되는 메서드 -> 최초 1회만 수행되도록 수정해야할 듯 -> static?
	//true를 리턴하면 Controller에게 요청 처리 메서드를 호출하고
	//false를 리턴하면 Controller의 요청 처리 메서드를 호출하지 않음
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		//작업
		List<Grid> gridlist = wservice.selectGrid();
		List<String> templist = wservice.getTemperature();
		request.setAttribute("gridlist", gridlist);
		request.setAttribute("templist", templist);
		return true;
	}
}
