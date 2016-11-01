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
       		 $.dialog({lock: true,title:'新增软件产品',drag: false,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/client/vshow?layout=no',close: function(){
       			 location.reload();
       	  	 }});
       	 }); 
            
            $('#fileadd').click(function(){
            	 $.dialog({lock: true,title:'上传文件',drag: false,width:600,height:200,resize: false,max: false,content: 'url:${ctx}/client/fileadd?layout=no',close: function(){
            		 location.reload();
              	 }});
          	 });   
});
 
 function edit(id){
	 $.dialog({lock: true,title:'编辑软件产品',drag: false,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/client/vshow?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}
 
 function plist(id){
	 $.dialog({lock: true,title:'上传plist文件',drag: false,width:600,height:200,resize: false,max: false,content: 'url:${ctx}/client/vplist?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}
 
 function pay_type(id){
	 $.dialog({lock: true,title:'设置软件支持的支付方式',drag: false,width:600,height:200,resize: false,max: false,content: 'url:${ctx}/client/vpay?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}
 
 function del(id){
  	 if(confirm('确定删除此版本吗？')){
  		$.ajax({url:'vdel',data:{'id':id},success:function(msg){
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

  function status(id,s){
  	tip='';
  	if(s==0){
  		tip='禁用';
  	}else{
  		tip='启用';
  	}
  	if(confirm('确定要'+tip+'此版本吗?')){
  		$.ajax({url:'vstatus',data:{'id':id,'s':s},success:function(msg){
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
    <form:form id="trans" action="${ctx}/client/appversion" method="get">
        <ul>
            <li>产品代码：<input type="text" value="${params['appCode']}"  name="appCode"></input></li>
             <li>应用平台：<select name="platform" style="width: 110px">
                <option value="">--全部--</option>
                <option value="android" ${params['platform'] eq 'android'?'selected':''}>安卓</option>
                <option value="ios" ${params['platform'] eq 'ios'?'selected':''}>苹果</option>
             </select></li>
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
	  
	  <div class="search_new"><input id="fileadd" class="button input_text  big gray_flat" type="submit" value="新增文件" />  </div>
	  
	    </div>
	 </div>   <!-- tableble_search end  -->       
</div>


<div class="tbdata">
    	
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="3%" align="center">序号</th>
            <th width="12%" align="center">应用名称</th>
            <th width="7%" align="center">应用平台</th>
            <th width="7%" align="center">应用版本</th>
            <th width="7%" align="center">强制更新</th>
            <th width="6%" align="center">启用状态</th>
             <th width="6%" align="center">下载地址</th>
            <th width="10%" align="center">发布时间</th>
            <th width="10%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${appVersionList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td><u:dict name="PRODUCT" key="${item.appCode}" /></td>
                <td><u:dict name="PLATFORM" key="${item.platform}" /></td>
                <td>${item.version}</td>
                 <td>
                   <c:if test="${item.mupdate eq 0 }">否</c:if>
                    <c:if test="${item.mupdate eq 1 }">是</c:if>
                 </td>
                 <td><u:dict name="SWITCH" key="${item.status}" /></td>
                 <td><a href="${item.address}">下载</a></td>
                <td>${item.publishTime}</td>
                <td align="center">
                <shiro:hasPermission name="DEFAULT">
                <c:if test="${item.status eq 0 }"><a href="javascript:status('${item.id}',1);">启用</a></font></c:if>
                <c:if test="${item.status eq 1 }"><a href="javascript:status('${item.id}',0);">关闭</a></c:if>
                  </shiro:hasPermission> 
                  <shiro:hasPermission name="DEFAULT">
                        |
                 <a href="javascript:edit('${item.id}');">编辑</a>
                 </shiro:hasPermission> 
                     <c:if test="${item.platform eq 'ios'}">   |
                 <a href="javascript:plist('${item.id}');">plist</a></c:if>
                   <shiro:hasPermission name="DEFAULT">
                          |
                 <a href="javascript:del('${item.id}');">删除</a>
                 </shiro:hasPermission> 
                <%--           |
                 <a href="javascript:pay_type('${item.id}');">支付方式</a> --%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${appVersionList.pages}"
                     current="${appVersionList.pageNum}" sum="${appVersionList.total}"/>
</div>
</body>

</html>