<#include "../../../common/core.ftl" />
<@htmlHead title="资格领取" description="">
	<@jsFile file=["common/zengxian.js", "wap/education/vipkid.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/education/vipkid/vipkid.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="logo">
		<img src="${ossUrl}/img/wap/education/vipkid/fenxiang/logo@3x.png" alt="">
	</div>
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${ossUrl}/img/wap/education/vipkid/fenxiang/banner@3x.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<div class="form-title">
					<img src="${ossUrl}/img/wap/education/vipkid/fenxiang/title@3x.png" alt="">
				</div>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitInformationButton">
						<img src="${ossUrl}/img/wap/education/vipkid/button@3x.png" alt="">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="desc">
		<img src="${ossUrl}/img/wap/education/vipkid/fenxiang/desc@3x.png" alt="">
		<p>1、活动日期：2018.6.10--2018.7.30，单个手机号仅限领取一次，多次领取无效</p>
		<p>2、用户领取资格后30天内支付即可享受惠学啦平台送出的助学现金礼包</p>
		<p>3、用户须同时在该平台领取VIPKID试听课并领取助学金资格，才可享受该助学金活动</p>
		<p>4、用户购买VIPKID课程金额5000元--7000元，可获得500元现金；用户购买VIPKID课程金额≥7000元，可获得1000元现金</p>
		<p>5、用户购买VIPKID课程并过完退款期后，凭借付款截图及个人手机号，联系惠学啦公众号客服领取助学金</p>
		<p>6、信息提交后，客服人员将对您的活动资格进行审核，审核结果将在2-3个工作日内电话通知您！</p>
		<p>7、活动详细说明可咨询惠学啦平台客服，或者咨询平台官方客服010-64010062</p>
		<p>8、本活动与VIPKID无关，活动解释权归惠学啦平台所有</p>
	</div>
		<@randomSmsToken/>
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
</div>
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money"></span></p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>