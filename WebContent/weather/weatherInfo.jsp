<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>날씨 정보</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="assets/css/weather/weatherInfo.css">
<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<%@include file="../nav2.jsp"%>

<%-- 현재 온도, 강수량.. --%>

<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
<script>
navigator.geolocation.getCurrentPosition(function(pos) {
	var arrays = '';
	var latitude = pos.coords.latitude;
    var longitude = pos.coords.longitude;
    
     console.log(latitude + " / " + longitude);
    // 37.493584, 127.048501
    /*http://openweathermap.org/data/2.5/forecast/hourly?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22*/
	var apiURI = 'http://api.openweathermap.org/data/2.5/forecast?lat='+latitude+'&lon='+longitude+'&APPID=739d9a6786339df7f824e399f9b7f50c';
        $.ajax({
            url: apiURI,
            dataType: "json",
            type: "GET",
            async: "false",
            success: function(resp) {
            	var d = new Date();
           	    var currentDate = d.getFullYear() + "." + ( d.getMonth() + 1 ) + "." + d.getDate();
                var currentTime = d.getHours() + " :  " + d.getMinutes() + " : " + d.getSeconds();
            	var country = resp.city.name + " / " + resp.city.country;
            	var weather_info =  resp.list[0].weather[0].main + " (" + resp.list[0].weather[0].description + ")";
            	var wind_speed =  resp.list[0].wind.speed + "㎧";
            	var humidity =  resp.list[0].main.humidity + "%";
            	var pressure =  resp.list[0].main.pressure + "hpa";
            	var rain =  resp.list[0].main.rain + "mm";
            	var imgsrc = "http://openweathermap.org/img/w/" + resp.list[0].weather[0].icon + ".png";
            	
				$(".weather-widget__main").html(weather_info); // 날씨
				$(".weather-widget__city-name").html(country);	 // 도시명
				$("#wrong-data-lat").html(latitude);	// 위도
				$("#wrong-data-lon").html(longitude);	// 경도
				$("#weather-widget-wind").html(wind_speed);	// 바람세기
				$("#humidity").html(humidity);	// 습도
				$("#pressure").html(pressure);	// 압력?
				$("#rain_3h").html(rain);	// 습도
				$(".weather-widget__img").attr('src', imgsrc); // 이미지
				$("#date").html(currentDate + "  " + currentTime);

				/*for(var i = 0; i < (resp.list).length; i++) {
            		arrays += '[' + resp.list[i].dt_txt.substring(11,16) + ", "+ resp.list[i].main.temp + ']' + ','
            	}
            	arrays = arrays.slice(0,-1);*/
            	
            	
            	
            	google.charts.load('current', {'packages':['line']});
                google.charts.setOnLoadCallback(drawChart);
                
                function drawChart() {
                	var linedata = new google.visualization.DataTable();

                	linedata.addColumn('string', '시간');
            	    linedata.addColumn('number', '온도');

            	    //for(var i = 0; i < 3; i++) {
            	    	linedata.addRows([
            	  			[resp.list[0].dt_txt.substring(11,16), resp.list[0].main.temp - 273.15],
            	  			[resp.list[1].dt_txt.substring(11,16), resp.list[1].main.temp - 273.15],
            	  			[resp.list[2].dt_txt.substring(11,16), resp.list[2].main.temp - 273.15],
            	  			[resp.list[3].dt_txt.substring(11,16), resp.list[3].main.temp - 273.15],
            	  			[resp.list[4].dt_txt.substring(11,16), resp.list[4].main.temp - 273.15]
	    	           	]);
					//}
            	    
            	    var lineOpts = {
           	  	      chart: {
           	  	        title: country + ' 시간별 온도',
           	  	        subtitle: ''
           	  	      },
           	  	      width: 800,
           	  	      height: 500
           	  	    };
            	    var linechart = new google.charts.Line(document.getElementById('line_chart'));
            	    linechart.draw(linedata, google.charts.Line.convertOptions(lineOpts));
                }    	
               }
            
        })
}) <%--navigator end--%>
</script>


<div class = "container"><%--m은 마진 l은 오른쪽 auto는 콘텐츠의 넓이 만큼만 너비로 주겠다.--%>
	<div id = "line_chart" style = "width:800px"></div>
	
	<div class = "widget_layout">
		<span>
			<div id = "weather_widget" class= "weather_widget">
				<h2 class = "weather-widget__city-name"></h2>
				<h3 class = "weather-widget__temperature">
					<img class="weather-widget__img" src="https://openweathermap.org/img/wn/09d@2x.png" alt="Weather Sinch’ŏn-dong , KR" width="50" height="50">
				</h3>
				<p class= "weather-widget__main">
					
				</p>
				<p id = "date"></p>
				<table class="weather-widget__items">
					<tr class="weather-widget__item">
						<td>Wind</td>
						<td id="weather-widget-wind"></td>
					</tr>
					<tr class="weather-widget__item">
						<td>Pressure</td>
						<td id = "pressure"></td>
					</tr>
					<tr class="weather-widget__item">
						<td>Humidity</td>
						<td id = "humidity"></td>
					</tr>
					<tr class="weather-widget__item">
						<td>Rain</td>
						<td id = "rain_3h"></td>
					</tr>
					<tr class="weather-widget__item">
						<td>Geo coords</td>
						<td><a
							href="/weathermap?zoom=8&amp;lat=37.52&amp;lon=127.08"
							class="weather-widget__link">[<span id="wrong-data-lat">  </span>,&nbsp;<span
								id="wrong-data-lon"></span>]
						</a></td>
					</tr>
				</table>
			</div>
		</span>
	</div>
	
</div>
<%@include file="../footer.jsp"%>