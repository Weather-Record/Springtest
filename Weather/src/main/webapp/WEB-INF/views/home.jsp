<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cc86cb6785a5cca164aac930b0b9a06a&libraries=services,clusterer,drawing"></script>
<title>메인페이지</title>
</head>
<body>
	<a href="uploadlocation" style="text-decoration: none">Location업로드</a><br/>
	<a href="getultrasrtncst" style="text-decoration:none">초단기실황</a><br/>
	<div id="map" style="width: 450px; height: 600px;"></div>
</body>
<script>
	window.addEventListener("load", function(event){
		var container = document.getElementById('map'); 
		//지도를 담을 영역의 DOM 레퍼런스
		var options = { 
			//지도를 생성할 때 필요한 기본 옵션
			//지도의 중심좌표
			center: new kakao.maps.LatLng(35.99344699621903, 127.35691175040806),
			//지도의 레벨(확대, 축소 정도) 클수록 축소
			level: 13
		};
		//지도 생성 및 객체 리턴
		var map = new kakao.maps.Map(container, options);
	
		// 마커를 표시할 위치와 title 객체 배열입니다 
		var positions = [
		    {
		        title: "${gridlist[0].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[0].lat}, ${gridlist[0].lon})
		    },
		    {
		        title: "${gridlist[1].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[1].lat}, ${gridlist[1].lon})
		    },
		    {
		        title: "${gridlist[2].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[2].lat}, ${gridlist[2].lon})
		    },
		    {
		        title: "${gridlist[3].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[3].lat}, ${gridlist[3].lon})
		    },
		    {
		        title: "${gridlist[4].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[4].lat}, ${gridlist[4].lon})
		    },
		    {
		        title: "${gridlist[5].grid_cityname}",
		        latlng: new kakao.maps.LatLng(${gridlist[5].lat}, ${gridlist[5].lon})
		    },
		    {
		        title:" ${gridlist[6].grid_cityname}", 
		        latlng: new kakao.maps.LatLng(${gridlist[6].lat}, ${gridlist[6].lon})
		    },
		    {
		        title: "${gridlist[7].grid_cityname}", 
		        latlng: new kakao.maps.LatLng(${gridlist[7].lat}, ${gridlist[7].lon})
		    },
		    {
		        title: "${gridlist[8].grid_cityname}", 
		        latlng: new kakao.maps.LatLng(${gridlist[8].lat}, ${gridlist[8].lon})
		    }  
		];
		
		var temp = ["${templist[0]}","${templist[1]}","${templist[2]}","${templist[3]}","${templist[4]}","${templist[5]}","${templist[6]}","${templist[7]}","${templist[8]}"];
		
		// 마커 이미지의 이미지 주소입니다
		var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
			
		for (var i = 0; i < positions.length; i ++) {
		    
		    // 마커 이미지의 이미지 크기 입니다
		    var imageSize = new kakao.maps.Size(24, 35); 
		    
		    // 마커 이미지를 생성합니다    
		    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
		    
		    // 마커를 생성합니다
		    var marker = new kakao.maps.Marker({
		        map: map, // 마커를 표시할 지도
		        position: positions[i].latlng, // 마커를 표시할 위치
		        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
		        image : markerImage // 마커 이미지 
		    });
		    
			// 커스텀 오버레이에 표시할 컨텐츠 입니다
			// 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
			// 별도의 이벤트 메소드를 제공하지 않습니다 
			var content = '<div class="wrap" style="width:100px;height:80px;">'
					+ '      <div class="info">'
					+ '        <div class="title">'
					+ 				positions[i].title
					+ '            <div class="close" onclick="closeOverlay()" title="닫기"></div>'
					+ '        </div>'
					+ '        <div class="body">'
					+ '            <div class="desc">'
					+ '                <div class="ellipsis">'
					+						temp[i]+' °C'				
					+ '				   </div>'
					+ '            </div>' 
					+ '        </div>' 
					+ '    </div>'
					+ '</div>';
			// 마커 위에 커스텀오버레이를 표시합니다
			// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
			var overlay = new kakao.maps.CustomOverlay({
				content : content,
				map : map,
				position : marker.getPosition()
			});

			// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
			kakao.maps.event.addListener(marker, 'click', function() {
				overlay.setMap(map);
			});

			// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
			function closeOverlay() {
				overlay.setMap(null);
			}
		}
		
	});
</script>
</html>