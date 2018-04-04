$(function() {
	$('#tabOption li').each(function() {
		$(this).click(function() {
			changeType($(this).attr('value'));
		});
	});

	$('.field-radio').find('label').click(function(){
		$(this).parent().parent().find('label').removeClass('radio-select');
		$(this).addClass('radio-select');
	});
	

	$('#submitButton').click(function(){
		submitForm();
	});

	$('#calculateButton').click(function(){
		calculate();
	});

	$('#surveyButton').click(function(){
		survey();
	});

	$('#participantButton').click(function(){
		location.href=getRealPath('/activity{0}/survey.html?gorderId=') + $('#gorderId').val();
	});

	$('#promotionButton').click(function(){
		gotoPromotion(false);
	});

	$('#promotionButtonInSurvey').click(function(){
		gotoPromotion(true);
	});

	popupInput('#insuredSumValue', '#insuredSum', '#insuredSumPopTipList');
	popupInput('#genderValue', '#gender', '#genderPopTipList');
	selectInput('#insuredSumSelectValue', '#insuredSum', '#insuredSumPopTipList');

	$('#insuredSumPopTipList').find('li').click(function(){
		var value = $(this).attr('reference');
        $('#dailyAllowanceValue').text('最高' + value + '元/天');
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
var zhonganLink = '/m/j/tuihuo.html';

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

function hasIndexSurvey() {
	return $('#surveyPositionConfig').val() == "index";
}

function hasPopupResult() {
	return $('#resultPagePositionConfig').val() == "popup";
}

function hasPopupEmail() {
	return $('#emailPositionConfig').val() == "popup";
}

function hasLastEmail() {
	return $('#emailPositionConfig').val() == "last";
}

function hasPopupSurvey() {
	return $('#surveyPositionConfig').val() == "popup";
}

function hasPopupIdentityCard() {
	return $('#identityCardPopupConfig').val() != "no";
}


function isPopupIdentityCardForce() {
	return $('#identityCardPopupConfig').val() == "force";
}

function isEmailNotOption() {
	return $('#emailOptionConfig').val() == "false";
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

function submitForm() {
	if (hasIndexSurvey()) {
		if (!checkSurvey()) {
			return;
		}
	}
	if (checkForm()) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commit.html'),
			data: getFormData(),
			dataType: 'json',
			success: function(data) {
				if (hasPopupIdentityCard() && data.hasIdentityCard == "false") {
					handleResultWithIdentityCardTip(data);
				} else if (hasPopupEmail()) {
					handleResultWithEmailTip(data);
				} else {
					handleSubmitResult(data);
				}
			},
			error: function(data) {
				hideLoading();
				errorCommit('', '信息已提交，保险公司客服后期会致电您确认保单信息等具体事宜。');
			}
		});
	}
}

function handleSubmitResult(data) {
	hideLoading();
	if (data.retCode == '200') {
		if (data.notOriginalMerchant=='true' || data.noCalculate=='true') {
			if (hasPopupSurvey()) {
				if (data.merchantKey == 'yangguang') {
					$('#surveyFormYangGuang').html($('#surveyFormYangGuangContent').val());
					$('#commonSurveyForm').empty();
				} else if (data.merchantKey == 'zhongying') {
					$('#surveyFormZhongYing').html($('#surveyFormZhongYingContent').val());
					$('#commonSurveyForm').empty();
				} else if (data.merchantKey == 'taikang') {
					$('#surveyFormTaiKang').html($('#surveyFormTaiKangContent').val());
					$('#commonSurveyForm').empty();
				} else if (data.merchantKey == 'taiping') {
					$('#surveyFormTaiPing').html($('#surveyFormTaiPingContent').val());
					$('#commonSurveyForm').empty();
				}
				$('#surveyGorderId').val(data.gorderId);
				$('#surveyGorderKey').val(data.gorderKey);
				$('#gorderId').val(data.gorderId);
				TipWindow.showTip('#surveyTip');
			} else {
				var url = getRealPath('/activity{0}/result.html?gorderId=') + data.gorderId;
				url = url + "&gorderKey=" + data.gorderKey;
				if (isNotEmpty($('#type').val())) {
					url = url + '&type='+$('#type').val();
				}
				location.href = url;
			}
		} else if (hasPopupResult()) {
			$('#resultGorderId').val(data.gorderId);
			$('#resultGorderKey').val(data.gorderKey);
			TipWindow.showTip('#resultTip');
		} else {
			var url = getRealPath('/activity{0}/result.html?gorderId=') + data.gorderId;
			url = url + "&gorderKey=" + data.gorderKey;
			if (isNotEmpty($('#type').val())) {
				url = url + '&type='+$('#type').val();
			}
			location.href = url;
		}
		
	} else if (data.retCode == '330') {
		handleFail();
	} else if (data.retCode == '331') {
		handleFail();
	} else if (data.retCode == '335') {
		handleFail();
	} else if (data.retCode == '336') {
		handleFail();
	} else if (data.retCode == '340') {
		handleInvalid(data);
	} else if (data.retCode == '202') {
		errorCommit('', '信息已提交，保险公司客服后期会致电您确认保单信息等具体事宜。');
		return false;
	} else {
		errorCommit('#' + data.retField, data.retDesc);
		return false;
	}
}

function handleFail() {
	var failText = $('#failTipTextConfig').val();
	var failLink = $('#failTipLinkConfig').val();
	if (failLink.split(',').length > 0) {
		showLoading();
		location.href="/activity/common/fail.html";
	} else {
		TipWindow.showSingle('小遗憾哦，您暂时没有符合的保险可以领，别气馁哈，领取' + failText + '压压惊', '立即领取' + failText, 'location.href="' + failLink +'"');
	}
}

function handleInvalid(data) {
	location.href="/activity/common/invalid.html?gorderKey=" + data.gorderKey;
}

function handleResultWithIdentityCardTip(data) {
	hideLoading();
	if (data.retCode == '200') {
		TipWindow.showTip('#identityCardTip');
		$('#identityCardTipButton').click(function(){
			commitIdentityCard(data.gorderId, data.gorderKey);
		});
		$('.tip-overlay').click(function() {
			if (TipWindow.isShow('#identityCardTip')) {
				commitIdentityCard(data.gorderId, data.gorderKey);
			}
		});
		$('#identityCardTipClose').click(function() {
			commitIdentityCard(data.gorderId, data.gorderKey);
		});
	} else {
		handleSubmitResult(data);
	}
}

function commitIdentityCard(gorderId, gorderKey) {
	if (!isPopupIdentityCardForce() || checkIdentityCard('#identityCard', identityCardErrorMessage)) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: getRealPath('/activity{0}/commitIdentityCard.html'),
			data: {
				gorderId: gorderId,
				gorderKey: gorderKey,
				identityCard: $('#identityCard').val()
			},
			dataType: 'json',
			success: function(data) {
				TipWindow.hide('#identityCardTip');
				if (hasPopupEmail()) {
					handleResultWithEmailTip(data);
				} else {
					handleSubmitResult(data);
				}
			}
		});
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

function getFormData() {
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
	if (hasIndexSurvey()) {
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
		& checkEmailOption();
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

function checkEmailOption() {
	if (isEmailNotOption()) {
		return checkEmail('#email', emailErrorMessage);
	}
	return isEmpty($('#email').val()) || checkEmail('#email', emailErrorMessage);
}


function calculate() {
	if (checkNormalInput('#insuredSum', insuredSumErrorMessage)) {
		if (!hasLastEmail() || isNotEmpty($('#existEmail').val())) {
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
					showEmailTip(function(){
						$('#calculateForm').submit();
					});
				} else {
					TipWindow.showSingle(data.retDesc);
				}
			}
		});
	}
}

