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

function showTipDialog(tipId) {
	var w = $(tipId).width();
	var h = $(tipId).height();
	var t = scrollY() + (windowHeight() / 2) - (h / 2);
	if (t < 0) {
		t = 0;
	}

	var l = scrollX() + (windowWidth() / 2) - (w / 2);
	if (l < 0) {
		l = 0;
	}

	$(tipId).css({
		left : l + 'px',
		top : t + 'px',
		display : 'block'
	});
}

function hideTipDialog(tipId) {
	$('#tipCloseBtn').css('display', 'block');
	$(tipId).css({
		display : 'none'
	});
	var body = findFirstElement(getCommitIFrame(), 'body');
	body.innerHTML = '';
}

function showTipDialogAndSetContent(content, error, noClose) {
	// showTipDialog('#tipDialog');
	// $('#tipInnerText').text(content);
	// if (error) {
	// 	if (typeof (content) == "undefined" || content.length == 0) {
	// 		content = '系统错误';
	// 	}
	// 	$('#tipIcon').attr('src', '/ms/html/img/iconfont-error.png');
	// } else {
	// 	if (typeof (content) == "undefined" || content.length == 0) {
	// 		content = '操作成功';
	// 	}
	// 	$('#tipIcon').attr('src', '/ms/html/img/iconfont-success.png');
	// }
	// if (error || noClose) {
	// 	$('#tipCloseBtn').css('display', 'none');
	// }
	TipWindow.showSingleWithContent(content);
}

function getCommitIFrame() {
	var formCommitIframe = document.getElementById("formCommitIframe");
	return formCommitIframe.contentDocument
			|| formCommitIframe.contentWindow.document;
}

function checkResultAndShowDialog() {
	var innerDoc = getCommitIFrame();
	var body = findFirstElement(innerDoc, 'body');
	if (body.innerHTML.length == 0) {
		return false;
	}
	clearInterval(interval);
	var data = JSON.parse(body.innerHTML);
	var content = data.html;
	if (typeof (content) == "undefined" || content.length == 0) {
		content = data.retDesc;
	}
	if (typeof (content) == "undefined" || content.length == 0) {
		content = data.retDesc;
	}
	if (data.retcode == '200' || data.retCode == '200') {
		showTipDialogAndSetContent(content, false);
		return true;
	} else {
		showTipDialogAndSetContent(content, true);
		return true;
	}

	showTipDialogAndSetContent('系统错误', true);
	return true;
}

function findFirstElement(root, tagName) {
	var tags = root.getElementsByTagName(tagName);
	if (tags.length > 0) {
		return tags[0];
	}
	return null;
}

var interval;
var intervalTimes = 0;
var errorTimes = 0;

function showError(error) {
	clearInterval(interval);
	var innerDoc = getCommitIFrame();
	var body = findFirstElement(innerDoc, 'body');
	body.innerHTML = '';
	if (error) {
		showTipDialog('#tipDialog');
		$('#tipInnerText').text('访问超时');
		$('#tipIcon').attr('src', '/ms/html/img/iconfont-error.png');
	}

}
function checkResult() {
	interval = setInterval(function() {
		if (intervalTimes++ == 60) {
			checkResultAndShowDialog();
		} else {
			try {
				checkResultAndShowDialog();
			} catch (e) {
				if (errorTimes++ == 10) {
					checkResultAndShowDialog();
				}
			}
		}
	}, 500);
}

function confirmDelete(deleteUrl) {
	confirmSend(deleteUrl, "确定要删除数据吗");
}

function confirmUnBind(bindUrl) {
	confirmSend(bindUrl, "确定要解绑用户吗");
	dataTable.ajax.reload();
}

function confirmBind(bindUrl) {
	confirmSend(bindUrl, "确定要绑定用户吗");
	dataTable.ajax.reload();
}

function confirmOperation(operationUrl) {
	confirmSend(operationUrl, "确定要进行此操作吗");
}

function confirmSend(url, title) {
	if (confirm(title)) {
		sendAjax(url, function() {
			$('#refreshPageListButton').click();
		});
	}
}

function sendAjax(url, handleSuccess) {
	$.ajax({
		url : url,
		dataType : 'json'
	}).then(function(data) {
		if (data.retcode == '200' || data.retCode == '200') {
			var content = data.html;
			if (typeof (content) == "undefined" || content.length == 0) {
				content = '操作成功';
			}
			showTipDialogAndSetContent(content, false, true);
			if (isNotNull(handleSuccess)) {
				handleSuccess(data);
			}
		} else {
			var content = data.retDesc;
			if (typeof (content) == "undefined" || content.length == 0) {
				content = '操作失败';
			}
			showTipDialogAndSetContent(data.retDesc, true, true);
		}
		console.log(data);
	}, function() {
		showTipDialogAndSetContent('网络错误，请重试', true, true);
	});
}

function isNull(value) {
	if (typeof (value) == 'undefined') {
		return true;
	}
	if (value == null) {
		return true;
	}
	return false;
}

function isNotNull(value) {
	return !isNull(value);
}

function isEmpty(value) {
	if (isNull(value)) {
		return true;
	}
	if (value == '') {
		return true;
	}
	return false;
}

function isNotEmpty(value) {
	return !isEmpty(value);
}

function endWith(content, end) {
	var value = content.substring(content.length - end.length, content.length);
	return value == end;
}
