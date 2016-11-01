<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/scripts/inputCheck.js"></script>
<style>
*{padding:0;margin:0;}
.lleft{width: 115px;text-align: right;float: left;}
li {padding: 4px 0px;}
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
		  jQuery.ajax({
		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
		      dataType: "script",
		      cache: true
		}).done(function() { 
	    var slist = tmjs.slist ;
       	var sres = [];
       	$.each(slist, function(i, item) {
       	  var inner_no = "" ;
          	if(item.inner_no){inner_no=item.inner_no+',';}
       		sres[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
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
      			 $("#substationNo").val(data[0]) ;
     			// $("#substationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
     			//$("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
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
    	    	
    	    	   if($("#validate_form").validationEngine("validate")){   
    	    		  
    	    		   var id = $.trim($('input[name=id]').val()) ;
    	    		   var substationNo = $.trim($('input[name=substationNo]').val()) ;
    	    		   var disUserName = $.trim($('input[name=disUserName]').val()) ;
    	    		   var contactName = $.trim($('input[name=contactName]').val()) ;
    	    		   var contactPhone = $.trim($('input[name=contactPhone]').val()) ;
    	    		   var email = $.trim($('input[name=email]').val()) ;
    	    		   var note = $.trim($('input[name=note]').val()) ;
    	    		   var api = frameElement.api, W = api.opener;
    	    			 $.ajax({
    	     				 type: "post",//使用get方法访问后台
    	     		            dataType: "text",//返回json格式的数据
    	     		           url: "/disuser/usave",//要访问的后台地址
    	     		            data: {'id':id,'disUserName':disUserName,'contactName':contactName,'contactPhone':contactPhone,'email':email,'note':note,'substationNo':substationNo},//要发送的数据
    	     		            success: function(msg){//msg为返回的数据，在这里做数据绑定
    	     		            	if('1'==msg){
    	     		            		alert("保存成功",'',function(){
    	     		            			api.reload();
        	     							api.close();
    	     		            		});
    	     		            		
    	     		            	}else{
    	     		            		alert(msg);
    	     		            	}
    	     		            }
    	     			 });
    	    		   
    	    		   
    	    		   
    	    	   }
    	    });
  
            
        });
 
    function osb(){
    	alert("123") ;
    	return false ;
    }
    

    </script>
</head>
<body>
    <form action="" class="validate_form" id="validate_form" >
   <input  value="${disUser.id}"  name="id" id="id" type="hidden" /> 
   
   
   	<div class="content">
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${muserMap.id }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
               <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员账号：</span>
		 				<input   name="disUserNo" id="disUserNo" disabled="disabled" type="text" value="${disUser.disUserNo} " style="width: 160px"  maxlength="15"/>
		 			</li>

		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员分站：</span>
		 				<input  maxlength="20" type="text" name="substationNo" id="substationNo" value="${disUser.substationNo }" style="width: 160px"></input>
		 			</li>
		 			
		 		

					</ul>
					<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
		 			<li style="width:670px"><span  class="lleft">会员名称：</span>
		 			<input  value="${disUser.disUserName}" class="" name="disUserName" id="disUserName" type="text" style="width: 160px" maxlength="15"/> 
		 			</li>
		 			<li style="width:670px"><span  class="lleft">联系人：</span>
		 				<input name="contactName"  id="contactName" value="${disUser.contactName}"  type="text" style="width: 160px" maxlength="20"/>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">联系电话：</span>
		 				<input name="contactPhone" class="validate[funcCall[phoneCall]]" value="${disUser.contactPhone}"   id="contactPhone" type="text" style="width: 160px;" maxlength="20"/> 
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">邮       箱：</span>
		 				<input name="email" class="validate[custom[email]]" value="${disUser.email}"  id="email" type="text" style="width: 160px;" maxlength="20"/>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note"  style="width: 480px"></input>
		 			</li>
		 			
		
		 			</ul>
		

		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="button" id="submit" value="保存" />
	 	</div>
	</div>
   </form>
   


</body>

</html>