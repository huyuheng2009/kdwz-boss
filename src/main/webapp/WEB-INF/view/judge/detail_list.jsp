<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
$(function(){
	$("#export").click(function(){
		var form = document.getElementById("detail_form");
		if(form){
			form.action="${ctx}/judge/exportData";
			form.submit();
		}
		form.action="${ctx }/judge/detailList";
	});
	$("#submitBtn").click(function(){
		var form = document.getElementById("detail_form");
		if(form){
			form.action="${ctx }/judge/detailList";
			form.submit();
		}
	});
	
})
</script>
<style type="text/css">
.search form{margin:10px 0;margin-left:10px;}
.col1{width:100px;}
.col3{width:300px;}
.col3 a{margin:0 5px;}
.disabled{color:#666 !important;}
</style>
</head>
<body>
	<div class="search">
		<div class="soso">
		        <div class="soso_left search_cont">
					<form:form action="${ctx }/judge/detailList" method="post" id="detail_form">
						<span>
							寄件时间：
						</span>
						<input value="${params.beginTime }" type="text" name='beginTime' onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>——
						<input value="${params.endTime }" type="text" name='endTime' onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						快递员：<input value="${params.courier_no }" type="text" name='courier_no'/>
						运单号：<input value="${params.lgc_order_no }" type="text" name='lgc_order_no'/>
						<input type="hidden" value="detailList" name="serviceName"/>
						<input  type="submit" value="查询" id="submitBtn" class="button input_text  medium sear_butt an_a an_aon" style="margin-left:10px;" />
					</form:form>
				</div>
			</div>
		<div class="tableble_search ">
	        <div class="operator">
				<input  type="submit" value="导出" id="export" class="button input_text  medium sear_butt an_a an_aon" style="margin-left:10px;"/>
			</div>
		</div>
		
	</div>
	<div>
		<table class="t2 tableble_table ta_ta" width="100%">
		   <thead>
		   	<th>序号</th>
		   	<th>寄件时间</th>
		   	<th>订单号</th>
		   	<th>运单号</th>
		   	<th>快递员编号</th>
		   	<th>快递员</th>
		   	<th>所属网点</th>
		   	<th>五星</th>
		   	<th>四星</th>
		   	<th>三星</th>
		   	<th>两星</th>
		   	<th>一星</th>
		   	<th>标签</th>
		   	<th>评价</th>
		   </thead>
		   <c:forEach items="${list }" var="item" varStatus="status">
		   	<tr>
		   		<td>${status.index+1 }</td>
		   		<td>${item.take_order_time}</td>
		   		<td>${item.order_no}</td>
		   		<td>${item.lgc_order_no}</td>
		   		
		   		<td>${item.courier_no }</td>
		   		<td>${item.real_name}</td>
		   		<td>${item.substation_name}</td>
		   		
		   		<td><c:out value="${item.star eq 5 ?'√':''}"></c:out></td>
		   		<td><c:out value="${item.star eq 4 ?'√':''}"></c:out></td>
		   		<td><c:out value="${item.star eq 3 ?'√':''}"></c:out></td>
		   		
		   		<td><c:out value="${item.star eq 2 ?'√':''}"></c:out></td>
		   		<td>
		   		<c:out value="${item.star eq 1 ?'√':''}"></c:out>
		   		</td>
		   		<td>${item.label_txt}</td>
		   		<td>${item.comments}</td>
		   	</tr>
			</c:forEach>
		</table>
		
	</div>
	<div id="page">
		<pagebar:pagebar total="${total}" 
			current="${current}" sum="${sum}" />
	</div>
	
</body>

</html>