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
			 $.dialog({lock: true,title:'新增处罚条例',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/system/punish_edit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});

	
	function edit(id){
		 $.dialog({lock: true,title:'编辑处罚条例',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/system/punish_edit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id,name){
		 if(confirm('确定删除该条处罚条例吗？')){
			 $.ajax({url:'punish_del',data:{'id':id},success:function(msg){
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
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>
  <shiro:hasPermission name="SYSTEM_PUNISH">
 <div class="search_new">  <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /></div>
	</shiro:hasPermission> 
	 </div>   <!-- tableble_search end  -->   
  
</div>
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">违规事项</th>
            <th align="center">处罚标准</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.punish_text}</td>
                <td>${item.rule_text}</td>
                <td align="center">
                  <shiro:hasPermission name="SYSTEM_PUNISH">
                  <a href="javascript:edit('${item.id}');">编辑</a>
	                 | 
	                <a href="javascript:del('${item.id}', '${item.punish_text}');"">删除</a>   
	                </shiro:hasPermission> 
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>