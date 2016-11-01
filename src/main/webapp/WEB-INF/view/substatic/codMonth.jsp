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
        function settle(id){
        	 if(confirm('确定已打款吗？')){
        			$.ajax({url:'${ctx}/substatic/cod_settle',data:{'id':id},success:function(msg){
        				 if(msg=='1'){
        					 alert('修改成功');
        					 location.reload();
        				 }else{
        					 alert(msg);
        				 }
        			 }});
        		}
        }
        function print(id){
        	 if(confirm('确定已打印发票吗？')){
     			$.ajax({url:'${ctx}/substatic/cod_print',data:{'id':id},success:function(msg){
     				 if(msg=='1'){
     					 alert('修改成功');
     					 location.reload();
     				 }else{
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
        function cod_price(){
        	var orderBy = $('input[name=orderBy]').val();
        	if('3'==orderBy){
        		  $('input[name=orderBy]').val('4');
        	 }else{
        		 $('input[name=orderBy]').val('3'); 
        	 }
        	  $("form:first").submit();
        }
        function return_price(){
        	var orderBy = $('input[name=orderBy]').val();
        	if('5'==orderBy){
        		  $('input[name=orderBy]').val('6');
        	 }else{
        		 $('input[name=orderBy]').val('5'); 
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
            $jqq("#courierNo").autocomplete(availablesrcKey1, {
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
         			 $("#courierNo").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
   			       } 
         	});	 
            
            
           	var codList = tmjs.codlist ;
           	var codres = [];
           	$.each(codList, function(i, item) {
           		var name = item.cod_sname ;
           		if(name.length<1){name = item.cod_name ;}
           		codres[i]=item.cod_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
                }); 
           	  $jqq("#codNo").autocomplete(codres, {
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
                $("form:first input[name=serviceName]").val('codMonthCount');
        	   $("form:first").attr("action","${ctx}/substatic/export").submit();
        	   $("form:first").attr("action",action);
        	   loaddata_end() ;
        	});
            
        });
        
        

    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/substatic/codMonth" method="post" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value=""/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
      <input type="hidden" name="orderBy" value="${params['orderBy']}"/>
        <ul>
          <li><span>账单月份：</span>
			 	<input onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true,maxDate:'#F{\'%y-%M\'}'})"   type="text"  style="width:112px" name="settleDate" value="${params['settleDate']}" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
			</li>
            <li class="search_cont_input">快递员：<input type="text" value="${params['courierNo']}" id="courierNo" name="courierNo"></input></li>
             <li class="search_cont_input">代收货款号：<input type="text" value="${params['codNo']}" id="codNo" name="codNo"></input></li>
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
	   </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >序号</th>
            <th align="center" ><a onclick="codno()">代收货款号</a></th>
            <th align="center" >公司简称</th>
            <th align="center" >账单月份</th>
            <th align="center" >所属快递员</th> 
            <th align="center" >打款时间</th>
            <th align="center" ><a onclick="cod_price()">代收现金</a></th>
            <th align="center" >扣款费率</th>
            <th align="center" ><a onclick="return_price()">返款额</a></th>
            <th align="center" >结算方式</th>
            <th align="center" >结算户名</th>
            <th align="center" >结算银行</th>
            <th align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
         <c:set var="sum_cod_price" value="0"></c:set>
          <c:set var="sum_return_price" value="0"></c:set>
        <c:forEach items="${orderList.list}" var="item" varStatus="status">
         <c:set var="sum_cod_price" value="${sum_cod_price+item.cod_price}"></c:set>
          <c:set var="sum_return_price" value="${sum_return_price+item.return_price}"></c:set>
        <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
            <td>${status.count}</td>
            <td>${item.cod_no}</td>   
            <td>${item.cod_sname}</td>               
            <td>${item.settle_date}</td> 
            <td><u:dict name="C" key="${item.courier_no}" /></td>
            <td><fmt:formatDate value="${item.settle_time}" type="both"/></td>  
             <td>${item.cod_price}</td>
             <td>${item.discount}‰(千分之)</td>   
             <td>${item.return_price}</td>
             <td>${item.settle_type}</td>
             <td>${item.settle_card_no}</td>
             <td>${item.settle_bank}</td>
              <td align="center">
              <c:if test="${item.settled ne 'Y'}"><a href="javascript:settle('${item.id}');">已打款 </a></c:if>
               <c:if test="${item.settled eq 'Y'}">已打款</c:if>
                   | 
               <c:if test="${item.printed ne 'Y'}"><a href="javascript:print('${item.id}');">已打印发票 </a></c:if>
               <c:if test="${item.printed eq 'Y'}">已打印发票</c:if>
             </td>
            </tr>
        </c:forEach>
          <tr style="background: #f5f5f5;border: none;" >
                   <td style="background: #f5f5f5;border: none;"  colspan="6"></td>
                   <td style="background: #f5f5f5;border: none;"> 合计：${sum_cod_price }</td>
                   <td style="background: #f5f5f5;border: none;"></td>
                   <td style="background: #f5f5f5;border: none;"> 合计：${sum_return_price }</td>
                   <td style="background: #f5f5f5;border: none;" colspan="4"></td>
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