<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取1万元账户安全保障" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/zhxkj-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安账户险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<@commonHeader description=""/>
<@commonBanner path="pingan/zhxkj" />
<div class="container">
	<div class="wrapper">
		<div class="form-wrap clearfix">
			<div class="form-area left">
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取</div>
				</div>		
				<#-- 条款 -->
				<@pinganProtocol/>
			</div>
			<div class="insurance-detail right">
				<img src="${cdnUrl}/img/web/activity/pingan/zhxkj/insurance-detail.png" alt="">
			</div>
		</div>
		<div class="insurance-description clearfix">
			<div class="box box-left left">
				<img src="${cdnUrl}/img/web/activity/pingan/zhxkj/insurance-description1.png"/>
				<img src="${cdnUrl}/img/web/activity/pingan/zhxkj/insurance-description2.png"/>
				<img src="${cdnUrl}/img/web/activity/pingan/zhxkj/insurance-description3.png"/>
			</div>
		</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}" chineseName=""/>
</div>
<@htmlFoot/>