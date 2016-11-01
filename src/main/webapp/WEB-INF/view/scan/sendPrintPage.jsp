<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/table_new.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/printer.css"/>
<script type="text/javascript" src="${ ctx}/scripts/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery.PrintArea.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript">
    
  
       
    
        $(function () {
            trHover('t2');
            var api = frameElement.api, W = api.opener;
       	    api.lock();
       	  var style = W.getStyle() ;
    	    if(style==1){
    	    	$("table").addClass("ta_ta");
     		$("table th").addClass("num_all");
     		//$("table td").addClass("num_all");
     		
     		$(".tableble_search").addClass("soso");
    	    }
           
            
    	    $('#submit').click(function(){
    			 var extraCss = '' ;
    			 var keepAttr = [];
    			 keepAttr.push('id') ;
    			 keepAttr.push('class') ;
    			 keepAttr.push('style') ;
    			  var headElements =  '<style>.tableble_table tr td {height: 30px;}</style>';
    			 var options = {popHt : 600 ,popWd  : 860, mode : "popup", popClose : false, extraCss : extraCss, retainAttr : keepAttr, extraHead : headElements };
    		      $(".tbdata").printArea( options ); 
    		 });
            
        });
        
        

    </script>
</head>
<body>
	<div style="width: 100%;text-align: center;position: fixed;padding: 10px 0 ;float: left;background-color: #f5f5f5;;z-index:100;margin: 0;"><input class="sear_butt" type="button" id="submit" value="打印"/></div>
<div style="padding: 40px 0;"></div>

<div class="tbdata">

      <div class="ptitle"><u:dict name="S" key="${params['substationNo']}" />出站交接清单</div>
    	  
    <table width="98%" cellspacing="0" class="t2" id="t2" style="margin-left: 1%;">
   <thead>
        <tr>
            <th  align="center" >序号</th>
             <th  align="center" >寄件时间</th>
            <th  align="center" >运单号</th>
            <th  align="center" >下一站名称</th>
            <th  align="center" >备注</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
               <td align="center">${status.count}</td>
                <td><fmt:formatDate value="${item.order_time}" type="both"/></td>
               <td>${item.lgc_order_no}</td>
               <td><u:dict name="${item.next_type}" key="${item.next_no}" /></td>
               <td align="center"></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
     <div class="pt" >&nbsp;&nbsp;&nbsp;&nbsp;</div>
     <div class="pt" >打印时间：<span class="ptt" >${params['printTime']}</span>&nbsp;&nbsp;&nbsp;&nbsp;</div>
    <div class="pt" >打印人：<span class="ptt">${params['userName']}</span>&nbsp;&nbsp;&nbsp;&nbsp;</div>
    <div class="pt">接收网点签字：<span class="ptt"></span>&nbsp;&nbsp;&nbsp;&nbsp;</div>
    
    
</div>



</body>

</html>