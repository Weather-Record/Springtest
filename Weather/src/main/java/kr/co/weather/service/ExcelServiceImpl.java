package kr.co.weather.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.weather.dao.Mapper;
import kr.co.weather.domain.Location;
import kr.co.weather.domain.Record;

@Service
public class ExcelServiceImpl implements ExcelService {
	@Autowired
	private Mapper mapper;

	@Override
	public String storeExcel(HttpServletRequest request, MultipartFile file) {
		//파일을 업로드 할 경로를 생성
		//프로젝트 내의 webapp 내의 upload 디렉터리의 절대 경로 생성
		String uploadPath = request.getServletContext().getRealPath("/excel/");

		//랜덤한 파일명 만들기
		//추후엔 userid를 함께 받아서 업로드한 사용자명과 올린 파일이름을 함께 알게 수정
		String filename = UUID.randomUUID() + file.getOriginalFilename();

		//전송할 파일 Path 만들기 -> 역슬래시 주의
		File filepath = new File( uploadPath + filename);

		try {
			file.transferTo(filepath);
			System.out.println("전송 성공");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return filename;
	}

	@Override
	@Transactional
	//location 데이터 업로드
	public boolean insertLocation(HttpServletRequest request, String filename) {
		boolean result = true;
		//excel 파일 경로 설정
		File file = new File(request.getServletContext().getRealPath("/excel/") + filename);
		//엑셀 파일 오픈
		HSSFWorkbook wb;
		try {
			Location location = new Location();
			wb = new HSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			//속성명 받아오기
			Row tmprow = sheet.getRow(0);
			Map<String, Object> map  = new HashMap<>();
			for(int i=0; i<3; i++) {
				map.put(tmprow.getCell(i).getStringCellValue(), i);
			}

			int num = sheet.getPhysicalNumberOfRows();
			for (int i=1; i<num; i++) {
				Row row = sheet.getRow(i);
				//숫자의 경우 없는 경우도 있으므로 일단 0을 넣어놓고 확인
				int location_id = 0;
				try {
					location_id = (int)row.getCell((Integer)map.get("지점")).getNumericCellValue();
					location.setLocation_id(location_id);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}

				//문자열
				String location_name = "";
				try {
					location_name = row.getCell((Integer)map.get("지점명")).getStringCellValue();
					location.setLocation_name(location_name);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}

				String location_state = "";
				try {
					location_state = row.getCell((Integer)map.get("도")).getStringCellValue();
					location.setLocation_state(location_state);
				}catch(Exception e) {
					//System.out.println(e.getLocalizedMessage());
				}
				mapper.insertLocation(location);
			}

		} catch (Exception e) {
			result=false;
			System.out.println(e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	@Transactional
	//record 데이터 업로드
	public boolean insertRecord(HttpServletRequest request, String filename) {
		boolean result = false;
		//excel 파일 경로 설정
		File file = new File(request.getServletContext().getRealPath("/excel/") + filename);
		//엑셀 파일 오픈
		HSSFWorkbook wb;
		try {
			wb = new HSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			//속성명 받아오기
			Row tmprow = sheet.getRow(0);
			Map<String, Object> map  = new HashMap<>();
			for(int i=0; i<13; i++) {
				map.put(tmprow.getCell(i).getStringCellValue(), i);
			}
			//실제 데이터는 1번째 row부터 시작됨
			int num = sheet.getPhysicalNumberOfRows();
			for (int i=1; i<num; i++) {
				Record record = new Record();
				Row row = sheet.getRow(i);

				//지점번호
				int location_id = 0;
				try {
					location_id = (int)row.getCell((Integer)map.get("stnId")).getNumericCellValue();
					record.setLocation_id(location_id); 
				}catch(Exception e) {}

				//시간
				Date record_date = null;
				try {
					record_date =  row.getCell((Integer)map.get("tm")).getDateCellValue();
					record_date.setDate(record_date.getDate()+1);
					record.setRecord_date(record_date); //시간
				} catch (Exception e) {}

				//평균기온
				double avg_tmp = 0.0;
				try {
					avg_tmp = row.getCell((Integer)map.get("avgTa")).getNumericCellValue();
					record.setAvg_tmp(avg_tmp);
				} catch (Exception e) {}

				//최저기온
				double min_tmp = 0.0;
				try {
					min_tmp = row.getCell((Integer)map.get("minTa")).getNumericCellValue();
					record.setMin_tmp(min_tmp);
				} catch (Exception e) {}

				//최고기온
				double max_tmp = 0.0;
				try {
					max_tmp = row.getCell((Integer)map.get("maxTa")).getNumericCellValue();
					record.setMax_tmp(max_tmp); //최고기온
				} catch (Exception e) {}

				//강수 계속시간
				double rain_hours = 0.0;
				try {
					rain_hours = row.getCell((Integer)map.get("sumRnDur")).getNumericCellValue();
					record.setRain_hours(rain_hours);
				} catch (Exception e) {}

				//일강수량
				double day_rain = 0.0;
				try {
					day_rain = row.getCell((Integer)map.get("sumRn")).getNumericCellValue();
					record.setDay_rain(day_rain);
				} catch (Exception e) {}

				//최대 순간풍속
				double max_insta_windspeed = 0.0;
				try {
					max_insta_windspeed = row.getCell((Integer)map.get("maxInsWs")).getNumericCellValue();
					record.setMax_insta_windspeed(max_insta_windspeed);
				} catch (Exception e) {}

				//최대 풍속
				double max_windspeed = 0.0;
				try {
					max_windspeed = row.getCell((Integer)map.get("maxWs")).getNumericCellValue();
					record.setMax_windspeed(max_windspeed);
				} catch (Exception e) {}

				//평균 풍속
				double avg_windspeed = 0.0;
				try {
					avg_windspeed = row.getCell((Integer)map.get("avgWs")).getNumericCellValue();
					record.setAvg_windspeed(avg_windspeed);
				} catch (Exception e) {}

				//평균 상대습도
				double avg_humid = 0.0;
				try {
					avg_humid = row.getCell((Integer)map.get("avgRhm")).getNumericCellValue();
					record.setAvg_humid(avg_humid);
				} catch (Exception e) {}

				//일 최심신적설
				double day_snow = 0.0;
				try {
					day_snow = row.getCell((Integer)map.get("ddMefs")).getNumericCellValue();
					record.setDay_snow(day_snow);
				} catch (Exception e) {}

				double accumul_snow = 0.0;
				try {
					accumul_snow = row.getCell((Integer)map.get("ddMes")).getNumericCellValue();
					record.setAccumul_snow(accumul_snow); //일 최심적설
				} catch (Exception e) {}

				mapper.insertRecord(record);
				System.out.println(i+" 번 데이터 삽입 성공");
			}
			result=true;
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return result;
	}

}
