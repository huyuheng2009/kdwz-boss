<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ ctx}/themes/default/download.css" />


    <script type="text/javascript">
    $(function(){
    	 
    	
    	
   /*  	$(".code").each(function(){
    		var t = toUtf8($(this).attr('url')) ;
    		 $(this).qrcode({
    		    	render: "table", //table方式
    		    	width: 240, //宽度
    		    	height:240, //高度
    		    	text: t
    		    });
       	});  */
    	
    	$(".code").each(function(){
    		var t = toUtf8($(this).attr('url')) ;
    		var qrcode = new QRCode(this, {
        		width : 240,
        		height : 240
        	});
    		qrcode.makeCode(t);
       	}); 
    	
   
    });   
    
    function toUtf8(str) {   
        var out, i, len, c;   
        out = "";   
        len = str.length;   
        for(i = 0; i < len; i++) {   
        	c = str.charCodeAt(i);   
        	if ((c >= 0x0001) && (c <= 0x007F)) {   
            	out += str.charAt(i);   
        	} else if (c > 0x07FF) {   
            	out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));   
            	out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));   
            	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
        	} else {   
            	out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));   
            	out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));   
        	}   
        }   
        return out;   
    } 
    </script>
</head>
<body>


<div class="download" style="min-height: 2300px;">
	<div class="download_tit">快递员端APP下载&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码：&nbsp;&nbsp;${cMap.lgc_key }</div>
	<div class="download_tit">月结客户批量下单地址:&nbsp;&nbsp;&nbsp;&nbsp;<a href="${cMap.web_xd_addr }">${cMap.web_xd_addr }</a></div>
    <div class="download_cont">
    	<div class="download_er" style="min-height: 182px;">
    	<c:forEach items="${cappVersions}" var="item" varStatus="status">
            <c:if test="${status.count==1}">
            <div class="download_li">
            	<div class="load_img"><div id="qrcode" class="code" url="http://${host}/download/app/${item.platform}" ></div></div>
                <div class="load_p">适用于${item.platform}</div>
            </div>
            </c:if>
             <c:if test="${status.count==2}">
            <div class="download_li02">
            	<div class="load_img"><div class="code" url="http://${host}/download/app/${item.platform}" ></div></div>
                <div class="load_p02">适用于${item.platform}</div>
            </div>
            </c:if>
           </c:forEach>      
       </div>
     </div>
        <div class="download_span" ><img src="${ ctx}/themes/default/images/load_span.png"></div>
    </div>
    
</div>


</body>

</html>