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
		 $.dialog({lock: true,title:'新增MCC',drag: false,width:420,height:230,resize: false,max: false,content: 'url:${ctx}/dict/mcc_add?layout=no',close: function(){
			 location.reload();
	  	 }});
	 });
	 $('#reload_btn').click(function(){
		 location.reload();
	});
});

function edit(id){
	 $.dialog({lock: true,title:'编辑MCC',drag: false,width:420,height:230,resize: false,max: false,content: 'url:${ctx}/dict/mcc_edit?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}


function del(id){
	 if(confirm('确定删除此MCC吗？')){
		 $.ajax({url:'mcc_del',data:{'id':id},success:function(msg){
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
		<form:form id="trans" action="${ctx}/dict/dict_mcc" method="get">
			<ul>
			<li>mcc值: <input type="text" value="${params['mcc']}" name="mcc"></input></li>
			<li>mcc名称: <input type="text" value="${params['name']}" name="name"></input></li>
			 <%-- <li>mcc类型:<select id="c1Name" name="c1Name" style="width: 110px">
							<option value="">--全部--</option>
							<option value="借记卡" ${params['cardType'] eq '借记卡'?'selected':''}>借记卡</option>
							<option value="贷记卡" ${params['cardType'] eq '贷记卡'?'selected':''}>贷记卡</option>
							<option value="准贷记卡" ${params['cardType'] eq '准贷记卡'?'selected':''}>准贷记卡</option>
							<option value="预付费卡" ${params['cardType'] eq '预付费卡'?'selected':''}>预付费卡</option>
					</select>
				</li> --%>
			 	<li><input class="button input_text  medium blue_flat"
					type="submit" value="查询" />
				</li>
			 	<li><input class="button input_text  medium blue_flat"
					type="reset" value="重置" />
				</li>
			</ul>
		</form:form>
		<div class="clear"></div>
	</div>
	<div class="tbdata">
		 <div class="operator">
	 <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>
	 <shiro:hasPermission name="DEFAULT">
	    <input id="add" class="button input_text  big gray_flat" type="submit" value="新增" />
	  </shiro:hasPermission>
	    </div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="10%" align="center">mcc值</th>
					<th width="20%" align="center">mcc名称</th>
				    <th width="15%" align="center">c1_name</th>
					<th width="15%" align="center">c2_name</th>
					<th width="15%" align="center">标准扣率</th>
					<th width="15%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.content}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.mcc }</td>
						<td>${item.name }</td>
						<td>${item.c1_name }</td>
						<td>${item.c2_name }</td>
						<td>${item.standard_rate }</td>
						<td>
						<shiro:hasPermission name="DEFAULT">
						<a href="javascript:edit('${item.id}');">修改</a> 
						</shiro:hasPermission>
						<shiro:hasPermission name="DEFAULT">
						|<a href="javascript:del('${item.id}');">删除</a>
						</shiro:hasPermission></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.totalPages}"
			current="${list.number + 1}" sum="${list.totalElements}"/>
	</div>
</body>

</html>