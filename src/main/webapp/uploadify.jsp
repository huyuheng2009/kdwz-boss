<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${ ctx}/scripts/uploadify/uploadify.css">
<script type="text/javascript" src="${ ctx}/scripts/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/uploadify/swfobject.js"></script>
<script type="text/javascript">
$(function() {
    $("#file_upload").uploadify({
        'swf'           : '${ctx}/scripts/uploadify/uploadify.swf',
        'uploader': '${ctx}/merchant/fileUpload',
        'auto': false,                            //是否自动上传 
        'multi': true,                            //是否允许多个上传 
        'removeCompleted': false,                            //上传完毕上传列表是否去除 
        'progressData': 'percentage',                     //进度 percentage或speed 
        'buttonText': 'BROWSE...',     
        'method': 'post',                           //提交方法  post或get 
        'formData'          : {'id':'测试','af':'ddd'},  
        'fileSizeLimit': '200MB',  
        'uploadLimit': 4,   
        'onUploadComplete': function (file) {                  //上传完成时事件 
            alert('文件： ' + file.name + '上传完成');
          //  $('#file_upload').uploadify('disable', true);       //设置上传按钮不可用 
        },
        'onUploadError': function (file, errorCode, errorMsg, errorString) {    //错误提示 
            alert('文件：' + file.name + '上传失败！错误代码：' + errorString);
        }
    });
    $('#upload').click(function(){
    	 $('#file_upload').uploadify('upload');    
    });    
        
});

</script>
</head>
<body>

<input type="file" name="file_upload" id="file_upload"/>
<input type="button" id="upload" value="wwwwwwwwwwwwwwwwwwww上传"/>

dfdf

</body>

</html>