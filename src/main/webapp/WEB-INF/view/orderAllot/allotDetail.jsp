<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="/tag.jsp"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${constants['backend_title']}</title>
<jsp:include page="/WEB-INF/view/batch/head.jsp" />
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script type="text/javascript" src="/scripts/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.wangying_li{ float: left; margin: 10px 20px 10px 0;}
</style>
<script>
var $jqq = jQuery.noConflict(true);
</script>
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<link  rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/jquery.autocomplete.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" >
$(function () {
    var substationList = ${substationList};
    var courierList = ${courierList};
    var sres = [];
    var cres = [];
    $.each(substationList, function(i, item) {
		 var inner_no = "" ;
		sres[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
   }); 
    $.each(courierList, function(i, item) {
		 var inner_no = "" ;
		  cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.inner_no+')'+'('+item.real_name.replace(/\ /g,"")+')';
   }); 
     $jqq("#substationNo").autocomplete(sres, {
  		minChars: 0,
  		max: 12,
  		autoFill: true,
  		mustMatch: false,
  		matchContains: true,
  		scrollHeight: 220,
  		formatItem: function(data, i, total) {
  			return data[0];
  		}
  	}).result(function(event, data, formatted) {
		if(data[0].indexOf(')')>-1){
			 $("#substationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
			 $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	});	   
			 
     $jqq("#courierNo").autocomplete(cres, {
  		minChars: 0,
  		max: 12,
  		autoFill: true,
  		mustMatch: false,
  		matchContains: true,
  		scrollHeight: 220,
  		formatItem: function(data, i, total) {
  			return data[0];
  		}
  	}).result(function(event, data, formatted) {
		if(data[0].indexOf(')')>-1){
			 $("#courierNo").val(data[0].substring(0,data[0].indexOf('('))) ;
			 $("#stips1").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	});	  

});
		</script>
</head>
<body>
	<div class="table" >
		<div class="ta_header" style="height:auto;">
			<form action="allotDetail?layout=no" method="post">
				<div class="ta_h_search"  style="overflow:hidden">
			<div class="wangying_li">统计时间：
			<input id="dateBegin"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" class="put_cont" style="width: 112px" name="beginTime"	value="${params['beginTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~  <input
							id="dateEnd"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text"  class="put_cont" style="width: 112px" name="endTime"	value="${params['endTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /></div>
					<div class="wangying_li">快递员：<input class="put_cont" type="text" name="courierNo" id = "courierNo"	
							placeholder="" value="${param.courierNo}"></div>
					
				   <div class="wangying_li">分站：<input class="put_cont" type="text" name="substationNo" id = "substationNo"	
							placeholder="" value="${param.substationNo}">			
							</div>
						<div class="wangying_li">运单号：<input class="put_cont" type="text" name="orderNo" id = "orderNo"	
							placeholder="" value="${param.orderNo}">						
							<input		class="button_2" type="submit" value="查询">			</div>
				</div>
					<input type="hidden" name="subNo" value="${params['subNo']}" />
					<input type="hidden" name="subName" value="${params['subName']}" />
					 <input type="hidden" name="scanTime" value="${params['scanTime']}"/>
			</form>
		</div>
		<div class="ta_table" style="max-height: none;">
			<table class="ta_ta" width="100%" cellpadding="0" cellspacing="0"
				border="0">
				<thead>
					<tr>
						<th width="10" align="center">序号</th>
						<th width="50" align="center">使用时间</th>
						<th width="50" align="center">运单号码</th>
						<th width="50" align="center">网点编号</th>				
						<th width="50" align="center">网点名称</th>
						<th width="50" align="center">快递员编号</th>	
						<th width="50" align="center">快递员名称</th>
						
					</tr>
				</thead>
				<tbody>	
					<c:forEach items="${list.list}" var="item" varStatus="status">
						<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
							<td align="center">${status.count}</td>
							 	<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.registerTime}" /></td>		
							<td>${item.orderNo}</td>									
							<td>${item.substationNo}</td>
							<td>${item.substationName}</td>
							<td>${item.courierNo}</td>
							<td>${item.courierName}</td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</div>
		<div id="page">
			<pagebar:pagebar total="${list.pages}" current="${list.pageNum}"
				sum="${list.total}" />
		</div>
</body>
</html>
