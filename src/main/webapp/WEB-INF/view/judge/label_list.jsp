<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
$(function(){
	$("#addBtn").click(function(){
		$.dialog({lock: true,title:'标签新增',drag: true,width:400,height:220,resize: false,max: false,content: 'url:${ctx}/judge/init?layout=no',close: function(){
			location.reload();
   	 }});
	});
});

function changeStatus(id,s,p){
	$.ajax({
		   type: "POST",
		   url: "${ctx}/judge/labelUpdate",
		   data: {"id":id,"status":s},
		   success: function(msg){
		     alert(msg );
		     setTimeout(function(){
		    	 window.location.href="${ctx }/judge/label?p="+p+"&labelName="+$("input[name=labelName]").val();
		     }, 1000)
		   }
		});
}

function edit(id){
	$.dialog({lock: true,title:'标签编辑',drag: true,width:400,height:220,resize: false,max: false,content: 'url:${ctx}/judge/init?id='+id+'&layout=no',close: function(){
		location.reload();
	 }});
}

</script>
<style type="text/css">
.search form{margin:10px 0;margin-left:10px;}
.col1{width:100px;}
.col3{width:300px;}
.col3 a{margin:0 5px;}
.disabled{color:#666 !important;}
</style>
</head>
<body>
	<div class="search">
		<form action="${ctx }/judge/label" method="post">
			<span>
				标签：
			</span>
			<input value="${params.labelName }" type="text" name='labelName'/>
			<input  type="submit" value="查询" id="submitBtn" class="button input_text  medium sear_butt an_a an_aon" style="margin-left:10px;"/>
			<input  type="button" value="新增" id="addBtn" class="button input_text  medium sear_butt an_a an_aon" style="margin-left:10px;"/>
			<span style="color:red;margin-left:20px;">温馨提示：标签启用最多10个，超过的标签微信端不显示</span>
		</form>
	</div>
	<div>
		<table class="t2 tableble_table ta_ta" width="100%">
		   <thead>
		   	<th class="col1">序号</th>
		   	<th>标签</th>
		   	<th>操作</th>
		   </thead>
		   <c:forEach items="${pageInfo.list }" var="item" varStatus="status">
		   	<tr>
		   		<td>${status.index+1 }</td>
		   		<td>${item.labelName}</td>
		   		<td class="col3">
		   			<a href="javascript:" onclick="edit(${item.id})">编辑</a>
		   			<a href="${ctx }/judge/delete?id=${item.id}">删除</a>
		   			<c:if test="${item.status eq 1}">
		   				<span  class="disabled" >启用</span>
		   				<a href="javascript:" onclick="changeStatus(${item.id},0,${pageInfo.pageNum})">停用</a>
		   			</c:if>
		   			<c:if test="${item.status eq 0}">
		   				<a href="javascript:" onclick="changeStatus(${item.id},1,${pageInfo.pageNum})">启用</a>
		   				<span  class="disabled" >停用</span>
		   			</c:if>
		   			
		   			
		   		</td>
		   	</tr>
			</c:forEach>
		</table>
		
	</div>
	<div id="page">
		<pagebar:pagebar total="${pageInfo.pages}"
			current="${pageInfo.pageNum}" sum="${pageInfo.total}" />
	</div>
</body>

</html>