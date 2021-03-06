<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<style>
	.uploadResult{
		width: 100%;
		background-color: gray; 
	}
	.uploadResult ul{
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	.uploadResult ul li{
		list-style: none;
		padding: 10px;
		align-content: center;
		text-align: center;
	}
	.uploadResult ul li img{
		width: 100px;
	}
	.uploadResult ul li span{
		color: white;
	}
	.bigPictureWrapper{
		position: absolute;
		display: none;
		justify-content: center;
		align-items: center;
		top:0%;
		width: 100%;
		height: 100%;
		background-color: gray;
		z-index: 100;
		background: rgba(255,255,255,0,5);
	}
	.bigPicture{
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.bigPicture img{
		width: 600px;
	}
</style>
<%@include file="../includes/header.jsp" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tables</h1>
    </div>
</div>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Board List Page</div>
            <div class="panel-body">
                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>Title</label><input class="form-control" name="title">
                    </div>
                    <div class="form-group">
                        <label>Text area</label><textarea class="form-control" rows="3" name="content"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Writer</label><input class="form-control" name="writer">
                    </div>
                    <button type="submit" class="btn btn-default">Submit Button</button>                    
                    <button type="reset" class="btn btn-default">Reset Button</button>                    
                </form>
            </div>
        </div>
    </div>
</div>
<!-- ???????????? ?????? -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">File attach</div>
            <div class="panel-body">
	            <div class="form-group uploadDiv">
	            	<input type="file" name="uploadFile" multiple="multiple">
	            </div>
                <div class="uploadResult">
                	<ul>
                	</ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../includes/footer.jsp" %>

<script type="text/javascript" >
	$(document).ready(function () {
		// submit ?????? ??????
		var formObj = $('form[role="form"]');
		$('button[type="submit"]').on('click', function (e) {
			// ????????? ?????? ???, submit ????????? ?????? ??????
			e.preventDefault();
			var str = '';
			$('.uploadResult ul li').each(function (i, obj) {
				var jobj = $(obj);
				str += "<input type='hidden' name='attachList[" + i + "].fileName' value='"+ jobj.data('filename') +"'>";
				str += "<input type='hidden' name='attachList[" + i + "].uuid' value='"+ jobj.data('uuid') +"'>";
				str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='"+ jobj.data('path') +"'>";
				str += "<input type='hidden' name='attachList[" + i + "].fileType' value='"+ jobj.data('type') +"'>";
			});
			formObj.append(str).submit();
		})
		
		// ?????? ????????? ??? ?????????, ?????? ?????? ??????
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; // 5MB
		// ?????? ????????? ??????
		function checkExtension(fileName, fileSize) {
			if(fileSize >= maxSize){
				alert("?????? ????????? ??????");
				return false;
			}
			
			if(regex.test(fileName)){
				alert("?????? ???????????? ????????? ????????? ??? ??? ????????????.");
				return false;
			}
			return true;
		}
		
		// input type = file ??? readonly?????? ????????? ????????? ????????? ????????? ??? ???????????? ????????? ?????? ?????? ??????
		// inputFile??? inputFile[0] ??? ?????????.
		// inputFile??? name??? ????????? ???????????? 
		// inputFile[0]??? input ?????? ????????? ????????????.
		// ???????????? files ??? ???????????? ???????????? inputFile[0]?????? ??????????????????.
		// ????????? ?????? ?????? ??? ???????????? ?????? ???????????? ????????? ???????????? object??? ????????? ????????? ?????????
		// ????????????????????? ????????? ?????? ?????? ???????????? ??????.
		$('input[type="file"]').change(function(e) {
			// ???????????? ?????? FormData ?????? ??????
			var formData = new FormData();
			var inputFile = $('input[name="uploadFile"]');
			var files = inputFile[0].files;
			
			for (var i = 0; i < files.length; i++) {
				if(!checkExtension(files[i].name, files[i].size)) return false;
				formData.append('uploadFile', files[i]);
			}
			console.log(formData)
			// ?????? ??????????????? ????????? processData : false, contentType : false, ??? ???????????? ??????.
			$.ajax({
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false,
				data : formData,
				type : 'POST',
				success : function (result) {
					showUploadedResult(result);
				}
			});
		})
		
		function showUploadedResult(uploadResultArr) {
			if(!uploadResultArr || uploadResultArr.length == 0) return false;

			var uploadUL = $('.uploadResult ul');
			var str = '';
			$(uploadResultArr).each(function (i, obj) {
				if(obj.image){
					var fileCallPath = encodeURIComponent(obj.uploadPath + '/s_' + obj.uuid + '_' + obj.fileName);
					str += "<li data-path='" + obj.uploadPath + "'";
					str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
					str += "<div>";
					str += "<span>" + obj.fileName + "</span>";
					str += "<button type='button' class='btn btn-warning btn-circle'data-file=\'"+ fileCallPath + "\' data-type='image'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/display?fileName=" + fileCallPath + "'>";
					str += "</div></li>";
				}else{
					var fileCallPath = encodeURIComponent(obj.uploadPath + '/' + obj.uuid + '_' + obj.fileName);
					var fileLink = fileCallPath.replace(new RegExp(/\\/g), '/');
					str += "<li data-path='" + obj.uploadPath + "'";
					str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>";
					str += "<div>";
					str += "<span>" + obj.fileName + "</span>";
					str += "<button type='button' class='btn btn-warning btn-circle'data-file=\'"+ fileCallPath + "\' data-type='file'><i class='fa fa-times'></i></button><br>";
					str += "<img src='/resources/img/attach.png></a>";
					str += "</div></li>";					
				}
			});
			uploadUL.append(str);
		}
		
		$('.uploadResult').on('click', 'button', function (e) {
			var targetFile = $(this).data('file');
			var type = $(this).data('type');
			var targetLi = $(this).closest("li");
			
			$.ajax({
				url : '/deleteFile',
				data : {fileName : targetFile, type : type},
				dataType : 'text',
				type : 'POST',
				success : function (result) {
					alert(result);
					targetLi.remove();
				}
			});
		});
	});// document ready??? ???
</script>