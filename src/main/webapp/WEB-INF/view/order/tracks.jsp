<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
.note_c{width: 40%;margin-left: 2%;float: left;border: 1px;border-style: solid;border-color: grey;min-height: 300px;}
.note_c ul{}
.note_c ul li{width:40%;  float: left;padding: 2px 1px ;}


* {
-webkit-text-size-adjust: none;
margin: 0 auto ;
}

.result-info2 {
width: 100%;
border: 1px solid #90bfff;
}
table {
border-collapse: collapse;
border-spacing: 0;

}

tr {
display: table-row;
vertical-align: inherit;
border-color: inherit;
}

.result-info2 .row1 {
width: 140px;
text-align: right;
}
.result-info2 .status {
width: 30px;
background: url("${ctx}/themes/default/images/ico_status.gif") -50px center no-repeat #fbfbfb;
}

.result-info2 .status-first {
background: url("${ctx}/themes/default/images/ico_status.gif") 0px center no-repeat #fbfbfb;
}

.result-info2 .last td {
color: #FF8c00;
border-bottom: none;
background-color: #ffffff !important;
}

.result-info2 .status-onway {
background: url("${ctx}/themes/default/images/ico_status.gif") -100px center no-repeat #fbfbfb;
}

.result-info2 .status-check {
background: url("${ctx}/themes/default/images/ico_status.gif") -150px center no-repeat #fbfbfb;
}
.result-info2 td {
padding: 10px;
color: black;
border-bottom: 1px solid #d8d8d8 !important;
background-color: #fbfbfb !important;
}

