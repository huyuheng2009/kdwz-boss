<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/zh-html.tld" prefix="zh"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
		<jsp:include page="/WEB-INF/view/batch/head.jsp" />
			<script>
var $jqq = jQuery.noConflict(true);
</script> 
		<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
		<link  rel="stylesheet" href="<%=request.getContextPath()%>/themes/default/jquery.autocomplete.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.6.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	
	
		   
       

	function add(){
		 var api = frameElement.api, W = api.opener;
		var lgcOrderNo  = $('input[name=lgcOrderNo]').val();
		var orderNote  = $('textarea[name=orderNote]').val();
			
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回文本格式
	            url: "<%=request.getContextPath()%>/substatic/addNote",//要访问的后台地址
	            data: {'lgcOrderNo':lgcOrderNo,
	            	'note':orderNote
	            },//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定                   	
	            		alert("添加备注成功");
	            		api.reload();
						api.close();
	            	
	            }
		 });
	}
	
	
	function cacel(){
		 var api = frameElement.api, W = api.opener;
		 api.close();
	}
	function deleteAll(){		
		 $('textarea[name=orderNote]').val("");			
	}
	</script>
		<style>
	
		.out_button{width:600px; text-align:center;      margin: 20px;}
		.out_button .button{ float:inherit; display:inline-block;   margin: 0 20px 0 0;}
		.out_button .button_2{ float:inherit; display:inline-block;}
		</style>
    </head>
    <body>
    
		<form id="myform">
						<div class="input_li">
							<input type="hidden" name="lgcOrderNo" value="${params.lgcOrderNo}" />
							<div class="input_name"><b></b>备注</div>
					
							<div class="input_wri"><textarea type="text" name="orderNote"  style="width:400px;height:100px;	"  value="${params.orderNote}">${params.orderNote}</textarea>        
						</div>  
						</div>
				<div class="out_button">
					<a href="javascript:void(0);" onclick="add()" class="button">保存</a>
					<a href="javascript:void(0);" onclick="deleteAll();" class="button">重置</a>
					<a href="javascript:void(0);" onclick="cacel();" class=" button_2">取消</a>	
				</div>
			
		</form>
	</body>
</html>
