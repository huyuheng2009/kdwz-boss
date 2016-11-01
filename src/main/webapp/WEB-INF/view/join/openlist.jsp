<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@include file="/tag.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="/scripts/jquery-auto.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/statics/js/dialog/lhgdialog.min.js"></script>
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
            
            
            function stopOrStart(id,status){  		
       			 $.ajax({
       				 type: "post",//使用get方法访问后台
       		            dataType: "test",//返回文本格式
       		            url: "<%=request.getContextPath()%>/join/startOrStop",//要访问的后台地址
       				data : {
       					'id' : id,
       					'status':status
       				},//要发送的数据   			
       				success : function(msg) {      										
       						alert(msg,'',function(){
       							location.reload();
       						});   							
       					
       				}
       			});       			 	   		
       	}    	
      	
    
            
            function check(e,warning,shut,note){
            	if(note.length<1){
            		note="请备注";
            	}
            	$('#edit'+e).hide();
             	$('.ss'+e).hide();
            	$('#save'+e).show();
            	$('#not'+e).show();
            	var waring1 = '#warning'+e;
            	var shut1 = '#shut'+e;
            	var note1 = '#note'+e;
            	
            	var waringinput = $( '<input type="text" value='+warning+' name=warning'+e+' id='+ waring1+' />'); 
            	var shutinput = $( '<input type="text"     value='+shut+' name=shut'+e+' id='+shut1 +'/>');  
            	var notinput = $( '<input type="text"     value='+note+ ' name=note'+e+' id='+note1 +'/>');  
	           	$(waring1).html(waringinput);
	           	$(shut1).html(shutinput);      	   
	           	$(note1).html(notinput);      	   
            }          
            function not(e,warning,shut,note){
            	$('#edit'+e).show();
             	$('.ss'+e).show();
            	$('#save'+e).hide();
            	$('#not'+e).hide();
            	var waring1 = '#warning'+e;
            	var shut1 = '#shut'+e;
             	var note1 = '#note'+e;
            	var waringinput = $( '<td width="120px; " style="border-style:none">'+warning+'</td>'); 
            	var shutinput = $( '<td width="120px;" style="border-style:none">'+shut+'</td>');  
            	var noteinput = $( '<td width="120px;" style="border-style:none">'+note+'</td>');  
	           	$(waring1).html(waringinput);
	           	$(shut1).html(shutinput);      	   
	           	$(note1).html(noteinput);      	   
            }   
            function save(e,id,warning1,shut1,note1){
            	var warningName = 'warning'+e;
            	var shutName= 'shut'+e;
            	var noteName= 'note'+e;
            	var	reg = /^\d+(.[0-9]{1,2})?$/i;//两位小数          	
            	var warning = $('input[name='+warningName+']').val();
            	var shut = $('input[name='+shutName+']').val();
            	var note = $('input[name='+noteName+']').val();
            	
            	 if(warning.length>1|| warning!=""){	
        			 if(!reg.test(warning)){
            			 alert("请输入正常的警戒金额,纯数字可保留两位小数");  		
            			return false;
            		 }			
        		}else{
        			 alert("请输入警戒金额");  		
         			return false;
        		}		
            	 if(shut.length>1|| shut!=""){	
        			 if(!reg.test(warning)){
            			 alert("请输入正常的停止金额,纯数字可保留两位小数");  		
            			return false;
            		 }			
        		}else{
        			alert("请输入关闭金额");
        			return;
        		}
            	if(note =="请备注"){
            		note ="";
            	}
            	 $.ajax({
       				 type: "post",//使用get方法访问后台
       		            dataType: "json",//返回文本格式
       		            url: "<%=request.getContextPath()%>/join/saveEdit",//要访问的后台地址
       				data : {
       					'id' : id,
       					'warning':warning,
       					'shut':shut,
       					'note':note
       				},//要发送的数据   			
       				success : function(msg) {      
       					if(msg.code=="1"){
       						alert(msg.message,'',function(){
       							location.reload();
       						});       						
       					}
       					if(msg.code=="0"){
       						alert(msg.message,'',function(){    						
       							$('#edit'+e).show();
       			             	$('.ss'+e).show();
       			            	$('#save'+e).hide();
       			            	$('#not'+e).hide();
       			            	var waring1 = '#warning'+e;
       			            	var shut1 = '#shut'+e;
       			         		var note1 = '#note'+e;
       			            	var waringinput = $( '<td width="120px; " style="border-style:none">'+warning+'</td>'); 
       			            	var shutinput = $( '<td width="120px;" style="border-style:none">'+shut+'</td>');  
       			            	var noteinput = $( '<td width="120px;" style="border-style:none">'+note+'</td>');  
       				           	$(waring1).html(waringinput);
       				           	$(shut1).html(shutinput);      	   								    							
       				           	$(note1).html(noteinput);      	   								    							
       						});       						
       					}    					   				  					
       				}
       			});       		     	
            }
        function upTime(id){
        	$.dialog({
				width: '480px',
				height: '200px',
				max: false,
				min: false,
				drag: false,
				resize: false,
				title: '时间更改',
				content: "url:<%=request.getContextPath()%>/join/up?layout=no&id="+id,
				lock: true
			}); 
        }    
            
            
        </script>
        
        
        
