<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script  type="text/javascript" src="/scripts/city_hp.js"></script>
<script type="text/javascript">
$(function(){
	var areas = spit1('${substation.sarea}').split("-") ;
 	if(!areas[2]){
 		var  areas1 = '${lgcConfig.defaultCity}'.split("-") ;
 		 if(areas1[1]){
   			InitOption('sprovince','scity','sarea',areas1[0],areas1[1]);
   		  }else{
   			 InitOption('sprovince','scity','sarea','广东省','深圳市');
   		  }
 		 
 	}else{
 		 InitOption('sprovince','scity','sarea',areas[0],areas[1],areas[2]);
 	 } 
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var innerNo = $('input[name=innerNo]').val();
		 if(innerNo.length<2){
			 alert('请输入网点编号');
			 return false;
		 }
		 var substationName = $('input[name=substationName]').val();
		 if(substationName.length<2){
			 alert('请输入快递分站名称');
			 return false;
		 }
		 var substationType = 	$('select[name=substationType]').val() ;
	 	 var sprovince = 	$('select[name=sprovince]').val() ;
		   var scity = 	$('select[name=scity]').val() ;
		   var sarea = 	$('select[name=sarea]').val() ;
		   
		   if(sprovince.length<1){
		    	alert("请选择省");
		    	$('select[name=sprovince]').focus();
				 return false;
			 }
		    if(scity.length<1){
		    	alert("请选择市");
		    	$('select[name=scity]').focus();
				 return false;
			 }
		    if(sarea.length<1){
		    	alert("请选择区");
		    	$('select[name=sarea]').focus();
				 return false;
			 }
		 var sarea1 = sprovince + '-' +scity + '-' + sarea ; 
		 var  substationNo = $('input[name=substationNo]').val();
		 var  innerNo = $('input[name=innerNo]').val();
		 var id = $('input[name=id]').val();
		 var substationAddr = $('input[name=substationAddr]').val();
		 var phone = $('input[name=phone]').val();
		 var location1 = $('input[name=location]').val();
		 var lgcNo = $('select[name=lgcNo]').val();
		 var subType = $('select[name=subType]').val();
		 if(id==''||id.trim()==''){
			 if('J'==substationType){
				 if(confirm('管理方式确认后不可更改，是否保存')){
		               	
			    }else{
			   	 return false ;
			   }
			 }
		 }
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	           url: "${ctx }/lgc/ssave",//要访问的后台地址
	            data: {'id':id,'substationName':substationName,'substationNo':substationNo,'substationAddr':substationAddr,'phone':phone,
	            	'location':location1,'lgcNo':lgcNo,'innerNo':innerNo,'sarea':sarea1,'substationType':substationType,'subType':subType
	            	},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==0){
	            		alert('数据有误');
	            	}else if(msg==1){
	            		 $.ajax({
	            			 type: "post",//使用get方法访问后台
	            	          dataType: "json",//返回json格式的数据
	            	          url: "${ctx }/updata",//要访问的后台地址
	            	          data: {},//要发送的数据
	            	           success: function(data){//msg为返回的数据，在这里做数据绑定
	            	           if(data.su==1){
	            	        	    jQuery.ajax({
	            	        	    	  type: "get",
	            	        		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	            	        		      dataType: "script",
	            	        		      cache: false
	            	        		  }).done(function() {
	            	        		    
	            	        		 });
	            	                }
	            	           }
	            		 }); 
	            		
	            		alert('保存成功');
	            		api.reload();
						api.close();
	            	}else if(msg==2){
	            		alert('分站名称已存在');
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
		 		<input  type="hidden" id="id" name="id" value="${substation.id }"/>
	 			<ul style="padding-top:30px;padding-left: 20px;">
		 			<li style="width:520px"><!-- 快递分站编号： -->
		 				<input  maxlength="15" type="hidden" name="substationNo" value="${substation.substationNo }" style="width: 360px"></input>
		 			</li>
		 			<li  style="width:520px"><span  class="lleft">所属快递公司：</span>
		 			<select id="lgcNo" name="lgcNo" style="width: 360px">
							<c:forEach items="${lgcList}" var="item" varStatus="status">
								<option value="${item.lgc_no }" <c:out value="${substation['lgcNo'] eq item.lgc_no?'selected':'' }"/> >${item.name }</option>
							</c:forEach>
					</select></li>
						<li style="width:520px;color: red;"><span  class="lleft">网点编号：</span>
		 				<input  maxlength="20" type="text" name=innerNo value="${substation.innerNo}" style="width: 360px"></input>
		 			</li>
		 			<li style="width:520px;color: red;"><span  class="lleft">快递分站名称：</span>
		 				<input  maxlength="20" type="text" name="substationName" value="${substation.substationName }" style="width: 360px"></input>
		 			</li>
		 			
		 			<li style="width:250px;color: red;"><span  class="lleft">管理方式：</span>
		 				<select  <c:out value="${!empty substation.id?'disabled':'' }"/>  name="substationType">
		 				<option value="Z" ${substation.substationType ne 'J'?'selected':''} >直营</option>
		 				<option value="J" ${substation.substationType eq 'J'?'selected':''} >加盟</option></select>
		 			</li>
		 			
		 			<li style="width:250px;color: red;"><span  class="lleft">网点类型：</span>
		 				<select name="subType">
		 				<option value="0" ${substation.subType ne '0'?'selected':''} >网点</option>
		 				<option value="1" ${substation.subType eq '1'?'selected':''} >中心</option></select>
		 			</li>
		 			
		 		<li style="width:520px"><span  class="lleft">快递管辖区域：</span>
		 			 <select name="sprovince" style="width: 21%;"  class="validate[required]"  id="sprovince" onchange="javascript:ChangeProvince('scity',this.options[this.selectedIndex].value)"></select>
                    <select name="scity"  style="width:21%;margin-left: 2%;"  class="validate[required]" id="scity" onchange="javascript:ChangeCity('sarea',this.options[this.selectedIndex].value)"></select>
                    <select name="sarea" style="width:21%;margin-left: 2%;" class="validate[required]"  id="sarea" ></select>
       
		 			</li>
		 			
		 			<li style="width:520px"><span  class="lleft">快递分站地址：</span>
		 				<input  maxlength="30" type="text" name="substationAddr" value="${substation.substationAddr }" style="width: 360px"></input>
		 			</li>
		 			<li style="width:520px"><span  class="lleft">联系方式：</span>
		 				<input  maxlength="20" type="text" name="phone" value="${substation.phone }" style="width: 360px"></input>
		 			</li>
		 			<li style="width:520px"><span  class="lleft">位置坐标：</span>
		 				<input  type="text" name="location" value="${substation.location }" style="width: 360px"></input>
		 			</li>
		 			<%-- <li style="width:420px">分站编号：
		 				<input  type="text" name="lgcNo" value="${substation.lgcNo }" style="width: 128px"></input>
		 			</li> --%>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat"
					type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>