
$(function() {
	
	$(".li_list_li a").each(function(){
	    $(this).attr("href",$(this).attr("href")+"?"+new Date().toUTCString()) ;
	});
	
	
	var h  = false ;
	var h1 = false ;
	$('.xitong_li_a').hover(function() {
		    $('.li_list').css("display","none");
            $(this).siblings('.li_list').css("display","block");
		}, function() {
			if(!h){
			 $(this).siblings('.li_list').css("display","none");
			}

		});

$('.xitong_cont_left ul').hover(function() {
		   h = true ;
		}, function() {
			h = false ;
			
		});

$('.li_list').hover(function() {
	        h1 = true ;
		    $(this).css("display","block");
		}, function() {
			h1 = false ;
			 $('.li_list').css("display","none");
		});



});
