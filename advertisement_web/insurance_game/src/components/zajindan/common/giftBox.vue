<template>
	<div class="gift-container">
		<div class="front-bg">
			<img :src="frontSrc" alt="">
		</div>
		<div class="back-bg">
			<img :src="backSrc" alt="">
		</div>
		<div class="box-lid">
			<img :src="lidSrc" alt="">
		</div>
		<div class="box-body">
			<img :src="bodySrc" alt="">
		</div>
	</div>
</template>

<script>
import ASIGN from '@/config/const'
export default {
	name: 'giftBox',
	data() {
		return {
			frontSrc: ASIGN.cdnUrl + '/zajindan/common/front_bg.png',
			backSrc: ASIGN.cdnUrl + '/zajindan/common/back_bg.png',
			lidSrc: ASIGN.cdnUrl + '/zajindan/common/box_lid.png',
			bodySrc: ASIGN.cdnUrl + '/zajindan/common/box_body.png'
		}
	}
}
</script>

<style lang="less" scoped>
.gift-container{
	position: absolute;
  left: 50%;
  top: 50%;
  width: 5rem;
  height: 5rem;
	transform: translate(-50%,-50%);
	z-index: 101;
	.front-bg{
		position: absolute;
    width: 5rem;
    height: 5rem;
    left: 0;
		bottom: 0;
		z-index: 103;
		animation: showGiftBox .5s ease-in-out;
		img{
			display: block;
			width: 100%;
		}
	}
	.back-bg{
		position: absolute;
    width: 5rem;
    height: 5rem;
    left: 0;
    bottom: 0;
		z-index: 100;
		animation: showGiftBox .5s ease-in-out;
		img{
			display: block;
			width: 100%;
		}
	}
	.box-lid {
		position: absolute;
    width: 5rem;
    height: 5rem;
    left: 0;
    bottom: 0;
		z-index: 102;
		animation: openbox .3s ease-in-out forwards;
		img{
			display: block;
			width: 100%;
		}
	}
	.box-body{
		position: absolute;
    width: 5rem;
    height: 5rem;
    left: 0;
    bottom: 0;
		z-index: 101;
		img{
			display: block;
			width: 100%;
		}
	}
}
@keyframes openbox {
	0% {
    transform: translate(0 ,0) rotate(0)
	}
	100% {
    transform: translate(-.1rem, -.5rem) rotate(-20deg);
	}
}
@keyframes showGiftBox {
	0% {
    transform: scale(.1);
	}
	100% {
    transform: scale(1);
	}
}
</style>
