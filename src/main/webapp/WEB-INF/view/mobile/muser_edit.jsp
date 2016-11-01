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
	 var api = frameElement.api, W = api.opener;
	 
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
               			
               			 $.ajax({
               				 type: "post",//使用get方法访问后台
               		            dataType: "text",//返回json格式的数据
               		            url: "/getSnameBycno",//要访问的后台地址
               		            data: {'cno':cno},//要发送的数据
               		            success: function(msg){//msg为返回的数据，在这里做数据绑定
               		             $("#substationNo").val(msg);
               		            }
               			 });
               	});	 
           }); 	
}); 

	function check(){
 		 var api = frameElement.api, W = api.opener;
		 var id = $('input[name=mid]').val();
		
		 var no_pre = $.trim($('input[name=noPre]').val());
		 var no_fix = $.trim($('input[name=noFix]').val());
		 var monthSettleNo = no_pre+no_fix;
		 $('input[name=monthSettleNo]').val(monthSettleNo) ;
		 
		 var monthName = $.trim($('input[name=monthName]').val());
		 var monthSname = $.trim($('input[name=monthSname]').val());
		 var mstype = $('select[name=mstype]').val();
		 
		 var pwd = $.trim($('input[name=pwd]').val());
		 
		 var contactName = $.trim($('input[name=contactName]').val());
		 var contactPhone = $.trim($('input[name=contactPhone]').val());
		 var contactAddr = $.trim($('textarea[name=contactAddr]').val());
		 
		 var settlePhone = $.trim($('input[name=settlePhone]').val());
		 var substationNo = $('input[name=substationNo]').val();
		 var courierNo = $('input[name=courierNo]').val();
		 var markName = $.trim($('input[name=markName]').val());
		 
		 
		 var settleType = $.trim($('input[name=settleType]').val());
		 var settleCardNo = $.trim($('input[name=settleCardNo]').val());
		 var settleBank = $.trim($('input[name=settleBank]').val());
		 var settleDate = $.trim($('input[name=settleDate]').val());
		 
		 var bizLic = $.trim($('input[name=bizLic]').val());
		 var email = $.trim($('input[name=email]').val());
		 var liced = $('select[name=liced]').val();
		 var note = $.trim($('input[name=note]').val());
		
		
		 if(no_fix.length<4){
			 alert('请输入正确的月结号');
			 return false;
		 }
		 
		 var p1 = /^[0-9]{5}$/ ;
		  if(id.length<=0){
		 if(!p1.test(no_fix)){
			 alert('请输入正确的月结号');
			 return false;
		 }
	   }
		 
		 if(id.length<=0){
			 var p = /^[0-9]{6,500}$/ ;
			 if(!p.test(pwd)){
				 alert('请输入正确的月结密码');
				 return false;
			 }
		 }
		 
		 if(monthName.length<2){
			 alert('请输入公司名称');
			 return false;
		 }
		 if(monthSname.length<2||monthSname.length>8){
			 alert('请输入正确的公司简称');
			 return false;
		 }
		 if(mstype.length<1){
			 alert('请选择优惠类型');
			 return false;
		 }

		   var filter = /^\s*([A-Za-z0-9_-]+(\.\w+)*@(\w+\.)+\w{2,3})\s*$/;
	         if(email.length>0&&!filter.test(email)){
	             alert("邮箱格式错误");
	             return false;
	         }
		 
	         
	       
	         
	  /*        
		 $.ajax({
			 type: "post",//使用get方法访问后台
	            dataType: "text",//返回json格式的数据
	            url: "${ctx }/mobile/muser_save",//要访问的后台地址
	            data: {'mid':id,'substationNo':substationNo,'monthSettleNo':monthSettleNo,'monthName':monthName,'contactName':contactName,'contactPhone':contactPhone,'bizLic':bizLic,
	            	'settleType':settleType,'settleCardNo':settleCardNo,'settleDate':settleDate,'email':email,'settleBank':settleBank,'note':note,'pwd':pwd,'noPre':no_pre,'noFix':no_fix,
	            	'monthSname':monthSname,'contactAddr':contactAddr,'settlePhone':settlePhone,'courierNo':courierNo,'markName':markName,'liced':liced,'mstype':mstype},//要发送的数据
	            success: function(msg){//msg为返回的数据，在这里做数据绑定
	            	if(msg==1){
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
	            	}else{
	            		alert(msg);
	            	}
	            }
		 }); */
		 
		 return true ;
 }
	
	function file(f){
		if(f==1){
			$('input[name=file11]').click();
		}else{
			$('input[name=file22]').click() ;
		}
	}	

