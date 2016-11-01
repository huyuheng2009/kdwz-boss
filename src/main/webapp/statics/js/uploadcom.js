function swfupload(id,inputid,title,file_limit,file_types,file_size,moduleid,file_category,yesdo){ 
		url = Web.path+'/backend/upload?file_limit='+file_limit+'&file_types='+file_types+'&file_size='+file_size+'&moduleid='+moduleid+'&file_category='+file_category;
		/*art.dialog.open(url, {
		id: id,
		title: title,
		lock: 'true',
		window: 'top',
		width: 600,
		height: 455,
		ok: function(){
			var iframeWin = this.iframe.contentWindow;
    		var topWin = art.dialog.top;
    		yesdo.call(this,iframeWin, topWin,id,inputid); 
			},
		cancel: true
		});*/
                 $.dialog({
                    width: '600px',
                    height: '450px',
                    max: false,
                    min: false,
                    drag: true,
                    resize: true,
                    title: title,
                    content: 'url:'+url,
                    lock: true,
                    ok:function(){
                        yesdo.call(this,this.content, '',id,inputid); 
                    },
                    cancel:true
                });
}

function clean_cover(inputid){
	$('#'+inputid+'_pic').attr('src',Web.path+'/statics/images/admin_upload_thumb.png');
	var aid = $('#'+inputid).val();

	$('#'+inputid).val('');
	$('#'+inputid+'_aid_box').html('');
}


function yesdo(iframeWin, topWin,id,inputid){
	 
	var num = iframeWin.$('#myuploadform > div').length;
	if(num){
		var aids = iframeWin.$('#myuploadform #ids').attr("value");
		var status = iframeWin.$('#myuploadform #status').attr("value");
		var filedata = iframeWin.$('#myuploadform #filedata').attr("value");
		var namedata = iframeWin.$('#myuploadform #namedata').attr("value");
	
		if(filedata){
			$('#'+inputid+'_pic').attr('src',filedata);
			$('#'+inputid).val(filedata);
			if(status==0) $('#'+inputid+'_aid_box').html('<input type="hidden" name="'+inputid+'_id" class="up_mark_class" value="'+aids+'" />');
		}
	}
}

function up_image(iframeWin, topWin,id,inputid){ 
	var num = iframeWin.$('#myuploadform > div').length;
	if(num){
		var aids = iframeWin.$('#myuploadform #aids').attr("value");
		var status = iframeWin.$('#myuploadform #status').attr("value");
		var filedata = iframeWin.$('#myuploadform #filedata').attr("value");
		var namedata = iframeWin.$('#myuploadform #namedata').attr("value");

		if(filedata){
			$('#'+inputid+'_pic').attr('src',filedata);
			$('#'+inputid).val(filedata);
			if(status==0) $('#'+inputid+'_aid_box').html('<input type="hidden"  name="aid[]" value="'+aids+'"  />');				
		}
	}
}

function up_images(iframeWin, topWin,id,inputid){ 
	var data = '';
	var aidinput='';
	var num = iframeWin.$('#myuploadform > div').length;
	if(num){
		iframeWin.$('#myuploadform  div ').each(function(){
				var status =  $(this).find('#status').val();
				var aid = $(this).find('#ids').val();
				var src = $(this).find('#filedata').val();
				var name = $(this).find('#namedata').val();
				if(status==0) aidinput = '<input type="hidden" name="'+inputid+'_ids" value="'+aid+'"/>';
				data += '<div id="uplist_'+aid+'">'+aidinput+'<input type="text" size="50" class="input-text" name="'+inputid+'" value="'+src+'"  />  <input type="text" class="input-text" name="'+inputid+'_name" value="'+name+'" size="30" /> &nbsp;<a href="javascript:remove_this(\'uplist_'+aid+'\');">移除</a> </div>';
		});			
		$('#'+inputid+'_images').append(data);
	}
}

function remove_this(obj){
	$('#'+obj).remove();
}

function selectall(name){
	
	$('input[name=\''+name+'\']').each(function(){
		$(this).attr('checked',!$(this).attr('checked'));
	});
}

function sync_server(item,is_add){
	$.ajax({
		   type: "POST",
		   url: config.contextPath+"/user/"+(is_add?'add_item':'modify_item'),
		   data: item,
		   dataType: "json",
		   success: function(reply){
			   if(reply.code==0){
                    //  先不写
                }
		   }
		});
}

function tocart(){
	location.href=config.contextPath+'/cart';
}

