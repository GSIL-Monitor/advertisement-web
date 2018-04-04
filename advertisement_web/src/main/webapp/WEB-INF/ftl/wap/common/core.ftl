<#include '/common/common.ftl'/>
<#include 'config.ftl'/>
<#macro cssFile file=[]>
    <#list file as x>
    	<#if x?? && (x?length > 0)>
    	<link rel="stylesheet" href="${cdnUrl}/css/${x}?${cdnFileVersion}"/>
    	</#if>
    </#list>
</#macro>
<#macro jsFile file=[]>
    <#list file as x>
    	<#if x?? && (x?length > 0)>
    	<script src="${cdnUrl}/js/${x}?${cdnFileVersion}"></script>
    	</#if>
    </#list>
</#macro>
<#macro htmlHead title="" keywords="" description="">
	<!doctype html>
	<html>
		<head>
			<meta charset="utf-8">
			<title>${title}</title>
			<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">
			<meta name="keywords" content="${keywords}"/>
			<meta name="description" content="${description}"/>
			<@cssFile file=["wap/common.css"] />
			<@jsFile file=["plugins/jquery-1.11.3.min.js", "base/core.js"] />
      		<#nested>
		</head>
		<body>
</#macro>

<#macro footer activityPath="" chineseName="中国平安保险（集团）股份有限公司" englishName="PING AN INSURANCE (GROUP) COMPANY OF CHINA，LTD.">
	<div class="footer">
		<input type="hidden" id="activityPath" value="${activityPath}"/>
		<span>
			<p>版权所有 © ${chineseName}</p>
			<p>未经许可不得复制、转载或摘编，违者必究!</p>
			<#if isDachuanbao??&&isDachuanbao=="true">
			<p>本DMP服务由远山保险提供</p>
			<p>京ICP备16050725号-1</p>
			<#else>
			<p>本DMP服务由北京远山保科技有限公司提供</p>
			<p>京ICP备16055004号-1</p>
			</#if>
			<#-- <p>Copyright © ${englishName}. All Rights Reserved</p> -->
			<#-- <p>本DMP服务由远山保科技有限公司提供，远山保科技有限公司为合法授权服务商</p> -->
		</span>
	</div>
</#macro>
<#macro footerYangGuang activityPath="">
	<@footer activityPath="${activityPath}" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</#macro>
<#macro footerMetlife activityPath="">
	<@footer activityPath="${activityPath}" chineseName="中美联泰大都会人寿保险有限公司" englishName="PEANUTS@United Feature Syndicate,Inc. "/>
</#macro>
<#macro footerZhongYing activityPath="">
	<@footer activityPath="${activityPath}" chineseName="中英人寿保险有限公司" englishName="Aviva-Cofco Inc."/>
</#macro>
<#macro footerBaiNian activityPath="">
	<@footer activityPath="${activityPath}" chineseName="百年人寿保险股份有限公司" englishName="Aeon Life Insurance Company,Ltd. "/>
</#macro>
<#macro footerHuaXia activityPath="">
	<@footer activityPath="${activityPath}" chineseName="华夏人寿保险股份有限公司" englishName=""/>
</#macro>
<#macro footerTaiKang activityPath="">
	<@footer activityPath="${activityPath}" chineseName="泰康人寿保险股份有限公司" englishName=""/>
</#macro>
<#macro randomSmsToken>
	<#list smsTokenList as tokenlist>
	<script type="text/javascript">
		(function() {
			var nengbunengbieshuale = 0;
			<#list tokenlist.list as smsToken>
				var ${smsToken.key} = ${smsToken.value?c};
			</#list>
			<#list tokenlist.list as smsToken>
				<#if smsToken.enable>
				nengbunengbieshuale = nengbunengbieshuale ${smsToken.operation} ${smsToken.key};
				</#if>
			</#list>
			<#if tokenlist.enable>
			setSmsTokenPrefix(nengbunengbieshuale);
			<#else>
			setSmsTokenPrefix(nengbunengbleshuale);
			</#if>
		})();
	</script>
	</#list>
</#macro>

<#macro commonTip>
	<input type="hidden" id="tipHtml" value='<@tipHtml/>'/>
	<div id="tipArea" class="tip-area"></div>
	<div class="tip-overlay" onclick="TipWindow.hide()"></div>
	<div class="overlay"></div>
</#macro>

<#macro tipHtml>
<div name="tipWindow" class="tip-window center">
	<div class="tip-content">
		<#-- <span class="tip-close-icon"><img src="${cdnUrl}/img/wap/common/tip-close.png" onclick="TipWindow.hide();" /></span> -->
		<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
		<div class="tip-title"></div>
		<div class="tip-text hide" name="tipText"></div>
		<div class="tip-button">
			<a href="javascript:;" name="tipLeftButton" class="tip-left-button" onclick="TipWindow.hide();">
				<div class="tip-left-button-text left" name="tipLeftButtonText">返回</div>
			</a>
			<a href="javascript:;" name="tipRightButton" class="tip-right-button" onclick="location.reload();">
				<div name="tipRightButtonText" class="tip-right-button-text right">确定</div>
			</a>
			<a href="javascript:;" name="tipSingleButton" class="tip-single-button center" onclick="TipWindow.hide();">
				<div name="tipSingleButton" class="tip-single-button-text">好的</div>
			</a>
		</div>
	</div>
