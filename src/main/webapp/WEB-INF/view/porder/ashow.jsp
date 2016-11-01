<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    $(function(){
   	 $('#submit').click(function(){
   		 var api = frameElement.api, W = api.opener;
   		descb = $('input[name=descb]').val();
   		
   		 orderNo = $('input[name=orderNo]').val();
   		 lgcOrderNo = $('input[name=lgcOrderNo]').val();
   		 proType = $('select[name=proType]').val();
   		if(proType.length<1){
  			 alert('请选择类型');
  			 return false;
  		 }
   	 if(descb.length<2){
			 alert('请输入详细原因');
			 return false;
		 }
   		 $.ajax({
   			 type: "post",//使用get方法访问后台
   	            dataType: "json",//返回json格式的数据
   	            url: "${ctx }/porder/psave",//要访问的后台地址
   	            data: {'orderNo':orderNo,'lgcOrderNo':lgcOrderNo,'proReason':proType,'descb':descb},//要发送的数据
   	            success: function(msg){//msg为返回的数据，在这里做数据绑定
   	            	if(msg.ret==1){
   	            		alert('操作成功');
   	            		api.reload();
						api.close();
   	            	}else{
   	            		alert(msg.ret);
   	            	}
   	            }
   		 });
   	 });
   	
   }); 
        
    </script>
</head>
<body>
<div class="search">
    <form:form id="trans" action="${ctx}/porder/add" method="get">
        <ul  style="margin:10px 30px;"> 
            <input type="hidden" name="orderNo" value="${params['orderNo']}" />
            <li>快件单号：<input type="text" value="${params['lgcOrderNo']}" name="lgcOrderNo" disabled="disabled" style="width: 280px"></input></li>
            <li>问题原因：
            <u:select id="proType" sname="proType" stype="PRO_REASON" value="OTHER"/></li>
			 <li>详细原因：<input type="text"  name="descb" style="width: 280px"></input></li>		
             <input type="hidden" name="layout" value="no"/>
            <li><input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/>
            </li>
        </ul>
    </form:form>
    <div class="clear"></div>
</div>


</body>

</html>