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
			 $.dialog({lock: true,title:'新增物品类型',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/lgc/type_edit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});

	
	function edit(id){
		 $.dialog({lock: true,title:'编辑物品类型',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/lgc/type_edit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id,name){
		 if(confirm('确定删除 ['+name+'] 类型吗？')){
			 $.ajax({url:'type_del',data:{'id':id},success:function(msg){
				 if(msg==1){
					 alert('删除成功');
					 location.reload();
				 }else{
					 alert(msg);
				 }
			 }});
		 }
	}
	
	function defaultItem(id){
		 if(confirm('确定保存吗？')){
			 $.ajax({url:'defaultItem',data:{'id':id},success:function(msg){
				 if(msg==1){
					 alert('保存成功');
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
    <form:form id="trans" action="${ctx}/lgc/item_type" method="get">
    	<ul>
            <li>物品类型：<input type="text" value="${params['itemText']}" name="itemText"></input></li>
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
            <th align="center">序号</th>
            <th align="center">物品类型</th>
            <th align="center">备注</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.item_text}</td>
                <td>${item.note}</td>
                <td align="center">
                  <a href="javascript:edit('${item.id}');">编辑</a>
	                 | 
	                <a href="javascript:del('${item.id}', '${item.item_text}');">删除</a>   
	                 | 
	                 <c:if test="${item.default_item ne '1' }"><a href="javascript:defaultItem('${item.id}');">设置默认</a>  </c:if> 
	                 <c:if test="${item.default_item eq '1' }">默认</c:if> 
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