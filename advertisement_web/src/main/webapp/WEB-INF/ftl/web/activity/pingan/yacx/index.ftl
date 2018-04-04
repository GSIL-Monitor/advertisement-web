<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万元平安出行保障" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/yacx-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<@emailPopWindow />
</#if>
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<@commonHeader description=""/>
<@commonBanner path="pingan/yacx" />
<div class="container">
	<div class="wrapper">
		<div class="form-wrap clearfix">
			<div class="form-area left">
				<div class="description-title">
					<span>
						填写信息 免费领取
					</span>
				</div>
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取</div>
				</div>		
				<#-- 条款 -->
				<@pinganProtocol/>
			</div>
			<div class="insurance-detail right">
				<div class="description-title">
					<span>
						赠险详情
					</span>
				</div>
				<img src="${cdnUrl}/img/web/activity/pingan/yacx/description.png" alt="">
			</div>
		</div>
	</div>
</div>
<div class="wrapper">
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}" chineseName=""/>
</div>
<@htmlFoot/>