</style>
    <script type="text/javascript">
        function show(orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:470,resize: false,max: false,content: 'url:${ctx}/order/detail?id=200&orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }
        
        function note(id,lgcOrderNo){
        	$.dialog({lock: true,title:'备注',drag: true,width:600,height:700,resize: false,max: false,content: 'url:${ctx}/order_ext/note?id='+id+'&lgcOrderNo='+lgcOrderNo+'&layout=no',close: function(){
        	 }});
        }
        
        function pro_order(orderNo,lgcOrderNo){
        	$.dialog({lock: true,title:'问题件',drag: true,width:450,height:270,resize: false,max: false,content: 'url:${ctx}/porder/add?lgcOrderNo='+lgcOrderNo+'&orderNo='+orderNo+'&layout=no',close: function(){
        	}});
        }    
        
        function track(orderNo){
        	$.dialog({lock: true,title:'物流动态',drag: true,width:1000,height:550,resize: false,max: false,content: 'url:${ctx}/order/track?orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
       
    
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
                //location.reload()
           	 $("form:first").submit();
       	});
           
         
         $("#restBtn").click(function(){
        	// document.getElementById("trans").reset()
        	 $("input[type=radio]:eq(0)").prop("checked",true);
        	 $("#lgcOrderNo").val('');
        	 $("#dateBegin").val('');
        	 $("#dateEnd").val('');
        	 $("#phone").val('');
         });
            
        });

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/order/tracks" method="post">
     <input type="hidden" name="serviceName" value=""/>
     <div style="width: 70px;float: left;margin: 15px 0 0 0;">
		  <label><input type="radio" name="noType" value="1" <c:out value="${params.noType eq '1'?'checked':'' }"/> >&nbsp;运单号码</input></label>
		  <label style="margin: 8px 0;display: block;"><input type="radio" name="noType" value="2" <c:out value="${params.noType eq '2'?'checked':'' }"/>>&nbsp;回单号码</input></label>
		  <label><input type="radio" name="noType" value="3" <c:out value="${params.noType eq '3'?'checked':'' }"/>>&nbsp;转单号码</input></label>
    </div>		    
        <ul>
			<textarea  cols="18" rows="4"   maxlength="1400" placeholder="请输入运单号" name="lgcOrderNo" id="lgcOrderNo" class="order_area" >${params['lgcOrderNo']}</textarea> 
		    
		   <li><span>寄件时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li>
			 
			 <li>联系电话：<input type="text" value="${params['phone']}" placeholder="" name="phone" id="phone"></input></li> 
			   
			<%--     <li><span>派件时间：</span>
			 	<input id="dateBegin1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd1\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="sendTimeBegin" value="${params['sendTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd1"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="sendTimeEnd" value="${params['sendTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li> --%>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
            <li><input class="button input_text  medium blue_flat" type="button" id="restBtn" value="重置"/></li>
        </ul>
    </form:form>
     </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
<!--   <div class="tableble_search">
   <div class="operator">
	   <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>
	    </div>
   </div>   tableble_search end     -->
</div>


<div class="tbdata">
<c:if test="${empty list.list}">
  <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
          <tr>
            <th  align="center" >运单号</th>
            <th  align="center" >转单号</th>
            <th  align="center" >寄件站 </th>
            <th  align="center" >取件员</th>
            <th  align="center" >派件站</th>
            <th  align="center" >派件员</th>
            <th  align="center" >寄件电话</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >来源</th>
            <th  align="center" >寄件时间</th>
            <th  align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        
            <tr>
               <td colspan="12">无记录</td>
            </tr>
         
        </tbody>
    </table>
</c:if>

     <c:forEach items="${list.list}" var="item" varStatus="status"> 	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center" >运单号</th>
            <th  align="center" >转单号</th>
            <th  align="center" >寄件站 </th>
            <th  align="center" >取件员</th>
            <th  align="center" >派件站</th>
            <th  align="center" >派件员</th>
            <th  align="center" >寄件电话</th>
            <th  align="center" >付款人</th>
            <th  align="center" >付款方式</th>
            <th  align="center" >来源</th>
            <th  align="center" >寄件时间</th>
           <!--  <th  align="center" >代收货款</th>
           <th  align="center" >签收时间</th>
            <th  align="center" >签收人</th>
            <th align="center" >是否问题件</th> -->
            <th  align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        
            <tr class="${item.count % 2 == 0 ? 'a1' : ''}">
               <td><a href="javascript:track('${item.order_no}');">${item.lgc_order_no}</a></td>
                <td>${item.for_no}</td>
               <td><u:dict name="S" key="${item.sub_station_no}" /></td>
               <td><u:dict name="C" key="${item.take_courier_no}" /></td>
                 <td><u:dict name="S" key="${item.send_substation_no}" /></td>
               <td><u:dict name="C" key="${item.send_courier_no}" /></td>
                <td>${item.send_phone}</td>
               <td><u:dict name="FREIGHT_TYPE" key="${item.freight_type}" /></td>
               <td><u:dict name="PAY_TYPE" key="${item.pay_type}" /></td>
              <%--  <td><c:if test="${item.cod==1 }">是</c:if></td> 
                  <td><fmt:formatDate value="${item.send_order_time}" type="both"/></td>  
                    <td>${item.sign_name}</td>
                    <td><c:if test="${item.pro_order eq 'Y'}">是</c:if><c:if test="${item.pro_order ne 'Y'}">否</c:if></td>
                  --%>
                 <td><u:dict name="ORDER_SRC" key="${item.source}" /></td> 
               <td><fmt:formatDate value="${item.take_order_time}" type="both"/></td>
                 <td align="center">
                   <a href="javascript:show('${item.order_no}');">详情</a>
                        |
                      <a href="javascript:note('${item.id}','${item.lgc_order_no}');">备注</a>
		    <shiro:hasPermission name="DEFAULT">
                     |
                      <c:if test="${item.pro_order ne 'Y'&&item.s!=3 }"> <a href="javascript:pro_order('${item.order_no}','${item.lgc_order_no}');">问题件</a></c:if>
                       <c:if test="${item.pro_order eq 'Y'}"> 问题件</c:if>
                       </shiro:hasPermission>
                </td>
            </tr>
         
        </tbody>
    </table>
    
    	<table id="queryResult2" class="result-info2" cellspacing="0" style="width:100%;">
		<tbody>
			<c:if test="${f:length(item.take_plane)>1 or f:length(item.send_plane)>1}">
		      <tr>
				<td style="width:50%">
		       <c:if test="${f:length(item.take_plane)>1}"> 
				 
                 <img src="${item.take_plane}" alt="" width="100%" height="320"/>
                 </c:if> 
                 </td>
                 
				<td style="width:50%">
                   <c:if test="${f:length(item.send_plane)>1}"> 
                 <img src="${item.send_plane}" alt="" width="100%" height="320"/>
                 </c:if> 
                 </td>
			</tr></c:if> 
		</tbody>
	</table>
 <div style="width: 100%; overflow:auto;display: inline-block;padding: 30px 0; background: #f6f6f6;"> 
   <div style="width: 55%;float: left;"> 
    <table id="queryResult2" class="result-info2" cellspacing="0" style="width:100%;">
		<tbody>
			<c:forEach items="${item.trackList}" var="item1" varStatus="status1" >
			<c:if test="${status1.count eq f:length(item.trackList)}">
              <tr class="last">
				<td class="row1"><fmt:formatDate value="${item1.time}" type="both"/></td>
				<td>${item1.context}</td>
			   </tr>
           </c:if>
			<c:if test="${status1.count ne f:length(item.trackList)}">
                <tr>
				<td class="row1"><fmt:formatDate value="${item1.time}" type="both"/></td>
				<td>${item1.context}</td>
			   </tr>
            </c:if>
			 </c:forEach>
			 
			<c:if test="${item.iffor eq '1'}">
                <tr>
				<td class="row1">转件信息</td>
				<td>${item.forname}</td>
			   </tr>
		<c:if test="${item.kdyb eq '1'}">	   
			   	<c:forEach items="${item.kdybMap.data}" var="item2" varStatus="status2" >
			<c:if test="${status2.count eq '1'}">
              <tr class="last">
				<td class="row1"><%-- <fmt:formatDate value="${item2.time}" type="both"/> --%>${item2.time}</td>
				<td>${item2.context}</td>
			   </tr>
           </c:if>
			<c:if test="${status2.count ne '1'}">
                <tr>
				<td class="row1">${item2.time}</td>
				<td>${item2.context}</td>
			   </tr>
            </c:if>
			 </c:forEach>
	 </c:if>		   
 </c:if>
			 
			 
		</tbody>
	</table>
 </div>	
 	<div class="note_c" style="">
	<c:forEach items="${item.noteList}" var="item2" varStatus="status2">
     <ul>
       <li  style="width:100%;">备注：${item2.note}</li>
       <li>备注人：${item2.operator}</li>
       <li class="tt"  style="width:58%;">时间：<fmt:formatDate value="${item2.create_time}" type="both"/></li>
     </ul>
     <div style="float: left;width: 100%;height: 10px;"></div>
   </c:forEach>

    </div>   
  </div>  
   </c:forEach> 
  
</div>
</body>

</html>