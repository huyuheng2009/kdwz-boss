<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript">
$(function(){
	
	 var lgcNo =  $('select[name=lgcNo]').val() ;  
  	$("."+lgcNo).css("display","block");
  	
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 ioName = $.trim($('input[name=ioName]').val());
		 if(ioName.length<2){
			 alert('请输入转入前快递公司名称');
			 return false;
		 }
		 
		 ioLgcOrderNo = $.trim($('input[name=ioLgcOrderNo]').val());
		 if(ioLgcOrderNo.length<2||ioLgcOrderNo.length>18){
			 alert('请输入正确的转入前快递公司运单号');
			 return false;
		 }
		 
		 curLgcOrderNo = $.trim($('input[name=curLgcOrderNo]').val());
		 if(curLgcOrderNo.length<2||curLgcOrderNo.length>18){
			 alert('请输入正确的转入后快递公司运单号');
			 return false;
		 }
		 lgcNo = $('select[name=lgcNo]').val();
		 substationNo = $('select[name=substationNo]').val();
		 if(substationNo.length<1){
    		 alert("请选择分站");
    	      return false ;	   
    	 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/scan/fiscan_save",//要访问的后台地址
	            data: {'lgcNo':lgcNo,'substationNo':substationNo,'ioName':ioName,'ioLgcOrderNo':ioLgcOrderNo,'curLgcOrderNo':curLgcOrderNo},//要发送的数据
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

function chl(lgcNo){
    $('#substationNo').prop('selectedIndex', 0);
     $(".s_none").css("display","none");
	$("."+lgcNo).css("display","block");
} ;   


</script>
</head>
<body>
	<div class="content">
		 	<div class="item">
	 			<ul style="padding-top: 30px;padding-left: 30px;">
		 			<li style="width:450px">转入前快递公司：
		 				<input  maxlength="30" type="text" name="ioName"  style="width: 128px"></input>
		 			</li>
		 			<li style="width:450px">转入前运单号：
		 				<input  maxlength="30" type="text" name="ioLgcOrderNo" style="width: 128px"></input>
		 			</li>
		 			<li style="width:500px">转入后快递公司：
		 				<select id="lgcNo" name="lgcNo" style="width: 110px" onchange="chl(this.value);">
							<c:forEach items="${lgcs}" var="item" varStatus="status">
								<option value="${item.lgc_no }"/> ${item.name }</option>
							</c:forEach>
					</select>
					当前站点：<select id="substationNo" name="substationNo" style="width: 220px">
					 <option value="">请选择</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option style="display: none;"  value="${item.substation_no }" id="${item.substation_name }"  class="s_none ${item.lgc_no}">${item.substation_name }</option>
							</c:forEach>
					</select>
		 			</li>
		 			<li style="width:450px">转入后运单号：
		 				<input  maxlength="20" type="text" name="curLgcOrderNo" style="width: 128px"></input>
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