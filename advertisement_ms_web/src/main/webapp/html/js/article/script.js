$(document).ready(function() {
	$('#onloadButton').click(function() {
		if ($('#onloadPicFile').val() == '') {
			alert('请添加图片文件');
			return false;
		}
		if ($('#onloadPicTitle').val() == '') {
			alert('请输入与图片相关的标题');
			return false;
		}
	});
	$('#upLoadPicBtn').click(function() {
		if ($('#onloadPicFile2').val() == '') {
			alert('请添加图片文件');
			return false;
		}
		if ($('#onloadPicTitle2').val() == '') {
			alert('请输入与图片相关的标题');
			return false;
		}
	});

});
function windowHeight() {
	var de = document.documentElement;

	return self.innerHeight || (de && de.clientHeight) || document.body.clientHeight;
}

function windowWidth() {
	var de = document.documentElement;

	return self.innerWidth || (de && de.clientWidth) || document.body.clientWidth
}

function scrollY() {
	var de = document.documentElement;

	return self.pageYOffset || (de && de.scrollTop) || document.body.scrollTop;
}

function scrollX() {
	var de = document.documentElement;

	return self.pageXOffset || (de && de.scrollLeft) || document.body.scrollLeft;
}

function tiantuTip(tipId) {
	var w = $(tipId).width();
	var h = $(tipId).height();

	var t = scrollY() + (windowHeight() / 2) - (h / 2);
	if (t < 0) {
		t = 0;
	}

	var l = scrollX() + (windowWidth() / 3) - (w / 2);
	if (l < 0) {
		l = 0;
	}

	$(tipId).css({
		left: l + 'px',
		top: t + 'px',
		display: 'block'
	});
}

function hideTiantuTip(tipId) {
	$(tipId).css({
		display: 'none'
	});
	$('#onloadPicTitle').attr('value', '');
	$('#onloadPicTitle').attr('value', '');
}
$(document).on('click', '.editedit', function() {
 
	tiantuTip('#tiantu-tip');
	$('#tianjiatupian').click(function() {
		tiantuTip('#tiantu-tip');
	});
	$('#tipCancel').click(function() {
		hideTiantuTip('#tiantu-tip');
	});
 
});
  
$(document).on('click','.removeremove',function(){
		var removeTr = window.confirm("单击“确定”删除，单击“取消”返回");
		if(removeTr){
			$(this).parent().parent('tr').remove();
		}
});

function uploadSuccess(dataContent) {
	var data = JSON.parse(dataContent);
  	if(data.retCode=='200') {
  		hideTiantuTip('#tiantu-tip');
  		addItem(data);
  	} else {
  		alert(data.retDesc);
  	}
}
function addItem(data) {
	var trs = $('<tr></tr>');
    var td1 = $('<td></td>');
    var td2 = $('<td></td>');
    var td3 = $('<td colspan="2"></td>');
    var hiddenInput = $('<input>');
    var titleInput = $('<input>');
    var imgs = $('<img>');
    var a1 = $('<a>删除</a>');
    var span1 = $('<span></span>');
    $('tfoot').append(trs);
    trs.addClass('picGroupTr');
    trs.append(td1);
    
    td1.after(td2);
    td2.after(td3);
    
    hiddenInput.attr({type:'hidden',name:'articleImageUrl'});
    hiddenInput.val(data.imageUrl);
    hiddenInput.addClass('picsSrc')
    td1.append(hiddenInput);
  
    imgs.attr('src',data.imageUrl);
    td1.append(imgs);
    titleInput.attr({type:'text',name:'articleImageTitle'});
    titleInput.val(data.title);
    td2.append(titleInput);
    titleInput.addClass('photoTitle');
    a1.attr('href','javascript:;');
    a1.addClass('removeremove');
    td3.append(a1);
    
    span1.addClass('icon icon-remove');
    a1.append(span1);
}
function edit(){
      	$(this).siblings('.photoTitle').val($('#onloadPicTitle2').val());
      	$(this).parent('td').prev().html();
}
window.onload = function(){
	//ueditor内容获取
	var childiFrame = document.getElementById("iFrameUeditor"); 
	var innerDoc = childiFrame.contentDocument || childiFrame.contentWindow.document;
    
	// Get the Control inside iFrame Document.
	var ueCache = innerDoc.getElementById("ueCache");

	// Get value of Control
	// var childValue = childInput.value;
	 
	// Set value to the Control 
	// childInput.value = "Your value";

	var infoInput = document.getElementById("info");
	var allInputBtn = document.getElementById("allInputBtn");
	
	allInputBtn.onclick = function(){
		$('#info').val(childiFrame.contentWindow.getText());
		$('#ueditorForm').submit();
		checkResult();
	};
};