</script>
</head>
<body>
<form class="tbdata" action="/mobile/muser_save"  enctype="multipart/form-data" method="post" onsubmit="return check()">
	<div class="content">
		 	<div class="item">
		 		<input  type="hidden" id="id" name="mid" value="${muserMap.id }"/>
		 		<input  type="hidden" id="monthSettleNo" name="monthSettleNo" value="${muserMap.month_settle_no }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>月结号：</span>
		 			<c:if test="${empty muserMap.id }">
		 			  <input  maxlength="20" type="text" name="noPre"  placeholder="前缀" value="${configMap.NO_FIX }" disabled style="width: 50px"></input>
		 			</c:if>
		 			<c:if test="${!empty muserMap.id }">
		 			  <input  maxlength="20" type="text" name="noPre"  placeholder="前缀" value="${muserMap.no_pre }" disabled style="width: 50px"></input>
		 			</c:if>
		 				<input  maxlength="20" type="text" name="noFix"  placeholder="5位纯数字" value="${muserMap.no_fix }" <c:out value="${!empty muserMap.month_settle_no?'disabled':'' }"/> style="width: 100px"></input>
		 			</li>
		 			<c:if test="${empty muserMap.id }">
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>月结密码：</span>
		 				<input  maxlength="20" type="text" name="pwd" value="" placeholder="6位以上数字" style="width: 160px"></input>
		 			</li>
		 			</c:if>
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>公司名称：</span>
		 				<input  maxlength="20" type="text" name="monthName" value="${muserMap.month_name }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>公司简称：</span>
		 				<input  maxlength="20" type="text" name="monthSname" value="${muserMap.month_sname }" style="width: 160px"></input>
		 			</li>
		 			
		 			  <li style="width:420px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>优惠类型：</span>
		 			<select id="mstype" name="mstype" style="width: 168px">
		 			  <option value="">请选择</option>
							<c:forEach items="${mslist}" var="item" varStatus="status">
								<option   value="${item.id }"  <c:out value="${muserMap.mstype eq item.id?'selected':'' }"/>>${item.name}</option>
							</c:forEach>
					</select>
					</li>
					</ul>
					<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
		 			<li style="width:320px"><span  class="lleft">联系人：</span>
		 				<input  maxlength="20" type="text" name="contactName" value="${muserMap.contact_name }" style="width: 160px"></input>
		 			</li>
		 			<li style="width:320px"><span  class="lleft">联系电话：</span>
		 				<input  maxlength="20" type="text" name="contactPhone" value="${muserMap.contact_phone }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft">联系地址：</span>
		 			<%--
		 			
		 				<input  maxlength="50" type="text" name="contactAddr" value="${muserMap.contact_addr }" style="width: 480px"></input>
		 			 --%>
		 				<textarea rows="5"  maxlength="50" cols="30" name="contactAddr" style="min-width:490px;min-height:80px;max-width:490px;max-height:80px;">${muserMap.contact_addr }</textarea>
		 			
		 			</li>
		 			
		 				<li style="width:320px"><span  class="lleft">财务电话：</span>
		 				<input  maxlength="20" type="text" name="settlePhone" value="${muserMap.settle_phone }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft">结算方式：</span>
		 				<input  maxlength="20" type="text" name="settleType" value="${muserMap.settle_type }" style="width: 160px"></input>
		 			</li>
		 			<!--  <li style="width:450px"><span  class="lleft">结算银行：</span> -->
		 				<input  maxlength="20" type="hidden" name="settleBank" value="${muserMap.settle_bank }" style="width: 160px"></input>
		 			<!-- </li>  -->
		 			<li style="width:320px"><span class="lleft">结算户名：</span>
		 				<input  maxlength="20" type="text" name="settleCardNo" value="${muserMap.settle_card_no }" style="width: 160px"></input>
		 			</li>
		 			<li style="width:320px"><span  class="lleft">结算日期：</span>
		 				<input  maxlength="20" type="text" name="settleDate" value="${muserMap.settle_date }" style="width: 160px"></input>
		 			</li>
		 			<li style="width:320px"><span  class="lleft">营业执照/身份证号：</span>
		 				<input  maxlength="20" type="text" name="bizLic" value="${muserMap.biz_lic }" style="width: 160px"></input>
		 			</li>
		 			<li style="width:320px"><span  class="lleft">电子邮箱：</span>
		 				<input  maxlength="200" type="text" name="email" value="${muserMap.email }" style="width: 160px"></input>
		 			</li>
		 			
		 			<li style="width:320px;display: none;"><span  class="lleft">&nbsp;</span>
		 				<input type="file" name="file11" accept="image/*"/>
		 				<input type="file" name="file22" accept="image/*"/>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft">&nbsp;</span>
		 				<a onclick="file(1)"><img src="/themes/default/images/pic.png" alt="附件1" /></a>
		 				<a onclick="file(2)" style="margin-left: 20px;"><img src="/themes/default/images/pic.png" alt="附件2" /></a>
		 			</li>
		 			
		 			<li style="width:320px;height: 33px;"><span  class="lleft">是否开票：</span>
		 				<select name="liced" style="width: 168px;">
                               <option value="Y" ${muserMap['liced'] eq 'Y'?'selected':''}>是</option>
                               <option value="N" ${muserMap['liced'] eq 'N'?'selected':''}>否</option>
                          </select>
		 			</li>
		 			
		 			<li style="width:320px"><span  class="lleft">合同开始日期：</span>
		 			<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')}'})"   type="text"  style="width:160px" name="htDateBegin" value="${muserMap['ht_date_begin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		 			</li>
		 			<li style="width:320px"><span  class="lleft">合同结束日期：</span>
		 			<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}'})"  type="text" style="width:160px" name="htDateEnd" value="${muserMap['ht_date_end']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		 			</li>
		 			
		 			
		 			</ul>
		 			<ul style="padding-top:20px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
		 		<%-- 	
		 				<li style="width:320px"><span  class="lleft">所属快递员：</span>
		 			<select id="courierNo" name="courierNo" style="width: 168px">
		 			  <option value="">请选择</option>
							<c:forEach items="${couriers}" var="item" varStatus="status">
								<option   value="${item.courier_no }"  <c:out value="${muserMap.courier_no eq item.courier_no?'selected':'' }"/>>${item.real_name }</option>
							</c:forEach>
					</select>
					</li> --%>
					
					<li style="width:320px"><span  class="lleft">所属快递员：</span>
		 				<input  maxlength="20" type="text" name="courierNo" id="courierNo" value="${muserMap.courier_no }" style="width: 160px"></input>
		 			</li>
		 			
		 			<%-- <li style="width:320px"><span  class="lleft">所属网点：</span>
		 			<select id="substationNo" name="substationNo" style="width: 168px">
		 			<option value="">请选择</option>
							<c:forEach items="${substations}" var="item" varStatus="status">
								<option   value="${item.substation_no }" id="${item.substation_name }" <c:out value="${muserMap.substation_no eq item.substation_no?'selected':'' }"/>>${item.substation_name }</option>
							</c:forEach>
					</select>
					</li> --%>
					
					<li style="width:320px"><span  class="lleft">所属网点：</span>
		 				<input  maxlength="20" disabled="disabled" type="text" name="substationNo" id="substationNo" value="${muserMap.substation_no }" style="width: 160px"></input>
		 			</li>		
		 	
					
					<li style="width:670px"><span  class="lleft">市场员：</span>
		 				<input  maxlength="20" type="text" name="markName" value="${muserMap.mark_name }" style="width: 160px"></input>
		 			</li>
		 			 
                    <li style="width:670px"><span  class="lleft">备注：</span>
		 				<input  maxlength="20" type="text" name="note" value="${muserMap.note }" style="width: 480px"></input>
		 			</li>
	 			</ul>
		 	</div>
		 	<div class="operator"  style="padding-top: 8px">
	 		<input class="button input_text  big blue_flat" style="margin-left: 150px;" type="submit" id="submit" value="保存" />
	 	</div>
	</div>
</form>
</body>
</html>