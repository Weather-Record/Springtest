package kr.co.weather.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface CsvService {
	//디렉터리에 업로드 한 파일 저장하고 파일 절대 경로 가져오기
	public String getRealPath(HttpServletRequest request, MultipartFile file);
	
	//Record 데이터 삽입
	public void insertRecord(String path);
}
