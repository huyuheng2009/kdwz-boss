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
			 $.dialog({lock: true,title:'新增品名',drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/matter/pro_edit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
		 $('#reload_btn').click(function(){
			 location.reload();
		});
	});

	
	function edit(id){
		 $.dialog({lock: true,title:'编辑品名',drag: false,width:650,height:400,resize: false,max: false,content: 'url:${ctx}/matter/pro_edit?id='+id+'&layout=no',close: function(){
			 location.reload();
	 	 }});
	}
	
	function del(id,name){
		 if(confirm('确定删除 ['+name+'] 品名吗？')){
			 $.ajax({url:'prodel',data:{'id':id},success:function(msg){
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
    <form:form id="trans" action="${ctx}/matter/matter_pro" method="get">
    	<ul>
            <li>物料编号：<input type="text" value="${params['matterNo']}" name="matterNo"></input></li>
            <li>物料品名：<input type="text" value="${params['matterName']}" name="matterName"></input></li>
            <li>物料类型：<u:select stype="MATTER_TYPE" sname="matterType" value="${params['matterType']}"/></li>
			 <%-- <li><span>创建日期：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li> --%>
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
            <th  align="center">序号</th>
            <th align="center">物料编号</th>
            <th align="center">物料品名</th>
            <th align="center">物料类型</th>
            <th align="center">单位</th>
            <th align="center">采购价</th>
            <th align="center">销售价</th>
            <th align="center">备注</th>
            <th align="center">所属公司</th>
            <th align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.matter_no}</td>
                <td>${item.matter_name}</td>
                <td><u:dict name="MATTER_TYPE" key="${item.matter_type}"/></td>
                <td>${item.matter_unit}</td>
                <td>${item.matter_price}</td>
                <td>${item.matter_sale_price}</td>
                <td>${item.note}</td>
                <td>${item.name}</td>
                <td align="center">
                     <shiro:hasPermission name="DEFAULT">
	                      <a href="javascript:edit('${item.id}');">编辑</a>
	                  </shiro:hasPermission>
                      <shiro:hasPermission name="DEFAULT">
	                      | <a href="javascript:del('${item.id}', '${item.matter_name}');"">删除</a>   
	                  </shiro:hasPermission>  
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