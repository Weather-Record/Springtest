package kr.co.weather.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
	//유저가 업로드한 xls 파일 저장 및 해당 엑셀 파일명을 리턴하기 위한 메서드
	public String storeExcel(HttpServletRequest request, MultipartFile file);
	
	//location 테이블에 자료를 삽입하기 위한 메서드
	public boolean insertLocation(HttpServletRequest request, String filename);
		
	//record 테이블에 자료를 삽입하기 위한 메서드
	public boolean insertRecord(HttpServletRequest request, String filename);
}
