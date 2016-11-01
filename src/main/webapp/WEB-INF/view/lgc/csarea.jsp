<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function(){
		trHover('t2');


		 $('#reload_btn').click(function(){
			 location.reload();
		});


	});


	function edit(id){
		 $.dialog({lock: true,title:'编辑收派范围',drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/lgc/csedit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	


</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
<form:form id="trans" action="${ctx}/lgc/csarea" method="get">
    	<ul>
    		<li>快递分站：<select id="substationNo" name="substationNo" style="width: 150px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select></li>
            <li>快递员：<input type="text" value="${params['realName']}" name="realName"></input></li>
            <li>收派范围：<input type="text" value="${params['sarea']}" name="sarea"></input></li>
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
  <div class="search_new"><input id="reload_btn" class="button input_text  big gray_flat" type="submit" value="刷新" /> </div>
   </div>   <!-- tableble_search end  -->  
  
</div>
<div class="tbdata">
 
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center">序号</th>
              <th align="center">网点名称</th>
            <th  align="center">快递员姓名</th>
            <th  align="center">电话</th>
            <th width="50%" align="center" >收派范围</th>
            <th  align="center">注册时间</th>
            <th  align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courierList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''}   <c:out value="${item.status eq '0'?'deled':' '}"></c:out>">
                <td align="center">${status.count}</td>
                 <td>${item.substation_name}</td>
                <td>${item.real_name}</td>
                 <td>${item.phone }</td>
               <td style="line-height: 26px;padding: 5px;">${item.sarea}</td>
                <td><fmt:formatDate value="${item.regist_time}" type="both"/></td>
                <td align="center">
                   	<shiro:hasPermission name="DEFAULT">
                     <a href="javascript:edit('${item.id}');">收派范围</a>
                     </shiro:hasPermission> 
                </td>
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