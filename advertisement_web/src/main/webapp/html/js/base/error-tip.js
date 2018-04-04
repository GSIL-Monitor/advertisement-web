function errorTip(id, message) {
	clearError(id);
	// var errorTip = $('<div class="error-tip clearfix"></div>');
	// errorTip.text(message);
	var errorTip = $(id).parent().next('.error-tip');
	errorTip.text(message);
	$(id).focus(function(){
		clearErrorByObject($(this));
	});
	if (isNotEmpty(selectInputMap.get(id))){
		$(selectInputMap.get(id)).focus(function(){
			clearErrorByObject($(this));
		});
		$(selectInputMap.get(id)).click(function(){
			clearErrorByObject($(this));
		});
	}
}

function errorCommit(id, message) {
	if ($(id).length <= 0) {
		TipWindow.showSingle(message);
		return;
	}
	errorTip(id, message);
	if (isNotEmpty(selectInputMap.get(id))){
		$(selectInputMap.get(id)).addClass('field-input-error');
	}
	$(id).parent().addClass('field-input-error');
}

function clearError(id) {
	$(id).parent().removeClass('field-input-error');
	$(id).parent().next('.error-tip').text('');
}

function clearErrorByObject(object) {
	object.parent().removeClass('field-input-error');
	object.parent().next('.error-tip').text('');
}

function isNumber(value) {
	var regex = /^[0-9]+.?[0-9]*$/;
	return regex.test(value);
}
