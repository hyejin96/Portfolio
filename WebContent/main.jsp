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
								<h1><a href="index.html" id="logo">Amazing</a></h1>
								<p>저의 포트폴리오를 찾아주셔서 감사합니다
								</p>
								<hr />
							</header>
							<div class="sub_contents member">
								<div class="ly_container bg_white">
									<div class="join_gate">			
										<div class="left_sec">
											<dl>
												<dt>일반 로그인</dt>				
												<dd class="btn_in">	
													<a href="jinPortfolio?cmd=peopleEnter" class="btn bg_join_blue">둘러보기</a>	
													<!-- <a href="javascript:loginNaver('https://nid.naver.com/oauth2.0/authorize?response_type=code&amp;svctype=0&amp;client_id=DjKONhsZw9imB6u9SuM6&amp;redirect_uri=https%3A%2F%2Fwww.starctmall.com%3A446%2Fmember%2Fjoin%2Fcallback_naver&amp;state=808109464624517542037210461038444332073');" class="btn bg_naver"><i class="ico ico_join_naver"></i>네이버로 로그인</a>
													<a href="javascript:loginFacebook();" class="btn bg_facebook"><i class="ico ico_join_face"></i>페이스북 로그인</a>
													<a href="javascript:loginKakao();" class="btn bg_cacao"><i class="ico ico_join_cacao"></i>카카오톡 로그인</a> -->
												</dd>					
											</dl>
										</div>
										<div class="right_sec">
											<dl>
												<dt>관리자 로그인</dt>
												<form action = "jinPortfolio?cmd=adminLogin" method = "post">
													<dd class="login_form">
														<div class="placeholder">
															<input type="text" name="id" class="boxline" id="hID" placeholder = "아이디" style="width: 100%;">
														</div> 
														<div class="placeholder">
															<input type="password" name="pw" class="boxline" id="hPW" placeholder = "비밀번호" style="width: 100%;">
														</div> 
													</dd>
													<dd class="btn_in pt20">
														<%--<a href="javascript:onLogin(document.loginForm);" class="btn bg_join_blue">로그인</a> --%>
														<input type = "submit" value = "로그인" class = "btn bg_join_blue" />
													</dd>
												</form>
												<%--<dd class="chk_in">
													<label>
															<input type="checkbox" id="idSaveCheck" name="idSaveCheck" class="checkbox">
															<span class="label">아이디 저장</span>
													</label>
													<ul class="login_link">
														<li><a href="/member/login/find_member_id">아이디 찾기</a></li>
														<li><a href="/member/login/find_member_pwd">비밀번호 찾기</a></li>
													</ul>
												</dd> --%>		
											</dl>			
										</div>
										<!-- //right_sec -->
									</div>
									<!-- //join_gate -->
								</div>
							</div>
						</div>
				<jsp:include page="nav.jsp" flush="true"></jsp:include>
				</div>
				<%@include file="/footer.jsp"%>

		</div>
	</body>
</html>