<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取1万元账户安全保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/zhx-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<div class="container">
	<@commonHeader/>
	<@commonBanner path="pingan/zhx" />

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">

			<div class="form-title">
				填写信息，领取1万元个人账户安全险
			</div>
			<#-- 字段 -->
			<div class="table">
				<div class="table-l">
					<@commonZengXianForm/>

						<#-- 条款 -->
						<@pinganProtocol/>


						<#-- 提交按钮 -->
						<div class="submit-button-area">
							<div class="submit-button" id="submitButton">
								立即领取
							</div>
						</div>
						<input type="hidden" id="smsTokenId" value="${smsId}" />

						<@warmTip/>
					</div>
					<@randomSmsToken/>
					<div class="table-r">
						<img class="pingan" src="${cdnUrl}/img/web/activity/pingan/zhx/logo1.png">
						<div class="title">网络e护个人账户安全险</div>
						<table>
							<tr><td class="l">保障项目</td><td class="r">个人银行/网络账户损失</td></tr>
							<tr><td class="l">保障金额</td><td class="r"><span class="money">10000元</span></td></tr>
							<tr><td class="l">投保年龄</td><td class="r">25-50周岁</td></tr>
							<tr><td class="l">保障日期</td><td class="r"><span class="money">30天</span></td></tr>
						</table>
						<@insuranceProvider/>
					</div>
				</div>
			</div>
			
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<@activityRule/>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}"/>
</div>
<@htmlFoot/>