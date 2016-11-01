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
    	
    	
    	$("input[type='reset']").click(function(){
    		 $("#stips").html('★分站名称');
    		 $("#ctips").html('★快递员');
    		 $("#mtips").html('输入账号后按回车显示名称');
      	});
		
    	
    	$(".validate_form").validationEngine("attach",{
    		ajaxFormValidation:false,
    		onBeforeAjaxFormValidation:function (){
    			alert("before ajax validate");
    		},
    		onAjaxFormComplete:
            function (){
    		}
    	}); 
    
  	  jQuery.ajax({
	      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	      dataType: "script",
	      cache: true
	}).done(function() {	 
    	
    	var slist = tmjs.slist;
    	var clist = tmjs.clist;
    	var mlist = tmjs.mlist;
    	var sres = [];
    	var cres = [];
    	var mres = [];
    	$.each(slist, function(i, item) {
    		 var inner_no = "" ;
           	if(item.inner_no){inner_no=item.inner_no+',';}
    		sres[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
        });
       $.each(clist, function(i, item) {
    	   var inner_no = "" ;
         	if(item.inner_no){inner_no=item.inner_no+',';}
        	cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
        }); 
       $.each(mlist, function(i, item) {
       	mres[i]=item.month_settle_no.replace(/\ /g,"")+'('+item.month_name.replace(/\ /g,"")+')';
       }); 
          $jqq("#substationNo").autocomplete(sres, {
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
    			 $("#substationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
    			 $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
			       } 
    	});	
         $jqq("#courierNo").autocomplete(cres, {
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
         			 $("#courierNo").val(data[0].substring(0,data[0].indexOf('('))) ;
         			 $("#ctips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
   			       } 
         	}); 
         			 
         	  $jqq("#monthNo").autocomplete(mres, {
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
           			 $("#monthNo").val(data[0].substring(0,data[0].indexOf('('))) ;
           			 $("#mtips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
     			       } 
           	}); 		 
	}); 	
    	
    	
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
    	       }); 
    	
     	   $('#submit').click(function(){
     		  if($(".validate_form").validationEngine("validate")){  
     			  var substationNo = $('input[name=substationNo]').val() ;
     			 if(substationNo.indexOf(')')>-1){
     				substationNo = substationNo.substring(0,substationNo.indexOf('(')); 
      			 }
     			 var courierNo = $('input[name=courierNo]').val() ;
     			 if(courierNo.indexOf(')')>-1){
     				 courierNo = courierNo.substring(0,courierNo.indexOf('(')); 
       			  }
     			var settleTime = $.trim($('input[name=settleTime]').val()) ;
     			var fcount = $.trim($('input[name=fcount]').val()) ;
     			if(fcount.length<1){fcount='0';}
     			var ccount = $.trim($('input[name=ccount]').val()) ;
     			if(ccount.length<1){ccount='0';}
     			var ocount = $.trim($('input[name=ocount]').val()) ;
     			if(ocount.length<1){ocount='0';}
     			var hcount = $.trim($('input[name=hcount]').val()) ;
     			if(hcount.length<1){hcount='0';}
     			var monthNo = $.trim($('input[name=monthNo]').val()) ;
     			var mcount = $.trim($('input[name=mcount]').val()) ;
     			if(mcount.length<1){mcount='0';}
     			var mtype = $.trim($('select[name=mtype]').val()) ;
     			var msettleDate = $.trim($('input[name=msettleDate]').val()) ;
     			var mcontact = $.trim($('input[name=mcontact]').val()) ;
     			var mphome = $.trim($('input[name=mphome]').val()) ;
     			var memail = $.trim($('input[name=memail]').val()) ;
     			var liced = $.trim($('select[name=liced]').val()) ;
     			var note = $.trim($('input[name=note]').val()) ;
     			 $.ajax({
     				 type: "post",//使用get方法访问后台
     		            dataType: "text",//返回json格式的数据
     		           url: "/gather/ssave",//要访问的后台地址
     		            data: {'substationNo':substationNo,'courierNo':courierNo,'settleTime':settleTime,'fcount':fcount,'ccount':ccount,'hcount':hcount,
     		            	   'ocount':ocount,'monthNo':monthNo,'mcount':mcount,'mtype':mtype,'msettleDate':msettleDate,'mcontact':mcontact,
     		            	   'mphome':mphome,'memail':memail,'liced':liced,'note':note
     		            },//要发送的数据
     		            success: function(msg){//msg为返回的数据，在这里做数据绑定
     		            	if('1'==msg){
     		            		alert("录入成功");
     		            		location.reload() ;
     		            	}else{
     		            		alert(msg);
     		            	}
     		            }
     			 }); 
     		    
     		    
     		  }  
     		   
     	   });
     	   
            
        });

    </script>
