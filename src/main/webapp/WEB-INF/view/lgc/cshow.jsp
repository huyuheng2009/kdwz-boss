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
		 			<li style="width:220px">快递员用户名：
		 				${courier.userName }
		 			</li>
		 			<li style="width:220px">快递员姓名：
		 				${courier.realName }
		 			</li>
		 			<li style="width:220px">快递分站编号：
		 				${courier.substationNo }
		 			</li>
		 			<li style="width:220px">快递员编号：
		 				${courier.courierNo }
		 			</li>
		 			<li style="width:220px">身份证号：
		 				${courier.idCard }
		 			</li>
		 			<li style="width:220px">电话：
		 				${courier.phone }
		 			</li>
		 			<%-- <li style="width:220px">队列名称：
		 				${courier.queueName }
		 			</li>
		 			<li style="width:220px">头像地址：
		 				${courier.headImage }
		 			</li> --%>
	 			</ul>
		 	</div>
	</div>
</body>
</html>