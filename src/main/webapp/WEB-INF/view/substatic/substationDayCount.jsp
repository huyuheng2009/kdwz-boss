<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
              $("form:first input[name=serviceName]").val('substationDayCountExport');
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
        
        function checkCNa(courierNo){
//         	$.post('/substatic/courierNoCheck',{'courierNo':courierNo},function(msg){
//            		$('#ConNa').val(msg.courierNam);        
//        		 },"JSON");
       	 $.ajax({url:'/substatic/courierNoCheck',data:{'courierNo':courierNo},success:function(msg){    		 
       		$('#ConNa').val(msg.courierNam);
   		 }});   	
        }

        function checkSbNa(substationNo){
       	 $.ajax({url:'/substatic/subNoCheck',data:{'substationNo':substationNo},success:function(msg){    		 
       		$('#subNa').val(msg.substaNam);
   		 }});   	
        }
        
        
        
        
        
    </script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/substatic/substationDayCount"
					method="get">
					<input type="hidden" name="serviceName" value="" />
					<ul>
						<li>网点编号：<input type="text" placeholder="网点编号"
							value="${params['substationNo']}" name="substationNo"
							onblur="checkSbNa($(this).val());"></input> <input
							readonly="readonly" type="text" value="${params['subNa']}"
							placeholder="网点名称" name="subNa" id="subNa"></input></li>
						<li><span>统计时间：</span> <input id="dateBegin"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"
							value="${params['beginTime']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"
							value="${params['endTime']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /></li>
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
			<div class="operator">
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
					<th width="50" align="center">网店编号</th>
					<th width="50" align="center">网点名称</th>
					<th width="30" align="center">账单日期</th>
					<th width="50" align="center">取件数</th>
					<th width="30" align="center">派件数</th>
					<th width="50" align="center">应收现金</th>
					<th width="80" align="center">应收月结</th>
					<th width="50" align="center">应收代收款</th>
					<th width="80" align="center">应收总额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${countList.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.substationNo}</td>
						<td>${item.substationName}</td>
				     	<td>${item.staticDate}</td>
						<td>${item.revCount}</td>
						<td>${item.sendCount}</td>
						<td>${item.fcount}</td>
						<td>${item.mcount }</td>
						<td>${item.ccount}</td>
						<td>${item.acount}</td>
					</tr>
				</c:forEach>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>				
			     	<td>取件总计：${sumTakeCount}</td>
					<td>派件总计：${sumSendCount}</td>
					<td>应收现金总计：${pfcount}</td>
					<td>应收月结总计：${pmcount}</td>
					<td>应收代收款总计：${pccount}</td>
					<td>应收总额总计：${pacount}</td>
				</tr>
				<tr class="${status.count % 2 == 0 ? 'a1' : ''}" align="left">
					<td></td>
					<td></td>
					<td></td>
					<td></td>				
					<td colspan="2">总计：${sumCount}</td>
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