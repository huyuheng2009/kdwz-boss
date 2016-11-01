<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
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
color: #878787;
border-bottom: 1px solid #d8d8d8 !important;
background-color: #fbfbfb !important;
}

</style>
    <script type="text/javascript">
    $(function(){
    	
    	// var curSubstationNo =  $('select[name=curSubstationNo]').val() ;  
    	// $(".courierNo_"+curSubstationNo).css("display","block");
    	
   	 $('#submit').click(function(){
   		 var api = frameElement.api, W = api.opener;
   		 
   		 
   		 var proReason =  $('select[name=proReason]').val() ; 
   		 var descb = $('input[name=descb]').val();
   		 if(proReason.length<1){
  			 alert('请选择问题原因');
  			 return false;
  		 }
   		
   		if(descb.length<1){
  			 alert('请输入详细原因');
  			 return false;
  		 }
   		
   		
   		 var dealText = $('#dealText').val();
   		 if(dealText.length<2){
   			 alert('请输入处理意见');
   			 return false;
   		 }
   		 var curSubstationNo =  $('select[name=curSubstationNo]').val() ;   
   		 var dealStatus =  $('select[name=dealStatus]').val() ;   
   		 var courierNo = $('select[name=courierNo]').val() ;  
   		  
   		if(curSubstationNo.length<1){
  			 alert('请选择接收网点');
  			 return false;
  		 }
   		 
   		if(dealStatus.length<1){
 			 alert('请选择处理状态');
 			 return false;
 		 }
   		
   		var orderNo = $('input[name=orderNo]').val();
   		
   		 $.ajax({
   			 type: "post",//使用get方法访问后台
   	            dataType: "text",//返回json格式的数据
   	            url: "${ctx }/porder/pro_text_save",//要访问的后台地址
   	            data: {'orderNo':orderNo,'proReason':proReason,'descb':descb,'dealText':dealText,'curSubstationNo':curSubstationNo,'dealStatus':dealStatus,'courierNo':courierNo,'ostatus':'2'},//要发送的数据
   	            success: function(msg){//msg为返回的数据，在这里做数据绑定
   	            	if(msg==1){
   	            		alert('操作成功');
   	            		api.reload();
						api.close();
   	            	}else{
   	            		alert(msg);
   	            	}
   	            }
   		 });
   	 });
   	
   }); 
        
    function chl1(sno){
        $('#courierNo').prop('selectedIndex', 0);
        var obj = $('#courierNo')[0] ;
        for (var i = obj.options.length - 1; i > 0; i--) { 
            obj.options.remove(i); //从后往前删除 
         } 
        if(sno.length>0){
      	  $.ajax({
	 			 type: "post",//使用get方法访问后台
	 	            dataType: "json",//返回json格式的数据
	 	           url: "/clistBySno",//要访问的后台地址
	 	            data: {'sno':sno},//要发送的数据
	 	            success: function(data){//msg为返回的数据，在这里做数据绑定
	 	            	if(data){
	 	            		  //var ops = eval("("+data.clist+")");
	 	            		  var ops = data.clist ;
	 	            		 	$.each(ops, function(i, item) {
	 	            		 		  var op = ops[i]; 
	            			            var oOption = document.createElement("OPTION"); //创建一个OPTION节点 
	            			            obj.options.add(oOption);    //将节点加入city选项中 
	            			            oOption.innerText = op.real_name;    //设置选择展示的信息 
	            			            oOption.value = op.courier_no;         //设置选项的值 
	 	                         }); 
	 	            	}
	 	            }
	 		 });
        }
       
  
        
        
        
       // $(".courierNo").css("display","none");
    	//$(".courierNo_"+sno).css("display","block");
} ;  

    </script>
</head>
<body>
	<div class="content" style="padding-bottom: 30px;">
		 	<div class="item" >
    <form:form id="trans" action="${ctx}/porder/pro_text_save" method="get">
        <ul  style="margin:0px 20px;padding-top: 30px;width: 45%;"> 
            <input type="hidden" name="orderNo" value="${params['orderNo']}" />
            <li style="width: 100%;margin-bottom: 20px;">&#12288;运单号：<input type="text" value="${pOrder.lgcOrderNo}" disabled="disabled" name="gcOrderNo" style="width: 380px"></input></li>	
            <li style="width: 100%;margin-bottom: 20px;">问题原因： <u:select    id="proReason" sname="proReason" stype="PRO_REASON" value="${pOrder.proReason}" style="width: 380px"/></li>	
            <li style="width: 100%;margin-bottom: 20px;">详细原因：<input   type="text" value="${pOrder.descb}" name="descb" style="width: 380px"></input></li>	
			<li style="width: 100%;margin-bottom: 20px;"><span style="vertical-align: top;">处理登记：</span><textarea style="font-size:10px;width: 380px;min-width: 380px;min-height: 70px;max-width: 380px;max-height: 70px;"  cols="18" rows="2"  maxlength="100" name="dealText" id="dealText"></textarea></li>
			<li style="width: 50px;margin-bottom: 20px;">详&#12288;情： 
			</li><textarea disabled="disabled" style="font-size:10px;margin-bottom: 20px;margin-top:0px;;width: 380px;min-width: 380px;min-height: 150px;max-width: 380px;max-height: 150px;"  cols="18" rows="4"   name="lgcOrderNo" class="order_area" >
