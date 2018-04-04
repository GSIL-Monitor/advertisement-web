<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取2万意外医疗保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/etyw.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<@commonBanner path="pingan/etyw"/>

	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<div class="form-title">
				<span class="form-title-tip">儿童（被保险人信息）</span>
			</div>
			<input type="hidden" id="hasChild" value="true"/>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<div class="form-item">
				<div class="left field-name">
					宝宝姓名：
				</div>
				<div class="right input-area">
					<div class="left name-input">
						<div class="field-input">
							<input class="field-input-control test-input" type="text" id="childName" name="childName" placeholder="请填写宝宝姓名" <#if code??>value="${code.name}"</#if>/>
						</div>
						<div class="error-tip">
						
						</div>
					</div>
					<ul class="right gender-radio" id="childGenderRadio">
						<#list genderList as gender>
							<li value="${gender.tagsId?c}">${gender.name}</li>
						</#list>
						<input type="hidden" id="childGender"/>
						<#-- <div class="left gender-male">男</div>
						<div class="left gender-female">女</div> -->
					</ul>
				</div>
			</div>
			<script type="text/javascript">
			    $(function () {
			        $('#childBirthday').mobiscroll({
			            preset: 'date',
			            theme: 'mobiscroll',
			            display: 'modal',
			            mode: 'scroller',
			            dateFormat: 'yy/mm/dd',
			            setText: '确定',
			            cancelText: '取消',
			            dateOrder: 'yymmdd',
			            startYear: 1995
			        });
			    });
			</script>
			<div class="form-item">
				<div class="left field-name">
					宝宝生日：
				</div>
				<div class="right input-area">
					<div class="field-input">
						<input class="field-input-control" type="text" id="childBirthday" name="childBirthday" placeholder="请填写宝宝生日"/>
					</div>
					<div class="error-tip">
						
					</div>
				</div>
			</div>
			<div class="form-title">
				<span class="form-title-tip">家长/监护人（投保人信息）</span>
			</div>
			<#-- 字段 -->
			<@commonZengXianForm/>


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
				免费领2万意外医疗保障
			</div>
			<div class="">
				<div class="product-description">
					<div>
						<span class="left">保障周期：180天</span>
						<span class="right">被保险人年龄：0-18周岁</span>
					</div>
					<table border="1" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="30%">保险名称</th>
								<th width="45%">保险权益</th>
								<th width="25%">保险金额</th>
							</tr>
						</thead>
						<tr>
							<td rowspan="2">平安儿童出行意外保障</td>
							<td>
								<p>
									乘坐公共交通工具（营运飞机、火车、出租车、轮船等）导致意外伤害
								</p>
							</td>
							<td>
								<span class="money">10-100万</span>
							</td>
						</tr>
						<tr>
							<td>
								<p>
									交通意外伤害医疗责任报销
								</p>
							</td>
							<td>
								<span class="money">2万</span>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="insurance-provider">
				以上保险由<span class="money">远山保险和平安寿险</span>联合提供
			</div>
			<div class="activity-rule">
				<h3>投保须知</h3>
				<p>1、宝宝参与年龄为0-18周岁，家长参与年龄须要在25-50周岁</p>
				<p>2、未成年人须有其监护人为其投保，每位家长限领一份保障，多次领取无效</p>
				<p>3、本保险仅提供电子保单，保单生效后，承保用户会自动收到保险平台及保险公司短信通知</p>
				<p>4、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）</p>
				<p>5、如有疑问，可咨询客服qq:2013780348</p>
			</div>
		</div>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
	</div>
</div>
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<@htmlFoot/>