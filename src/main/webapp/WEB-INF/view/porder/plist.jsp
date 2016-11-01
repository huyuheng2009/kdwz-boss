<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
      
    function show(orderNo){
    	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:470,resize: false,max: false,content: 'url:${ctx}/order/detail?id=111&orderNo='+orderNo+'&layout=no',close: function(){
    	 }});
    }
        function pro_text(id,orderNo){
        	$.dialog({lock: true,title:'处理登记',drag: true,width:1200,height:600,resize: false,max: false,content: 'url:${ctx}/porder/pro_text?orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }  
       /*  function edit(orderNo){
        	$.dialog({lock: true,title:'编辑',drag: true,width:520,height:250,resize: false,max: false,content: 'url:${ctx}/porder/pro_edit?orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }
        function deal(orderNo){
        	$.dialog({lock: true,title:'完成',drag: true,width:450,height:270,resize: false,max: false,content: 'url:${ctx}/porder/pro_deal?orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }    */
        
    
        function rev(orderNo){
        	 if(confirm('确认快件已经到达分站？')){
           		 $.ajax({url:'/scan/srev',data:{'orderNo':orderNo},success:function(msg){
           			 if(msg==1){
           				 alert('操作成功');
           				 location.reload();
           			 }else{
           				 alert(msg);
           			 }
           		 }});
           	 }
        }
        
       
    
        $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
        	    //location.reload()
        	 $("form:first").submit();
       	});
           
       	$('#exportData').click(function(){
   		 var action= $("form:first").attr("action");
    	   $("form:first").attr("action","${ctx}/export/service").submit();
    	   $("form:first").attr("action",action); 
     	});
         
         $('#revscan_btn').click(function(){
        	 $.dialog({lock: true,title:'到件扫描',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/scan/revscan?layout=no',close: function(){
         	}});
       	}); 
         
            
        });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/porder/list" method="get">
     <input type="hidden" name="serviceName" value="porderlist"/>
        <ul>
         <textarea  cols="18" rows="4"  maxlength="75" value="${params['lgcOrderNo']}" placeholder="请输入运单号" name="lgcOrderNo" class="order_area" >${params['lgcOrderNo']}</textarea> 
           <%--  <li>运单号：<input type="text" value="${params['lgcOrderNo']}" placeholder="物流单号" name="lgcOrderNo"></input></li> --%>
            <li><span>寄件日期：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			   <li>接收网点：<select id="curSubstationNo" name="curSubstationNo" style="width: 210px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['curSubstationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
			</select></li>
			   <li>联系电话：<input type="text" value="${params['sendPhone']}" name="sendPhone"></input></li> 
               <li>月结号：<input type="text" value="${params['monthSettleNo']}" name="monthSettleNo"></input></li> 
               <li>问题原因： <u:select id="proReason" sname="proReason" stype="PRO_REASON" value="${params['proReason']}"/></li>
               <li>登记人：<input type="text" value="${params['checkName']}" name="checkName"></input></li> 
             
			    <li>处理状态： <u:select id="dealStatus" sname="dealStatus" stype="DEAL_STATUS" value="${params['dealStatus']}"/></li>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
           <!--  <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li> -->
        </ul>
    </form:form>
   </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
  <div class="operator">
	     <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>   </div>
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div> 
	    </div>
     </div>   <!-- tableble_search end  -->   
</div>


<div class="tbdata">
    	 
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >序号</th>
            <th align="center" >运单号</th>
              <th align="center" >处理状态</th>
            <th align="center" >寄件时间</th>
            <th align="center" >联系电话</th>
            <th align="center" >月结号</th>
            <th align="center" >问题原因</th>
            <th align="center" >详细原因</th>
            <th align="center" >登记人</th>
            <th width="15%" align="center" >完成情况</th>
            <th align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
               <td>  <a href="javascript:show('${item.order_no}');">${item.lgc_order_no}</a></td>
                <td style="background: <u:dict name="DEAL_STATUS_BACKGROUND_COLOR" key="${item.deal_status}" />;color:<u:dict name="DEAL_STATUS_COLOR" key="${item.deal_status}" />;"><u:dict name="DEAL_STATUS" key="${item.deal_status}" /></td>
               <td><fmt:formatDate value="${item.otime}" type="both"/></td>
               <td>${item.send_phone}</td>
               <td>${item.month_settle_no}</td>
               <td><u:dict name="PRO_REASON" key="${item.pro_reason}" /></td>
               <td>${item.descb}</td>
                <td>${item.check_name}</td>
                <td>${item.last_desc}</td>
               <td align="center">
		    <shiro:hasPermission name="DEFAULT">
                 <c:if test="${item.status ne 'DEALED'}"><a href="javascript:pro_text('${item.id}','${item.order_no}');">处理登记</a></c:if>
             </shiro:hasPermission>
		   <%--  <shiro:hasPermission name="DEFAULT">
                  <c:if test="${item.status ne 'DEALED'}">  |
                   <a href="javascript:deal('${item.order_no}')">完成</a></c:if>
             </shiro:hasPermission> --%>
                </td>
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