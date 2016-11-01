$(document).ready(function(){
			

	
	var nav_ul = $(".menu_nav_two");
	var nav_li = nav_ul.find("li");
	var nav_lis = nav_ul.find("a");
	var nav_span = nav_ul.find("span");
	var nav_img = nav_ul.find("img");
	var nav_con = $(".moving");
	var ph_tab_ul = $(".ph_tab");
	var ph_tab_lis = ph_tab_ul.find("li");
	var ph_con = $(".share_table");
	var notice_ul = $(".notice_lies");
	var notice_lies = notice_ul.find("li");
	//
	var all_height =$(window).height();
	var top_height =$("#head_top").height();
	var footer_height =$("#footer").height();
	var main_height = all_height - top_height - footer_height;	
	$("#main_con").attr("style","min-height:"+main_height+"px;height:auto;_height:"+main_height+"px;");
	
	//---------------------------------------------------------------------
	$(window).resize(function() {
		
		var all_height =$(window).height();
		var top_height =$("#head_top").height();
		var footer_height =$("#footer").height();
		var main_height = all_height - top_height - footer_height;
		$("a").focus(function(){this.blur()});
		
		$("#main_con").attr("style","min-height:"+main_height+"px;height:auto;_height:"+main_height+"px;");
		
		
		var all_width =$(window).width();
		
		if(all_width < 1281){

			//alert(main_height)
			$(".main_right").css({width:'1078px'});
			$(".right_con").css({width:'1038px'});
			$(".wid_1280").css({width:'1280px'});
			
			$("#main_con").css({width:'1280px'});
			
    	}else{			
    		//$("#main_con").attr("style","min-height:600px";"height:auto";"_height:600px;");
			$(".main_right").css({width:'auto'});
    		$(".right_con").css({width:"auto"});
			$(".wid_1280").css({width:'auto'});
			$("#main_con").css({width:'auto'});
			
		}
		
		if(all_height < 800){

			//alert(main_height)
			$("#main_con").attr("style","min-height:553px;height:auto;_height:553px;");
			
    	}else{			
    		//$("#main_con").attr("style","min-height:600px";"height:auto";"_height:600px;");			
    		$("#main_con").attr("style","min-height:"+main_height+"px;height:auto;_height:"+main_height+"px;");
		}
		
		
	});

	//nav action!
	
	nav_lis.each(function(n){
	 
	         nav_lis.eq(n).hover(function(e){
					//$(this).addClass("no-bg");
			   		nav_con.eq(n).animate({width:"202px"},300);
					nav_span.eq(n).animate({padding:"0 0 0 10px"},100);
					nav_img.eq(n).animate({padding:"14px 0px 0 40px"},600);
			 },
			 		function(e){
						//$(this).removeClass("no-bg");
				 		nav_con.eq(n).stop(true,false).animate({width:"0px"},300);
						nav_span.eq(n).stop(true,false).animate({padding:"0"},600);
						nav_img.eq(n).stop(true,false).animate({padding:"14px 10px 0 20px"},100);
					}
			 );
			 
			 nav_lis.eq(n).click(function(e){
										  
			   		nav_li.eq(n).addClass("vot").siblings().removeClass("vot").parent().parent().siblings().find("li").removeClass("vot");
			 });
	  });
	
	//
	
	$(".cotr .close").click(function(){
									 $(this).hide().next(".open").show().parent().parent().parent().next(".main_con").stop(true,false).slideToggle(300);
									 });
	$(".cotr .open").click(function(){
									 $(this).hide().prev(".close").show().parent().parent().parent().next(".main_con").stop(true,false).slideToggle(300);
									 });
	
	//-----------------
	$(".notice_lies li:odd").addClass("tr_bck"); 
	$(".share_table .tr_line:odd").addClass("grd"); 
	$(".share_table").find("tr:last-child .border").css("border-bottom","0");  //
	
	//-----------------------------------
	
	ph_tab_lis.each(function(n){
	
		ph_tab_lis.eq(n).click(function(e){// click 
								   ph_con.hide();
								   ph_con.eq(n).fadeIn("fast");
								   ph_tab_lis.eq(n).addClass("vot").siblings().removeClass("vot");
		});
						   
	});
	//--------------------------------
	
	notice_lies.each(function(n){
	
		notice_lies.eq(n).hover(function(e){// click 
								   notice_lies.eq(n).addClass("site").siblings().removeClass("site");
		});
						   
	});
	//-----------------------------------
	
})
