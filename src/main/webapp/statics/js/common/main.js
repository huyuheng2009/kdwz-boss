/*!
 * 主页js
 */

/*jQuery(function() {		
 jQuery(".menu_nav_one li a").click(function() {			
 jQuery("#location").html("当前位置："+jQuery(this).parent().parent().parent().find("p").html()+"&nbsp;>&nbsp;&nbsp;"+jQuery(this).find("span").html());        
 });
 });*/

/**
 * 键盘事件处理
 */
/*function keyDown(e){
 var currKey=0,e=e||event;
 currKey=e.keyCode||e.which||e.charCode;
 if (currKey == 116) {
 e.keyCode = 0;
 e.returnValue = false;
 }
 //	if (e.ctrlKey || currKey == 82 || currKey == 27){
 //		e.returnValue = false;
 //	}
 }*/
/**
 * 清空所有查询条件
 * @return
 */
function clearValue() {
	$("input[type=text],select,input[type=hidden]").each(function() {		
		$(this).val('');
	});
}
var utils = {
	dialog: function(param) {
		var _param = {
			width: '840px',
			height: '600px',
			max: false,
			min: false,
			drag: false,
			resize: false,
			title: '窗口',
			content: '没有内容',
			lock: true
		};
		for (var k in param) {
			_param[k] = param[k];
		}
		return $.dialog(_param);
	},
	closeCDialog: function() {
		var api = frameElement.api, W = api.opener;
		api.close();
	}
};

function selectDialog(title, url, width, height) {
	$.dialog({
		width: width,
		height: height,
		max: false,
		min: false,
		drag: false,
		resize: false,
		title: title,
		content: 'url:' + url,
		lock: true
	});
}

function del(url) {
	$.dialog.confirm('您真的确定要删除吗？', function() {
		location.href = url;
	});
}








