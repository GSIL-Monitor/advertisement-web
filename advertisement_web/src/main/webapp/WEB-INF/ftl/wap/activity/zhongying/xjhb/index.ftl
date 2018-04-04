<#include "../../../common/core.ftl" />
<@htmlHead title="领20万出行保障，送25元现金红包" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/zhongying/xjhb.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<@commonBanner path="zhongying/xjhb"/>
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
	<div id="insuranceDetailButton" class="insurance-detail-button">
		更多
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">20万元</span>交通意外险
				</div>
				<@surveyFormZhongYing/>
				<div class="form-title">
					填写信息，保单生效
				</div>
				</#if>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">
						免费领取
					</div>
				</div>
				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/zhongying/xjhb/check.png"/>
						我已阅读<#-- <a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>及 --><a href="javascript:;" onclick="showProtocol();">《安全条款》</a>并同意中英人寿致电确认免费保障生效事宜
					</span>
					<@commonProtocolContent merchantName="中英" merchantDomain="www.aviva-cofco.com.cn"/>
					<#-- <div id="activityRulePopWindow" class="hide">
						<@activityRule defaultInsurance="中英人寿" otherInsurance="平安人寿、泰康人寿、中美大都会、百年人寿、华夏保险"/>
					</div> -->
				</div>
			</div>
			<div class="activity-wrapper">
				<@activityRule defaultInsurance="中英人寿" otherInsurance="平安人寿、泰康人寿、中美大都会、百年人寿、华夏保险"/>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footerZhongYing activityPath="${activityPath}"/>
	</div>
</div>
<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/activity/zhongying/xjhb/insurance-detail.png">
		</div>
	</div>
</div>
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<#-- 邮箱弹窗 -->
<@emailPopWindow />
</#if>
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">中英交通意外险</span>，中英客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与问卷调查领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>