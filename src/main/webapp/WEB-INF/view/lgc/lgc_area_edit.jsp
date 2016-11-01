<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script  type="text/javascript" src="/scripts/city_hp.js"></script> 
<style>
textarea {
border: 1px solid #cdcdcd;
overflow: auto;
font-size: 12px;
line-height: 22px;
padding: 3px 5px 0;
border-radius: 2px;
-moz-border-radius: 2px;


height: 200px;
max-height: 200px;
min-height: 200px;
width: 450px;
max-width: 450px;
min-width: 450px;
}
</style>
<script type="text/javascript">
$(function(){

	   var rareas = spit1('${lgcArea.addrArea}').split("-") ;
	   InitOption('rprovince','rcity','rarea',rareas[0],rareas[1],rareas[2]);
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		  var id = $('input[name=id]').val();
		  
		   var rprovince = 	$('select[name=rprovince]').val() ;
 		   var rcity = 	$('select[name=rcity]').val() ;
 		   var rarea = 	$('select[name=rarea]').val() ;
			   if(rprovince.length<1){
 		    	alert("请区域省信息");
 		    	$('select[name=rprovince]').focus();
 				 return false;
 			 }
 		    if(rcity.length<1){
 		   	alert("请区域市信息");
 		    	$('select[name=rcity]').focus();
 				 return false;
 			 }
 		    if(rarea.length<1){
 		   	alert("请区域区信息");
 		    	$('select[name=rarea]').focus();
 				 return false;
 			 }
		  var addrArea  = rprovince+'-'+rcity+'-'+rarea;
		  var baddr = $.trim($("#baddr").val());
		  var naddr = $.trim($("#naddr").val());
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/lgc/lgc_area_save",//要访问的后台地址
	            data: {'id':id,'addrArea':addrArea,'baddr':baddr,'naddr':naddr},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
                  if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else{
	            		alert('数据有误');
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
		 		<input  type="hidden" id="id" name="id" value="${lgcArea.id }"/>
	 			<ul  style="padding-top:20px;padding-left: 0px;">
		 			<li style="width:650px"><span  class="lleft">&nbsp;&nbsp;区域：</span>
		 			
		 <select name="rprovince" style="width: 22%;" id="rprovince" <c:out value="${!empty lgcArea.id?'disabled':'' }"/> onchange="javascript:ChangeProvince('rcity',this.options[this.selectedIndex].value)"></select>
         <select name="rcity" style="width:22%;margin-left: 2%;" id="rcity" <c:out value="${!empty lgcArea.id?'disabled':'' }"/> onchange="javascript:ChangeCity('rarea',this.options[this.selectedIndex].value)"></select>
          <select name="rarea"  style="width:22%;margin-left: 2%;" id="rarea" <c:out value="${!empty lgcArea.id?'disabled':'' }"/> onchange="javascript:ChangeArea()"></select>
		 			</li>
                  <li style="width:650px"><span  class="lleft">&nbsp;&nbsp;收派范围：</span>
		 				  <textarea name="sarea" id="baddr"  placeholder="请输入收派范围,以逗号分隔">${lgcArea.baddr}</textarea>
		 			</li>
		 			<li style="width:650px"><span  class="lleft">&nbsp;&nbsp;不收派范围：</span>
		 				  <textarea name="sarea" id="naddr"  placeholder="请输入不收派范围,以逗号分隔">${lgcArea.naddr}</textarea>
		 			</li>
                </li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px;padding-left: 200px;">
	 		<input class="button input_text  big blue_flat" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>

 <script>
  $(function() {
	  
  
  });
  </script>

</html>