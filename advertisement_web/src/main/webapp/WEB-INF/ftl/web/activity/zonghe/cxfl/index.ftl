<#include "../../../common/core.ftl" />
<@htmlHead title="综合出行福利大放送活动" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js"] />
	<@cssFile file=["web/activity/zonghe/cxfl.css", "plugins/jquery.datetimepicker.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>信息已成功提交，<span class="money">阳光客服</span>后续将致电以确认免费保险生效事宜。</p>
	<p class="coupon-tip">滴滴红包已发送到您的手机，请注意查收。</p>
</#macro>
<@resultCalculatePopWindow actionValue="再送"/>
<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<div class="header-embed">
	<@commonHeader />
</div>
<@commonBanner path="zonghe/cxfl"/>
<div class="counter-wrap">
	<div class="counter">已有<span><b class="animated tada">${appliedCount}</b></span>位用户成功领取</div>
</div>
<div class="container">
	<div class="wrapper">
		<div class="form-wrap">
			<div class="title clearfix">
				<h2 class="left l">完善信息</h2>
				<h2 class="right r">保障范围</h2>
			</div>
			<div class="ddhb-form">
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton"></div>
				</div>		
				<img class="coupon" src="${cdnUrl}/img/web/activity/zonghe/cxfl/description.png" alt="">
			</div>
			<div class="hr"></div>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</div>
<@htmlFoot/>