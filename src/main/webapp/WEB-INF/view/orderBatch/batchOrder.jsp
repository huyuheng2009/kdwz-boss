<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<style>
			#uploadXLS {display: none;z-index: 100;background: white;border-bottom: 1px solid #DCDCDC;}
			#uploadXLS input:first-child {display: none;}
			#uploadXLS .a {height: 2em;line-height: 2em;text-indent: 20px;}
			#uploadXLS .a span.a1{font-weight: bolder;}
			#uploadXLS .p{height: 20px;width: calc(100% - 40px);margin: 10px auto;
						  box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -webkit-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -ms-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -o-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -moz-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);}
			#uploadXLS .p2 {height: 100%;width: 0;background: #E25A48;}
		</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/ImportExcel.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine.js"></script>
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

	$(function() {
	
 	jQuery("#myform").validationEngine();
	 var u = new ImportExcel($("#uploadXLS"), [".xls"], {params: {}});
		$("#importExcel").click(function(evt) {
		
			if (!u.lock) {			
				u.$file.click();
			}	
			evt.preventDefault();
			
		}); 

	});

	$(function() {
		trHover('t2');
		$('#reload_btn').click(function() {
			location.reload();
		});
	});

	  
	  $(function(){
		  $('#import').click(function(){		
			  location.href = "<%=request.getContextPath()%>/template/takeBatch.xls";		  		  
		  });	  
	  });

	function checkMonthSettleNo(e) {
		var monthSettleNo = $(e).val();
		$.ajax({
			url : '/orderBatch/checkMonthUser',
			type:'get',
			data : {
				'monthSettleNo' : monthSettleNo
			},
			success : function(msg) {
				$('#company').val(msg.company);
				$('#monthUser').val(msg.name);
				$('#monthPhone').val(msg.phone);
				$('#monthAddr').val(msg.address);
			}
		});
	}
$(function(){
	$('#deleteAll').click(function(){
		 event.returnValue = confirm("删除是不可恢复的，你确认要删除吗？");
		if(event.returnValue){
		 $.ajax({
             type: "GET",
             url: "/orderBatch/deleteAll",
             data: { },
             dataType: "json",
             success: function(data){
            		location.reload(); 
                }
         });		
	}else{
		return;
	}
	});	
});


$(function () {
            var courierList = ${courierList};
            var monthList = ${monthList};
            var subNo = ${substationList};
            var sres = [];
        	var cres = [];
        	var sber =[];
        	
        	  $.each(subNo, function(i, item) {

        		  sber[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
        	   });
        	
            $.each(monthList, function(i, item) {
            	
        		sres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_sname.replace(/\ /g,"")+')';
           });
          $.each(courierList, function(i, item) {
      
           	cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.inner_no+')'+'('+item.real_name.replace(/\ /g,"")+')';
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
             $jqq("#sendSubNo").autocomplete(sber, {
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
         			 $("#sendSubNo").val(data[0].substring(0,data[0].indexOf('('))) ;
       	
         		       } 
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


</script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont" style="overflow:auto; zoom:1;">
	<form:form id="trans" action="/orderBatch/addOrderMuch"	method="get">
					<input type="hidden" name="serviceName" value="" />
					<input type ="hidden"  name ="orderBy" value="${params['orderBy']}"/>
					<ul style="float:left;">
						<li><span>寄件时间：</span> <input id="dateBegin"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"
							value="${params['beginTime']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> </li>
						<li>取件快递员：<input  style="width:200px;" type="text" placeholder="取件快递员"value="${params['courierNo']}" name="courierNo"	id="courierNo"></input></li>
						<li>月结客户编号：<input type="text" placeholder="月结客户编号"	value="${params['monthSettleNo']}" name="monthSettleNo"	id="monthSettleNo" onblur="checkMonthSettleNo(this)"></input> </li>		
						<li>收件分站：<input type="text" placeholder="收件分站"	value="${params['sendSubNo']}" name="sendSubNo"	id="sendSubNo"></input>				
						</li>	
						
						<li><input class="button input_text  medium blue_flat" 	
						type="submit" value="提交" /></li>
						<li><input class="button input_text  medium blue_flat"		type="reset" value="重置" /></li>		
						</ul>
						<div style="display:block;">
						<ul style="float:left;">
					
		                <li>月结公司：<input style="background:#ccc;"type="text" st	value="${params['company']}" name="company"	id="company" readonly="true "></input>				
						</li>
						<li>月结联系人：<input style=" background:#ccc;"type="text" 	value="${params['monthUser']}" name="monthUser"	id="monthUser" readonly="true "></input>				
						</li>
						<li>月结联系电话：<input style="background:#ccc;"type="text" 	value="${params['monthPhone']}" name="monthPhone"	id="monthPhone" readonly="true "></input>				
						</li>
						<li>月结用户地址：<input style="width:400px; background:#ccc;"  type="text" value="${params['monthAddr']}" name="monthAddr"	id="monthAddr" readonly="true "></input>				
						</li>		
						<li><span style="color: red;font-size: 18px">请先导入订单信息！</span></li>							
					</ul>
					</div>
					</form:form>
			</div>
			<!-- search_cont e
			nd  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="刷新" id="reload_btn" />
				</div>
		
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="导入" id="importExcel" />
				</div>		
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="清空" id="deleteAll" />
				</div>		
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="模版"  id="import"/>
				</div>		
				
			</div>		
				<strong style=" line-height:50px; font-weight:normal;color: red; font-size:16px">&nbsp;&nbsp;&nbsp;&nbsp;${msg}</strong>
		</div>
		<div id="uploadXLS">
						<input type="file" />
						<div class="a">
							上传　<span class="a1">标准表格.xls</span>　<span class="a2"></span>
						</div>
						<div class="p"><div class="p2"></div></div>
					</div>
		<!-- tableble_search end  -->
	</div>


	<div class="tbdata">

		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="10" align="center">序号</th>
					<th width="30" align="center">运单号</th>
					<th width="50" align="center">收件公司</th>
					<th width="20" align="center">地址</th>
					<th width="40" align="center">姓名</th>
					<th width="30" align="center">联系手机</th>
					<th width="30" align="center">件数</th>
					<th width="25" align="center">重量</th>
					<th width="35" align="center">费用总计</th>
					<th width="10" align="center">货款</th>
					<th width="50" align="center">回单号码</th>
					<th width="50" align="center">综合备注</th>
					<th width="50" align="center">派件网点</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.orderNo	}</td>
						<td>${item.takeCompany}</td>
						<td>${item.takeAddr}</td>
						<td>${item.takeName}</td>
						<td>${item.takePhone}</td><!-- 签约快递员 编号-->
						<td>${item.takeCount}</td><!-- 签约快递员 -->
						<td>${item.weight}</td>
						<td>${item.payAcount}</td>
						<td>${item.goodPrice}</td>
						<td>${item.backNumber}</td>
						<td>${item.orderNote}</td>
						<td>${item.sendSubtation}</td>
					</tr>
				</c:forEach>	
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>