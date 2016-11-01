<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(function(){
	
	jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {

       	 var slist =  tmjs.slist ;
       	var slists = [];
           $.each(slist, function(i, item) {
           	var inner_no = "" ;
           	if(item.inner_no){inner_no=item.inner_no+','}
           	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
           });
		     $jqq("#sno").autocomplete(slists, {
		  		minChars: 0,
		  		max: 12,
		  		autoFill: true,
		  		mustMatch: false,
		  		matchContains: true,
		  		scrollHeight: 220,
		  		formatItem: function(data, i, total) {
		  			return data[0];
		  		}
		  	}).result(function(event, data, formatted) {
				if(data[0].indexOf(')')>-1){
					 $("#sno").val(data[0].substring(0,data[0].indexOf('('))) ;
				   } 
			});	

  });  
	
	
	trHover('t2');
	 $('#add').click(function(){
		 $.dialog({lock: true,title:'新增系统用户',cache:false,drag: false,width:1000,height:600,resize: false,max: false,content: 'url:${ctx}/system/ushow?layout=no',close: function(){
			 location.reload();
	  	 }});
	 });
	 $('#reload_btn').click(function(){
		 location.reload();
	});
});

function edit(username,id){
	 $.dialog({lock: true,title:'编辑系统用户',drag: false,width:1000,height:600,resize: false,max: false,content: 'url:${ctx}/system/ushow?userName='+username+'&id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}

function reset(id,userName,email){
	 if(confirm('确定重置 ['+userName+'] 用户吗？')){
		$.ajax({url:'${ctx}/system/ureset',data:{'id':id,'userName':userName,'email':email},success:function(msg){
			 if(msg=='err'){
				 alert('重置失败');
				 location.reload();
			 }else{
				 alert(msg);
				 location.reload();
			 }
		 }});
	}
}

function status(id,userName,s){
	tip='';
	if(s==0){
		tip='禁用';
	}else{
		tip='启用';
	}
	if(confirm('确定要'+tip+' ['+userName+'] 用户吗?')){
		$.ajax({url:'ustatus',data:{'id':id,'userName':userName,'s':s},success:function(msg){
			 if(msg==1){
				 alert('修改成功');
				 location.reload();
			 }
		 }});
	}
}


function del(id,userName){
	 if(confirm('确定删除 ['+userName+'] 用户吗？')){
		 $.ajax({url:'udel',data:{'id':id,'userName':userName},success:function(msg){
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
<span style="color:red;font-size:15px;">温馨提示：系统用户页面管理系统登录用户，<b>登录账号 </b>用于登录系统后台，完成系统操作(密码与登录账号一致)。</span>
<div class="search">
    <div class="tableble_search">
        <div class=" search_cont">
		<form:form id="f"   method="get">
			<ul>
			<li><select name="sk">
						<option value="real_name" <c:out value="${params['sk'] eq 'real_name'?'selected':'' }"/> >用户姓名</option>
						<option value="user_name" <c:out value="${params['sk'] eq 'user_name'?'selected':'' }"/> >登录账号</option>
				</select> <input type="text" value="${params['sv']}" name="sv"></input>
				</li>
				<li>网点编号：<input type="text" value="${params['sno']}" name="sno" id="sno"></input></li>
				<li>用户状态：<select name="ustatus">
				        <option value=""  >全部</option>
						<option value="1" <c:out value="${params['ustatus'] eq '1'?'selected':'' }"/> >启用</option>
						<option value="0" <c:out value="${params['ustatus'] eq '0'?'selected':'' }"/> >关闭</option>
				</select> 
				</li>
				
				<li><input class="button input_text  medium blue_flat" type="submit" value="查询" />
				</li>
                <li><input class="button input_text  medium blue_flat" type="reset" value="重置" />
			</ul>
		</form:form>
		 </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
 <div class="operator">
	 <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/></div>
	 <shiro:hasPermission name="DEFAULT">
	    <div class="search_new"> <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /></div>
	  </shiro:hasPermission>
	    </div>
	</div>
 </div>    <!-- tableble_search end  -->	
	
	<div class="tbdata">
	 
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th align="center">序号</th>
					<th align="center">所属网点</th>
					<th  align="center">登录账号</th>
					<th  align="center">用户姓名</th>
					<th  align="center">用户角色</th>
					<th  align="center">电子邮件</th>
					<th align="center">用户状态</th>
					<th  align="center">创建者</th>
					<th  align="center">创建时间</th>
					<th  align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}  <c:out value="${item.status eq '0'?'  deled':''}"></c:out> " >
						<td align="center">${status.count}</td>
						<td>${item.substation_name }</td>
						<td>${item.user_name }</td>
						<td>${item.real_name }</td>
						<td>${item.group_names }</td>
						<td>${item.email }</td>
						<td><c:if test="${item.status eq 0 }">关闭</font></c:if>
                            <c:if test="${item.status eq 1 }">开启</c:if></td>
						<td>${item.create_operator}</td>
						<td><fmt:formatDate value="${item.create_time}"  pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
						<shiro:hasPermission name="DEFAULT">
							<c:if test="${item.status eq 0 }">
									<a href="javascript:status('${item.id}','${item.user_name }',1);">启动</a>
								</c:if>
								<c:if test="${item.status eq 1 }">
									<a href="javascript:status('${item.id}','${item.user_name }',0);">禁用</a>
								</c:if>
								 | 
						</shiro:hasPermission>
						 <shiro:hasPermission name="DEFAULT">
							<a  href="javascript:edit('${item.user_name}',${item.id});">编辑</a>
								 | 
						</shiro:hasPermission>
						 <shiro:hasPermission name="DEFAULT">
							 <a href="javascript:del('${item.id}','${item.user_name}');">删除</a>
						 </shiro:hasPermission>	 
						  <shiro:hasPermission name="DEFAULT">
								 |  <a href="javascript:reset('${item.id}','${item.user_name}','${item.email}');">重置密码</a>
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