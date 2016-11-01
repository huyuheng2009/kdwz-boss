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

   <script type="text/javascript">
   
   function cno(){
   	var orderBy = $('input[name=orderBy]').val();
   	if('1'==orderBy){
   		  $('input[name=orderBy]').val('2');
   	 }else{
   		 $('input[name=orderBy]').val('1'); 
   	 }
   	  $("form:first").submit();
   }
   
   function sno(){
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
                //location.reload()
           	 $("form:first").submit();
       	});
            
        	jQuery.ajax({
    		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
    		      dataType: "script",
    		      cache: true
    		}).done(function() {
    		     // var slist = ${slist}; 
                var slist =  tmjs.slist ;
                // $.getJSON("${ctx}/lgc/calllist", function(data1) {
                 	var slists = [];
                     $.each(slist, function(i, item) {
                     	var inner_no = "" ;
                     	if(item.inner_no){inner_no=item.inner_no+','}
                     	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                     });
                     val1 = '';
                       $jqq("#substationNo").autocomplete(slists, {
                    		minChars: 0,
                    		max: 12,
                    		autoFill: true,
                    		mustMatch: false,
                    		matchContains: true,
                    		scrollHeight: 220,
                    		formatItem: function(slists, i, total) {
                    			return slists[0];
                    		}
                    	}).result(function(event, data, formatted) {
                     		if(data[0].indexOf(')')>-1){
                    			 $("#substationNo").val(data[0]) ;
              			       } 
                    	});	
               
                   	var data1 = tmjs.clist ;
       	         //var data1 = ${clist}; 
       	         // $.getJSON("${ctx}/lgc/calllist", function(data1) {
       	          	var availablesrcKey1 = [];
       	              $.each(data1, function(i, item) {
       	              	var inner_no = "" ;
       	              	if(item.inner_no){inner_no=item.inner_no+','}
       	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
       	              });
                            val1 = '';
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
                         			 $("#courierNo").val(data[0]);
                         	});	 
                          }); 	      
            
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
         	   $("form:first").attr("action","${ctx}/export/service").submit();
         	   $("form:first").attr("action",action);
         	  loaddata_end() ;
        	});
        	
        	$('#base').click(function(){
        	  	$.dialog({lock: true,title:'基数设置',drag: true,width:550,height:750,resize: false,max: false,content: 'url:${ctx}/wages/base?layout=no',close: function(){
        	   	 }});
        	});
        	
        });
        	
        	function payoff(a){
        		if(confirm("确定批量发工资吗?")){
        		var dateMonth=$("input[name=dateMonth").val();
        		var tcDay = $("input[name=tcDay]:checked").val();
        		$.ajax({
        			   type: "POST",
        			   url: "${ctx}/wages/payOff",
        			   data: "dateMonth="+dateMonth+"&tcDay="+tcDay+"&courierNo="+a,
        			   success: function(msg){
        			     alert(  msg );
        			   }
        			});
        	}
        	}
        	
        	
       $(function(){
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
    	   
    	   $("#batchPayOff").click(function(){
    		   if(confirm("确定批量发工资吗?")){
	    		   var ids ="";
	    		   $("input[name=ids").each(function(){
	    			   if($(this).prop('checked')){
	    					ids=ids+$(this).val()+",";
	    				}
	    		   });
	    		   var tcDay = $("input[name=tcDay]:checked").val();
	    		   var dateMonth = $("input[name=dateMonth").val();
	    		   $.ajax({
        			   type: "POST",
        			   url: "${ctx}/wages/batchPayOff",
        			   data: "dateMonth="+dateMonth+"&tcDay="+tcDay+"&courierNo="+ids,
        			   success: function(msg){
        			     alert(  msg );
        			   }
        			});
    		   	}
	    	   });
    	   
       })
       
    </script>
    <style type="text/css">
    .el table,.el tr,.el td{background:#8cf6ff}
    </style>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/wages/list" method="post">
     <input type="hidden" name="serviceName" value="wagesList"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
     <input type="hidden" name="orderBy" value="${params['orderBy']}"/>
        <ul>
	 <li><span>账单时间：</span>
			<input onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true,maxDate:'#F{\'%y-%M\'}'})"   type="text"  style="width:120px" name="dateMonth" value="${params['dateMonth']}" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
		</li>	
       <li>网点名称：<input type="text" value="${params['substationNo']}" name="substationNo" id="substationNo" style="width: 200px;"></input></li>
        <li>&#12288;快递员：<input type="text" value="${params['courierNo']}" name="courierNo" id="courierNo" style="width: 200px;"></input></li>  
        <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
        <li><lable><input type="radio" <c:out value="${params.tcDay eq 'A'?'checked':''}"/> name="tcDay" value="A"/> &nbsp;按最后一次修改</lable> &nbsp; &nbsp; &nbsp;<input name="tcDay" <c:out value="${params.tcDay ne 'A'?'checked':''}"/> type="radio" value="" /> &nbsp;按每天统计</lable></li>   
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
	      <div class="search_new"><input class="sear_butt" type="submit" id="base" value="基数设置" /></div>
	      <div class="search_new"><input class="sear_butt" type="submit" id="batchPayOff" value="批量发工资" /></div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th  align="center"><input type="checkbox" id="checkall" class="select_all"></input></th>
					<th  align="center">序号</th>
					<th  align="center">账单时间</th>
					<th  align="center"><a onclick="sno()">网点</a></th>
					<th  align="center"><a onclick="cno()">快递员编号</a></th>
					<th  align="center">快递员</th>
					<th  align="center">名称</th>
					<th colspan="2" align="center">同城收件</th>
					<th colspan="2" align="center">同城派件</th>
					<th colspan="2" align="center">外件</th>
					
					 <c:forEach items="${nameList}" var="item0" varStatus="status0">     
                     <th align="center">${item0.cn_name}</th>
                     </c:forEach> 
                     <th align="center">应发工资</th>
                     <th align="center">操作</th>
                     <th align="center">工资发放时间</th>
				</tr>
				
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
         <c:set var="wages" value="${item.take_sum+item.send_sum+item.for_sum}"></c:set>
            <tr class="select_bg" id="${item.courier_no}_1">
            <td rowspan="4"><input type="checkbox" name="ids" value="${item.courier_no}"  <c:out value="${item.pay_time eq null?'':'disabled'}"/>  /></td>
            <td rowspan="4">${status.count}</td>
            <td rowspan="4">${item.ccmonth}</td> 
            <td rowspan="4">${item.substation_name}</td>         
            <td rowspan="4">${item.inner_no}</td>     
            <td rowspan="4">${item.real_name}</td> 
             
             <td>总计</td>
             <td colspan="2">${item.take_sum}</td> 
             <td colspan="2">${item.send_sum}</td> 
             <td colspan="2">${item.for_sum}</td> 
              
          <c:forEach items="${nameList}" var="item1" varStatus="status1">  
                 <c:set var="wages" value="${wages+item[item1.name]}"></c:set>
              <td rowspan="4">
                <c:if test="${item[item1.name]>=0}">${item[item1.name]}</c:if>
                <c:if test="${item[item1.name]<0}">${item[item1.name]*-1}</c:if>
              </td> 
          </c:forEach> 
          <td  rowspan="4"><fmt:formatNumber type="number" value="${wages}" maxFractionDigits="2"/></td> 
          <td rowspan="4">
          		<c:if test="${item.pay_time!=null}">
             	<span>发工资</span>
             	</c:if>
             	<c:if test="${item.pay_time==null}">
             	<a onclick="payoff('${item.courier_no}')">发工资</a>
             	</c:if>
             </td>
             <td rowspan="4">
             	<fmt:formatDate value="${item.pay_time }" pattern="yyyy-MM-dd HH:mm"/>
             </td>
            </tr>
            
            <tr id="${item.courier_no}_2">
            <td>金额</td>
              <td>${item.take_freight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.take_tcf}</c:if></td>  
              <td>${item.take_sum_freight}</td>  
             
              <td>${item.send_freight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.send_tcf}</c:if></td>  
             <td>${item.send_sum_freight}</td> 
             
              <td>${item.for_freight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.for_tcf}</c:if></td>  
             <td>${item.for_sum_freight}</td>  
             </tr>
            
            <tr id="${item.courier_no}_3">
            <td>件数</td>
              <td>${item.take_count}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.take_tcc}</c:if></td>  
             <td>${item.take_sum_count}</td>  
             
              <td>${item.send_count}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.send_tcc}</c:if></td>  
             <td>${item.send_sum_count}</td> 
             
              <td>${item.for_count}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.for_tcc}</c:if></td>  
             <td>${item.for_sum_count}</td>  
          </tr>   
          
             <tr id="${item.courier_no}_4">   
             <td>重量</td>
              <td>${item.take_weight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.take_tcw}</c:if></td>  
             <td>${item.take_sum_weight}</td>  
             
              <td>${item.send_weight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.send_tcw}</c:if></td>  
             <td>${item.send_sum_weight}</td> 
             
              <td>${item.for_weight}<c:if test="${params.tcDay eq 'A'}">&nbsp;x&nbsp;${item.for_tcw}</c:if></td>  
             <td>${item.for_sum_weight}</td> 
             </tr> 
            
            
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
<div>
<pre>

