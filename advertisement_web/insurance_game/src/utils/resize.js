(function () {
	function resize() {
		document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px'
		let deviceWidth = document.documentElement.clientWidth
		deviceWidth = deviceWidth > 750 ? 750 : deviceWidth
		document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px'
	}
	resize()
	window.onresize = resize()
})()
