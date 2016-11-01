<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<style>
			#uploadXLS {display: none;z-index: 100;background: white;border-bottom: 1px solid #DCDCDC;}
			#uploadXLS input:first-child {display: none;}
			#uploadXLS .a {height: 2em;line-height: 2em;text-indent: 20px;}
			#uploadXLS .a span.a1{font-weight: bolder;}
			#uploadXLS .p{height: 20px;width: calc(100% - 40px);margin: 10px auto;
						  box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -webkit-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -ms-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -o-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);
						  -moz-box-shadow: inset 0 0 10px -3px rgba(0,0,0,0.5);}
			#uploadXLS .p2 {height: 100%;width: 0;background: #E25A48;}
		</style>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/ImportExcel1.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery.engine.aster/js/jquery.validationEngine.js"></script>
<script>
var $jqq = jQuery.noConflict(true);
</script> 
<script type="text/javascript" src="/scripts/jquery.autocomplete.js"></script>

<script type="text/javascript">
	//获取时间段，单位、天
	//月结用户月报
	function getToday(date, less) {
		var yesterday_milliseconds = date.getTime() - 1000 * 60 * 60 * 24* less;
		var yesterday = new Date();
		yesterday.setTime(yesterday_milliseconds);

		var strYear = yesterday.getFullYear();
		var strDay = yesterday.getDate();
		var strMonth = yesterday.getMonth() + 1;
		if (strMonth < 10) {
			strMonth = "0" + strMonth;
		}
		datastr = strYear + "-" + strMonth + "-" + strDay;
		return datastr;
	}
	  $(function () {
	       $('#sixMonth').click(function(){
	    	   var now= new Date();
	    		var begin = getToday(now, 180);
				var end = getToday(now, 0);
	    	   $('#dateBegin').val(begin);
	    	   $('#dateEnd').val(end);	   
	     	});     
	      });
	$(function() {
		$('#month').click(function() {
			var now = new Date();
			var begin = getToday(now, 30);
			var end = getToday(now, 0);
			$('#dateBegin').val(begin);
			$('#dateEnd').val(end);
		});
	});
	$(function() {
		$('#threeMonth').click(function() {
			var now = new Date();
			var begin = getToday(now, 90);
			var end = getToday(now, 0);
			$('#dateBegin').val(begin);
			$('#dateEnd').val(end);
		});
	});

	$(function(){
		  $('#import').click(function(){		
			  location.href = "<%=request.getContextPath()%>/template/sendBatch.xls";		  		  
		  });	  
	  });
	
	$(function() {
 	jQuery("#myform").validationEngine();
	 var u = new ImportExcel1($("#uploadXLS"), [".xls"], {params: {}});
		$("#importExcel").click(function(evt) {
		
			if (!u.lock) {			
				u.$file.click();
			}	
			evt.preventDefault();
			
		}); 

	});

	$(function() {
		trHover('t2');
		$('#reload_btn').click(function() {
			location.reload();
		});
	});


$(function(){
	$('#deleteAll').click(function(){
		 event.returnValue = confirm("删除是不可恢复的，你确认要删除吗？");
		 if(event.returnValue){
		 $.ajax({
             type: "GET",
             url: "/sendBatch/deleteAll",
             data: { },
             dataType: "json",
             success: function(data){
            		location.reload(); 
               }
         });	
		 }else{
        	 return;
         }	
	});	
});

$(function(){
	$('#submitAll').click(function(){	
		$('form1').attr("action", "/sendBatch/addOrderMuch").submit();
	});
});
</script>
</head>
<body>
	<div class="search">
		<div class="tableble_search">

			<!-- search_cont e
			nd  -->
			<div class="clear"></div>
		</div>
		<!-- tableble_search end  -->
		<div class="tableble_search">
			<div class="operator">
		
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="导入" id="importExcel" />
				</div>		
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="清空" id="deleteAll" />
				</div>		
				<form action="/sendBatch/addOrderMuch" id="form1">
				<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="提交" id="submitAll" />
				</div>		
				</form>	
					<div class="search_new">
					<input class="button input_text  big gray_flat" type="submit"
						value="模版" id="import" />
				</div>			
			</div>		
				<strong style=" line-height:50px; font-weight:normal;color: red; font-size:16px">&nbsp;&nbsp;&nbsp;&nbsp;${msg}</strong>
		</div>
		<div id="uploadXLS">
						<input type="file" />
						<div class="a">
							上传　<span class="a1">标准表格.xls</span>　<span class="a2"></span>
						</div>
						<div class="p"><div class="p2"></div></div>
					</div>
		<!-- tableble_search end  -->
	</div>


	<div class="tbdata">

		<table width="100%" cellspacing="0" class="t2" id="t2">
			<thead>
				<tr>
					<th width="10" align="center">序号</th>
					<th width="30" align="center">运单编号</th>
					<th width="50" align="center">签收时间</th>
					<th width="20" align="center">签收人</th>
					<th width="40" align="center">派件员内部编号</th>
					<th width="30" align="center">派件网点</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr class="${status.count % 2 == 0 ? 'a1' : ''}">
						<td align="center">${status.count}</td>
						<td>${item.orderNo}</td>
						<td>${item.sendTime}</td>
						<td>${item.sendName}</td>
						<td>${item.sendCourierNo}</td>
						<td>${item.sendSubNo}</td><!-- 签约快递员 编号-->					
					</tr>
				</c:forEach>	
			</tbody>
		</table>
	</div>
	<div id="page">
		<pagebar:pagebar total="${list.pages}"
			current="${list.pageNum}" sum="${list.total}" />
	</div>
</body>

</html>