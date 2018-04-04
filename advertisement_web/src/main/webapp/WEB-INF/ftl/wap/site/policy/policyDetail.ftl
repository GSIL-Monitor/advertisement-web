<#include '../../common/core.ftl'/>
<@htmlHead title="" > 
	<@cssFile file=["wap/site/policy.css", "wap/site/mine.css"] />
	<@jsFile file=[""] />
</@htmlHead>
<@calculatePage/>
<div class="wrapper">
	<section>
		<ul class="policy-list">
			<li class="clearfix">
				<a href="">
					<div class="l-side"></div>
					<div class="main">
						<img src="${cdnUrl}/img/common/logo/${order.merchant.englishName}-logo.png?${cdnFileVersion}">
						<h1>${order.insurance.fullName}</h1>
						<#if order.firstInsurant.policyNumber??>
						<p>保单号：${order.firstInsurant.policyNumber}</p>
						</#if>
					</div>
					<#if valid?? && valid=="true">
					<div class="r-side">
						<img src="${cdnUrl}/img/wap/site/icon_lipei.png" alt="">
					</div>
					</#if>
				</a>
			</li>
		</ul>
	</section>
	<div class="split"></div>
	<section class="policy-detail">
		<ul>
			<li class="clearfix"><span class="left">投保人</span><span class="right">${order.firstInsurant.name}</span></li>
			<li class="clearfix"><span class="left">身份信息</span><span class="right">${order.firstInsurant.identityCard}</span></li>
			<li class="clearfix"><span class="left">保险与期限</span><span class="right">即日起至${order.firstInsurant.endTimeValue}</span></li>
		</ul>
		<ul>
			<li class="clearfix"><b class="left">保险范围</b><span class="right">保障金额</span></li>
			<#if order.insurance.detailList??>
				<#list order.insurance.detailList as detail>
				<li class="clearfix"><span class="left">${detail.cover}</span><span class="right">${detail.amount}</span></li>
				</#list>
			</#if>
		</ul>
	</section>
	<section class="description">
		该保险产品由${order.merchant.fullName}承保，您可点击申请理赔按钮或拨打${order.merchant.fullName}客服电话<span class="tel">${order.merchant.telephone}</span>提出理赔申请，也可以前往保险公司各地营业网点办理。
	</section>
	<section class="bottom-tab clearfix">
		<a href="tel:${order.merchant.telephone}" class="left zixun">
			<span class="icon-zixun"></span>
		</a>
		<div class="lipei right">
			<a href="tel:${order.merchant.telephone}" class="btn-lipei">申请理赔</a>
		</div>
	</section>
</div>
<@htmlFoot />
