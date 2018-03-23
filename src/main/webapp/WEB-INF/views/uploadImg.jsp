<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#dropBox {
	margin-top:50px;
	height: 300px;
	background:gray;
	overflow: auto;
}

#dropBox img {
	max-width: 100%;
	max-height: 100%;
}

#dropBox div.item {
	position: relative;
	width: 100px;
	height: 130px;
	margin: 5px;
	float: left;
}

.del{
	position: absolute;
	right:10px;
	top:10px;
}
</style>
	<div class="row" style="margin-top: 50px;">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<div style="margin-top: 50px;">
				<div class="col-sm-6">
					<button type="button" class="btn btn-success">이미지 업로드</button>
				</div>
				<div class="col-sm-6">
					<ul class="pager" style="margin:0;">
						<li><a href="#">Previous</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</div>
				
			</div>
			<form id="f1" action="uploadDrag" method="post" enctype="multipart/form-data">
			</form>

			<div id="dropBox" class="col-sm-12">
			</div>
			</div>
		<div class="col-sm-2"></div>
	</div>
	
	<script>
		var formData = new FormData();

		$("#dropBox").on("dragenter dragover", function(e) {
			e.preventDefault();
		});

		$("#dropBox").on("drop", function(e) {
			e.preventDefault();

			var files = e.originalEvent.dataTransfer.files;

			
			
			for(var i=0; i<files.length; i++){
				console.log(files[i]);
				var file = files[i];
				
				var reader = new FileReader();
				
				reader.addEventListener("load", function() {
					var divObj = $("<div>").addClass("item");
					var imgObj = $("<img>").attr("src", reader.result);
					divObj.html(imgObj);

					$("#dropBox").append(divObj);
				}, false);

				if (file) {
					reader.readAsDataURL(file);
				}

				if (formData == null) {
					formData = new FormData();
				}

				formData.append("files", file);
			}
			
			
			//<input type="file" name="files" value="file">
		});

		$("#f1").submit(function(e) {
			e.preventDefault();
			
			$.ajax({
				url : "uploadDrag",
				data : formData,
				processData : false,
				contentType : false,
				type : "post",
				dataType : "json",
				success : function(result) {
					console.log(result);
					
					$("#dropBox").empty();
					
					if(result == null){
						alert("전송 실패");
					}else{
						$(result).each(function(i,obj){
							var divObj = $("<div>").addClass("item");
							var imgObj = $("<img>").attr("src","displayFile?filename="+obj);
							divObj.append(imgObj);
							
							var inputObj = $("<input>").val("X").addClass("del").attr("type","button").attr("data-del",obj);
							
							divObj.append(inputObj);
							
							$("#dropBox").append(divObj);
							
							formData = null;
						})
					} 
				}
			}) 
		})
		
		$("#dropBox").on("click",".del",function(){
			var fileName = $(this).attr("data-del"); 
				
			console.log(fileName);
			
			var obj = $(this);
			$.ajax({
				url : "deleteFile",
				data : {"filename":fileName},
				type : "get",
				dataType : "text",
				success : function(result) {
					if(result == "success"){
						alert("삭제 성공");
						obj.parent(".item").remove();
					}else{
						alert("삭제 실패");
					}
				}
			})
		})
	</script>
</body>
</html>