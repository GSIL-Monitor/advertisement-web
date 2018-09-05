<template>
<div id="myPrizeTip" class="my-prize-tip hide">
	<div class="tip-window">
		<div class="tip-content">
			<div id="closeMyPrizeBtn" class="close"></div>
			<h1>我的奖品</h1>
			<ul id="myPrizeList" class="my-prize-list">

			</ul>
			<div class="no-prize" id="noPrize">
				还没有领取奖品，点击抽奖立即领取
			</div>
		</div>
	</div>
</div>
</template>

<script>
export default {
	name: 'myPrizetip',
	data() {
		return {}
	},
	props: {
		path: {
			type: String,
			default: 'common'
		}
	},
	methods: {},
	computed: {
	}
}
</script>

<style lang="less" scoped>
.my-prize-tip{
	position: absolute;
  width: 100%;
	z-index: 9999;
	.tip-window{
		position: absolute;
    left: 50%;
		text-align: center;
		width: 90%;
    background-color: #fff;
		border-radius: 5px;
		transform: translateX(-50%);
		.tip-content{
			padding: .2rem 0 .3rem;
			text-align: center;
			.close{
				position: absolute;
				right: -.25rem;
				top: -.25rem;
				width: .61rem;
				height: .61rem;
				img{
					display: block;
					width: 100%;
				}
			}
			h1{
				text-align: center;
				font-size: .4rem;
				border-bottom: 1px solid #ccc;
				padding-bottom: .2rem;
				font-weight: 400;
			}
			.my-prize-list{
				max-height: 8rem;
    		overflow: auto;
			}
			.no-prize{
				text-align: center;
				font-size: .3rem;
				margin-top: .2rem;
			}
		}
	}
}
</style>
