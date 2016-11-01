<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:200px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin:-28px 0 0 -4px;display: none;}
</style>
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
      	$('#derive_btn').click(function(){
      		var action= $("form:first").attr("action");
              $("form:first input[name=serviceName]").val('scanOrder');
      	   $("form:first").attr("action","${ctx}/scanEx/export").submit();
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
    var sres = [];
    $.each(slist, function(i, item) {
      	var inner_no = "" ;
      	if(item.inner_no){inner_no=item.inner_no+','}
      	sres[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
      });

     $jqq("#curSubstationNo").autocomplete(sres, {
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
			 $("#curSubstationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
			 $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	});	   
			 
     $jqq("#preSubstationNo").autocomplete(sres, {
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
			 $("#preSubstationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
			 $("#stips1").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	});	  
			 
     $jqq("#nextSubstationNo").autocomplete(sres, {
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
			 $("#nextSubstationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
			 $("#stips2").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
		       } 
	 });	 
    });
});

        function showAddr(e){
        	$(".out_address").css("display","none");
        	$(e).siblings(".out_address").css("display","block");
        }
        
    </script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">
			<div class=" search_cont">
				<form:form id="trans" action="${ctx}/scanEx/orderScan"
					method="get">			
					<input type="hidden" name = "serviceName" value=""/>
					<input type="hidden" name="ff" value="${params['ff']}"/>
					<ul>
					 <textarea  cols="18" rows="4"   maxlength="1400" value="${orderNo}" placeholder="请输入运单号" name="orderNo"  class="order_area">${orderNo}</textarea> 							
							<li>
								扫描类型：<select name="statusNo" style="width: 139px;">
								 <option value="全部" >全部</option>     					    									
								 <option value="收件"  ${param.statusNo eq '收件' ? 'selected':''} >收件</option>     					    									
								 <option value="派件"  ${param.statusNo eq '派件' ? 'selected':''} >派件</option>     					    									
								 <option value="出站"  ${param.statusNo eq '出站' ? 'selected':''} >出站</option>     					    									
								 <option value="到站"  ${param.statusNo eq '到站' ?'selected':''} >到站</option>     					    															     					    																
								 <option value="签收"  ${param.statusNo eq '签收' ?' selected':''} >签收</option>     						    															     					    																										
								</select>											
							</li>
					<li style="margin-right: 380px;"><span>&#12288;&#12288;操作时间：</span> 
						<input id="dateBegin"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d \'}'})"
							type="text" style="width: 112px" name="beginTime"	value="${params['beginTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" /> ~ <input
							id="dateEnd"	onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d '})"
							type="text" style="width: 112px" name="endTime"	value="${params['endTime']}"	onClick="WdatePicker({dateFmt:'yyyy-MM-dd '})" />
							</li>
						<li>&#12288;操作人：<input type="text" placeholder=""		value="${params['opname']}" name="opname" id="opname"> </input> 			
						</li>
						<li>上一站/下一站：<input style="width: 250px;" type="text" placeholder=""		value="${params['nextSubstationNo']}" name="nextSubstationNo" id="nextSubstationNo"> </input> 			
						</li>						
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
					<th width="50" align="center">操作时间</th>
					<th width="50" align="center">运单号</th>
					<th width="50" align="center">扫描类型</th>
					<th width="50" align="center">操作人</th>
					<th width="50" align="center">上一站</th>
					<th width="50" align="center">下一站</th>
					<th width="50" align="center">流转信息</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${newList}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.orderTime}" /></td>
						<td>${item.lgcOrderNo}</td>
						<td>${item.orderStatus}</td>
						<td>${item.opname}</td>
						<td>${item.preName}</td>
						<td>${item.nextName}</td>
						  <c:set var="context" value="${item.context}" />
						 <td><a onclick="showAddr(this)">${fn:substring(context,fn:length(context)-8,fn:length(context))}</a><div class="out_address">${item.context}</div></td>
					</tr>
				</c:forEach>
				<tr></tr>
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>
