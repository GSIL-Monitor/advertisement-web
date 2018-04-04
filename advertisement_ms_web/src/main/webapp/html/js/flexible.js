(function flexible (window, document) {
	var docEl = document.documentElement;
	var dpr = window.devicePixelRatio || 1;

	function setBodyFontSize () {
	    if (document.body) {
	      document.body.style.fontSize = (12 * dpr) + 'px';
	    }
	    else {
	      document.addEventListener('DOMContentLoaded', setBodyFontSize)
	    }
	}
	setBodyFontSize();

	function setRemUnit () {
		var cw = docEl.clientWidth;
		if(cw <= 1280) {
			cw = 1280;
		}
	    var rem = cw / 19.2;
	    docEl.style.fontSize = rem + 'px';
	}
    setRemUnit();

	window.addEventListener('resize', setRemUnit);
	window.addEventListener('pageshow', function (e) {
	    if (e.persisted) {
	        setRemUnit();
	    }
	})

	if (dpr >= 2) {
	    var fakeBody = document.createElement('body');
	    var testElement = document.createElement('div');
	    testElement.style.border = '.5px solid transparent';
	    fakeBody.appendChild(testElement);
	    docEl.appendChild(fakeBody);
	    if (testElement.offsetHeight === 1) {
	      docEl.classList.add('hairlines');
	    }
	    docEl.removeChild(fakeBody);
	}
}(window, document))