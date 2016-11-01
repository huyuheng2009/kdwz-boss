<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
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


            .sms_pop{}
            .sms_pop_tit{ text-align:center; font-weight:bold; font-size:15px;}
            .sms_pop ul{ margin-top:10px;}
            .sms_pop ul li{ height:26px; line-height:26px; text-align:left;}
            .sms_pop ul li strong{ width:40%; text-align:right; display:inline-block; margin-right:10px;}
        </style>
        <script type="text/javascript">
            function messageInfo() {
                $.dialog({
                    width: '400px',
                    height: '200px',
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                    title: '联系人',
                    content: "url:${ctx}/messageInfo/queryContacts?layout=no",
                    lock: true
                });
            }
        </script>
    </head>
    <body>
        <div class="sms"><!--信息显示-->
            <div class="sms_info">
                <ul>
                    <li><strong>账户名称：</strong>${countMap.realName}</li>
                    <%--<li><strong>当前余额：</strong><b>${countMap.messageCost * countMap.messagecount}</b></li>--%>
                    <li><strong>剩余条数：</strong><b>${countMap.messagecount}</b></li>
                </ul>
            </div>
           <!--  <a href="javascript:messageInfo();" class="sms_a">联系人</a> -->
        </div>
        <div class="search">

            <div class="tableble_search">

                <div class=" search_cont">
                    <form id="trans" action="${ctx}/messageInfo/queryMessage" method="post">

                        <ul>
                            <li><span>发送时间：</span>
                                <input id="dateBegin" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="beginTimeText" value="${params['beginTimeText']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
                                ~
                                <input id="dateEnd"  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'dateBegin\')}', maxDate: '%y-%M-%d'})"  type="text" style="width:112px" name="endTimeText" value="${params['endTimeText']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
                            </li>
                            <li class="soso_li">
                                <span>短信状态：</span>
                                <select name="rescode"  style="width: 110px">
                                    <option value="">--全部--</option>
                                    <c:forEach items="${messageMap}" var="item" varStatus="status">
                                        <option value="${item.key }" <c:out value="${params['rescode'] eq item.key?'selected':'' }"/> >${item.value}</option>
                                    </c:forEach>
                                </select>
                            </li>
                            <li><input class="button input_text  medium blue_flat"
                                       type="submit" value="查询"/>
                            </li>
                            <li><input class="button input_text  medium blue_flat" 
                                       type="reset" value="重置"/>
                            </li>
                        </ul>
                    </form>
                </div>   <!-- search_cont end  -->
                <div class="clear"></div>
            </div>   <!-- tableble_search end  -->   
            <div class="tableble_search">
                <div class="operator">
                    <div class="search_new">   <input class="button input_text  big gray_flat" type="submit" value="刷新" id="reload_btn"/>  </div>                    
                </div>
            </div>   <!-- tableble_search end  -->    
        </div>


        <div class="tbdata">
            <table width="100%" cellspacing="0" class="t2" id="t2">
                <thead>
                    <tr>
                        <!--                        <th  align="center" >&#12288;<input class="select_all" name="select_all" type="checkbox"  />&#12288;</th>-->
                        <th  align="center" >发送时间</th>
                        <th  align="center" >接受号码</th>
                        <th  align="center" >是否成功</th>
                        <th  align="center" >短信内容</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list.list}" var="item" varStatus="status">
                        <tr class="over_${status.count}" over="over_${status.count}">
<!--                            <td  rowspan="2" align="center"><input class="ids" name="ids" type="checkbox" value="${item.id}" /></td>-->
                            <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                            <td>${item.phone}</td>                            
                            <td>
                                <c:choose>
                                    <c:when test="${item.rescode eq '00'}">发送失败</c:when> 
                                    <c:when test="${item.rescode eq '01'}">发送成功</c:when>
                                    <c:otherwise>
                                        发送超时
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${item.content}</td>                                            
                        </tr>                       
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div id="page">
            <pagebar:pagebar total="${list.pages}"  current="${list.pageNum}" sum="${list.total}"/>
        </div>
    </body>

</html>