</div>
</#macro>

<#macro commonBanner path>
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/activity/${path}/banner.png?${cdnFileVersion}">
		</#if>
	</div>
</#macro>


<#macro commonAppliedCount>
	<div class="applied-count-area">
		<div class="applied-count-content">
			已有
			<span class="applied-count" id="appliedCount"><b class="animated tada">${appliedCount}</b></span>
			位用户成功领取保障
		</div>
	</div>
</#macro>

<#macro surveySubmitButton>
	<div class="submit-button-area">
		<div class="submit-button-shadow">
			<div class="submit-button" id="surveyButton">
				<#if surveyPromotionTextConfig?? && (surveyPromotionTextConfig?length>0)>
				立即领取${surveyPromotionTextConfig}
				<#else>
				提交
				</#if>
			</div>
		</div>
	</div>
</#macro>

<#macro pinganHongYunCalculateForm activityPath="" promotion="false">
	<form action="/m/activity${activityPath}/survey/calculateResult.html" method="post" id="calculateForm">
		<#-- 字段 -->
		<input type="hidden" id="gorderId" name="gorderId" value="${gorderId?c}"/>
		<input type="hidden" id="type" value="chuxing"/>
		<div class="form-item">
			<div class="left field-name">
				保险金额：
			</div>
			<div class="right input-area">
				<div class="field-input clearfix">
					<i class="input-triangle"></i>
					<div id="insuredSumValue" name="insuredSumValue" class="left field-input-control field-select field-center-align">请选择保额</div>
					<input type="hidden" id="insuredSum" name="insuredSum" />
				</div>
				<div class="error-tip">
				
				</div>
			</div>
		</div>
		<div id="insuredSumPopTipList" class="poptip">
			<ul>
			<div class="poptip-select-head" >请选择保额<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
				<#list calculatorList as calculator>
					<li value="${calculator.insuredSum?c}" reference="${calculator.dailyAllowance}">${calculator.insuredSumValue}</li>
				</#list>
			</ul>
		</div>

		<div class="form-item">
			<div class="left field-name">
				住院津贴：
			</div>
			<div class="right input-area">
				<div id="dailyAllowanceValue" name="dailyAllowanceValue" class="field-allowance" >最高150元/天</div>
				<input type="hidden" id="dailyAllowance" name="dailyAllowance" value="150" readonly="readonly" />
			</div>
		</div>

		<div class="form-item">
			<div class="left field-name">
				缴费方式：
			</div>
			<div class="right input-area">
				<div name="payWayValue" class="left radio-btn radio-btn-select" value="month">按月缴费</div>
				<div name="payWayValue" class="right radio-btn" value="year">按年缴费</div>
				<input type="hidden" id="payWay" name="payWay" value="month"/>
			</div>
		</div>
		<@emailPopWindow title="我们会将保费试算结果发送到您的邮箱"/>
		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button" id="calculateButton">
				测算保费<#if promotion=="true">领取${surveyPromotionTextConfig}</#if>
			</div>
		</div>
	</form>
</#macro>

<#macro yangguangZhenXinCalculateForm activityPath="">
	<form action="/m/activity${activityPath}/survey/calculateResult.html" method="post" id="calculateForm">
		<#-- 字段 -->
		<input type="hidden" id="gorderId" name="gorderId" value="${gorderId?c}"/>
		<input type="hidden" id="existEmail" value="<#if insurant.email?? && (insurant.email?length>0)>true</#if>"/>
		<div class="form-item">
			<div class="left field-name">
				保险金额：
			</div>
			<div class="right input-area">
				<div class="field-input">
					<i class="input-triangle"></i>
					<div id="insuredSumValue" name="insuredSumValue" class="left field-input-control field-select field-center-align">请选择保额</div>
					<input type="hidden" id="insuredSum" name="insuredSum" />
				</div>
				<div class="error-tip">
				
				</div>
			</div>
		</div>
		<div id="insuredSumPopTipList" class="poptip">
			<ul>
				<div class="poptip-select-head" >请选择保额<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
				<li value="100000">10万</li>
				<li value="150000">15万</li>
				<li value="200000">20万</li>
				<li value="250000">25万</li>
				<li value="300000">30万</li>
				<li value="350000">35万</li>
				<li value="400000">40万</li>
				<li value="450000">45万</li>
				<li value="500000">50万</li>
				<li value="600000">60万</li>
			</ul>
		</div>

		<div class="form-item hide">
			<div class="left field-name">
				住院津贴：
			</div>
			<div class="right input-area">
				<div id="dailyAllowanceValue" name="dailyAllowanceValue" class="field-allowance" >150元/天</div>
				<input type="hidden" id="dailyAllowance" name="dailyAllowance" value="150" readonly="readonly" />
			</div>
		</div>

		<div class="form-item">
			<div class="left field-name">
				交费方式：
			</div>
			<div class="right input-area">
				<div name="payWayValue" class="left radio-btn radio-btn-select" value="month">按月交费</div>
				<div name="payWayValue" class="right radio-btn" value="year">按年交费</div>
				<input type="hidden" id="payWay" name="payWay" value="month"/>
			</div>
		</div>

		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button" id="calculateButton">
				测算保费
			</div>
			<@emailPopWindow title="我们会将保费试算结果发送到您的邮箱"/>
		</div>
	</form>
</#macro>