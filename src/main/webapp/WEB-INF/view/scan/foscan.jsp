<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 curLgcOrderNo = $('input[name=curLgcOrderNo]').val();
		 if(curLgcOrderNo.length<2){
			 alert('请输入转出前运单号');
			 return false;
		 }
		 ioName = $('input[name=ioName]').val();
		 if(ioName.length<2){
			 alert('请输入转出后快递公司名称');
			 return false;
		 }
		 ioLgcOrderNo = $('input[name=ioLgcOrderNo]').val();
		 if(ioLgcOrderNo.length<2){
			 alert('请输入转出后运单号');
			 return false;
		 }
		 lgcNo = $('select[name=lgcNo]').val();
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/scan/foscan_save",//要访问的后台地址
	            data: {'lgcNo':lgcNo,'ioName':ioName,'ioLgcOrderNo':ioLgcOrderNo,'curLgcOrderNo':curLgcOrderNo},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功');
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
	<div class="content">
		 	<div class="item">
	 			<ul style="padding-top: 30px;padding-left: 30px;">
	 			   <li>转出前快递公司：
		 				<select id="lgcNo" name="lgcNo" style="width: 110px">
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }"/> ${item.name }</option>
							</c:forEach>
					</select>
		 			</li>
		 			<li style="width:450px">转出前运单号：
		 				<input  maxlength="30" type="text" name="curLgcOrderNo"  style="width: 150px"></input>
		 			</li>
		 			<li style="width:450px">转出后快递公司：
		 				<input  maxlength="30" type="text" name="ioName" style="width: 150px"></input>
		 			</li>
		 			<li style="width:450px">转出后运单号：
		 				<input  maxlength="20" type="text" name="ioLgcOrderNo" style="width: 150px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>