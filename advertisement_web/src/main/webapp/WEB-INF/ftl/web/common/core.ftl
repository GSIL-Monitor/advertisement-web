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
			<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
			<meta name="baidu-site-verification" content="3QzDHZVkvx" />
			<meta name="keywords" content="${keywords}"/>
			<meta name="description" content="${description}"/>
			<@cssFile file=["web/base.css", "web/common.css"] />
			<@jsFile file=["plugins/jquery.min.js", "base/core.js"] />
      		<#nested>
      		<link rel="shortcut icon" type="image/ico" href="/favicon.ico">
      		<!--[if lt IE 9]>
		      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		    <![endif]-->
		</head>
		<body>
			<script type="text/javascript">
				$(function(){
					coreConfig = {
						cdnUrl : '${cdnUrl}'
					}
				});
			</script>
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

<#macro footer activityPath="" chineseName="中国平安保险（集团）股份有限公司" englishName="PING AN INSURANCE (GROUP) COMPANY OF CHINA，LTD.">
	<div class="footer">
		<input type="hidden" id="activityPath" value="${activityPath}"/>
		<span>
			<#if chineseName?? && (chineseName?length>0)>
			<#-- <p>版权所有 © ${chineseName}，未经许可不得复制、转载或摘编，违者必究!</p> -->
			</#if>
			<#-- <p>Copyright © ${englishName} All Rights Reserved</p> -->
			<p>本DMP服务未经许可不得复制、转载或摘编，违者必究!</p>
			<#if isDachuanbao??&&isDachuanbao=="true">
			<p>京ICP备16050725号-1</p>
			<#else>
			<p>京ICP备16055004号-1</p>
			</#if>
		</span>
	</div>
</#macro>

<#macro footerZhongYing activityPath="">
	<@footer activityPath="${activityPath}" chineseName="中英人寿保险有限公司" englishName="Aviva-Cofco Inc."/>
</#macro>

<#macro commonTip>
	<input type="hidden" id="tipHtml" value='<@tipHtml/>'/>
	<div id="tipArea" class="tip-area"></div>
	<div class="overlay"></div>
</#macro>

<#macro tipHtml>
<div name="tipWindow" class="tip-window center">
	<div class="tip-content">
		<span class="tip-close-icon"><img src="${cdnUrl}/img/wap/common/tip-close.png" onclick="TipWindow.hide();" /></span>
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
	<div class="banner-wrap">
		<div class="banner">
			<#if customBannerConfig?? && (customBannerConfig?length > 0)>
			<img src="${customBannerConfig}">
			<#else>
			<img src="${cdnUrl}/img/web/activity/${path}/banner.png?${cdnFileVersion}">
			</#if>
		</div>
	</div>
</#macro>

<#macro pinganHongYunCalculateForm activityPath="">
	<form action="/activity${activityPath}/survey/calculateResult.html" method="post" id="calculateForm">
		<#-- 字段 -->
		<input type="hidden" id="gorderId" name="gorderId" value="${gorderId?c}"/>
		<div class="form-item">
			<div class="field-name">
				保险金额：
			</div>
			<div class="input-area">
				<div class="field-input calculate-input">
					<input id="insuredSumSelectValue" name="insuredSumSelectValue" class="left field-input-control field-center-align calculate-input" value="请选择保额"/>
					<i class="input-triangle"></i>
					<input type="hidden" id="insuredSum" name="insuredSum" />
				</div>
				<div class="error-tip">
				
				</div>
			</div>
		</div>
		<ul id="insuredSumPopTipList" class="select-list">
			<#list calculatorList as calculator>
            <li value="${calculator.insuredSum?c}" reference="${calculator.dailyAllowance}" style="padding-left:150px">${calculator.insuredSumValue}</li>
            </#list>
		</ul>
		<div class="form-item">
			<div class="field-name">
				住院津贴：
			</div>
			<div class="input-area">
				<div id="dailyAllowanceValue" name="dailyAllowanceValue" class="field-input-control field-allowance" >150元/天</div>
				<input type="hidden" id="dailyAllowance" name="dailyAllowance" value="150" readonly="readonly" />
			</div>
		</div>

		<div class="form-item">
			<div class="field-name">
				交费方式：
			</div>
			<div class="input-area">
				<div name="payWayValue" class="left radio-btn radio-btn-select" value="year">按年交费</div>
				<div name="payWayValue" class="right radio-btn" value="month">按月交费</div>
				<input type="hidden" id="payWay" name="payWay" value="year"/>
			</div>
		</div>
		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button" id="calculateButton">
				测算保费
			</div>
		</div>
	</form>
</#macro>

<#macro yangguangZhenXinCalculateForm activityPath="">
	<form action="/activity${activityPath}/survey/calculateResult.html" method="post" id="calculateForm">
		<#-- 字段 -->
		<input type="hidden" id="gorderId" name="gorderId" value="${gorderId?c}"/>
		<div class="form-item">
			<div class="field-name">
				保险金额：
			</div>
			<div class="input-area">
				<div class="field-input calculate-input">
					<input id="insuredSumSelectValue" name="insuredSumSelectValue" class="left field-input-control field-center-align calculate-input" value="请选择保额"/>
					<i class="input-triangle"></i>
					<input type="hidden" id="insuredSum" name="insuredSum" />
				</div>
				<div class="error-tip">
				
				</div>
			</div>
		</div>
		<ul id="insuredSumPopTipList" class="select-list">
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
		<div class="form-item">
			<div class="field-name">
				交费方式：
			</div>
			<div class="input-area">
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
		</div>
	</form>
</#macro>

<#macro commonHeader insurance="" description="" headerClass="">
	<div class="header ${headerClass}">
		<div class="header-wrap">
		<#if channel?? && channel.imageUrl?? && (channel.imageUrl?length>0)>
			<img src="${channel.imageUrl}">
		<#elseif insurance?? && (insurance?length>0)>
			<img src="${ossUrl}/img/common/logo/${insurance}-logo.png?${cdnFileVersion}">
		</#if>

		<#if hasHeaderCooperateConfig?? && hasHeaderCooperateConfig == "true" && (description?? && description?length > 0)>
			<div class="header-description">
				远山携手${description}
			</div>
		</#if>
		</div>
	</div>
</#macro>

<#macro beianNumber>
	<#if isDachuanbao??&&isDachuanbao=="true">
  	京ICP备16050725号-1
  	<#elseif isYuanshanbx?? && isYuanshanbx=="true">
  	京ICP备16055004号-2
  	<#elseif isHuhabao?? && isHuhabao=="true">
  	京ICP备17008756号-1
  	<#elseif isRjbbx?? && isRjbbx=="true">
 	京ICP备14033532号-1
  	<#else>
  	京ICP备16055004号-1
  	</#if>
</#macro>

<#macro companyName>
<#if isDachuanbao??&&isDachuanbao=="true">
大川保
<#elseif isYuanshanbx?? && isYuanshanbx=="true">
远山保
<#elseif isHuhabao?? && isHuhabao=="true">
呼哈保
<#else>
远山保
</#if>
</#macro>

<#macro copyRight>
	<#if isHuhabao?? && isHuhabao=="true">
	版权所有&copy;2017北京北方青蓝电子科技有限公司
	<#else>
	版权所有&copy;2017北京远山保科技有限公司
	</#if>
</#macro>