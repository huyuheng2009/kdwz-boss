<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
 function chl(checked,lgcNo){
      	if(checked){
      		$("."+lgcNo).css("display","block");
      	}else{
      		$("."+lgcNo).css("display","none");
      		$(".in_"+lgcNo).prop('checked',false); 
      	}
 } ;

$(function(){
	
	
  	jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {
 
         	 var slist =  tmjs.slist ;
         	var slists = [];
             $.each(slist, function(i, item) {
             	var inner_no = "" ;
             	if(item.inner_no){inner_no=item.inner_no+','}
             	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
             });
		     $jqq("#sno").autocomplete(slists, {
		  		minChars: 0,
		  		max: 12,
		  		autoFill: true,
		  		mustMatch: false,
		  		matchContains: true,
		  		scrollHeight: 220,
		  		formatItem: function(data, i, total) {
		  			return data[0];
		  		}
		  	}).result(function(event, data, formatted) {
				if(data[0].indexOf(')')>-1){
					 $("#sno").val(data[0].substring(0,data[0].indexOf('('))) ;
					 $("#sname").val(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
				   } 
			});	


    });  
	
	
	
	$('input[name=lgcNo]:checked').each(function(){
		//$(this).prop('checked',true); 
		var lgcNo = $(this).val() ;
		$("."+lgcNo).css("display","block");
	});
	
	$('#cancel').click(function() {
		$('input:checkbox:checked').each(function(){
			$(this).prop('checked',false); 
		});
});

$('.selectAll1').click(function() {
	$('input[name=userGroup]').each(function(){
		$(this).prop('checked',true); 
	});
});

$('.selectDis1').click(function() {
	$('input[name=userGroup]').each(function(){
		  if($(this).prop("checked"))	{
			  $(this).prop('checked',false); 
		   }
		   else
		   {
		     $(this).prop("checked",true);
		   }
	});
});

$('.selectAll2').click(function() {
	$('input[name=lgcNo]').each(function(){
		$(this).prop('checked',true); 
		var lgcNo = $(this).val() ;
		$("."+lgcNo).css("display","block");
	});
});

$('.selectDis2').click(function() {
	$('input[name=lgcNo]').each(function(){
		  if($(this).prop("checked"))	{
			  $(this).prop('checked',false); 
			  var lgcNo = $(this).val() ;
				$("."+lgcNo).css("display","none");
		   }
		   else
		   {
		     $(this).prop("checked",true);
		     var lgcNo = $(this).val() ;
				$("."+lgcNo).css("display","block");
		   }
	});
});

$('.selectAll3').click(function() {
	$('input[name=substationNo]').each(function(){
		$(this).prop('checked',true); 
	});
});

$('.selectDis3').click(function() {
	$('input[name=substationNo]').each(function(){
		  if($(this).prop("checked"))	{
			  $(this).prop('checked',false); 
		   }
		   else
		   {
		     $(this).prop("checked",true);
		   }
	});
});
	
	
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var userName = $.trim($('input[name=userName]').val());
         $('input[name=userName]').val(userName);
         var r = /^[a-zA-Z0-9]{2,500}$/ ;
		 if(!r.test(userName)){
			 alert('请输入正确的登录账号');
			 return false;
		 }
         var realName = $.trim($('input[name=realName]').val());
         $('input[name=realName]').val(realName);
		 if(realName.length<2){
			 alert('请输入用户姓名');
			 return false;
		 }
         var  userGroup = $("input[name=userGroup]:checked").val();
		 
		 if (typeof(userGroup) == "undefined")
		 {
			 alert('请选择角色');
			 return false;
		 }
		 
		 var clogin = false ;
		 $("input[name=userGroup]:checked").each(function(){
			  if('Y'==$(this).attr("clogin"))	{
				  clogin = true ;
			   }
		});

		 var info = "";
		 var items = $('[name = "userGroup"]:checkbox:checked');
		 for (var i = 0; i < items.length; i++) {
		      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
		      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
		 }
		 
         var filter = /^\s*([A-Za-z0-9_-]+(\.\w+)*@(\w+\.)+\w{2,3})\s*$/;
         var email = $.trim($('input[name=email]').val());
         $('input[name=email]').val(email);
         if(email!=''){
        	 if(!filter.test(email)){
                 alert("邮箱格式错误");
                 return false;
             }
         }
         
         
         var sno = $.trim($('input[name=sno]').val());
         //  $('input[name=email]').val(sno);
         
		 /* var status = $('select[name=status]').val();
		 if(status==''){
			 alert('请选择用户状态');
			 return false;
		 } */
		 if(sno==''){
			 alert("请选择网点");
			 return false;
		 }
		 var lgcNo = "";
		 var lgcNos = $('[name = "lgcNo"]:checkbox:checked');
		 for (var i = 0; i < lgcNos.length; i++) {
		      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
		      lgcNo = (lgcNo +  lgcNos.get(i).value) + (((i + 1)==  lgcNos.length) ? '':','); 
		 }
		// if(lgcNo.length<1){
		//	 alert('请选择快递公司');
		//	 return false;
		// }
		 
		 var substationNo = "";
		 var substationNos = $('[name = "substationNo"]:checkbox:checked');
		 
		 if(clogin&&substationNos.length>1){
			 alert('用户角色具有仓管员权限，只能选择一个分站');
			 return false; 
		 }
		 for (var i = 0; i < substationNos.length; i++) {
		      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
		      substationNo = (substationNo +  substationNos.get(i).value) + (((i + 1)==  substationNos.length) ? '':','); 
		 }
		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/system/usave",//要访问的后台地址
	            data: {'id':$('#id').val(),'userName':userName,'userGroup':info,'lgcNos':lgcNo,'realName':realName,'email':email,'status':1,'substationNos':substationNo,'sno':sno},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('数据有误');
	            	}
	            	if(msg==1){
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}if(msg==2){
	            		alert('登录账号或邮箱已经存在');
	            	}if(msg==3){
	            		alert('用户所属网点不存在或已被停用');
	            	}
	            }
		 });
	 });
	
}); 
 
