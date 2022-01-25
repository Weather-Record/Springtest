package kr.co.weather.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.weather.domain.Grid;
import kr.co.weather.domain.Location;
import kr.co.weather.domain.Member;
import kr.co.weather.domain.Record;
import kr.co.weather.domain.Warning;

@Repository
public interface Mapper {
	//Location 테이블에 데이터 삽입하기
	@Insert("insert into Location values(#{location_id}, #{location_name}, #{location_state})")
	public int insertLocation(Location location);
	
	//Member 테이블에 회원정보 삽입하기
	@Insert("insert into Member(member_id, member_pw, member_email, nickname) values (#{member_id}, #{member_pw}, #{member_email}, #{nickname})")
	public int insertMember(Member member);
	
	//Record 테이블에 날씨 정보 삽입하기
	@Insert("insert into Record(location_id, record_date, avg_tmp, min_tmp, max_tmp, rain_hours, day_rain, max_insta_windspeed, max_windspeed, avg_windspeed, avg_humid, day_snow, accumul_snow) "
			+ "values(#{location_id}, #{record_date}, #{avg_tmp}, #{min_tmp}, #{max_tmp}, #{rain_hours}, #{day_rain}, #{max_insta_windspeed}, #{max_windspeed}, #{avg_windspeed}, #{avg_humid}, #{day_snow}, #{accumul_snow})")
	public int insertRecord(Record record);
	
	//Record 테이블 데이터 불러오기
	@Select("select * from Record where record_id=#{record_id}")
	public Record selectRecord(int record_id);
	
	//위치와 date 기준으로 record 데이터 불러오기 -> 데이터 중복으로 list로 구현. 필요시 selectOne으로 된 메서드 하나 만들어도 됨
	@Select("select * from Record where location_id=#{loc} and record_date=#{date}")
	public List<Record> recordLocDate(@Param("loc") int loc, @Param("date") String date);
	
	//Grid 테이블에서 격자 데이터 불러오기
	@Select("select grid_cityname, grid_x, grid_y, lat, lon from Grid")
	public List<Grid> selectGrid();
	
	//Warning 테이블에 특보 데이터 삽입하기
	@Insert("insert into Warning(record_id, alert_wind, alert_rain, alert_snow, alert_cold, alert_hot, alert_dry, warn_wind, warn_rain, warn_snow, warn_cold, warn_hot, warn_dry) "
			+ "values(#{record_id}, #{alert_wind}, #{alert_rain}, #{alert_snow}, #{alert_cold}, #{alert_hot}, #{alert_dry}, #{warn_wind}, #{warn_rain}, #{warn_snow}, #{warn_cold}, #{warn_hot}, #{warn_dry})")
	public int insertWarning(Warning warning);
	
	
	//Record 테이블에 날씨 정보 삽입하기_csv
	@Insert("insert into Record(location_id, record_date, avg_tmp, min_tmp, max_tmp, rain_hours, day_rain, max_insta_windspeed, max_windspeed, avg_windspeed, avg_humid, day_snow, accumul_snow) "
			+ "values(#{location_id}, #{record_date}, #{avg_tmp}, #{min_tmp}, #{max_tmp}, #{rain_hours}, #{day_rain}, #{max_insta_windspeed}, #{max_windspeed}, #{avg_windspeed}, #{avg_humid}, #{day_snow}, #{accumul_snow})")
	public int insertRecordCsv(Record record);
}
