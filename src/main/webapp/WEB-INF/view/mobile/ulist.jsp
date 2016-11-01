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
});
 
 function ureset(id,userName){
  	 if(confirm('确定重置 ['+userName+'] 用户吗？')){
  		$.ajax({url:'${ctx}/mobile/ureset',data:{'id':id,'phone':userName},success:function(msg){
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
  		$.ajax({url:'ustatus',data:{'id':id,'s':s},success:function(msg){
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
    <form:form id="trans" action="${ctx}/mobile/userlist" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>用户编号：<input type="text" value="${params['userNo']}"  name="userNo"></input></li>
            <li>联系方式：<input type="text" value="${params['phone']}" name="phone"></input></li>
			      <li><span>注册时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d %H:%m\'}'})"   type="text"  style="width:112px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d %H:%m'})"  type="text" style="width:112px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
			 </li>
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
  <div class="search_new"> <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/> </div> 
   </div>   <!-- tableble_search end  -->   
  
</div>


<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th width="3%" align="center">序号</th>
            <th width="10%" align="center">用户编号</th>
            <th width="7%" align="center">登陆号</th>
            <th width="10%" align="center">姓名</th>
            <th width="7%" align="center">联系方式</th>
            <th width="6%" align="center">积分</th>
            <th width="5%" align="center">账号状态</th>
            <!-- <th width="7%" align="center">头像</th> -->
            <th width="13%" align="center">注册时间</th>
            <th width="10%" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${item.user_no}</td>
                <td>${item.user_name}</td>
                 <td>${item.real_name}</td>
                <td>${item.phone}</td>
                <td>${item.jifen}</td>
                <td><u:dict name="SWITCH" key="${item.status}" /></td>
              <%--   <td> <c:if test="${empty item.head_image}">未上传</c:if>
                 <c:if test="${!empty item.head_image}"><a href="javascript:head_image('${item.head_image}');">查看</a></c:if></td> --%>
                <td><fmt:formatDate value="${item.regist_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center">
                 <shiro:hasPermission name="DEFAULT">
                <c:if test="${item.status eq 0 }"><a href="javascript:status('${item.id}','${item.user_name}',1);">启用</a></font></c:if>
                <c:if test="${item.status eq 1 }"><a href="javascript:status('${item.id}','${item.user_name}',0);">关闭</a></c:if>
                  </shiro:hasPermission>  
                     <shiro:hasPermission name="DEFAULT">
                         |
                 <a href="javascript:ureset('${item.id}','${item.user_name}');">重置密码</a>
                 </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${userList.pages}"
                     current="${userList.pageNum}" sum="${userList.total}"/>
</div>
</body>

</html>