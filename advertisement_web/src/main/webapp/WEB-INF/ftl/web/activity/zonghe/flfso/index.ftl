<#include "../../../common/core.ftl" />
<@htmlHead title="综合出行福利大放送活动" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js"] />
	<@cssFile file=["web/activity/zonghe/flfso.css", "plugins/jquery.datetimepicker.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>信息已成功提交，<span class="money">保险公司客服</span>后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow actionValue="再送"/>
<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<@vertifyCodePopWindow />
<div class="container">
	<div class="header-embed">
		<@commonHeader />
	</div>
	<div class="banner-wrap">
		<div class="banner">
			<#if customBannerConfig?? && (customBannerConfig?length > 0)>
			<img src="${customBannerConfig}">
			<#else>
			<img src="${cdnUrl}/img/web/activity/zonghe/flfso/banner.jpg?${cdnFileVersion}">
			</#if>
		</div>
	</div>
	<div class="wrapper">
		<div class="form-header">
			<div class="count-backwards">
				<span class="l">仅剩</span>
				<ul class="cards">
					<#list reverseCount as count>
						<li>${count}</li>
					</#list>
				</ul>
				<span class="r">份</span>
			</div>
		</div>
		<div class="form-wrap">
			<div class="title clearfix">
				<div class="title-detail">
					<i class="icon-left"></i>
					<span class="form-title">输入信息，速领保障</span>
					<i class="icon-right"></i>
				</div>
			</div>
			<div class="ddhb-form">
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton"></div>
				</div>
				<div class="protocal-area clearfix">
					<div class="protocal-item">
						<i class="check"></i>
						<span>我已阅读<a href="###" onclick="showActivityRule();">活动规则</a></span>
					</div>
					<div class="protocal-item">
						<i class="check"></i>
						<span>我已阅读<a href="###" onclick="showProtocol();">赠险条款</a></span>
					</div>
				</div>
				<@commonProtocolContent/>
				<div id="activityRulePopWindow" class="hide">
					<@activityRule/>
				</div>
			</div>
		</div>
		<#if channel?? && channel.key=='woyxflfso'>
		<#else>
		<div class="insurance-provider-area">
			<img src="${cdnUrl}/img/web/activity/zonghe/flfso/insurance-provider.png">
		</div>
		</#if>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}"/>
</div>
<@htmlFoot/>