<#include "../../../common/core.ftl" />
<#if surveyPositionConfig?? && surveyPositionConfig=="index">
	<#assign surveyTitle="参与调查，"/>
<#else>
	<#assign surveyTitle=""/>
</#if>
<#if type?? && type=="zijia">
	<#assign typeTitle="${surveyTitle}免费领5万平安自驾险"/>
<#elseif type?? && type=="hangyan">
	<#assign typeTitle="${surveyTitle}免费领200元航班延误险"/>
<#else>
	<#assign typeTitle="${surveyTitle}免费领100万平安出行险"/>
</#if>

<@htmlHead title="${typeTitle}" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/swzx/banner-${type}.png">
	</div>

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area" style="margin-top: 3rem;">
			<input type="hidden" id="type" value="${type}"/>
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<div class="form-tag-area">
				<span class="form-tag-content">${typeTitle}</span>
				<i class="form-tag-left"></i>
				<i class="form-tag-right"></i>
			</div>
			<@surveyForm />
			<div class="form-title">
				填写信息立即领取
			</div>
			<#else>
			<div class="form-title">
				<#if type?? && type=="zijia">
					填写信息免费领取5万元平安自驾险
				<#elseif type?? && type=="hangyan">
					填写信息免费领取200元航班延误险
				<#else>
					填写信息免费领取100万平安出行险
				</#if>
				
			</div>
			</#if>
			
			<#-- 字段 -->
			<@commonZengXianForm/>

			<input type="hidden" id="smsTokenId" value="${smsId}" />
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
				<#if type?? && type=="zijia">
				<span>自驾保障详情</span>
				<#elseif type?? && type=="hangyan">
				<span>航班延误保障详情</span>
				<#else>
				<span>出行保障详情</span>
				</#if>
			</div>
			<div class="">
				<div class="product-description">
					<table border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="25%">产品名称</th>
								<th width="20%">产品期限</th>
								<th width="55%">保险责任描述</th>
							</tr>
						</thead>
						<#if type?? && type=="zijia">
						<tr>
							<td>自驾险<br/>(平安驾驶)</td>
							<td>90天</td>
							<td>
								<p>在保险期间内，被保险人驾驭七座及以下非营运机动车辆发生道路交通事故导致身故/残疾，将赔付意外身故保险金最高<span class="money">5万元</span>。</p>
							</td>
						</tr>
						<#elseif type?? && type=="hangyan">
						<tr>
							<td>乐航无忧<br/>(中国平安)</td>
							<td>60天</td>
							<td>
								<p>航班延误4小时，赔付<span class="money">200元</span>。</p>
							</td>
						</tr>
						<#else>
						<tr>
							<td>出行险<br/>(畅行天下)</td>
							<td>180天</td>
							<td align="left">
								<p>1、乘坐飞机、高铁等意外伤害最高赔付<span class="money">100万</span>；</p>
								<p>2、乘坐出租车意外伤害赔付<span class="money">10万</span>；</p>
								<p>3、交通意外医疗<span class="money">2万</span>；</p>
								<p>4、行李物品和旅行证件损失保险最高<span class="money">500元</span>。</p>
							</td>
						</tr>
						</#if>
					</table>
				</div>
			</div>
			<@insuranceProvider/>
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
<@resultPopWindow title="" buttonText="参与保费测算领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>