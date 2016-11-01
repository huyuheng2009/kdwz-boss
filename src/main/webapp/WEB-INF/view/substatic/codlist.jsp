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

</style>
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        function track(orderNo){
        	$.dialog({lock: true,title:'物流动态',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/order/track?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function codPay(orderNo){
        	$.dialog({lock: true,title:'付款',drag: true,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/substatic/codPay?orderNos='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function noPay(id){
        	 if(confirm("确定取消该运单的付款记录吗？")){
          		  $.ajax({
          			  type: "post",//使用get方法访问后台
         	          dataType: "text",//返回json格式的数据
          			  url: '/substatic/cod_nopay', data: {'id': id}, success: function (msg) {
                       if (msg == 1) {
                           alert('保存成功');
                       } else {
                           alert(msg);
                       }
                   }});
             } 
        }
    	
        
        function codno(){
        	var orderBy = $('input[name=orderBy]').val();
        	if('1'==orderBy){
        		  $('input[name=orderBy]').val('2');
        	 }else{
        		 $('input[name=orderBy]').val('1'); 
        	 }
        	  $("form:first").submit();
        }
        function gp(){
        	var orderBy = $('input[name=orderBy]').val();
        	if('3'==orderBy){
        		  $('input[name=orderBy]').val('4');
        	 }else{
        		 $('input[name=orderBy]').val('3'); 
        	 }
        	  $("form:first").submit();
        }
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload() ;
       	});
       	 jQuery.ajax({
   	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
   	      dataType: "script",
   	      cache: true
   	}).done(function() {	
   		var data1 = tmjs.clist ;
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
               $jqq("#tcourierNo").autocomplete(availablesrcKey1, {
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
          			 $("#tcourierNo").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
    			       } 
          	});	
        
        
               $jqq("#scourierNo").autocomplete(availablesrcKey1, {
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
         			 $("#scourierNo").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
   			       } 
         	});	 	
        
               var monthList =  tmjs.mlist;			
               var sres = [];
               $.each(monthList, function(i, item) {
               	
           		sres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_sname.replace(/\ /g,"")+')';
              });
               
           	  $jqq("#codNo").autocomplete(sres, {
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
             			 $("#codNo").val(data[0].substring(0,data[0].indexOf('('))) ;
       			       } 
             	}); 
        
  	});    			 
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('codMonthList');
        	   $("form:first").attr("action","${ctx}/substatic/export").submit();
        	   $("form:first").attr("action",action);
        	   loaddata_end() ;
        	});
        	
        	
        	$('#countVpay').click(function(){
        		var rate = $.trim($('input[name=cpayRate]').val()) ;
        		var r = /^\d+(.[0-9]{1,4})?$/i ;   //两位小数
        		if(!r.test(rate)){
        			alert("请输入正确的费率","",function(obj){
        				$('input[name=cpayRate]').focus() ;
        			}) ;
        			return ;
        		}
        		
               var ids = '0';
            	
            	$('.ids').each(function(){
                    	  ids += ','+$(this).val(); 
            	});
            	 if(ids.length<2){
            		alert("列表无数据！");
            		return ;
            	} 
        		
        		 if(confirm("确定重算订单手续费吗？")){
             		  $.ajax({
             			  type: "post",//使用get方法访问后台
            	          dataType: "text",//返回json格式的数据
             			  url: '/substatic/cpay_rate', data: {'ids': ids,'rate':rate}, success: function (msg) {
                          if (msg == 1) {
                        		var sum = 0 ;
                       		    $('.cpay').each(function(){
                       			 var goodp = $(this).attr("goodp") ;
                       			 var ratev = (goodp*rate).toFixed(2) ;
                       			     sum = sum - (-ratev) ;
                               		$(this).html(ratev); 
                               	}); 
                       		 $(".cpay_sum").html(sum.toFixed(2));  
                              alert('保存成功');
                          } else {
                              alert(msg);
                          }
                      }});
                } 
        		
        	
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
            
            $('#batchPay').click(function(){
            	var ids = '0';
            	
            	$('.ids').each(function(){
                      if($(this).prop("checked"))	{
                    	  ids += ','+$(this).val(); 
           			 }
            	});
            	 if(ids.length<2){
            		alert("请选择一项或多项！");
            		return ;
            	} 
         $.dialog({lock: true,title:'付款',drag: true,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/substatic/codPay?orderNos='+ids+'&layout=no',close: function(){
            	 }}); 

          	});   
            
            
        });
        
        

    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/substatic/codList" method="post" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value=""/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
      <input type="hidden" name="orderBy" value="${params['orderBy']}"/>
        <ul>
                 <textarea  cols="18" rows="4"  maxlength="1400" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" >${params['orderNo']}</textarea> 
		 <li>
		 <select name="ttype">
		   <option value="SSS" ${params['ttype'] eq 'SSS'?'selected':''}>寄件时间</option>
		   <option value="RRR" ${params['ttype'] eq 'RRR'?'selected':''}>派件时间</option>
		   <option value="PPP" ${params['ttype'] eq 'PPP'?'selected':''}>付款时间</option>
		 </select>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
            <li class="search_cont_input">收件员：<input type="text" value="${params['tcourierNo']}" id="tcourierNo" name="tcourierNo"></input></li>
            <li class="search_cont_input">派件员：<input type="text" value="${params['scourierNo']}" id="scourierNo" name="scourierNo"></input></li>
             <li class="search_cont_input">&#12288;&#12288;&#12288;月结号：<input type="text" id="codNo" value="${params['codNo']}" name="codNo"></input></li>
              <li>付款状态：
		 <select name="payStatus">
		   <option value="">全部</option>
		   <option value="NN" ${params['payStatus'] eq 'NN'?'selected':''}>未付款</option>
		   <option value="YY" ${params['payStatus'] eq 'YY'?'selected':''}>已付款</option>
		 </select>
		 </li>
		 <li>付款方式：
		 <select name="payType">
		   <option value="">全部</option>
		    <option value="现金" ${params['payType'] eq '现金'?'selected':''}>现金</option>
			  <option value="转账" ${params['payType'] eq '转账'?'selected':''}>转账</option>
			   <option value="POS票" ${params['payType'] eq 'POS票'?'selected':''}>POS票</option>
		 </select>
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
         <shiro:hasPermission name="DEFAULT">
	      <div  class="search_new"><input style="width: 100px;" class="sear_butt" type="submit" id="batchPay" value="批量付款" /></div>
	        </shiro:hasPermission>
	      <div class="search_new"><input class="sear_butt" type="submit" value="刷新" id="reload_btn"/> </div>
		    <shiro:hasPermission name="DEFAULT">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" />
	        <span style="margin-left: 190px;color: red;">费率&nbsp;&nbsp;<input type="text" name="cpayRate" id="cpayRate" style="width: 220px;"/> 
	                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input class="sear_butt" type="submit" id="countVpay" value="重算手续费" /></span></div>
	        </shiro:hasPermission>
	         
	   </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" ><input class="select_all" name="select_all" type="checkbox"  /></th>
             <th align="center" >序号</th>
            <th align="center" >月结号</th>
            <th align="center" >公司简称</th>
            <th align="center" >运单号</th>
            <th align="center" >回单号</th> 
             <th align="center" >转单号</th> 
             <th  align="center" >寄件时间</th>
            <th  align="center" >寄件人</th>
            <th align="center" >签收时间</th>
            <th align="center" >签收人</th>
            <th align="center" >物品类型</th>
            <th align="center" >重量</th>
            <th align="center" ><a onclick="gp()">代收货款</a></th>
             <th align="center" >手续费</th>
            <th align="center" >收件员</th>
             <th align="center" >派件员</th>
             <th align="center" >付款状态</th>
             <th align="center" >付款日期</th>
             <th align="center" >付款方式</th>
             <th align="center" >付款人</th>
             <th align="center" >操作</th>

        </tr>
        </thead>
        <tbody>
           <c:set var="sum_good_price" value="0"></c:set>
            <c:set var="sum_cpay" value="0"></c:set>
        <c:forEach items="${orderList.list}" var="item" varStatus="status">
         <c:set var="sum_good_price" value="${sum_good_price+item.good_price}"></c:set>
          <c:set var="sum_cpay" value="${sum_cpay+item.cpay}"></c:set>
        <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
            <td><input name="ids" class="ids"  type="checkbox" value="${item.order_no}" <c:out value="${item.pay_status eq '1'?'disabled':' '}"/> /></td>
             <td>${status.count}</td>  
            <td>${item.month_settle_no}</td>   
            <td>${item.month_sname}</td>               
            <td>${item.lgc_order_no}</td> 
            <td>${item.rece_no}</td>   
            <td>${item.for_no}</td>   
            <td><fmt:formatDate value="${item.create_time}" type="both"/></td>  
             <td>${item.send_name}</td>
             <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>   
             <td>${item.sign_name}</td>
             <td>${item.item_Status}</td>   
             <td>${item.item_weight}</td>
             <td>${item.good_price}</td>
             <td><span class="cpay" goodp="${item.good_price}">${item.cpay}</span></td>
             <td>${item.takeName}</td>
             <td>${item.sendName}</td>
             <td><c:if test="${item.pay_status eq '1'}">已付款</c:if><c:if test="${item.pay_status ne '1'}">未付款</c:if></td>
             <td><fmt:formatDate value="${item.pay_time}" type="both"/></td>   
                 <td>${item.pay_type}</td>
                  <td>${item.pay_name}</td>
             <td>
              <shiro:hasPermission name="DEFAULT">
                  <c:if test="${item.pay_status eq '1'}"><a href="javascript:noPay('${item.d_id}');">取消付款</a></c:if>
                  <c:if test="${item.pay_status ne '1'}"><a href="javascript:codPay('${item.order_no}');">付款</a></c:if>
              </shiro:hasPermission>
             </td>
            </tr>
        </c:forEach>
          <tr>
                   <td style="background: #f5f5f5;border: none;"  colspan="13"></td>
                   <td style="background: #f5f5f5;border: none;"><span class="">${sum_good_price }</span></td>
                    <td style="background: #f5f5f5;border: none;"><span class="cpay_sum"> ${sum_cpay }</span></td>
                <td style="background: #f5f5f5;border: none;" colspan="7"></td>
            </tr>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${orderList.pages}"
                     current="${orderList.pageNum}" sum="${orderList.total}"/>
</div>
</body>

</html>