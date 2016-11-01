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
			 $.dialog({lock: true,title:'新增转件公司',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/forcpn/edit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 
		 $('#select').click(function(){
			 $.dialog({lock: true,title:'选择公司',drag: false,width:500,height:850,resize: false,max: false,content: 'url:${ctx}/forcpn/kdyblist?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});

	
	function edit(id){
		 $.dialog({lock: true,title:'编辑转件公司',drag: false,width:500,height:170,resize: false,max: false,content: 'url:${ctx}/forcpn/edit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id,name){
		 if(confirm('确定删除 ['+name+'] 转件公司吗？')){
			 $.ajax({url:'del',data:{'id':id},success:function(msg){
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
    <form:form id="trans" action="${ctx}/forcpn/list" method="get">
    	<ul>
            <li>转出公司：<input type="text" value="${params['cpnName']}" name="cpnName"></input></li>
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
	 <div class="search_new">  <input id="select" class="button input_text  big gray_flat" type="submit" value="选择已接入公司" /></div>
	 </div>   <!-- tableble_search end  -->   
  
</div>
<div class="tbdata">
   
	<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">转出公司</th>
            <th align="center">备注</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.cpn_name}</td>
                <td>${item.note}</td>
                <td align="center">
                  <a href="javascript:edit('${item.id}');">编辑</a>
	                 | 
	                <a href="javascript:del('${item.id}', '${item.cpn_name}');"">删除</a>   
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