<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
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



function del(id,name){
	 if(confirm('确定删除 ['+name+'] 群组吗？')){
		 $.ajax({url:'gdel',data:{'id':id},success:function(msg){
			 if(msg==1){
				 alert('删除成功');
				 location.reload();
			 }else{
				 alert(msg);
			 }
		 }});
	 }
}
</script>

</head>
<body>
<div class="search">
  <div class="tableble_search">
        <div class=" search_cont">
		<form:form id="f"   method="get">
			<ul>
			<li>角色名称 <input type="text" value="${params['groupName']}" name="groupName"></input></li>
			<li><input class="button input_text  medium blue_flat"
					type="submit" value="查询" />
				</li>
                <li><input class="button input_text  medium blue_flat"
                           type="reset" value="重置" />
			</ul>
		</form:form>
				 </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
  <div class="operator">
	<div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/></div>
		 	<shiro:hasPermission name="ADMIN">
	    	<div class="search_new"> <input  onclick="javascript:location.href='groupauth'" class="button input_text  big gray_flat" type="submit" value="新增" /></div>
	    </shiro:hasPermission>
	    </div>
 </div>    <!-- tableble_search end  -->		    
  
  
	</div>
	<div class="tbdata">
	 
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="center">角色名称</th>
					<th align="center">角色介绍</th>
					<th align="center">创建人</th>
					<th align="center">创建时间</th>
					<th align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.group_name }</td>
						<td>${item.group_desc}</td>
						<td>${item.creator}</td>
						<td><fmt:formatDate value="${item.create_time}"  pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td  align="center"> 
							<shiro:hasPermission name="ADMIN">
							<a href="groupauth?id=${item.id}">修改</a>
							|
						</shiro:hasPermission>
						<shiro:hasPermission name="ADMIN">
							<a href="javascript:del('${item.id}','${item.group_name}');">删除</a>
						</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>