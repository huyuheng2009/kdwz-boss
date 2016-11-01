<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true" %>
<%@include file="/tag.jsp" %>
<%@ include file="/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script  type="text/javascript" src="/scripts/city_hp.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
     	   InitOption('province','city','area','广东','深圳市');
     	   
     		 $('#submit').click(function(){
     	   		 var api = frameElement.api, W = api.opener;

     	   	 var province = 	$('select[name=province]').val() ;
   		     var city = 	$('select[name=city]').val() ;
   		     var area = 	$('select[name=area]').val() ;
   		     if(province.length<2||city.length<2||area.length<2){
   		    	 alert("请选择区域");
   		    	 return false ;
   		     }
   		     
   		     sarea = province+city+area ;
   		     var inid =  $('input[name=inid]').val();
   		        W.document.getElementById(inid).value=sarea ;
   		        api.close();
     	   	 });
     		 
     		 
        });

    </script>
</head>
<body>
<div style="padding-top: 30px;padding-left: 15px;">
 <input type="hidden" name="inid" value="${params['inid']}" />
<select name="province" style="width: 24%;"  id="province" onchange="javascript:ChangeProvince('city',this.options[this.selectedIndex].value,this.options[this.selectedIndex].value)"></select>
         <select name="city" style="width:24%;margin-left: 2%;" id="city" onchange="javascript:ChangeCity('area',this.options[this.selectedIndex].value,this.options[this.selectedIndex].value)"></select>
          <select name="area"  style="width:44%;margin-left: 2%;" id="area" ></select>
      
 </div> 
 <div style="padding-top: 10px;padding-left: 55px;">        
      <input class="button input_text  medium blue_flat" type="button" id="submit" value="保存"/> 
   </div>
</body>

</html>