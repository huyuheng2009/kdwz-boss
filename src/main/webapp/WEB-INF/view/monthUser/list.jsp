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
       		 location.reload()
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
               
               
              
            	var mlist =tmjs.mlist; 	 
           	var mres = [];
           	$.each(mlist, function(i, item) {
           		var name = item.month_sname ;
           		if(name.length<1){name = item.month_name ;}
                	mres[i]= item.month_settle_no.replace(/\ /g,"")+'('+name.replace(/\ /g,"")+')';
                }); 
           	  $jqq("#monthSettleNo").autocomplete(mres, {
             		minChars: 0,
             		max: 12,
             		autoFill: true,
             		mustMatch: false,
             		matchContains: true,
             		scrollHeight: 220,
             		formatItem: function(data, i, total) {
             			return data[0].substring(0,data[0].indexOf(')')+1);
             		}
             	}).result(function(event, data, formatted) {
             		if(data[0].indexOf(')')>-1){
             			 $("#monthSettleNo").val(data[0]) ;
       			  } 
             		//_submit() ;
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

    <form:form id="trans" action="${ctx}/monthCount/list" method="get" onsubmit="loaddata()">
      <input type="hidden" name="serviceName" value="monthClist"/>
     <input type="hidden" name="ff" value="${params['ff']}"/>
        <ul>
	 <li><span>统计年份：</span>
		<input id="cyear" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy',readOnly:true,maxDate:'#F{\'%y\'}'})"   type="text"  style="width:120px" name="cyear" value="${params['cyear']}" onClick="WdatePicker({dateFmt:'yyyy'})"/>
     </li> 
       <li class="search_cont_input">网点名称：<input type="text" value="${params['substationNo']}" name="substationNo" id="substationNo"></input></li>
       <li class="search_cont_input">月结客户：<input type="text" value="${params['monthNo']}" name="monthNo" id="monthSettleNo"></input></li>
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
	

<div class="tbdata" style="position: relative;" >
<div class="loaddata" style="top:120px; left:45%; z-index:20;position:absolute;display: none;"><img src="${ctx }/themes/default/images/loading.gif" alt="" /></div>
		<table width="100%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
					<th align="center">序号</th>
					<th align="center">网点名称</th>
					<th align="center">快递员姓名</th>
					<th align="center">月结客户账号</th>
					<th align="center">月结客户</th>
					<th align="center">合计</th>
					<th align="center">一月</th>
					<th align="center">二月</th>
					<th align="center">三月</th>
					<th align="center">四月</th>
					<th align="center">五月</th>
					<th align="center">六月</th>
					<th align="center">七月</th>
					<th align="center">八月</th>
					<th align="center">九月</th>
					<th align="center">十月</th>
					<th align="center">十一月</th>
					<th align="center">十二月</th>
				</tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr>
            <td>${status.count}</td>
            <td><u:dict name="S" key="${item.substation_no}" /></td>   
            <td><u:dict name="C" key="${item.courier_no}" /></td>   
            <td>${item.month_settle_no}</td>   
            <td>${item.month_name}</td>   
            <td>${item.m}</td>   
            <td>${item.m1}</td>   
            <td>${item.m2}</td>   
            <td>${item.m3}</td>   
            <td>${item.m4}</td>   
            <td>${item.m5}</td>   
            <td>${item.m6}</td>   
            <td>${item.m7}</td> 
            <td>${item.m8}</td>   
            <td>${item.m9}</td>     
            <td>${item.m10}</td> 
            <td>${item.m11}</td>   
            <td>${item.m12}</td>  
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