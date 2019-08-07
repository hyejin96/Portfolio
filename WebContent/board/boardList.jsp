<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table id = "t01">
	<caption>
	<span class = "count">${boardNumber}개의 글</span>
	</caption>
    <tr class = "tr00">
    	<th> </th>
        <th>번호</th>
        <th>제목</th>
        <th>등록일</th>
        <th>조회수</th>
    </tr>
    <c:forEach items = "${bList}" var = "getBoardList" varStatus = "status">
    <tr class = "tr01">
    	<c:choose>
    		<c:when test = "${id == 'admin'}">
    			<td><input type = "checkbox" class = "deleteBoardId" name = "deleteBoardId" value = "${getBoardList.boardId}" /></td>	
    		</c:when>
    		<c:otherwise>
    			<td></td>
    		</c:otherwise>
    	</c:choose>
<%--    	
	    <c:if test = "${id == 'admin'}">
	    	<td><input type = "checkbox" class = "deleteBoardId" name = "deleteBoardId" value = "${getBoardList.boardId}" /></td>
	   	</c:if>
	   	
	    <c:if test = "${id eq 'people'} ">
	    	<td>  /////</td>
	   	</c:if>
--%>
	    	<%--( 총 개시물 수 - c:forEach의 varStatus.index ) - ( (현재페이지 번호 - 1 ) * pageSize ) --%>
	        <td onClick="location.href='jinPortfolio?cmd=boardDetail&boardId=${getBoardList.boardId}&pageNum=${pageNum}&boardNum=${(boardNumber - status.index) - ((pageNum - 1) * pageSize)}'">${(boardNumber - status.index) - ((pageNum - 1) * pageSize)}</td>
	        <td onClick="location.href='jinPortfolio?cmd=boardDetail&boardId=${getBoardList.boardId}&pageNum=${pageNum}&boardNum=${(boardNumber - status.index) - ((pageNum - 1) * pageSize)}'"> <c:out value="${fn:substring(getBoardList.title,0,13)}"/>....</td>
	        <td onClick="location.href='jinPortfolio?cmd=boardDetail&boardId=${getBoardList.boardId}&pageNum=${pageNum}&boardNum=${(boardNumber - status.index) - ((pageNum - 1) * pageSize)}'">${getBoardList.writeDate}</td>
	        <td onClick="location.href='jinPortfolio?cmd=boardDetail&boardId=${getBoardList.boardId}&pageNum=${pageNum}&boardNum=${(boardNumber - status.index) - ((pageNum - 1) * pageSize)}'">${getBoardList.boardHit}</td>
        
    </tr>
    </c:forEach>
</table>

<c:if test = "${id == 'admin'}">
	<div class = "wr_de_button">
		<a href ="jinPortfolio?cmd=postBoard" class = "writeButton">글작성</a>
		<a href = "#" class = "deleteButton" onclick = "deleteFunction()">글삭제</a>
	</div>
</c:if>
<div class = "pagingDiv">
	<c:if test="${ boardNumber > 0}">
		<ul class = "pagingUl">  
		<c:if test="${startPage > pageBlock}">
			<li class = "pagingLi" ><a href = "#" onclick = "p(this);" name="${startPage-pageBlock}" ><</a></li>
		</c:if>  
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<c:choose>
					<c:when test="${currentPage == i}">
		    			<li class = "pagingLi"><a href = "#" onclick = "p(this);" name="${i}" class = "on">${i}</a></li>
		    		</c:when> <%-- c: when end --%>
		    		<c:otherwise>
		    			<li class = "pagingLi"><a href = "#" onclick = "p(this);" name="${i}">${i}</a></li>
		    		</c:otherwise> <%-- c:otherwise end --%>
		    	</c:choose>  
		    </c:forEach>
		    <c:if test="${endPage < pageCount }">
		    	<li class = "pagingLi" ><a href = "#" onclick = "p(this);" name="${startPage+pageBlock}" >></a></li>  
		    </c:if>
		</ul>	<%-- pagingUl end --%>
	</c:if>
</div> <%-- pagingDiv end --%>

<script>
/*
if (self.name != 'reload') {
    self.name = 'reload';
    self.location.reload(true);
}
else self.name = '';
*/

function p(jumpBtn) {
	// console.log(" jumpBtn.name 확인 " +  jumpBtn.name);
	var rStr = jumpBtn.name;
	// alert(rStr);
	$.ajax({
		url : "jinPortfolio?cmd=boardListPaging&pageNum=" + rStr,
		success : function(result) {
			$("div.mainList").html(result);
		}
	})
}

function deleteFunction() { // deleteBoardId
	var isChecked = false;
	var deleteId = '';
	
	$('.deleteBoardId').each(function(index, data) {
		if(data.checked) {
			isChecked = data.checked;
		}
	});
	
	if(!isChecked) {
		alert("삭제할 대상을 선택하세요.");
		return;
	}
	
	if(confirm("정말로 삭제하겠습니까?")) {
		alert("삭제되었습니다.");
		$('input[name=deleteBoardId]:checked').each(function() { 
			deleteId = deleteId + $(this).val() + ',';
			console.log(deleteId + " / 삭제 번호 jsp 확인");
		});
		deleteId = deleteId.substring(0, deleteId.lastIndexOf(','));
		console.log(deleteId);
		$.ajax({
			data: deleteId,
			type: "POST",
			url: "jinPortfolio?cmd=boardListDelete&deleteId=" + deleteId,
			success : window.location.reload()
		});
	}
}


</script>



