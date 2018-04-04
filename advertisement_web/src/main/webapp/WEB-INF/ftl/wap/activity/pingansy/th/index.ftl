<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万出行保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/shanyin/pinganth.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<@commonBanner path="pingansy/th"/>

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<img src="${cdnUrl}/img/wap/activity/pingansy/th/title.png" class="form-title-img" />
			<#-- 字段 -->
			<@commonZengXianForm/>
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button-form" id="submitButton">
				</div>
			</div>

			<#-- 条款 -->
			<div class="protocal">
				<span>
					<img src="${cdnUrl}/img/wap/activity/pingan/xxjhb/check.png"/>
					我同意<a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>及<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>并领取<a href="javascript:;" id="insuranceDetailButton">免费保障</a>
				</span>
				<@commonProtocolContent/>
				<div id="activityRulePopWindow" class="hide">
					<@activityRule/>
				</div>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<#-- <div class="insurance-description">
			<div class="product-description">
				<div class="description-title">
					出行保障详情
				</div>
				<table border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th width="25%">产品名称</th>
							<th width="25%">产品期限</th>
							<th width="50%">保险责任描述</th>
						</tr>
					</thead>
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
				</table>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<div class="insurance-provider">
				以上保险由<span class="money">闪银&超级社保卡、远山保险和平安寿险</span>联合提供
			</div>
		</div> -->
		<div class="insurance-description">
			<img src="${cdnUrl}/img/wap/activity/pingansy/th/description.png" style="width:100%" />
		</div>
		<#-- <div class="activity-rule">
			<h3>投保须知</h3>
			<p>1、有效参与用户身体健康，须要在25-50周岁</p>
			<p>2、每位用户限领1份出行保障，多次领取无效</p>
			<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知</p>
			<p>4、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）</p>
			<p>5、如有疑问，可咨询客服qq:2013780348</p>
		</div> -->
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
	</div>
</div>
<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/activity/pingan/xxjhb/insurance-detail.png">
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$('#insuranceDetailButton').click(function(){
			TipWindow.showTip('#insuranceDetailTip');
		});
		$('.tip-overlay').click(function() {
			if (TipWindow.isShow('#insuranceDetailTip')) {
				TipWindow.hide('#insuranceDetailTip');
			}
		});
		$('#insuranceDetailTip').click(function(){
			TipWindow.hide('#insuranceDetailTip');
		});
	});
</script>
<@loading />
<@emailPopWindow />
<#if noIdentityCard?? && noIdentityCard=="true">
<div id="genderPopTipList" class="poptip">
	<ul>
	<div class="poptip-select-head" >请选择性别<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
		<#list genderList as gender>
			<li value="${gender.tagsId?c}">${gender.name}</li>
		</#list>
	</ul>
</div>
</#if>
<@htmlFoot/>