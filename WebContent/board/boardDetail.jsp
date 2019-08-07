<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>boardDetail</title>

<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<link rel="stylesheet" href="assets/css/board/boardDetail.css" />

<%@include file="../nav2.jsp"%>

<div class = "container">
<%--
<table id = "t01">
 	<tr>
        <th class = "tmp01">제목</th>
        <td colspan = "3" class = "boardTitle">${bVO.title}</td>
    </tr>
    <tr>
        <th class = "tmp01">번호</th>
        <td class = "tmp02">${boardNum}</td>
        
        <th class = "tmp01">등록일</th>
        <td class = "tmp02">${bVO.writeDate}</td>
    </tr>
    <tr>
    </tr>
    <tr>
        <th class = "tmp01">작성자</th>
        <td class = "tmp02">${bVO.write}</td>
        
        <th class = "tmp01">조회수</th>
        <td class = "tmp02">${bVO.boardHit}</td>
    </tr>
    <tr>
        <th colspan="4" class = "contentTitle">내용</th>
    </tr>
    <tr>
        <td colspan="4" class = "content">
               ${bVO.content}
        </td>
    </tr>
    <tr>
        <th class = "tmp01">파일첨부</th>
        <td colspan = "3" class = "boardTitle"><a href = "../assets/uploadFile/boardFile/${bVO.file}">${bVO.file}</a></td>
    </tr>
</table>
<div class = "divBox">
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardUpdate&boardId=${bVO.boardId}&boardNum=${boardNum}'" class = "boardList"> 글 수정</a>
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardDelete&boardId=${bVO.boardId}'" class = "boardList"> 글 삭제</a>
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardList&pageNum=${pageNum}'" class = "boardList"> 목록 </a>
</div>

<table id = "t02">
<c:if test = "${afterBoardId ne 0}">
	<tr>
	<th class = "tmp01">다음글</th>
	<td class = "boardTitle moveBoard" num = "${boardNum+1}" onclick = "moveButton(this)" id = "${bAVO.boardId}"><c:out value="${fn:substring(bAVO.title,0,13)}"/>....</td>
	</tr>
</c:if>
<c:if test = "${preBoardId  ne 0}">
	<tr>
	<th class = "tmp01">이전글</th>
	<td class = "boardTitle moveBoard" num = "${boardNum-1}" onclick = "moveButton(this)" id = "${bPVO.boardId}"><c:out value="${fn:substring(bPVO.title,0,13)}"/>....</td>
	</tr>
</c:if>

</table>
 --%>
 
<%@include file="boardDetailAjax.jsp"%>
 
<%--
<div class = "commentTotal">

		<%@include file="commentCreateResult.jsp"%>
	<div class = "commentTable">
		<table>
			<tr class = "commentTr">
				<th class = "commentTh">작성자</th>
				<td class = "commentName"><input type = "text" class = "commentNames" name = "name" placeholder = "작성안할시 익명으로 작성됩니다."/>
			</tr>
			<tr class = "commentTr">
				<th class = "commentTh">내용</th>
				<td class = "commentContent"><textarea name = "content"  class = "commentContentText"></textarea></td>
			</tr>
		</table>
		<div class = "submitDiv"> 
		<button class = "submitInput" onclick = "commentCreate()">댓글달기</button>
		</div>
	</div>
	<div class = "commentList">
		<table class = "commentListTable">
			<c:forEach var = "cList" items="${cList}">
				<tr class = "commentListTr">
					<th class = "commentListTh">작성자</th>
					<td class = "commentListName">${cList.name }</td>
					<th class = "commentListTh">작성일</th>
					<td class = "commentListDate">${cList.createDate}</td>
				</tr>
				<tr class = "commentListTr">
					<th class = "commentListTh">내용</th>
					<td class = "commentListContent" colspan = "3"><textarea readonly="readonly" disabled>${cList.content}</textarea></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	 --%>
</div>
</div>



<%@include file="../footer.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
function moveButton(btn) {
	var boardId = btn.id; 
	var boardNum = btn.getAttribute("num");
	// console.log(boardId);
	// console.log(boardNum);
	$.ajax({
		data : boardId,
		url : "jinPortfolio?cmd=boardMoveDetailAction&boardId=" + boardId + "&boardNum=" + boardNum,
		type : "POST",
		success : function(result) {
			$(".container").html(result);
		}
	});
}

function commentCreate() {
	//var boardId = ${bVO.boardId}; // 게시글 번호 -- > 현재 게시글 번호
	var boardId = $(".currentId").val(); // 게시글 번호 -- > 현재 게시글 번호
	var name = $(".commentNames").val();
	if(name == undefined) {
		name = "익명";
	}
	var content = $(".commentContentText").val();
	
	var params="boardId="+boardId+"&name="+name + "&content=" + content;
	$.ajax({
		data : params,
		url : "jinPortfolio?cmd=commentCreate&" + params,
		type : "POST",
		success : function(result) {
			$(".commentTotal").html(result);
		}
	});
}
</script>