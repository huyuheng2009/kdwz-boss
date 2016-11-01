<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>错误</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/themes/default/error.css" />

</head>
<body>
	<div id="error">
		<div id="">
			<img width="600" src="${pageContext.request.contextPath}/themes/default/images/404.jpg"></img>
		</div>
		<div id="">
			<ul>
				<li><a href="javascript:history.go(-1);"><返回上一页</a></li>
				<li><a href="/"><返回首页</a></li>
			</ul>
			<ul style="clear: both;">
				<li style="color: #9a9a9a">您的输入有误，请检查是否包含非法字符</li>
			</ul>
		</div>
	</div>
	
</body>

</html>