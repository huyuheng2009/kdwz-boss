<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
        <script>
			var $jqq = jQuery.noConflict(true);
        </script> 
        <script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
        <script>
			function importSubstation() {
				var action = $("form:first").attr("action");
				$("form:first input[name=serviceName]").val('dayCourierExport');
				$("form:first").attr("action", "${ctx}/substatic/export").submit();
				$("form:first").attr("action", action);
				 loaddata_end() ;
			}
			$(function() {

//                var courierList = ${courierList};
//                var cres = [];
//                $.each(courierList, function(i, item) {
//                    cres[i] = item.courier_no.replace(/\ /g, "") + '(' + item.real_name.replace(/\ /g, "") + ')';
//                });
//
//                $jqq("#courierNo").autocomplete(cres, {
//                    minChars: 0,
//                    max: 12,
//                    autoFill: true,
//                    mustMatch: false,
//                    matchContains: true,
//                    scrollHeight: 220,
//                    formatItem: function(data, i, total) {
//                        return data[0];
//                    }
//                }).result(function(event, data, formatted) {
//                    if (data[0].indexOf(')') > -1) {
//                        $("#courierNo").val(data[0].substring(0, data[0].indexOf('(')));
//                    }
//                });

				var data1 = ${courierList};
				// $.getJSON("${ctx}/lgc/calllist", function(data1) {
				var availablesrcKey1 = [];
				$.each(data1, function(i, item) {
					var inner_no = "";
					if (item.inner_no) {
						inner_no = item.inner_no + ','
					}
					availablesrcKey1[i] = inner_no + '(' + item.real_name.replace(/\ /g, "") + ')';
				});
				val1 = '';
				$jqq("#courierNo").autocomplete(availablesrcKey1, {
					minChars: 0,
					max: 12,
					autoFill: true,
					mustMatch: false,
					matchContains: true,
					scrollHeight: 220,
					formatItem: function(data11, i, total) {
						return data11[0];
					}
				}).result(function(event, data, formatted) {
					if (data[0].indexOf(')') > -1) {
						$("#courierNo").val(data[0].substring(data[0].indexOf('(') + 1, data[0].indexOf(')')));
					}
				});
			})
			
			function courierNo(){
				var orderBy = $('input[name=orderBy]').val();
				if('1'==orderBy){
					$('input[name=orderBy]').val('2');
				}else{
					$('input[name=orderBy]').val('1');
				}
				$("form:first").submit();
			}
			function courierName(){
				var orderBy = $('input[name=orderBy]').val();
				if('3'==orderBy){
					$('input[name=orderBy]').val('4');
				}else{
					$('input[name=orderBy]').val('3');
				}
				$("form:first").submit();
			}
			
			
        </script>
    </head>
    <body>
        <div class=" soso"><!--按钮上下结构-->    
            <!--按钮左右结构-->
            <div class="soso_left">  	  
                <form id="myform" action="queryDayCourierInfo" method="post" onsubmit="loaddata()">
                    <div class="soso_li">
                        <div class="soso_b">账单时间</div>
                        <div class="soso_input">
                            <input type="hidden" name="serviceName" value="" />
                            <input type ="hidden"  name ="orderBy" value="${params['orderBy']}"/>
                            <input type="hidden" name="ff" value="${params['ff']}"/>
                            <input class="time_bg" type="text" id="beginTime" name="beginTime" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, maxDate: '#F{$dp.$D(\'endTime\')||\'%y-%M-%d \'}'})" placeholder=" " value="${params['beginTime']}" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd '})"> - <input class="time_bg" id="endTime" type="text" name="endTime" onFocus="WdatePicker({isShowClear: false, dateFmt: 'yyyy-MM-dd', readOnly: true, minDate: '#F{$dp.$D(\'beginTime\')}', maxDate: '%y-%M-%d '})" placeholder=" " value="${params['endTime']}">
                                    </div>
                                    <div class="soso_b" style="margin-left: 10px;">网点</div>
                                    <div class="soso_input">
                                        <select id="substationNo" name="substationNo"  class="time_bg" style="width: 180px">
                                            <option value="">--全部--</option>
                                            <c:forEach items="${substations}" var="item" varStatus="status">
                                                <option value="${item.substation_no }" <c:out value="${params['substationNo'] eq item.substation_no?'selected':'' }"/> >${item.substation_name }</option>
                                            </c:forEach>
                                        </select>
                                    </div>        
                                    <div class="soso_b" style="margin-left: 10px;">快递员</div>
                                    <div class="soso_input">
                                        <input class="time_bg" type="text" id="courierNo" name="courierNo"  value="${params['courierNo']}">
                                    </div>

                                    </div>
                                    <div class="soso_search_b">
                                        <!--                                    <div class="soso_button"><button>7天</button></div>
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
                                                <a href="javascript:void(0);" id="importSubstation" class="button_2" onclick="importSubstation();">导出</a>	
                                            </div>
                                        </div>
                                       <div class="ta_table"  style="position: relative;" >
                                        <div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
                                             <table class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
                                                <tr>
                                                    <th>账单时间</th>
                                                    <th>网点编号</th>
                                                    <th>网点名称</th>                                               
                                                    <th><a onclick="courierNo()">快递员编号</a></th>
                                                    <th><a onclick="courierName()">快递员名称</a></th>
                                                    <th>收件票数</th>
                                                    <th>寄付现金</th>
                                                    <th>寄付月结</th>
                                                    <th>寄付会员</th>
                                                    <th>外件票数</th>
                                                    <th>外件金额</th>
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
                                             	<td>${item.timeJoin1} </td>
                                             	<td>${item.subtationInnerNo} </td>
                                             	<td>${item.substation_name} </td>
                                             	<td>${item.courierInnerNo} </td>
                                             	<td>${item.real_name} </td>
                                             	<td>${item.takeCount} </td>
                                             	<td>${item.takeCashCol} </td>
                                             	<td>${item.takeMonthCol} </td>
                                             	<td>${item.takeHuiyuanCol} </td>
                                             	<td>${item.takeForCount} </td>
                                             	<td>${item.takeForCol} </td>
                                             	<td>${item.sendCount} </td>
                                             	<td>${item.sendCashCol} </td>
                                             	<td>${item.sendMonthCol} </td>
                                             	<td>${item.sendHuiyuanCol} </td>
                                             	<td>${item.sendGoodPrice} </td>
                                             	<td>${item.sumCash} </td>
                                             	<td>${item.sumMonth} </td>
                                             	<td>${item.sumHuiyuan} </td>
                                             	<td>${item.sumAll} </td>                                            
                                                    </tr>
                                                    </c:forEach>
                                                 
                                                    <tr>
                                                   <td></td> 
                                                   <td></td> 
                                                   <td></td> 
                                                   <td></td> 
                                                   <td></td> 
                                                   <td>${takeCount}</td> 
                                                   <td>${takeCash}</td> 
                                                   <td>${takeMonth}</td> 
                                                   <td>${takeHuiyuan}</td> 
                                                   <td>${forCount}</td> 
                                                   <td>${forSum}</td> 
                                                   <td>${sendCount}</td> 
                                                   <td>${sendCash}</td> 
                                                   <td>${sendMonth}</td> 
                                                   <td>${sendHuyuan}</td> 
                                                   <td>${sendGoodPrice}</td> 
                                                   <td>${sumCash}</td> 
                                                   <td>${sumMonth}</td> 
                                                   <td>${sumHuiyuan}</td> 
                                                   <td>${sumAll}</td>                                                
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
