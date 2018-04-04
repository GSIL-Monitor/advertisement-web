<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/partner/sqbcx.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container result-gift-container">
	<div class="result-area">
		<div class="result-header">
			<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/result-header.png"/>
		</div>
		<div class="result-content">
			<#if money??>
			<h1>恭喜您获得${money}元</h1>
			<h2>现金红包在<span class="money">收钱吧</span>商户通用</h2>
			<#else>
			<h1>小遗憾哦</h1>
			<h2>暂时无法领取红包</h2>
			</#if>
			<div class="result-content-split"></div>
			<h2>恭喜您已成功提交申请</h2>
			<h2>${insurance.fullName}</h2>
			<h3>${merchant.fullName}工作人员近期会通过<span class="money">${merchant.telephone}</span>给您致电确认保险生效事宜及提供保险服务。</h3>
		</div>
	</div>
	<div class="result-gift-flap">
		<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/result-gift-flap.png"/>
	</div>
	<div class="tip-gift-area">
		<#-- <#list failTipLinkConfig?split(",") as link>
		<div class="tip-gift-item clearfix">
			<div class="tip-gift-icon left">
				<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/result-gift.png"/>
			</div>
			<div class="tip-gift-name left">礼包<#if (link_index>0 || failTipLinkConfig?split(",")[link_index+1]??)>${link_index+1}</#if></div>
			<a href="${link}"><div class="tip-gift-button right">领<#if failTipTextConfig?split(",")[link_index]?? && (failTipTextConfig?split(",")[link_index]?length > 0)>${failTipTextConfig?split(",")[link_index]}<#else>取礼包</#if></div></a>
		</div>
		</#list> -->
	</div>
	<div class="tip-gift-footer">
		<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/result-gift-footer.png"/>
	</div>

	<#-- <div class="footer-logo">
		<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/sqb-logo.png?${cdnFileVersion}"/>
		<#if merchant.englishName?? && merchant.englishName=="pingan">
		<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/pingan-logo.png"/>
		<#else>
		<img src="${cdnUrl}/img/common/logo/${merchant.englishName}-logo.png">
		</#if>
	</div> -->
</div>
<@htmlFoot />