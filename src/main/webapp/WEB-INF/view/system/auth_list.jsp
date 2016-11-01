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
		 $.dialog({lock: true,title:'新增权限资源',drag: false,width:520,height:300,resize: false,max: false,content: 'url:${ctx}/system/ashow?layout=no',close: function(){
			 location.reload();
	  	 }});
	 });
	 $('#reload_btn').click(function(){
		 location.reload();
	});
	
     $('.select_all').click(function() {
       	 if($(this).prop("checked"))	{
       			$('input[name=ids]').each(function(){
       				if(!$(this).prop('disabled')){
       					$(this).prop('checked',true); 
       				}
               		
               	}); 
       	 }else{
       		 $('input[name=ids]').each(function(){
            		$(this).prop('checked',false); 
            	}); 
       	 }
       
       });
     
     
     $('#sudo').click(function(){
     	var ids = '';
     	$('.ids:checked').each(function(){
     		ids += $(this).val()+','; 
     	});
     	if(ids.length>0){
     		ids = ids.substring(0, ids.length-1) ;
     		$.dialog({lock: true,title:'特殊权限设置',drag: true,width:500,height:300,resize: false,max: false,content: 'url:${ctx}/system/sudo?ids='+ids+'&layout=no',close: function(){
           	 }});
     	}else{
     		alert("请选择一项或多项！");
     		return ;
     	}
   	});   
	 
});



function del(id,name){
	 if(confirm('确定删除 ['+name+'] 权限吗？')){
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
		<form:form id="trans" action="${ctx}/system/auth" method="get">
			<ul>
			<li>权限名称: <input type="text" value="${params['auth_name']}" name="auth_name"></input></li>
			<li>权限代码: <input type="text" value="${params['auth_code']}" name="auth_code"></input></li>
			 	<li><input class="button input_text  medium blue_flat"
					type="submit" value="查询" />
				</li>
			 	<li><input class="button input_text  medium blue_flat"
					type="reset" value="重置" />
				</li>
			</ul>
		</form:form>
		 </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->  
   <div class="tableble_search">
  <div class="operator">
	<div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
	 <shiro:hasPermission name="DEFAULT">
	   <div class="search_new">   <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /> </div>
	    <div class="search_new"><input class="sear_butt" type="submit" value="设置特殊权限" id="sudo"/> </div>
	  </shiro:hasPermission>
	    </div>
	  </div>    <!-- tableble_search end  -->
 
	</div>

	<div class="tbdata">
	
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
				 <th align="center"  class="nosort">&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;</th>
					<th width="50" align="center">序号</th>
					<th align="center">权限名称</th>
					<th align="center">权限代码</th>
					<th align="center">上级权限</th>
					<th width="70" align="center">权限类别</th>
					<th align="center">创建时间</th>
					<th width="50" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
					<td align="center"><input class="ids" name="ids"  type="checkbox" value="${item.id}" /></td> 
						<td align="center">${status.count}</td>
						<td>${item.auth_name }</td>
						<td>${item.auth_code }</td>
						<td><c:choose>
	          	<c:when test="${empty item.parent_name}">
	          	无
	          	</c:when>
	          	<c:otherwise>
	          	${item.parent_name}
	          	</c:otherwise>
	          </c:choose></td>
						<td><u:dict name="AUTH_TYPE" key="${item.category}" /></td>
						<td><fmt:formatDate value="${item.create_time}"  pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td  align="center"> 
						<shiro:hasPermission name="DEFAULT">
							<a href="javascript:del('${item.id}','${item.auth_name}');">删除</a>
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