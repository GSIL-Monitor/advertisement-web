<#include "../../../common/core.ftl" />
<@htmlHead title="世纪佳缘情感咨询服务" description="">
	<@jsFile file=["common/zengxian.js", "wap/loan/sjjy.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/sjjy/mfl.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<@toutiao />
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/sjjy/mfl/banner@3x.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<div class="form-item">
					<div class="left input-area">
						<div class="left premium-input" id="ageSelectControl">
							<div class="field-input">
								<span class="field-embeded-name">月收入</span>
								<i class="sj"></i>
								<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="请选择月收入" disabled></div>
							</div>
						</div>
					</div>
					<input type="hidden" name="question1" value="月收入">
					<input type="radio" name="answer1" id="answer1">
				</div>
				<div id="agePopTipList" class="poptip">
					<div class="poptip-select-head">请选择月收入<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
					<ul>
						<li><label for="option11">5000元以下</label><input type="radio" name="answer1" id="option11" value="5000元以下"></li>
						<li><label for="option12">5000~10000元</label><input type="radio" name="answer1" id="option12" value="5000~10000元"></li>
						<li><label for="option13">10000元~15000元</label><input type="radio" name="answer1" id="option13" value="10000元~15000元"></li>
						<li><label for="option14">15000元~20000元</label><input type="radio" name="answer1" id="option14" value="15000元~20000元"></li>
						<li><label for="option15">20000元以上</label><input type="radio" name="answer1" id="option15" value="20000元以上"></li>
					</ul>
				</div>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitInformationButton"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="product-description">*限单身没有注册过世纪佳缘的用户</div>
	<div class="desc"><img src="${cdnUrl}/img/wap/loan/sjjy/mfl/detail@3x.png" alt=""></div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<#-- <@footer activityPath="${activityPath}" /> -->
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">vipkid</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>