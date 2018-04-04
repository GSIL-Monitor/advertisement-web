$(function() {
	var $carouselInner = $('#carouselInner');
	
	$carouselInner.unslider({
		speed: 1000,
		delay: 8000,
		complete: function() {},  
		keys: true,
		dots: true,
		fluid: false,
		arrows: false,
		autoplay: true    
	});
});