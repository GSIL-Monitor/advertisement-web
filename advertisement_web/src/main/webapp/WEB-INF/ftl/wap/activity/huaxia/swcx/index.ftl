<#include "../../../common/core.ftl" />

<@htmlHead title="参与调查，免费领50万华夏出行险" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderHuaXia/>
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/huaxia/swcx/banner-${type}.png">
	</div>

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area" style="margin-top: 3rem;">
			<input type="hidden" id="type" value="${type}"/>
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<div class="form-tag-area">
				<span class="form-tag-content">参与调查，免费领50万华夏出行险</span>
				<i class="form-tag-left"></i>
				<i class="form-tag-right"></i>
			</div>
			<@surveyForm />

			<div class="form-title">
				填写信息、保单生效
			</div>
			</#if>
			<#-- 字段 -->
			<@commonZengXianForm/>

			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<#-- 条款 -->
			<@commonProtocol merchantName="华夏" merchantDomain="www.hxlife.com"/>


			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					免费领取
				</div>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="description-title">
				<span>出行保障详情</span>
			</div>
			<div class="">
				<div class="product-description">
					<table border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="25%">产品名称</th>
								<th width="20%">产品期限</th>
								<th width="55%">保险责任描述</th>
							</tr>
						</thead>
						<tr>
							<td>美猴王<br/>意外伤害</td>
							<td>180天</td>
							<td align="left">
								<p>1、飞机意外身故/残疾<span class="money">50万</span>；</p>
								<p>2、火车、地铁、轻轨、轮船意外身故/残疾<span class="money">10万</span>；</p>
								<p>3、营运汽车意外身故/残疾<span class="money">2万</span>；</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<#-- <@activityRule defaultInsurance="华夏保险" otherInsurance=""/> -->
		</div>
		<#-- 尾部 -->
		<@footerHuaXia activityPath="${activityPath}"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">华夏出行险</span>，华夏客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>