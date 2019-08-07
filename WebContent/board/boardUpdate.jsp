<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<link rel="stylesheet" href="assets/css/board/boardWrite.css" />
<title>Board Update</title>

<%@include file="../nav2.jsp"%>
<div class = "container">
	<form method = "POST" action="jinPortfolio?cmd=boardUpdateAction">
	<input type = "hidden" name = "boardId" value = "${bVO.boardId}" />
	<input type = "hidden" name = "boardNum" value = "${boardNum}" />
	<input type = "hidden" name = "pageNum" value = "${pageNum}" />
    <table id = "t01">
        <tr class = "tr01">
            <th>작성자</th>
            <c:choose>
            	<c:when test = "${bVO.write ne '익명'}">
            		<td><input type = "text" placeholder = "${bVO.write}" name = "writer" value = "${bVO.write}"></td>	
            	</c:when>
            	<c:otherwise>
            		<td><input type = "text" placeholder = "익명(입력안할시 익명으로 자동저장)" name = "writer" value = "익명"></td>
            	</c:otherwise>
            </c:choose>
            
        </tr>
        <tr class = "tr01">
            <th>제목</th>
            <td><input type = "text" name = "title" placeholder = "${bVO.title }" value = "${bVO.title }"></td>
        </tr>
        <tr class = "tr01">
            <th>내용</th>
            <td><textarea name = "content">${bVO.content }</textarea></td>
        </tr>
        <tr class = "tr01 filebox">
            <th>파일 첨부</th>
            <td>
            	<c:choose>
	            	<c:when test = "${bVO.file ne null}">
	                <input class = "upload_name"  disabled = "disabled" placeholder = "${bVO.file}" value = "${bVO.file}">
					</c:when>
					<c:otherwise>
						<input class = "upload_name" value = "파일선택" disabled = "disabled">
					</c:otherwise>
            	</c:choose>
            	
                <label for = "upload_file">업로드</label>
                <input type = "file" class = "upload_hidden" name = "file" id = "upload_file">
            </td>
        </tr>
    </table>
    <div class = "writeButtonDIV">
    	<input class = "writeButtonA" type = "submit" value = "수정"></input>
    </div>
    </form>
</div>

<%@include file="../footer.jsp"%>

<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
$(document).ready(function(){
    var fileTarget = $('.filebox .upload_hidden');
    fileTarget.on('change', function(){
        if(window.FileReader){
            var filename = $(this)[0].files[0].name;
        }else{
            var filename = $(this).val().split('/').pop().split('\\').pop();
        }
        $(this).siblings('.upload_name').val(filename)
    });
});
</script>
