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
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:470,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function bangding(orderNo,lgcNo,lgcOrderNo){
        	$.dialog({lock: true,title:'修改运单号',drag: true,width:400,height:120,resize: false,max: false,content: 'url:${ctx}/order/bing?lgcNo='+lgcNo+'&orderNo='+orderNo+'&lgcOrderNo='+lgcOrderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function note(id,lgcOrderNo){
        	$.dialog({lock: true,title:'备注',drag: true,width:600,height:350,resize: false,max: false,content: 'url:${ctx}/order_ext/note?id='+id+'&lgcOrderNo='+lgcOrderNo+'&layout=no',close: function(){
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
        function asign(orderNo){
        	$.dialog({lock: true,title:'订单分配',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/order/asign_ss?ids='+orderNo+'&layout=no',close: function(){
        		location.reload();
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
            $('#reload_btn').click(function(){
       		    //location.reload()
            	 $("form:first").submit();
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
                
    			
    			
         	var mlist = tmjs.mlist;	  
         	var mres = [];
         	$.each(mlist, function(i, item) {
         		var name = item.month_sname ;
         		if(name.length<1){name = item.month_name ;}
              	mres[i]=item.month_settle_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
              }); 
         	  $jqq("#monthSettleNo").autocomplete(mres, {
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
        	  
            $('#print').click(function(){
            	var mode = $('input[name=btn_mode]:checked').val() ;
            	if('P'!=mode){
            		alert("请选择打印模式！");
            		return ;
            	}
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
            		$.dialog({lock: true,title:'订单打印',drag: true,width:580,height:700,resize: false,max: false,content: 'url:${ctx}/order/print?ids='+ids+'&layout=no',close: function(){
                  	 }});
            	}else{
            		alert("请选择一项或多项！");
            		return ;
            	}
          	});
            
            $('#batch_asign').click(function(){
            	var mode = $('input[name=btn_mode]:checked').val() ;
            	if('A'!=mode){
            		alert("请选择分配模式！");
            		return ;
            	}
            	var ids = '';
            	$('.ids:checked').each(function(){
            		ids += $(this).val()+','; 
            	});
            	if(ids.length>0){
            		ids = ids.substring(0, ids.length-1) ;
            		$.dialog({lock: true,title:'订单分配',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/order/asign_ss?ids='+ids+'&layout=no',close: function(){
               	 }});
            	}else{
            		alert("请选择一项或多项！");
            		return ;
            	}
          	});
            
        	
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('exportOrderList');
        	   $("form:first").attr("action","${ctx}/order/export").submit();
        	   $("form:first").attr("action",action);
        	   loaddata_end() ;
        	});
            
        });
        
        function validate_form(){
        	var dateEnd =$("#dateEnd").val();
        	var dateBegin =$("#dateBegin").val();
        	var dateEnd1 =$("#dateEnd1").val();
        	var dateBegin1 =$("#dateBegin1").val();
        	if(dateEnd!=''&&dateBegin==''){
        		alert("请输入寄件开始时间");
        		return false;
        	}
        	if(dateEnd1!=''&&dateBegin1==''){
        		alert("请输入签收开始时间");
        		return false;
        	}
        	
        }

    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/order/olist" method="post" onsubmit="return validate_form();">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
         <textarea  cols="18" rows="4"  maxlength="75" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" style="max-height: 125px;min-height: 125px;">${params['orderNo']}</textarea> 
          <%--   <li class="soso_li">运单号： <input type="text" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo"></input> 
              </li> --%>
			      <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:100px" name="createTimeBegin" value="${params.createTimeBegin==null?today:params.createTimeBegin}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span style="margin:0 30px;">~</span>
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:100px" name="createTimeEnd" value="${params.createTimeEnd==null?today:params.createTimeEnd}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			     <li><span>签收时间：</span>
			 	<input id="dateBegin1" onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:100px" name="sendTimeBegin" value="${params['sendTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	<span style="margin:0 30px;">~</span>
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:100px" name="sendTimeEnd" value="${params['sendTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			  <li class="soso_li"><span style="margin-left:15px;">寄件人：</span><input style="width:100px;" type="text" value="${params['sendName']}" name="sendName"></input></li>
			   <li class="search_cont_input"><span style="margin-left:14px;">快递员：</span><input type="text" value="${params['srcourier']}" style="width:100px;" name="srcourier" id="courier"></input></li>
             <li class="search_cont_input">&#12288;&nbsp;月结号：<input id="monthSettleNo" type="text" style="width:100px;" value="${params['monthNo']}" name="monthNo"></input></li>
             
            <li class="soso_li">运单状态：<select name="status" style="width: 110px">
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
              
              <li class="soso_li">&nbsp;分配状态：<select name="asign" style="width: 108px">
                <option value="">--全部--</option>
                <option value="NONE" ${params['asign'] eq 'NONE'?'selected':''}>未分配</option>
                <%-- <option value="SUBSTATION" ${params['asign'] eq 'SUBSTATION'?'selected':''}>已分配给分站</option> --%>
                 <option value="COURIER" ${params['asign'] eq 'COURIER'?'selected':''}>已分配</option>
             </select></li>
             
              <shiro:hasPermission name="DEFAULT">
                 <li><span style="margin-left:2px;">审核状态：</span><select name="examineStatus" style="width: 108px">
                <option value="">--全部--</option>
                <option value="NONE" ${params['examineStatus'] eq 'NONE'?'selected':''}>未审核</option>
                <option value="PASS" ${params['examineStatus'] eq 'PASS'?'selected':''}>已审核</option>
             </select></li>
             </shiro:hasPermission> 
             
             
             <%-- <li>快递公司：<select id="lgcNo" name="lgcNo" style="width: 110px">
							<option value="">--全部--</option>
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
					</select></li> --%>
			 <li><span style="margin-left:27px;">网点：</span><select id="substationNo" name="substationNo" style="width: 108px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
			</select></li> 
            <li>
             <label><input style="float: left;display: inline-block;margin: 8px 5px 0 0; ;margin-left:15px;" type="checkbox" name="reqRece" value="Y" <c:out value="${orderMap.req_rece eq 'Y'?'checked':'' }"/> />&nbsp;&nbsp;<span style="float: left;display: inline-block;">是否有回单</span>
          </label>
           </li> 
            <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询" />
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
           <div class="search_new"><input class="sear_butt" type="submit" value="新增" id="add"/> </div>
		   </shiro:hasPermission>
		   
		    <shiro:hasPermission name="DEFAULT">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
	        </shiro:hasPermission>
	        
		    <shiro:hasPermission name="DEFAULT">
           <div class="search_new"><input class="sear_butt" type="submit" value="分配" id="batch_asign"/> </div>
		   </shiro:hasPermission>
		   
		       <shiro:hasPermission name="DEFAULT">
           <div class="search_new"><input class="sear_butt" type="submit" value="打印" id="print"/> </div>
		   </shiro:hasPermission>
		    <div class="search_new" style="margin: 20px 15px 0 15px;"><input type="radio" checked="checked" name="btn_mode" value="A" onchange="mchange(this)"/> &nbsp;分配模式 &nbsp; &nbsp; &nbsp;<input name="btn_mode" type="radio" onchange="mchange(this)" value="P" /> &nbsp;打印模式</div>
	   </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;</th>
            <th align="center" >运单号</th>
            <th align="center" >回单号</th>
            <th align="center" >物品类型</th> 
            <th  align="center" >收件员</th>
            <th  align="center" >派件员</th>
            <th align="center" >寄件电话</th>
            <th align="center" >寄件地址</th>
            <th align="center" >收件电话</th>
            <th align="center" >收件地址</th>
            <th align="center" >来源</th>
            <th align="center" >运单状态</th>
            <th align="center" >分配状态</th>
            <th align="center" >运费</th>
            <th align="center" >代收款</th>
            <shiro:hasPermission name="DEFAULT">
            <th align="center" >审核</th>
             </shiro:hasPermission>
            <th align="center" >寄件时间</th>
            <th align="center" >签收时间</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''} <c:out value="${ item.deled==1?'deled':''}"></c:out>">
                <td align="center"><input class="ids <c:out value="${empty item.lgc_order_no?'p_dis':' '}"/> <c:out value="${item.status ne '1'?'a_dis':' '}"/>" name="ids"   type="checkbox" value="${item.id}" /></td>
            <td> <a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a></td>    
            <td>${item.rece_no}</td>   
            <td><a href="javascript:images('${item.order_no}');">${item.item_Status}</a></td>
            <td>${item.take_courier_name}</td>
            <td>${item.send_courier_name}</td>       
               <%--   <td><u:dict name="C" key="${item.take_courier_no}" /></td>
                   <td>${fn:substring(item.send_courier_no,fn:length(item.send_courier_no)-3,fn:length(item.send_courier_no))}</td>  --%>
                 <td>${item.send_phone}</td>
                 <c:set var="sendaddr" value="${item.send_area}${item.send_addr}" />   
                 <td><a onclick="showAddr(this)">${fn:substring(sendaddr,fn:length(sendaddr)-6,fn:length(sendaddr))}</a><div class="out_address">${sendaddr}</div></td>
                 
                 <td>${item.rev_phone}</td>
                 <c:set var="revaddr" value="${item.rev_area}${item.rev_addr}" />      
                 <td><a onclick="showAddr(this)">${fn:substring(revaddr,fn:length(revaddr)-6,fn:length(revaddr))}</a><div class="out_address">${revaddr}</div></td>
                 
                <td><u:dict name="ORDER_SRC" key="${item.source}" /></td>
                <td><u:dict name="ORDER_STATUS" key="${item.status}" /> </td>
                <td><%--<c:choose>
                 <c:when test="${item.asign_status ne 'S' and item.asign_status ne 'C' and item.status  eq '1'}"> 未分配 </c:when>
                <c:when test="${item.asign_status eq 'S'}">分站 </c:when>
                <c:otherwise>快递员</c:otherwise>
			     </c:choose> --%>
			     <c:if test="${item.asign_status ne 'S' and item.asign_status ne 'C' and item.status eq '1' }">未分配</c:if>
				                    <c:if test="${item.asign_status eq 'S'}">已分配</c:if>
				                    <c:if test="${item.asign_status eq 'C' or item.status  eq '2' or item.status eq '3' or item.status eq '7' or item.status eq '8'}">已分配</c:if></td>
                <td>${item.freight}</td>
                 <td>${item.good_price}</td>
               <%--  <td>${item.lgc_name}</td> --%>
               <shiro:hasPermission name="DEFAULT">
                <td><c:if test="${item.examine_status ne 'PASS'}"><span style="color: orange;">未审核</span></font></c:if>
                  <c:if test="${item.examine_status eq 'PASS'}">已审核</font></c:if></td>
                 </shiro:hasPermission>
                <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>
                <td align="center">
                 
                   <c:if test="${item.deled!=1}">
                
                 <shiro:hasPermission name="DEFAULT">
                 <c:if test="${item.status eq 1 }"><a href="javascript:asign('${item.id}');">分配</a></c:if>
                 <c:if test="${item.status ne 1 }">分配</c:if>
                        |
                  <a href="javascript:note('${item.id}','${item.lgc_order_no}');">备注</a>
                       |
				</shiro:hasPermission>
                   <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
                   <%--      |
                     <a href="javascript:track('${item.order_no}');">物流动态</a> --%>
                 <%--       <c:if test="${!empty item.lgc_order_no}">
                        |
                       <a href="javascript:bangding('${item.order_no}','${item.lgc_no}','${item.lgc_order_no}');">修改运单号</a>
                       </c:if> --%>
                       <c:if test="${item.examine_status ne 'PASS' }"> |<a href="javascript:deled('${item.id}');">无效单</a></c:if>
                 </c:if>        
                   <c:if test="${item.deled==1}">无效单  | <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a></c:if> 
                </td>
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