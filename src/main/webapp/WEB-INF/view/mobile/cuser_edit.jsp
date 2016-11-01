<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
.lleft{width: 120px;text-align: right;float: left;}
</style>
<script type="text/javascript">
$(function(){
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var id = $('input[name=id]').val();
		
		 
		 var codNo = $.trim($('input[name=codNo]').val());
		 var codName = $.trim($('input[name=codName]').val());
		 var codSname = $.trim($('input[name=codSname]').val());
		 var cstype = $('select[name=cstype]').val();
		 
		 var contactName = $.trim($('input[name=contactName]').val());
		 var contactPhone = $.trim($('input[name=contactPhone]').val());
		 var contactAddr = $.trim($('input[name=contactAddr]').val());
		 
		 var settlePhone = $.trim($('input[name=settlePhone]').val());
		 var substationNo = $('select[name=substationNo]').val();
		 var courierNo = $('select[name=courierNo]').val();
		 var markName = $.trim($('input[name=markName]').val());
		 
		 
		 var settleType = $.trim($('input[name=settleType]').val());
		 var settleCardNo = $.trim($('input[name=settleCardNo]').val());
		 var settleBank = $.trim($('input[name=settleBank]').val());
		 var bankCpns = $.trim($('input[name=bankCpns]').val());
		 var settleDate = $.trim($('input[name=settleDate]').val());
		 
		 var bizLic = $.trim($('input[name=bizLic]').val());
		 var email = $.trim($('input[name=email]').val());
		 var liced = $('select[name=liced]').val();
		 var note = $.trim($('input[name=note]').val());
		
		 var p2 = /^[0-9]{10}$/ ;
		 if(!p2.test(codNo)){
			 alert('请输入正确的用户号');
			 return false;
		 }
		 
		 if(codName.length<2){
			 alert('请输入公司名称');
			 return false;
		 }
		 if(codSname.length<2||codSname.length>8){
			 alert('请输入正确的公司简称');
			 return false;
		 }
		 if(cstype.length<1){
			 alert('请选择费率类型');
			 return false;
		 } 
		 
		 if(!checkPhone(contactPhone,'Y')){
			 alert('请输入正确的联系电话');
			 return false;
		 }
		 
		 if(!checkPhone(settlePhone,'Y')){
			 alert('请输入正确的财务电话');
			 return false;
		 }
		 
		 if(settleType.length<2){
			 alert('请输入正确的结算方式');
			 return false;
		 }
		 
	 var filter = /^\s*([A-Za-z0-9_-]+(\.\w+)*@(\w+\.)+\w{2,3})\s*$/;
	         if(settleDate.length>0&&!filter.test(email)){
	             alert("邮箱格式错误");
	             return false;
	         }
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/mobile/cuser_save",//要访问的后台地址
	            data: {'mid':id,'substationNo':substationNo,'codNo':codNo,'codName':codName,'contactName':contactName,'contactPhone':contactPhone,'bizLic':bizLic,
	            	'settleType':settleType,'settleCardNo':settleCardNo,'settleDate':settleDate,'email':email,'settleBank':settleBank,'note':note,'bankCpns':bankCpns,
	            	'codSname':codSname,'contactAddr':contactAddr,'settlePhone':settlePhone,'courierNo':courierNo,'markName':markName,'liced':liced,'cstype':cstype},//要发送的数据
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
		 		<input  type="hidden" id="id" name="id" value="${cuserMap.id }"/>
	 			<ul style="padding-top:30px;padding-left: 25px;">
		 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>代收货款用户号：</span>
		 				<input  maxlength="20" type="text" name="codNo"  placeholder="10位纯数字" value="${cuserMap.cod_no }" <c:out value="${!empty cuserMap.cod_no?'disabled':'' }"/> style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>公司名称：</span>
		 				<input  maxlength="20" type="text" name="codName"  placeholder="请输入公司名称"  value="${cuserMap.cod_name }" style="width: 300px"></input>
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>公司简称：</span>
		 				<input  maxlength="20" type="text" name="codSname"  placeholder="请输入公司简称"  value="${cuserMap.cod_sname }" style="width: 300px"></input>
		 			</li>
		 			
		 			  <li style="width:450px;"><span  class="lleft"><span style="color: red;">*&nbsp;</span>费率类型：</span>
		 			<select id="cstype" name="cstype" style="width: 300px">
		 			  <option value="">请选择</option>
							<c:forEach items="${cslist}" var="item" varStatus="status">
								<option   value="${item.id }"  <c:out value="${cuserMap.cstype eq item.id?'selected':'' }"/>>${item.name}</option>
							</c:forEach>
					</select>
					</li>
		 			
		 			<li style="width:450px"><span  class="lleft">联系人：</span>
		 				<input  maxlength="20" type="text" name="contactName"  placeholder="请输入联系人"  value="${cuserMap.contact_name }" style="width: 300px">
		 			</li>
		 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>联系电话：</span>
		 				<input  maxlength="20" type="text" name="contactPhone"  placeholder="请输入联系电话"  value="${cuserMap.contact_phone }" style="width: 300px"></input>
		 			</li>
		 			
		 			<li style="width:450px"><span  class="lleft">客户地址：</span>
		 				<input  maxlength="20" type="text" name="contactAddr" value="${cuserMap.contact_addr }" style="width: 300px"></input>
		 			</li>
		 			
		 				<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>财务电话：</span>
		 				<input  maxlength="20" type="text" name="settlePhone" value="${cuserMap.settle_phone }" style="width: 300px"></input>
		 			</li>
		 			
		 			  <li style="width:450px"><span  class="lleft">所属快递员：</span>
		 			<select id="courierNo" name="courierNo" style="width: 300px">
		 			  <option value="">请选择</option>
							<c:forEach items="${couriers}" var="item" varStatus="status">
								<option   value="${item.courier_no }"  <c:out value="${cuserMap.courier_no eq item.courier_no?'selected':'' }"/>>${item.real_name }</option>
							</c:forEach>
					</select>
					</li>
					
					  <li style="width:450px"><span  class="lleft">所属网点：</span>
		 			<select id="substationNo" name="substationNo" style="width: 300px">
		 			<option value="">请选择</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option   value="${item.substation_no }" id="${item.substation_name }" <c:out value="${cuserMap.substation_no eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
					</select>
					</li>
					
					<li style="width:450px"><span  class="lleft">市场员：</span>
		 				<input  maxlength="20" type="text" name="markName" value="${cuserMap.mark_name }" style="width: 300px"></input>
		 			</li>
		 			
		 			
		 			<li style="width:450px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>结算方式：</span>
		 				<input  maxlength="20" type="text" name="settleType" value="${cuserMap.settle_type }" style="width: 300px"></input>
		 			</li>
		 			 <li style="width:450px"><span  class="lleft">结算银行：</span>
		 				<input  maxlength="20" type="text" name="settleBank" value="${cuserMap.settle_bank }" style="width: 300px"></input>
		 			</li>  
		 			<li style="width:450px"><span class="lleft">结算户名：</span>
		 				<input  maxlength="20" type="text" name="settleCardNo" value="${cuserMap.settle_card_no }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span class="lleft">银行联行号：</span>
		 				<input  maxlength="20" type="text" name="bankCpns" value="${cuserMap.bank_cpns }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft">结算日期：</span>
		 				<input  maxlength="20" type="text" name="settleDate" value="${cuserMap.settle_date }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft">营业执照/身份证号：</span>
		 				<input  maxlength="20" type="text" name="bizLic" value="${cuserMap.biz_lic }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft">电子邮箱：</span>
		 				<input  maxlength="20" type="text" name="email" value="${cuserMap.email }" style="width: 300px"></input>
		 			</li>
		 			<li style="width:450px"><span  class="lleft">是否开票：</span>
		 				<select name="liced" style="width: 110px;">
                               <option value="Y" ${params['liced'] eq 'Y'?'selected':''}>是</option>
                               <option value="N" ${params['liced'] eq 'N'?'selected':''}>否</option>
                          </select>
		 			</li>
		 			
		 			 
                    <li style="width:450px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note" value="${cuserMap.note }" style="width: 300px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>