</script>
<style type="text/css">
.label{display:inline-block;width:59px;text-align:right;}
.input{display:inline-block;width:200px;height:28px;}
</style>

</head>
<body>
	 <div class="content">
	 <form action="${ctx }/system/usave" method="post">
	 	<div class="item">
	 		 <input  type="hidden" id="id" name="id" value="${params.id }"/>
	 		<ul style="padding-top:20px">
	 			<li style="width:350px">
	 				<span class="label">
	 					登录账号:<span style="color:red;">*</span>
	 				</span>
	 			     <input style="height:26px;width:150px;"  <c:out value="${!empty params.id?'disabled':'' }"/>  placeholder="2位以上英文或数字" maxlength="15" type="text" name="userName" value="${params.userName }" ></input>
	 				用于登录后台系统
	 			</li>
	 			<li style="width:370px">
	 				<span class="label">用户姓名:<span style="color:red">*</span></span>
	 				<input  maxlength="15" type="text" name="realName" value="${params.realName }" style="width: 150px;height:26px;"></input>
	 			</li>
	 			<li style="width:350px">
		 			<span class="label">Email:</span>
		 			<input type="text" name="email" value="${params.email }" style="width: 150px;height:26px;"></input>
		 			获得修改密码信息
	 			</li>
	 			<li style="width:400px">
	 					<span class="label">所属网点:<span style="color:red">*</span></span>
	 					<input type="text" value="${params.sno}" name="sno" id="sno" style="width: 150px;height:26px;"></input>
	 			     	<input style="width: 150px;height:26px;" type="text" name="sname"  id="sname" disabled="disabled"  value='<u:dict name="S" key="${params.sno}" />' />
	 			</li>
	 			<li style="width:100px">
	 			<div class="operator"  style="padding-top: 0px;padding-bottom: 20px;">
			 		<input class="button input_text  big blue_flat" style="width:80px;"
							type="button" id="submit" value="保存" />
			 	</div>
	 			</li>
	 		</ul>
	 		 <ul>
	 		  <li style="width:100%;"><b>用户角色:</b><span style="color:red">*&nbsp;&nbsp;</span>
	 		  <a href="javascript:void(0);" class="selectAll1">全选</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="selectDis1">反选</a></li>
	 			<c:forEach items="${list}" var="item" varStatus="status">
	 			<li style="width:25%;overflow: hidden;display: inline-block;white-space: nowrap;">
	    			<c:choose>
	    				<c:when test="${item.checked eq 'show'}">
				   			<label><input type="checkbox" style="width: 20px" name="userGroup" value="${item.id}" clogin="${item.clogin}" checked/>${item.group_name }</label>
				   		</c:when>
				   		<c:otherwise>
				   			<label><input type="checkbox" style="width: 20px" name="userGroup" value="${item.id}" clogin="${item.clogin}"/>${item.group_name }</label>
				   		</c:otherwise>
				   </c:choose>
				   </li>
    		</c:forEach>
	 			
	 		</ul>
	 		
	 			<ul>
	 			<li style="width:100%;"><b>快递公司:</b>
		 			<c:forEach items="${lgcList}" var="item" varStatus="status">
		    			<input type="checkbox" checked="checked" onchange="chl(this.checked,'${item.lgc_no}')" style="width: 20px;display:none;" name="lgcNo"  value="${item.lgc_no}" <u:Logic value2="${item.lgc_no}" value1="${params.id }" name="lgcUserChecked"/> />${item.name }
	    			</c:forEach>
	 			</li>
	 			
	 		</ul>
	 		
	 		<ul>
	 			<li style="width:100%;"><b>网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;点:&nbsp;&nbsp;</b><a href="javascript:void(0);" class="selectAll3">全选</a>&nbsp;&nbsp;<a href="javascript:void(0);" class="selectDis3">反选</a></li>
	 			<c:forEach items="${substationList}" var="item" varStatus="status">
	 				<c:if test="${item.status=='1'}">
		 				<li style="width:25%;overflow: hidden;height:20px;display: none;white-space: nowrap;" class="${item.lgc_no}">
		    				<label><input class="in_${item.lgc_no}" type="checkbox" style="width: 20px" name="substationNo"  value="${item.substation_no}" <u:Logic value2="${item.substation_no}" value1="${params.id }" name="substationUserChecked"/> />${item.sname }</label>
					   </li>
    				</c:if>
    			</c:forEach>
	 		</ul>
	 	
	 	</div>
	 	</form>
	 </div>
	 
	 
</body>

</html>