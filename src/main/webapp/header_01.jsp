 <%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/reset.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/main.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/color.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/header.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/left_list.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/layout_css/little.css"/>
<link rel="stylesheet" href="${ctx}/themes/default/jquery.autocomplete.css"/>


<link rel="stylesheet" href="${ctx}/themes/default/new_layout/main_new.css"/>
 <link rel="stylesheet" href="${ctx}/themes/default/new_layout/default_new.css"/> 
<%-- <link rel="stylesheet" type="text/css" href="${ ctx}/themes/default/global.css" />
<link rel="stylesheet" type="text/css" href="${ ctx}/themes/button.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ ctx}/themes/default/validationEngine.jquery.css" />
<link rel="stylesheet" href="${ctx}/themes/default/jalert.css"/>

 <link rel="stylesheet" href="${ctx}/themes/default/common.css"/> 
<link rel="stylesheet" href="${ctx}/themes/default/addbetter.css"/>

<script type="text/javascript" src="${ ctx}/scripts/jquery-1.9.1.min.js"></script> 
<script type="text/javascript" src="${ ctx}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/index201012.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/lhgdialog/lhgcore.lhgdialog.min.js"></script>
<script language="javascript" type="text/javascript" src="${ ctx}/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery-ui.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/common.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery.sweetTitles.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery.validationEngine.js" charset="utf-8"></script>
<script type="text/javascript" src="${ ctx}/scripts/languages/jquery.validationEngine-zh_CN.js" charset="utf-8"></script>

<script type="text/javascript" src="${ ctx}/scripts/tableSorted.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/jquery.alerts.js"></script>

<script type="text/javascript" src="${ ctx}/scripts/city_json.js"></script>
<script type="text/javascript" src="${ ctx}/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/qrcode.min.js"></script>

 <script type="text/javascript" >
 
 function showAddr(e){
	  
	   if("block"==$(e).siblings(".out_address").css("display")){
		   $(".out_address").css("display","none"); 
	   }else{
		   $(".out_address").css("display","none");
		   	$(e).siblings(".out_address").css("display","block");
	   }
 	
 }
 
 
 function checkPhone(phone,isCall){
	 var p = /^(17[0-9]|13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/ ;     
	 var c = /^(\d{3,4}-)?\d{7,12}(-\d{3,4})?$/;
     var r = true ;
	 if(!p.test(phone))
	 {
		if('Y'==isCall){
			 if(!c.test(phone)){
				 r = false ;
			 } 
		 }else{
			 r = false  ;
		 }
	 }
	 return r ;
 };
 
 
 function editCell(event){
		var currentCell;
		if(event == null){
			currentCell = window.event.srcElement;
		}else{
			currentCell = event.target;	
		}
		
		if(currentCell.tagName.toLowerCase() == "td"){
			var input = document.createElement("input");
	        input.type = 'text';
	        input.setAttribute('class', 'editable');
	        input.value = currentCell.innerHTML;
	        input.width = currentCell.style.width;
	        
	        input.onblur = function(){
	            currentCell.innerHTML = input.value;
	            //currentCell.removeChild(input);
	        };
	        input.onkeydown = function(event){
	            if(event.keyCode == 13){
	                input.blur();
	            }
	        };

	        currentCell.innerHTML = '';
	        currentCell.appendChild(input);
	        input.focus();
		}	
	};



 
 
 
 
 $(document).ready(function(){ 
	     //sdocument.getElementById("t2").ondblclick=editCell;
	 
		$("table").addClass("tableble_table");
		$("table th").addClass("tableble_tit center");
		$("table").addClass("ta_ta");
		$("table th").addClass("num_all");
		//$("table td").addClass("num_all");
		
		$(".tableble_search").addClass("soso");
		
		$(".button").removeClass("gray_flat");
		$(".button").removeClass("blue_flat");
		$(".button").addClass("an_a an_aon sear_butt ");
		$("input[type='reset']").removeClass("an_a  an_aon sear_butt ");
		$("input[type='reset']").addClass("sear_butt1 ");
		$("input[type='reset']").click(function(){
			 var n=window.location.href.indexOf("?") ;
			 var href=window.location.href ;
			 if('N'!=$(this).attr("roload")){
				 if(n>0){
					  href= href.substr(0,n); 
					  location.href = href;
					}else{
						location.href = href;
					}
			 }else{
					$("input[type='text']").val("");
			 }
				
      	});
		
		
		
   
		
		$("input[class*='noltd']").keyup(function(){     
 	        var tmptxt=$(this).val();     
 	        $(this).val(tmptxt.replace(/\D|^0/g,''));     
 	    }).bind("paste",function(){     
 	        var tmptxt=$(this).val();     
 	        $(this).val(tmptxt.replace(/\D|^0/g,''));     
 	    }).css("ime-mode", "disabled");   
 		
 		$("input[class*='ltdlen_']").keyup(function(){  
 			var array = $(this).attr("class").split(" ");
 			var len = array[array.length-1].substr(7,array[array.length-1].length) ;
 	        var tmptxt=$(this).val();     
 	        $(this).val(tmptxt.substr(0,len));     
 	    }).bind("paste",function(){     
 	    	var array = $(this).attr("class").split(" ");
 			var len = array[array.length-1].substr(7,array[array.length-1].length) ;
 	        var tmptxt=$(this).val();     
 	        $(this).val(tmptxt.substr(0,len));   
 	    }).css("ime-mode", "disabled");  
		
	
 	//page
 		$("#pageGo").click(function(){
 			var p = $(".pageText").val() ;
 			var pno = /^[0-9]{1,8}$/ ; 
 			if(!pno.test(p)){
 				alert("页码不正确");
 				return false;
 			}
 			var input = $("<input type='hidden' name='p' value='"+p+"' />");
 			var form = $("form:first");
 			form.append(input);
 			form.submit();
 			
 			/* var p_url = "?p="+p ;
 			var url = $(this).attr("url") ;
 			console.log(url) ;
 			url = url.replace("?p=0",p_url);
 			console.log(url) ;
 			location.href = url ; */
     	});	
 	
 		$(".linkpage").click(function(){
 			var p = $(this).attr("pno") ;
 			var pno = /^[0-9]{1,8}$/ ; 
 			if(!pno.test(p)){
 				alert("页码不正确");
 				return false;
 			}
 			var input = $("<input type='hidden' name='p' value='"+p+"' />");
 			var form = $("form:first");
 			form.append(input);
 			form.submit();
 			
     	});	
 	
 		
 		$(".pageText").bind('keydown', function (e) {
 			  if(e.which == 13) {
 				 $("#pageGo").trigger('click');
 			  }
 		});	
 		
 		
 		
 		 var d = document.getElementsByTagName('form')[0];
		 if(d){
			 $(d).addClass("validate_form") ;
		 }
		
		$(".validate_form").validationEngine("attach",{
 			ajaxFormValidation:false,
 			onBeforeAjaxFormValidation:function (){
 				alert("before ajax validate");
 			},
 			onAjaxFormComplete:
 	        function (){
 				
 			}
 			
 		});  
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
	  });

</script> 
 
 