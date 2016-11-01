<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <script type="text/javascript">

        </script>
    </head>
    <body>
        <div class="search">
            <div class="tableble_search">
                <div class=" search_cont">
                    <form id="trans" action="${ctx}/messageInfo/queryInfo" method="post">
                    
                        <ul>
                            <li><span>充值时间：</span>
                                <input id="dateBegin" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="beginTimeText" value="${params['beginTimeText']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
                                ~
                                <input id="dateEnd"  onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'dateBegin\')}', maxDate: '%y-%M-%d'})"  type="text" style="width:112px" name="endTimeText" value="${params['endTimeText']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"/>
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
                        <th  align="center" >充值时间</th>
                        <th  align="center" >账户名称</th>
                        <th  align="center" >充值金额</th>
                        <th  align="center" >单价</th>
                        <th  align="center" >数量</th>
                        <th  align="center" >收款人</th>
                        <th  align="center" >登记人</th>     
                       

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list.list}" var="item" varStatus="status">
                        <tr class="over_${status.count}" over="over_${status.count}">
<!--                            <td  rowspan="2" align="center"><input class="ids" name="ids" type="checkbox" value="${item.id}" /></td>-->
                            <td><fmt:formatDate value="${item.createTime}" type="both"/></td>
                            <td>${item.companyName}</td>
                            <td>￥<fmt:formatNumber value="${item.totalPrice/100}" pattern="#0.00#" /></td>                            
                            <td>￥<fmt:formatNumber value="${item.price/100}" pattern="#0.00#" /></td>
                            <td>${item.quantity}</td>
                            <td>${item.payee}</td>
                            <td>${item.userName}</td>                           
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