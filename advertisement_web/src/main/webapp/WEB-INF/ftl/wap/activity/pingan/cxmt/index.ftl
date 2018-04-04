<#include "../../../common/core.ftl" />
<@htmlHead title="免费领出行保障送20元美团外卖红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/activity/pingan/cxmt.css", "wap/base.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<script type="text/javascript">
	$(function(){
		$('#mobile').blur(function(){
			if (checkMobile('#mobile')) {
				$('.form-item').removeClass('hide');
			} else {
				clearError('#mobile');
			}
		});
		$('#mobile').bind('input', function(){
			if (checkMobile('#mobile')) {
				$('.form-item').removeClass('hide');
			} else {
				clearError('#mobile');
			}
		});
	});
</script>
<div class="container">
	<@commonHeader/>
	<@commonBanner path="pingan/cxmt"/>

	<div class="wrapper">
		<#-- 已有多少人领取 -->
		<@commonAppliedCount/>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<div class="form-title">
				参与调查，免费领<span class="money">100万元</span>出行险
			</div>
			<@surveyForm/>
			<div class="form-title" style="margin-top:2rem;">
				填写信息，保单生效
			</div>
			</#if>
			<#-- 字段 -->
			<@commonZengXianForm hideIdentityCard="true"/>

			<#-- 条款 -->
			<div class="protocal">
				<span>
					<img src="${cdnUrl}/img/wap/activity/pingan/cxmt/check.png"/>
					同意<a href="javascript:;" onclick="showProtocol();">《安全条款》</a><#-- 及<a href="javascript:;" onclick="showActivityRule();">《活动说明》</a> -->并领取免费保险
				</span>
				<@pinganProtocolContent/>
				<#-- <div id="activityRulePopWindow" class="hide">
					<@activityRule/>
				</div> -->
			</div>

			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					免费领取礼包
				</div>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="product-description">
				<div class="product-description-title">
					交通综合保障详情
				</div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="28%">
							<p class="product-description-header">产品名称</p>
						</td>
						<td width="22%">
							<p class="product-description-header">产品期限</p>
						</td>
						<td width="50%">
							<p class="product-description-header">保险责任描述</p>
						</td>
					</tr>
					<tr>
						<td>
							<p>出行险</p>
							<p>（神州畅行）</p>
						</td>
						<td>
							<p><span class="money">180天</span></p>
						</td>
						<td class="product-detail">
							<p>1、乘坐飞机、高铁等意外伤害最高赔付<span class="money">100万</span>；</p>
							<p>2、乘坐出租车意外伤害赔付<span class="money">10万</span>；</p>
							<p>3、交通意外医疗<span class="money">2万</span>；</p>
							<p>4、行李物品和旅行证件损失保险最高<span class="money">500元</span>。</p>
						</td>
					</tr>
				</table>
			</div>
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
<@resultPopWindow title="" buttonText="参与保费测算再送${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>