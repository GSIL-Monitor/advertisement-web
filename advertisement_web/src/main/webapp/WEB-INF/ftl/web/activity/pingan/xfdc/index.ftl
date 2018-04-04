<#include "../../../common/core.ftl" />
<@htmlHead title="参与调查免费送100万出行意外险" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/xfdc-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<#if surverPositionConfig?? && surverPositionConfig=="popup">
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
</#if>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<@commonBanner path="pingan/xfdc" />

	<div class="wrapper">
		<div class="pen-area">
			<i class="form-pen">
			</i>
		</div>
		<div class="form-header">
			<h2>填写调查问卷</h2>
		</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<div class="form-radio-area">
				<#-- 问卷 -->
				<@surveyForm/>
			</div>

			<div class="form-title">
				<h2>填写信息领取赠险</h2>
			</div>
			<div class="form-input-area">
				<#-- 字段 -->
				<@commonZengXianForm/>
			</div>
			<input type="hidden" id="${smsId}" value="${smsToken}" />
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					免费领取
				</div>
			</div>


			<#-- 条款 -->
			<@pinganProtocol/>

			<@warmTip/>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="description-title">
				<img src="${cdnUrl}/img/web/activity/pingan/xfdc/product-description.png"/>
			</div>
			<div class="">
				<div class="product-description">
					<table border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="25%">产品名称</th>
								<th width="55%">保障内容</th>
								<th width="20%">最高保额</th>
							</tr>
						</thead>
						<tr>
							<td rowspan="6">出行险<br/>(畅行天下)</td>
							<td>
								<p>飞机意外身故/残疾</p>
							</td>
							<td>
								100万
							</td>
						</tr>
						<tr>
							<td>
								<p>火车意外身故/残疾</p>
							</td>
							<td>
								50万
							</td>
						</tr>
						<tr>
							<td>
								<p>轮船意外身故/残疾</p>
							</td>
							<td>
								50万
							</td>
						</tr>
						<tr>
							<td>
								<p>汽车（含出租车）意外身故/残疾</p>
							</td>
							<td>
								10万
							</td>
						</tr>
						<tr>
							<td>
								<p>公共交通意外伤害医疗保险</p>
							</td>
							<td>
								2万
							</td>
						</tr>
						<tr>
							<td>
								<p>行李物品和旅行证件损失</p>
							</td>
							<td>
								500元
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="activity-rule">
				<div class="rule-title">
					<img src="${cdnUrl}/img/web/activity/pingan/xfdc/activity-rule.png"/>
				</div>
				<p>1、有效参与用户身体健康，须要在25-50周岁</p>
				<p>2、每位用户限领1份出行保障，多次领取无效</p>
				<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知</p>
				<#if channel?? && channel=="tianm">
				<p>4、如有疑问，可咨询客服qq:2013780348</p>
				<#else>
				<p>4、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）</p>
				<p>5、如有疑问，可咨询客服qq:2013780348</p>
				</#if>
			</div>
		</div>
		<div class="foot-finger">
			<img src="${cdnUrl}/img/web/activity/pingan/xfdc/finger.png"/>
		</div>
	</div>
	<#-- 尾部 -->
	<div class="footer">
		<input type="hidden" id="activityPath" value="/pingan/xfdc"/>
		<span>
			<#-- <p>版权所有 © 中国平安保险（集团）股份有限公司，未经许可不得复制、转载或摘编，违者必究!</p> -->
			<#-- <p>Copyright © ${englishName} All Rights Reserved</p> -->
			<#-- <p>本DMP服务由北京远山保科技有限公司提供，京ICP备16055004号-1</p> -->
			<p>版权所有 © 北京远山保科技有限公司，京ICP备16055004号-1</p>
			<p>未经许可不得复制、转载或摘编，违者必究!</p>
		</span>
	</div>
</div>
<@htmlFoot/>