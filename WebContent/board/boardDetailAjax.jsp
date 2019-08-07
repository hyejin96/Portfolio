<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<fmt:parseNumber  var = "currentId" value = "${(afterBoardId + preBoardId) /2}" integerOnly = "true"/>
<input type = "hidden" value = "${currentId}" class = "currentId"/>

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
        <th class = "tmp01">파일</th>
        <td colspan = "3" class = "boardTitle">${bVO.file}</td>
    </tr>
</table>

<div class = "divBox">
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardUpdate&boardId=${bVO.boardId}'" class = "boardList"> 글 수정</a>
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardDelete&boardId=${bVO.boardId}'" class = "boardList"> 글 삭제</a>
	<%-- 
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardUpdate&boardId=${bVO.boardId}'" class = "boardList"> 글 수정</a>
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardDelete&boardId=${bVO.boardId}'" class = "boardList"> 글 삭제</a>
	--%>
	<a href = "#" onClick = "location.href='jinPortfolio?cmd=boardList'" class = "boardList"> 목록 </a>
</div>

<table id = "t02">
<c:if test = "${afterBoardId ne 0}">
	<tr>
	<th class = "tmp01">다음글</th>
	<td class = "boardTitle moveBoard" num = "${boardNum+1}" onclick = "moveButton(this)" id = "${bAVO.boardId}">${bAVO.title}</td>
	</tr>
</c:if>
<c:if test = "${preBoardId  ne 0}">
	<tr>
	<th class = "tmp01">이전글</th>
	<td class = "boardTitle moveBoard" num = "${boardNum-1}" onclick = "moveButton(this)" id = "${bPVO.boardId}">${bPVO.title}</td>
	</tr>
</c:if>

</table>
<%@include file="commentCreateResult.jsp"%>
<%--
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
</div>
 --%>

