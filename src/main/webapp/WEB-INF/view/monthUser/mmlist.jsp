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
<style>
/*********地址展开样式**********/
.out_address{ position:absolute; width:200px; padding:6px; background:#fff; border:1px solid #cdcdcd; margin:-28px 0 0 -4px;display: none;}
</style>
   <script type="text/javascript">
        $(function () {
            trHover('t2');
            $('#reload_btn').click(function(){
       		 location.reload() ;
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

    <form:form id="trans" action="${ctx}/monthCount/mmlist" method="get" onsubmit="loaddata()">
     <input type="hidden" name="serviceName" value="monthMmlist"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
	 <li><span>上月开始日期：</span>
			<input id="date1" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'date2\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="date1" value="${params['date1']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="date2"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'date1\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="date2" value="${params['date2']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
		</li>	
       <li>网点名称：<input type="text" value="${params['substationNo']}" name="substationNo" id="substationNo" style="width: 240px;"></input></li>
        <li  style="margin-right: 300px;"><input class="button input_text  medium blue_flat sear_butt" 
                       type="submit" value="查询"/>
            </li>
        
         <li><span>本月开始日期：</span>
			<input id="date3" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'date4\')||\'%y-%M-%d\'}'})"   type="text"  style="width:120px" name="date3" value="${params['date3']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			 	~
			 	<input id="date4"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'date3\')}',maxDate:'%y-%M-%d'})"  type="text" style="width:120px" name="date4" value="${params['date4']}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
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
	

<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		 <table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th align="center">序号</th>
					<th align="center">网点编号</th>
					<th align="center">网点名称</th>
					<th align="center">上月收件</th>
					<th align="center">本月收件</th>
					<th align="center">收件环比</th>
					<th align="center">上月派件</th>
					<th align="center">本月派件</th>
					<th align="center">派件环比</th>
				</tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td>${status.count}</td>
            <td>${item.substation_no}</td>   
            <td>${item.substation_name}</td>   
            <td>${item.s1}</td>   
            <td>${item.s2}</td>   
             <td>
             <c:if test="${item.s1==0}">100</c:if>
             <c:if test="${item.s1>0}"><fmt:formatNumber type="number" value="${(item.s2-item.s1)/(item.s1)*100}" maxFractionDigits="2"/> </c:if>%</td>   
            <td>${item.r1}</td>   
            <td>${item.r2}</td> 
             <td>
             <c:if test="${item.r1==0}">100</c:if>
             <c:if test="${item.r1>0}"><fmt:formatNumber type="number" value="${(item.r2-item.r1)/(item.r1)*100}" maxFractionDigits="2"/></c:if>%</td>   
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