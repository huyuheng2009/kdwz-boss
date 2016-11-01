<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
<script>
            function importSubstation() {
                var action = $("form:first").attr("action");
                $("form:first input[name=serviceName]").val('daySubstationExport');
                $("form:first").attr("action", "${ctx}/substatic/export").submit();
                $("form:first").attr("action", action);
            }
            
            $(function(){
            	$("#kai").click(function(){
          				if(confirm("你确定要给所有未开账的加盟网点开账么")){
          					$.ajax({
          						type:"post",
          						dataType:"text",
          						url:"/join/open",
          						data:{},
          						success:function(data){         		       							
          							alert(data, '', function() {
          								location.reload();   								
          							})        		 	            	
          						} 					
          					});       					
          				}                      		          
            	});     	
            });
            
          $(function(){
        	  $('#chongzhi').click(function(){
        	
        		  var nowDate = $.trim($('input[name=nowDate]').val());
        		  var substationNo = $.trim($('select[name=substationNo]').val());
        		  var type = $.trim($('select[name=type]').val());
        		  var pay = $.trim($('select[name=pay]').val());
        		  var money = $.trim($('input[name=money]').val());
        		  var lgcOrderNo = $.trim($('input[name=lgcOrderNo]').val());
        		  var note = $.trim($('textarea[name=note]').val());
        		  var reg = /^[0-9]+(.[0-9]{1})?$/;
        		  if(substationNo.length<1 || substationNo==''){
        			  alert("请选择加盟分站！");
        			  return;
        		  }
        		  if(money.length<1 || money==''){
        			  alert("请输入金额");
        			  return;
        		  }
        		  if(!reg.test(money)){
        			  alert("请输入正确的金额格式，纯数字可保留一位小数！");
        			  return;
        		  }     		  
   	  
   
        			 $.ajax({
           				 type: "post",//使用get方法访问后台
           		            dataType: "json",//返回文本格式
           		            url: "<%=request.getContextPath()%>/join/upMoney",//要访问的后台地址
           				data : {
           				'nowDate':nowDate,
           				'substationNo':substationNo,
           				'type':type,
           				'pay':pay,
           				'money':money,
           				'lgcOrderNo':lgcOrderNo,
           				'note':note        			
           				},//要发送的数据   			
           				success : function(msg) {   
           					if(msg.code =='1'){       						
           						alert(msg.message,'',function(){    					
           						});   		
           					}
           					if(msg.code =='0'){       						
           						alert(msg.message,'',function(){
           							location.reload();
           						});   		
           					}	
           										
           					
           				}
           			});       		
        		  
        		  
        	  });    	  
          });  
       
            	
            	
            	
            	
            	
       			 	   		
        	
      	
    
      $(function(){
    	  $('#reset').click(function(){
    		  location.reload();		  
    	  });   	  
      });
       
            
            
            
        </script>
<style type="text/css">
.soso_input {
	margin: 3px 0;
}
</style>


</head>
<body>
	<div class=" soso">
		<!--按钮上下结构-->
		<!--按钮左右结构-->
		<div class="soso_left">
			<form id="myform" action="/join/openList" method="post">
				<div style="width: 100%;">
					<div class="soso_input">
						<div class="soso_b" style="margin-left: 10px;">交款时间　</div>
						<div class="soso_input">
							<input id="dateBegin" type="text"
								style="    width: 190px; margin: 0, 0, 0, 20px;" name="nowDate"
								value="${nowDate}"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								style="display:inline" />
						</div>
						<div class="soso_b" style="margin-left: 30px;">网点　　</div>
						<div class="soso_input">
							<select id="substationNo" name="substationNo" class="time_bg"
								style="width: 196px;">
								<option value="">--全部--</option>
								<c:forEach items="${substationList}" var="item"
									varStatus="status">
									<option value="${item.substation_no}"
										${substationNo eq item.substation_no?'selected':''}>${item.substation_name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div style="width: 100%;">
					<div class="soso_input">
						<div class="soso_b" style="margin-left: 10px;">预付款类型</div>
						<div class="soso_input">
							<select name="type" id="type" style="width: 196px;">
								<option value="S" ${type eq 'S'?'selected':''}>充值</option>
								<option value="Z" ${type eq 'Z'?'selected':''}>冲账</option>

							</select>
						</div>
						<div class="soso_b" style="margin-left: 30px;">付款方式</div>
						<div class="soso_input">
							<select id="pay" name="pay" class="time_bg" style="width: 196px;">
								<option value="CASH" ${pay eq 'CASH'?'selected':''}>现金</option>
								<option value="POS" ${pay eq 'POS'?'selected':''}>转账</option>
							</select>
						</div>
					</div>
				</div>
				<div style="width: 100%;">
					<div class="soso_input">
						<div class="soso_b" style="margin-left: 10px; color: red;">交款金额　</div>
						<div class="soso_input">
							<input type="text" name="money" value="${money}" style="width: 188px;"/>
						</div>
						<div class="soso_b" style="margin-left: 30px;">运单号　</div>
						<div class="soso_input">
							<input type="text" name="lgcOrderNo" value="${lgcOrderNo} " style="width: 188px;"/>
						</div>
					</div>
				</div>
				<div style="width: 100%;">
					<div class="soso_input">
						<div class="soso_b" style="margin-left: 10px;">备　注　　</div>
						<textarea rows="1o" cols="20" name="note"
							style="height: 120px; width: 300px;">${note}</textarea>


					</div>
				</div>
				<div class="soso_search_b" style="width: 90%; margin: 20px;">
					<!--                                    <div class="soso_button"><button>7天</button></div>
                                                                            <div class="soso_button"><button>8天</button></div>-->
					<div class="soso_button" style="margin-left: 180px;">
						<input type="button" id="chongzhi" value="充值" style="background: rgb(28,122,216);color: #fff;"/>
					</div>
					<div class="soso_button">
						<input type="button" id="reset" value="重置" />
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
