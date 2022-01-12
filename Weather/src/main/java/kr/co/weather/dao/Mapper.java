package kr.co.weather.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.weather.domain.Grid;
import kr.co.weather.domain.Location;
import kr.co.weather.domain.Member;
import kr.co.weather.domain.Record;

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
			+ "values(#{location_id}, #{record_date}, #{avg_tmp}, #{min_tmp}, #{max_tmp}, #{rain_hours}, #{day_rain}, #{max_insta_windspeed}, #{max_windspeed}, #{avg_windspeed}, #{avg_humid}, #{day_snow}, #{accumul_snow}")
	public int insertRecord(Record record);
	
	//Warning 테이블에 경보 데이터 삽입하기
	
	//Grid 테이블에서 격자 데이터 불러오기
	@Select("select grid_cityname, grid_x, grid_y, lat, lon from Grid")
	public List<Grid> selectGrid();
	
}
