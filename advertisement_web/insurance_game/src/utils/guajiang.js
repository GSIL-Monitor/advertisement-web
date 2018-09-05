function Lottery (conNode, mask, coverImg, drawPercentCallback) {
	this.conNode = conNode
	this.mask = mask
	this.coverImg = coverImg
	this.maskCtx = null
	this.width = parseInt(window.innerWidth * 6.5 / 7.5)
	this.height = parseInt(window.innerWidth * 6.5 / 7.5 * 3.6 / 6.5)
	this.clientRect = null
	this.drawPercentCallback = drawPercentCallback
}

Lottery.prototype = {
	getTransparentPercent: function (ctx, width, height) {
		var imgData = ctx.getImageData(0, 0, width, height)
		var pixles = imgData.data
		var transPixs = []
		for (var i = 0, j = pixles.length; i < j; i += 4) {
			var a = pixles[i + 3]
			if (a < 128) {
				transPixs.push(i)
			}
		}
		return (transPixs.length / (pixles.length / 4) * 100).toFixed(2)
	},
	resizeCanvas: function (canvas, width, height) {
		canvas.width = width
		canvas.height = height
		canvas.getContext('2d').clearRect(0, 0, width, height)
	},
	drawPoint: function (x, y) {
		this.maskCtx.beginPath()
		var radgrad = this.maskCtx.createRadialGradient(x, y, 0, x, y, 30)
		radgrad.addColorStop(0, 'rgba(0,0,0,1)')
		radgrad.addColorStop(1, 'rgba(255, 255, 255, 0)')
		this.maskCtx.fillStyle = radgrad
		this.maskCtx.arc(x, y, 30, 0, Math.PI * 2, true)
		this.maskCtx.fill()
		this.drawPercentCallback.call(null, this.getTransparentPercent(this.maskCtx, this.width, this.height))
	},
	bindEvent: function () {
		var _this = this
		var device = (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()))
		var clickEvtName = device ? 'touchstart' : 'mousedown'
		var moveEvtName = device ? 'touchmove' : 'mousemove'
		if (!device) {
			var isMouseDown = false
			document.addEventListener('mouseup', function (e) {
				isMouseDown = false
			}, false)
		} else {
			document.addEventListener('touchmove', function (e) {
				if (isMouseDown) {
					e.preventDefault()
				}
			}, false)
			document.addEventListener('touchend', function (e) {
				isMouseDown = false
			}, false)
		}
		this.mask.addEventListener(clickEvtName, function (e) {
			isMouseDown = true
			var docEle = document.documentElement
			if (!_this.clientRect) {
				_this.clientRect = {
					left: 0,
					top: 0
				}
			}
			var x = (device ? e.touches[0].clientX : e.clientX) - _this.clientRect.left + docEle.scrollLeft - docEle.clientLeft
			var y = (device ? e.touches[0].clientY : e.clientY) - _this.clientRect.top + docEle.scrollTop - docEle.clientTop
			_this.drawPoint(x, y)
		}, false)

		this.mask.addEventListener(moveEvtName, function (e) {
			if (!device && !isMouseDown) {
				return false
			}
			var docEle = document.documentElement
			if (!_this.clientRect) {
				_this.clientRect = {
					left: 0,
					top: 0
				}
			}
			var x = (device ? e.touches[0].clientX : e.clientX) - _this.clientRect.left + docEle.scrollLeft - docEle.clientLeft
			var y = (device ? e.touches[0].clientY : e.clientY) - _this.clientRect.top + docEle.scrollTop - docEle.clientTop
			_this.drawPoint(x, y)
		}, false)
	},
	drawCover: function () {
		this.resizeCanvas(this.mask, this.width, this.height)
		var image = new Image()
		var _this = this
		image.onload = function () {
			_this.maskCtx.drawImage(this, 0, 0, _this.width, _this.height)
			_this.maskCtx.globalCompositeOperation = 'destination-out'
		}
		image.src = this.coverImg
	},
	init: function () {
		this.clientRect = this.conNode ? this.conNode.getBoundingClientRect() : null
		this.bindEvent()
		this.maskCtx = this.maskCtx || this.mask.getContext('2d')
		this.drawCover()
	}
}
export default Lottery
