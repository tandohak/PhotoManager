<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
#dropBox {
	margin-top: 10px;
	height: 300px;
	background: #ddd;
	overflow: auto;
	text-align: center;
	line-height: 300px;
}

#uploadBox {
	padding: 0px;
	display: none;
}

#imgBox {
	margin-top: 50px
	overflow: auto;
}

#dropBox img, #imgBox img {
	max-width: 100%;
	max-height: 100%;
}

#detailShow {
	display: none;
	position: absolute;
	width: 100%;
	height: 100%;
	background: rgba(255, 255, 255, 0.3);
	top: 0;
	left: 0;
	z-index: 9999;
	text-align: center;
	/* line-height: 100%; */
}

#detailShow img {
	text-align: center;
	margin-top: 50px;
}

#dropBox div.item, #imgBox  div.item {
	position: relative;
	width: 100px;
	height: 130px;
	margin: 5px;
	float: left;
}

.del {
	position: absolute;
	right: 10px;
	top: 10px;
}
</style>
<div class="row" style="margin-top: 50px;">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<div style="margin-top: 50px;">
			<div class="col-sm-6">
				<input type="button" class="btn btn-success" id="openUpload"
					value="이미지 업로드">
			</div>
			<div class="col-sm-6">
				<ul class="pager" style="margin: 0;">
					<li><a href="#">Previous</a></li>
					<li><a href="#">Next</a></li>
				</ul>
			</div>

		</div>

		<div id="uploadBox" class="col-sm-12">
			<p>이미지를 드랍앤&드래그로 넣으세요</p>
			<div id="dropBox"></div>
			<br>
			<form id="f1" action="uploadDrag" method="post"
				enctype="multipart/form-data">
				<input type="submit" class="btn btn-success" value="전송">
			</form>
		</div>

		<div id="imgBox" class="col-sm-12">
			<c:if test="${imgList != null }">
				<c:forEach items="${imgList }" var="item">
					<div class="item">
						<img src="displayFile?filename=${item.vo.gpath }">  <br>
						<span>${item.filename}</span><br>
						<span><fmt:formatDate value="${item.vo.uploadDate}" pattern="yyyy-MM-dd"/></span>
						<button class="del" data-no="${item.vo.gno}" data-del="${item.vo.gpath }">X</button>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<div class="col-sm-2"></div>

	<div id="detailShow" class="col-sm-12">
		<img src="">
	</div>
</div>
<script>
	var formData = new FormData();

	$("#openUpload").click(function() {
		$("#uploadBox").toggle();
	})

	$("#dropBox").on("dragenter dragover", function(e) {
		e.preventDefault();
	});

	$("#dropBox").on("drop", function(e) {
		e.preventDefault();

		var files = e.originalEvent.dataTransfer.files;
		var i = 0;
		var length = files.length;

		var reader = new FileReader();

		reader.addEventListener("load", function() {
			var divObj = $("<div>").addClass("item");
			var imgObj = $("<img>").attr("src", reader.result);
			divObj.html(imgObj);

			$("#dropBox").append(divObj);

			i++;

			if (files[i]) {
				reader.readAsDataURL(files[i]);
				formData.append("files", files[i]);
			}

		}, false);

		if (formData == null) {
			formData = new FormData();
		}

		if (files[0]) {
			reader.readAsDataURL(files[0]);

			formData.append("files", files[0]);
		}

		//<input type="file" name="files" value="file">
	});

	$("#f1").submit(
			function(e) {
				e.preventDefault();

				formData.append("id", "${login.userId}");

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

						if (result == null) {
							alert("전송 실패");
						} else {
							$(result).each(
									function(i, obj) {
										var divObj = $("<div>")
												.addClass("item");
										var imgObj = $("<img>").attr("src",
												"displayFile?filename=" + obj.vo.gpath);
										divObj.append(imgObj);
										divObj.append("<br><span>"+obj.savedName+"</span><br>");
										var date = new Date(obj.vo.uploadDate);
										
										divObj.append("<span>"+date.getFullYear()+"-" + (date.getMonth()+1) +"-"+ date.getDate() +"</span>");

										var inputObj = $("<input>").val("X")
												.addClass("del").attr("type",
														"button").attr(
														"data-del", obj.vo.gpath);  

										divObj.append(inputObj);

										$("#imgBox").append(divObj);

										formData = null;
									})
						}
					}
				})
			})

	$("#imgBox").on("click",".item img",
			function() {
				$("#detailShow").toggle();
				var parent = $(this).parent(".item");
				var fileName = parent.find(".del").attr("data-del");
				fileName = fileName.replace("s_", "");

				var imgObj = $("<img>").attr("src",
						"displayFile?filename=" + fileName);

				$("#detailShow").html(imgObj);  
			})

	$("#detailShow").click(function() {
		$("#detailShow").toggle();
	})

	$("#imgBox").on("click", ".del", function() {
		var conf = confirm("정말 삭제 하시겠습니까?");

		if (!conf) {
			return false;
		}

		var fileName = $(this).attr("data-del");
		var gno = $(this).attr("data-no");

		var obj = $(this);

		$.ajax({
			url : "deleteFile",
			data : {
				"filename" : fileName,
				"gno" : gno
			},
			type : "get",
			dataType : "text",
			success : function(result) {
				console.log(result);
				if (result == "success") {
					alert("삭제 성공");
					obj.parent(".item").remove();
				} else {
					alert("삭제 실패");
				}
			}
		})
	})
</script>
</body>
</html>