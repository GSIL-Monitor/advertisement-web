<#include "../../common/core.ftl" />
<@htmlHead title="幸运大转盘，白拿iPhone7 Plus" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<@cssFile file=["common/activity/zhuanpan/zhuanpan.css"] />
	<@jsFile file=["wap/activity/zhuanpan/jQueryRotate.js", "wap/activity/zhuanpan/zhuanpan.js"] />
</@htmlHead>
	<section class="top-bg"></section>
	<a href="javascript:void(0)" id="readRule" class="btn-rule"></a>
	<div class="jianbian">
		<section class="lucky-users">
			<div class="lucky-users-inner">
				<#-- <div class="title"></div> -->
				<div class="wrap">
					<ul class="lucky-users-ul" id="luckyUsersUl">
						<#list luckUserList as luckUser>
							<li>恭喜${luckUser.mobile}获得<span class="money">${luckUser.prize.name}</span></li>
						</#list>
					</ul>
				</div>
			</div>
		</section>
		<section class="zhuanpan">
			<div class="paomadeng" id="paomadeng"></div>
			<div class="prize-bg" id="prizeBg">
				<img src="${cdnUrl}/img/wap/activity/zhuanpan/prize.png" alt="">
			</div>
			<div class="pointer"></div>
			<div class="start-btn" id="startBtn"></div>
		</section>
		<section class="shadow"></section>
		<section class="space-holder"></section>
		<div class="poptip rule-tip" id="ruleTip">
			<div class="inner">
				<div id="closeBtn" class="close"></div>
				<h1>活动规则</h1>
				<div class="cont">
					<p>1、用户点击抽奖按钮即可开始抽奖。</p>
					<p>2、活动抽奖奖品包括：iPhone7 Plus，300元话费，100元滴滴红包，万达IMAX电影票及神秘礼包。</p>
					<p>3、iPhone7 Plus由第三方提供，领取奖品后需填写真实收货地址。签收快递时，请务必先核实内件与所获得商品是否一致，或完好无损。</p>
					<p>4、手机话费、电影票等虚拟类商品的信息请不要透露给第三方，请尽快充值或使用。</p>
					<p>5、虚拟类商品的发票，请您联系虚拟卡发行方申请，具体政策以发行方解释为准。</p>
					<p>6、参与抽奖并获得奖品后，可能需要您的手机号等信息以领取奖品。</p>
					<p>7、最终解释权归远山保险所有。</p>
					<p>8、本活动与Apple lnc无关。</p>
				</div>
				<div id="ruleBtn" class="button">好的</div>
			</div>
		</div>
		<div id="prizeTip" class="poptip prize-tip">
			<div class="prize-icon" id="awardReceived">
				<img id="prizeImage">
			</div>
			<div class="prize-name" id="prizeName">
			</div>
			<#-- <a id="prizeBtn" class="button"></a> -->
		</div>
	</div>
 <@htmlFoot />