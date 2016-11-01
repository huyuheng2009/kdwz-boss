<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> </head>
<body>
<c:if test="${picPath=='' }">
	<img src="${ ctx}/themes/default/images/home_index.png" style="max-width:1200px;"/>
</c:if>
<c:if test="${picPath !='' }">
	<img src="${ ctx}${picPath}" style="max-width:1200px;"/>
</c:if>

</body>

</html>