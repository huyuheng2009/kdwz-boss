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
.scoll_search_fixed{position:fixed;margin:0 auto;top:0;width: 1180px;z-index:99;}
.tableble_table tr.el td{
	background: #8cf6ff;
}
</style>
    <script type="text/javascript">
  
    function showAddr(e){
    	$(".out_address").css("display","none");
    	$(e).siblings(".out_address").css("display","block");
    }


        $(function () {
  
	
            new TableSorter("t2");
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload()
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
   				   } 
   			});	
   					 
   		  var monthList =  tmjs.mlist;			
          var mres = [];
          $.each(monthList, function(i, item) {
      		mres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_sname.replace(/\ /g,"")+')';
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
        
            $('#settleExport').click(function(){
            	 var action = $("form:first").attr("action");
                 $("form:first input[name=serviceName]").val('monthSettleExport');
                 $("form:first").attr("action", "${ctx}/export/service").submit();
                 $("form:first").attr("action", action);
          	});
            
    
            $('#batch_examine').click(function(){
             	var ids = '0,';
          	var pids = '0,' ;
          	
          	$('.ids').each(function(){
                    if($(this).prop("checked"))	{
                  	  ids += $(this).val()+','; 
         			 }else{
         				  pids += $(this).val()+','; 
         			 }
          	});
          	ids = ids.substring(0, ids.length-1) ;
          	pids = pids.substring(0, pids.length-1) ;
          	 if(ids==pids){
          		alert("请选择一项或多项！");
          		return ;
          	} 
          	 if(confirm("是否全部审核？")){
          		  $.ajax({	 
          			 type: "post",//使用get方法访问后台
        	             dataType: "text",//返回json格式的数据
        	            url: '/settle/mexamine_save',
        	            data: {'pids': pids,'ids': ids}, 
        	            success: function (msg) {
                       if (msg == 1) {
                           alert('保存成功');
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

    <form:form id="trans" action="${ctx}/settle/melist" method="post">
     <input type="hidden" name="serviceName" value=""/>
      <input type="hidden" name="ff" value="${params['ff']}"/>
      <input type="hidden" name="settleStatus" value="1"/>
        <ul>
			 <li><span>账单时间：</span><input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
			 </li>
			
			<li>网点编号：<input type="text" placeholder="网点编号"		value="${params['substationNo']}" name="substationNo" id="substationNo"></input> 			
			</li>	 
            <li class="search_cont_input">快递员：<input type="text" value="${params['courierNo']}" name="courierNo" id="courier"></input></li>
           
            <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat"
                       type="reset" value="重置"/>
            </li>
             <li><span>收款时间：</span><input id="dateBegin1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="settleTimeBegin" value="${params['settleTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="settleTimeEnd" value="${params['settleTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
            <li><span>收款类型：</span><select name="settleType" style="width: 138px;">
                    <option value="" >全部</option>
                    <option value="CASH"  ${params['settleType'] eq 'CASH'?'selected':''}>现金</option>
                    <option value="WEIXIN" ${params['settleType'] eq 'WEIXIN'?'selected':''}>微信</option>
                    <option value="BANK" ${params['settleType'] eq 'BANK'?'selected':''}>转账</option>
              </select>
         </li>     
          <li>月结号：<input type="text" placeholder="月结客户编号"	value="${params['monthSettleNo']}" name="monthSettleNo"	id="monthSettleNo"></input> 	</li>	
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search scoll_search" id="scoll_search">
        <div class="operator">
	       <div class="search_new"><input class="sear_butt" type="submit" value="批量审核" id="batch_examine"/> </div>
	       <div class="search_new"><input class="sear_butt" type="submit" value="批量导出" id="settleExport"/> </div>
	        <div class="search_new"><input class="sear_butt" type="submit" value="刷新" id="reload_btn"/> </div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

  
<div class="tbdata">
   <table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
				    <th align="center" class="nosort sumc"><input class="select_all" name="select_all" type="checkbox"  /></th>
					<th  align="center">序号</th>
					 <th align="center" >收款状态</th>
					<th  align="center">账单月份</th>
					<th  align="center">月结账号</th>
					<th  align="center">客户简称</th>		
					<th  align="center">网点编号</th>		
					<th  align="center">快递员编号</th>	
					<th  align="center">快递员</th>	
					<th  align="center">总票数</th>	
					<th  align="center">应收金额</th>	
					<th  align="center">实收金额</th>	
					<th  align="center">收款时间</th>
					<th  align="center">收款员</th>
			     	<th  align="center">收款类型</th>
			     	<th  align="center">收款说明</th>
				</tr>
			</thead>
			<tbody>
			 <c:set var="sum_month_count" value="0"></c:set>
            <c:set var="sum_sum_count" value="0"></c:set>
            <c:set var="sum_settle_count" value="0"></c:set>
				<c:forEach items="${orderList.list}" var="item" varStatus="status">
				<c:set var="sum_month_count" value="${sum_month_count+item.month_count}"></c:set>
                <c:set var="sum_sum_count" value="${sum_sum_count+item.sum_count}"></c:set>
                <c:set var="sum_settle_count" value="${sum_settle_count+item.settle_count}"></c:set>
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					            <td align="center"><input  class="ids" name="ids" type="checkbox" <c:out value="${item.examine_status eq '1'?'checked':''}" />  value="${item.id}" /></td>
						<td align="center">${status.count}</td>
						<td><c:if test="${item.settle_status ne '1'}"><span style="color: red;">未收款</span></c:if> 
                               <c:if test="${item.settle_status eq '1'}"><span style="color:green;">已收款</span></c:if>
                       </td>
						<td>${item.settleMonth}</td>
						<td>${item.month_no}</td>
						<td>${item.month_sname}</td>
						<td>${item.sno}</td>
						<td>${item.cno}</td>
						<td>${item.cname}</td>
						<td>${item.sum_count}</td>
						<td>${item.month_count}</td>
						<td>${item.settle_count}</td>
			             <td><fmt:formatDate value="${item.settle_time}" type="both"/></td>
                         <td>${item.settle_name}</td>
                         <td>${item.tname}</td>
                         <td><a onclick="showAddr(this)">${fn:substring(item.note,fn:length(item.note)-6,fn:length(item.note))}</a><div class="out_address">${item.note}</div></td>
					</tr>
				</c:forEach>
				<tr>
                   <td style="background: #f5f5f5;border: none;"  colspan="2"></td>    
                   <td style="background: #f5f5f5;border: none;"   colspan="4">合计</td> 
                    <td style="background: #f5f5f5;border: none;"  colspan="3"></td>    
                   <td style="background: #f5f5f5;border: none;"> ${sum_sum_count }</td>   
                   <td style="background: #f5f5f5;border: none;"> ${sum_month_count }</td>
                    <td style="background: #f5f5f5;border: none;"> ${sum_settle_count }</td>
                <td style="background: #f5f5f5;border: none;" colspan="5"></td>
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