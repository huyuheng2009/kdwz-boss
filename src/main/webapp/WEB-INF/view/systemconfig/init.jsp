<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    $(function(){
         $("#save").click(function(evt) {
        	 var lgcName =  $.trim($("input[name='lgcName']").val());
        	 var key =  $.trim($("input[name='key']").val());
        	 var login =  $.trim($("input[name='login']").val());
        	 if(lgcName.length<3){alert("请输入公司名称");return ;}
        	 var r1 = /^[a-zA-Z]{3,8}$/
        	 var r2 = /^[a-zA-Z0-9]{6}$/
        	 if(!r1.test(key)){alert("请输入正确的英文域名");return ;}
        	 if(!r2.test(login)){alert("请输入正确的验证码");return ;}
        	  $.ajax({url: '/systemconfig/save',async:true,beforeSend:loading,  data: {'lgcName':lgcName,'key':key,'login':login}, success: function (msg) {
        		  loaded() ;
                  if (msg == 1) {
                      alert('保存成功');
                  } else {
                      alert(msg);
                  }
              }});
         });
    	
    });   
    </script>
</head>
<body>
<div style="font-size: 16px;">
<span style="width: 100%;display: inline-block;margin: 30px 0 ;">&#12288;&#12288;&#12288;公司名称：<input type="text" name="lgcName" style="width:350px;height: 40px;line-height: 40px;font-size: 16px; "></input>&nbsp;3个字以上</span>		
<span style="width: 100%;display: inline-block;margin: 30px 0 ;">&#12288;&#12288;&#12288;英文域名：<input type="text" name="key"  style="width:350px;height: 40px;line-height: 40px;font-size: 16px; "></input>&nbsp;访问域名为： <span style="color: red;">***</span>.pro-boss.yogapay.com,<span style="color: red;">***</span>为所填内容,3-8位英文</span>	
<span style="width: 100%;display: inline-block;margin: 30px 0 ;">&nbsp;app登录验证码：<input type="text" name="login"  style="width:350px;height: 40px;line-height: 40px;font-size: 16px; "></input>&nbsp;登录app客户端使用的验证码，6位英文或数字</span>			
<span style="width: 100%;display: inline-block;margin: 30px 0 ;"><input class="button input_text  medium blue_flat sear_butt" style="font-size: 16px;width: 220px; height: 45px;line-height: 45px;margin: 0 auto 40px;margin-left:80px;border-radius: 5px;" id="save" type="button" value="新增接入"/></span>		               
 </div>   
                
</body>

</html>