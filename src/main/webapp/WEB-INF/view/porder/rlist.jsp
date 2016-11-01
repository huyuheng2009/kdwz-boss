<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
      
        function edit(id){
        	$.dialog({lock: true,title:'问题原因编辑',drag: true,width:650,height:300,resize: false,max: false,content: 'url:${ctx}/porder/redit?id='+id+'&layout=no',close: function(){
        	}});
        }
    
        $(function () {
            trHover('t2');
         $('#reload_btn').click(function(){
        	    //location.reload()
        	 $("form:first").submit();
       	});
         
    	 $('#add').click(function(){
			 $.dialog({lock: true,title:'新增问题原因',drag: false,width:650,height:300,resize: false,max: false,content: 'url:${ctx}/porder/redit?layout=no',close: function(){
				 location.reload();
		  	 }});
		 });
            
    });
        
   function stop(id,context){	   
	   if(confirm("你确定要停用问题原因【"+context+"】么？")){
		   $.ajax({
				  url:'pstop',
				  dataType:'text',
				  data:{
					  'id':id
				  },
				  success:function(msg){			
					  if(msg==1){			
							 location.reload();				  
					  }else{
						  alert("停用失败");
					  }		  
				  } 			   
			   });	    
	   }	  
   }     
   function start(id,context){	   
	   if(confirm("你确定要启用问题原因【"+context+"】么？")){
		   $.ajax({
				  url:'pstart',
				  dataType:'text',
				  data:{
					  'id':id
				  },
				  success:function(msg){
					  if(msg==1){			
						 location.reload();				  
					  }else{
						  alert("启用失败");
					  }		  
				  } 			   
			   });	    
	   }	  
   }     

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/porder/rlist" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>问题件原因：<input type="text" value="${params['context']}" placeholder="问题件原因" name="context"></input></li>
            <li><input class="button input_text  medium blue_flat" type="submit" value="查询"/></li>
            <li><input class="button input_text  medium blue_flat" type="reset" value="重置"/></li>
        </ul>
    </form:form>
   </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- tableble_search end  -->   
  <div class="tableble_search">
  <div class="operator">
	     <div class="search_new"><input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>   </div>
	     <shiro:hasPermission name="DEFAULT">
         <div class="search_new"><input id="add" class="button input_text  big gray_flat" type="submit" value="新增" /> </div>
	</shiro:hasPermission> 
	    </div>
     </div>   <!-- tableble_search end  -->   
</div>


<div class="tbdata">
    	 
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center" >序号</th>
            <th align="center" >问题件编号</th>
            <th align="center" >问题件原因</th>
            <%--
            <th align="center" >是否需要客服处</th>
             --%>
            <th align="center" >备注</th>
            <th align="center" >操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1 ' : ''} <c:out value="${item.status eq '0'?'deled':''}"></c:out>">
               <td align="center">${status.count}</td>
               <td>${item.reason_no}</td>
               <td>${item.context}</td>
               <%--
               <td><c:out value="${item.dealed ne 'Y'?'否':'是'}"></c:out></td>
                --%>
               <td>${item.note}</td>
               <td align="center">
		           <shiro:hasPermission name="DEFAULT">
                           <a href="javascript:edit('${item.id}');">编辑</a>
                  </shiro:hasPermission>           
                  <c:if test="${item.status == 1}"> <a href="javascript:stop('${item.id}','${item.context}');">停用</a></c:if>   
                  <c:if test="${item.status == 0}"><a href="javascript:start('${item.id}','${item.context}');">启用</a></c:if>    
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