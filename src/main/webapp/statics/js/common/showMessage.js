
/**
 * 成功消息提示框
 * @param content
 */
function showSuccessMessage(content){
	$.dialog({ 
		title:'提示',
		width:200,
		height:100,
		drag: false, 
	    resize: false,
	    icon: 'success.gif', 
	    lock:true,
	    content: content
	});
}

function showSuccessCallback(content,obj){
	$.dialog({ 
		title:'提示',
		width:200,
		height:100,
		drag: false, 
	    resize: false,
	    icon: 'success.gif', 
	    lock:true,
	    content: content,
	    close:obj
	});
}

function showAddSuccess(content,objok,obj){
	$.dialog({ 
		title:'提示',
		width:200,
		height:100,
		drag: false, 
	    resize: false,
	    icon: 'success.gif', 
	    lock:true,
	    content: content,
	    ok:objok,
	    cancel:obj
	});
}

/**
 * 失败消息提示框
 * @param content
 */
function showErrorMessage(content){
	$.dialog({ 
		title:'提示',
		width:200,
		height:100,
		drag: false, 
	    resize: false,		
	    icon: 'error.gif', 
	    lock:true,
	    content: content 
	});
}

/**
 * 失败消息提示框
 * @param content
 */
function showTipMessage(content){
	$.dialog({ 
		title:'提示',
		width:200,
		height:100,
	    icon: 'alert.gif',
	    drag: false, 
	    resize: false,	  
	    lock:true,
	    content: content
	    
	});
}
