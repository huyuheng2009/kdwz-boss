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
    function getToday(date,less){      
        var yesterday_milliseconds=date.getTime()-1000*60*60*24*less;    
        var yesterday = new Date();       
            yesterday.setTime(yesterday_milliseconds);       
            
        var strYear = yesterday.getFullYear();    
        var strDay = yesterday.getDate();    
        var strMonth = yesterday.getMonth()+1;  
        if(strMonth<10)    
        {    
            strMonth="0"+strMonth;    
        }    
        datastr = strYear+"-"+strMonth+"-"+strDay;  
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
    
      $(function () {
          $('#month').click(function(){
       	   var now= new Date();
       	   var begin  = getToday(now,30);
        	 var end  = getToday(now,0);
       	   $('#dateBegin').val(begin);
       	   $('#dateEnd').val(end);	   
        	});     
         });
      $(function () {
          $('#threeMonth').click(function(){
       	   var now= new Date();
       	   var begin  = getToday(now,90);
      	 var end  = getToday(now,0);
       	   $('#dateBegin').val(begin);
       	   $('#dateEnd').val(end);	   
        	});     
         });  
      //导出
      $(function () {      
      	$('#derive_btn').click(function(){
      		var action= $("form:first").attr("action");
              $("form:first input[name=serviceName]").val('dayCountExport');
      	   $("form:first").attr("action","${ctx}/substatic/export").submit();
      	   $("form:first").attr("action",action);
      	 loaddata_end() ;
      	});        
      });

        $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
       		 location.reload();
       	});     
        });
        
        
        
        
        $(function () {
    var courierList = ${courierList};
    var substationList = ${substationList};
    var sres = [];
	var cres = [];
    $.each(substationList, function(i, item) {
		 var inner_no = "" ;
		sres[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
   });
  $.each(courierList, function(i, item) {
	   var inner_no = "" ;
 
   	cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.real_name.replace(/\ /g,"")+')';
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
			 $("#ctips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
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



   
});

function substationName(){
	var orderBy = $('input[name=orderBy]').val();
	if('1'==orderBy){
		$('input[name=orderBy]').val('2');
	}else{
		$('input[name=orderBy]').val('1');
	}
	 $("form:first").submit();	
}
   

function courierName(){
	var orderBy = $('input[name=orderBy]').val();
	if('3'==orderBy){
		$('input[name=orderBy]').val('4');
	}else{
		$('input[name=orderBy]').val('3');
	}
	 $("form:first").submit();	
}
   

function staticDate(){
	var orderBy = $('input[name=orderBy]').val();
	if('5'==orderBy){
		$('input[name=orderBy]').val('6');
	}else{
		$('input[name=orderBy]').val('5');
	}
	 $("form:first").submit();	
}
   
        
      
        
 
    </script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/substatic/dayCount"
					method="get">
					<input type="hidden" name="serviceName" value="" />
					 <input type="hidden" name="orderBy" value="${params['orderBy']}"/>
					<ul>
						<li>网点编号：<input type="text" placeholder="网点编号"		value="${params['substationNo']}" name="substationNo" id="substationNo"></input> 			
							</li>
						<li>快递员编号：<input type="text" placeholder="快递员编号"	value="${params['courierNo']}" name="courierNo" id = "courierNo"></input> 
				
							</li>
						<li><span>统计时间：</span> 
						<input id="dateBegin"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"	value="${params['beginTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"	value="${params['endTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" />
							</li>
						<div class="operator" style="float:left; overflow:auto; zoom:1;">					
							<div class="search_new">
								<input class="button input_text  big gray_flat" type=button
									value="30天" id="month" />
							</div>
							<div class="search_new">
								<input class="button input_text  big gray_flat" type=button
									value="90天" id="threeMonth" />
							</div>
								<div class="search_new">
								<input class="button input_text  big gray_flat" type=button
									value="180天" id="sixMonth" />
							</div>
						</div>
						<li ><input class="button input_text  medium blue_flat"
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
			<div class="operator"  >
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"		value="刷新" id="reload_btn" />
				</div>
			
				<shiro:hasPermission name="DEFAULT">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"		value="导出" id="derive_btn" />
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
					<th width="50" align="center">网点编号</th>
					<th width="50" align="center"><a onclick="substationName()">网点名称</a></th>
					<th width="30" align="center">快递员编号</th>
					<th width="50" align="center"><a onclick="courierName()">快递员姓名</a></th>
					<th width="30" align="center"><a onclick="staticDate()">日期</a></th>
					<th width="50" align="center">收件数</th>
					<th width="80" align="center">派件数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${countList.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.substationNo}</td>
						<td>${item.substationName}</td>
						<td>${item.courierNo}</td>
						<td>${item.courierName}</td>					
						<td>	<fmt:formatDate value="${item.staticDate}" type="both"  pattern="yyyy-MM-dd"/></td>
						<td>${item.revCount}</td>
						<td>${item.sendCount}</td>
					</tr>
				</c:forEach>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>收件总计：${sumTakeCount}</td>
					<td>派件总计：${sumSendCount}</td>
				</tr>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}" align="left">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td colspan="2">总计：${sumCount}</td>
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