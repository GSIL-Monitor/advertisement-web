<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万出行保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/shanyin/pinganwq.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<#-- <div class="header">
		<img src="${cdnUrl}/img/common/logo/<#if channel??>/activity/pingan-logo-${channel}<#else>pingan-logo</#if>.png?${cdnFileVersion}">
	</div> -->

	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingansy/wq/banner.png">
	</div>

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#-- 字段 -->
			<div class="form-item">
				<div class="left field-name">
					您的姓名：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control test-input" type="text" id="name" name="name" placeholder="请填写您的姓名" <#if code??>value="${code.name}"</#if>/>
					</div>
					<div class="error-tip">
					
					</div>
				</div>
			</div>
			<input type="hidden" id="${smsId}" value="${smsToken}" />

			<div class="form-item">
				<div class="left field-name">
					手机号码：
				</div>
				<div class="right input-area">
					<div class="left mobile-input">
						<div class="field-input">
							<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="请填写手机号"/>
						</div>
						<div class="error-tip">
							
						</div>
					</div>
					<div class="right sms-button" id="sendSmsButton">
						获取验证码
					</div>
				</div>
			</div>
			<div class="form-item">
				<div class="left field-name">
					验证码：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control test-input" type="text" id="smsCode" name="smsCode" placeholder="请填写验证码"/>
					</div>
					<div class="error-tip">
					
					</div>
					<input type="hidden" id="smsTipContent" value="平安出行保障"/>
				</div>
			</div>
			<#if noIdentityCard?? && noIdentityCard=="true">
			<input type="hidden" id="noIdentityCard" value="true"/>
			<div class="form-item">
				<div class="left field-name">
					您的性别：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<div class="field-input-control field-select" type="text" id="genderValue" name="genderValue"/><span class="placeholder">请选择您的性别</span></div>
						<input type="hidden" id="gender"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			<script type="text/javascript">
			    $(function () {
			        $('#birthday').mobiscroll({
			            preset: 'date',
			            theme: 'mobiscroll',
			            display: 'modal',
			            mode: 'scroller',
			            dateFormat: 'yy/mm/dd',
			            setText: '确定',
			            cancelText: '取消',
			            dateOrder: 'yymmdd',
			            startYear: 1965,
			            endYear: 1995
			        });
			    });
			</script>
			<div class="form-item">
				<div class="left field-name">
					您的生日：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="birthday" name="birthday" placeholder="请填写您的生日"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			<#else>
			<input type="hidden" id="noIdentityCard" value="false"/>
			<div class="form-item">
				<div class="left field-name">
					身份证号：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="请填写您的身份证号"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			</#if>
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-insurant-button" id="submitButtonWithEmailTip">
					
				</div>
			</div>

			<#-- 条款 -->
			<div class="protocal">
				<span>
					<img src="${cdnUrl}/img/wap/activity/pingan/shy/check.png"/>
					我已阅读投保须知及<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>，领取赠险同时开通超级社保卡
				</span>
				<div id="protocolContent" class="hide">
					<p>1. 每一客户受赠平安出行保险以1份为限。保险对象为出生25-50周岁，身体健康、能正常工作和劳动。</p>
					<p>2. 本保险仅提供电子保单，仅限赠送。保单生效后客户会自动收到短信通知，您也可以通过平安官网自助卡平台及寿险免费险平台查询功能查询您的保单信息。</p>
					<p>3. 投保成功后，请将短信保存并将短信上的电子保单记录在适当位置，以方便查询及理赔。</p>
					<p>4. 保险生效日期及被保险人收到短信通知上的保险起止日期为准。对保险起止日期之外发生的保险事故本公司不负给付保险金责任。</p>
					<p>5. 告知义务：依据我国《保险法》的规定，投保人、被保险人应如实告知，否则保险人有权依法解除保险合同，并对于保险合同解除前发生的保险事故不负任何责任。投保人、被保险人在投保时，应填写正确的个人信息，否则保险人有权依法解除保险合同。</p>
					<p>6. 本保险不接受撤保、退保、加保及被保险人更换。</p>
					<p>7. 免费险适用条款以及保单信息可以登录www.pingan.com网站查询。</p>
				</div>
			</div>

			<div class="warn-tips">
				*郑重承诺：我们将严格遵守信息保密制度
			</div>

			<div class="warm-tips">
				<span>平安人寿后续将致电以确认免费保险生效事宜</span>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<img src="${cdnUrl}/img/wap/activity/pingansy/wq/table-header.png" class="insurance-description-header">
		<div class="insurance-description">
			<div class="product-description">
				<div class="description-title">
					出行保障详情
				</div>
				<table border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th width="30%">产品名称</th>
							<th width="20%">产品期限</th>
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
				以上保险由<span class="money">闪银、远山保险和平安寿险</span>联合提供
			</div>
			<div>
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/description.png" style="width:100%" />
			</div>
			<div class="activity-rule">
				<h3>投保须知</h3>
				<p>1、有效参与用户身体健康，须要在25-50周岁</p>
				<p>2、每位用户限领1份出行保障，多次领取无效</p>
				<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知</p>
				<p>4、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）</p>
				<p>5、如有疑问，可咨询客服qq:2013780348</p>
			</div>
		</div>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
	</div>
</div>
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