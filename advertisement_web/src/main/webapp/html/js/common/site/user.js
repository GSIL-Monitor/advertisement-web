$(function() {
	$('#loginButton').click(function(){
		loginBySmsCode();
	});
});

function loginBySmsCode() {
	if (checkMobile('#mobile', mobileErrorMessage) & checkNormalInput('#smsCode', smsCodeErrorMessage)) {
		showLoading();
		$.ajax({
			type: 'POST',
			url: '/user/ajaxLogin.html',
			data: {
				username: $('#mobile').val(),
				smsCode: $('#smsCode').val()
			},
			dataType: 'json',
			success: function(data) {
				hideLoading();
				if (data.retCode=='200') {
					location.href=$('#returnUrl').val();
				} else {
					errorCommit('#' + data.retField, data.retDesc);
					return false;
				}
			},
			error: function(data) {
				hideLoading();
				errorCommit('', failErrorMessage);
			}
		});
	}
}