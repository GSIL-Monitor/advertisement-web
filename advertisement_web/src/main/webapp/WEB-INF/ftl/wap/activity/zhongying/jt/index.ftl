<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取20万元交通意外保障" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/activity/metlife/jt.css", "wap/base.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<@commonHeaderZhongYing/>
	<@commonBanner path="zhongying/jt"/>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">20万元</span>交通意外险
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
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/common/check.png"/>
						我已知晓并许可中英人寿客服通过950951专线致电我沟通赠险领取事宜
					</span>
				</div>
				
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
						<p><span class="money">90天</span></p>
					</td>
					<td class="product-detail">
						<p>1. 飞轨道客运车辆<span class="money">20万元</span>。</p>
						<p>2. 客运汽车<span class="money">20万元</span>。</p>
						<p>3. 民航客运飞机<span class="money">20万元</span>。</p>
						<p>4. 非营运汽车<span class="money">8万元</span>。</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="insurance-provider">
			以上保险由<span class="money">远山保险和中英人寿</span>联合提供
		</div>
		<#-- 活动规则 -->
		<div class="activity-rule">
			<h3>投保须知</h3>
			<p>1、有效参与用户身体健康，须要在${indexRuleAgeConfig!"25-50"}周岁</p>
			<p>2、每位用户限领1份赠险，多次领取无效</p>
			<p>3、如有疑问，可致电服务热线：010-64167126或发送Email至服务邮箱：service@yuanshanbao.com</p>
		</div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footerZhongYing activityPath="${activityPath}"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
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