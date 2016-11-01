/*!
 * 公共js
 */

$(function() {
	
	showControl(top.userRight);		
	// 文本输入框获得焦点时将所有文字选中
	$("input[class=inputText],textarea[class=inputTextArea]").each(function(){
		$(this).bind("focus",function(){
			$(this).select();
		});
	});
	
	// 表单提交时去除文本框内容的前后空格
	$("form").submit(function(){
		$("form :text").each(function(){
			$(this).val($.trim($(this).val()));
		});
	});
	
	if($('.share_table')){
		$('.share_table tr').click(function (){
			if(!$(this).data('class')){
				$('.share_table tr').each(function (){
					$(this).data('class',$(this).attr('class'));
				});
			}
			$('.share_table tr').each(function (){
				$(this).attr('class',$(this).data('class'));
			});
			$(this).attr('class','tr_selected');
		})
	}
	
//	try{
//		top.unblockUI();		
//	}catch(e){
//	}
	/**
	 * 权限功能显示 用于显示需要权限的页面元素
	 * @param userRightStr 用户权限字符串
	 * @return
	 */
	function showControl(userRightStr){		
		if(!userRightStr){	
			return;
		}	
		var rightIds=userRightStr.split(",");	
		
		jQuery("* :hidden[name]").each(function(){
			
			var name=jQuery(this).attr("name");		
			if(name){
				for(var i=rightIds.length-1;i>=0;i--){
					if(rightIds[i]!="" && name.indexOf(rightIds[i])!=-1){
						jQuery(this).show();
					}
				}
			}
		});		
	}
	
	/**
	 * 键盘事件处理
	 */ 
	function keyDown(e){
		var currKey=0,e=e||event;
	    currKey=e.keyCode||e.which||e.charCode;
	//	var k = event.keyCode;
		if (currKey == 116) {
			e.keyCode = 0;
			e.returnValue = false;
		}
		if (e.ctrlKey || currKey == 82 || currKey == 27){
			e.returnValue = false;
		}
	}
	
});


/**
 * 清空所有查询条件
 * @return
 */
function clearValue(){
	$("input[type=text],select").each(function(){
		$(this).val('');
	});	
}





