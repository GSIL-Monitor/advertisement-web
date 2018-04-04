<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/cycx.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container result-container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description="" headerClass="result-header"/>
	<div class="bg-wrap"><img src="${cdnUrl}/img/wap/activity/pingan/cycx/img_top.png" alt=""></div>
	<div class="bg-wrap bg">
		亲爱的<span>赵玉</span>，恭喜您已经成功提交申请<span class="bx-name">平安畅行天下春运出行保障</span>。<span>平安</span>工作人员近期会通过给您致电确认保险生效事宜及提供保险服务，为您提供更全面的保障计划，请注意接听。
	</div>
	<div class="bg-wrap">
		<img src="${cdnUrl}/img/wap/activity/pingan/cycx/img_middle.png" alt="">
	</div>
	<div class="bg-wrap bg button-wrap">
		<img class="share-wenan" src="${cdnUrl}/img/wap/activity/pingan/cycx/share_wenan.png" alt="">
		每逢佳节倍思亲，将这份平安的祝福，分享给你最在乎的家人朋友吧，祝愿旅途平安！
		<h3>分享后还可领取100元滴滴红包</h3>
		<a href="javascript:void(0)" class="button"><img src="${cdnUrl}/img/wap/activity/pingan/cycx/button_share.png" alt=""></a>
	</div>
	<div class="bg-wrap">
		<img src="${cdnUrl}/img/wap/activity/pingan/cycx/img_bottom.png" alt="">
	</div>
	<#-- 保单详情 -->
	<#-- <div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
			<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/table-header.png" class="table-header" />
			<@policyResultTable/>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
	<div class="policy-footer result-footer">
		<@footer activityPath="/pingan/cycx"/>
	</div> -->
</div>
<#-- <@notOriginalMerchantAlert/> -->
<#-- 尾部按钮 -->
<#-- <@commonFooterButton/> -->
<#if jsSignature??>
	<@wxShare title="" description="" imgUrl="" link=""/>
</#if>
<@htmlFoot />