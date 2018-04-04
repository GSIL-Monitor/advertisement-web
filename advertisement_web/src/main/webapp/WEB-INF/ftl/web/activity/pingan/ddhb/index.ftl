<#include "../../../common/core.ftl" />
<@htmlHead title="免费领出行保障送百元滴滴红包" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/ddhb-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
	<p class="coupon-tip">滴滴红包已发送到您的手机，请注意查收。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<@commonHeader/>
<@commonBanner path="pingan/ddhb" />
<div class="container">
	<div class="wrapper">
		<div class="counter">
			已有
				<span><b class="animated tada">${appliedCount}</b></span>
				位用户成功领取红包
		</div>
		<div class="form-wrap">
			<div class="ddhb-form">
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取<span>100元</span>红包</div>
				</div>		
				<img class="coupon" src="${cdnUrl}/img/web/activity/pingan/ddhb/coupon.png" alt="">
				<p class="coupon-title">百元滴滴红包+百万出行保障</p>
			</div>
		</div>
		<div class="line"></div>
		<div class="cont-box clearfix">
			<div class="box box-left left">
				<div class="detail">
					<div class="title">平安出行保障详情</div>
					<div class="tr">
						<div class="name">产品名称</div>
						<div class="desc">出行险（神州畅行）</div>
					</div>
					<div class="tr">
						<div class="name">产品期限</div>
						<div class="desc desc2">180天</div>
					</div>
					<div class="tr">
						<div class="name">保险责任描述</div>
						<div class="desc special">
							<p>1、乘坐飞机、高铁等意外伤害最高赔付<span>100万</span>；</p>
							<p>2、乘坐出租车意外伤害赔付<span>10万</span>；</p>
							<p>3、交通意外医疗<span>2万</span>；</p>     
							<p>4、行李物品和旅行证件损失保险最高<span>350元</span>。</p>
						</div>
					</div>
				</div>
				<p class="insurance-provider">以上保险由<span>远山保险和平安寿险</span>提供</p>
			</div>
			<div class="box box-right right">
				<div class="rule">
					<div class="title">活动规则</div>
					<p>1、有效参与用户身体健康，须要在25-50周岁；</p>
					<p>2、每位用户限领1份出行保障，多次领取无效；</p>
					<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知；</p>
					<p>4、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）</p>
					<p>5、滴滴红包，参与活动即可获得5~100元滴滴红包，乘坐快车、专车等均可抵现使用。</p>
				</div>
				<p class="insurance-provider">我已同意<span>《安全条款》</span>并领取免费保险</p>
			</div>
		</div>
		<div class="line foot-line"></div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}"/>
</div>
<@htmlFoot/>