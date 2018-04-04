function check(data) {
	if (data == '1') {
		showOverlay();
		adjust();
	}
	$('#close-mem').click(function() {
		$('#overlay').fadeOut(200);
		$('#pop-tip').css({
			display: 'none'
		});
	});
};

function show() {
	showOverlay();
	adjust();
	$('#close-mem').click(function() {
		$('#overlay').fadeOut(200);
		$('#pop-tip').css({
			display: 'none'
		});
	});
};


function showOverlay() {
	$('#overlay').height(pageHeight());
	$('#overlay').width(pageWidth());
	$('#overlay').fadeTo(500, 0.5);
}

function hideOverlay() {
	$('#overlay').fadeOut(200);
	window.location.reload();
}

function pageHeight() {
	return document.body.scrollHeight;
}

function pageWidth() {
	return document.body.scrollWidth;
}

function adjust() {
	var w = $('#pop-tip').width();
	var h = $('#pop-tip').height();

	var t = scrollY() + (windowHeight() / 2) - (h / 2);
	if (t < 0) {
		t = 0;
	}

	var l = scrollX() + (windowWidth() / 2) - (w / 2);
	if (l < 0) {
		l = 0;
	}

	$('#pop-tip').css({
		left: l + 'px',
		top: t + 'px',
		display: 'block'
	});
}

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