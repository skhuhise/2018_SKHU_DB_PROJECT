<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<script src="//code.jquery.com/jquery.min.js"></script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="../../res/css/user/login.css?ver=1">
<link rel="stylesheet" type="text/css"
	href="../../res/css/common/login.css?ver=1">
<title>성공회대 학사 시스템</title>
</head>
<body>
	<div class="wrap text-center">
		<div class="outer">
			<div class="inner">
				<div class="centered">
					<form class="form-horizontal" action="../menu/main">
						<img src="../../res/image/login_logo.png"
							class="img-responsive center-block" alt="Responsive image" />
						<div class="form-group mt-10">
							<div class="col-sm-12">
								<input type="text" class="form-control" name="studentNumber"
									placeholder="아이디">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<input type="password" class="form-control" name="password"
									placeholder="패스워드">
							</div>
						</div>
						<div>
							<button type="submit" class="btn btn-info btn-block">로그인</button>
						</div>
					</form>
					<div class="account">
						<a href="../regist/regist.jsp">회원가입</a>
						*
						<a href="../forgot/forgotpwd.jsp">비밀번호 찾기</a>
					</div>
					<div class="account">
						<a href="guest/basic.jsp">기본 졸업 요건</a>
						*
						<a href="guest/detail.jsp">학과/학부별 졸업 요건</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>