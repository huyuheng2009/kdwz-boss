<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	//获取时间段，单位、天
	//月结用户月报
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
			$("form:first input[name=serviceName]").val('monthUserCountExport');
			$("form:first").attr("action", "${ctx}/substatic/export").submit();
			$("form:first").attr("action", action);
		});
	});


	$(function() {
		trHover('t2');
		$('#reload_btn').click(function() {
			location.reload();
		});
	});

// 	function checkCNa(courierNo) {
// 		//         	$.post('/substatic/courierNoCheck',{'courierNo':courierNo},function(msg){
// 		//            		$('#ConNa').val(msg.courierNam);        
// 		//        		 },"JSON");
// 		$.ajax({
// 			url : '/substatic/checkMonthUser',
// 			data : {
// 				'courierNo' : courierNo
// 			},
// 			success : function(msg) {
// 				$('#ConNa').val(msg.courierNam);
// 			}
// 		});
// 	}
// 	  function checkSbNa(substationNo){
// 	       	 $.ajax({url:'/substatic/subNoCheck',data:{'substationNo':substationNo},success:function(msg){    		 
// 	       		$('#subNa').val(msg.substaNam);
// 	   		 }});   	
// 	        }
	  
// 	function checkMonthSettleNo(monthSettleNo) {
// 		$.ajax({
// 			url : '/substatic/checkMonthUser',
// 			type:'get',
// 			data : {
// 				'monthSettleNo' : monthSettleNo
// 			},
// 			success : function(msg) {
// 				$('#company').val(msg.company);
// 				$('#name').val(msg.name);
// 				$('#phone').val(msg.phone);
// 				$('#address').val(msg.address);
// 			}
// 		});
// 	}


$(function () {	
	 jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {	
            var courierList =  tmjs.clist;			
            var monthList =  tmjs.mlist;			
            var sres = [];
        	var cres = [];
			var substations = [];
            $.each(monthList, function(i, item) {
            	
        		sres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_sname.replace(/\ /g,"")+')';
           });
          $.each(courierList, function(i, item) {
        	  var inner_no = "" ;
             	if(item.inner_no){
             		inner_no=item.inner_no ;
             		cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+","+item.real_name.replace(/\ /g,"")+')';
             	}else{
             		cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.real_name.replace(/\ /g,"")+')';
             	}
				substations[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
           }); 
          
          $jqq("#substationNo").autocomplete(substations, {
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
        		       } 
        	}); 
          
             $jqq("#monthSettleNo").autocomplete(sres, {
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
        			 $("#monthSettleNo").val(data[0].substring(0,data[0].indexOf('('))) ;
        		       } 
        	});	
	});
      
        });
function monthSettleNo(){
	var orderBy = $('input[name=orderBy]').val();
	if('1'==orderBy){
		$('input[name=orderBy]').val('2');
	}else{
		$('input[name=orderBy]').val('1');
	}
	$("form:first").submit();
}
function monthAcount(){
	var orderBy = $('input[name=orderBy]').val();
	if('3'==orderBy){
		$('input[name=orderBy]').val('4');
	}else{
		$('input[name=orderBy]').val('3');
	}
	$("form:first").submit();
}


function mchange(e)
{
	var mode = $(e).val();		  
	if("D" == mode ){
		$('#month').hide();		
		$('#day').show();		
		$('#isNewLi').hide();		
	}else{
		$('#month').show();		
		$('#day').hide();	
		$('#isNewLi').show();	
	}
}


