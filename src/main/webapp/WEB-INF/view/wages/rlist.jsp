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
                //location.reload()
           	 $("form:first").submit();
       	});
            
        	jQuery.ajax({
    		      url: '/codfile/tmjs/${lgcConfig.key}/${lgcConfig.curName}_${cmenu_sub_limit}.js',
    		      dataType: "script",
    		      cache: true
    		}).done(function() {
    		     // var slist = ${slist}; 
                var slist =  tmjs.slist ;
                // $.getJSON("${ctx}/lgc/calllist", function(data1) {
                 	var slists = [];
                     $.each(slist, function(i, item) {
                     	var inner_no = "" ;
                     	if(item.inner_no){inner_no=item.inner_no+','}
                     	slists[i]=item.substation_no.replace(/\ /g,"")+'('+inner_no+item.substation_name.replace(/\ /g,"")+')';
                     });
                     val1 = '';
                       $jqq("#substationNo").autocomplete(slists, {
                    		minChars: 0,
                    		max: 12,
                    		autoFill: true,
                    		mustMatch: false,
                    		matchContains: true,
                    		scrollHeight: 220,
                    		formatItem: function(slists, i, total) {
                    			return slists[0];
                    		}
                    	}).result(function(event, data, formatted) {
                     		if(data[0].indexOf(')')>-1){
                    			 $("#substationNo").val(data[0]) ;
              			       } 
                    	});	
               
                   	var data1 = tmjs.clist ;
       	         //var data1 = ${clist}; 
       	         // $.getJSON("${ctx}/lgc/calllist", function(data1) {
       	          	var availablesrcKey1 = [];
       	              $.each(data1, function(i, item) {
       	              	var inner_no = "" ;
       	              	if(item.inner_no){inner_no=item.inner_no+','}
       	              	availablesrcKey1[i]=item.courier_no.replace(/\ /g,"")+'('+inner_no+item.real_name.replace(/\ /g,"")+')';
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
                         			 $("#courierNo").val(data[0]);
                         	});	 
                          }); 	      
            
            
        	$('#exportData').click(function(){
        		var action= $("form:first").attr("action");
         	   $("form:first").attr("action","${ctx}/export/service").submit();
         	   $("form:first").attr("action",action);
         	  loaddata_end() ;
        	});
        });            
    </script>
</head>
<body>
<div class="search">


      <div class="soso">
        <div class="soso_left search_cont">

    <form:form id="trans" action="${ctx}/wages/rlist" method="post">
     <input type="hidden" name="serviceName" value="wagesRlist"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
	    <li><span>录入时间：</span>
			 	<input id="dateBegin" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'dateEnd\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="createTimeBegin" value="${params['createTimeBegin']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="dateEnd"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'dateBegin\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="createTimeEnd" value="${params['createTimeEnd']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 </li>
       <li>网点名称：<input type="text" value="${params['substationNo']}" name="substationNo" id="substationNo" style="width: 240px;"></input></li>
        <li>&#12288;快递员：<input type="text" value="${params['courierNo']}" name="courierNo" id="courierNo" style="width: 240px;"></input></li>  
        <li><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
        
        </ul>
    </form:form>
      </div>   <!-- search_cont end  -->
    <div class="clear"></div>
 </div>   <!-- soso end  -->    
 
  <div class="tableble_search ">
        <div class="operator">
	      <div class="search_new"><input class="sear_butt" type="submit" id="exportData" value="导出" /></div>
      </div>
  </div>    <!-- tableble_search end  -->
 
</div>  <!--search end  -->    
	

<div class="tbdata">
    <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th align="center">序号</th>
					<th align="center">账单时间</th>
					<th align="center">网点</th>
					<th align="center">快递员编号</th>
					<th align="center">快递员</th>
					 <c:forEach items="${nameList}" var="item0" varStatus="status0">     
                     <th align="center">${item0.cn_name}</th>
                     </c:forEach> 
                    <th align="center">编辑人</th>
					<th align="center">编辑时间</th>
				</tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td>${status.count}</td>
            <td>${item.cost_month}</td> 
            <td>${item.substation_name}</td>         
            <td>${item.inner_no}</td>     
            <td>${item.real_name}</td>   
          <c:forEach items="${nameList}" var="item1" varStatus="status1">  
              <td>${item[item1.name]}</td> 
          </c:forEach>   
             <td>${item.operator}</td>     
            <td>${item.create_time}</td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>
</body>

</html>