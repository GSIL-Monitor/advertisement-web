<#include "../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/loan/vipkid/result.css"] />
</@htmlHead>
<@calculatePage/>
	<div class="result-box">
		<div class="result-tip-logo"></div>
		<div class="title"><span class="text-success">恭喜您获得688元英语试听课！</span></div>
		<div class="text"><span class="money">VIPKID</span>客服人员将会致电与您联系，落实课程事宜，请注意接听<span class="money">010</span>开头的电话！</div>
		<#if channel?? && channel.key == 'whhvipkid'>
		
		<#else>
			<div class="kefu"></div>
		</#if>
		<div class="kefu-time">工作日：24小时内与您联系</div>
		<div class="kefu-time">休息日：48小时内与您联系</div>
		<#if channel?? && channel.key == 'whhvipkid'>
			<div class="we-chat">
				<div class="weChat-content">
				<p class="weChat-title">扫二维码关注“惠学啦”公众号</p>
				<div class="weChat-subtitle"><img src="${ossUrl}/img/wap/loan/vipkid/mfl/one-thousand@3x.png" alt=""></div>
				<div class="weChat-qrcode"><img src="${ossUrl}/img/wap/loan/vipkid/mfl/qrcode.png?${cdnFileVersion}" alt=""></div>
				<p class="weChat-foot">关注微信公众号：huixuela</p>
				</div>
			</div>
		<#else>
			<#if advertisementList?? && (advertisementList?size>0)>
			<div class="ad-split"></div>
			</#if>

			<#list advertisementList as advertisement>
				<div class="coupon-area">
					<a href="${advertisement.link}"><img src="${advertisement.imageUrl}" alt=""></a>
				</div>
			</#list>
		</#if>
	</div>
<@htmlFoot/>