</head>
<body>
<div id="tt" >
 <div  class="split">现在位置：网点录入</div>    
    <form action="msave" method="post"  class="validate_form" id="validate_form">
        <ul style="list-style: none;width:100%;float: left;">
            <li class="lileft" style="width:12%;margin-right: 2%;"><span style="color: red;">★</span>网点编号</li>
         <li class="liright"><input class="validate[required]" name="substationNo" id="substationNo" type="text" style="width: 28%;" maxlength="15"/>
         <div  style="color: white;background-color: gray;height:30px;display: inline-block;line-height: 30px;margin-left: 10px;padding: 0 10px;" id="stips">★分站名称</div></li>
  <div style="clear: both;"></div>  
   
      <li class="lileft" style="width:12%;margin-right: 2%;"><span style="color: red;">★</span>快递员</li>
         <li class="liright"><input class="validate[required]" name="courierNo" id="courierNo" type="text" style="width: 28%;" maxlength="15"/>
         <div style="color: white;background-color: gray;height:30px;display: inline-block;line-height: 30px;margin-left: 10px;padding: 0 10px;" id="ctips">★快递员</div></li>
  <div style="clear: both;"></div>  
         </ul> 
      

 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;border-color: rgb(219, 219, 219);float: left;margin-top:20px;margin-left: 5%;margin-right: 15%;" >
 </div>  
         
<div id="left_" style="width:35%;float: left;margin-top:20px;">
 <div style="clear: both;"></div>    

 <ul style="list-style: none;width:100%;float: left;text-indent: 0;">
         <li class="lileft"><span style="color: red;"></span>账单时间</li>
         <li class="liright">
         <input  type="text"   name="settleTime"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{\'%y-%M-%d\'}'})"  style="width: 80%;margin-left: 3%;"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
         </li>
  <div style="clear: both;"></div>  
  
         <li class="lileft"><span style="color: red;"></span>邮费现金</li>
         <li class="liright"><input   name="fcount"   class="validate[funcCall[xiaoshu]]"  type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
   
         <li class="lileft">代收款金额</li>
         <li class="liright"><input id="ccount"  class="validate[funcCall[xiaoshu]]"  name="ccount" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
  
    <li class="lileft">其他</li>
         <li class="liright"><input id="ocount"  class="validate[funcCall[xiaoshu]]"  name="ocount" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
  
   <li class="lileft">会员现金</li>
         <li class="liright"><input id="hcount"  class="validate[funcCall[xiaoshu]]"  name="hcount" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
  
  </ul>
    
  </div>       
 <div id="right_" style="width:60%;float: left;margin-top:20px;border-left:thin;border-left-style: solid;border-color: rgb(219, 219, 219);">
      <div style="clear: both;"></div>    
 <ul style="list-style: none;width:100%;float: left;text-indent: 0;">
         <li class="lileft1">月结客户</li>
         <li class="liright2"><input  class="validate[funcCall[monthSettle]"  name="monthNo" id="monthNo" type="text" style="width: 30%;margin-left: 3%;" maxlength="15"/>
          <div style="color: white;background-color: gray;height:30px;display: inline-block;line-height: 30px;margin-left: 10px;padding: 0 10px;" id="mtips">输入账号后按回车显示名称</div></li>
  <div style="clear: both;"></div>  
  
         <li class="lileft1">月结金额</li>
         <li class="liright2"> <input name="mcount"  class="validate[funcCall[xiaoshu]]" id="mcount" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/>元
         <select name="mtype" style="width: 110px">
                <option value="CASH" >现金</option>
                <option value="ZZ">转账</option>
                <option value="OT">其他</option>
             </select></li>
  <div style="clear: both;"></div> 
   
         <li class="lileft1">账单月份</li>
         <li class="liright2"> <input name="msettleDate"  id="msettleDate" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',readOnly:true,maxDate:'#F{\'%y-%M\'}'})"  onClick="WdatePicker({dateFmt:'yyyy-MM'})"  type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/></li>
  <div style="clear: both;"></div> 
  
  <li class="lileft1">联系人</li>
         <li class="liright2"> <input name="mcontact"  class="validate[funcCall[zys]]"  id="mcontact" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/></li>
  <div style="clear: both;"></div> 
  
  
  <li class="lileft1">联系电话</li>
         <li class="liright2"> <input name="mphome" class="validate[funcCall[phoneCall]]"   id="mphome" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/> </li>
  <div style="clear: both;"></div> 
  
  
  <li class="lileft1">邮       箱</li>
         <li class="liright2"> <input name="memail" class="validate[custom[email]]"  id="memail" type="text" style="width: 30%;margin-left: 3%;" maxlength="20"/> </li>
  <div style="clear: both;"></div> 
  
  <li class="lileft1">是否开具发票</li>
         <li class="liright2">
         <select name="liced" style="width: 110px;margin-left: 3%;">
                <option value="Y" ${params['liced'] eq 'Y'?'selected':''}>是</option>
                <option value="N" ${params['liced'] eq 'N'?'selected':''}>否</option>
             </select> </li>
  <div style="clear: both;"></div> 
  
  
  
  </ul>
</div>   
   
 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;float: left;margin-top:20px;margin-bottom:20px;border-color: rgb(219, 219, 219);margin-left: 5%;margin-right: 15%;" >
 </div>    
    <ul style="list-style: none;width:100%;float: left;">
         <li class="lileft2">备注描述<input style="width:65%;margin-left: 2%;" name="note" type="text"  maxlength="50"></input></li>     
       </ul>
    
       
   
       
    <div style="clear: both;"></div>    
  <div style="margin-left: 23%;margin-top: 10px;margin-bottom: 10px;">
 <input name="reset" type="reset" value="重置" width="100" height="50" class="button input_text  big gray_flat"/>
  <input id="submit" name="submit" type="button" value="提交" width="100" class="button input_text  big gray_flat" height="50" style="margin-left: 6%;"/>
</div>

</form>
</div>
</body>

</html>