<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取10万元意外出行险" description="">
	<@jsFile file=["common/zengxian.js", "plugins/jquery.datetimepicker.js"] />
	<@cssFile file=["web/activity/yangguang/wnhh-web.css", "plugins/jquery.datetimepicker.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">阳光10万元出行意外险</span>，阳光客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="好的"/>
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<@emailPopWindow />
</#if>
<@vertifyCodePopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/activity/zhongying/xjhb/insurance-detail.png">
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$('#insuranceDetailButton').click(function(){
			TipWindow.showTip('#insuranceDetailTip');
		});
		$('.tip-overlay').click(function() {
			if (TipWindow.isShow('#insuranceDetailTip')) {
				TipWindow.hide('#insuranceDetailTip');
			}
		});
		$('#insuranceDetailTip').click(function(){
			TipWindow.hide('#insuranceDetailTip');
		});
	});
</script>
<div class="container">
	<div class="wrapper">
		<div class="banner-wrap">
			<div class="banner">
				<#if customBannerConfig?? && (customBannerConfig?length > 0)>
				<img src="${customBannerConfig}">
				<#else>
				<img src="${cdnUrl}/img/web/activity/yangguang/wnhh/banner.jpg?${cdnFileVersion}">
				</#if>
			</div>
		</div>
		<div class="form-wrap clearfix">
			<div class="form-head"></div>
			<div class="survey-area left">
				<div class="survey-title"></div>
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<@surveyForm/>
				</#if>
			</div>
			<div class="form-area right">
				<div class="form-title"></div>
				<@embededTitleZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取</div>
				</div>		
				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/yangguang/wnhh/checked.png"/>
						本人已阅读并同意阳光人寿保险<a href="javascript:;" onclick="showProtocol();">《个人信息使用授权》</a>
					</span>
					<@commonProtocolContent merchantName="" merchantDomain=""/>
				</div>
			</div>
			<div class="desc-wrap">
				<div class="detail-title"></div>
				<div class="desc"></div>
			</div>
		</div>
	</div>
	<@footer activityPath="${activityPath}"/>
</div>
<@htmlFoot/>