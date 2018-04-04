<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/zhongying/xjhb.css"] />
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
		亲爱的${insurant.name}，恭喜您已成功提交申请<span class="money">${insurance.fullName}</span>。${merchant.fullName}工作人员近期会通过<span class="money">${merchant.telephone}</span>给您致电确认保险生效事宜及提供保险服务，为您提供更全面的保障计划，请注意接听。
	</div>
	<div class="result-survey-form">
		<div class="result-survey-title">
			<p>有99%领取赠险的用户还关注了</p>
			<h2>平安鸿运随行意外保障计划</h2>
		</div>
		<div class="result-survey-form-area">
			<@simpleCalculateDescription/>
			<@pinganHongYunCalculateForm activityPath="/pingan/swzx"/>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/xjhb"/>
</div>
<@notOriginalMerchantAlert/>
<@htmlFoot />