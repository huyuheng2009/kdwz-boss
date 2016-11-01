<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
</style>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 id = $('input[name=id]').val();
		 lgcNo = $('select[name=lgcNo]').val()
		 matterNo = $.trim($('input[name=matterNo]').val());
		 matterName = $.trim($('input[name=matterName]').val());
		 matterType = $.trim($('input[name=matterType]').val());
		 matterUnit = $.trim($('input[name=matterUnit]').val());
		 matterPrice = $.trim($('input[name=matterPrice]').val());
		 matterSalePrice = $.trim($('input[name=matterSalePrice]').val());
		 note = $.trim($('input[name=note]').val());
		 
		 if(lgcNo.length<2){
			 alert('请选择快递公司');
			 return false;
		 }
		 if(matterNo.length<2){
			 alert('请输入物料编号');
			 return false;
		 }
		 
		 if(matterName.length<2){
			 alert('请输入物料品名');
			 return false;
		 }
		 
		 if(matterType.length<2){
			 alert('请输入物料类型');
			 return false;
		 }
		 
		 if(matterUnit.length<1){
			 alert('请输入单位');
			 return false;
		 }
		 if(!reg[6].test(matterPrice)){
			 alert('请输入正确的采购价');
			 return false;
		 }

		 if(matterSalePrice.length>0&&!reg[6].test(matterSalePrice)){
			 alert('请输入正确的销售价');
			 return false;
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/matter/pro_save",//要访问的后台地址
	            data: {'id':id,'lgcNo':lgcNo,'matterNo':matterNo,'matterName':matterName,'matterType_':matterType,'matterUnit':matterUnit,'matterPrice':matterPrice,
	            	'matterSalePrice':matterSalePrice,'note':note},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${matterPro.id }"/>
	 			<ul style="padding-top:30px;padding-left: 25px;">
	 			   <li style="width:450px">所属公司：
		 			<select id="lgcNo" name="lgcNo" style="width: 300px" <c:out value="${!empty matterPro.id?'disabled':'' }"/> >
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option   value="${item.lgc_no }"  <c:out value="${matterPro.lgcNo eq item.lgc_no?'selected':'' }"/>>${item.name }</option>
							</c:forEach>
					</select><span style="color: red;">★</span>
					</li>
		 			<li style="width:450px">物料编号：
		 				<input  maxlength="20" type="text" name="matterNo" value="${matterPro.matterNo }" <c:out value="${!empty matterPro.id?'disabled':'' }"/> style="width: 300px"></input>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			<li style="width:450px">物料品名：
		 				<input  maxlength="20" type="text" name="matterName" value="${matterPro.matterName }" style="width: 300px"></input>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			<li style="width:450px">物料类型：
		 				<input  maxlength="20" type="text" name="matterType" value="<u:dict name="MATTER_TYPE" key="${matterPro.matterType }"/>" style="width: 300px"></input>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			<li style="width:450px">&#12288;&#12288;单位：
		 				<input  maxlength="20" type="text" name="matterUnit" value="${matterPro.matterUnit }" style="width: 300px"></input>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			<li style="width:450px">&#12288;采购价：
		 				<input  maxlength="20" type="text" name="matterPrice" value="${matterPro.matterPrice }" style="width: 300px"></input>
		 				<span style="color: red;">★</span>
		 			</li>
		 			<li style="width:450px">&#12288;销售价：
		 				<input  maxlength="20" type="text" name="matterSalePrice" value="${matterPro.matterSalePrice }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px">&#12288;&#12288;备注：
		 				<input  maxlength="20" type="text" name="note" value="${matterPro.note }" style="width: 300px"></input>
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