<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    var api = frameElement.api, W = api.opener;
	 api.lock();
        function asign(orderNo,courierNo,substationNo,cName,cPhone,sub_station_name,take_courier_name){
        	  var tips  = "确定分配给"+cName+"吗？";
       	   if(sub_station_name.length>1){
       		
       		  if(take_courier_name.length>1){
       			  tips = "该订单已经被具体分配给快递员："+take_courier_name+",确定重新分配给"+cName+"吗？" ;
       		  }else{
       			  tips = "该订单已经被分配给分站："+sub_station_name+",确定重新分配给"+cName+"吗？" ;
       		  }
       	   }
        if(confirm(tips)){
   		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx}/order/asign_save",//要访问的后台地址
	            data: {'orderNo':orderNo,'courierNo':courierNo,'substationNo':substationNo,'cName':cName,'cPhone':cPhone},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('分配失败');
	            	}
	            	if(msg==1){
	            		alert('分配成功');
	            		//api.reload();
						api.close();
	            	}
	            }
		 });
       }	
    }
        
  
    </script>
</head>
<body>
<div class="search">
    <form:form id="trans" action="${ctx}/order/asign_s" method="get">
     <input type="hidden" name="orderNo" value="${params['orderNo']}"/>
        <ul> 
             
             <li>快递公司：<select id="lgcNo" name="lgcNo" style="width: 110px">
							<option value="">--全部--</option>
							<c:forEach items="${lgcs.list}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${params['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
					</select></li>
            <li>分站名称/地址：<input type="text" value="${params['substationName']}" name="substationName"></input></li>
             <input type="hidden" name="layout" value="no"/>
            <li><input  style="width:120px;"  class="button input_text  medium blue_flat"
                       type="submit" value="获取分站列表"/>
            </li>
           <!--  <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li> -->
        </ul>
    </form:form>
    <div class="clear"></div>
</div>

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="5%" align="center">序号</th>
            <th width="27%" align="center">分站名称</th>
            <th width="15%" align="center">快递员名称</th>
            <th width="13%" align="center">联系电话</th>
            <th width="25%" align="center">身份证号</th>
            <th width="15%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.substation_name}</td>
                <td>${item.real_name}</td>
                <td>${item.phone}</td>
                <td>${item.id_card}</td>
                <td align="center">
                   <c:if test="${item.courier_no eq take_courier_no}">已分配</c:if>
                    <c:if test="${item.courier_no ne take_courier_no}">
                <a href="javascript:asign('${params['orderNo']}','${item.courier_no}','${item.substation_no}','${item.real_name}','${item.phone}','${sub_station_name}','${take_courier_name}');">分配给此人</a></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${cList.pages}"
                     current="${cList.pageNum}" sum="${cList.total}"/>
</div>
</body>

</html>