function add_cooike(item,flag){
	var data = getCookie('prod_data');
	var total = 0;
	if(data == null){
		data = [];
		data.push(item);
		total = item.quantity;
		sync_server(item,true);
	}else{
		data = $.parseJSON(data);
		
		var exist = false;
		for(var i=0;i<data.length;i++){
			if(data[i].id == item.id && data[i].type==item.type){
				if(flag){
					data[i].quantity = item.quantity;
				}else{
					data[i].quantity += item.quantity;
					exist = true;
				}
				sync_server(data[i],false);
			}
			total += data[i].quantity;
		}
		if(!exist){
			data.push(item);
			sync_server(item,true);
		}
	}
	setCookie('prod_data',$.toJSONString(data));
	refresh_cart();
}

function getStat(){
	var d = {'total_items':0,'total_prices':0}
	var data = getCookie('prod_data');
	if(data != null){
		data = $.parseJSON(data);
		for(var i=0;i<data.length;i++){
			d.total_items += data[i].quantity;
			d.total_prices += data[i].quantity*data[i].price;
		}
	}
	return d;
}

/**
 * ajax  分页页码
 * @param page
 * @param count
 * @param eachPageNum
 * @param click
 * @return
 */
function Paginated(page,count,eachPageNum,click){
	
	   this.page = page;

	   this.count = count;

	   this.eachPageNum = eachPageNum;
	   
	   this._num = 8;

	   this.maxNum = -1;

	   this.url = "";

	   this.geUrl = function(page){
			return this.url+(this.url.indexOf("?")>0?"&":"?")+"page="+page;
		};
	   
	   this.html = function(){
		   var _s = [];
		   _s.push("<div class=\"pagination\">");
			 if(this.getTotalPage()>1){
				 if(this.page==1){
					 _s.push("<span class=\"disabled prev_page\">&laquo; 上一页</span>");
					 _s.push(this.pagebar());
					 _s.push("<a class=\"next_page\" href='javascript:;'  "+click(this.getNextPage())+">&raquo; 下一页</a>&nbsp;");
				 }else if(this.page>1 && this.page<this.getTotalPage()){
					 _s.push("<a class=\"prev_page\" href='javascript:;' "+click(this.getPrePage())+">&laquo; 上一页</a>");
					 _s.push(this.pagebar());
					 _s.push("<a class=\"next_page\" href='javascript:;' "+click(this.getNextPage())+">&raquo; 下一页</a>&nbsp;");
				 }else{
					 _s.push("<a class=\"prev_page\" href='javascript:;' "+click(this.getPrePage())+">&laquo; 上一页</a>&nbsp;");
					 _s.push(this.pagebar());
					 _s.push("<span class=\"disabled next_page\">&raquo; 下一页</span>");
				 }
			 }
			 _s.push("</div>");
			 return _s.join('');
	   };
	   
	   this.getTotalPage = function() {
			return Math.floor((this.count+this.eachPageNum-1)/this.eachPageNum);
		};
		
		this.pagebar = function(){
			var _bar = [];
			var _startPos = this.page-(this._num/2)>0?this.page-(this._num/2):1;
			if(_startPos>1){
				_bar.push("<a href='javascript:;' "+click(1)+">"+1+" ..."+"</a>");
			}
			var _endPos = (_startPos+this._num)>this.getTotalPage()?this.getTotalPage():(_startPos+this._num);
			if(_endPos-this._num>0)  _startPos = _endPos-this._num;
			for(;_startPos<=_endPos;_startPos++){
				if(_startPos == this.page){
					_bar.push("<span class=\"current\">"+_startPos+"</span>");
				}else{
					_bar.push("<a href='javascript:;' "+click(_startPos)+">"+_startPos+"</a>");
				}
			}
	        if(_endPos<this.getTotalPage()){
	        	_bar.push("<a href='javascript:;' "+click(this.getTotalPage())+">"+" ..."+this.getTotalPage()+"</a>");
			}
			return _bar.join('');
		};
		
		this.getNextPage = function(){
			return this.page+1;
		};
		
		this.getPrePage = function(){
			return this.page-1;
		};
	}


