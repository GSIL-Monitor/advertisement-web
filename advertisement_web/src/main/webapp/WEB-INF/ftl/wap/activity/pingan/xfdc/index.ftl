<#include "../../../common/core.ftl" />
<@htmlHead title="参与调查免费送100万出行意外险" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/xfdc.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>

	<@commonBanner path="pingan/xfdc"/>

	<div class="wrapper">
		<div class="pen-area">
			<i class="form-pen">
			</i>
		</div>
		<div class="form-header">
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<h2>填写调查问卷</h2>
			<#else>
			<h2>填写信息领取赠险</h2>
			</#if>
		</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<#-- 问卷 -->
			<@surveyForm/>
			<div class="form-split"></div>
			</#if>
			<#-- 字段 -->
			<input type="hidden" id="${smsId}" value="${smsToken}" />
			<#-- 字段 -->
			<@embededTitleZengXianForm/>

			<#-- 条款 -->
			<@pinganProtocol/>

			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					免费领取
				</div>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />

			<@warmTip/>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="description-title">
				<img src="${cdnUrl}/img/wap/activity/pingan/xfdc/product-description.png"/>
			</div>
			<div class="">
				<div class="product-description">
					<table border="0" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th align="left">保障内容</th>
								<th align="right">最高保额</th>
							</tr>
						</thead>
						<tr>
							<td align="left">
								<p>飞机意外身故/残疾</p>
							</td>
							<td align="right">
								100万
							</td>
						</tr>
						<tr>
							<td align="left">
								<p>火车意外身故/残疾</p>
							</td>
							<td align="right">
								50万
							</td>
						</tr>
						<tr>
							<td align="left">
								<p>轮船意外身故/残疾</p>
							</td>
							<td align="right">
								50万
							</td>
						</tr>
						<tr>
							<td align="left">
								<p>汽车（含出租车）意外身故/残疾</p>
							</td>
							<td align="right">
								10万
							</td>
						</tr>
						<tr>
							<td align="left">
								<p>公共交通意外伤害医疗保险</p>
							</td>
							<td align="right">
								2万
							</td>
						</tr>
						<tr>
							<td align="left">
								<p>行李物品和旅行证件损失</p>
							</td>
							<td align="right">
								500元
							</td>
						</tr>
					</table>
				</div>
			</div>
			<#-- 活动规则 -->
			<@activityRule/>
		</div>
		<#-- 尾部 -->
		<div class="footer">
		<input type="hidden" id="activityPath" value="${activityPath}"/>
		<span>
			<#-- <p>版权所有 © 中国平安保险（集团）股份有限公司</p> -->
			<p>版权所有 © 北京远山保科技有限公司</p>
			<p>未经许可不得复制、转载或摘编，违者必究!</p>
			<#-- <p>本DMP服务由北京远山保科技有限公司提供</p> -->
			<p>京ICP备16055004号-1</p>
		</span>
	</div>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算领取100元滴滴红包" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>