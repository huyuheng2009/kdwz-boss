<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.springframework.web.servlet.mvc.condition.ParamsRequestCondition"%>
<%@page pageEncoding="utf8"%>
<%@include file="/tag.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
      <c:if test="${!empty lgcConfig.res.title}"><link rel="shortcut icon" href="${lgcConfig.res.title}"></c:if>
  <c:if test="${empty lgcConfig.res.title}"><link rel="shortcut icon" href="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_title.png"></c:if>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/reset.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/main.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/color.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/header.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/left_list.css"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
    <title>登录后台系统</title>

    <script type="text/javascript">
        function refresh(obj) {
            obj.src = "${ctx}/identifyImage?"+Math.random();
        }
    </script>
    
</head>
<body style="height:100%;">
  
<div class="login">
	<table class="logingin" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td>
  <form:form id="loginForm" action="${ctx}/login" method="post" name="jhform"  onsubmit="return check_bfr_submit();">
	<div class="login_logo">
   <c:if test="${!empty lgcConfig.res.logo_login}"><img src="${lgcConfig.res.logo_login}"  width="200" height="160"></c:if>
  <c:if test="${empty lgcConfig.res.logo_login}"><img src="${ctx }/themes/default/lgc_logo/${lgcConfig.key}_login.png"  width="200" height="160"></c:if>
  </div>
 
    <div class="login_cont" style="margin-top: 20px;">
   	  <div class="login_input"><input name="username" type="text" placeholder="用户" class="logininput"></div>
      <div class="login_input"><input name="password" type="password" placeholder="密码" class="logininput"></div>
     <%--  <div class="login_input padding100"><input name="captcha" type="text" placeholder="验证码" class="logininput check_input">
        <img src="${ctx}/identifyImage" title="点击更换" onclick="javascript:refresh(this);" class="check_img"></div> --%>
      <div class="input_login"><input type="submit" class="login_button" name="_do" value="登录" /></div>
    </div>
  </form:form>
  <div  style="margin-top:65px;font-size:18px;color:white;">
  <%
                                String msg = "";
                                    String error = (String) request.getAttribute

                                    (FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
                                    String flag = (String) request.getAttribute("LOGINFLAG");
                                    if (flag != null) {
                                        if (flag.endsWith("NoUpdatePasswordException")) {
                                            msg = "口令过期";
                                        }
                                    } else if (error != null) {
                                        if (error.endsWith("UnknownAccountException")) {
                                            msg = "用户登陆失败";
                                        }
                                        if (error.endsWith("FailTimesException")) {
                                            msg = "密码错误次数超限";
                                        }
                                        if (error.endsWith("RandomValidateCodeException")) {
                                            msg = "验证码输入有误";
                                        }
                                        if (error.endsWith("IncorrectCredentialsException")) {
                                            msg = "用户名或密码错";
                                        }
                                        if (error.endsWith("UserCloseException")) {
                                            msg = "用户被禁用";
                                        }
                                    }
                            %> <%=msg%>
       </div>
  	</td>
  	
    </tr>
    </table>
    
    <div class="login_footer" style="bottom: 40px;">
    <div style="display:inline-block;background:#0055ab;color: #ffe535;border-radius:2em;-moz-border-radius:2em;padding: 0 20px;margin-bottom: 20px;">温馨提示：为了不影响系统使用，请用谷歌浏览器！</div><br/>
    Copyright © 2012-2016. 深圳支付界科技有限公司. All Rights Reserved.粤ICP备14012895号</div>
    
</div>
  
</body>

</html>
