package kr.co.weather;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.weather.service.CsvService;

@Controller
public class CsvController {
	@Autowired
	private CsvService csvService;

	//Record 업로드 폼
	@GetMapping("/uploadcsvrecord")
	public String uploadrecordform() {
		return "/admin/uploadcsvrecordform";
	}

	//실제 record 업로드 처리 후 원래 페이지로 이동
	@PostMapping("/uploadcsvrecord")
	public String uploadrecord(MultipartHttpServletRequest request, HttpSession session) {
		boolean result = false;
		MultipartFile csv = request.getFile("csv");
		String path = csvService.getRealPath(request, csv);
		csvService.insertRecord(path);
		//session.setAttribute("record_result", result);
		return "redirect:/";
	}
}
