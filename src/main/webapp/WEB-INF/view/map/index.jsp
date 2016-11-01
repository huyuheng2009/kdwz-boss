<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${ctx}/themes/default/map.css"/>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=50b92cafd039403ab7e117e9153dc66d"></script> 
    <script type="text/javascript">
        function show(id,orderNo){
        	$.dialog({lock: true,title:'订单详情',drag: true,width:900,height:470,resize: false,max: false,content: 'url:${ctx}/order/detail?id='+id+'&orderNo='+orderNo+'&layout=no',close: function(){
        	 }});
        }
        
        
        function lists(e){
        	if($(e).parent(".people_one").siblings('.people_two').css("display")=='none'){
        		$(e).parent(".people_one").siblings('.people_two').css("display","block") ;
        		$(e).find('.bt').removeClass("people_one_off") ;
        		$(e).find('.bt').addClass("people_one_on") ;
        	}else{
        		$(e).parent(".people_one").siblings('.people_two').css("display","none") ;
        		$(e).find('.bt').removeClass("people_one_on") ;
        		$(e).find('.bt').addClass("people_one_off") ;
        	}
        }  

        $(function () {
        	var mapObj = new AMap.Map('map');
        	mapObj.setZoom(10);
            //map.setCenter([116.39,39.9]);
            
            var slist = ${slist} ;
            var smarkers = [] ;
            for(var c in slist){
            
             var local = slist[c].location ;
             var l = local.split(',') ;
             if(l.length==2){
            	 var slngX =l[0], slatY =l[1];   
               	 var marker = new AMap.Marker({
                        icon : '/themes/default/images/sub_icon.png',//24px*24px
                        position: [slngX, slatY],
                        title: slist[c].substation_name,
                        map : mapObj
                      });
               	smarkers.push(marker); 
             }
           }
            
            var onlineList = ${onlineList} ;
            var markers = [] ;
            for(var c in onlineList){
            	 var lngX = onlineList[c].track_longitude, latY = onlineList[c].track_latitude;   
            	 var marker = new AMap.Marker({
                     icon : '/themes/default/images/per_icon.png',//24px*24px
                     position: [lngX, latY],
                     title: onlineList[c].real_name,
                     map : mapObj
                   });
            	 markers.push(marker);
            }
            
        	setInterval(function () {
				$.post("/map/onlineList", function (res) {
					mapObj.remove(markers);   //清除点
					markers = []  ;
					 for(var c in res){
		            	 var lngX = res[c].track_longitude, latY = res[c].track_latitude;   
		            	 var marker = new AMap.Marker({
		                     icon : '/themes/default/images/per_icon.png',//24px*24px
		                     position: [lngX, latY],
		                     title: res[c].real_name,
		                     map : mapObj
		                   });
		            	 markers.push(marker);
		            }
				}, "json");
			}, 10000);
            
        	
    		Date.prototype.Format = function (fmt)
			{ //author: meizz   
				var o = {
					"M+": this.getMonth() + 1, //月份   
					"d+": this.getDate(), //日   
					"h+": this.getHours(), //小时   
					"m+": this.getMinutes(), //分   
					"s+": this.getSeconds(), //秒   
					"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
					"S": this.getMilliseconds()             //毫秒   
				};
				if (/(y+)/.test(fmt))
					fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
				for (var k in o)
					if (new RegExp("(" + k + ")").test(fmt))
						fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				return fmt;
			};
			function UpdateDate() {
				var date = new Date();
				var _date = date.Format("yyyy-MM-dd hh:mm:ss");
				$(".udate").html(_date);
			}
			setInterval(function () {
				UpdateDate();
			}, 1000);
            
            // mapObj.remove(markers);
            //marker.setMap(mapObj);
           
            
            
           /*  var _onClick = function(e){
                if(e.target.subMarkers){
                    mapObj.add(e.target.subMarkers);
                    mapObj.setFitView(e.target.subMarkers);
                    mapObj.remove(markers)
                }
            }
            AMap.event.addListener(marker, 'click', _onClick); */
            
            
            var newCenter = mapObj.setFitView();     
        });
   
        
        

    </script>
</head>
<body>
<div  class="map" style="margin: 20px 0 0 0;">
<div id="map" style="width: 100%;height: 620px;padding: 20px 0 0 0;overflow: hidden;"></div>
  <div class="map_list" >
    <p class="udate"></p>
     <form:form  action="${ctx}/map/index" method="post">
         <div class="map_search"><input type="text" name="realName" value="${params.realName}" placeholder="快递员姓名">
         <input class="button" type="submit" value=""/>
         </div>
     </form:form>
    <div class="plist" style="height:480px; overflow-y: scroll;">
     <div class="map_people">
    	<div class="people_one"><button class="bt people_one_off"></button><a href="/map/index">返回首页</a></div>
    </div>
 <c:forEach items="${list}" var="item" varStatus="status">
    <div class="map_people">
    	<div class="people_one"><a onclick="lists(this)"><button class="bt people_one_on"></button>${item.substation_name}</a></div>
     
        <div class="people_two">
        	<ul>
        	<c:forEach items="${item.clist}" var="item1" varStatus="status1">
            <li class="<c:out value="${item1.online ne 'Y'?'people_no':'' }"/>"><a href="/map/polyline?cno=${item1.courier_no}">${item1.real_name}</a></li>
             </c:forEach> 
            </ul>
        </div>
         
     </div>
   </c:forEach>   
  </div>  <!--  plist end    --> 
    
  </div>
</div>  
</body>

</html>