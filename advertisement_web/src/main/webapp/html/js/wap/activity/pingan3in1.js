$(function() {
	$('#tabOption li').each(function() {
		$(this).click(function() {
			changeType($(this).attr('value'));
		});
	});

	$('#submitButton').click(function(){
		submitForm(false);
	});

	$('#submitButtonWithEmailTip').click(function(){
		submitFormWithEmailTip();
	});

	$('#submitButtonWithSurveyAndEmailTip').click(function(){
		submitFormWithSurveyAndEmailTip();
	});

	$('#submitButtonWithSurvey').click(function(){
		submitFormWithSurvey(false);
	});

	$('#submitButtonWithResultTip').click(function(){
		submitForm(true);
	});

	$('#submitButtonWithSurveyAndResultTip').click(function(){
		submitFormWithSurvey(true);
	});

	$('#calculateButton').click(function(){
		calculate();
	});

	$('#calculateButtonWithEmailTip').click(function(){
		calculateWithEmailTip();
	});

	$('#surveyButton').click(function(){
		survey(false);
	});

	$('#surveyButtonWithEmailTip').click(function(){
		survey(true);
	});

	$('#participantButton').click(function(){
		location.href=getRealPath('/activity{0}/survey.html?gorderId=') + $('#gorderId').val();
	});

	$('#couponButton').click(function(){
		location.href=didiLink;
	});

	$('#giftButton').click(function() {
		location.href=giftLink;
	});

	$('#zhuanpanButton').click(function() {
		location.href='/m/activity/pingan/zhuanpan.html?channel=dd';
	});

	popupInput('#insuredSumValue', '#insuredSum', '#insuredSumPopTipList');
	popupInput('#genderValue', '#gender', '#genderPopTipList');
	selectInput('#insuredSumSelectValue', '#insuredSum', '#insuredSumPopTipList');

	$('#insuredSumPopTipList').find('li').click(function(){
		var value = $(this).attr('reference');
        $('#dailyAllowanceValue').text(value + "元/天");
        $('#dailyAllowance').val(value);
        TipWindow.hide('#insuredSumPopTipList');
    });


	$('div[name="payWayValue"]').click(function() {
		$('#payWay').val($(this).attr('value')).change();
		$('div[name="payWayValue"]').each(function() {
			$(this).removeClass('radio-btn-select');
		});
		$(this).addClass('radio-btn-select');
	});

	$('#sendSmsButton').click(function() {
		var disabled = $('#sendSmsButton').attr('disabled');
		if (disabled == 'disabled') {
			return;
		}
		sendSmsCode(false);
	});

	selectGender('#genderRadio', '#gender');
	selectGender('#childGenderRadio', '#childGender');
});

var didiLink = 'http://t.cn/Rfy3x3C';
var giftLink = '/m/j/51xyk.html';

function selectGender(inputId, hiddenId) {
	$(inputId).find('li').click(function(){
        $(inputId).find('li').each(function(){
        	$(this).removeClass('gender-select');
        });
        clearError(hiddenId);
        $(this).addClass('gender-select');
        $(hiddenId).val($(this).attr('value'));
    });
}

function getRealPath(path) {
	if ($('#isMobilePage').val() == "true") {
		path = '/m' + path;
	}
	return $.format(path, $('#activityPath').val());
}

function changeType(type) {
	$('#tabOption li').each(function() {
		$('#' + $(this).attr('value') + 'Option').addClass('option-unselect');
		$('#' + $(this).attr('value') + 'Banner').addClass('hide');
		$('#' + $(this).attr('value') + 'Triangle').addClass('hide');
		$('#' + $(this).attr('value') + 'Title').addClass('hide');
		$('#' + $(this).attr('value') + 'Description').addClass('hide');
		$('#' + $(this).attr('value') + 'Count').addClass('hide');
	});
	$('#' + type + 'Option').removeClass('option-unselect');
	$('#' + type + 'Banner').removeClass('hide');
	$('#' + type + 'Title').removeClass('hide');
	$('#' + type + 'Triangle').removeClass('hide');
	$('#' + type + 'Description').removeClass('hide');
	$('#' + type + 'Count').removeClass('hide');
	$('#' + type + 'Image').attr('src', $.format($('#imageValue').val(), type));
	$('#type').val(type);
}

function submitForm(resultTip) {
	// TipWindow.show('请填写正确的手机号码');
	if (checkForm()) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commit.html'),
			data: getFormData(false),
			dataType: 'json',
			success: function(data) {
				handleSubmitResult(data, resultTip);
			}
		});
	}
}

function submitFormWithSurvey(resultTip) {
	if (checkSurvey() && checkForm()) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commit.html'),
			data: getFormData(true),
			dataType: 'json',
			success: function(data) {
				handleSubmitResult(data, resultTip);
			}
		});
	}
}

