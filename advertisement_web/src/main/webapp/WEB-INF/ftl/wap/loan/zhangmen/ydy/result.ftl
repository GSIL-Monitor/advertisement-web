<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/loan/zhangmen/result.css"] />
</@htmlHead>
<@calculatePage/>
	<div class="result-box">
		<div class="result-tip-logo">
			<img src="${cdnUrl}/img/wap/loan/zhangmen/ydy/result_logo.png" alt="">
		</div>
		<br/>
		<div class="title"><span class="text-success">恭喜您获得200元试听大礼包！</span></div>
		<div class="text"><span class="money">掌门1对1</span>客服人员将会致电与您联系，落实课程事宜，请注意接听电话！</div>
		<div class="kefu"></div>
		<div class="kefu-time">工作日：24小时内与您联系</div>
		<div class="kefu-time">休息日：48小时内与您联系</div>
		<#if advertisementList?? && (advertisementList?size>0)>
		<div class="ad-split"></div>
		</#if>

		<#list advertisementList as advertisement>
			<div class="coupon-area">
				<a href="${advertisement.link}"><img src="${advertisement.imageUrl}" alt=""></a>
			</div>
		</#list>
	</div>
<@htmlFoot/>