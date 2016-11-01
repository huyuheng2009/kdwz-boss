<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	//获取时间段，单位、天
	function getToday(date, less) {
		var yesterday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24* less;
		var yesterday = new Date();
		yesterday.setTime(yesterday_milliseconds);

		var strYear = yesterday.getFullYear();
		var strDay = yesterday.getDate();
		var strMonth = yesterday.getMonth() + 1;
		if (strMonth < 10) {
			strMonth = "0" + strMonth;
		}
		datastr = strYear + "-" + strMonth + "-" + strDay;
		return datastr;
	}
	  $(function () {
	       $('#sixMonth').click(function(){
	    	   var now= new Date();
	    		var begin = getToday(now, 180);
				var end = getToday(now, 0);
	    	   $('#dateBegin').val(begin);
	    	   $('#dateEnd').val(end);	   
	     	});     
	      });
	$(function() {
		$('#month').click(function() {
			var now = new Date();
			var begin = getToday(now, 30);
			var end = getToday(now, 0);
			$('#dateBegin').val(begin);
			$('#dateEnd').val(end);
		});
	});
	$(function() {
		$('#threeMonth').click(function() {
			var now = new Date();
			var begin = getToday(now, 90);
			var end = getToday(now, 0);
			$('#dateBegin').val(begin);
			$('#dateEnd').val(end);
		});
	});
	//导出
	$(function() {
		$('#derive_btn').click(function() {
			var action = $("form:first").attr("action");
			$("form:first input[name=serviceName]").val('monthUserRevCountExport');
			$("form:first").attr("action", "${ctx}/substatic/export").submit();
			$("form:first").attr("action", action);
		});
	});
	function rev(orderNo) {
		//         	 if(confirm('确认快件已经到达分站？')){
		//            		 $.ajax({url:'/scan/srev',data:{'orderNo':orderNo},success:function(msg){
		//            			 if(msg==1){
		//            				 alert('操作成功');
		//            				 location.reload();
		//            			 }else{
		//            				 alert(msg);
		//            			 }
		//            		 }});
		//            	 }
	}

	$(function() {
		trHover('t2');
		$('#reload_btn').click(function() {
			location.reload();
		});
	});

	function checkCNa(courierNo) {
		//         	$.post('/substatic/courierNoCheck',{'courierNo':courierNo},function(msg){
		//            		$('#ConNa').val(msg.courierNam);        
		//        		 },"JSON");
		$.ajax({
			url : '/substatic/checkMonthUser',
			data : {
				'courierNo' : courierNo
			},
			success : function(msg) {
				$('#ConNa').val(msg.courierNam);
			}
		});
	}
	  function checkSbNa(substationNo){
	       	 $.ajax({url:'/substatic/subNoCheck',data:{'substationNo':substationNo},success:function(msg){    		 
	       		$('#subNa').val(msg.substaNam);
	   		 }});   	
	        }
	  
	function checkMonthSettleNo(monthSettleNo) {
		$.ajax({
			url : '/substatic/checkMonthUser',
			type:'get',
			data : {
				'monthSettleNo' : monthSettleNo
			},
			success : function(msg) {
				$('#company').val(msg.company);
				$('#name').val(msg.name);
				$('#phone').val(msg.phone);
				$('#address').val(msg.address);
			}
		});
	}
</script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/substatic/monthUserRevCount"
					method="get">
					<input type="hidden" name="serviceName" value="" />
					<ul>
						<li><span>统计年份：</span> <input id="dateBegin"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy',readOnly:true,maxDate:'\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"
							value="${params['beginTime']}"
							onClick="WdatePicker({dateFmt:'yyyy '})" /> </li>
						<li>网点编号：<input type="text" placeholder="网点编号"value="${params['substationNo']}" name="substationNo"	onblur="checkSbNa($(this).val());"></input>
						<input 	readonly="readonly" type="text" value="${params['subNa']}"	placeholder="网点名称" name="subNa" id="subNa"></input></li>
						<li>月结客户编号：<input type="text" placeholder="月结客户编号"	value="${params['monthSettleNo']}" name="monthSettleNo"	onblur="checkMonthSettleNo($(this).val());"></input> 
						<input	readonly="readonly" type="text" value="${params['name']}"	placeholder="月结客户名称" name="name" id="name"></input>						
						</li>			
						<li><input class="button input_text  medium blue_flat"
							type="submit" value="查询" /></li>
						<li><input class="button input_text  medium blue_flat"
							type="reset" value="重置" /></li>
					</ul>
				</form:form>
			</div>
			<!-- search_cont end  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="刷新" id="reload_btn" />
				</div>
					<shiro:hasPermission name="DEFAULT">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="导出" id="derive_btn" />
				</div>	
					</shiro:hasPermission>	
			</div>
		</div>
		<!-- tableble_search end  -->
	</div>


	<div class="tbdata">

		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="10" align="center">序号</th>
					<th width="30" align="center">月结客户账号</th>
					<th width="50" align="center">月结客户</th>
					<th width="30" align="center">网点编号</th>
					<th width="40" align="center">网点名称</th>
					<th width="30" align="center">快递员姓名</th>
					<th width="30" align="center">小计</th>
					<th width="25" align="center">一月</th>
					<th width="25" align="center">二月</th>
					<th width="25" align="center">三月</th>
					<th width="25" align="center">四月</th>
					<th width="25" align="center">五月</th>
					<th width="25" align="center">六月</th>
					<th width="25" align="center">七月</th>
					<th width="25" align="center">八月</th>
					<th width="25" align="center">九月</th>
					<th width="25" align="center">十月</th>
					<th width="25" align="center">十一月</th>
					<th width="25" align="center">十二月</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${countList.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.monthSettleNo	}</td>
						<td>${item.monthName}</td>
						<td>${item.substationNo}</td>
						<td>${item.substationName}</td>
						<td>${item.courierName}</td>
						<td>${item.count}</td>
						<td>${item.oneCount}</td>
						<td>${item.twoCount}</td>
						<td>${item.threeCount}</td>
						<td>${item.fourCount}</td>
						<td>${item.fiveCount}</td>
						<td>${item.sixCount}</td>
						<td>${item.sevenCount}</td>
						<td>${item.eightCount}</td>
						<td>${item.nineCount}</td>
						<td>${item.tenCount}</td>
						<td>${item.elevenCount}</td>
						<td>${item.twelveCount}</td>					
					</tr>
				</c:forEach>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>总计：${count}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>			
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${countList.pages}"
			current="${countList.pageNum}" sum="${countList.total}" />
	</div>
</body>

</html>