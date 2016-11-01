<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
 <script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
<script>
var $jqq = jQuery.noConflict(true);
</script> 

<style>
  table tr.hiding td{border:0px; background-color:none;}
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
    	  	$('#check').click(function(){
    	  		var lDiv = document.getElementById("loading");
    	  		if(lDiv.style.display=='none'){
    	  		    lDiv.style.display='block';
    	  		} 	
    	  		document.getElementById("loading1").style.display='block';
    	  		});
  
    	  
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
        	
        	
        	jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
  			
  			 var slist =  tmjs.slist ;
              	var slists = [];
                  $.each(slist, function(i, item) {
                  	var inner_no = "" ;
                  	if(item.inner_no){inner_no=item.inner_no+','}
                  	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                  });
  		     $jqq("#substationNo").autocomplete(slists, {
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
  			
     
});

		function address(date) {
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '地址蒲',
					content: "url:<%=request.getContextPath()%>/batch/queryRadiaoAddr?layout=no",
					lock: true
				}); 
			}    
		function takeDetail(time,sub){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '收件明细',
					content: "url:<%=request.getContextPath()%>/scanEx/takeDetail?layout=no&scanTime="+time+"&subNo="+sub,
					lock: true
				}); 
			} 
        
		function sendDetail(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '派件明细',
					content: "url:<%=request.getContextPath()%>/scanEx/sendDetail?layout=no&scanTime="+time+"&subNo="+sub,                                        
					lock: true
				}); 
			} 
		function inCount(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '到件明细',
					content: "url:<%=request.getContextPath()%>/scanEx/inCount?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		
		function outCount(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '出站明细',
					content: "url:<%=request.getContextPath()%>/scanEx/outCount?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		function errorCount(time,sub,subNam){	
			$.dialog({
					width: '1200px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '问题件明细',
					content: "url:<%=request.getContextPath()%>/scanEx/errorCount?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: false
				}); 
			} 
        
		function complateOrder(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '签收票明细',
					content: "url:<%=request.getContextPath()%>/scanEx/complateOrder?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		
		function inAndOut(time,sub,subNam){	
			$.dialog({
					width: '1200px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '有到站有出站',
					content: "url:<%=request.getContextPath()%>/scanEx/inAndOut?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		function inNotOut(time,sub,subNam){	
			$.dialog({
					width: '1200px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '有到站无出站',
					content: "url:<%=request.getContextPath()%>/scanEx/inNotOut?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		function noInhaveOut(time,sub,subNam){	
			$.dialog({
					width: '1200px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '地址蒲',
					content: "url:<%=request.getContextPath()%>/scanEx/noInhaveOut?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		function noInhaveOutSend(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '无到站有派件',
					content: "url:<%=request.getContextPath()%>/scanEx/noInhaveOutSend?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		
		function inNotSend(time,sub,subNam){	
			$.dialog({
					width: '920px',
					height: '540px',
					max: false,
					min: false,
					drag: false,
					resize: false,
					title: '地址蒲',
					content: "url:<%=request.getContextPath()%>/scanEx/inNotSend?layout=no&scanTime="+time+"&subNo="+sub+"&subName="+subNam,
					lock: true
				}); 
			} 
		
		
		
 
    </script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/substatic/subScan"
					method="get"  onsubmit="loaddata()">
					<input type="hidden" name="serviceName" value="" />
					<input type="hidden" name="ff" value="${params['ff']}"/>
					<ul>
						<li>网点编号：<input type="text" placeholder="网点编号"		value="${params['substationNo']}" name="substationNo" id="substationNo"></input> 			
							</li>					
						<li><span>统计时间：</span> 
						<input id="dateBegin"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"	value="${params['beginTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"	value="${params['endTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" />
							</li>
<!-- 						<div class="operator" style="float:left; overflow:auto; zoom:1;">					 -->
<!-- 							<div class="search_new"> -->
<!-- 								<input class="button input_text  big gray_flat" type=button -->
<!-- 									value="30天" id="month" /> -->
<!-- 							</div> -->
<!-- 							<div class="search_new"> -->
<!-- 								<input class="button input_text  big gray_flat" type=button -->
<!-- 									value="90天" id="threeMonth" /> -->
<!-- 							</div> -->
<!-- 								<div class="search_new"> -->
<!-- 								<input class="button input_text  big gray_flat" type=button -->
<!-- 									value="180天" id="sixMonth" /> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<li ><input class="button input_text  medium blue_flat"
							type="submit" value="查询"  id ="check"/></li>
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
			<div id="loading" style="display:none">
    		<div class="loading-indicator" style="color:red;font-size:20px;">正在查询，请稍候……</div>
    		</div>
<%-- 				<shiro:hasPermission name="DEFAULT"> --%>
<!-- 				<div class="search_new"> -->
<!-- 					<input class="button input_text  big gray_flat" type="submit"		value="导出" id="derive_btn" /> -->
<!-- 				</div>	 -->
<%-- 					</shiro:hasPermission>	 --%>
			</div>
		</div>
		<!-- tableble_search end  -->
	</div>


<div class="tbdata" style="position: relative;" >
<div class="loaddata"  style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="10" align="center">序号</th>
					<th width="50" align="center">扫描日期</th>
					<th width="50" align="center">网点编号</th>
					<th width="50" align="center">网点名称</th>
					<th width="50" align="center">收件票数</th>
					<th width="30" align="center">派件票数</th>
					<th width="50" align="center">到站票数</th>
					<th width="50" align="center">出站票数</th>
					<th width="30" align="center">问题件</th>
					<th width="50" align="center">签收票数</th>
					<th width="50" align="center">有到站有出站</th>
					<th width="50" align="center">有到站无出站</th>
<!-- 					<th width="50" align="center">无到站有出站</th> -->
					<th width="50" align="center">无到站有派件</th>
<!-- 					<th width="50" align="center">有到站无派件</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${packageList}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.scanTime}</td>
						<td>${item.sinnerNo}</td>
						<td>${item.subNam}</td>
						<td><a href="javascript:void(0);" onclick="takeDetail(' ${item.scanTime}','${item.subNo}')" >${item.takeCount}</a></td>
						<td><a href="javascript:void(0);" onclick="sendDetail(' ${item.scanTime}','${item.subNo}')" >${item.sendCount}</a></td>
						<td><a href="javascript:void(0);" onclick="inCount(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.daozhanpiaoshu}</a></td>
						<td><a href="javascript:void(0);" onclick="outCount(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.chuzhanpiaoshu}</a></td>
						<td><a href="javascript:void(0);" onclick="errorCount(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.wentijian}</a></td>
						<td><a href="javascript:void(0);" onclick="complateOrder('${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.qianshoupiaoshu}</a></td>
						<td><a href="javascript:void(0);" onclick="inAndOut(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.inAndOut}</a></td>
						<td><a href="javascript:void(0);" onclick="inNotOut(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.inNotOut}</a></td>
<%-- 						<td><a href="javascript:void(0);" onclick="noInhaveOut(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.noInhaveOut}</a></td> --%>
						<td><a href="javascript:void(0);" onclick="noInhaveOutSend(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.noInhaveOutSend}</a></td>
<%-- 						<td><a href="javascript:void(0);" onclick="inNotSend(' ${item.scanTime}','${item.subNo}','${item.subNam}' )" >${item.inNotSend}</a></td>			 --%>
					</tr>
				</c:forEach>
				<tr class="hiding" >

				</tr>		
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${current.pages}"
			current="${current.pageNum}" sum="${current.total}" />
	</div>
</body>

</html>