function survey() {
	var answer1 = $("input[name='answer1']:checked").val();
	var answer2 = $("input[name='answer2']:checked").val();
	var answer3 = $("input[name='answer3']:checked").val();
	if (isEmpty(answer1) || isEmpty(answer2) || isEmpty(answer3)) {
		TipWindow.showSingle('请您回答调查中的问题');
		return;
	}
	TipWindow.hide('#surveyTip');
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
					if (hasLastEmail()) {
						showEmailTip(function(){
							location.href=$('#surveyPromotionLinkConfig').val();
						});
					} else {
						var link = $('#surveyPromotionLinkConfig').val();
						if (isNotEmpty(link)) {
							location.href=link;
						} else {
							TipWindow.showSingle('提交成功');
						}
					}
				} else {
					errorCommit('#' + data.retField, data.retDesc);
					return false;
				}
			}
		});
}

function showEmailTip(handleSuccess) {
	TipWindow.showTip('#emailTip');
	$('#emailTipClose').click(function(){
		submitEmailTip(handleSuccess);
	});
	$('#emailTipButton').click(function(){
		submitEmailTip(handleSuccess);
	});
	$('.tip-overlay').click(function() {
		if (TipWindow.isShow('#emailTip')) {
			submitEmailTip(handleSuccess);
		}
	});
}

function submitEmailTip(handleSuccess) {
	$.ajax({
		type: 'POST',
		url: getRealPath('/activity/common/emailCommit.html'),
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

function showActivityRule() {
	var content = $('#activityRulePopWindow').html();
	TipWindow.showSingleWithContent('活动规则', '确定', content);
}

function gotoPromotion(checkSurvey) {
	if (checkSurvey) {
		if (hasLastEmail() && isEmpty($('#existEmail').val())) {
			showEmailTip(function(){
				location.href=$('#surveyPromotionLinkConfig').val();
			});
			return;
		}
	}

	location.href=$('#surveyPromotionLinkConfig').val();
}