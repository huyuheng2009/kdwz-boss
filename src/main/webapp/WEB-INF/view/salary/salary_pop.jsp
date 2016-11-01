<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function(){
		$('.submit').click(function(){
			 var api = frameElement.api, 
				 W = api.opener;
			   var reg = /^[0-9]+(.[0-9]{1,2})?$/;
			   var reg1 = /^(?:0|[1-9][0-9]?|100)$/;//100内整数
			  var courierNo = $.trim($('input[name=courierNo]').val());	 //价格
			 var takeTcf = $.trim($('input[name=takeTcf]').val());	 //价格
			 var takeTcw = $.trim($('input[name=takeTcw]').val());	//重量
			 var takeTcc = $.trim($('input[name=takeTcc]').val());	//件数
			 
			 var sendTcf = $.trim($('input[name=sendTcf]').val());	 //价格
			 var sendTcw = $.trim($('input[name=sendTcw]').val());	//重量
			 var sendTcc = $.trim($('input[name=sendTcc]').val());	//件数

			 
			 var forTcf = $.trim($('input[name=forTcf]').val());	 //价格
			 var forTcw = $.trim($('input[name=forTcw]').val());	//重量
			 var forTcc = $.trim($('input[name=forTcc]').val());	//件数
		
			 if(takeTcf !="0"){
				 if(takeTcf.length<1 || takeTcf == ''){
					 alert("请输入同城收件运费！");
					 return;
				 }
				 if(!reg1.test(takeTcf)){
					 alert("请输入正确的同城收件运费格式，只能是1-100整数！");
					 return;	 				 
				 }			 				
			 }			 			 
			 if(takeTcw !="0.0"){
				 if(takeTcw.length<1 || takeTcw == ''){
					 alert("请输入同城收件重量！");
					 return;
				 }
				 if(!reg.test(takeTcw)){
					 alert("请输入正确的同城收件重量格式，可保留两位小数！");
					 return;	 				 
				 }
			 }
			 if(takeTcc !="0.0"){
				 if(takeTcc.length<1 || takeTcc == ''){
					 alert("请输入同城收件件数！");
					 return;
				 }
				 if(!reg.test(takeTcc)){
					 alert("请输入正确的收件件数格式，可保留两位小数！");
					 return;	 				 
				 }	 
			 }
			  
			 
			 
			 
			 if(sendTcf !="0"){
				 if(sendTcf.length<1 || sendTcf == ''){
					 alert("请输入同城派件运费！");
					 return;
				 }
				 if(!reg1.test(sendTcf)){
					 alert("请输入正确的派件运费格式，只能是1-100整数！");
					 return;	 				 
				 }			 				
			 }			 			 
			 if(sendTcw !="0.0"){
				 if(sendTcw.length<1 || sendTcw == ''){
					 alert("请输入同城派件重量！");
					 return;
				 }
				 if(!reg.test(sendTcw)){
					 alert("请输入正确的同城派件重量格式，可保留两位小数！");
					 return;	 				 
				 }
			 }
			 if(sendTcc !="0.0"){
				 if(sendTcc.length<1 || sendTcc == ''){
					 alert("请输入同城收件件数！");
					 return;
				 }
				 if(!reg.test(sendTcc)){
					 alert("请输入正确的同城收件件数格式，可保留两位小数！");
					 return;	 				 
				 }	 
			 }
			 
			 					 
			 if(forTcf !="0"){
				 if(forTcf.length<1 || forTcf == ''){
					 alert("请输入外件运费！");
					 return;
				 }
				 if(!reg1.test(forTcf)){
					 alert("请输入正确的外件运费格式，只能是1-100整数！");
					 return;	 				 
				 }			 				
			 }			 			 
			 if(forTcw !="0.0"){
				 if(forTcw.length<1 || forTcw == ''){
					 alert("请输入外件派件重量！");
					 return;
				 }
				 if(!reg.test(forTcw)){
					 alert("请输入正确的外件派件重量格式，可保留两位小数！");
					 return;	 				 
				 }
			 }
			 if(forTcc !="0.0"){
				 if(forTcc.length<1 || forTcc == ''){
					 alert("请输入外件件数！");
					 return;
				 }
				 if(!reg.test(forTcc)){
					 alert("请输入正确的外件件数格式，可保留两位小数！");
					 return;	 				 
				 }	 
			 }
			
	 
			 $.ajax({
				 type: "post",//使用get方法访问后台
		           dataType: "json",//返回文本格式
		           url: "<%=request.getContextPath()%>/salary/saveCompile",//要访问的后台地址
				data : {
					'courierNo' : courierNo,
					'takeTcf':takeTcf,
					'takeTcw':takeTcw,
					'takeTcc':takeTcc,
					'sendTcf':sendTcf,
					'sendTcw':sendTcw,
					'sendTcc':sendTcc,
					'forTcf':forTcf,
					'forTcw':forTcw,
					'forTcc':forTcc

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

		});
	});
</script>
</head>
<body style="background: #fff; font-size: 12px; color: #333;">
	<!--é¡µé¢èæ¯-->
	<div class="salary_pop_list">
		<div class="salary_pop_cont">
			<input type="hidden" name="courierNo" value="${map.courierNo}" />
			<div class="shou_li">
				<strong><b></b>网点</strong><input type="text" class="popinput01"
					id="substationName" value="${map.substationName}"
					disabled="disabled" />
			</div>
			<div class="shou_li">
				<strong><b></b>快递员编号</strong><input type="text" class="popinput01"
					id="innerNo" value="${map.innerNo}" disabled="disabled" />
			</div>
			<div class="shou_li">
				<strong><b></b>快递员</strong><input type="text" class="popinput01"
					id="realName" value="${map.realName}" disabled="disabled" />
			</div>
			<div class="shou_li">
				<strong><b></b>联系电话</strong><input type="text" class="popinput01"
					id="phone" value="${map.phone}" disabled="disabled" />
			</div>
		</div>
	</div>
	<div class="salary_pop_list">
		<div class="salary_pop_list_tit">同城收件</div>
		<div class="salary_pop_cont">
			<div class="shou_li">
				<strong><b></b>运费</strong><input type="text" class="sinput01"
					name="takeTcf" value="${map.takeTcf}" maxlength="10" />%
			</div>
			<div class="shou_li">
				<strong><b></b>重量</strong><input type="text" class="sinput01"
					name="takeTcw" value="<fmt:formatNumber value="${map.takeTcw}" pattern="0.00"/>"  maxlength="10" />元/kg
			</div>
			<div class="shou_li">
				<strong><b></b>件数</strong><input type="text" class="sinput01"
					name="takeTcc" value="<fmt:formatNumber value="${map.takeTcc}" pattern="0.00"/>" maxlength="10" />元/件
			</div>
		</div>
	</div>
	<div class="salary_pop_list">
		<div class="salary_pop_list_tit">同城派件</div>
		<div class="salary_pop_cont">
			<div class="shou_li">
				<strong><b></b>运费</strong><input type="text" class="sinput01"
					name="sendTcf" value="${map.sendTcf}" maxlength="10" />%
			</div>
			<div class="shou_li">
				<strong><b></b>重量</strong><input type="text" class="sinput01"
					name="sendTcw" value="<fmt:formatNumber value="${map.sendTcw}" pattern="0.00"/>" maxlength="10" />元/kg
			</div>
			<div class="shou_li">
				<strong><b></b>件数</strong><input type="text" class="sinput01"
					name="sendTcc"  value="<fmt:formatNumber value="${map.sendTcc}"  pattern="0.00"/>"  maxlength="10" / >元/件
			</div>
		</div>
	</div>
	<div class="salary_pop_list">
		<div class="salary_pop_list_tit">&nbsp;外&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;</div>
		<div class="salary_pop_cont">
			<div class="shou_li">
				<strong><b></b>运费</strong><input type="text" class="sinput01"
					name="forTcf" value="${map.forTcf}" maxlength="10" />%
			</div>
			<div class="shou_li">
				<strong><b></b>重量</strong><input type="text" class="sinput01"
					name="forTcw" value="<fmt:formatNumber value="${map.forTcw}"  pattern="0.00"/>"  maxlength="10" />元/kg
			</div>
			<div class="shou_li">
				<strong><b></b>件数</strong><input type="text" class="sinput01"
					name="forTcc" value="<fmt:formatNumber value="${map.forTcc}" pattern="0.00"/>" maxlength="10" />元/件
			</div>
		</div>
	</div>
	<div class="online_button">
		<input type="submit" class="submit" value="保存" />
	</div>
</body>
</html>
