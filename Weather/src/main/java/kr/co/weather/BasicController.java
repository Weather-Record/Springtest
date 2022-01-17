package kr.co.weather;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {
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


}
