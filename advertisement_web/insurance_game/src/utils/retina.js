(function () {
	// 处理retina屏幕显示效果
	var classNames = []
	let pixelRatio = window.devicePixelRatio || 1
	classNames.push('pixel-ratio-' + Math.floor(pixelRatio))
	if (pixelRatio >= 2) {
		classNames.push('retina')
	}
	let html = document.getElementsByTagName('html')[0]
	classNames.forEach((className) => html.classList.add(className))

	let ua = window.navigator.userAgent.toLowerCase()
	if (ua.match(/MicroMessenger/i) === 'micromessenger') {
		html.classList.add('wechat')
	}
})()
