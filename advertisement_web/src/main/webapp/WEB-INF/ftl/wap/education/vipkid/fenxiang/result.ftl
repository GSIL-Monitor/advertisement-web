<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js","wap/education/vipkid.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/education/vipkid/fenxiang/result.css"] />
</@htmlHead>
<@calculatePage/>
<div class="result-box">
	<div class="logo">
		<img src="${cdnUrl}/img/wap/education/vipkid/fenxiang/logo@3x.png" alt="">
	</div>
	<div class="top-box">
		<img class="hongbao" src="${cdnUrl}/img/wap/education/vipkid/fenxiang/hongbao@3x.png" alt="">
		<div class="title">恭喜您已成为惠学啦暑假活动推广员！</div>
		<div class="subtitle">
			您的专属推广链接如下
		</div>
	</div>

	<div class="share">
		<div class="share-content">
			<div class="share-title">分享活动 赢好礼</div>
			<img class="share-bg" src="${cdnUrl}/img/wap/education/vipkid/result/share-girl@3x.png" alt="">
			<div class="share-content-subtitle">分享试听课程，若产生支付返现<span>1000</span>元</div>
			<div class="share-btn" id="shareBtn">
				<img src="${cdnUrl}/img/wap/education/vipkid/result/button@3x.png" alt="">
			</div>
		</div>
		<div class="share-steps">
			<img src="${cdnUrl}/img/wap/education/vipkid/result/steps@3x.png" alt="">
		</div>
		<div class="share-desc">
			<img src="${ossUrl}/img/wap/education/vipkid/desc@3x.png" alt="">
			<p>➊、活动日期：2018.6.10--2018.7.30</p>
			<p>➋、用户填写个人手机号领取专属链接，成为惠学啦推广员；可通过分享推广员专属链接推荐用户领取VIPKID试听课，若用户30天内支付且度过全额退款期可享受500-1000元现金</p>
			<p>➌、被推广用户购买VIPKID课程金额在5000元以上7000元以下，推广员可获得500元现金；被推广用户购买VIPKID课程金额≥7000元,可获得1000元现金</p>
			<p>➍、被推广用户不再享受该平台的其他助学金活动，即每单支付仅能得到一次助学金</p>
			<p>➎、被推广用户购买VIPKID课程并过完全额退款期，推广员凭借付款截图及个人手机号，联系惠学啦公众号客服领取助学金</p>
			<p>➏、活动详细说明可咨询惠学啦平台客服，或者咨询平台官方客服010-64010062</p>
			<p>➐、本活动与VIPKID无关，活动解释权归惠学啦平台所有</p>
		</div>
	</div>
</div>
<div class="tip-area modalTip hide" id="modalTip">
	<div class="tip-img">
		<img src="${cdnUrl}/img/wap/education/vipkid/result/modal@3x.png" alt="">
	</div>
	<div class="modal-tip-title">点击右上角分享给好友</div>
	<#-- <div class="modal-tip-content">分享后完成购买可获得1000元现金</div> -->
</div>
<@wxShare title="免费领取价值688元VIPKID英语试听礼包" description="VIPKID在线少儿英语-纯美外教，1对1教学......" imgUrl="${ossUrl}/img/wap/education/vipkid/share.png" link="${link?no_encode}"/>
<@htmlFoot/>