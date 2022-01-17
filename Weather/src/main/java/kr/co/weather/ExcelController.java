package kr.co.weather;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.weather.service.ExcelService;

@Controller
public class ExcelController {
	@Autowired
	private ExcelService eservice;

	//location 업로드 폼
	@GetMapping("/uploadlocation")
	public String uploadlocationform() {
		return "/admin/uploadlocationform";
	}
	
	//실제 location 업로드 처리 후 원래 페이지로 이동
	@PostMapping(value = "/uploadlocation")
	public String uploadlocation(MultipartHttpServletRequest request, HttpSession session) {
		MultipartFile excel = request.getFile("excel");
		String filename = eservice.storeExcel(request, excel);
		//true이면 DB에 삽입 성공, false이면 DB에 삽입 실패 -> 성공하면 메인 페이지 실패하면,?
		boolean result = eservice.insertLocation(request, filename);
		session.setAttribute("loc_result", result);
		//리다이렉트로 바꾸기
		return "redirect:/";
	}
	
	//Record 업로드 폼
	@GetMapping("/uploadrecord")
	public String uploadrecordform() {
		return "/admin/uploadrecordform";
	}
	
	//실제 record 업로드 처리 후 원래 페이지로 이동
	@PostMapping("/uploadrecord")
	public String uploadrecord(MultipartHttpServletRequest request, HttpSession session) {
		MultipartFile excel = request.getFile("excel");
		String filename = eservice.storeExcel(request, excel);
		boolean result = eservice.insertRecord(request, filename);
		session.setAttribute("record_result", result);
		return "redirect:/";
	}
	
}