<c:forEach items="${dList}" var="item" varStatus="status" ><c:if test="${status.count>1}">
&nbsp;
&nbsp;</c:if><c:if test="${status.count<2}">&nbsp;</c:if>处理${status.count}：${item.deal_text}
&#12288;&#12288;接收网点：<u:dict name="S" key="${item.substation_no}" />&#12288;&#12288;接收快递员：<u:dict name="C" key="${item.couier_no}" />
&#12288;&#12288;原因：${item.reson}&#12288;&#12288;详细原因：${item.reson_text}
&#12288;&#12288;处理人：${item.dealer} &#12288;&#12288;时间：<fmt:formatDate value="${item.create_time}" type="both"/>
 </c:forEach>
                    </textarea>
			<li style="width: 100%;margin-bottom: 20px;">接收网点：<select  onchange="chl1(this.value);" id="curSubstationNo" name="curSubstationNo" style="width: 380px">
							  <option value="">请选择</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${pOrder.curSubstationNo eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select></li>
			<li style="width: 100%;margin-bottom: 20px;">&#12288;快递员：<select id="courierNo" name="courierNo"  style="width: 380px"    >
			                <option value="">请选择</option>
							<%-- <c:forEach items="${clist}" var="item" varStatus="status">
								<option value="${item.courier_no }"  class="courierNo courierNo_${item.substation_no}" style="display: none;" >${item.real_name }</option>
							</c:forEach> --%>
					</select></li>
			</li>
			<li style="width: 100%;margin-bottom: 20px;">处理状态： <u:select id="dealStatus" sname="dealStatus" stype="DEAL_STATUS" value="${pOrder.dealStatus}"/></li>
			
             <input type="hidden" name="layout" value="no"/>
            <c:if test="${oMap.status!=3}">
                <li><input class="button input_text  medium blue_flat" type="button" id="submit" value="处理"/>
               </li>
            </c:if> 
              <c:if test="${oMap.status==3}">
                <li>当前问题件已经处理完成 </li>
            </c:if> 
        </ul>
        
         <table width="53%" cellspacing="0" class="t2" id="t2" >
        <thead>
        <tr>
           <th  align="center" >物品类型</th>
            <th  align="center" >取件员</th>
            <th  align="center" >派件员</th>
            <th  align="center" >收件人</th>
            <th  align="center" >收件人手机号</th>
            <th  align="center" >运费</th>
            <th  align="center" >代收款</th>
            <th  align="center" >付款方</th>
        </tr>
        </thead>
        <tbody>
            <tr class="a1">
             <td>${oMap.item_Status}</td>
               <td><u:dict name="C" key="${oMap.take_courier_no}" /></td>
                <td><u:dict name="C" key="${oMap.send_courier_no}" /></td>
               <td>${oMap.send_name}</td>
               <td>${oMap.send_phone}</td>
               <td>${oMap.freight}</td>
               <td>${oMap.good_price}</td>
                <td><c:if test="${oMap.freight_type==1}">寄方付</c:if>
                <c:if test="${oMap.freight_type==2}">收方付</c:if></td>
            </tr>
             <tr><td colspan="8">
              <table  id="queryResult2" class="result-info2" cellspacing="0">
		<tbody>
		      <tr>
				<td class="row1">快递公司：${lgcName}</td>
				<td >&nbsp;</td>
				<td>物流单号：${lgcOrderNo}</td>
			   </tr>
			<c:forEach items="${trackList}" var="item" varStatus="status" >
			<c:if test="${status.count eq f:length(trackList)}">
              <tr class="last">
				<td class="row1">${item.time}</td>
				<td class="status ${ischeck eq 0 ? 'status-onway' : 'status-check'}">&nbsp;</td>
				<td>${item.context}</td>
			   </tr>
           </c:if>
			<c:if test="${status.count ne f:length(trackList)}">
                <tr>
				<td class="row1">${item.time}</td>
				<td class="status ${status.count eq 1 ? 'status-first' : ''}">&nbsp;</td>
				<td>${item.context}</td>
			   </tr>
            </c:if>
			 </c:forEach>
			
		<!-- 	<tr class="last">
				<td class="row1">2015-01-25 12:55:44</td>
				<td class="status status-onway">&nbsp;</td>
				<td>已签收,签收人是本人</td>
			</tr> -->
		</tbody>
	</table>  
		<table id="queryResult2" class="result-info2" cellspacing="0">
		<tbody>
		<c:if test="${f:length(oMap.take_plane)>1}">
		      <tr>
				<td >
                 <img src="${oMap.take_plane}" alt="" width="100%" height="250"/>
                 </td>
			   </tr>
		 </c:if>
		<c:if test="${f:length(oMap.send_plane)>1}">
			   <tr>
				<td>
                 <img src="${oMap.send_plane}" alt="" width="100%" height="250"/>
                 </td>
			   </tr>
	  </c:if>		   
		</tbody>
	</table>
            </td> </tr>
        </tbody>
    </table>
        
     
        
        
    </form:form>
	</div>
	</div>
</body>

</html>