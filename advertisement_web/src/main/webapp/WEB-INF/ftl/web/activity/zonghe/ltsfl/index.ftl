<#include "../../../common/core.ftl" />
<@htmlHead title="中国联通送福利" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js"] />
	<@cssFile file=["web/activity/zonghe/ltsfl.css", "plugins/jquery.datetimepicker.css"] />
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
			<img src="${cdnUrl}/img/web/activity/zonghe/ltsfl/banner.png?${cdnFileVersion}">
		</div>
	</div>
	<div class="wrapper">
		<div class="form-header"></div>
		<div class="form-wrap">
			<div class="ddhb-form">
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton"></div>
				</div>
				<div class="protocal-area clearfix">
					<div class="protocal-item">
						<i class="check"></i>
						<span>同意<a href="###" onclick="showProtocol();">《用户协议》</a>及<a href="###" onclick="showActivityRule();">《活动说明》</a>并领取免费保障</span>
					</div>
				</div>
				<@commonProtocolContent/>
				<div id="activityRulePopWindow" class="hide">
					<@activityRule/>
				</div>
			</div>
			<div class="description"><img src="${cdnUrl}/img/web/activity/zonghe/ltsfl/description.png?${cdnFileVersion}"></div>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}"/>
</div>
<@htmlFoot/>