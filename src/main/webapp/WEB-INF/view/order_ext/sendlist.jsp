<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:200px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin:-28px 0 0 -4px;display: none;}
</style>



    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function bangding(orderNo,lgcNo,lgcOrderNo){
        	$.dialog({lock: true,title:'修改运单号',drag: true,width:400,height:120,resize: false,max: false,content: 'url:${ctx}/order/bing?lgcNo='+lgcNo+'&orderNo='+orderNo+'&lgcOrderNo='+lgcOrderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function note(id,lgcOrderNo){
        	$.dialog({lock: true,title:'备注',drag: true,width:600,height:700,resize: false,max: false,content: 'url:${ctx}/order_ext/note?id='+id+'&lgcOrderNo='+lgcOrderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function images(orderNo){
        	$.dialog({lock: true,title:'订单图片',drag: true,width:760,height:470,resize: false,max: false,content: 'url:${ctx}/order/imagelist?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function track(orderNo){
        	$.dialog({lock: true,title:'物流动态',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/order/track?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }

        
        
        function deled(id){
        	$.dialog({lock: true,title:'无效单',drag: true,width:500,height:160,resize: false,max: false,content: 'url:${ctx}/order/deled_page?id='+id+'&layout=no',close: function(){
        	 }});
        }
        function showAddr(e){
        	$(".out_address").css("display","none");
        	$(e).siblings(".out_address").css("display","block");
        }
       
        function mchange(e){
        	var mode = $(e).val();
        	 $('input[name=ids]').each(function(){
         		$(this).prop('disabled',false); 
         		$(this).prop('checked',false); 
         	});
        	 
        	if("A"==mode){  
        		$('.a_dis').each(function(){
      					$(this).prop('disabled',true); 
              	}); 
        	}else{
        		$('.p_dis').each(function(){
  					$(this).prop('disabled',true); 
          	});
        	}
        } 
        
        $(function () {
            trHover('t2');
            new TableSorter("t2");
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
            
            $('#print').click(function(){
            	if(navigator.userAgent.indexOf("MSIE")!=-1){
            		alert("不支持IE内核浏览器打印！建议使用360浏览器极速模式、谷歌或火狐");
            		return ;
            	}
            	var ids = '';
            	$('.ids:checked').each(function(){
            		ids += $(this).val()+','; 
            	});
            	if(ids.length>0){
            		ids = ids.substring(0, ids.length-1) ;
            		$.dialog({lock: true,title:'订单打印',drag: true,width:580,height:750,resize: false,max: false,content: 'url:${ctx}/order/print?ids='+ids+'&layout=no',close: function(){
                  	 }});
            	}else{
            		alert("请选择一项或多项！");
            		return ;
            	}
          	});   
            
            
         	jQuery.ajax({
  		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
  		      dataType: "script",
  		      cache: true
  		}).done(function() {
  			var data1 = tmjs.clist ;
             // $.getJSON("${ctx}/lgc/calllist", function(data1) {
              	var availablesrcKey1 = [];
                   $.each(data1, function(i, item) {
                  	var inner_no = "" ;
                  	if(item.inner_no){
                  		inner_no=item.inner_no
                  		availablesrcKey1[i]=inner_no+'('+item.real_name.replace(/\ /g,"")+')';
                  	}else{
                  		availablesrcKey1[i]=item.real_name.replace(/\ /g,"");
                  	}
                  }); 
                  val1 = '';
                    $jqq("#courier").autocomplete(availablesrcKey1, {
                 		minChars: 0,
                 		max: 12,
                 		autoFill: true,
                 		mustMatch: false,
                 		matchContains: true,
                 		scrollHeight: 220,
                 		formatItem: function(data11, i, total) {
                 			return data11[0];
                 		}
                 	}).result(function(event, data, formatted) {
                 		if(data[0].indexOf(')')>-1){
               			 $("#courier").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
         			       } 
               	});	
             
             
            
          	var mlist =tmjs.mlist; 	 
         	var mres = [];
         	$.each(mlist, function(i, item) {
         		var name = item.month_sname ;
         		if(name.length<1){name = item.month_name ;}
              	mres[i]= item.month_settle_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
              }); 
         	  $jqq("#monthSettleNo").autocomplete(mres, {
           		minChars: 0,
           		max: 12,
           		autoFill: true,
           		mustMatch: false,
           		matchContains: true,
           		scrollHeight: 220,
           		formatItem: function(data, i, total) {
           			return data[0].substring(0,data[0].indexOf(')')+1);
           		}
           	}).result(function(event, data, formatted) {
           		if(data[0].indexOf(')')>-1){
           			 $("#monthSettleNo").val(data[0]) ;
     			  } 
           		//_submit() ;
           	}); 
  	    });         
            
            $('#add').click(function(){
          		 location.href="/order/add";
          	});
            
            $('.a_dis').each(function(){
					$(this).prop('disabled',true); 
        	}); 
            
            $('.select_all').click(function() {
           	 if($(this).prop("checked"))	{
           			$('input[name=ids]').each(function(){
           				if(!$(this).prop('disabled')){
           					$(this).prop('checked',true); 
           				}
                   		
                   	}); 
           	 }else{
           		 $('input[name=ids]').each(function(){
                		$(this).prop('checked',false); 
                	}); 
           	 }
           
           });
            
        	$('#exportData').click(function(){
        		var action = $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('takeExport');
                $("form:first").attr("action", "${ctx}/export/service").submit();
                $("form:first").attr("action", action);
        	});
            
        	
        	$("#config").click(function(){
        		$.dialog({lock: true,title:'设置',drag: true,width:600,height:600,resize: false,max: false,content: 'url:${ctx}/tabelFiled/tableFiledSelect?tab=order_sendlist&layout=no',close: function(){
             	 }});
        	});
        	
        	
        });
        
        

    </script>
</head>
<body>
<div class="search">

   <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/order_ext/sendlist" method="post">
     <input type="hidden" name="serviceName" value=""/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
         <textarea  cols="18" rows="4"  maxlength="1400" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" style="max-height: 125px;min-height: 125px;">${params['orderNo']}</textarea> 
          <%--   <li class="soso_li">运单号： <input type="text" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo"></input> 
              </li> --%>
                         <input type="hidden" name="serviceName" value="" />
			      <li><span>寄件时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:102px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span style="margin:0 30px;">~</span>
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:102px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			 
			 	 <li><span style="margin-left:27px;">网点：</span><select id="substationNo" name="substationNo" style="width: 110px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
			</select></li> 
			 
			  <li class="soso_li">月结客户：<input type="text" value="${params['monthSettleNo']}" id="monthSettleNo" name="monthSettleNo" style="width:102px" ></input></li>
			    <li class="search_cont_input">快递员：<input type="text" value="${params['courier']}" name="courier" id="courier" style="width:102px"></input></li>
         
          <li>付款方式：<select name="payType" style="width: 110px">
                <option value="">--全部--</option>
                <option value="CASH" ${params['payType'] eq 'CASH'?'selected':''}>现金</option>
                <option value="MONTH" ${params['payType'] eq 'MONTH'?'selected':''}>月结</option>
             </select></li>
             
             <li>审核状态：<select name="examineStatus" style="width: 110px">
                <option value="">--全部--</option>
                <option value="NONE" ${params['examineStatus'] eq 'NONE'?'selected':''}>未审核</option>
                <option value="PASS" ${params['examineStatus'] eq 'PASS'?'selected':''}>已审核</option>
             </select></li>
         
            <li class="soso_li">&nbsp;运单状态：<select name="status" style="width: 110px">
                <option value="">--全部--</option>
                <option value="1" ${params['status'] eq '1'?'selected':''}>未取件</option>
                <option value="2" ${params['status'] eq '2'?'selected':''}>已收件</option>
                <option value="7" ${params['status'] eq '7'?'selected':''}>待派件</option>
                <option value="8" ${params['status'] eq '8'?'selected':''}>派件中</option>
                <option value="3" ${params['status'] eq '3'?'selected':''}>已完成</option>
                <option value="4" ${params['status'] eq '4'?'selected':''}>已取消</option>
                <option value="5" ${params['status'] eq '5'?'selected':''}>拒收</option>
                <option value="6" ${params['status'] eq '6'?'selected':''}>拒签</option>
               <%--  <option value="9" ${params['status'] eq '9'?'selected':''}>无效单</option> --%>
             </select></li>
             
             
                <li><span style="margin-left:12px;">付款人：</span><select name="freightType" style="width: 110px">
                <option value="">--全部--</option>
                <option value="1" ${params['freightType'] eq '1'?'selected':''}>寄方付</option>
                <option value="2" ${params['freightType'] eq '2'?'selected':''}>收方付</option>
             </select></li>
                   <li style="width:150px;"></li>
              <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="reqRece" value="Y" <c:out value="${params.reqRece eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">有回单</span>
          </label>
           </li> 
           
             <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="proOrder" value="Y" <c:out value="${params.proOrder eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">问题件</span>
          </label>
           </li> 
           
             <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="goodPrice" value="Y" <c:out value="${params.goodPrice eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">有代收款</span>
          </label>
           </li> 
           
             <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="vpay" value="Y" <c:out value="${params.vpay eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">有附加费</span>
          </label>
           </li> 
           
               <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="forOrder" value="Y" <c:out value="${params.forOrder eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">有转件</span>
          </label>
           </li> 
           
            <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="zidan" value="Y" <c:out value="${params.zidan eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">有子单</span>
          </label>
           </li>
           
           <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; " type="checkbox" name="untake" value="Y" <c:out value="${params.untake eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">无收件</span>
          </label>
           </li> 
           
            
            <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat"
                       type="reset" value="重置"/>
            </li>
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" value="刷新" id="reload_btn"/> </div>
	     <shiro:hasPermission name="DEFAULT">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
	        </shiro:hasPermission>
	         <div class="search_new"><input class="sear_butt" type="submit" value="打印" id="print"/> </div>
	         <div class="search_new"><input class="sear_butt" type="submit" value="设置" id="config"/> </div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	
	
	<%int col = 0; %>
		<c:forEach items="${tableFieldSorts }" var="taf">
          	<c:if test="${taf.isShow==1 }">
          		<%col++; %>
          	</c:if>
          </c:forEach>

<div class="tbdata" style="overflow-x:auto;">
	<%if(col<=20){%>
		<table width="1200px;" cellspacing="0" class="t2" id="t2" >
	 <%}else{%>
		<table width="<%=1200+(col-20)*70 %>px" cellspacing="0" class="t2" id="t2" >
	<%} %>
    <%--<table width="auto" cellspacing="0" class="t2" id="t2" style="overflow-x:scroll;"> --%>
        <thead>
        <tr>
          <th align="center"  class="nosort">&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;</th>
          <c:forEach items="${tableFieldSorts }" var="taf">
          	<c:if test="${taf.isShow==1 }">
          		<th align="center">${taf.colName}</th>
          	</c:if>
          </c:forEach>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''} <c:out value="${ item.deled==1?'deled':''}"></c:out>">
            
              
               <td align="center"><input class="ids" name="ids"  type="checkbox" value="${item.id}" /></td> 
               
               	   <c:forEach items="${tableFieldSorts }" var="taf">
		          	<c:if test="${taf.isShow==1 }">
		          		<td align="center">
		          		<c:set var="k" value="${taf.col }" scope="page" ></c:set>
		          		<c:choose> 
		          		<c:when test="${k=='id' }">
		          			${status.count}
		          		</c:when>
		          		<c:when test="${k=='examiner' }">
		          			${item.examiner}
		          		</c:when>
		          		
		          		<c:when test="${k=='examine_time' }">
		          			${item.examine_time}
		          		</c:when>
		          		
		          		<c:when test="${k=='order_plane' }">
		          		   <c:if test="${!empty item.take_plane or !empty item.send_plane }">
		          			<img src="/themes/default/images/plane_pic.png"  />
		          			</c:if>
		          		</c:when>
		          		<c:when test="${k=='lgc_order_no' }">
		          			<a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a>
		          		</c:when>
		          		
		          		<c:when test="${k=='sub_station_no' }">
		          			<u:dict name="S" key="${item.sub_station_no}" />
		          		</c:when>
		          		<c:when test="${k=='send_substation_no' }">
		          			<u:dict name="S" key="${item.send_substation_no}" />
		          		</c:when>
		          		
		          		<c:when test="${k=='send_courier_no' }">
		          			<u:dict name="C" key="${item.send_courier_no}" />
		          		</c:when>
		          		<c:when test="${k=='source' }">
		          			<u:dict name="ORDER_SRC" key="${item.source}" />
		          		</c:when>
		          		
		          		<c:when test="${k=='freight_type' }">
		          			<c:if test="${item.freight_type eq 2 }">收方付</c:if><c:if test="${item.freight_type ne 2 }">寄方付</c:if>
		          		</c:when>
		          		<c:when test="${k=='pay_type' }">
		          			<u:dict name="PAY_TYPE" key="${item.pay_type}" />
		          		</c:when>
		          		<c:when test="${k=='create_time' }">
		          			<fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
		          		</c:when>
		          		<c:when test="${k=='send_addr' }">
		          			${item.send_area}&#12288;${item.send_addr}
		          		</c:when>
		          		
		          		<c:when test="${k=='send_order_time' }">
		          			<fmt:formatDate value="${item.send_order_time}" pattern="yyyy-MM-dd HH:mm:ss" />
		          		</c:when>
		          		<c:when test="${k=='rev_addr' }">
		          			${item.rev_area}&#12288;${item.rev_addr}
		          		</c:when>
		          		<c:when test="${k=='remark' }">
		          			<u:dict name="ORDER_NOTE" key="${item.id}" />
		          		</c:when>
		          		
		          		<c:when test="${k=='asign_status' }">
		          			<c:if test="${item.asign_status ne 'S' and item.asign_status ne 'C' and item.status eq '1' }">未分配</c:if>
		                    <c:if test="${item.asign_status eq 'S'}">已分配</c:if>
		                    <c:if test="${item.asign_status eq 'C' or item.status  eq '2' or item.status eq '3' or item.status eq '7' or item.status eq '8'}">已分配</c:if>
		          		</c:when>
		          		<c:when test="${k=='status' }">
		          			<u:dict name="ORDER_STATUS" key="${item.status}" /> 
		          		</c:when>
		          		<c:when test="${k=='edit' }">
		          			<c:if test="${item.deled!=1}">
			                  <a href="javascript:note('${item.id}','${item.lgc_order_no}');">备注</a>
			                       |
			                   <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
			                     <c:if test="${!empty item.lgc_order_no}">                 
			                        |  <a href="javascript:bangding('${item.order_no}','${item.lgc_no}','${item.lgc_order_no}');">修改运单号</a> </c:if>   
			                 <c:if test="${item.examine_status ne 'PASS'}"> |<a href="javascript:deled('${item.id}');">无效单</a></c:if>   
			                 </c:if>        
			                <c:if test="${item.deled==1}">无效单  | <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a></c:if> 
		          		</c:when>
		          		<c:otherwise>
		          			${item[k]}
		          		</c:otherwise>
		          	  </c:choose>
		          	  </td>
		          	</c:if>
		          </c:forEach>
               <%--
              <td align="center">${status.count}</td>
            <td><a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a></td>    
            <td>${item.monthSname}</td> 
            <td>${item.month_settle_no}</td> 
             <td>${item.item_weight}</td>
            <td>${item.freight}</td>
             <td>${item.vpay}</td>
            <td>${item.good_price}</td>  
            <td><u:dict name="S" key="${item.sub_station_no}" /></td> 
            <td><u:dict name="S" key="${item.send_substation_no}" /></td>   
             <td>${item.take_courier_name}</td>
            <td><u:dict name="C" key="${item.send_courier_no}" /></td>   
            <td>${item.rece_no}</td>
            <td>${item.for_no}</td>  
            <td><u:dict name="ORDER_SRC" key="${item.source}" /></td>
            <td><u:dict name="ORDER_STATUS" key="${item.status}" /> </td>
              <td align="center">
                   <c:if test="${item.deled!=1}">
                  <a href="javascript:note('${item.id}','${item.lgc_order_no}');">备注</a>
                       |
                   <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
                     <c:if test="${!empty item.lgc_order_no}">                 
                        |  <a href="javascript:bangding('${item.order_no}','${item.lgc_no}','${item.lgc_order_no}');">修改运单号</a> </c:if>   
                 <c:if test="${item.examine_status ne 'PASS'}"> |<a href="javascript:deled('${item.id}');">无效单</a></c:if>   
                 </c:if>        
                <c:if test="${item.deled==1}">无效单  | <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a></c:if> 
               </td>
                 --%>
             <%-- <td><fmt:formatDate value="${item.create_time}" type="both"/></td>      
                 <td>${item.inputer}</td>    
               <shiro:hasPermission name="DEFAULT">
                <td><c:if test="${item.examine_status ne 'PASS'}"><span style="color: orange;">未审核</span></font></c:if>
                  <c:if test="${item.examine_status eq 'PASS'}">已审核</font></c:if></td>
                 </shiro:hasPermission> --%>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${orderList.pages}"
                     current="${orderList.pageNum}" sum="${orderList.total}"/>
</div>
</body>

</html>