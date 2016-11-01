<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>

        <script type="text/javascript" src="/scripts/jquery-auto.js"></script>  
        <script>
            function importCompany() {
                var action = $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('dayCompanyExport');
                $("form:first").attr("action", "${ctx}/substatic/export").submit();
                $("form:first").attr("action", action);
            }
        </script>
    </head>
    <body>
        <div class=" soso"><!--按钮上下结构-->    
            <!--按钮左右结构-->
            <div class="soso_left">  	  
                <form id="myform" action="queryDayCompanyInfo" method="post"  onsubmit="loaddata()">
                  <input type="hidden" name="ff" value="${params['ff']}"/>
                    <div class="soso_li">
                        <div class="soso_b">账单时间</div>
                        <div class="soso_input">
                            <input type="hidden" name="serviceName" value="" />
                            <input class="time_bg" type="text" id="beginTime" name="beginTime" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'endTime\')||\'%y-%M-%d \'}'})" placeholder=" " value="${params['beginTime']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})"> - <input class="time_bg" id="endTime" type="text" name="endTime" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'beginTime\')}', maxDate: '%y-%M-%d'})" placeholder=" " value="${params['endTime']}"></div>
                                    </div>
                                    <div class="soso_search_b">
                                        <!--                                        <div class="soso_button"><button>7天</button></div>
                                                                                <div class="soso_button"><button>8天</button></div>-->
                                        <div class="soso_button"><input type="submit" value="查询"></div>
                                        <div class="soso_button"><input type="reset" value="重置"></div>
                                    </div>
                                    </form>
                                    </div>    
                                    </div>
                                    <div class="table">
                                        <div class="ta_header">
                                            <div class="ta_butt">                                                                              
                                                <a href="javascript:void(0);" id="importCompany" class="button_2" onclick="importCompany();">导出</a>
                                            </div>
                                        </div>
                                       <div class="ta_table"  style="position: relative;" >
                                        <div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
                                            <table class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
                                                <tr>
                                                    <th>账单时间</th>
<!--                                                    <th>问题件</th>-->
                                                    <th>收件票数</th>
                                                    <th>寄付现金</th>
                                                    <th>寄付月结</th>
                                                    <th>寄付会员</th>
                                                    <th>派件票数</th>
                                                    <th>到付现金</th>
                                                    <th>到付月结</th>
                                                    <th>到付会员</th>
                                                    <th>派件代收</th>      
                                                    <th>现金合计</th>
                                                    <th>月结合计</th>
                                                    <th>会员合计</th>
                                                    <th>总计</th>
                                                </tr>                                                
                                                <c:forEach items="${list.list}" var="item" varStatus="status">
                                                    <tr>
                                                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.timejoin}" /></td>
<!--                                                        <td>${item.wtcount}</td>-->
                                                        <td>${item.jcount == null ? 0:item.jcount}</td>
                                                        <td><c:if test="${item.jcashMoney == null}">0.00</c:if><fmt:formatNumber value="${item.jcashMoney}" pattern="#,##0.00#" /></td>
                                                        <td><c:if test="${item.jmonthMoney == null}">0.00</c:if><fmt:formatNumber value="${item.jmonthMoney}" pattern="#,##0.00#" /></td>
                                                        <td><c:if test="${item.jmemberMoney == null}">0.00</c:if><fmt:formatNumber value="${item.jmemberMoney}" pattern="#,##0.00#" /></td>
<!--                                                        <td>${item.jtotalMoney}</td>-->
                                                        <td>${item.dcount == null ? 0:item.dcount}</td>
                                                        <td><c:if test="${item.dcashMoney == null}">0.00</c:if><fmt:formatNumber value="${item.dcashMoney}" pattern="#,##0.00#" /></td>
                                                        <td><c:if test="${item.dmonthMoney == null}">0.00</c:if><fmt:formatNumber value="${item.dmonthMoney}" pattern="#,##0.00#" /></td>   
                                                        <td><c:if test="${item.dmemberMoney == null}">0.00</c:if><fmt:formatNumber value="${item.dmemberMoney}" pattern="#,##0.00#" /></td>
