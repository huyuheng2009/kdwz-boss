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
        	 $.dialog({lock: true,title:'代收货款客户新增',drag: true,width:680,height:720,resize: false,max: false,content: 'url:${ctx}/mobile/cuser_edit?layout=no',close: function(){
        	 }});
         	});
});
 
 
 function mshow(id){
	 $.dialog({lock: true,title:'代收货款客户详情',drag: true,width:550,height:620,resize: false,max: false,content: 'url:${ctx}/mobile/cuser_show?id='+id+'&layout=no',close: function(){
	 }});
  }
 
 function medit(id){
	 $.dialog({lock: true,title:'代收货款客户编辑',drag: true,width:680,height:720,resize: false,max: false,content: 'url:${ctx}/mobile/cuser_edit?id='+id+'&layout=no',close: function(){
	 }});
  }
 
 function status(id,s){
		tip='';
		if(s==0){
			tip='禁用';
		}else{
			tip='启用';
		}
		if(confirm('确定要'+tip+'此用户吗?')){
			$.ajax({url:'status_cuser',data:{'id':id,'s':s},success:function(msg){
				 if(msg==1){
					 alert('修改成功');
					 location.reload();
				 }
			 }});
		}
	}

  function del(id){
  	if(confirm('确定要删除此代收货款客户吗?')){
  		$.ajax({url:'/userManager/del_cuser',data:{'id':id},success:function(msg){
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
    <form:form id="trans" action="${ctx}/mobile/cuser_list" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>代收货款号：<input type="text" value="${params['codNo']}"  name="codNo"></input></li>
            <li>公司/个人：<input type="text" value="${params['codName']}" name="codName"></input></li>
                <li><span>创建时间：</span>
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
  <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div>  
     <shiro:hasPermission name="DEFAULT">
  <div class="search_new">  <input class="button input_text  big gray_flat" type="submit" value="新增" id="add"/> </div>  
      </shiro:hasPermission>
  </div>   <!-- tableble_search end  -->    
</div>


<div class="tbdata">
    	 
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">代收货款号</th>
            <th  align="center">公司简称</th>
           <th  align="center">费率类型</th> 
            <th align="center">联系人</th>
            <th  align="center">联系电话</th>
            <th  align="center">财务电话</th>
            <th  align="center">所属快递员</th>
            <th  align="center">所属网点</th>
              <th  align="center">市场员</th>
            <th align="center">结算方式</th>
            <th  align="center">结算日期</th>
            <th  align="center">邮箱</th>
            <th  align="center">注册时间</th>
            <th width="10%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.cod_no}</td>
                <td>${item.cod_sname}</td>
                <td><u:dict name="CSTYPE" key="${item.cstype}" /></td> 
                 <td>${item.contact_name}</td>
                <td>${item.contact_phone}</td>
                <td>${item.settle_phone}</td>
                <td><u:dict name="C" key="${item.courier_no}" /></td>
                <td><u:dict name="S" key="${item.substation_no}" /></td>
                  <td>${item.mark_name}</td>
                <td>${item.settle_type}</td>
                <td>${item.settle_date}</td>
                 <td>${item.email}</td>
                <td><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center">
                <a href="javascript:mshow('${item.id}');">详情</a>
                <shiro:hasPermission name="DEFAULT">
                         |
                <a href="javascript:medit('${item.id}');">编辑</a>
                </shiro:hasPermission>
                  <shiro:hasPermission name="DEFAULT">
                         |
                 <a href="javascript:del('${item.id}');">删除</a>
                 </shiro:hasPermission>
                         |
                	<c:if test="${item.status eq 0 }">
									<a href="javascript:status('${item.id}',1);">启动</a>
					</c:if>
					<c:if test="${item.status eq 1 }">
									<a href="javascript:status('${item.id}',0);">禁用</a>
					</c:if>
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