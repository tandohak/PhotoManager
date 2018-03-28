<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>

<div class="row" style="margin-top: 50px;">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div style="margin: 350px auto; width: 400px; height: 350px;">
			<form class="form-horizontal" action="join" method="post" id="joinfrom">  
				<div class="form-group">
					<label class="control-label col-sm-3" for="userId" style="text-align: left;">아이디:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="userId" name="userId" placeholder="아이디">
						<span id="check"></span>
					</div>  
				</div> 
				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<a href="#" class="btn btn-success" id="idCheck">아이디 중복체크</a>  
					</div>
				</div>
				<div class="form-group">  
					<label class="control-label col-sm-3" for="password" style="text-align: left;">비밀번호:</label>
					<div class="col-sm-9">
						<input type="password" class="form-control" id="password" name="password" placeholder="비밀번호">
					</div>
				</div>
				<div class="form-group">  
					<label class="control-label col-sm-3" for="passwordCheck" style="text-align: left;">비번확인:</label>
					<div class="col-sm-9">
						<input type="password" class="form-control" id="passwordCheck" name="passwordCheck" placeholder="비밀번호">  
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="name" style="text-align: left;">이름:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="name" name="name"
							placeholder="이름">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="email" style="text-align: left;">이메일:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="email" name="email"
							placeholder="이메일">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="phone" style="text-align: left;">연락처:</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="phone" name="phone" placeholder="연락처">
					</div>
				</div>
				<div class="form-group"> 
					<div class="col-sm-offset-3 col-sm-9">
						<button type="submit" class="btn btn-primary">회원가입</button>  
						<a href="${pageContext.request.contextPath }" class="btn btn-success">뒤로 가기</a>  
					</div>  
				</div>
			</form>
			<script>
				var regId = /^[a-z0-9]{6,15}$/;
				var existId = false;
				
				$("#joinfrom").submit(function(e){
					$("input").each(function(i,obj){
						if(!checkFrom($(this))){
							return false;
						}
					})
					
					var regName = /^[가-힣]{2,5}$/;
					var regPw =/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,20}$/; 
					
					if(!regId.test($("#userId").val())){
						alert("아이디는 6자이상, 영어, 숫자 가능합니다.");
						return false;				
					}  
					
					if(!regName.test($("#name").val())){
						alert("이름 2~5 한글만 가능합니다.")
						return false;		
					}
					 
					if(!regPw.test($("#password").val())){
						alert("비밀번호는 8~20자 영어,숫자, 특수문자만 가능합니다.");
						$("#password").focus();
						return false;		 
					}  
					
					var pw = $("#password").val();
					var pwck = $("#passwordCheck").val();
					if(!pw == pwck){
						alert("비밀번호가 다릅니다.");
						$("#password").focus();
						return false;		 
					} 
					
					if(!existId){
						alert("아이디 체크를 먼저 해주세요.");
						return false;
					}
				});
				
								
				$("#idCheck").click(function(){
					if(!checkFrom($("#userId"))){
						return false;
					}
					
					if(!regId.test($("#userId").val())){
						alert("아이디는 6자이상, 영어, 숫자 가능합니다.");
						return false;				
					}
					 
					var userid = $("#userId").val();
					
					$.ajax({
						url:"${pageContext.request.contextPath }/checkId",
						type:"get",
						headers:{"Content-Type":"application/json"},
						dataType:"text",
						data:{"userId":userid},
						success:function(res){
							if(res == "exist"){
								alert("이미 존재하는 아이디입니다.");
								$("#check").text("X");
								existId = false;
							}else if(res == "notexist"){
								alert("사용가능한 아이디입니다.");
								$("#check").text("O");
								existId = true;
							}
						}
					})
				});
				
				function checkFrom(input){
					if(input.val() == ""){
						alert("모두 입력하세여");
						input.focus();
						return false;
					}
					return true;
				}
			</script>
		</div>
	</div>
	<div class="col-sm-2"></div>
</div>
</body>
</html>
