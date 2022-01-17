package kr.co.weather;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.weather.domain.Weather;
import kr.co.weather.service.WeatherService;

@Controller
public class WeatherController {
	
	@Autowired
	private WeatherService wservice;
	
	//초단기 실황 -> 1일 이내만 가능
	@GetMapping("/getultrasrtncst")
	public String getultrasrtncst() {
		return "/api/getultrasrtncstform";
	}
	
	@PostMapping("/getultrasrtncst")
	public String getultrasrtncst(HttpServletRequest request, Model model) throws IOException, ParseException {
		//String date = request.getParameter("date");
		//String time = request.getParameter("time");
		String nx = request.getParameter("nx");
		String ny = request.getParameter("ny");
		//추후 지역 전체 데이터 테이블 생성하고 거기서 불러오도록 수정
		Weather list = wservice.getultrasrtncst(nx, ny);
		model.addAttribute("list", list);
		return "/api/getultrasrtncst";
	}	
	
	
}
