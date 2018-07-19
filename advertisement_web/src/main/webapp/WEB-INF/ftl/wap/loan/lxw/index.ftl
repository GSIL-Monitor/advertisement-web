<#include "../../common/core.ftl" />
<#assign title="免费领取英语试听课"/>
<@htmlHead title="${title}" description="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<@jsFile file=["plugins/jquery.cookie.js", "wap/loan/lxw.js", "common/zengxian.js"] />
<@cssFile file=["wap/base.css", "wap/loan/lxw/lxw.css"] />
<@toutiao/>
</@htmlHead>
<@calculatePage/>
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/lxw/banner.png?${cdnFileVersion}">
		</#if>
	</div>
	<div class="wrapper">
		<div class="pen-area">
			<i class="form-pen">
			</i>
		</div>
		<div class="counter-bg">
					<div class="applied-count-area left">
						<div class="applied-count-content">
							已有
							<span class="applied-count" id="appliedCount"><b class="animated tada">${appliedCount}</b></span>
							位用户成功领取
						</div>
					</div>
					<div class="count-backwards right">
						<span class="l">剩余</span>
						<ul class="cards">
							<#list reverseCount as count>
								<li>${count}</li>
							</#list>
						</ul>
						<span class="r">份</span>
					</div>
				</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#-- 字段 -->
			<input type="hidden" id="${smsId}" value="${smsToken}" />
			<#-- 字段 -->
			<@embededTitleZengXianForm/>
			<div class="form-item">
				<div class="left input-area">
					<div class="left premium-input" id="ageSelectControl">
						<div class="field-input">
							<span class="field-embeded-name">孩子年龄</span>
							<i class="sj"></i>
							<div class="field-input-control field-select field-center-align"><input id="ageInput" type="text" placeholder="请选择您孩子的年龄" disabled></div>
						</div>
					</div>
				</div>
				<input type="hidden" name="question1" value="您孩子的年龄">
				<input type="radio" name="answer1" id="answer1">
			</div>
			<div id="agePopTipList" class="poptip">
				<div class="poptip-select-head">请选择孩子的年龄<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
				<ul>
					<li><label for="option11">4岁以下</label><input type="radio" name="answer1" id="option11" value="4岁以下"></li>
					<li><label for="option12">4-6岁</label><input type="radio" name="answer1" id="option12" value="4-6岁"></li>
					<li><label for="option13">7-9岁</label><input type="radio" name="answer1" id="option13" value="7-9岁"></li>
					<li><label for="option14">10-12岁</label><input type="radio" name="answer1" id="option14" value="10-12岁"></li>
					<li><label for="option15">13-18岁</label><input type="radio" name="answer1" id="option15" value="13-18岁"></li>
					<li><label for="option16">18岁以上</label><input type="radio" name="answer1" id="option16" value="18岁以上"></li>
				</ul>
			</div>
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton"></div>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
		</div>
		<@randomSmsToken/>
		<div class="description">
			<img src="${cdnUrl}/img/wap/loan/lxw/description.png" alt="">
		</div>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}" />
		<div class="bottom-btn" id="bottomBtn"></div>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />
<@htmlFoot/>