$(function(){
	var s ='${params.dayOrMonth}';
	console.info(s);
	if(	s == 'M'){
// 	 document.getElementById('checkMonth').checked=true;
	 $('#checkMonth').prop('checked',true);
	 $('#month').show();		
	$('#day').hide();	
	$('#isNewLi').show();	
	}else{
// 	 document.getElementById('checkDay').checked=true;
	 $('#checkDay').prop('checked',true);
	 $('#month').hide();		
	 $('#day').show();
	 $('#isNewLi').hide();		
	}	
	if('${params.isNew}'=='1'){
		$('#isNew').attr("checked",true);	
	}else{
		$('#isNew').attr("checked",false);	
	}
	
	
});
</script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/substatic/monthUserCount"
					method="get" onsubmit="loaddata()">
					<input type="hidden" name="serviceName" value="" />
					 <input type="hidden" name="ff" value="${params['ff']}"/>
					<input type ="hidden"  name ="orderBy" value="${params['orderBy']}"/>
					<ul>			
					<li><input type="radio" name="dayOrMonth"  style="vertical-align:middle;"  id ="checkDay" value ="D"  onchange="mchange(this);"><strong>按天</strong></input></li>
					<li><input type="radio" name="dayOrMonth"   style="vertical-align:middle;" id ="checkMonth" value ="M"  checked="checked"   onchange="mchange(this);"><strong>按月</strong></input></li>
						<li><span style="float: left; ">账单日期：</span>
						 <div id="month"  style="float: left; margin:3px 0 0 0;"><input id="dateBeginM"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true})"
							type="text" style="width: 112px" name="beginTimeM"
							value="${params['beginTimeM']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM '})" />
							</div>
						<div id ="day" style="display: none;float: left; margin:2px 0 0 0;" >
						 <input id="dateBegin"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
														  type="text" style="width: 112px" name="beginTime"
														  value="${params['beginTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" /> ~ <input
														  id="dateEnd"
														  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'dateBegin\')}', maxDate: '%y-%M-%d '})"
														  type="text" style="width: 112px" name="endTime"
														  value="${params['endTime']}"
														  onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})" />
							 </li>
						<li>网点名称：<input type="text" placeholder="网店编号"value="${param['substationNo']}" name="substationNo"	id="substationNo"></input>
						<li>快递员：<input type="text" placeholder="快递员编号"value="${params['courierNo']}" name="courierNo"	id="courierNo"></input>
						<li>月结号：<input type="text" placeholder="月结客户编号"	value="${params['monthSettleNo']}" name="monthSettleNo"	id="monthSettleNo"></input> 						
						</li>	
<%--						<li>
					<span>是否有邮箱：</span> <select name ="isEmail" >
					<option  value="">-全部-</option>
					<option  value="有邮箱"  ${params.isEmail eq '有邮箱' ? 'selected':''}>有邮箱</option>
					<option  value="无邮箱"  ${params.isEmail eq '无邮箱' ? ' selected':''}>无邮箱</option>				
					</select>								
					</li>	--%>
					<li style="display:none" id="isNewLi"><input type="checkbox"  name="isNew" value="1" id="isNew" style="position:relative;top:3px;"/><label for="isNew">新增客户</label></li>
						<li><input class="button input_text  medium blue_flat"
							type="submit" value="查询"  style="margin-left: 900px"/></li>
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
<!-- 				<div class="search_new"> -->
<!-- 					<input class="button input_text  big gray_flat" type="submit" -->
<!-- 						value="刷新" id="reload_btn" /> -->
<!-- 				</div> -->
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


	<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="10" align="center">序号</th>
					<th width="30" align="center">起始日期</th>
					<th width="30" align="center">结束日期</th>
					<th width="40" align="center">所属网点</th>												
					<th width="40" align="center">所属快递员</th>												
					<th width="30" align="center"><a onclick="monthSettleNo()">月结号</a></th>	
					<th width="50" align="center">公司简称</th>		
<%--					<th width="25" align="center">寄付票数</th>
					<th width="30" align="center">寄付金额</th>		
					<th width="25" align="center">到付票数</th>
					<th width="30" align="center">到付金额</th>--%>
					<th width="20" align="center">总票数</th>
					<th width="20" align="center">月结合计</th>
					<th width="20" align="center">折扣</th>										
					<th width="50" align="center"><a onclick="monthAcount()">实收款项</a></th>		
					<th width="20" align="center">结算方式</th>
					<th width="20" align="center">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>	<c:choose>
							<c:when test="${params.dayOrMonth eq 'M'}"> ${params.beginTimeM}</c:when>
							<c:otherwise>${params.beginTime}</c:otherwise>						
							</c:choose>
						</td>
							<td>	<c:choose>
							<c:when test="${params.dayOrMonth eq 'M'}"> ${params.beginTimeM}</c:when>
							<c:otherwise>${params.endTime}</c:otherwise>						
							</c:choose>
						</td>				
							<td>${item.substationName}</td>
							<td>${item.courierName}</td>
							<td>${item.TmonthSettleNo}</td>
							<td>${item.monthSname}</td>
<%--							<td>${item.takeCount}</td>
							<td>${item.TALL}</td>
							<td>${item.sendCount}</td>
							<td>${item.SALL}</td>--%>
							<td>${item.sumCount}</td>
							<td>${item.TSALL}</td>
							<td>${item.discountText}</td>
							<td>${item.sumMoney}</td>
							<td>${item.settleType}</td>	
							<td>${item.note}</td>												
					</tr>
				</c:forEach>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
<!--					<td></td>
					<td></td>
					<td></td>
					<td>${count}</td>-->
					<td></td>
					<td></td>					
					<td>实收总额:${AllsumMoney}</td>		
					<td></td>		
					<td></td>
				</tr>			
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>