jQuery.extend({ 
	   /** * @see 将javascript数据类型转换为json字符串 * @param 待转换对象,支持object,array,string,function,number,boolean,regexp * @return 返回json字符串 */ 
	   toJSONString: function(object) { 
	     var type = typeof object; 
	     if ('object' == type) { 
	       if (Array == object.constructor) type = 'array'; 
	       else if (RegExp == object.constructor) type = 'regexp'; 
	       else type = 'object'; 
	     } 
	     switch (type) { 
	     case 'undefined': 
	     case 'unknown': 
	       return; 
	       break; 
	     case 'function': 
	     case 'boolean': 
	     case 'regexp': 
	       return object.toString(); 
	       break; 
	     case 'number': 
	       return isFinite(object) ? object.toString() : 'null'; 
	       break; 
	     case 'string': 
	       return '"' + object.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function() { 
	         var a = arguments[0]; 
	         return (a == '\n') ? '\\n': (a == '\r') ? '\\r': (a == '\t') ? '\\t': "" 
	       }) + '"'; 
	       break; 
	     case 'object': 
	       if (object === null) return 'null'; 
	       var results = []; 
	       for (var property in object) { 
	         var value = jQuery.toJSONString(object[property]); 
	         if (value !== undefined) results.push(jQuery.toJSONString(property) + ':' + value); 
	       } 
	       return '{' + results.join(',') + '}'; 
	       break; 
	     case 'array': 
	       var results = []; 
	       for (var i = 0; i < object.length; i++) { 
	         var value = jQuery.toJSONString(object[i]); 
	         if (value !== undefined) results.push(value); 
	       } 
	       return '[' + results.join(',') + ']'; 
	       break; 
	     } 
	   } 
	});


/** ********** Date 日期扩展 *************** */
/**
 * 是否在该日期前
 * @param {} date  
 * @return {Boolean}
 */
Date.prototype.before = function(date){
	return this.getTime()<date.getTime();
}

/**
 * 是否在该日期后
 * @param {} date
 * @return {}
 */
Date.prototype.after = function(date){
	return this.getTime()>date.getTime();
}

Date.prototype.between = function(startDate,endDate){
	var t = this.getTime();
    return startDate.getTime() <= t && t <= endDate.getTime();
}

Date.prototype.toStr = function() {
	return this.getFullYear() + "-" + ((this.getMonth() + 1)<10?("0"+(this.getMonth() + 1)):(this.getMonth() + 1))
	+ "-" + (this.getDate()<10?("0"+this.getDate()):this.getDate());
}

Date.prototype.format = function(format){
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}

function setCookie(name,value,time){
    var exp  = new Date();
    exp.setTime(exp.getTime() + (typeof(time)=='undefined'?(360*24*60*60*1000):time));
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}

function getCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
     if(arr != null) {
    	 var tmp = unescape(arr[2]);
    	 if(tmp.indexOf('"') == 0){
    		 tmp = tmp.substr(1,tmp.length-2);
    	 }
    	 return tmp;
      }
     return null;
}

function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";path=/;expires="+exp.toGMTString();
}

String.prototype.toDate = function() {
	var _tmp = this.split("-");
	return new Date(parseInt(_tmp[0], 10), parseInt(_tmp[1], 10) - 1, parseInt(
			_tmp[2], 10))
}

String.prototype.replaceAll = function(s1, s2) {
	var r, re;
	eval("re = /" + s1 + "/g;")
	r = this.replace(re, s2);
	return (r);
}

String.prototype.replaceAll2Excep = function(s1, s2) {      
    var temp = this;      
    while (temp.indexOf(s1) != -1) {      
        temp = temp.replace(s1, s2);      
    }      
    return temp;      
}  

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
}
String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
}

function format_number(pnumber,decimals){
    if (isNaN(pnumber)) { return 0};
    if (pnumber=='') { return 0};
     
    var snum = new String(pnumber);
    var sec = snum.split('.');
    var whole = parseFloat(sec[0]);
    var result = '';
     
    if(sec.length > 1){
        var dec = new String(sec[1]);
        dec = String(parseFloat(sec[1])/Math.pow(10,(dec.length - decimals)));
        dec = String(whole + Math.round(parseFloat(dec))/Math.pow(10,decimals));
        var dot = dec.indexOf('.');
        if(dot == -1){
            dec += '.';
            dot = dec.indexOf('.');
        }
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec;
    } else{
        var dot;
        var dec = new String(whole);
        dec += '.';
        dot = dec.indexOf('.');    
        while(dec.length <= dot + decimals) { dec += '0'; }
        result = dec;
    }  
    return result;
}
 
/**
*   Usage:  format_number(12345.678, 2);
*   result: 12345.68
**/