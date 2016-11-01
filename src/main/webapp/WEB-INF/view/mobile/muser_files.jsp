<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	 var api = frameElement.api, W = api.opener;

 	
}); 

</script>
</head>
<body>
<div class="show_pic" style="margin-top: 60px;">
   <c:if test="${!empty muserMap.file1 }">
         <div class="show_picli"><img src="${muserMap.file1}" /></div>
	</c:if>
   <c:if test="${!empty muserMap.file2 }">
         <div class="show_picli"><img src="${muserMap.file2}" /></div>
	</c:if>
</div>
</body>
</html>