&#12288;&#12288;&#12288;&#12288;<b style="color:red;width:180px;display: inline-block;">总人数:${total.totalCount}</b>收件总票数：<b style="color:red;width:120px;display: inline-block;">${total.total_take_count}</b>收件总重量：<b style="color:red;width:120px;display: inline-block;">${total.total_take_weight}</b>收件总金额：<b style="color:red;width:120px;display: inline-block;">${total.total_take_freight}</b>收件总提成：<b style="color:red;width:120px;display: inline-block;">${total.total_take_sum}</b>

&#12288;&#12288;&#12288;&#12288;<b style="color:red;width:180px;display: inline-block;">应发工资:<fmt:formatNumber type="number" value="${total.total_take_sum+total.total_send_sum+total.total_for_sum+total.total_cost}" maxFractionDigits="2"/></b>派件总票数：<b style="color:red;width:120px;display: inline-block;">${total.total_send_count}</b>派件总重量：<b style="color:red;width:120px;display: inline-block;">${total.total_send_weight}</b>派件总金额：<b style="color:red;width:120px;display: inline-block;">${total.total_send_freight}</b>派件总提成：<b style="color:red;width:120px;display: inline-block;">${total.total_send_sum}</b>

&#12288;&#12288;&#12288;&#12288;<b style="color:red;width:180px;display: inline-block;"></b>外件总票数：<b style="color:red;width:120px;display: inline-block;">${total.total_for_count}</b>外件总重量：<b style="color:red;width:120px;display: inline-block;">${total.total_for_weight}</b>外件总金额：<b style="color:red;width:120px;display: inline-block;">${total.total_for_freight}</b>外件总提成：<b style="color:red;width:120px;display: inline-block;">${total.total_for_sum}</b>
</pre>
</div>


    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>