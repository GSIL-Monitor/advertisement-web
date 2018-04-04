function getMessage(url) {
	$.ajax({
		url : url,
		data : new FormData($('#uploadForm')[0]),
		dataType : 'json',
		cache : false,
		type : "POST",
		processData : false,
		contentType : false
	}).then(function(data) {
		if (data.retcode == '200' || data.retCode == '200') {
			showOverlay();
			adjust();
			var text = data.path;
			$("#needCopyUrl").attr("value", text);
		} else {
			var content = data.error_msg;
			if (typeof (content) == "undefined" || content.length == 0) {
				content = '操作失败';
			}
			showTipDialogAndSetContent(data.error_msg, true, true);
		}
		console.log(data);
	}, function() {
		showTipDialogAndSetContent('网络错误，请重试', true, true);
	});
}

function uploadShortUrl(url) {
	var encodeUrl = encodeURIComponent($('#transformUrl').val());
	var needTransformUrl = "needTransformUrl=" + encodeUrl;
	sendSingleDataAjax(url, needTransformUrl);
}

function getInviteFile(url) {
	var inviteNumber = "number=" + $('#inviteNumber').val();
	sendSingleDataAjax(url, inviteNumber);
}

function sendSingleDataAjax(url, param) {
	$.ajax({
		url : url,
		data : param,
		dataType : 'json',
		type : "POST",
	}).then(function(data) {
		if (data.retcode == '200' || data.retCode == '200') {
			showOverlay();
			adjust();
			var text = data.path;
			$("#needCopyUrl").attr("value", text);
		} else {
			var content = data.error_msg;
			if (typeof (content) == "undefined" || content.length == 0) {
				content = '操作失败';
			}
			showTipDialogAndSetContent(data.error_msg, true, true);
		}
		console.log(data);
	}, function() {
		showTipDialogAndSetContent('网络错误，请重试', true, true);
	});
}

$(function() {
	$('#copyUrl').click(function() {
		var e = document.getElementById("needCopyUrl");
		e.select(); // 选择对象
		document.execCommand("Copy"); // 执行浏览器复制命令
	});
});

$(function() {
	$('#close-mem').click(function() {
		$('#overlay').fadeOut(200);
		$('#pop-tip').css({
			display : 'none'
		});
	});
});

function showOverlay() {
	$('#overlay').height(pageHeight());
	$('#overlay').width(pageWidth());
	$('#overlay').fadeTo(500, 0.5);
}

function hideOverlay() {
	$('#overlay').fadeOut(200);
	window.location.reload();
}

function pageHeight() {
	return document.body.scrollHeight;
}

function pageWidth() {
	return document.body.scrollWidth;
}

function adjust() {
	var w = $('#pop-tip').width();
	var h = $('#pop-tip').height();

	var t = scrollY() + (windowHeight() / 2) - (h / 2);
	if (t < 0) {
		t = 0;
	}

	var l = scrollX() + (windowWidth() / 2) - (w / 2);
	if (l < 0) {
		l = 0;
	}

	$('#pop-tip').css({
		left : l + 'px',
		top : t + 'px',
		display : 'block'
	});
}

function windowHeight() {
	var de = document.documentElement;

	return self.innerHeight || (de && de.clientHeight)
			|| document.body.clientHeight;
}

function windowWidth() {
	var de = document.documentElement;

	return self.innerWidth || (de && de.clientWidth)
			|| document.body.clientWidth
}

function scrollY() {
	var de = document.documentElement;

	return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
}

function scrollX() {
	var de = document.documentElement;

	return self.pageXOffset || (de && de.scrollLeft)
			|| document.body.scrollLeft;
}
