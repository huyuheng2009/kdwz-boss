<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
</head>
<body>
	 <div class="content">
	 	<div class="item">
	 		<ul style="padding-top:20px">
	 			<li style="width:220px">快递公司编号：
		 				${lgc1.lgcNo }
		 			</li>
		 			<li style="width:220px">快递公司名称：
		 				${lgc1.name }
		 			</li>
		 			<li style="width:220px">快递公司拼音：
		 				${lgc1.pingyin }
		 			</li>
		 			<li style="width:220px">联系方式：
		 				${lgc1.contact }
		 			</li>
		 			<li style="width:220px">网址：
		 				${lgc1.website }
		 			</li>
		 			<!-- <li style="width:220px">logo：
		 			</li> -->
	 		</ul>
	 	</div>
	 </div>
	 
	 
</body>

</html>