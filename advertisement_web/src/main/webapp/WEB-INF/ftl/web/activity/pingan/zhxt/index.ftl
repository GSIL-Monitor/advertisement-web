<#include "../../../common/core.ftl" />
<@htmlHead title="免费领万元账户安全保障送10元现金红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/zhxlb-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	已成功领取<span class="money">平安账户险</span>，平安客服后续将致电以确认免费保险生效事宜。
</#macro>
<@resultPopWindow title="" buttonText="立即领取10元现金红包" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@commonHeader/>
<@commonBanner path="pingan/zhxt" />
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
					<div class="submit-button" id="submitButton">立即领取<span>10元</span>红包</div>
				</div>		
				<img class="coupon" src="${cdnUrl}/img/web/activity/pingan/zhxt/zhxlb_coupon.png" alt="">
				<p class="coupon-title">10元现金红包+万元账户保障</p>
			</div>
		</div>
		<div class="line"></div>
		<div class="cont-box clearfix">
			<div class="box box-left left">
				<div class="detail">
					<div class="title">账户安全保障详情</div>
					<div class="tr">
						<div class="name">产品名称</div>
						<div class="desc">网银e护（中国平安）</div>
					</div>
					<div class="tr">
						<div class="name">产品期限</div>
						<div class="desc desc2">30天</div>
					</div>
					<div class="tr">
						<div class="name">保险责任描述</div>
						<div class="desc special">
							<p>个人银行/网络账户损失，保障10000元</p>
						</div>
					</div>
				</div>
				<@insuranceProvider/>
			</div>
			<div class="box box-right right">
				<div class="rule">
					<div class="title">活动规则</div>
					<p>1、有效参与用户身体健康，须要在25-50周岁；</p>
					<p>2、每位用户限领1份出行保障，多次领取无效；</p>
					<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知；</p>
				</div>
				<p class="insurance-provider">我已同意<span>《用户协议》</span>并领取免费保险</p>
			</div>
		</div>
		<div class="line foot-line"></div>
	<#-- 尾部 -->
	<@footer activityPath="${activityPath}"/>
</div>
<@emailPopWindow />
<@htmlFoot/>