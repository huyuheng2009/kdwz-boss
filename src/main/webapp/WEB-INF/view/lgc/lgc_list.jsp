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
			 $.dialog({lock: true,title:'新快递公司',drag: false,width:500,height:300,resize: false,max: false,content: 'url:${ctx}/lgc/lgcedit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});


	function show(id){
		 $.dialog({lock: true,title:'查看快递公司',drag: false,width:500,height:200,resize: false,max: false,content: 'url:${ctx}/lgc/lgcshow?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function edit(id){
		 $.dialog({lock: true,title:'编辑快递公司',drag: false,width:600,height:300,resize: false,max: false,content: 'url:${ctx}/lgc/lgcedit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	 function pay_type(lgcNo){
		 $.dialog({lock: true,title:'设置支付方式',drag: false,width:600,height:200,resize: false,max: false,content: 'url:${ctx}/lgc/paytype?lgcNo='+lgcNo+'&layout=no',close: function(){
			 location.reload();
	  	 }});
	}

	function del(id,name){
		 if(confirm('确定删除 ['+name+'] 快递公司吗？')){
			 $.ajax({url:'lgcdel',data:{'id':id,'name':name},success:function(msg){
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
    <form:form id="trans" action="${ctx}/lgc/lgclist" method="get">
    	<ul>
            <li>快递公司编号：<input type="text" value="${params['lgcNo']}" name="lgcNo"></input></li>
            <li>快递公司名称：<input type="text" value="${params['name']}" name="name"></input></li>
			      <li><span>创建日期：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li>
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
            <th width="3%" align="center">序号</th>
            <th width="10%" align="center">快递公司编号</th>
            <th width="7%" align="center">快递公司名称</th>
            <th width="7%" align="center">联系方式</th>
            <th width="6%" align="center">网址</th>
            <th width="7%" align="center">创建时间</th>
            <th width="8%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${lgcList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.lgc_no}</td>
                <td>${item.name}</td>
                <td>${item.contact}</td>
                <td>${item.website}</td>
                <td><fmt:formatDate value="${item.access_time}" type="both"/></td>
                <td align="center">
                   <a href="javascript:show('${item.id}')">详情</a>
                     <shiro:hasPermission name="DEFAULT">
	                      |  <a href="javascript:edit('${item.id}');">编辑</a>
	                  </shiro:hasPermission>
                      <shiro:hasPermission name="DEFAULT">
	                      | <a href="javascript:del('${item.id}', '${item.name}');"">删除</a>   
	                  </shiro:hasPermission>  
	                     |
                   <a href="javascript:pay_type('${item.lgc_no}');">支付方式</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${lgcList.pages}"
                     current="${lgcList.pageNum}" sum="${lgcList.total}"/>
</div>
</body>

</html>