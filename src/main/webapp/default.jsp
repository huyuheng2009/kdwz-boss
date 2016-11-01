<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript" src="${ctx}/scripts/ckeditor.js"></script>

<script type="text/javascript">

$(function(){
	
	 $('#look').click(function(){
		 var data = CKEDITOR.instances.editor1.getData(); 
		alert(data);
	});
});


</script>
</head>
<body>
	<textarea class="ckeditor" cols="80" id="editor1" name="editor1" rows="10">
	</textarea>
	<input type="button" value="look" id="look"/> 
</body>

</html>