function submitFormWithEmailTip() {
	// TipWindow.show('请填写正确的手机号码');
	if (checkForm()) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commit.html'),
			data: getFormData(false),
			dataType: 'json',
			success: function(data) {
				handleResultWithEmailTip(data);
			}
		});
	}
}

function submitFormWithSurveyAndEmailTip() {
	if (checkSurvey() && checkForm()) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commit.html'),
			data: getFormData(true),
			dataType: 'json',
			success: function(data) {
				handleResultWithEmailTip(data);
			}
		});
	}
}

function handleSubmitResult(data, hasResultTip) {
	hideLoading();
	if (data.retCode == '200') {
		if (hasResultTip) {
			if (data.alert=='true') {
				if ($('#hasSurveyTip').val() == 'true') {
					$('#surveyGorderId').val(data.gorderId);
					$('#surveyGorderKey').val(data.gorderKey);
					TipWindow.showTip('#surveyTip');
				} else {
					var url = getRealPath('/activity{0}/result.html?gorderId=') + data.gorderId;
					url = url + "&gorderKey=" + data.gorderKey;
					if (isNotEmpty($('#type').val())) {
						url = url + '&type='+$('#type').val();
					}
					location.href = url;
				}
			} else {
				$('#resultGorderId').val(data.gorderId);
				$('#resultGorderKey').val(data.gorderKey);
				TipWindow.showTip('#resultTip');
			}
			
		} else {
			var url = getRealPath('/activity{0}/result.html?gorderId=') + data.gorderId;
			url = url + "&gorderKey=" + data.gorderKey;
			if (isNotEmpty($('#type').val())) {
				url = url + '&type='+$('#type').val();
			}
			location.href = url;
		}
		
	} else if (data.retCode == '330') {
		TipWindow.showSingle('小遗憾哦，您填写的手机号已经在近期领取过赠险，别气馁哈，领取25元现金红包压压惊', '立即领取', 'location.href="' + giftLink +'"');
	} else if (data.retCode == '331') {
		TipWindow.showSingle('小遗憾哦，您填写的身份证号已经在近期领取过赠险，别气馁哈，领取25元现金红包压压惊', '立即领取', 'location.href="' + giftLink + '"');
	} else if (data.retCode == '335') {
		TipWindow.showSingle('小遗憾哦，您暂时没有符合的保险可以领，别气馁哈，领取25元现金红包压压惊', '立即领取', 'location.href="' + giftLink + '"');
	} else if (data.retCode == '336') {
		TipWindow.showSingle('小遗憾哦，您的手机号在一年之内已领取过免费险，领取25元现金红包压压惊', '立即领取', 'location.href="' + giftLink + '"');
	} else {
		errorCommit('#' + data.retField, data.retDesc);
		return false;
	}
}

function handleResultWithEmailTip(data) {
	hideLoading();
	if (data.retCode == '200') {
		TipWindow.showTip('#emailTip');
		$('#emailTipClose').click(function(){
			setEmailAndShowResult(data.gorderId, data.gorderKey);
		});
		$('#emailTipButton').click(function(){
			setEmailAndShowResult(data.gorderId, data.gorderKey);
		});
		$('.tip-overlay').click(function() {
			setEmailAndShowResult(data.gorderId, data.gorderKey);
		});
	} else {
		handleSubmitResult(data);
	}
}

function setEmailAndShowResult(gorderId, gorderKey) {
		var url = getRealPath('/activity{0}/result.html?gorderId=') + gorderId;
		url = url + "&gorderKey=" + gorderKey;
		if (isNotEmpty($('#type').val())) {
			url = url + '&type='+$('#type').val();
		}
		if (isNotEmpty($('#email').val())) {
			url = url + '&email='+$('#email').val();
		}
		location.href = url;
}

function getFormData(hasQuestion) {
	var answer1 = $("input[name='answer1']:checked").val();
	var answer2 = $("input[name='answer2']:checked").val();
	var answer3 = $("input[name='answer3']:checked").val();
	var data = {
		name: $('#name').val(),
		mobile: $('#mobile').val(),
		identityCard: $('#identityCard').val(),
		email: $('#email').val(),
		type: $('#type').val(),
		code: $('#code').val(),
		inviteCode: $('#inviteCode').val(),
		smsCode: $('#smsCode').val(),
		smsToken: $('#' + $('#smsTokenId').val()).val() + smsTokenPrefix
	};
	if (hasQuestion) {
		data.question1 = $('#question1').text();
		data.answer1 = answer1;
		data.question2 = $('#question2').text();
		data.answer2 = answer2;
		data.question3 = $('#question3').text();
		data.answer3 = answer3;
	}
	if ($('#noIdentityCard').val() == 'true') {
		data.birthdayValue = $('#birthday').val();
		data.gender = $('#gender').val();
		data.noIdentityCard = 'true';
	}
	if ($('#hasChild').val() == 'true') {
		data.childBirthdayValue = $('#childBirthday').val();
		data.childGender = $('#childGender').val();
		data.childName = $('#childName').val();
		data.hasChild = 'true';
	}
	return data;
}

