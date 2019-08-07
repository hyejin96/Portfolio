<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<link rel="stylesheet" type="text/css" href="assets/css/movieList/movieList.css">

<%@include file="../nav2.jsp"%>

  <%--
<td>순위</td>
<td>영화명</td>
<td>개봉일</td>
<td>매출액</td>
<td>매출액점유율</td>
<td>매출액증감(전일대비)</td>
<td>누적매출액</td>
<td>관객수</td>
<td>관객수증감(전일대비)</td>
<td>누적관객수</td>
<td>스크린수</td>
<td>상영횟수</td>
 --%>
<div class = "container">
<div class= "today">${todayyyy}년 ${todaymm}월 ${todaydd}일 영화 랭킹</div>
<table class = "movieTable">
	<tr class = "movieTitle">
		<th>순위</th>
		<th>영화명</th>
		<th>개봉일</th>
		<%--
		<th>매출액<span>(원)</span></th>
		<th>매출액점유율</th>
		<th>매출액증감<br><span>(전일대비)</span></th>
		<th>누적매출액<span>(원)</span></th>
		 --%>
		<th>관객수</th>
		<th>관객수증감<span>(전일대비)</span></th>
		<th>누적관객수</th>
	</tr>
	<c:if test = "${not empty dailyResult.boxOfficeResult.dailyBoxOfficeList}">
	 	<c:forEach var = "movies" items = "${dailyResult.boxOfficeResult.dailyBoxOfficeList}">
	 	<tr class = "movieContent">
		 	<td class = "num">${movies.rank}</td>
		 	<td class = "ellip">${movies.movieNm}</td>
			<td>${movies.openDt}</td>
			<%--
			<td>
				<fmt:formatNumber type="number" maxFractionDigits="3" value="${movies.salesAmt}" />원
			</td>
			<td>${movies.salesShare}%</td>
			<td>${movies.salesChange}%</td>
			<td>
				<fmt:formatNumber type="number" maxFractionDigits="3" value="${movies.salesAcc}" />원
			</td>
			--%>
			<td>
				<fmt:formatNumber type="number" maxFractionDigits="3" value="${movies.audiCnt}" />명
			</td>
			<td>${movies.audiChange}%</td>
		 	<td class = "fr">
		 		<fmt:formatNumber type="number" maxFractionDigits="3" value="${movies.audiAcc}" />명
		 	</td>
			<%--	<div class = "movieBox">
						<ol class = "rank_realtime">
							<li class = "sel on">
								<a class = "block">
									<span class = "num">${movies.rank}</span>
									<strong class = "tit"><span class = "ellip">${movies.movieNm}</span></strong>
									<span class = "fr">${movies.audiAcc}명</span>
								</a>
							</li>
						</ol>
				</div> --%>
		</tr>
		</c:forEach>	
 </c:if>
</table>
 
</div>


<%@include file="../footer.jsp"%>