<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function(){
		trHover('t2');
		
		$('#exportData').click(function(){
    		var action= $("form:first").attr("action");
            $("form:first input[name=serviceName]").val('exportCourier');
    	   $("form:first").attr("action","${ctx}/lgc/export").submit();
    	   $("form:first").attr("action",action);
    	});
		
	});
</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
<form:form id="trans" action="${ctx}/lgc/ccount" method="post">
  <input type="hidden" name="serviceName" value=""/>
    	<ul>
    		<li>快递分站：<select id="substationNo" name="substationNo" style="width: 150px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select></li>
            <li>快递员用户名：<input type="text" value="${params['userName']}" name="userName"></input></li>
            <li>快递员姓名：<input type="text" value="${params['realName']}" name="realName"></input></li>
            <li><span>寄件日期：</span>
			 	<input onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
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
  <div class="search_new"> <input id="reload_btn" class="button input_text  big gray_flat" type="submit" value="刷新" /> </div>
 <shiro:hasPermission name="DEFAULT">
  <div class="search_new"><input id="exportData" class="button input_text  big gray_flat" type="submit" value="导出统计数据" /> </div>
	</shiro:hasPermission> 
   </div>   <!-- tableble_search end  -->   
</div>
<div class="tbdata">
 
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="3%" align="center">序号</th>
            <th width="10%" align="center">分站名称</th>
            <th width="10%" align="center">快递用户名</th>
            <th width="7%" align="center">快递员姓名</th>
            <th width="5%" align="center">收件个数</th>
            <th width="7%" align="center">收件金额</th>
            <th width="5%" align="center">派件个数</th>
            <th width="7%" align="center">派件金额</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courierList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.substation_name}</td>
                <td>${item.user_name}</td>
                <td>${item.real_name}</td>
                <td>${item.takeCount }</td>
                <td>${item.tnpay }</td>
                <td>${item.sendCount }</td>
                <td>${item.snpay }</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${courierList.pages}"
                     current="${courierList.pageNum}" sum="${courierList.total}"/>
</div>
</body>

</html>