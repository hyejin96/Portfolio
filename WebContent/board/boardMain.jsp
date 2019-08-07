<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BoardMain</title>

<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<link rel="stylesheet" href="assets/css/board/boardMain.css" />
<link rel="stylesheet" href="assets/css/board/boardList.css" />
<link rel="stylesheet" href="assets/css/tmp/pagination_board.css" />

<div class = "container">
	<div class = "header">
	<%@include file="../nav2.jsp"%>
	</div>
	<div class = "contentBox">
		<div class = "searchDiv selectDiv">
		    <input class = "searchBox selectBox" placeholder="검색" name = "searchName">
		    <button class = "searchButton selectButton" onclick = "searchButton()">검색</button>
		</div>
		<div class="mainList">
		<%@include file="boardList.jsp"%> 
		</div>
	</div>
</div>
<%@include file="../footer.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>
function searchButton() {
	var searchName = $(".searchBox").val();
	// console.log(searchName);
	$.ajax ({
		data: searchName,
		url: "jinPortfolio?cmd=boardSearch&searchName=" + searchName,
		type: "POST",
		success: function(result) {
			$(".mainList").html(result);
		}
		
	});
}
</script>

