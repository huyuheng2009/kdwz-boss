<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    $(function(){
   	 $('#submit').click(function(){
   		 var api = frameElement.api, W = api.opener;
   		notes1 = $('input[name=notes1]').val();
   		notes2 = $('input[name=notes2]').val();
   		notes3 = $('input[name=notes3]').val();
   		notes = notes1 + ',' + notes2 +','+notes3
   		 orderNo = $('input[name=orderNo]').val();
   		 $.ajax({
   			 type: "post",//使用get方法访问后台
   	            dataType: "text",//返回json格式的数据
   	            url: "${ctx }/porder/pro_edit_save",//要访问的后台地址
   	            data: {'orderNo':orderNo,'notes':notes},//要发送的数据
   	            success: function(msg){//msg为返回的数据，在这里做数据绑定
   	            	if(msg==1){
   	            		alert('操作成功');
   	            		api.reload();
						api.close();
   	            	}else{
   	            		alert(msg);
   	            	}
   	            }
   		 });
   	 });
   	
   }); 
        
    </script>
</head>
<body>
<div class="search">
    <form:form id="trans" action="${ctx}/porder/pro_text_save" method="get">
        <ul  style="margin:10px 30px;"> 
            <input type="hidden" name="orderNo" value="${params['orderNo']}" />
            <li>客户记录1：<input type="text" value="${fn:split(pOrder.notes,",")[0]}" name="notes1" maxlength="50" style="width: 280px"></input></li>	
            <li>客户记录2：<input type="text" value="${fn:split(pOrder.notes,",")[1]}" name="notes2" maxlength="50" style="width: 280px"></input></li>	
            <li>客户记录3：<input type="text" value="${fn:split(pOrder.notes,",")[2]}" name="notes3" maxlength="50" style="width: 280px"></input></li>		
             <input type="hidden" name="layout" value="no"/>
            <li><input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/>
            </li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>


</body>

</html>