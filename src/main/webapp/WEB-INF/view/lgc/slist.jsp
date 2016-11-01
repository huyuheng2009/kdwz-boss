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
			 $.dialog({lock: true,title:'新增网点分站',drag: false,width:650,height:360,resize: false,max: false,content: 'url:${ctx}/lgc/sedit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});


	function show(id){
		 $.dialog({lock: true,title:'查看网点分站',drag: false,width:650,height:360,resize: false,max: false,content: 'url:${ctx}/lgc/sshow?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function edit(id){
		 $.dialog({lock: true,title:'编辑网点分站',drag: false,width:650,height:360,resize: false,max: false,content: 'url:${ctx}/lgc/sedit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id,name){
		 if(confirm('确定删除 ['+name+'] 网点分站吗？')){
			 $.ajax({url:'/userManager/sdel',
				 data:{'id':id,'name':name},
				 success:function(msg){
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
		if(confirm('您确认要停用['+realName+']分站吗?停')){
			$.ajax(
				{
				url:'/userManager/sstop'	,
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
		if(confirm('您确认要启用['+realName+']分站吗?')){
			$.ajax(
				{
				url:'/userManager/sstart'	,
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
</script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
<form:form id="trans" action="${ctx}/lgc/slist" method="get">
    	<ul>
            <li>网点编号：<input type="text" value="${params['innerNo']}" name="innerNo"></input></li>
            <li>网点名称：<input type="text" value="${params['substationName']}" name="substationName"></input></li>
             <li>管理方式：<select name="substation_type" style="width: 139px;">
								 <option value="全部" >全部</option>     					    									
								 <option value="ZZ"  ${param.substation_type eq 'ZZ' ? 'selected':''} >直营</option>     					    									
								 <option value="JJ"  ${param.substation_type eq 'JJ' ? 'selected':''} >加盟</option>     					    									
								</select>	</li>
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
  
      <div class="search_new"> <input id="reload_btn" class="button input_text  big gray_flat" type="submit" value="刷新" /></div>
	<shiro:hasPermission name="DEFAULT">
   <div class="search_new"> <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /></div>
	</shiro:hasPermission> 
  
  </div>   <!-- tableble_search end  -->  
</div>
<div class="tbdata">


	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th  align="center">序号</th>
            <th  align="center">网点编号</th>
            <th  align="center">网点名称</th>
            <th  align="center">网点区域</th>
            <th align="center">网点地址</th>
            <th align="center">管理方式</th>
            <th  align="center">电话</th>
            <th align="center">快递员</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${substationList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''}  <c:out value="${item.status eq '0'?'deled':''}"></c:out>">
                <td align="center">${status.count}</td>
                <td>${item.inner_no}</td>
                <td>${item.substation_name}</td>
                  <td>${item.sarea}</td>
                <td>${item.substation_addr}</td>
                 <td><c:if test="${item.substation_type eq 'J'}">加盟</c:if><c:if test="${item.substation_type ne 'J'}">直营</c:if>  </td> 
                <td>${item.phone}</td>
                <td>  
                 <c:if test="${item.cc>0}"> <a href="/lgc/clist?substationNo=${item.substation_no}">${item.cc}人</a></c:if>
                 <c:if test="${item.cc<=0}"> 0人</c:if>
               </td>
                <td align="center">
                   <a href="javascript:show('${item.id}')">详情</a>
                   <shiro:hasPermission name="DEFAULT">
                        |
                     <a href="javascript:edit('${item.id}');">编辑</a>
                     </shiro:hasPermission> 
                    <shiro:hasPermission name="DEFAULT">
                  	   |
                        <c:if test="${item.status==1}">   <a href="javascript:stopStatus('${item.id}', '${item.substation_name}');"">停用</a> </c:if>                 
                	    <c:if test="${item.status==0}">   <a href="javascript:startStatus('${item.id}', '${item.substation_name}');"">启用</a> </c:if>                            
                        |
                     <a href="javascript:del('${item.id}', '${item.substation_name}');"">删除</a>   
                     </shiro:hasPermission> 
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${substationList.pages}"
                     current="${substationList.pageNum}" sum="${substationList.total}"/>
</div>
</body>

</html>