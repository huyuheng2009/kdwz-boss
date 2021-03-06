<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">		
        <title>${constants['backend_title']}</title>
        <jsp:include page="/WEB-INF/view/batch/head.jsp" />     
    </head>
    <body>

        <div class="gdt">
            <div class="down_fi">
                <div class="down_cont">
                    <div class="down_icon"><img src="<%=request.getContextPath()%>/statics/front/images/yes.png" width="70" height="70"></div>
                    <div class="down_p">
                        <p class="font_b">恭喜，您已经成功下单！ </p>                       
                    </div>     
                    <div class="down_butt"><a href="<%=request.getContextPath()%>${orderPath}" class="button_2">继续下单</a></div>   
                </div>
            </div>
        </div>       
        <c:if test="${errorMsg != null && errorMsg != ''}">         
            <script>
                            alert("${errorMsg}");
            </script>
        </c:if>
    </body>
</html>
