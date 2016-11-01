<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style type="text/css">
.split {
height: 30px;
line-height: 30px;
border-bottom: 1px dashed #d2d2d2;
clear: both;
font-weight: bold;
color: #454545;
margin-top: 14px;
}

ul {
list-style: none;
padding: 0;
margin: 0;
outline: none;
text-indent: 0;
}
.lileft,.lileft1,.liright,.liright2 {
text-indent: 0;
padding: 0;
margin: 0;
margin-top: 20px;
}
.lileft{
display:block;
width: 35%;text-align: right;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-right: 3%;
}
.lileft1{
display:block;
width: 15%;text-align: right;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-left: 1%;
}
.lileft2{
display:block;
width: 90%;float: left;
font-size: 1em;
padding: .5em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
margin-left: 9%;
margin-top: 15px;
}

.liright{
width: 55%;text-align: left;float: left;
padding: 0;
margin-left: 0%;
}

.liright2{
width: 80%;text-align: left;float: left;
padding: 0;
margin-left: 0%;
}


input[type="text"],select{
font-size: 1em;
padding: .3em 0;
line-height: 1.5;
word-break: break-all;
outline: none;
}
select{
height:32px;
display:inline;
line-height: 32px;
}
/*  background-color:#C4C4C4;  */
.title{
width: 100%;height:30px;line-height:30px;margin-top: 10px;font-size:14px;

}
.title1{
width: 140px;float: left;margin-left: 70px;font-weight: bold;
}

</style>
    <script type="text/javascript">
    
    $(function(){
    	
    	$(".validate_form").validationEngine("attach",{
    		ajaxFormValidation:false,
    		onBeforeAjaxFormValidation:function (){
    			alert("before ajax validate");
    		},
    		onAjaxFormComplete:
            function (){
    		}
    	}); 
    	
    	if('${msg}'!=''){
    		alert('${msg}') ;
    	}
	        	jQuery.ajax({
	    		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	    		      dataType: "script",
	    		      cache: true
	    		}).done(function() {
	    

	                   	var data1 = tmjs.clist ;
	       	          	var availablesrcKey1 = [];
	       	              $.each(data1, function(i, item) {
	       	              	var inner_no = "" ;
	       	              	if(item.inner_no){inner_no=item.inner_no+','}
	       	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
	       	              });
	                            val1 = '';
	                              $jqq("#gatherNo").autocomplete(availablesrcKey1, {
	                           		minChars: 0,
	                           		max: 12,
	                           		autoFill: true,
	                           		mustMatch: false,
	                           		matchContains: true,
	                           		scrollHeight: 220,
	                           		formatItem: function(data11, i, total) {
	                           			return data11[0];
	                           		}
	                           	}).result(function(event, data, formatted) {
	                         			 $("#gatherNo").val(data[0]);
	                         	});	 
	                          }); 	      

	        	
	          	$("#rmoney").blur(function(e){  
	        		var dno = $(this).val() ;
	        			 $.ajax({url:'dis_type',data:{'rmoney':dno},success:function(data){
	        				 if(data!=null){
	        					 $("#realPay").val(data.payMoney) ;
	        					 $("#disType").val(data.disText) ;
	        				 }else{
	        					 $("#realPay").val("") ;
	        					 $("#disType").val("找不到适配的优惠类型") ;
	        				 }
	        			 }});
	        	});
	          	
	          	
/* 
     	  var $inp = $('input:text');
    	    $inp.bind('keydown', function (e) {
    	        // 回车键事件  
    	           if(e.which == 13) {  
    	        	   var focused = $(':focus').attr("name");
                  		   var $inp = $('input:text');
               		      e.preventDefault();
                          var nxtIdx = $inp.index(this) + 1;
                          $(":input:text:eq(" + nxtIdx + ")").focus();
    	           }  
    	       });   */
    	
  
    });

    </script>
</head>
<body>
<div id="tt" >
 <div  class="split"></div>    
    <form action="uadd" method="post"  class="validate_form" id="validate_form">
        <ul style="list-style: none;width:100%;float: left;">
         <li class="lileft" style="width:12%;margin-right: 2%;"><span style="color: red;">★</span>会员号（手机号）</li>
         <li class="liright" style="width:15%;"><input class="validate[required,funcCall[phones]]" name="disUserNo" id="disUserNo" type="text" style="width: 100%;" maxlength="15"/></li>
         <li class="lileft" style="width:12%;margin-right: 6%;"><span style="color: red;"></span>会员分站</li>
         <li class="liright" style="width:15%;"><select id="substationNo" name="substationNo" style="width: 210px">
                      <option value="" >无</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option value="${item.substation_no }" >${item.substation_name }</option>
							</c:forEach>
			</select></li>
  <div style="clear: both;"></div>  
    
         </ul> 
      

 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;border-color: rgb(219, 219, 219);float: left;margin-top:20px;margin-left: 5%;margin-right: 15%;" >
 </div>  
         
