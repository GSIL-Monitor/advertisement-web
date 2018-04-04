$(function() {
	$('#openProduct').on('click', function() {
		if($(this).parent().next('.widget-content').hasClass('open-widget-content')) {
			$(this).children('span').text('展开');
			$(this).parent().next('.widget-content').removeClass('open-widget-content');
			$(this).parent().parent().css('z-index', 'auto');
		} else {
			$('.widget-box').css('z-index', 'auto');
			$('.widget-content').removeClass('open-widget-content');
			$(this).children('span').text('收起');
			$(this).parent().next('.widget-content').addClass('open-widget-content');
			$(this).parent().parent().css('z-index', 11);
		}
	});

	$('#telRecordBtn').on('click', function() {
		if($('#telRecordBtn').hasClass('open')) {
			$('#telRecordMask').removeClass('fadein');
			$('body').removeClass('modal-open');
			$('#telRecordBtn').removeClass('open');
			$('#telRecordTip').removeClass('show-tel-record-tip');
		} else {
			$('#telRecordBtn').addClass('open');
			$('#telRecordMask').addClass('fadein');
			$('body').addClass('modal-open');
			$('#telRecordTip').addClass('show-tel-record-tip');
			$('.btn-open').children('span').text('展开');
			$('.widget-content').removeClass('open-widget-content');
			$('.widget-box').css('z-index', 'auto');
		}
	});

	$('#userBtn').on('click', function() {
		if($('#userBtn').hasClass('info-up')) {
			$('#userBtn').removeClass('info-up');
			$('#userBox').removeClass('user-box-up');
		} else {
			$('#userBtn').addClass('info-up');
			$('#userBox').addClass('user-box-up');
			$('.btn-open').children('span').text('展开');
			$('.widget-content').removeClass('open-widget-content');
			$('.widget-box').css('z-index', 'auto');
		}
	});

	$('#telRecordNav li').each(function(index) {
		$(this).click(function() {
			$('#telRecordNav li').removeClass('act');
			$(this).addClass('act');
			$('#telRecordCont > li').addClass('hide');
			$('#slide'+index).removeClass('hide');
		})
	});

	$('.audio-btn').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('play')) {
				$(this).next('audio').get(0).pause();
				$(this).removeClass('play');
			} else {
				document.addEventListener('play', function(e){
				    var audios = document.getElementsByTagName('audio');
				    for(var i = 0, len = audios.length; i < len;i++){
				        if(audios[i] != e.target){
				            audios[i].pause();
				        }
				    }
				}, true);

				$('.audio-btn').removeClass('play');
				$(this).next('audio').get(0).play();
				$(this).addClass('play');
			}
		});
	});

	$('#noteBtn').on('click', function() {
		$('#noteTip').css('display', 'block');
		$('#noteBtn').css('display', 'none');
	});

	$('#minimize').on('click', function() {
		$('#noteBtn').css('display', 'block');
		$('#noteTip').css('display', 'none');
	});

	$('#submitNote').on('click', function() {
		$.ajax({
            type:"POST",
            dataType:"json",
            url: "/ms/admin/staff/workorder/remark.do",
            async:false,
            data:{
                
            },
            success:function(data){
                if(data.retCode === "200"){
                    popWindow.showPopTipWithContent('#okTip', content);
                } else{
                    $t = 0;
                    errorCommit('#mobile', data.retDesc, 2);
                    return;
                }
            }
        });
	});

	$('#nextWorkOrder').on('click', function(){
		var nextWorkOrderId = $('#nextWorkOrderId').val();
		if (isNumber(nextWorkOrderId)) {
			location.href=$('#absolutePathPrefix').val() + '/admin/staff/workorder/detail.do?workOrderId=' + nextWorkOrderId;
		}
	})

	$('#userStarList li').each(function(index) {
		$(this).click(function() {
			$('#userStarList li').removeClass('stared');
			var clickedStarIndex = index;
			$('#userStarList li').each(function(index) {
				if(index <= clickedStarIndex) {
					$(this).addClass('stared');
					$('#userStarVal').val(clickedStarIndex + 1);
				}
			})
		})
	});

	$('#orderStarList li').each(function(index) {
		$(this).click(function() {
			$('#orderStarList li').removeClass('stared');
			var clickedStarIndex = index;
			$('#orderStarList li').each(function(index) {
				if(index <= clickedStarIndex) {
					$(this).addClass('stared');
					$('#orderStarVal').val(clickedStarIndex + 1);
				}
			})
		})
	});

	var selectedUserTagArr = [];
	$('#userTagList li').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				selectedUserTagArr.splice($.inArray($(this).attr('index'), selectedUserTagArr),1);
			} else {
				$(this).addClass('selected');
				selectedUserTagArr.push($(this).attr('index'));
			}

			var userTagsStr = selectedUserTagArr.join(',');
			$('#userTagsVal').val(userTagsStr);
		});
	});

	var selectedOrderTagArr = [];
	$('#orderTagList li').each(function(index) {
		$(this).click(function() {
			if($(this).hasClass('selected')) {
				$(this).removeClass('selected');
				selectedOrderTagArr.splice($.inArray($(this).attr('index'), selectedOrderTagArr),1);
			} else {
				$(this).addClass('selected');
				selectedOrderTagArr.push($(this).attr('index'));
			}

			var orderTagsStr = selectedOrderTagArr.join(',');
			$('#orderTagsVal').val(orderTagsStr);
		});
	});
});