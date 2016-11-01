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
<%@ include file="/header.jsp"%>
<style>
*{padding:0;margin:0;}
.lleft{width: 115px;text-align: right;float: left;}
li {padding: 4px 0px;}
</style>
<script type="text/javascript">
$(function(){
	
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
                    $jqq("#courierNo").autocomplete(availablesrcKey1, {
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
               			 $("#courierNo").val(data[0]);
               			 var cno = data[0] ;
               			if(data[0].indexOf(')')>-1){
               			  cno = data[0].substring(0,data[0].indexOf('(')) ;
                          }
               	});	 
               			  
               			  
                    var slist =  tmjs.slist ;
                    // $.getJSON("${ctx}/lgc/calllist", function(data1) {
                     	var slists = [];
                         $.each(slist, function(i, item) {
                         	var inner_no = "" ;
                         	if(item.inner_no){inner_no=item.inner_no+','}
                         	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                         });
                         val1 = '';
                           $jqq("#substationNo").autocomplete(slists, {
                        		minChars: 0,
                        		max: 12,
                        		autoFill: true,
                        		mustMatch: false,
                        		matchContains: true,
                        		scrollHeight: 220,
                        		formatItem: function(slists, i, total) {
                        			return slists[0];
                        		}
                        	}).result(function(event, data, formatted) {
                         		if(data[0].indexOf(')')>-1){
                        			 $("#substationNo").val(data[0]) ;
                  			       } 
                        	});	  			  
               			  
               			  
               			  
               			  
               			  
               			  
           }); 	
	
	
	 $('#submit').click(function(){
		 var api = frameElement.api, W = api.opener;
		 var id = $('input[name=id]').val();


		 var cpn_sname = $.trim($('input[name=cpn_sname]').val());
		 var cpn_name = $.trim($('input[name=cpn_name]').val());
		 var concat_name = $.trim($('input[name=concat_name]').val());
		 
		 var concat_phone = $.trim($('input[name=concat_phone]').val());
		 var cell_phone = $.trim($('input[name=cell_phone]').val());
		 var concat_addr = $.trim($('input[name=concat_addr]').val());
		 
		 var substation_no = $.trim($('input[name=substation_no]').val());
		 var courier_no = $.trim($('input[name=courier_no]').val());
		 var kefu_name = $.trim($('input[name=kefu_name]').val());
		 
		 var month_no = $.trim($('input[name=month_no]').val());
		 var note = $.trim($('input[name=note]').val());
		  
		 if(!checkPhone(concat_phone,'Y')){
			 alert('请输入正确的联系电话');
			 return false;
		 } 
		 if(cell_phone.length>1){
			 if(!checkPhone(cell_phone,'Y')){
				 alert('请输入正确的手机号');
				 return false;
			 } 
		 } 

		 
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/customer/update",//要访问的后台地址
	            data: {'id':id,'cpn_sname':cpn_sname,'cpn_name':cpn_name,'concat_name':concat_name,'concat_phone':concat_phone,'note':note,
	            	'cell_phone':cell_phone,'concat_addr':concat_addr,'substation_no':substation_no,'courier_no':courier_no,'kefu_name':kefu_name,'month_no':month_no},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
	            		alert('保存成功',"",function(){
	            			api.reload();
							api.close();	
	            		});
	            		
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
		 		<input  type="hidden" id="id" name="id" value="${lgcCustomer.id }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 		
                   <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>客户号：</span>
		 				<input  maxlength="20" type="text" name="customer_no" disabled="disabled" value="${lgcCustomer.customer_no }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">&nbsp;</span>公司简称：</span>
		 				<input  maxlength="20" type="text" name="cpn_sname" value="${lgcCustomer.cpn_sname }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">&nbsp;</span>联系人：</span>
		 				<input  maxlength="20" type="text" name="concat_name" value="${lgcCustomer.concat_name }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">&nbsp;</span>公司名称：</span>
		 				<input  maxlength="20" type="text" name="cpn_name" value="${lgcCustomer.cpn_name }" style="width: 160px"></input>
		 			</li>
		 		
					</ul>
					<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
		 		
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>联系电话：</span>
		 				<input  maxlength="20" type="text" name="concat_phone" value="${lgcCustomer.concat_phone }" style="width: 160px"></input>
		 			</li>
		 			
		 		  <li style="width:320px"><span  class="lleft">手机号：</span>
		 				<input  maxlength="20" type="text" name="cell_phone" value="${lgcCustomer.cell_phone }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">联系地址：</span>
		 				<input  maxlength="20" type="text" name="concat_addr" value="${lgcCustomer.concat_addr }" style="width: 480px"></input>
		 			</li>
		 			
		 			</ul>
		 			<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">


					<li style="width:320px"><span  class="lleft">所属快递员：</span>
		 				<input  maxlength="20" type="text" name="courier_no" id="courierNo" value="${lgcCustomer.courier_no }" style="width: 160px"></input>
		 			</li>
		 	
					
					<li style="width:320px"><span  class="lleft">所属网点：</span>
		 				<input  maxlength="20"  type="text" name="substation_no" id="substationNo" value="${lgcCustomer.substation_no }" style="width: 160px"></input>
		 			</li>		
		 	
					
					<li style="width:670px"><span  class="lleft">客服负责人：</span>
		 				<input  maxlength="20" type="text" name="kefu_name" value="${lgcCustomer.kefu_name }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">月结号：</span>
		 				<input  maxlength="20" type="text" name="month_no" value="${lgcCustomer.month_no }" style="width: 160px"></input>
		 			</li>
		 			 
                    <li style="width:670px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note" value="${lgcCustomer.note }" style="width: 480px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="button" id="submit" value="保存" />
	 	</div>
	</div>
</body>
</html>