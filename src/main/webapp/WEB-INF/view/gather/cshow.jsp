<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>
    <script type="text/javascript">
  

        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload() ;
       	});
            
            var slist = eval('${slist}');
        	var clist = eval('${clist}');
        	var sres = [];
        	var cres = [];
        	$.each(slist, function(i, item) {
        		sres[i]=item.substation_no.replace(/\ /g,"")+'('+item.substation_name.replace(/\ /g,"")+')';
            });
           $.each(clist, function(i, item) {
            	cres[i]=item.courier_no.replace(/\ /g,"")+'('+item.real_name.replace(/\ /g,"")+')';
            }); 
           
           $jqq("#courierNo").autocomplete(cres, {
        		minChars: 0,
        		max: 12,
        		autoFill: true,
        		mustMatch: false,
        		matchContains: true,
        		scrollHeight: 220,
        		formatItem: function(data, i, total) {
        			return data[0];
        		}
        	}).result(function(event, data, formatted) {
        		if(data[0].indexOf(')')>-1){
        			 $("#courierNo").val(data[0].substring(0,data[0].indexOf('('))) ;
        			// $("#ctips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
  			       } 
        	}); 
           
              $jqq("#substationNo").autocomplete(sres, {
           		minChars: 0,
           		max: 12,
           		autoFill: true,
           		mustMatch: false,
           		matchContains: true,
           		scrollHeight: 220,
           		formatItem: function(data, i, total) {
           			return data[0];
           		}
           	}).result(function(event, data, formatted) {
         		if(data[0].indexOf(')')>-1){
        			 $("#substationNo").val(data[0].substring(0,data[0].indexOf('('))) ;
        			// $("#stips").html(data[0].substring(data[0].indexOf('(')+1,data[0].indexOf(')')));
    			       } 
        	});	
      


            
        });
        
        
      
        
        

    </script>
</head>
<body>
<div class="search">
<div class="tableble_search">
        <div class=" search_cont">
    <form:form id="trans" action="${ctx}/gather/list" method="get">
     <input type="hidden" name="serviceName" value=""/>
        <ul>
            <li>网点编号：<input  name="substationNo" id="substationNo"  value="${params['substationNo']}" style="width: 200px;"  type="text"/></li>
             <li>快递员编号：<input  name="courierNo" id="courierNo"  value="${params['courierNo']}"  style="width: 200px;"  type="text"/></li>
         	      <li><span>账单日期：</span>
			 	<input onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{\'%y-%M-%d\'}'})"   type="text"  style="width:112px" name="settleTime" value="${params['settleTime']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	</li>
            <li><input class="button input_text  medium blue_flat"
                       type="submit" value="查询"/>
            </li>
            <li><input class="button input_text  medium blue_flat" 
                       type="reset" value="重置"/>
            </li>
        </ul>
    </form:form>
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
            
             <th align="center" >序号</th>
            <th align="center" >网点编号</th>
            <th align="center" >快递员编号</th>
            <th  align="center" >快递员</th>
            <th align="center" >账单时间</th>
            <th  align="center" >录入时间</th>
            <th  align="center" >邮费现金</th>
            <th  align="center" >代收款</th>
            <th align="center" >月结账号</th>
            <th  align="center" >月结金额</th>
            <th  align="center" >月结类型</th>
            <th  align="center" >账单月份</th>
            <th  align="center" >合计</th>
            <th  align="center" >备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td align="center">${status.count}</td>
                <td>${fn:substring(item.substation_no,fn:length(item.substation_no)-3,fn:length(item.substation_no))}</td>
                <td>${fn:substring(item.courier_no,fn:length(item.courier_no)-3,fn:length(item.courier_no))}</td>
                <td>${item.real_name}</td>
                 <td>${item.settle_time}</td>
                 <td><fmt:formatDate value="${item.create_time}" type="both"/></td>
                <td>${item.fcount}</td>
                <td>${item.ccount}</td>
                <td>${item.month_settle_no}</td>
                <td>${item.mcount }</td>
                <td>${item.mtype }</td>
                <td>${item.msettle_date}</td>
                <td>${item.acount}</td>
                <td>${item.note}</td>
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