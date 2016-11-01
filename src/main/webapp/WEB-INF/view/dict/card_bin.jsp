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
		 $.dialog({lock: true,title:'新增卡bin',drag: false,width:450,height:230,resize: false,max: false,content: 'url:${ctx}/dict/card_add?layout=no',close: function(){
			 location.reload();
	  	 }});
	 });
	 $('#reload_btn').click(function(){
		 location.reload();
	});
});

function edit(id){
	 $.dialog({lock: true,title:'编辑卡bin',drag: false,width:450,height:230,resize: false,max: false,content: 'url:${ctx}/dict/card_edit?id='+id+'&layout=no',close: function(){
		 location.reload();
  	 }});
}


function del(id){
	 if(confirm('确定删除此银行卡吗？')){
		 $.ajax({url:'card_del',data:{'id':id},success:function(msg){
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
		<form:form id="trans" action="${ctx}/dict/card_bin" method="get">
			<ul>
			<li>银行名称: <input type="text" value="${params['bankName']}" name="bankName"></input></li>
			<li>卡名称: <input type="text" value="${params['cardName']}" name="cardName"></input></li>
			 <li>卡类型:<select id="cardType" name="cardType" style="width: 110px">
							<option value="">--全部--</option>
							
	           <c:forEach items="${cardDist}" var="item" varStatus="status">
                    <option value="${item.dictValue }" <c:out value="${params['cardType'] eq item.dictValue?'selected':'' }"/>>${item.dictValue }</option>
                </c:forEach>						
			</select>
				</li>
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
					<th width="3%" align="center">序号</th>
					<th width="20%" align="center">银行名称</th>
					<th width="15%" align="center">卡名称</th>
				    <th width="10%" align="center">卡类型</th>
					<th width="7%" align="center">卡号长度</th>
					<th width="7%" align="center">卡标识符长度</th>
				    <th width="12%" align="center">卡标识号</th>
					<th width="13%" align="center">创建时间</th>
					<th width="12%" align="center">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.content}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.bank_name }</td>
						<td>${item.card_name }</td>
						<td>${item.card_type }</td>
						<td>${item.card_length }</td>
						<td>${item.verify_length }</td>
						<td>${item.verify_code }</td>
						<td><fmt:formatDate value="${item.create_time}"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
						<td>
						<shiro:hasPermission name="DEFAULT">
						<a href="javascript:edit('${item.id}');">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="DEFAULT">
						 |<a href="javascript:del('${item.id}');">删除</a>
						</shiro:hasPermission> </td>
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