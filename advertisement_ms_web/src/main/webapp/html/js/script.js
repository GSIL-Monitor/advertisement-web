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

$(document).on('click', '.editedit', function() {
 
  tiantuTip('#tiantu-tip');
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
	}
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

function uploadByForm(fileInputName,fileInputId) {
    var formData;
    formData = new FormData();
    formData.append(fileInputName, $(fileInputId)[0].files[0]);
    $.ajax({
      url: '/ms/html/a.htm',
      type: 'POST',
      data: formData,
      dataType: 'JSON',
      processData: false,
      contentType: false
    }).then(function(){
      
    },function(data){
      var trs = $('<tr></tr>');
        var td1 = $('<td></td>');
        var td2 = $('<td></td>');
        var td3 = $('<td></td>');
        var td4 = $('<td></td>');
        var hiddenInput = $('<input>');
        var hiddenInput2 = $('<input>');
        var imgs = $('<img>');
        var a1 = $('<a>修改</a>');
        var span1 = $('<span></span>');
        var a2 = $('<a>删除</a>');
        var span2 = $('<span></span>');
        $('tfoot').append(trs);
        trs.addClass('picGroupTr');
        trs.append(td1);
        
        td1.after(td2);
        td2.after(td3);
        td3.after(td4);
        
        hiddenInput.attr({type:'hidden',name:'grouppics'});
        hiddenInput.addClass('picsSrc')
        td1.append(hiddenInput);
      
        imgs.attr('src',data);
        td1.append(imgs);
        hiddenInput2.attr({type:'hidden',name:'photoTitle'});
        td2.append(hiddenInput2);
        hiddenInput2.addClass('photoTitle');
        a1.attr('href','javascript:;');
        a1.addClass('editedit');
        td3.append(a1);
        
        span1.addClass('icon icon-edit');
        a1.append(span1);
        
        a2.attr('href','javascript:;');
        a2.addClass('removeremove');
        td4.append(a2);
        
        span2.addClass('icon icon-remove');
        a2.append(span2);
        hiddenInput.val(data);
        hiddenInput2.val();
        td2.html($('#onloadPicTitle2').val());
    });
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
		childiFrame.contentWindow.uptext();
		infoInput.value = ueCache.value;
	};
};


