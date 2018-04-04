<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/zsm.css"] />
</@htmlHead>

<@notOriginalMerchantAlert/>

<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<#-- 保单详情 -->
	<div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
			<@policyResultTable/>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/zsm/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
</div>
<div class="policy-footer">
	<@footer activityPath="/pingan/zsm"/>
</div>
<@commonFooterButton/>
<@htmlFoot />