<div id="left_" style="width:35%;float: left;margin-top:10px;">
 <div style="clear: both;"></div>    

 <ul style="list-style: none;width:100%;float: left;text-indent: 0;">
       <%--   <li class="lileft"><span style="color: red;">★</span>适用优惠</li>
         <li class="liright"><select id="disType" name="disType" style="width: 80%;margin-left: 3%;">
							<c:forEach items="${tlist}" var="item" varStatus="status">
								<option value="${item.id }" >${item.name }&#12288;&#12288;${item.discount_text }</option>
							</c:forEach>
					</select></li> --%>
  
         <li class="lileft"><span style="color: red;">★</span>充值金额</li>
         <li class="liright"><input id="rmoney"  name="rmoney"   class="validate[required,funcCall[xiaoshu]]"  type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
         
          <li class="lileft"><span style="color: red;"></span>适用优惠</li>
         <li class="liright"><input   name="disType" id="disType"   type="text" style="width: 80%;margin-left: 3%;" disabled="disabled"   />&nbsp;</li>
   
         <li class="lileft">奖励金额</li>
         <li class="liright"><input id="omoney"  class="validate[funcCall[xiaoshu]]"  name="omoney" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
  
<!--     <li class="lileft"><span style="color: red;"></span>会员密码</li>
         <li class="liright"><input id="pwd"  class="validate[required,minSize[4]]" placeholder=""   name="pwd" type="text" style="width: 80%;margin-left: 3%;"   maxlength="4"/>&nbsp;</li>
  <div style="clear: both;"></div>  -->
  
      <li class="lileft"><span style="color: red;"></span>应付金额</li>
         <li class="liright"><input id="realPay"   disabled="disabled"   placeholder=""   name="realPay" type="text" style="width: 80%;margin-left: 3%;" />&nbsp;</li>
  
   <li class="lileft">收款人</li>
         <li class="liright"><input id="gatherNo"  class=""  placeholder="不填默认当前登录用户"   name="gatherNo" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;</li>
  
  </ul>
    
  </div>       
 <div id="right_" style="width:60%;float: left;margin-top:10px;border-left:thin;border-left-style: solid;border-color: rgb(219, 219, 219);">
      <div style="clear: both;"></div>    
 <ul style="list-style: none;width:100%;float: left;text-indent: 0;">
         <li class="lileft1">会员名称</li>
         <li class="liright2"><input  class="validate[funcCall[monthSettle]"  name="disUserName" id="disUserName" type="text" style="width: 30%;margin-left: 3%;" maxlength="15"/> </li>
  
   
         <li class="lileft1">联系人</li>
         <li class="liright2"> <input name="contactName"  id="contactName" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/></li>
  
  
  <li class="lileft1">联系电话</li>
         <li class="liright2"> <input name="contactPhone" class="validate[funcCall[phoneCall]]"   id="contactPhone" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/> </li>
  
  
  <li class="lileft1">邮       箱</li>
         <li class="liright2"> <input name="email" class="validate[custom[email]]"  id="email" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/> </li>
  
  <li class="lileft1">是否开具发票</li>
         <li class="liright2">
         <select name="lied" style="width: 110px;margin-left: 3%;">
                <option value="Y" >是</option>
                <option value="N" >否</option>
             </select> </li>
  
  </ul>
</div>   
   
 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;float: left;margin-top:20px;margin-bottom:20px;border-color: rgb(219, 219, 219);margin-left: 5%;margin-right: 15%;" >
 </div>    
    <ul style="list-style: none;width:100%;float: left;">
         <li class="lileft2">备注描述<input style="width:55%;margin-left: 2%;" name="note" type="text"  maxlength="50"></input></li>     
       </ul>
    
       
   
       
    <div style="clear: both;"></div>    
  <div style="margin-left: 23%;margin-top: 10px;margin-bottom: 10px;">
 <input name="reset" type="reset" value="重置" width="100" height="50" class="button input_text  big gray_flat"/>
  <input id="submit" name="submit" type="submit" value="提交" width="100" class="button input_text  big gray_flat" height="50" style="margin-left: 6%;"/>
</div>

</form>
</div>
</body>

</html>