<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/*********地址展开样式**********/
.out_address {
	position: absolute;
	width: 200px;
	padding: 6px;
	background: #fff;
	border: 1px solid #cdcdcd;
	margin: -28px 0 0 -4px;
	display: none;
}
</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>

<script>
var $jqq = jQuery.noConflict(true);
</script>

<style>
table tr.hiding td {
	border: 0px;
	background-color: none;
}
</style>
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
              $("form:first input[name=serviceName]").val('allotExport');
      	   $("form:first").attr("action","${ctx}/orderAllot/export").submit();
      	   $("form:first").attr("action",action);
      	});        
      });

        $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
       		 location.reload();
       	});     
        });
        
        
        
        
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
		  cres[i]=item.user_name.replace(/\ /g,"")+'('+item.real_name.replace(/\ /g,"")+')';
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

        function showAddr(e){
        	$(".out_address").css("display","none");
        	$(e).siblings(".out_address").css("display","block");
        }
    	function fenpei() {
			$.dialog({
					width: '680px',
					height: '600px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '订单分配',
					content: "url:<%=request.getContextPath()%>/orderAllot/allotPage?layout=no",
					lock: true
				}); 
			}    
    	function detail(id,subNo) {
			$.dialog({
					width: '1200px',
					height: '560px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '使用明细',
					content: "url:<%=request.getContextPath()%>/orderAllot/allotDetail?id="+id+"&subNo="+subNo+"&layout=no",
					lock: true
				}); 
			}   
 	function stopUser(id){
 		 event.returnValue = confirm("你是否确定停用该票段？");
		 if(event.returnValue){
		 $.ajax({
             type: "GET",
             url: "/orderAllot/stopUser",
             data: {'id':id },
             dataType: "text",
             success: function(data){
            		location.reload(); 
               }
         });	
		 }else{
        	 return;
         }	
 		
 		
 	}   	
    	
 	function startUser(id){
 		
		 event.returnValue = confirm("你是否确定启用该票段？");
		 if(event.returnValue){
		 $.ajax({
             type: "GET",
             url: "/orderAllot/startUser",
             data: {'id':id },
             dataType: "text",
             success: function(data){
            		location.reload(); 
               }
         });	
		 }else{
        	 return;
         }	
 		
 	}   	
    	
   	
 	
   	
 	function deleteOrder(id){	
		 event.returnValue = confirm("你是否确定启用该票段？");
		 if(event.returnValue){
			 event.returnValue = confirm("你真的确定启用该票段？");
			 if(event.returnValue){	 
		 $.ajax({
             type: "GET",
             url: "/orderAllot/deleteOrder",
             data: {'id':id },
             dataType: "text",
             success: function(data){
            		location.reload(); 
               }
         });	
		 }else{
        	 return;
         }	
 		
 	}else{
 		}
		 return;
 	}
    	
    	
    </script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/orderAllot/list" method="get">
					<input type="hidden" name="serviceName" value="" />
					<ul>
						<li><span>登记时间：</span> <input id="dateBegin"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"
							value="${params['beginTime']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"
							onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"
							value="${params['endTime']}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /></li>
						<li>运单号：<input type="text" placeholder=""
							value="${params['orderNo']}" name="orderNo" id="orderNo">
						</input>
						</li>
						<li>网点编号：<input type="text" placeholder=""
							value="${params['substationNo']}" name="substationNo"
							id="substationNo"> </input>
						</li>
						<li>登记人：<input type="text" placeholder=""
							value="${params['courierNo']}" name="courierNo" id="courierNo">
						</input>
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
					<input class="button input_text  big gray_flat"
						style="width: 200px" onclick="fenpei();" type="submit" value="分配"
						id="fenpei" />
				</div>
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
					<th width="15" align="center">序号</th>
					<th width="50" align="center">名称</th>
					<th width="50" align="center">分配时间</th>
					<th width="50" align="center">网点</th>
					<th width="50" align="center">开始单号</th>
					<th width="30" align="center">结束单号</th>
					<th width="50" align="center">分配数量</th>
					<th width="50" align="center">已用数量</th>
					<th width="30" align="center">剩余数量</th>
					<th width="50" align="center">单价</th>
					<th width="50" align="center">总金额</th>
					<th width="50" align="center">登记人</th>
					<th width="50" align="center">备注</th>
					<th width="80" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.managerInfo}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${item.registerTime}" /></td>
						<td>${item.substationName}</td>
						<td>${item.beginOrder}</td>
						<td>${item.endOrder}</td>
						<td>${item.number}</td>
						<td>${item.userdCount}</td>
						<td>${item.residueCount}</td>
						<td>${item.unitCost}</td>
						<td>${item.cost}</td>
						<td>${item.registerName}</td>
						<c:set var="context" value="${item.note}" />
						<td><a onclick="showAddr(this)">${fn:substring(context,fn:length(context)-8,fn:length(context))}</a>
						<div class="out_address">${item.note}</div></td>
						<td><a href="javascript:void(0);"
							onclick="detail('${item.id}','${item.substationNo}');">明细 </a>&nbsp;&nbsp;
							<c:if test="${item.status eq 'Y'}">
								<a href="javascript:void(0);" onclick="stopUser(${item.id});">停用</a>
							</c:if> <c:if test="${item.status eq 'N'}">
								<a href="javascript:void(0);" onclick="startUser(${item.id});">启用</a>
							</c:if> <c:if test="${item.ISDelete eq 'Y'}">&nbsp;&nbsp;<a
									href="javascript:void(0);" onclick="deleteOrder(${item.id});">
									删除</a>
							</c:if> <c:if test="${item.ISDelete eq 'N'}">&nbsp;&nbsp; 删除</c:if></td>
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
