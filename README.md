# Springtest

< 엑셀 데이터 DB(Record) 에 업로드 하는 법 >
1) context path가 /로 되어 있는지 확인
2) server 구동 후 '관리자 페이지' 로 이동
3) 'Record업로드' 클릭
4) xls 파일 넣기(확장자 csv를 xls로 변경) 후 업로드(submit)
5) STS 콘솔창에서 데이터가 업로드되고 있는지 확인

※ 단, database명, user명, password는 weather, user00, 1234 로 일치해야 하며 생성한 Record 테이블이 테이블명 및 속성명, 자료형이 아래와 모두 일치해야 함(MySQL은 대소문자 구분,,?)

create table Record(
	record_id int primary key auto_increment,
	location_id int references Location(location_id) on delete cascade,
	record_date Date,
	avg_tmp double default 0,
	min_tmp double default 0,
	max_tmp double default 0,
	rain_hours double default 0,
	day_rain double default 0,
	max_insta_windspeed double default 0,
	max_windspeed double default 0,
	avg_windspeed double default 0,
	avg_humid double default 0,
	day_snow double default 0,
	accumul_snow double default 0
);
