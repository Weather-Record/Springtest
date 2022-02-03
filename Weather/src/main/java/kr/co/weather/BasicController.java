package kr.co.weather;

import java.util.Map;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import kr.co.weather.service.BasicService;

@Controller
public class BasicController {
	@Autowired
	private BasicService basicService;
	
	//////////////// View Control
	//메인 페이지
	@GetMapping("/")
	public String home() {
		return "home";
	}

	//관리자 페이지 -> 로그인 기능 구현 후 수정
	@GetMapping("/adminpage")
	public String adminpage() {
		return "adminpage";
	}

	@GetMapping("/userpage")
	public String userpage() {
		return "userpage";
	}

	//로그인, 로그아웃, 회원가입 처리
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	//////////////// Data Control
	@PostMapping("/signup")
	public Map<String, Object> insertMember(HttpServletRequest request, HttpServletResponse response){
		return basicService.insertMember(request, response);
	}
	
	@GetMapping("/idcheck")
	public Map<String, Object> idCheck(HttpServletRequest request, HttpServletResponse response){
		return basicService.idCheck(request, response);
	} 
	
	@GetMapping("/emailcheck")
	public Map<String, Object> emailCheck(HttpServletRequest request, HttpServletResponse response){
		return basicService.emailCheck(request, response);
	} 

	@GetMapping("/nicknamecheck")
	public Map<String, Object> nicknameCheck(HttpServletRequest request, HttpServletResponse response){
		return basicService.nicknameCheck(request, response);
	} 

}
