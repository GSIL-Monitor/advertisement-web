<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["plugins/unslider.min.js","wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/common/common-result.css", "wap/loan/sjjy/result.css"] />
</@htmlHead>
<@calculatePage/>
	<div class="result-box">
		<div class="result-tip-logo"></div>
		<div class="title"><span class="icon-success"></span><span class="text-success">申请成功！</span></div>
		<div class="text"><span class="money">您已申请成功，</span>稍后我们的工作人员会与您联系，您也可以拨打<span class="money">4001 520 882</span>资讯详情！</div>
		<div class="kefu"></div>
		<div class="kefu-time">工作日：24小时内与您联系</div>
		<div class="kefu-time">休息日：48小时内与您联系</div>
		<div class="ganxie">感谢您的支持，祝您生活愉快！</div>
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