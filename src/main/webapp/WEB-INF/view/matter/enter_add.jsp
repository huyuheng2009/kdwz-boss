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
		 var substationNo = $.trim($('select[name=substationNo]').val());
		 var matterId = $.trim($('select[name=matterId]').val());
		 var bplaneNo = $.trim($('input[name=bplaneNo]').val());
		 var eplaneNo = $.trim($('input[name=eplaneNo]').val());
		 var matterPrice = $.trim($('input[name=matterPrice]').val());
		 var mcount = $.trim($('input[name=mcount]').val());
		 var macount = $.trim($('input[name=macount]').val());
		 var brokerage = $.trim($('input[name=brokerage]').val());
		 var supplier = $.trim($('input[name=supplier]').val());
		 var wareTime = $.trim($('input[name=wareTime]').val());
		 var note = $.trim($('input[name=note]').val());
		 
		 if(substationNo.length<2){
			 alert('请选择分站');
			 return false;
		 }
		 if(matterId.length<2){
			 alert('请选择物料品名');
			 return false;
		 }
		 if(!reg[6].test(matterPrice)){
			 alert('请输入正确的单价');
			 return false;
		 }
		 if(!reg[5].test(mcount)){
			 alert('请输入正确的数量');
			 return false;
		 }
		 if(!reg[6].test(macount)){
			 alert('请输入正确的总金额');
			 return false;
		 }
		 
		 if(brokerage.length<1){
			 alert('请输入经手人');
			 return false;
		 }
		 if(wareTime.length<1){
			 alert('请输入入库时间');
			 return false;
		 }
		
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/matter/enter_add_save",//要访问的后台地址
	            data: {'substationNo':substationNo,'matterId':matterId,'bplaneNo':bplaneNo,'eplaneNo':eplaneNo,'matterPrice':matterPrice,
	            	  'mcount':mcount,'macount':macount,'macount':macount,'brokerage':brokerage,
	            	  'supplier':supplier,'wareTime':wareTime,'note':note},   //要发送的数据
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
	 			   <li style="width:380px">所属分站：
	 			   <select id="substationNo" name="substationNo" style="width: 210px">
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
							</c:forEach>
					</select><span style="color: red;">★</span>
					</li>
					<li style="width:300px">&#12288;经手人：
		 				<input  maxlength="20" type="text" name="brokerage" style="width: 210px"></input>
		 			    <span style="color: red;">★</span>
		 			</li>
					
		 			<li style="width:380px">物料品名：
		 			<u:select stype="MATTER_NAME" def="none" sname="matterId" style="width: 210px" value=""/>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			<li style="width:300px">&#12288;供货商：
		 				<input  maxlength="20" type="text" name="supplier" style="width: 210px"></input>
		 			</li>
		 			
		 			<li style="width:380px">开始单号：
		 				<input  maxlength="20" type="text" name="bplaneNo"  style="width: 210px"></input>
		 			</li>
		 			 
		 			<li style="width:300px">入库时间：
		 				<input  type="text" name="wareTime" style="width: 210px" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'%y-%M-%d %H:%m'})"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
		 			    <span style="color: red;">★</span>
		 			</li>
		 			
		 			<li style="width:380px">结束单号：
		 				<input  maxlength="20" type="text" name="eplaneNo" style="width: 210px"></input>
		 			</li>
		 			<li style="width:300px">&#12288;&#12288;备注：
		 				<input  maxlength="20" type="text" name="note" style="width: 210px"></input>
		 			</li>
		 			
		 			<li style="width:380px">&#12288;&#12288;单价：
		 				<input  maxlength="20" type="text" name="matterPrice"  style="width: 210px"></input>
		 				<span style="color: red;">★</span>
		 			</li>
		 			<li style="width:380px">&#12288;&#12288;数量：
		 				<input  maxlength="20" type="text" name="mcount"  style="width: 210px"></input>
		 				<span style="color: red;">★</span>
		 			</li>
		 			<li style="width:380px">&#12288;总金额：
		 				<input  maxlength="20" type="text" name="macount" style="width: 210px"></input>
		 				<span style="color: red;">★</span>
		 			</li>

	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px;margin-left: 100px;">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>