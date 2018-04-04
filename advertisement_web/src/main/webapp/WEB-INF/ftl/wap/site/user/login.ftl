<#include '../../common/core.ftl'/>
<@htmlHead title="绑定手机号" > 
	<@cssFile file=["wap/base.css", "wap/site/insurance.css", "wap/site/mine.css"] />
	<@jsFile file=["common/zengxian.js", "common/site/user.js"] />
</@htmlHead>
<@calculatePage/>
	<div class="wrapper">
		<div class="form-area">
			<div class="form-item">
				<div class="input-area">
					<div class="left mobile-input">
						<div class="field-input">
							<span class="field-embeded-name">手机</span>
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
				<div class="input-area">
					<div class="field-input">
						<span class="field-embeded-name">验证码</span>
						<input class="field-input-control test-input" type="text" id="smsCode" name="smsCode" placeholder="请填写验证码"/>
					</div>
					<div class="error-tip">
					
					</div>
					<input type="hidden" id="smsTipContent" value="赠险"/>
				</div>
			</div>
			<input type="hidden" id="returnUrl" value="${returnUrl?no_encode}" />
			<div class="submit-button-area">
				<div class="submit-button" id="loginButton">
					绑定手机号
				</div>
			</div>
		</div>
	</div>
<@htmlFoot />
