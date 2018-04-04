<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/xxjhb.css"] />
</@htmlHead>
<@calculatePage/>
<#-- 头部信息（包括公司logo，菜单等） -->
<@commonHeader insurance="${merchant.englishName}" description=""/>
<div class="container result-survey-container">
	<#-- 保单详情 -->
	<div class="success-corner">
		<img src="${cdnUrl}/img/wap/common/result-corner.png">
	</div>
	<div class="success-title">
		<img src="${cdnUrl}/img/wap/common/success-icon.png"/>
		<p>恭喜您，提交成功！</p>
	</div>
	<div class="success-detail">
		<@resultSuccessDetail/>
	</div>
	<div class="result-survey-form">
		<div class="result-survey-title">
			<p>有99%领取赠险的用户还关注了</p>
			<h2>平安鸿运随行意外保障计划</h2>
		</div>
		<div class="result-survey-form-area">
			<@simpleCalculateDescription/>
			<@pinganHongYunCalculateForm activityPath="/pingan/swzx" promotion="true"/>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/xxjhb"/>
</div>
<@notOriginalMerchantAlert/>
<@htmlFoot />