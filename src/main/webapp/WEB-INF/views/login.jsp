<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>

<div class="row" style="margin-top: 50px;">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div style="margin: 350px auto; width: 400px; height: 350px;">
			<form class="form-horizontal" action="loginPost" method="post">  
				<div class="form-group">
					<label class="control-label col-sm-3" for="userId" style="text-align: left;">아이디:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="userId" name="userId"
							placeholder="Enter email">
					</div>  
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="password" style="text-align: left;">비밀번호:</label>
					<div class="col-sm-9">
						<input type="password" class="form-control" id="password" name="password"
							placeholder="Enter password">
					</div>
				</div>
				<div class="form-group"> 
					<div class="col-sm-offset-3 col-sm-9">
						<button type="submit" class="btn btn-primary">로그인</button>  
						<a href="join" class="btn btn-success">회원가입 하기</a>  
					</div>  
				</div>
			</form>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>
</body>
</html>
