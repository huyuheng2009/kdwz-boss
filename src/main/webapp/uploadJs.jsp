<%@ page contentType="text/html;charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<link href="${ ctx}/scripts/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/scripts/pluploader/js/plupload.full.min.js"></script>
<script src="${ctx}/scripts/pluploader/js/i18n/zh_CN.js"></script>
<script type="text/javascript">
 	if(typeof console=="undefined"){
 		console={};
 		console.log=function(msg){
 			
 		};
 	}
	function createImgContainer(cId) {
		var html = '<div class="tupian">\
			<div class="close_btn"></div>\
			<div id="'+cId+'">\
			</div>\
			<div class="process">\
				<div class="process_inner" id="pi-'+cId+'" ></div>\
			</div>\
			<div class="notUpload">未上传</div>\
			</div>';
		return html;
	}
	function createErrorInfo(info){
		var html='<div class="picListError">'+info+'</div>';
		return html;
	}
	function Process(innerId) {
		this.innerId = innerId;
		this.inner = $(innerId);
	}
	Process.prototype.start = function() {
		var inner = this.inner;
		inner.css("width", "100%");
		function h() {
			var o = inner;
			var x = o.css("backgroundPositionX")
					|| o.css("background-position").split(" ")[0] || 0;
			x = parseFloat(x);
			x += 1;
			if (x > 30)
				x = 0;
			o.css("background-position", x + "px" + " 0");
		}
		if (this.interval) {
			clearInterval(this.interval);
		}
		this.interval = setInterval(h, 20);
	};
	Process.prototype.complete = function() {
		clearInterval(this.interval);
	};
	Process.prototype.setComplete = function(val) {
		this.inner.css("width", val);
	};
	Process.prototype.hide=function(){
		this.inner.parent().fadeOut(300);
	}
	$(function() {
		var uploader = new plupload.Uploader({
			runtimes : "flash,html5,silverlight,html4",
			browse_button : 'browse', // this can be an id of a DOM element or the DOM element itself
			url : '${ ctx}/servlet/upload;jsessionid=<%=session.getId()%>',
			filters : {
				mime_types : [ {
					title : "Image files",
					extensions : "jpg,png"
				} ],
				max_file_size:"2024kb"
			},
			// Flash settings
			flash_swf_url : '${ctx}/scripts/pluploader/js/Moxie.swf',

			// Silverlight settings
			silverlight_xap_url : '${ctx}/scripts/pluploader/js/Moxie.xap',
			prevent_duplicates: true
		});
		uploader.init();
		uploader.bind("Init", function() {
		});
		//添加图片事件
		uploader.bind("filesAdded", function(up, files) {
			$.each(files, function() {
				var img = new mOxie.Image();
				var html = createImgContainer(this.id);
				$("#picList .clear_fix").before(html);
				var file = this;
				img.onload = function() {
					this.embed(document.getElementById(file.id));

				}
				img.load(this.getSource());
			});
		});
		var processes={};
		uploader.bind("UploadFile",function(up,file){
			$("#pi-"+file.id).parent().fadeIn(300);
			var status=$("#"+file.id).nextAll(".notUpload");
			status.html("上传中");
			var p=new Process("#pi-"+file.id);
			processes[file.id]=p;
			p.start();
			$("#"+file.id).prev().fadeOut(300);
		});
		//上传进度事件
		uploader.bind("UploadProgress",function(up,file){
			console.log("UploadProgress",arguments);
			processes[file.id].setComplete(file.percent+"%");
			
		});
		//上传完成事件
		uploader.bind("FileUploaded",function(up,file,info){
			var status=$("#"+file.id).nextAll(".notUpload");
			status.html("已上传");
			status.css("backgroundColor","#ed9c28");
			processes[file.id].complete();
			processes[file.id].hide();
			console.log("FileUploaded",arguments);
			var data=info.response;
			data=eval("("+data+")");
			console.log("data",data.datas);
			var objId="attachment";
			if (data.error==1){
			       alert(data.datas);
			    }else if (data.error == 0 ){    
			      if (true){
				      var oldVal = $("#"+objId+"").val();
				      if (oldVal != null && oldVal != "" ){
				        oldVal = oldVal+",";
				      }
			      	$("#"+objId+"").val(oldVal+data.datas);
			      }else{
			      	$("#"+objId+"").val(data.datas);
			      }
			 }
		});
		//绑定删除图片按钮事件
		$(".picList").on("click",".close_btn",function(){
				var _this=$(this);
				var fileId=_this.next().attr("id");
				var tupian=_this.parent();
				tupian.fadeOut(400,function(){
					_this.parent().remove();
					uploader.removeFile(fileId);	
				});
		});
		//错误处理
		uploader.bind("Error",function(up,obj){
			var info="文件:&nbsp;&nbsp;"+obj.file.name+obj.message+"请重新选择！";
			var html=createErrorInfo(info);
			$(".startUpload").after(html);
			setTimeout(delayClearErrorInfo,5000);
		});
		$(".startUpload").on("click",function(){
				var allowStart=false;
				for(var i in uploader.files){
					if(uploader.files[i].status==plupload.QUEUED){
						allowStart=true;
						break;
					}
				}
				if(!allowStart){
					alert("没有要上传的文件");
				}else{
					uploader.start();	
				}
		});
		function delayClearErrorInfo(){
			var next=$(".startUpload").nextAll(".picListError");
			next.fadeOut(800);
			
		}
	});
</script>
<style>
.tupian {
	width: 120px;
	height: 80px;
	float: left;
	position: relative;
	padding: 6px;
	border: #d3d3d3 1px solid;
	margin-right: 12px;
	margin-bottom: 12px;
}
.tupian img{
	width: 120px;
	height: 80px;
}
.notUpload{
	position: absolute;
	text-align: center;
	background-color: #000;
	opacity:0.4;
	filter:alpha(opacity=40);
	color:#fff;
	height: 15px;
	bottom: 0px;
	left: 0px;
	height: 20px;
	width: 100%;
	line-height: 20px;
}
.process {
	display:none;
	position: absolute;
	left: 0px;
	bottom: -10px;
	background: url(${ctx}/themes/default/images/process_bg.png) repeat-x;
	width: 0;
	height: 10px;
	width: 132px;
}

.process_inner {
	height: 10px;
	background: url(${ctx}/themes/default/images/process.jpg) repeat-x;
	width: 100%;
}

.clear_fix {
	clear: both;
}

.close_btn {
	background: url(${ctx}/themes/default/images/image_upload.png) no-repeat;
	background-position: -100px -182px;
	position: absolute;
	width: 14px;
	height: 14px;
	overflow: hidden;
	right: -6px;
	top: -6px;
	cursor: pointer;
}

.startUpload {
	border: 1px solid red;
	display: inline;
	color: #fff;
	background-color: #428bca;
	border-color: #357ebd;
	padding: 6px 10px 6px 8px;
	cursor: pointer;
	-webkit-user-select: none;
	user-select: none;
	border-radius: 4px;
	margin-left: 12px;
}

.startUpload:hover {
	border-color: #3276b1;
	background-color: #3276b1;
}

.startUpload .flag {
	font-weight: bold;
	display: inline;
	font-size: 16px;
}

.close_btn:HOVER {
	background-position: -100px -200px;
}
.picListError{
	margin-top: 12px;
	background-color: #d2322d;
	padding: 6px 10px 6px 8px;
	color:#fff;
	width: 280px;
}
</style>
