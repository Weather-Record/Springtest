package kr.co.weather;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.weather.domain.Weather;
import kr.co.weather.service.WeatherService;

@Controller
public class HomeController {
	
	@Autowired
	private WeatherService wservice;
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	@GetMapping("/uploadlocation")
	public String fileuploadform() {
		return "uploadlocationform";
	}
	
	@RequestMapping(value = "uploadlocation.action", method = RequestMethod.POST)
	public String fileupload(MultipartHttpServletRequest request, Model model) {
		MultipartFile excel = request.getFile("excel");
		
		//파일을 업로드 할 경로를 생성
		//프로젝트 내의 webapp 내의 upload 디렉터리의 절대 경로 생성
		String uploadPath=
				request.getServletContext().getRealPath("/excel");
		
		//랜덤한 파일명 만들기
		//추후엔 userid를 함께 받아서 업로드한 사용자명과 올린 파일이름을 함께 알게 해주면 좋을듯
		String filename = UUID.randomUUID() + excel.getOriginalFilename();
		
		//전송할 파일 Path 만들기 -> 역슬래시 주의
		File filepath = new File( uploadPath + "\\" + filename);
		//System.out.println(uploadPath+"\"+filename);
		try {
			excel.transferTo(filepath);
			System.out.println("전송 성공");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		//true이면 DB에 삽입 성공, false이면 DB에 삽입 실패 -> 성공하면 메인 페이지 실패하면,?
		boolean result = wservice.insertLocation(request, filename);
		model.addAttribute("result", result);
		//리다이렉트로 바꾸기
		return "redirect:/";
	}
	
	//초단기 실황 -> 1일 이내만 가능
	@GetMapping("/getultrasrtncst")
	public String getultrasrtncst() {
		return "/api/getultrasrtncstform";
	}
	
	@PostMapping("/getultrasrtncst")
	public String getultrasrtncst(@RequestParam("date") String date, @RequestParam("time") String time, Model model) throws IOException, ParseException {
		Weather list = wservice.getultrasrtncst(date, time);
		model.addAttribute("list", list);
		return "/api/getultrasrtncst";
	}	
	
	/*
	@GetMapping("/getwarning") //아직 안 넣음
	public String getwarning() {
		wservice.getWarning();
		//return "getwarning";
		return "redirect:/";
	}
	*/
}
