<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
$(function(){
	trHover('t2');
	 $('#add').click(function(){
		 $.dialog({lock: true,title:'新增顶级区域',drag: false,width:500,height:150,resize: false,max: false,content: 'url:${ctx}/dict/ashow?level=1&parentId=0&layout=no',close: function(){
			// location.reload();
	  	 }});
	 });
	 $('#reload_btn').click(function(){
		 location.reload();
	});
});

function edit(id,name,parentId){
	 $.dialog({lock: true,title:'编辑地区',drag: false,width:500,height:150,resize: false,max: false,content: 'url:${ctx}/dict/ashow?id='+id+'&name='+name+'&parentId='+parentId+'&layout=no',close: function(){
		// location.reload();
  	 }});
}

function addson(level,parentId){
	 $.dialog({lock: true,title:'新增下级',drag: false,width:500,height:150,resize: false,max: false,content: 'url:${ctx}/dict/ashow?level='+level+'&parentId='+parentId+'&layout=no',close: function(){
		// location.reload();
 	 }});
}


function del(id,name){
	 if(confirm('确定删除 ['+name+'] 区域吗？')){
		 $.ajax({url:'adel',data:{'id':id},success:function(msg){
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
		<form:form id="f"   method="post">
			<ul>
			<li>区域名称 <input type="text" value="${params['name']}" name="name"></input></li>
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
	 <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
	 <shiro:hasPermission name="DEFAULT">
	    <div class="search_new"><input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /> </div>
	  </shiro:hasPermission>
	    </div>
   </div>   <!-- tableble_search end  -->  
	</div>
	<div class="tbdata">
	
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="4%" align="center">序号</th>
					<th width="30%" align="center">区域名称</th>
					<th width="10%" align="center">等级</th>
					<th width="30%" align="center">上级区域</th>
					<th width="26%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:if test="${f:length(areaList.list)<1}">
			  <tr><td  colspan="5"><a href="${ctx}/dict/area?parentId=${ppId}">返回上级</a></td></tr></c:if>
				<c:forEach items="${areaList.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<c:if test="${item.level>2}"><td>${item.name}</td></c:if>
						<c:if test="${item.level<3}"><td><a href="${ctx}/dict/area?parentId=${item.id}">${item.name}</a></td></c:if>
						<td>${item.level }级</td>
						<td>
						<c:if test="${item.parentId<1}">无</c:if>
						<c:if test="${item.parentId>0}"><u:city cityId="${item.parentId}"></u:city></c:if>
						</td>
						<td>
						<c:if test="${item.level<2}">上级</c:if>
						<c:if test="${item.level>1}"><a href="${ctx}/dict/area?parentId=${ppId}">上级</a></c:if>
						
						<shiro:hasPermission name="DEFAULT">
								 |  
						<c:if test="${item.level>2}">增加下级</c:if>
						<c:if test="${item.level<3}"><a href="javascript:addson('${item.level+1}',${item.id});">增加下级</a></c:if>
						 </shiro:hasPermission>	
						 <shiro:hasPermission name="DEFAULT">
						 	 |  
							<a  href="javascript:edit('${item.id}','${item.name}',${item.parentId});">编辑</a>
						</shiro:hasPermission>
						 <shiro:hasPermission name="DEFAULT">
						 	 | 
							 <a href="javascript:del('${item.id}','${item.name}');">删除</a>
						 </shiro:hasPermission>	 
						  
							 </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${areaList.pages}"
			current="${areaList.pageNum}" sum="${areaList.total}" />
	</div>
</body>

</html>