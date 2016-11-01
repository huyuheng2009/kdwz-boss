	<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/themes/default/new_layout/table_new.css"/>
<script  type="text/javascript" src="/scripts/city_hp.js"></script>
<style>
.tableble_table tr{
	cursor: pointer;
}
</style>
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:760,height:520,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        $(function(){
        	var api = frameElement.api, W = api.opener;
        	 api.lock();
        	 var style = W.getStyle() ;
     	    if(style==1){
     	    	$("table").addClass("ta_ta");
      		$("table th").addClass("num_all");
      		//$("table td").addClass("num_all");
      		
      		$(".tableble_search").addClass("soso");
     	    }
            trHover('t2');
            trClick('t2');
            
            $('tr').dblclick(function(){
            	var api = frameElement.api, W = api.opener;
                var arr = new Array();
            	$(this).find("td").each(function(i){
            		arr[i] = $(this).text() ;
            	});
            	
            	 var inid =  $('input[name=inid]').val();
            	if('S'==inid){
            		 W.document.getElementById('callPhone').value=arr[6] ;
            		 W.document.getElementById('sendPhone').value=arr[3] ;
            		 W.document.getElementById('sendName').value=arr[0] ;
            		 W.document.getElementById('sendAddr').value=arr[2] ; 
            		 var areas = spit1(arr[1]);
            		 var area = areas.split('-') ;
            		 if(area.length>2){
            			 W.InitOption('sprovince','scity','sarea',area[0],area[1],area[2]); 
            		 }else{
            			 W.InitOption('sprovince','scity','sarea','','','');  
            		 }
            		 var obj =  W.document.getElementById('courier') ;
            		 if(obj){
            			 obj.options.length = 1; 
                		 if(arr[4].length>1){
                			 var oOption = document.createElement("OPTION"); //创建一个OPTION节点 
     			            obj.options.add(oOption);    //将节点加入city选项中 
     			            oOption.innerText = arr[5];    //设置选择展示的信息 
     			            oOption.value = arr[4];         //设置选项的值 
     			          obj.options[1].selected = true;
                		 } 
            		 }
            		 
            		 W.document.getElementById('revPhone').focus();
            		// W.document.getElementById('stips').innerHTML = areas ;
            	}else{
            		 W.document.getElementById('revPhone').value=arr[3] ;
            		 W.document.getElementById('revName').value=arr[0] ;
            		 W.document.getElementById('revAddr').value=arr[2] ;
            		 var areas = spit1(arr[1]);
            		 var area = areas.split('-') ;
            		 if(area.length>2){
            			 W.InitOption('rprovince','rcity','rarea',area[0],area[1],area[2]); 
            		 }else{
            			 W.InitOption('rprovince','rcity','rarea','','','');  
            		 }
            		// W.document.getElementById('take_courier_no').focus();
            		 W.document.getElementById('revAddr').focus();
            		 //W.document.getElementById('rtips').innerHTML = areas ;
            	} 
   		        api.close();
         	});
            
            
            $('#ok').click(function(){
            	var api = frameElement.api, W = api.opener;
            	var mytr = $("tr.tar");
                if(mytr.length<1){
                	alert("请选择地址") ;
                	return ;
                }
                $("tr.tar").trigger('dblclick');
         	});
            
            $('#res').click(function(){
            	 $("#t2 tr").removeClass("tar");
         	});
            
            
            $('#return').click(function(){
            	api.close() ;
         	});
            
            
            
            
            
            
   }); 
        
        
  
    </script>
</head>
<body>
<div class="search">
     <input type="hidden" name="inid" value="${params['inid']}" />
        <ul> 
            <li>手机号码：<input type="text" value="${params['incomingPhone']}" name="incomingPhome"></input></li>
        </ul>
    <div class="clear"></div>
</div>

<div class="tbdata">
    <table width="99%" cellspacing="0" class="t2" id="t2">
        <thead>
        <tr>
            <th align="center">姓   名</th>
            <th align="center">所在地区</th>
            <th align="center">详细地址</th>
            <th align="center">电话</th>
            <th align="center" style="display: none;">快递员员编号</th>
            <th align="center">快递员员名称</th>
            <th align="center">来电电话</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.list}" var="item" varStatus="status">
            <tr class="${status.count % 2 == 0 ? 'a1' : ''}">
                <td>${item.name}</td>
                <td>${item.area_addr}</td>
                <td>${item.detail_addr}</td>
                <td>${item.phone}</td>
                <td  style="display: none;">${item.courier_no}</td>
                <td>${item.cname}</td>
                <td>${item.incoming_phone}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div id="page">
    <pagebar:pagebar total="${list.pages}"
                     current="${list.pageNum}" sum="${list.total}"/>
</div>

 <div class="operator" style="margin-left: 50px;">
	      <div class="search_new"><input class="sear_butt" type="submit" value="确定" id="ok"/> </div>
         <!--   <div class="search_new"><input class="sear_butt" type="submit" value="重选" id="res"/> </div> -->
	      <div class="search_new"><input class="sear_butt" type="submit" id="return" value="返回" /></div>
	   </div>
</body>

</html>