<#include "../../common/core.ftl" />
<@htmlHead title="3分钟快速申请最高10万贷款" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/lhp.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/lhp/banner.png?${cdnFileVersion}">
		</#if>
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
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitInformationButton">
						
					</div>
				</div>
				<#-- 条款 -->
				<div class="protocal">
					<label for="agreeCheck" class="agree-check-label">
						<input type="checkbox" id="agreeCheck" class="agree-check">
						<em class="agree-icon"></em>	
						<span>申请赢豪礼：送阳光人寿出行无忧意外伤害保险2013款赠险</span>
					</label>
				</div>
				<#-- <div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/pingan/cycx/check.png"/>
						我同意<a href="javascript:;" onclick="showClause();">《安全条款》</a>并申请信用贷款
					</span>
					<div id="clauseContent" class="hide">
						<p>数据与信息安全 </p>
						<p>本公司在任何时候都竭力保证客户的个人信息不被人擅自或意外取得、处理或删除。采取各种实际措施保证个人信息不会被保管超过合理的期限 , 本公司将遵守所有关于可辨识个人信息保存的法规要求。 </p>
						<p>支持安全套接层协议和 128 位加密技术 -- 这种加密技术是互联网上保护数据安全的行业标准，让客户在进行会员管理、个人账户管理、充值等涉及敏感信息的操作时，信息被自动加密，然后才被安全地通过互联网发送出去。 </p>
						<p>采取各种合适的物理、电子和管理方面的措施来保护数据，以实现对数据安全的承诺。 </p>
						<p>采用集中的影像存储服务来保证合同等文件信息的存储，有效避免被篡改以及删除，并可以实现永久保存。 </p>
						<p>网站之间的页面跳转以及数据的发送采用数字签名技术来保证信息以及来源的不可否认性。 </p>
						<p>隐私保密</p>
						<p>本公司的业务是建立在与客户彼此信任的基础之上的，为了提供更优质的客户服务和产品。为了使您提供的所有信息都能得到机密保障，我们采用以下关于信息保护的政策： </p>
						<p>当您在本公司的推广页面中自主填写了您的个人需求及信息后，即视为您同意本公司将您的信息提供与本公司合作的贷款产品方，我们将根据您的财务需要向您推送符合您需求的贷款产品，并安排贷款产品专业服务人员为您提供专业咨询类服务。 </p>
						<p>本公司收集信息的范围仅限于那些本公司认为对了解您的财务需求和开展业务所必需的相关资料。 </p>
						<p>本公司将对客户提供的信息严格保密，除具备下列情形之一外，不会向任何外部机构披露： </p>
						<p>-经过客户事先同意而披露； </p>
						<p>-应法律法规的要求而披露； </p>
						<p>-应政府部门或其他代理机构的要求而披露； </p>
						<p>-应上级监管机构的要求而披露； </p>
						<p>本公司尽力确保对客户的信息记录是准确和及时的。 </p>
						<p>本公司设有严格的安全系统，以防止未经授权的任何人、包括本公司的职员获取客户信息。 </p>
						<p>因服务必要而委托的第三方，在得到本公司许可获取客户的个人信息时，都被要求严格遵守保密责任。</p>
					</div>
				</div> -->
			</div>
			<div class="warm-tips">
				<span>平台郑重承诺：所有贷款申请在未成功放款前绝不收取任何费用，为保障您的资金安全，请不要相信任何要求您支付费用的信息、邮件、电话等不实信息。</span>
			</div>
			<div class="desc">
				<img src="${cdnUrl}/img/wap/loan/lhp/desc.png?${cdnFileVersion}" alt="">
			</div>
		</div>
		<@randomSmsToken/>
		<@footer activityPath="${activityPath}"/>
		<#-- 尾部 -->
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
</div>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>