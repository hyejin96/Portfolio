<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="assets/css/board/commentCreate.css" />

<div class = "commentTotal">
	<div class = "commentTable">
		<table>
			<tr class = "commentTr">
				<th class = "commentTh">작성자</th>
				<td class = "commentName"><input type = "text" name = "name" placeholder = "작성안할시 익명으로 작성됩니다."/>
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
					<%--<th class = "commentListTh">작성일</th> --%>
					<td class = "commentListDate">${cList.createDate}</td>
					<td class = "deleteTh"><a href = "#" class = "deleteButton" id = "${cList.commentId}" name = "${cList.boardId}" onclick = 'deleteFunction(this)'>글삭제</a></td>
				</tr>
				<tr class = "commentListTr">
					<th class = "commentListTh">내용</th>
					<%-- <td class = "commentListContent"><span>${cList.content }</span></span></td> --%>
					<td class = "commentListContent" colspan = "3"><textarea readonly="readonly" disabled>${cList.content}</textarea></td>
				</tr>
				<%--<tr class = "commentListTr">
				<th colspan = "4" class = "deleteTh">
					<a href = "#" class = "deleteButton" id = "${cList.commentId}" name = "${cList.boardId}" onclick = 'deleteFunction(this)'>글삭제</a>
				</th>
				</tr> --%>
			</c:forEach>
		</table>
	</div>
	<%-- 수정시 생길 영역 --%>
	<div class="update_wrap"></div>
</div>

<script>
function deleteFunction(btn) { // deleteBoardId
	
	if(confirm("정말로 삭제하겠습니까?")) {
		alert("삭제되었습니다.");
		deleteCommentId = btn.id;
		deleteBoardId = btn.name;
		$.ajax({
			type: "POST",
			url: "jinPortfolio?cmd=commentDelete&deleteCommentId=" + deleteCommentId + "&deleteBoardId=" + deleteBoardId,
			success : window.location.reload()
		});
	}
}

</script>