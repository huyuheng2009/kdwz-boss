<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function(){
		trHover('t2');
		 $('#add').click(function(){
			 $.dialog({lock: true,title:'新增物料',drag: false,width:800,height:400,resize: false,max: false,content: 'url:${ctx}/matter/enter_add?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});



</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/matter/enter" method="get">
    	<ul>
            <li>物料品名：<u:select stype="MATTER_NAME" sname="matterId" value="${params['matterId']}"/></li>
            <li>经手人：<input type="text" value="${params['brokerage']}" name="brokerage"></input></li>
            <li>供货商：<input type="text" value="${params['supplier']}" name="supplier"></input></li>
            <li>单价：<input type="text" value="${params['matterPrice']}" name="matterPrice"></input></li>
            <li>登记人：<input type="text" value="${params['operator']}" name="operator"></input></li>
            <li>总金额：<input type="text" value="${params['macount']}" name="macount"></input></li>
            <li>单号：<input type="text" value="${params['planeNo']}" name="planeNo"></input></li>       
			<li><span>入库时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
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
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
  <shiro:hasPermission name="DEFAULT">
 <div class="search_new">  <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /></div>
	</shiro:hasPermission> 
	 </div>   <!-- tableble_search end  -->   
  
</div>
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center">序号</th>
            <th align="center">物料编号</th>
            <th align="center">物料品名</th>
            <th align="center">入库时间</th>
            <th align="center">供货商</th>
            <th align="center">经手人</th>
            <th align="center">开始单号</th>
            <th align="center">结束单号</th>
            <th align="center">数量</th>
            <th align="center">单价</th>
            <th align="center">总金额</th>
            <th align="center">所属网点</th>
            <th align="center">登记人</th>
            <th align="center">备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.matter_no}</td>
                <td>${item.matter_name}</td>
                <td><fmt:formatDate value="${item.ware_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.supplier}</td>
                <td>${item.brokerage}</td>
                <td>${item.bplane_no}</td>
                <td>${item.eplane_no}</td>
                <td>${item.mcount}</td>
                <td>${item.matter_price}</td>
                <td>${item.macount}</td>
                <td>${item.substation_name}</td>
                <td>${item.operator}</td>
                <td>${item.note}</td>

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