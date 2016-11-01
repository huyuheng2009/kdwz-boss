<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
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
        
        function track1(orderNo){
        	$.dialog({lock: true,title:'运单编辑',drag: true,width:1400,height:810,resize: false,max: false,content: 'url:${ctx}/order_ext/edit_track?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function examine(ids){
        	$.dialog({lock: true,title:'订单审核',drag: true,width:560,height:250,resize: false,max: false,content: 'url:${ctx}/order/examine_page?ids='+ids+'&layout=no',close: function(){
        	 }});
        }
        function edit(orderNo,sid){
        	$.dialog({lock: true,title:'审核编辑',drag: true,width:700,height:800,resize: false,max: false,content: 'url:${ctx}/order/examine_edit?orderNo='+orderNo+'&sid='+sid+'&layout=no',close: function(){
        	 }});
        }

        $(function () {
        	trHover1('t2');
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
            $('#add').click(function(){
          		 location.href="/order/add";
          	});
            
            $('.select_all').click(function() {
            	 if($(this).prop("checked"))	{
            			$('input[name=ids]').each(function(){
                    		$(this).prop('checked',true); 
                    	}); 
            	 }else{
            		 $('input[name=ids]').each(function(){
                 		$(this).prop('checked',false); 
                 	}); 
            	 }
            
            });
            
       
            
            $('#batch_examine').click(function(){
            	var ids = '';
            	$('.ids:checked').each(function(){
            		ids += $(this).val()+','; 
            	});
            	if(ids.length>0){
            		ids = ids.substring(0, ids.length-1) ;
            	}else{
            		alert("请选择一项或多项！");
            		return ;
            	}
            	 if(confirm("是否全部审核通过？")){
            		  $.ajax({url: '/order/examine_save', data: {'ids': ids,'examineStatus': 'PASS'}, success: function (msg) {
                         if (msg == 1) {
                             alert('审核成功');
                             //reload location.reload();
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
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/order/examine" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
         <textarea  cols="18" rows="4"  maxlength="75" value="${params['orderNo']}" placeholder="请输入运单号" name="orderNo" class="order_area" >${params['orderNo']}</textarea> 
      <%--       <li>运单号：<input type="text" value="${params['orderNo']}" placeholder="运单号" name="orderNo"></input></li> --%>
         	      <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			    <li><span>派件时间：</span>
			 	<input id="dateBegin1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="sendTimeBegin" value="${params['sendTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="sendTimeEnd" value="${params['sendTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
<!-- 			  <li>是否有误：<select name="erred" > -->
<!-- 			  <option value="">全部</option> -->
<%-- 			  <option value="1" ${params['erred'] eq '1'?'selected':''}>是</option> --%>
<%-- 			  <option value="2" ${params['erred'] eq '2'?'selected':''} >否</option> --%>
<!-- 			  </select></li> -->
<!-- 			  <li>审核状态：<select name=examineStatus style="width: 110px"> -->
<!--                 <option value="">--全部--</option> -->
<%--                 <option value="WAIT" ${params['examineStatus'] eq 'WAIT'?'selected':''}>待审核</option> --%>
<%--                 <option value="NONE" ${params['examineStatus'] eq 'NONE'?'selected':''}>未通过</option> --%>
<%--                 <option value="PASS" ${params['examineStatus'] eq 'PASS'?'selected':''}>已通过</option> --%>
<!--              </select></li> -->
            <li>收件员/派件员：<input type="text" value="${params['courierNo']}" name="courierNo"></input></li>
            <li><input class="button input_text  medium blue_flat"
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat" 
                       type="reset" value="重置"/>
            </li>
        </ul>
    </form:form>
   </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
  	 <div class="operator">
	    <div class="search_new">   <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>
<!-- 	    <div class="search_new">   <input style="width: 100px;" class="button input_text  medium blue_flat" type="submit" style="float: right;" value="批量审核" id="batch_examine"/>  </div> -->
	    </div>
  </div>   <!-- tableble_search end  -->    
</div>


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;</th>
            <th  align="center" >运单号</th>
            <th  align="center" >是否有误</th>
            <th  align="center" >收件员</th>
            <th  align="center" >派件员</th>
            <th  align="center" >运费合计</th>
            <th  align="center" >代收款</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >月结账号</th>
            <th  align="center" >代收客户号</th>
            <th  align="center" >会员号</th>
            <th  align="center" >寄/派时间</th>
            <th  align="center" width="10%" >操作</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
             <tr class="over_${status.count}" over="over_${status.count}">
                <td  rowspan="2" align="center"><input class="ids" name="ids" type="checkbox" value="${item.id}" /></td>
                <td  rowspan="2"> <a href="javascript:track1('${item.order_no}');">${item.lgc_order_no}</a></td>
                <td  rowspan="2"><c:if test="${item.si_erred ne 'N'}"><span style="color: red;">是</span></c:if><c:if test="${item.si_erred eq 'N'}">否</c:if></td>
                <td>${item.take_courier_name}</td>
                <td>---</td>
                <td>${item.freight+item.vpay+item.dpay}</td>
                <td>${item.good_price}</td>
                <td><u:dict name="FREIGHT_TYPE" key="${item.freight_type}" /></td>
                <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td>
                <td>${item.month_settle_no }</td>
                <td>${item.cod_name }</td>
                <td>${item.dis_user_no}</td>
                <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                <td  rowspan="2" align="center">
                   <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
                </td> 
              </tr>
           <tr class="over_${status.count}" over="over_${status.count}">
                <td>---</td>
                <td>${item.send_courier_name}</td>    
                <td>${item.si_freight+item.si_vpay+item.si_dpay}</td>
                <td>${item.si_good_price}</td>
                <td><u:dict name="FREIGHT_TYPE" key="${item.si_freight_type}" /></td>
               <td><u:dict name="PAY_TYPE" key="${item.si_pay_type}" /></td>
                <td>${item.si_month_settle_no }</td>
                 <td>${item.si_cod_name }</td>
                <td>${item.si_dis_user_no}</td>
                 <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>
              </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
<pagebar:pagebar total="${list.pages}"  current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>