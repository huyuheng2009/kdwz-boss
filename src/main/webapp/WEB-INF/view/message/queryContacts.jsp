<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <style>
            .sms{ overflow:auto; zoom:1; background: #fff; border: 1px solid #ddd; border-bottom: 0; border-top: 0;}
            .sms_info{ margin-left:20px;float:left;}
            .sms_info ul{ overflow:auto; zoom:1; }
            .sms_info ul li{ float:left; margin-right:40px; height:40px; line-height:40px;} 
            .sms_info ul li strong{ margin-right:10px;}
            .sms_info ul li b{ font-weight:normal; color:red;}
            .sms_a{ float:left; height:40px; line-height:40px; color:#1c7ad8;text-decoration:underline;}


            .sms_pop{ margin: 20px 0;}
            .sms_pop_tit{ text-align:center; font-weight:bold; font-size:15px;}
            .sms_pop ul{ margin-top:10px;}
            .sms_pop ul li{ height:26px; line-height:26px; text-align:left;}
            .sms_pop ul li strong{ width:40%; text-align:right; display:inline-block; margin-right:10px;}
        </style>
        <script type="text/javascript">

        </script>
    </head>
    <body>
        <div class="sms_pop"><!--联系人资料弹出框-->
            <div class="sms_pop_tit">支付界科技有限公司</div>
            <ul>
                <li><strong>联系人：</strong>黄超</li>
                <li><strong>联系电话：</strong>18938880515</li>
                <li><strong>QQ：</strong>1176695067</li>
                <li><strong>Email：</strong>hc@yogapay.com</li>
            </ul>
        </div>
    </body>

</html>