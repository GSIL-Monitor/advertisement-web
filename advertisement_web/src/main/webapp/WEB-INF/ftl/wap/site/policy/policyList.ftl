<#include '../../common/core.ftl'/>
<@htmlHead title="" > 
	<@cssFile file=["wap/site/policy.css", "wap/site/mine.css"] />
	<@jsFile file=[""] />
</@htmlHead>
<@calculatePage/>
<div class="wrapper">
	<#if claim?? && claim=="true">
	<section class="top-title">请选择您要申请理赔的表单</section>
	<#else>
	<section class="top-tabbar" id="topTabbar">
		<a href="/m/policy/list.html"<#if !status?? || status==1> class="active"</#if>><span>有效</span></a>
		<a href="/m/policy/list.html?status=2"<#if status?? && status==2> class="active"</#if>><span>已终止</span></a>
	</section>
	</#if>
	<section>
		<ul class="policy-list">
			<#list orderList as order>
			<li class="clearfix">
				<a href="/m/policy/detail.html?orderId=${order.orderId?c}">
					<div class="l-side"></div>
					<div class="main">
						<img src="${cdnUrl}/img/common/logo/${order.merchant.englishName}-logo.png?${cdnFileVersion}">
						<h1>${order.insurance.fullName}</h1>
						<p>有效期至${order.firstInsurant.endTimeValue}</p>
					</div>
					<div class="r-side">
						<#if !status?? || status==1>
						<img src="${cdnUrl}/img/wap/site/icon_lipei.png" alt="">
						</#if>
					</div>
				</a>
			</li>
			</#list>
		</ul>
	</section>
</div>
<@htmlFoot />
