<#include "../../common/core.ftl" />
<@htmlHead title="" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/pingan3in1.css", "web/activity/pingan3in1-web.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		changeType('${type}');
	});
</script>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<div class="header-nav">
			<img src="${cdnUrl}/img/wap/activity/pingan/shy/pingan-logo.png">
		</div>
	</div>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/web/activity/pingan3in1/banner.png">
	</div>
	<#-- 选项卡（选择不同险种）当前页面因为是单个险种没有使用 -->
	<div class="tab hide">
		<ul id="tabOption">
			<li class="tab-option" value="hangban">
				<div class="option-content option-unselect" id="hangbanOption">账户险</div>
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
				<div class="option-content" id="chuxingOption">出行险</div>
				<div class="triangle-area" id="chuxingTriangle">
					<span class="select-triangle"></span>
				</div>
			</li>
		</ul>
	</div>
	<#-- 已有多少人领取 -->
	<div class="wrapper form-box">
		<div class="applied-count-area">
			<span class="applied-count-content">
				已有
				<span class="applied-count animated shake hide" id="hangbanCount">12,852</span>
				<span class="applied-count animated shake hide" id="zijiaCount">25,143</span>
				<span class="applied-count animated shake hide" id="chuxingCount">16,890</span>
				位用户成功领取保障
			</span>
		</div>
		<div class="form-title-split">
			<div class="form-title-left-icon"></div>
			<div class="form-title-line"></div>
			<div class="form-title-right-icon"></div>
		</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#-- 字段 -->
			<input type="hidden" id="type" value="chuxing"/>
			<div class="form-item">
				<div class="left field-name">
					姓名：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="name" name="name" placeholder="请填写您的姓名"/>
					</div>
					<div class="error-tip">
					
					</div>
				</div>
			</div>
			<div class="form-item">
				<div class="left field-name">
					手机号：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="mobile" name="mobile" placeholder="用于接收保单信息"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
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
			<div class="form-item">
				<div class="left field-name">
					邮箱：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="email" name="email" placeholder="请填写您的邮箱（选填）"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			
			<#-- 条款 -->
			<div class="protocal">
				<span>
					<img src="${cdnUrl}/img/wap/activity/pingan/shy/check.png"/>
					同意<a href="javascript:;">《用户协议》</a>并领取免费保险
				</span>
			</div>

			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button-shadow">
					<div class="submit-button" id="submitButton">
						免费领取
					</div>
				</div>
			</div>

			<div class="warm-tips">
				<span>温馨提示：正确填写才能成功领取、生成保单哦！</span>
			</div>
		</div>
	</div>
	<div class="split"></div>
	<#-- 保险详情描述 -->
	<div class="wrapper">
		<div class="insurance-description">
			<div class="description-title">
				<span id="hangbanTitle" class="hide">网银卫士，万元保障！</span>
				<span id="zijiaTitle" class="hide">驾驶全保障，放心开！</span>
				<span id="chuxingTitle">百万出行保障，任性选！</span>
			</div>
			<div class="policy-description">
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
							<td>网银e护<br/>(中国平安)</td>
							<td>90天</td>
							<td>
								<p>个人银行/网络账户损失，保障<span class="money">10000元</span>。</p>
							</td>
						</tr>
						<tr id="zijiaDescription" class="hide">
							<td>自驾险<br/>(平安驾驶)</td>
							<td>90天</td>
							<td>
								<p>在保险期间内，被保险人驾驭七座及以下非营运机动车辆发生道路交通事故导致身故/残疾，将赔付意外身故保险金最高<span class="money">5万元</span>。</p>
							</td>
						</tr>
						<tr id="chuxingDescription">
							<td>出行险<br/>(畅行天下)</td>
							<td>180天</td>
							<td>
								<p>1、乘坐飞机、高铁等意外伤害最高赔付<span class="money">100万</span>；</p>
								<p>2、乘坐出租车意外伤害赔付<span class="money">10万</span>；</p>
								<p>3、交通意外医疗<span class="money">2万</span>；</p>
								<p>4、行李物品和旅行证件损失保险最高<span class="money">500元</span>。</p>
							</td>
						</tr>
					</table>
				</div>
				<div class="policy-envelope">
					<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
					<div class="product-provider">
						<div>
							<span class="provider-title">以上保险由</span>
						</div>
						<div>
							<#-- <div class="provider-line"></div> -->
							<div class="provider-content">
								<span>远山保险和平安保险</span>
							</div>
						</div>
						<div>
							<span class="provider-title">联合提供</span>
						</div>
					</div>
				</div>
			</div>
			<div class="activity-rule">
				<h1>活动规则</h1>
				<p>1、有效参与用户身体健康，须要在25-50周岁；</p>
				<p>2、每位用户限领1份出行保障，多次领取无效；</p>
				<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知；</p>
			</div>
		</div>
	</div>
	<#-- 尾部 -->
	<div class="footer">
		
	</div>
</div>
<@htmlFoot />