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
			 $.dialog({lock: true,title:'新增快递员',cache:false,drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/lgc/cedit?layout=no',close: function(){
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
		 $('#ccount').click(function(){
      		 location.href="/lgc/ccount";
      	});
	});


	function show(id){
		 $.dialog({lock: true,title:'查看快递员',drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/lgc/cshow?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function edit(id){
		 $.dialog({lock: true,title:'编辑快递员',drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/lgc/cedit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
 	function del(id,realName){
		 if(confirm('确定删除 ['+realName+'] 快递员吗？')){
			 $.ajax({url:'cdel',data:{'id':id,'realName':realName},success:function(msg){
				 if(msg==1){
					 alert('删除成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
			 }});
		 }
	} 
	
	function stopStatus(id,realName){
		if(confirm('您确认要停用['+realName+']快递员吗?停用后快递员将无法登录APP收派件')){
			$.ajax(
				{
				url:'/userManager/cstop'	,
				dataType:'text',
				data:{
					'id':id				
				},
			success:function(msg){			
				 if(msg==1){
					 alert('停用成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
						}
			});		
		}	
	}
	
	function startStatus(id,realName){
		if(confirm('您确认要启用['+realName+']快递员吗?启用后快递员可登录APP收派件')){
			$.ajax(
				{
				url:'/userManager/cstart'	,
				dataType:'text',
				data:{
					'id':id				
				},
			success:function(msg){			
				 if(msg==1){
					 alert('启用成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
				}
			});		
		}	
	}
	
	function reset(id){
		 if(confirm('确定重置吗？')){
			$.ajax({url:'${ctx}/lgc/creset',data:{'id':id},success:function(msg){
				 if(msg=='err'){
					 alert('重置失败');
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
<form:form id="trans" action="${ctx}/lgc/clist" method="get">
    	<ul>
    		<li>网点名称：<select id="substationNo" name="substationNo" style="width: 150px">
							<option value="">--全部--</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select></li>
            <li>登录号：<input type="text" value="${params['userName']}" name="userName"></input></li>
            <li>快递员姓名：<input type="text" value="${params['realName']}" name="realName"></input></li>
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
	<shiro:hasPermission name="DEFAULT">
 <div class="search_new"><input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /> </div>
	</shiro:hasPermission> 
<%-- 		<shiro:hasPermission name="DEFAULT">
 <div class="search_new"><input style="width: 120px;" id="ccount" class="button input_text  big gray_flat" type="submit" value="查看统计数据" /> </div>
	</shiro:hasPermission>    --%>
   </div>   <!-- tableble_search end  -->  
  
</div>
<div class="tbdata">
 
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center">序号</th>
              <th align="center">网点名称</th>
            <th align="center">登陆号</th>
            <th  align="center">快递员姓名</th>
            <th  align="center">快递员内部编号</th>
            <th  align="center">内部联系号码</th>
            <th  align="center">电话</th>
            <th  align="center">注册时间</th>
            <th align="center">创建者</th>
            <th  align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courierList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''}  <c:out value="${item.status eq '0'?'deled':''}"></c:out>">
                <td align="center">${status.count}</td>
                 <td>${item.substation_name}</td>
                <td>${item.user_name}</td>
                <td>${item.real_name}</td>
                <td>${item.inner_no}</td>
                <td>${item.inner_phone}</td>
                <td>${item.phone }</td>
                <td><fmt:formatDate value="${item.regist_time}" type="both"/></td>
                <td>${item.create_operator }</td>
                <td align="center">
                   <a href="javascript:show('${item.id}')">详情</a>
                   	<shiro:hasPermission name="DEFAULT">
                        |
                     <a href="javascript:edit('${item.id}');">编辑</a>
                     </shiro:hasPermission> 
                         |
                        <c:if test="${item.status==1}">   <a href="javascript:stopStatus('${item.id}', '${item.real_name}');"">停用</a> </c:if>                 
                	    <c:if test="${item.status==0}">   <a href="javascript:startStatus('${item.id}', '${item.real_name}');"">启用</a> </c:if>                            
               	                  
                     <shiro:hasPermission name="DEFAULT">
                        |
                     <a href="javascript:del('${item.id}', '${item.real_name}');">删除</a>  
                     </shiro:hasPermission>
                       |
                     <a href="javascript:reset('${item.id}');">重置密码</a>    
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