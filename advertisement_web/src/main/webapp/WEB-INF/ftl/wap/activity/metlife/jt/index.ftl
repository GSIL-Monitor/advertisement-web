<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万元交通意外保障" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/activity/metlife/jt.css", "wap/base.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<@commonHeaderMetlife/>
	<@commonBanner path="metlife/jt"/>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">100万元</span>交通意外险
				</div>
				<@surveyForm/>
				<div class="form-title" style="margin-top:2rem;">
					填写信息，保单生效
				</div>
				</#if>
				<#-- 字段 -->
				<@commonZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<#-- 条款 -->
				<@commonProtocol merchantName="大都会" merchantDomain="www.metlife.com.cn"/>

				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitButton">
					</div>
				</div>
			</div>
		</div>

		<div class="product-description">
			<div class="product-description-title">
				交通综合保障详情
			</div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="28%">
						<p class="product-description-header">产品名称</p>
					</td>
					<td width="22%">
						<p class="product-description-header">产品期限</p>
					</td>
					<td width="50%">
						<p class="product-description-header">保险责任描述</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>交通综合保障</p>
					</td>
					<td>
						<p><span class="money">30天</span></p>
					</td>
					<td class="product-detail">
						<p>1. 飞机意外身故/残疾赔付<span class="money">100万元</span>。</p>
						<p>2. 水陆公交意外身故/残疾赔付<span class="money">10万元</span>。</p>
						<p>3. 自驾（驾驶＋乘坐）意外身故/残疾赔付<span class="money">10万元</span>。</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="insurance-provider">
			以上保险由<span class="money">远山保险和大都会保险</span>联合提供
		</div>
		<#-- 活动规则 -->
		<@activityRule defaultInsurance="大都会保险" otherInsurance=""/>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footerMetlife activityPath="${activityPath}"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">大都会交通意外险</span>，大都会客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与问卷调查领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>