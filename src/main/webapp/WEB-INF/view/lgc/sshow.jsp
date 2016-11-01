<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
</head>
<body>
	<div class="content">
		 	<div class="item">
	 			<ul style="padding-top:30px;padding-left: 20px;">
		 			<li style="width:220px">快递分站编号：
		 				${substation.substationNo }
		 			</li>
		 			<li style="width:220px">快递公司名称：
		 				<u:lgc lgcNo="${substation.lgcNo }"></u:lgc>
		 			</li>
		 			<li style="width:220px">快递分站名称：
		 				${substation.substationName }
		 			</li>
		 			<li style="width:220px">快递分站地址：
		 				${substation.substationAddr }
		 			</li>
		 			<li style="width:220px">联系方式：
		 				${substation.phone }
		 			</li>
		 			<li style="width:220px">位置坐标：
		 				${substation.location }
		 			</li>
		 			
	 			</ul>
		 	</div>
	</div>
</body>
</html>