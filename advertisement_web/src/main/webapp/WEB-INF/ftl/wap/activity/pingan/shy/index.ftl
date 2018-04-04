<#include "../../../common/core.ftl" />
<@htmlHead title="全民领百万出行保障，出行平安，全家安心" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan3in1-wap.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		<#if type??>
			changeType('${type}');
		<#else>
			changeType('chuxing');
		</#if>
	});
	function changeType(type) {
		$('#tabOption li').each(function() {
			$('#' + $(this).attr('value') + 'Option').addClass('option-unselect');
			$('#' + $(this).attr('value') + 'Banner').addClass('hide');
			$('#' + $(this).attr('value') + 'Triangle').addClass('hide');
			$('#' + $(this).attr('value') + 'Title').addClass('hide');
			$('#' + $(this).attr('value') + 'Description').addClass('hide');
			$('#' + $(this).attr('value') + 'Count').addClass('hide');
		});
		$('#' + type + 'Option').removeClass('option-unselect');
		$('#' + type + 'Banner').removeClass('hide');
		$('#' + type + 'Title').removeClass('hide');
		$('#' + type + 'Triangle').removeClass('hide');
		$('#' + type + 'Description').removeClass('hide');
		$('#' + type + 'Count').removeClass('hide');
		$('#' + type + 'Image').attr('src', $.format($('#imageValue').val(), type));
		$('#type').val(type);
	}
</script>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
<#-- 	<div class="header">

	</div> -->
	<#-- Banner信息 -->
	<div class="banner">
		<input type="hidden" id="imageValue" value="${ossUrl}/img/wap/activity/pingan3in1/banner/banner-{0}.jpg">
		<div id="hangbanBanner" class="hide">
			<img src="" id="hangbanImage">
		</div>
		<div id="zijiaBanner" class="hide">
			<img src="" id="zijiaImage">
		</div>
		<div id="chuxingBanner" class="hide">
			<img src="" id="chuxingImage">
		</div>
	</div>
	<input type="hidden" id="smsTokenId" value="${smsId}" />
	<#-- 选项卡（选择不同险种） -->
	<#if single?? && single=="true">
	<div class="split"></div>
	<#else>
	<div class="tab">
		<ul id="tabOption">
			<li class="tab-option" value="hangban">
				<div class="option-content option-unselect" id="hangbanOption">航班延误险</div>
				<div class="triangle-area hide" id="hangbanTriangle">
					<span class="select-triangle"></span>
				</div>
			</li>
			<li class="tab-option" value="zijia">
				<div class="option-content option-unselect" id="zijiaOption">自驾险</div>
				<div class="triangle-area hide" id="zijiaTriangle">
					<span class="select-triangle"></span>
				</div>
			</li>
			<li class="tab-option" value="chuxing">
				<div class="option-content option-unselect" id="chuxingOption">出行险</div>
				<div class="triangle-area hide" id="chuxingTriangle">
					<span class="select-triangle"></span>
				</div>
			</li>
		</ul>
	</div>
	</#if>
	<#-- 已有多少人领取 -->
	<div class="applied-count-area">
		<div class="applied-count-content">
			已有
			<span class="applied-count hide" id="hangbanCount"><b class="animated tada">${hangbanCount}</b></span>
			<span class="applied-count hide" id="zijiaCount"><b class="animated tada">${zijiaCount}</b></span>
			<span class="applied-count" id="chuxingCount"><b class="animated tada">${chuxingCount}</b></span>
			位用户成功领取保障
		</div>
	</div>
	<#-- 填写赠险表单信息区域 -->
	<div class="form-area">
		<#-- 字段 -->
		<input type="hidden" id="type" value="chuxing"/>
		<#-- 字段 -->
		<@commonZengXianForm/>

		<#-- 条款 -->
		<@pinganProtocol/>

		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button-shadow">
				<div class="submit-button" id="submitButton">
					免费领取
				</div>
			</div>
		</div>

		<@randomSmsToken/>
		<@warmTip/>
	</div>
	<input type="hidden" id="${smsId}" value="${smsToken}" />
	<#-- 保险详情描述 -->
	<div class="insurance-description">
		<div class="description-title">
			<span id="hangbanTitle" class="hide">延误即赔付，航行保无忧！</span>
			<span id="zijiaTitle" class="hide">驾驶全保障，放心开！</span>
			<span id="chuxingTitle" class="hide">百万出行保障，任性选！</span>
		</div>
		<div class="">
			<div class="product-description">
				<table border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th width="25%">产品名称</th>
							<th width="25%">产品期限</th>
							<th width="50%">保险责任描述</th>
						</tr>
					</thead>
					<tr id="hangbanDescription" class="hide">
						<td>乐航无忧<br/>(中国平安)</td>
						<td>60天</td>
						<td>
							<p>航班延误4小时，赔付<span class="money">200元</span>。</p>
						</td>
					</tr>
					<tr id="zijiaDescription" class="hide">
						<td>自驾险<br/>(平安驾驶)</td>
						<td>90天</td>
						<td>
							<p>在保险期间内，被保险人驾驭七座及以下非营运机动车辆发生道路交通事故导致身故/残疾，将赔付意外身故保险金最高<span class="money">5万元</span>。</p>
						</td>
					</tr>
					<tr id="chuxingDescription" class="hide">
						<#if channel?? && channel=="huaxia">
						<td>行者美猴王</td>
						<td>180天</td>
						<td>
							<p>1、飞机意外身故/残疾<span class="money">50万</span>；</p>
							<p>2、火车、地铁、轻轨、轮船意外身故/残疾<span class="money">10万</span>；</p>
							<p>3、营运汽车意外身故/残疾<span class="money">2万</span>；</p>
						</td>
						<#else>
						<td>出行险<br/>(畅行天下)</td>
						<td>180天</td>
						<td>
							<p>1、乘坐飞机、高铁等意外伤害最高赔付<span class="money">100万</span>；</p>
							<p>2、乘坐出租车意外伤害赔付<span class="money">10万</span>；</p>
							<p>3、交通意外医疗<span class="money">2万</span>；</p>
							<p>4、行李物品和旅行证件损失保险最高<span class="money">500元</span>。</p>
						</td>
						</#if>
					</tr>
				</table>
			</div>
		</div>
		<@activityRule/>
	</div>
	<#-- 尾部 -->
	<#if channel?? && channel=="huaxia">
	<input type="hidden" id="activityPath" value="/pingan/shy"/>
	<#else>
	<@footer activityPath="/pingan/shy"/>
	</#if>
</div>
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