function checkForm() {
	return checkChildForm() & checkNormalInput('#name', nameErrorMessage)
		& checkMobile('#mobile', mobileErrorMessage)
		& checkIdentityCardOrBirthday()
		& (isEmpty($('#email').val()) || checkEmail('#email', emailErrorMessage));
}

function checkSurvey() {
	var answer1 = $("input[name='answer1']:checked").val();
	var answer2 = $("input[name='answer2']:checked").val();
	var answer3 = $("input[name='answer3']:checked").val();
	if (isEmpty(answer1) || isEmpty(answer2) || isEmpty(answer3)) {
		TipWindow.showSingle('亲，答完题才可以领取赠险哟');
		return false;
	}
	return true;
}

function checkIdentityCardOrBirthday() {
	var noIdentityCard = $('#noIdentityCard').val();
	if (noIdentityCard == 'true') {
		return checkNormalInput('#birthday', birthdayErrorMessage) & checkNormalInput('#gender', genderErrorMessage);
	} else {
		return checkIdentityCard('#identityCard', identityCardErrorMessage);
	}
}

function checkChildForm() {
	if ($('#hasChild').val() == 'true') {
		return checkNormalInput('#childBirthday', birthdayErrorMessage) & checkNormalInput('#childName', nameErrorMessage) & checkNormalInput('#childGender', genderErrorMessage);
	}
	return true;
}

function calculate() {
	if (checkNormalInput('#insuredSum', insuredSumErrorMessage)) {
		$('#calculateForm').submit();
	}
}


function calculateWithEmailTip() {
	if (checkNormalInput('#insuredSum', insuredSumErrorMessage)) {
		if (isNotEmpty($('#existEmail').val())) {
			$('#calculateForm').submit();
			return;
		}
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/survey/calculateCommit.html'),
			data: {
				gorderId: $('#gorderId').val(),
				insuredSum: $('#insuredSum').val(),
				payWay: $('#payWay').val()
			},
			dataType: 'json',
			success: function(data) {
				hideLoading();
				if (data.retCode == '200') {
					TipWindow.showTip('#emailTip');
					$('#emailTipClose').click(function(){
						$('#calculateForm').submit();
					});
					$('#emailTipButton').click(function(){
						$('#calculateForm').submit();
					});
					$('.tip-overlay').click(function() {
						$('#calculateForm').submit();
					});
				} else {
					TipWindow.showSingle(data.retDesc);
				}
			}
		});
	}
}

function survey(hasEmailTip) {
	var answer1 = $("input[name='answer1']:checked").val();
	var answer2 = $("input[name='answer2']:checked").val();
	var answer3 = $("input[name='answer3']:checked").val();
	if (isEmpty(answer1) || isEmpty(answer2) || isEmpty(answer3)) {
		TipWindow.showSingle('亲，答完题才可以领取滴滴红包哟');
		return;
	}
	$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/survey/commit.html'),
			data: {
				gorderId: $('#gorderId').val(),
				question1: $('#question1').text(),
				answer1: answer1,
				question2: $('#question2').text(),
				answer2: answer2,
				question3: $('#question3').text(),
				answer3: answer3
			},
			dataType: 'json',
			success: function(data) {
				if (data.retCode == '200') {
					if (hasEmailTip) {
						TipWindow.showTip('#emailTip');
						$('#emailTipClose').click(function(){
							showEmailTipAndSubmit(function(){
								location.href=didiLink;
							});
						});
						$('#emailTipButton').click(function(){
							showEmailTipAndSubmit(function(){
								location.href=didiLink;
							});
						});
						$('.tip-overlay').click(function() {
							showEmailTipAndSubmit(function(){
								location.href=didiLink;
							});
						});
					} else {
						location.href=didiLink;
					}
				} else {
					errorCommit('#' + data.retField, data.retDesc);
					return false;
				}
			}
		});
}

function showEmailTipAndSubmit(handleSuccess) {
	$.ajax({
		type: 'POST',
		url: getRealPath('/activity{0}/survey/emailCommit.html'),
		data: {
			gorderId: $('#gorderId').val(),
			email: $('#email').val()
		},
		dataType: 'json',
		success: function(data) {
			hideLoading();
			if (data.retCode == '200') {
				handleSuccess();
			} else {
				TipWindow.showSingle(data.retDesc);
			}
		}
	});
}

function showProtocol() {
	var content = $('#protocolContent').html();
	TipWindow.showSingleWithContent('安全条款', '确定', content);
}