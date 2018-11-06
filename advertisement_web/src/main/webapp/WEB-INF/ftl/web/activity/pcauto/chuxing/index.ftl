<#include "../../../common/core.ftl" />
<@htmlHead title="免费领出行保障送百元滴滴红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["web/activity/pcauto/pcauto-chuxing-web.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		selectInput('#yearValue', '#year', '#yearPopTipList');
		selectInput('#monthValue', '#month', '#monthPopTipList');
		selectInput('#dayValue', '#day', '#dayPopTipList');
		$('#yearPopTipList').find('li').click(function(){
			setBirthday($(this).attr('value'), null, null);
	    });
	    $('#monthPopTipList').find('li').click(function(){
			setBirthday(null, $(this).attr('value'), null);
	    });
	    $('#dayPopTipList').find('li').click(function(){
			setBirthday(null, null, $(this).attr('value'));
	    });
	});
	function setBirthday(year, month, day) {
		if (isNull(year)) {
			year = $('#year').val();
		}
		if (isNull(month)) {
			month = $('#month').val();
		}
		if (isNull(day)) {
			day = $('#day').val();
		}
		if (isNotEmpty(year) && isNotEmpty(month) && isNotEmpty(day)) {
			$('#birthday').val(year + "/" + month + "/" + day);
		}
	}
</script>
<div class="container">
	<div class="logo left">
		<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/pingan-logo.png"/>
	</div>
	<div class="insurance-description left">
		<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/zengxian-info.png"/>
	</div>
	<div class="form-wrap left">
		<input type="hidden" id="noIdentityCard" value="true"/>
		<input type="hidden" id="birthday" value="true"/>
		<input type="hidden" id="name" name="name" value="${name}"/>
		<input type="hidden" id="mobile" name="mobile" value="${mobile}"/>
		<div class="form-item clearfix">
			<div class="left field-name">
				出生日期：
			</div>
			<div class="left field-input">
				<input id="yearValue" name="yearValue" class="left field-input-control field-center-align calculate-input" value="年"/>
				<input type="hidden" id="year"/>
				<ul id="yearPopTipList" class="select-list">
					<#list 1995..1965 as y>
					<li value="${y?c}">${y?c}</li>
					</#list>
				</ul>
			</div>
			<div class="left field-input">
				<input id="monthValue" name="monthValue" class="left field-input-control field-center-align calculate-input" value="月"/>
				<input type="hidden" id="month"/>
				<ul id="monthPopTipList" class="select-list select-list-month">
					<#list 1..12 as m>
					<li value="${m?c}">${m?c}</li>
					</#list>
				</ul>
			</div>
			<div class="left field-input">
				<input id="dayValue" name="dayValue" class="left field-input-control field-center-align calculate-input" value="日"/>
				<input type="hidden" id="day"/>
				<ul id="dayPopTipList" class="select-list select-list-day">
					<#list 1..31 as d>
					<li value="${d?c}">${d?c}</li>
					</#list>
				</ul>
			</div>
			<ul class="right gender-radio" id="genderRadio">
				<#list genderList as gender>
					<li value="${gender.tagsId?c}">${gender.name}</li>
				</#list>
				<input type="hidden" id="gender"/>
				<#-- <div class="left gender-male">男</div>
				<div class="left gender-female">女</div> -->
			</ul>
		</div>
		<div class="submit-button-area">
			<div class="submit-button" id="submitButton">免费领好礼</div>
		</div>
		<p class="warm-tip"><img src="${cdnUrl}/img/wap/common/check.png"/>我已同意<a href="javascript:;" onclick="showProtocol();">《用户协议》</a><p>
		<div id="protocolContent" class="hide">
			<p>1. 每一客户受赠平安出行保险以1份为限。保险对象为出生25-50周岁，身体健康、能正常工作和劳动。</p>
			<p>2. 本保险仅提供电子保单，仅限赠送。保单生效后客户会自动收到短信通知，您也可以通过平安官网自助卡平台及寿险免费险平台查询功能查询您的保单信息。</p>
			<p>3. 投保成功后，请将短信保存并将短信上的电子保单记录在适当位置，以方便查询及理赔。</p>
			<p>4. 保险生效日期及被保险人收到短信通知上的保险起止日期为准。对保险起止日期之外发生的保险事故本公司不负给付保险金责任。</p>
			<p>5. 告知义务：依据我国《保险法》的规定，投保人、被保险人应如实告知，否则保险人有权依法解除保险合同，并对于保险合同解除前发生的保险事故不负任何责任。投保人、被保险人在投保时，应填写正确的个人信息，否则保险人有权依法解除保险合同。</p>
			<p>6. 本保险不接受撤保、退保、加保及被保险人更换。</p>
			<p>7. 免费险适用条款以及保单信息可以登录www.pingan.com网站查询。</p>
		</div>
		<p class="warm-tip">平安客服后续将致电以确认免费保险生效事宜</p>
	</div>
		
	<#-- 尾部 -->
	<input type="hidden" id="activityPath" value="${activityPath}"/>
</div>
<@loading/>
<@htmlFoot/>