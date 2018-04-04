$(function() {
	$('.field-radio').find('label').click(function(){
		$(this).parent().parent().find('label').removeClass('radio-select');
		$(this).addClass('radio-select');
	});
});