<!--                                                        <td>${item.dtotalMoney}</td>-->
                                                <c:set var="goodPrice" value="${item.dgoodMoney==null?0.0:item.dgoodMoney}"></c:set>
                                                        <td><c:if test="${item.dgoodMoney == null}">0.00</c:if><fmt:formatNumber value="${item.dgoodMoney}" pattern="#,##0.00#" /></td>
                                                        <td>                                                           
                                                            <c:choose>
                                                                <c:when test="${item.jcashMoney == null && item.dcashMoney != null}"><fmt:formatNumber value="${item.dcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.dcashMoney+goodPrice}"></c:set></c:when>
                                                                <c:when test="${item.dcashMoney == null && item.jcashMoney != null}"><fmt:formatNumber value="${item.jcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.jcashMoney+goodPrice}"></c:set></c:when>
                                                                <c:when test="${item.jcashMoney != null && item.dcashMoney != null}"><fmt:formatNumber value="${item.dcashMoney + item.jcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.dcashMoney + item.jcashMoney+goodPrice}"></c:set></c:when>                                                            
                                                                <c:otherwise>  
                                                                    <c:set var="jcash" value="0.00"></c:set>0.00
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${item.jmonthMoney == null && item.dmonthMoney != null}"><fmt:formatNumber value="${item.dmonthMoney}" pattern="#,##0.00#" /><c:set var="jmonth" value="${item.dmonthMoney}"></c:set></c:when>
                                                                <c:when test="${item.dmonthMoney == null && item.jmonthMoney != null}"><fmt:formatNumber value="${item.jmonthMoney}" pattern="#,##0.00#" /><c:set var="jmonth" value="${item.jmonthMoney}"></c:set></c:when>
                                                               <c:when test="${item.jmonthMoney != null && item.dmonthMoney != null}"><fmt:formatNumber value="${item.dmonthMoney+item.jmonthMoney}" pattern="#,##0.00#" /><c:set var="jmonth" value="${item.dmonthMoney+item.jmonthMoney}"></c:set></c:when>
                                                                <c:otherwise>    
                                                                     <c:set var="jmonth" value="0.00"></c:set>0.00
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td><c:choose>
                                                                <c:when test="${item.jmemberMoney == null && item.dmemberMoney != null}"><fmt:formatNumber value="${item.dmemberMoney}" pattern="#,##0.00#" /><c:set var="jmember" value="${item.dmemberMoney}"></c:set></c:when>
                                                                <c:when test="${item.dmemberMoney == null && item.jmemberMoney != null}"><fmt:formatNumber value="${item.jmemberMoney}" pattern="#,##0.00#" /><c:set var="jmember" value="${item.jmemberMoney}"></c:set></c:when>
                                                                 <c:when test="${item.jmemberMoney != null && item.dmemberMoney != null}"><fmt:formatNumber value="${item.dmemberMoney+item.jmemberMoney}" pattern="#,##0.00#" /><c:set var="jmember" value="${item.dmemberMoney+item.jmemberMoney}"></c:set></c:when>
                                                                <c:otherwise>    
                                                                     <c:set var="jmember" value="0.00"></c:set>0.00
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                           <fmt:formatNumber value="${jcash+jmonth+jmember}" pattern="#,##0.00#" />
                                                        </td>
                                                    </tr>
                                                </c:forEach>                                            
                                                <tr>
                                                    <td>总计</td>                                             
<!--                                                    <td>${mapcount.wtcount}</td>-->
                                                    <td>${mapcount.jcount == null ? 0: mapcount.jcount}</td>
                                                    <td><c:if test="${mapcount.jcashMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.jcashMoney}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.jmonthMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.jmonthMoney}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.jmemberMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.jmemberMoney}" pattern="#,##0.00#" /></td>
<!--                                                    <td>${mapcount.jtotalMoney}</td>-->
                                                    <td>${mapcount.dcount == null ? 0: mapcount.dcount}</td>
                                                    <td><c:if test="${mapcount.dcashMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.dcashMoney}" pattern="#,##0.00#" /></td>   
                                                    <td><c:if test="${mapcount.dmonthMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.dmonthMoney}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.dmemberMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.dmemberMoney}" pattern="#,##0.00#" /></td>
<!--                                                    <td>${mapcount.dtotalMoney}</td>  -->
                                                    <td><c:if test="${mapcount.dgoodMoney == null}">0.00</c:if><fmt:formatNumber value="${mapcount.dgoodMoney}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.xjtotal == null}">0.00</c:if><fmt:formatNumber value="${mapcount.xjtotal}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.yjtotal == null}">0.00</c:if><fmt:formatNumber value="${mapcount.yjtotal}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.hytotal == null}">0.00</c:if><fmt:formatNumber value="${mapcount.hytotal}" pattern="#,##0.00#" /></td>
                                                    <td><c:if test="${mapcount.total == null}">0.00</c:if><fmt:formatNumber value="${mapcount.total}" pattern="#,##0.00#" /></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="height02"></div>
                                    <div id="page">
                                        <pagebar:pagebar total="${list.pages}"
                                                         current="${list.pageNum}" sum="${list.total}" />
                                    </div>
                                    <!--                                <div class="sx_page">
                                                                        <div class="sx_page_p">共100条记录，每页10条，共99页</div>
                                                                        <div class="sx_page_list">
                                                                            <ul>
                                                                                <li><a href="#" class="sx_page_li"><span class="sx_page_ex"></span></a></li>
                                                                                <li><a href="#" class="sx_page_li li_on">1</a></li>
                                                                                <li><a href="#" class="sx_page_li">2</a></li>
                                                                                <li><a href="#" class="sx_page_li">3</a></li>
                                                                                <li>···</li>
                                                                                <li><a href="#" class="sx_page_li">4</a></li>
                                                                                <li><a href="#" class="sx_page_li">5</a></li>
                                                                                <li><a href="#" class="sx_page_li"><span class="sx_page_ne"></span></a></li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>-->
                                    </body>
                                    </html>
