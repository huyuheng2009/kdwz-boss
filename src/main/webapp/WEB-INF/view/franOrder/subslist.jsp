<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:300px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin: -40px 0px 0 50px;display: none;}
</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>

   <script type="text/javascript">
   
   function showAddr(e){
	  
	   if("block"==$(e).siblings(".out_address").css("display")){
		   $(".out_address").css("display","none"); 
	   }else{
		   $(".out_address").css("display","none");
		   	$(e).siblings(".out_address").css("display","block");
	   }
   	
   }
 	
	
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
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
                       $jqq("#takeNo").autocomplete(slists, {
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
                    			 $("#takeNo").val(data[0]) ;
              			       } 
                    	});	
                
                       $jqq("#sendNo").autocomplete(slists, {
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
                   			 $("#sendNo").val(data[0]) ;
             			       } 
                   	});
               
                      	var data1 = tmjs.clist ;
              	          	var availablesrcKey1 = [];
              	              $.each(data1, function(i, item) {
              	              	var inner_no = "" ;
              	              	if(item.inner_no){inner_no=item.inner_no+','}
              	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
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
                                			 $("#tcourierNo").val(data[0]);
                                	});	 
               
         }); 	      
            
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
         	   $("form:first").attr("action","${ctx}/export/service").submit();
         	   $("form:first").attr("action",action); 
        	});
        	
        	$('#queren').click(function(){
        	   	var ids = '';
            	$('.ids').each(function(){
                      if($(this).prop("checked"))	{
                    	  ids += $(this).val()+','; 
           			 }
            	});
            	if(ids.length<1){
            		alert("请选择一项或多项！");
            		return ;
            	}else{
            		ids = ids.substring(0, ids.length-1) ;
            	}
            	 if(confirm("是否全部确认？")){
            		  $.ajax({	 
            			 type: "post",//使用get方法访问后台
          	             dataType: "text",//返回json格式的数据
          	            url: '/franOrder/queren',
          	            data: {'ids': ids,'moneyType':'P'}, 
          	            success: function (msg) {
                         if (msg == 1) {
                             alert('账单确认完成');
                             //location.reload();
                             //$("form:first").submit();
                         } else {
                             alert(msg);
                         }
                     }});
               } 
        		
        	});
        	
        	
        	
        });            
    </script>
</head>
<body>
<div class="search">

      <div class="soso">
        <div class="soso_left search_cont">
    <form:form id="trans" action="${ctx}/franOrder/subslist" method="post">
     <input type="hidden" name="serviceName" value="jmsubslist"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
        	<textarea  cols="18" rows="4"   maxlength="1400" placeholder="请输入运单号" name="lgcOrderNo" class="order_area" >${params['lgcOrderNo']}</textarea> 
		     <li><select name="ttype" style="width: 110px">
		          <option value="RRR" ${params['ttype'] eq 'RRR'?'selected':''}>派件时间</option>
                 <option value="SSS" ${params['ttype'] eq 'SSS'?'selected':''}>寄件时间</option>
                 <option value="QQQ" ${params['ttype'] eq 'QQQ'?'selected':''}>确认时间</option>
             </select></li>  
		   <li>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li>
		<li>派件员：<input type="text"  value="${params['tcourierNo']}" name="tcourierNo" id="tcourierNo" style="width: 200px;"></input></li>
       <li>批带状态：<select name="settleStatus" style="width: 110px">
                <option value="">--全部--</option>
                <option value="NONE" ${params['settleStatus'] eq 'NONE'?'selected':''}>未批带</option>
                <option value="SUCCESS" ${params['settleStatus'] eq 'SUCCESS'?'selected':''}>已批带</option>
             </select></li>
       <li>寄件网点：<input type="text" value="${params['takeNo']}" name="takeNo" id="takeNo" style="width: 200px;"></input></li>
       <li>派件网点：<input type="text" value="${params['sendNo']}" name="sendNo" id="sendNo" style="width: 200px;"></input></li>
        
        <li>物品类型：<u:select id="itype" sname="itype" stype="ITEM_TYPE"  value="${params.itype}"/></li> 
        <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
          <div class="search_new"><input class="sear_butt" type="submit" id="queren" value="批量确认" /></div>
	    <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div> 
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
         <th align="center" class="nosort"><input class="select_all" name="select_all" type="checkbox"  /></th>
					<th align="center">序号</th>
					<th align="center">批带状态</th>
					<th align="center">确认时间</th>
					<th align="center">确认人编号</th>
					<th align="center">确认人</th>
					<th align="center">运单号</th>
					<th align="center">寄件时间</th>
					<th align="center">寄件网点</th>
					<th align="center">收件员编号</th>
                    <th align="center">收件员</th>
                   <th align="center">物品类型</th>
                   <th align="center">重量</th>
                  <th align="center">派件网点</th>
                  <th align="center">派件时间</th>
                  <th align="center">派件员编号</th>
                  <th align="center">派件员</th>
                  <th align="center">派件费</th>
                  <th align="center">批带时间</th>
	</tr>
				
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td align="center"><input  class="ids" name="ids" type="checkbox"  <c:out value="${item.settle_status ne 'SUCCESS'?'disabled':''}" />  value="${item.id}" /></td>
            <td>${status.count}</td>
             <td><c:if test="${item.settle_status eq 'SUCCESS'}">已批带</c:if><c:if test="${item.settle_status ne 'SUCCESS'}">未批带</c:if>  </td>   
              <td>${item.confirm_time}</td>
              <td>${item.confirm_name}</td>
               <td>${item.confirm_real_name}</td>        
            <td>${item.lgc_order_no}</td>
            <td>${item.take_order_time}</td> 
            <td>${item.tsname}</td> 
            <td>${item.tcno}</td> 
             <td>${item.tcname}</td> 
            <td>${item.item_type}</td> 
            <td>${item.item_weight}</td> 
            <td>${item.ssname}</td> 
             <td>${item.send_order_time}</td> 
            <td>${item.scno}</td> 
            <td>${item.scname}</td> 
            <td>${item.settle_money}</td> 
            <td>${item.settle_time}</td> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>