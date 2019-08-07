<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="assets/css/tmp/tmp.css" />
<link rel="stylesheet" href="assets/css/board/boardWrite.css" />
<title>Board Write</title>

<%@include file="../nav2.jsp"%>
<div class = "container">
	<form method = "POST" action="jinPortfolio?cmd=postBoardAction" enctype = "multipart/form-data" >
    <table id = "t01">
        <tr class = "tr01">
            <th>작성자</th>
            <td><input type = "text" placeholder = "입력안할시 익명으로 저장" name = "writer"></td>
        </tr>
        <tr class = "tr01">
            <th>제목</th>
            <td><input type = "text" name = "title"></td>
        </tr>
        <tr class = "tr01">
            <th>내용</th>
            <td><textarea name = "content"></textarea></td>
        </tr>
        <tr class = "tr01 filebox">
            <th>파일 첨부</th>
            <td>
                <input class = "upload_name" value = "파일선택" disabled = "disabled">

                <label for = "upload_file">업로드</label>
                <input type = "file" class = "upload_hidden" name = "file" id = "upload_file">
            </td>
        </tr>
    </table>
    <div class = "writeButtonDIV">
    	<input class = "writeButtonA" type = "submit" value = "작성"></input>
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
