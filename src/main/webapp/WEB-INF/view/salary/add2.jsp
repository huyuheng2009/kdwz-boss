<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<jsp:include page="/WEB-INF/view/batch/head.jsp" />
<script>
	var $jqq = jQuery.noConflict(true);
</script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/themes/default/jquery.autocomplete.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/js/jquery-1.6.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	function cacel() {
		var api = frameElement.api, W = api.opener;
		api.close();
	}
	
	function add(){
		 var api = frameElement.api, 
		 W = api.opener;
		   var reg = /^[0-9]+(.[0-9]{1,2})?$/;
		 var message = $.trim($('input[name=message]').val());	 
		if(message.length<1 || message==''){
			alert("请输入需要增加的工资扣除项名称");
			return false;
		}
		
		 $.ajax({
			 type: "post",//使用get方法访问后台
	           dataType: "json",//返回文本格式
	           url: "<%=request.getContextPath()%>/salary/add_2",//要访问的后台地址
			data : {
				'message' : message
			
			},//要发送的数据
			beforeSend : function() { //请求成功前触发的局部事件
				alert("正在努力请求数据，请稍候......");
			},
			success : function(msg) {//msg为返回的数据，在这里做数据绑定         						
				if (msg.code == 0) {
					alert(msg.message, '', function() {
						W.location.reload();
						api.close();
					})
				} else if (msg.code == 1) {
					alert(msg.message);
				} else {
					alert('新增失敗', '', function() {
						api.reload;
						api.close();
					})
				}

			}
		});
	}
	
	
</script>
<style>
.out_button {
	width: 480px;
	text-align: center
}

.out_button .button {
	float: inherit;
	display: inline-block;
	margin: 0 20px 0 0;
}

.out_button .button_2 {
	float: inherit;
	display: inline-block;
}

.out_button .button_1 {
	 text-align:center; 	
	 	  color: #fff;
    background: #0866c6;
     width: 120px;
    height: 32px;
    line-height: 30px;
      display: inline-block;
      margin: 0 10px 0 0;
}

.input_li {
	overflow: auto;
	zoom: 1;
	margin: 30px 0 0 0;
	float: left;
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<form id="myform">
		<div class="outnew_cont" style="margin: 0;">
			<div class="input_li">
				<div class="input_name">
					<b>*</b>工资扣除项
				</div>
				<div class="input_wri">
					<input style="width: 240px; height: 28px;" type="text"
						id="message" name="message">
				</div>
			</div>

		</div>
		<div class="out_button">
			<a href="javascript:void(0);" onclick="add()" class="button_1">保存</a>
			<a href="javascript:void(0);" onclick="cacel();" class=" button_2">取消</a>
		</div>

	</form>
</body>
</html>
