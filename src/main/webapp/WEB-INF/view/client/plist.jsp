<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">

 $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload()
       	});       
            
            $('#add').click(function(){
       		 $.dialog({lock: true,title:'新增软件产品',drag: false,width:560,height:200,resize: false,max: false,content: 'url:${ctx}/client/pshow?layout=no',close: function(){
       			 location.reload();
       	  	 }});
       	 }); 
            
         
});
 
 function edit(id){
	 $.dialog({lock: true,title:'编辑软件产品',drag: false,width:560,height:200,resize: false,max: false,content: 'url:${ctx}/client/pshow?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}
 
 function del(id,appName,appCode){
  	 if(confirm('确定删除 ['+appName+'] 吗？')){
  		$.ajax({url:'${ctx}/client/pdel',data:{'id':id,'appCode':appCode},success:function(msg){
  			 if(msg=='1'){
  				 alert('删除成功');
  				 location.reload(); 
  			 }else if(msg=='err'){
  				 alert('删除失败');
  				 location.reload();
  			 }else{
  				 alert(msg);
  				 location.reload();
  			 }
  		 }});
  	}
  }

  function status(id,appName,s){
  	tip='';
  	if(s==0){
  		tip='禁用';
  	}else{
  		tip='启用';
  	}
  	if(confirm('确定要'+tip+' ['+appName+'] 产品吗?')){
  		$.ajax({url:'pstatus',data:{'id':id,'s':s},success:function(msg){
  			 if(msg==1){
  				 alert('修改成功');
  				 location.reload();
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
    <form:form id="trans" action="${ctx}/client/product" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>产品代码：<input type="text" value="${params['appCode']}"  name="appCode"></input></li>
            <li>产品名称：<input type="text" value="${params['appName']}" name="appName"></input></li>
            <li>启用状态：<select name="status" style="width: 110px">
                <option value="">--全部--</option>
                <option value="1" ${params['status'] eq '1'?'selected':''}>启用</option>
                <option value="0" ${params['status'] eq '0'?'selected':''}>关闭</option>
             </select></li>
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
   <div class="operator">
	     <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>
	    <shiro:hasPermission name="DEFAULT">
	     <div class="search_new"><input id="add" class="button input_text  big gray_flat" type="submit" value="新增" />  </div>
	  </shiro:hasPermission> 
	    </div>
	 </div>   <!-- tableble_search end  -->     
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="3%" align="center">序号</th>
            <th width="10%" align="center">产品代码</th>
            <th width="7%" align="center">中文名称</th>
            <th width="7%" align="center">英文别名</th>
            <th width="10%" align="center">启用状态</th>
            <th width="10%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appProductList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.appCode}</td>
                <td>${item.appName}</td>
                 <td>${item.bname}</td>
                <td><u:dict name="SWITCH" key="${item.status}" /></td>
              <%--   <td> <c:if test="${empty item.head_image}">未上传</c:if>
                 <c:if test="${!empty item.head_image}"><a href="javascript:head_image('${item.head_image}');">查看</a></c:if></td> --%>
                <td align="center">
                  <shiro:hasPermission name="DEFAULT">
                <c:if test="${item.status eq 0 }"><a href="javascript:status('${item.id}','${item.appName}',1);">启用</a></font></c:if>
                <c:if test="${item.status eq 1 }"><a href="javascript:status('${item.id}','${item.appName}',0);">关闭</a></c:if>
                  </shiro:hasPermission> 
                   <shiro:hasPermission name="DEFAULT">
                        |
                 <a href="javascript:edit('${item.id}');">编辑</a>
                 </shiro:hasPermission> 
                  <shiro:hasPermission name="DEFAULT">
                          |
                 <a href="javascript:del('${item.id}','${item.appName}','${item.appCode}');">删除</a>
                  </shiro:hasPermission> 
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${appProductList.pages}"
                     current="${appProductList.pageNum}" sum="${appProductList.total}"/>
</div>
</body>

</html>