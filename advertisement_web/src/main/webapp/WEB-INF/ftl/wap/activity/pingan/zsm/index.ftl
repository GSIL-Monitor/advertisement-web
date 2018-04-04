<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取2万意外医疗保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/zsm.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<@commonHeader/>
	<@commonBanner path="pingan/zsm"/>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">

			<div class="form-title">
				保障说明：乘坐公共交通工具时，如眼部或身体受到意外伤害时，均获得<span class="money">2万元</span>医疗额度报销。
			</div>
			<#-- 字段 -->
			<@commonZengXianForm/>

			<#-- 条款 -->
			<@pinganProtocol/>
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					免费领取
				</div>
			</div>

			<@warmTip/>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="description-title">
				免费领2万意外医疗保障
			</div>
			<div class="">
				<div class="product-description">
					<table border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="35%">产品名称</th>
								<th width="22%">产品期限</th>
								<th width="40%">保险责任描述</th>
							</tr>
						</thead>
						<tr>
							<td>平安交通意外伤害医疗保险（眼部及身体部分享受保障）</td>
							<td>
								<p style="text-align: center;"><span class="money">180天</span></p>
							</td>
							<td>
								交通意外伤害医疗责任（保额为<span class="money">2万元</span>，免赔<span class="money">100元</span>后按<span class="money">90%</span>赔付）
							</td>
						</tr>
					</table>
				</div>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<@insuranceProvider/>
			<#-- 活动规则 -->
			<@activityRule/>
		</div>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
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
<@resultPopWindow title="" buttonText="立即领取${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>