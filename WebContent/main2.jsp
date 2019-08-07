<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="assets/css/main/main.css" rel="stylesheet" type="text/css">
<link href="assets/css/tmp/tmp.css" rel="stylesheet" type="text/css">
<title>Hyejin's Portfolio</title>
</head>
	<body class="homepage is-preload">
		<div id="page-wrapper">
				<div id="header">
						<div class="inner">
							<header>
								<h1><a href="jinPortfolio?cmd=peopleEnter" id="logo">Amazing</a></h1>
								<p>저의 포트폴리오를 찾아주셔서 감사합니다
								</p>
								<hr />
							</header>
						</div>
				<jsp:include page="nav2.jsp" flush="true"></jsp:include>
				</div>
				<%@include file="/footer.jsp"%>

		</div>
	</body>
</html>