<%@page pageEncoding="utf8"%>
<%@include file="/tag.jsp"%>
<%@ include file="/header.jsp"%>
<html>
<head>
<style type="text/css">
li{
margin-bottom: 5px;
}
body{
padding:20px;
padding-left: 40px;
}
input{
width: 180px;
}
</style>
<script type="text/javascript">
function pwStrength(pwd) {
    var level = 0, strength = "O";
    if (pwd == null || pwd == '') {
        strength = "O";
    }
    else {
        var mode = 0;
        if (pwd.length <= 4)
            mode = 0;
        else {
            for (i = 0; i < pwd.length; i++) {
                var charMode, charCode;
                charCode = pwd.charCodeAt(i);
                // 判断输入密码的类型
                if (charCode >= 48 && charCode <= 57) //数字  
                    charMode = 1;
                else if (charCode >= 65 && charCode <= 90) //大写  
                    charMode = 2;
                else if (charCode >= 97 && charCode <= 122) //小写  
                    charMode = 4;
                else
                    charMode = 8;
                mode |= charMode;
            }
            // 计算密码模式
            level = 0;
            for (i = 0; i < 4; i++) {
                if (mode & 1)
                    level++;
                mode >>>= 1;
            }
        }
        switch (level) {
            case 0:
                strength = "O";
                break;
            case 1:
                strength = "L";
                break;
            case 2:
                strength = "M";
                break;
            default:
                strength = "H";
                break;
        }
    }
    return strength;
    
};
$(function(){
	var api = frameElement.api, W = api.opener,oldpwd,newpwd,repwd;
	$('#ok').click(function(){
		oldpwd = $('#oldpwd');
		newpwd = $('#newpwd');
		repwd = $('#repwd');
		if(oldpwd.val().length<2){
			alert('请输入现在使用的密码');
			oldpwd.focus();
			return;
		}
	    var pwdth = pwStrength(newpwd.val()) ;
		if(pwdth=='O'||pwdth=='L'){
			alert('新密码过于简单');
			newpwd.focus();
			newpwd.select();
			return;
		}
		
		if(newpwd.val().length<6||newpwd.val().length>20){
			alert('新密码长度不正确');
			newpwd.focus();
			newpwd.select();
			return;
		}
		if(newpwd.val()!=repwd.val()){
			alert('两次密码输入不一致');
			repwd.focus();
			repwd.select();
			return;
		}
		
		$.ajax({url:'${ctx}/savepwd',data:{'oldpwd':oldpwd.val(),'pwd':repwd.val()},success:function(msg){
			 if(msg==3){
				 alert('现在使用的密码不正确');
				 oldpwd.focus();
				 oldpwd.select();
			 }else if(msg==2){
				 alert('此用户不允许修改密码');
			 }else if(msg==1){
				 alert('密码修改成功');
				 api.close();
			 }
		 }});
		
		
	});
});


</script>
</head>
<body>
	<ul>
		<li>现在用的密码：<input type="password" id="oldpwd"/></li>
		<li>设置新的密码：<input type="password" id="newpwd"/> 6~12位数字与字母</li>
		<li>重复新的密码：<input type="password" id="repwd"/> 重新输入新密码</li>
		<li><input  id="ok" class="button input_text  big blue_flat" style="width: 90px;margin-top: 10px;" type="submit" value="确认修改" /></li>
	</ul>
</body>
</html>