</head>
<body>
	<div class=" soso">
		<!--按钮上下结构-->
		<!--按钮左右结构-->
		<div class="soso_left">
			<form id="myform" action="/join/openList" method="post">
				<div class="soso_li">

					<div class="soso_input">
						<input type="hidden" name="serviceName" value="" />

						<div class="soso_b" style="margin-left: 10px;">网点</div>
						<div class="soso_input">
							<select id="substationNo" name="substationNo" class="time_bg"	style="width: 180px">
								<option value="">--全部--</option>
								<c:forEach items="${substationList}" var="item"
									varStatus="status">
									<option value="${item.substation_no}" ${params.substationNo eq item.substation_no?'selected':''}>${item.substation_name }</option>
								</c:forEach>
							</select>
						</div>

						<div class="soso_b" style="margin-left: 10px;">账户状态</div>
						<div class="soso_input">
							<select id="status" name="status" class="time_bg"
								style="width: 180px">
								<option value=""  ${params.status eq ''?'selected':''}>--全部--</option>							
								<option value="1" ${params.status eq '1'?'selected':''}>启用</option>
								<option value="0" ${params.status eq '0'?'selected':''}>停用</option>
							</select>
						</div>

						<div class="soso_b" style="margin-left: 10px;">
							<input type="checkbox" name="H" value="S"
								style="width: 18px; height: 15px; vertical-align: middle;" ${params.H eq 'S'?'checked':''}  /><span 
								style="font-size: 13px;">余额小于警戒金额 </span>
						</div>
						<div class="soso_b" style="margin-left: 10px;">
							<input type="checkbox" name="H2" value="S"
								style="width: 18px; height: 15px; vertical-align: middle;" ${params.H2 eq 'S'?'checked':''}/><span
								style="font-size: 13px;">余额小于关闭金额 </span>
						</div>
					</div>
					<div class="soso_search_b">
						<!--                                    <div class="soso_button"><button>7天</button></div>
                                                                            <div class="soso_button"><button>8天</button></div>-->
						<div class="soso_button">
							<input type="submit" value="查询"/>
						</div>
					</div>
						<div class="soso_search_b" style="width: 100%">
						<!--                                    <div class="soso_button"><button>7天</button></div>
                                                                            <div class="soso_button"><button>8天</button></div>-->
                                                                            
						<div class="soso_button" >
							<input type="button" value="开账" id ="kai" style="display:block;background: rgb(28,122,216); border: 1px solid #1c7ad8;
    color: #fff;" />
						</div>
					</div>
			</form>
		</div>
	</div>
	<div class="table">
		<!--                                         <div class="ta_header"> -->
		<!--                                             <div class="ta_butt">                                                                                     -->
		<!--                                                 <a href="javascript:void(0);" id="importSubstation" class="button_2" onclick="importSubstation();">导出</a>	 -->
		<!--                                             </div> -->
		<!--                                         </div> -->
		<div class="ta_table">
			<table class="ta_ta" width="100%" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<th width="120px;">账户状态</th>
					<th width="120px;">启用时间</th>
					<th width="120px;">网点编号</th>
					<th width="120px;">网点名称</th>
					<th width="120px;">当前余额</th>
					<th width="120px;">警戒金额</th>
					<th width="120px;">关闭系统金额</th>
					<th width="140px;" >备注</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${list.list}" var="item" varStatus="status">
					<tr>
					<td>
					<c:if test="${item.status eq '1' }">启用</c:if>
					<c:if test="${item.status eq '0' }">停用</c:if>				</td>	
					<td><a href="#" onclick="upTime('${item.id}');" }><fmt:formatDate value="${item.start_time}" type="both" /> </a>     </td>
					<td>${item.inner_no}</td>
					<td>${item.substation_name}</td>
					<td>${item.cur_balance}</td>
					<td id="warning${status.count}">${item.warning_balance}</td>
					<td id="shut${status.count}">${item.shut_balance}</td>			
					<td  id="note${status.count}">${item.note}</td>
					<td><a href="#"  id="edit${status.count}" onclick="check('${status.count}','${item.warning_balance}','${item.shut_balance}','${item.note}');">编辑 </a>
					<a href="#" style="display:none;"  id="save${status.count}" onclick="save('${status.count}','${item.id}','${item.warning_balance}','${item.shut_balance}','${item.note}')">保存</a>
					<a href="#" style="display:none;"  id="not${status.count}" onclick="not('${status.count}','${item.warning_balance}','${item.shut_balance}','${item.note}')">取消</a>
					&nbsp;&nbsp;
						<c:if test="${item.status eq '1' }"><a href="#" onclick="stopOrStart('${item.id}','0');" class="ss${status.count}" }>停用</a></c:if>
						<c:if test="${item.status eq '0' }"><a href="#" onclick="stopOrStart('${item.id}','1');" class="ss${status.count}" >启用</a></c:if>				
					</td>
					</tr>
				</c:forEach>
			
			</table>
		</div>
	</div>
	<!--                                         <div class="height02"></div> -->
	<div id="page">
		<pagebar:pagebar total="${list.pages}" current="${list.pageNum}"
			sum="${list.total}" />
	</div>
</body>
</html>
