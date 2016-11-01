<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:820,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }
        function pro_order(orderNo,lgcOrderNo){
        	$.dialog({lock: true,title:'问题件',drag: true,width:450,height:270,resize: false,max: false,content: 'url:${ctx}/porder/add?lgcOrderNo='+lgcOrderNo+'&orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }    
        
        
    
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
       		 location.reload();
       	});
           
         
         $('#revscan_btn').click(function(){
        	 $.dialog({lock: true,title:'到站扫描',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/scan/revscan?layout=no',close: function(){
         	}});
       	}); 
         
            
        });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/scan/slist" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>运单号：<input type="text" value="${params['lgcOrderNo']}" placeholder="物流单号" name="lgcOrderNo"></input></li>
             <li>当前站点：<select id="substationNo" name="substationNo" style="width: 220px">
					 <option value="">请选择</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option   value="${item.substation_no }" id="${item.substation_name }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
					</select>
			</li>		
           <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d\'}'})"   type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
			 
			<%--  <li>快件状态：<select name="orderStatus" style="width: 110px">
                <option value="INIT" ${params['orderStatus'] eq 'INIT'?'selected':''}>待收件</option>
                <option value="REV" ${params['orderStatus'] eq 'REV'?'selected':''}>未发送</option>
                <option value="SEND" ${params['orderStatus'] eq 'SEND'?'selected':''}>已完成</option>
             </select></li> --%>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
            <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li>
        </ul>
    </form:form>
  </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
   <div class="operator">
	   <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/></div> 
	   
	  
		    <shiro:hasPermission name="DEFAULT">
	    <div class="search_new"> <input style="width: 100px;" class="button input_text  big gray_flat" type="submit" value="到站扫描" id="revscan_btn"/> </div>
	     </shiro:hasPermission>
	    </div>
   </div>   <!-- tableble_search end  -->   
  
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="15" align="center" >序号</th>
            <th width="50" align="center" >运单号</th>
            <th width="30" align="center" >上一站</th>
            <th width="50" align="center" >上一站名称</th>
            <th width="30" align="center" >当前站</th>
            <th width="50" align="center" >当前站名称</th>
            <th width="80" align="center" >寄件时间</th>
            <th width="20" align="center" >运单状态</th>
            <th width="50" align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
               <td>${item.lgc_order_no}</td>
               <td>${item.cur_no}</td>
               <td><u:dict name="${item.cur_type}" key="${item.cur_no}" /></td>
               <td>${item.next_no}</td>
               <td><c:if test="${params['orderStatus'] eq 'INIT' }"><span  style="color: red;"></c:if><u:dict name="${item.next_type}" key="${item.next_no}" /></span></td>
               <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                <td>
                    <c:if test="${params['orderStatus'] eq 'INIT' }"><span style="color:red;">待收件<span></c:if>
                <td align="center">
                   <a href="javascript:show('${item.id}','${item.order_no}');">运单详情</a>
                      <shiro:hasPermission name="DEFAULT">
                   |
                   <a href="javascript:pro_order('${item.order_no}','${item.lgc_order_no}');">问题件</a>
                    </shiro:hasPermission>
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