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
			function settleCreate() {
			
			var beginTime =  $('input[name=beginTime]').val();
			var endTime =  $('input[name=endTime]').val();
			var substationNo =  $('input[name=substationNo]').val();
			var courierNo =  $('input[name=courierNo]').val();
			
			var ids = '';
			$('.ids').each(function(){
                if($(this).prop("checked"))	{
                 	if(!$(this).prop('disabled')){
                 		 ids += $(this).val()+','; 
              	   }
     			 }
      	    });
			 if(ids.length>0){
    		  ids = ids.substring(0, ids.length-1) ;
    	    }else{
    		 alert("请新添加一项或多项！");
    		return ;
    	 } 
			
		if(confirm("是否生成账单？")){
       		  $.ajax({	 
       			 type: "post",//使用get方法访问后台
     	             dataType: "text",//返回json格式的数据
     	            url: '/settle/settleCreate',
     	            data: {'ctime':ids,'beginTime': beginTime,'endTime': endTime,'substationNo': substationNo,'courierNo':courierNo}, 
     	            success: function (msg) {
                    if (msg == 1) {
                        alert('生成成功');
                    } else {
                        alert(msg);
                    }
                }});
          } 
			
			
			}
			$(function() {
				
				loaddata_end() ;
			     
		         $('.select_all').click(function() {
		           	 if($(this).prop("checked"))	{
		           			$('input[name=ids]').each(function(){
		           				if(!$(this).prop('disabled')){
		           					$(this).prop('checked',true); 
		           				}
		           				
		                   	}); 
		           	 }else{
		           		 $('input[name=ids]').each(function(){
		           			if(!$(this).prop('disabled')){
	           					$(this).prop('checked',false); 
	           				}
		                	}); 
		           	 }
		           
		           });
				
		         
				
				
	         	jQuery.ajax({
	    		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
	    		      dataType: "script",
	    		      cache: true
	    		}).done(function() {
				var data1 =  tmjs.clist ;
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
			})
        </script>
    </head>
    <body>
        <div class=" soso"><!--按钮上下结构-->    
            <!--按钮左右结构-->
            <div class="soso_left">  	  
                <form id="myform" action="queryDayCourierInfo" method="post"  onsubmit="loaddata()">
                    <div class="soso_li">
                        <div class="soso_b">账单时间</div>
                        <div class="soso_input">
                            <input type="hidden" name="serviceName" value="" />
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
                                                <a href="javascript:void(0);" id="settleCreate" class="button_2" onclick="settleCreate();">生成账单</a>	
                                            </div>
                                        </div>
                                        <div class="ta_table"  style="position: relative;" >
                                        <div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
                                            <table class="ta_ta" width="100%" cellpadding="0" cellspacing="0" border="0">
                                                <tr>
                                                   <th align="center" class="nosort"><input class="select_all" name="select_all" type="checkbox"  /></th>
                                                    <th>账单时间</th>
                                                    <th>网点编号</th>
                                                    <th>网点名称</th>                                               
                                                    <th>快递员编号</th>
                                                    <th>快递员名称</th>
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
                                                    <th>操作</th>                  
                                                </tr>                                                
                                                <c:forEach items="${list.list}" var="item" varStatus="status">
                                                    <tr>
                                                      <td align="center"><input  class="ids" name="ids" type="checkbox" <c:out value="${!empty item.cdsid?'checked':''}" /> value="${item.ctime}" /></td>
                                                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.timejoin}" /></td>
                                                          <td>${item.scno} </td>
                                                          <td>  ${item.scname} </td>
                                                        <td>                                                           
                                                            ${item.cinnerNo}
                                                        </td>
                                                        <td>
                                                            ${item.courierName}                                                             
                                                        </td>
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
                                                        <td  style="${item.goodNew eq 'Y'?'color: red':''}"><c:if test="${item.dgoodMoney == null}">0.00</c:if><fmt:formatNumber value="${item.dgoodMoney}" pattern="#,##0.00#" /></td>    
                                                            <td style="${item.cashNew eq 'Y'?'color: red':''}">                                                           
                                                            <c:choose>
                                                                <c:when test="${item.jcashMoney == null && item.dcashMoney != null}"><fmt:formatNumber value="${item.dcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.dcashMoney+goodPrice}"></c:set></c:when>
                                                                <c:when test="${item.dcashMoney == null && item.jcashMoney != null}"><fmt:formatNumber value="${item.jcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.jcashMoney+goodPrice}"></c:set></c:when>
                                                                <c:when test="${item.jcashMoney != null && item.dcashMoney != null}"><fmt:formatNumber value="${item.dcashMoney + item.jcashMoney+goodPrice}" pattern="#,##0.00#" /><c:set var="jcash" value="${item.dcashMoney + item.jcashMoney+goodPrice}"></c:set></c:when>                                                            
                                                                <c:otherwise>  
                                                                    <c:set var="jcash" value="0.00"></c:set>0.00
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td  style="${item.monthNew eq 'Y'?'color: red':''}">
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
                                                         <td>
                                                           <c:if test="${!empty item.cdsid}">已生成</c:if>                                               
                                                        </td>
                                                    </tr>
                                                </c:forEach>                                            
                                                <tr>
                                                <td></td>
                                                    <td>总计</td>      
                                                    <td></td>
                                                    <td></td>
                                                      <td></td>
                                                    <td></td>
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
                                                     <td></td>
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
