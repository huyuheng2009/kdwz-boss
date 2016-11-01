function ImportExcel($e, accepts) {
	this.$e = $e;
	this.$file = $e.find("input[type='file']");
	this.accepts = accepts;
	this.lock = false;
	if (accepts.length > 0) {
		this.$file.attr("accept", accepts.join(","));
	}
	if (arguments.length > 2) {
		this.opts = arguments[2];
	} else {
		this.opts = {};
	}
	//
	var _this = this;
	this.$file.change(function(evt) {
		_this.do(evt);
		evt.preventDefault();
		var e = this;
		setTimeout(function() {
			$(e).val("");
		}, 200);
	});
}
ImportExcel.prototype.process = function(process) {
	if (process >= 0) {
		this.$e.find(".a2").text("已完成　" + (process < 10 ? '0' : '') + process + "%");
		this.$e.find(".p2").css("width", process + "%");
	} else {
		switch (process) {
			case -1:
				this.$e.find(".a2").text("请求中止");
				break;
			case -2:
				this.$e.find(".a2").text("请求发生错误");
				break;
			case -6:
				this.$e.find(".a2").text("上传超时");
				break;
			case -7:
				this.$e.find(".a2").text("上传错误  code:" + arguments[1]);
				break;
			case -100:
				var ret = arguments[1];
				if (ret.errorCode == 0) {
					this.$e.find(".a2").text("上传成功");
					location.reload();
				} else {
					this.$e.find(".a2").text("上传失败  code:" + ret.errorCode + "  msg:" + ret.message);
				}
				break;
			case -101:
				var ret = arguments[1];
				if (ret.errorCode == 0) {
					this.$e.find(".a2").text("上传成功");
				} else {
					this.$e.find(".a2").text("上传失败  code:" + ret.errorCode + "  msg:" + ret.message);
				}
				break;
		}
	}
};
ImportExcel.prototype.do = function(evt) {
	if (this.lock) {
		return;
	}
	var f = evt.target.files[0];
	/*
	 if (f.size > 10 * 1024 * 1024) {
	 alert("上传的文件超过10M");
	 return;
	 }*/
	var validType = this.accepts.length;
	for (var i = 0; i < this.accepts.length; i++) {
		var k = f.name.lastIndexOf(this.accepts[i]);
		if (k < 0 || k + this.accepts[i].length !== f.name.length) {
			validType--;
		}
	}
	if (validType === 0) {
		alert("上传的文件格式不正确");
		return;
	}
	//
	var _this = this;
	this.lock = true;
	var status = 0;
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/orderBatch/importOrderExcel", true);
	xhr.responseType = "text";//IE不支持 JSON 类型
	var formData = new FormData();
	formData.append('file', f);
	if (this.opts.params !== undefined) {
		for (var name in this.opts.params) {
			formData.append(name, this.opts.params[name]);
		}
	}
	//
	xhr.upload.addEventListener("abort", function(evt) {
		_this.process(-1);
	}, false);
	xhr.upload.addEventListener("error", function(evt) {
		_this.process(-2);
	}, false);
	xhr.upload.addEventListener("load", function(evt) {
		status++;
	}, false);
	xhr.upload.addEventListener("loadstart", function(evt) {
		_this.process(0);
	}, false);
	xhr.upload.addEventListener("progress", function(evt) {
		if (evt.lengthComputable) {
			var p = Math.floor(evt.loaded * 100 / evt.total);
			_this.process(p);
		}
	}, false);
	xhr.upload.addEventListener("timeout", function(evt) {
		_this.process(-6);
	}, false);
	xhr.upload.addEventListener("loadend", function(evt) {
		if (status === 0) {
			_this.lock = false;
		}
	}, false);
	xhr.addEventListener("readystatechange", function(evt) {
		if (this.readyState === this.DONE) {
			if (this.status !== 200) {
				_this.process(-7, this.status);
			} else {
				var res = null;
				if (this.response !== null)
					res = $.parseJSON(this.response);
				if (res === null || res.errorCode != 0) {
					_this.process(-101, res);
					_this.lock = false;
				} else {
					_this.process(-100, res);
					setTimeout(function() {
						_this.$e.slideUp("slow", function() {
							_this.lock = false;
						});
					}, 3000);
					window.frames[0].location.reload();
				}
			}
		}
	});
	//
	this.$e.find(".a1").text(f.name);
	this.$e.slideDown("fast", function() {
		xhr.send(formData);
	});
};