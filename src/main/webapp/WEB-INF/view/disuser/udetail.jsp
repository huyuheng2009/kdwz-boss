<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/header.jsp"%>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<style>
*{padding:0;margin:0;}
.lleft{width: 115px;text-align: right;float: left;}
li {padding: 4px 0px;}
</style>
    <script type="text/javascript">
    
    $(function(){
		
  });
 
    </script>
</head>
<body>
   
   	<div class="content">
		 	<div class="item">
		 		<input  type="hidden" id="id" name="id" value="${muserMap.id }"/>
	 			<ul style="padding-top:30px;padding-left: 5px;border: 1px;border-bottom-style: solid;border-color:#D4CDCD;float: left;">
		 			
                     <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员账号：</span>
		 				<span style="width:160px; ">${disUser.dis_user_no}</span>
		 			</li>

		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员分站：</span>
		 				<span style="width:160px; ">${disUser.substation_name}</span>
		 			</li>
		 			
		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员余额：</span>
		 				<span style="width:160px; ">${disUser.balance}</span>
		 			</li>

		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>会员名称：</span>
		 				<span style="width:160px; ">${disUser.dis_user_name}</span>
		 			</li>
		 			
		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>适用优惠：</span>
		 				<span style="width:160px; ">${disUser.disText}</span>
		 			</li>

		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>联系人：</span>
		 				<span style="width:160px; ">${disUser.contact_name}</span>
		 			</li>
		 			
		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>登记人：</span>
		 				<span style="width:160px; ">${disUser.operator}</span>
		 			</li>

		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>联系电话：</span>
		 				<span style="width:160px; ">${disUser.contact_phone}</span>
		 			</li>
		 			
		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>注册时间：</span>
		 				<span style="width:160px; ">${disUser.create_time}</span>
		 			</li>

		 			 <li style="width:320px"><span  class="lleft"><span style="color: red;">*&nbsp;</span>邮      箱：</span>
		 				<span style="width:160px; ">${disUser.email}</span>
		 			</li>
		 			
		 			<li style="width:670px"><span  class="lleft" style="height: auto;">备注：</span>
		 				<span style="width:480px; display: inline-block;">${disUser.gnote}</span>
		 			</li>
		 			</ul>

		 	</div>
	</div>
   


</body>

</html>