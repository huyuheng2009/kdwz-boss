<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    
        $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
       		 location.reload();
       	});
           
        
         $('#exportData').click(function(){
        	 var action = $("form:first").attr("action");
             $("form:first input[name=serviceName]").val('forOrderExport');
             $("form:first").attr("action", "${ctx}/export/service").submit();
             $("form:first").attr("action", action);
       	}); 
         
         $('#fiscan_btn').click(function(){
        	 $.dialog({lock: true,title:'转入扫描',drag: true,width:700,height:260,resize: false,max: false,content: 'url:${ctx}/scan/fiscan?layout=no',close: function(){
         	}});
       	}); 
         
         $('#foscan_btn').click(function(){
        	 $.dialog({lock: true,title:'转出扫描',drag: true,width:550,height:260,resize: false,max: false,content: 'url:${ctx}/scan/foscan?layout=no',close: function(){
         	}});
       	}); 
         
         
     });
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/scan/exchange" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
          <%--   <li>转件类型：<select name="forwardType" style="width: 110px">
                <option value="">全部</option>
                <option value="IN" ${params['forwardType'] eq 'IN'?'selected':''}>转入</option>
                <option value="OUT" ${params['forwardType'] eq 'OUT'?'selected':''}>转出</option>
             </select></li> 
            <li>快递公司：<input type="text" value="${params['ioName']}" placeholder="快递公司" name="ioName"></input></li>
            <li>运单号：<input type="text" value="${params['ioLgcOrderNo']}" placeholder="物流单号" name=ioLgcOrderNo></input></li> --%>
              <li><span>扫描时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d\'}'})" type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
   <div class="operator">
	    <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>   </div>
	    <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
		    <shiro:hasPermission name="EXCHANGE_INADD1">
	    <div class="search_new"> <input style="width: 100px;"class="button input_text  big gray_flat" type="submit" value="新增转出" id="foscan_btn"/>   </div>
	    </shiro:hasPermission>
		    <shiro:hasPermission name="EXCHANGE_OUTADD1">
	    <div class="search_new"> <input style="width: 100px;" class="button input_text  big gray_flat" type="submit" value="新增转入" id="fiscan_btn"/>   </div>
	    </shiro:hasPermission>
	    </div>
   </div>   <!-- tableble_search end  -->   
  
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >序号</th>
            <th  align="center" >运单号</th>
            <th  align="center" >转出公司</th>
            <th  align="center" >转出单号</th>
            <th  align="center" >扫描员</th>
            <th  align="center" >扫描时间</th>
            <th  align="center" >寄件时间</th>
            <th  align="center" >签收网点</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >运费</th>
             <th  align="center" >附加费</th>
              <th  align="center" >代收货款</th>
               <th  align="center" >件数</th>
                <th  align="center" >重量</th>
                 <th  align="center" >收件员</th>
                  <th  align="center" >签收人</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
               <td>${item.lgc_order_no}</td>
               <td>${item.io_name}</td>
               <td>${item.io_lgc_order_no}</td>
               <td>${item.scan_name}</td>
               <td>${item.forward_time}</td>
                <td>${item.create_time}</td>
               <td><u:dict name="S" key="${item.send_substation_no}" /></td>
               <td><c:if test="${item.freight_type eq 2 }">收方付</c:if><c:if test="${item.freight_type ne 2 }">寄方付</c:if></td> 
                <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td> 
                <td>${item.freight}</td>
                <td>${item.vpay}</td>
                <td>${item.good_price}</td>
                <td>${item.item_count}</td>
                <td>${item.item_weight}</td>
               <td><u:dict name="C" key="${item.take_courier_no}" /></td>
                <td>${item.sign_name}</td>
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