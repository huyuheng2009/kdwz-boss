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
    	
    	
    	$("#disUserNo").on('input',function(e){  
    		var dno = $(this).val() ;
    		 if(checkPhone(dno,'N')){
    			 $.ajax({url:'info',data:{'disUserNo':dno},success:function(data){
    				 if(data!=null){
    					 $("#utip").html(data.disUserName) ;
    					 $("#disType").val(data.disType) ;
    				 }else{
    					 $("#utip").html("会员号不存在") ;
    				 }
    			 }});
   		    }else{
   		     $("#utip").html("") ;
   		    }	
    		
    	});
    	
    	jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() {
    	var clist = tmjs.clist;
    	var cres = [];
       $.each(clist, function(i, item) {
    	   var inner_no = "" ;
          	if(item.inner_no){inner_no=item.inner_no+',';}
        	cres[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
        }); 
       
      
          $jqq("#gatherNo").autocomplete(cres, {
       		minChars: 0,
       		max: 12,
       		autoFill: true,
       		mustMatch: true,
       		matchContains: true,
       		scrollHeight: 220,
       		formatItem: function(data, i, total) {
       			return data[0];
       		}
       	}).result(function(event, data, formatted) {
     		/* if(data[0].indexOf(')')>-1){
    			 $("#gatherNo").val(data[0].substring(0,data[0].indexOf('('))) ;
    			 $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
			  }  */
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
    	
  
     	   
            
        });

    </script>
</head>
<body>
<div id="tt" >
 <div  class="split">现在位置：会员充值</div>    
    <form action="urecharge" method="post"  class="validate_form" id="validate_form">
        <ul style="list-style: none;width:100%;float: left;">
         <li class="lileft" style="width:12%;margin-right: 2%;"><span style="color: red;">★</span>会员号（手机号）</li>
         <li class="liright" style="width:15%;"><input class="validate[required,funcCall[phones]]" name="disUserNo" id="disUserNo"  value="${params['userNo']}" type="text" style="width: 100%;" maxlength="15"/>
         </li>
          <li class="liright" id="utip" style="width:15%;line-height: 35px;margin-left: 20px;color: red;"></li>
  <div style="clear: both;"></div>  
    
         </ul> 
      

 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;border-color: rgb(219, 219, 219);float: left;margin-top:20px;margin-left: 5%;margin-right: 15%;" >
 </div>  
         
<div id="left_" style="width:35%;float: left;margin-top:10px;">
 <div style="clear: both;"></div>    

 <ul style="list-style: none;width:100%;float: left;text-indent: 0;">
         <li class="lileft"><span style="color: red;">★</span>适用优惠</li>
         <li class="liright"><select id="disType" name="disType" style="width: 80%;margin-left: 3%;">
							<c:forEach items="${tlist}" var="item" varStatus="status">
								<option value="${item.id }" <c:out value="${item.id eq duser.disType?'selected':'' }"/> >${item.name }&#12288;&#12288;${item.discount_text }</option>
							</c:forEach>
					</select></li>
  <div style="clear: both;"></div>  
  
         <li class="lileft"><span style="color: red;">★</span>充值金额</li>
         <li class="liright"><input   name="rmoney"   class="validate[required,funcCall[xiaoshu]]"  type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
   
         <li class="lileft">奖励金额</li>
         <li class="liright"><input id="omoney"  class="validate[funcCall[xiaoshu]]"  name="omoney" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;元</li>
  <div style="clear: both;"></div> 
  
   <li class="lileft">收款人</li>
         <li class="liright"><input id="gatherNo"  class=""  placeholder="不填默认当前登录用户"   name="gatherNo" type="text" style="width: 80%;margin-left: 3%;"   maxlength="20"/>&nbsp;</li>
  <div style="clear: both;"></div> 
  
 <li class="lileft">是否开具发票</li>
         <li class="liright">  <select name="lied" style="width: 110px;margin-left: 3%;">
                <option value="Y" >是</option>
                <option value="N" >否</option>
             </select></li>
  <div style="clear: both;"></div> 
  
  
  </ul>
    
  </div>       

 <div style="width:81%;height:1px;clear: both;border-bottom:thin;border-bottom-style:dashed ;float: left;margin-top:20px;margin-bottom:20px;border-color: rgb(219, 219, 219);margin-left: 5%;margin-right: 15%;" >
 </div>    
    <ul style="list-style: none;width:100%;float: left;">
         <li class="lileft2">备注描述<input style="width:18%;margin-left: 2%;" name="note" type="text"  maxlength="50"></input></li>     
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