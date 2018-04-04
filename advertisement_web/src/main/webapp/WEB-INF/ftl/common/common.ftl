<#macro calculatePage>
	<script type="text/javascript">
		function resize() {
			document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
			var deviceWidth = document.documentElement.clientWidth;
			if(deviceWidth > 750) document.documentElement.style.fontSize = '100px';
			document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
		}
		var resizeEvent = 'orientationchange' in window ? 'orientationchange' : 'resize';
		window.addEventListener(resizeEvent, resize, false);
		document.addEventListener('DOMContentLoaded', resize, false);
	</script>
</#macro>
<#macro wxConfig>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
    /*
     * 注意：
     * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
     * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
     */
    wx.config({
        beta: true, //注入wx.invoke和wx.on方法。wx.invoke用于调用未开放的由页面主动调用的JS接口，wx.on用于调用未开放的监听类JS接口。
        debug: false,
        appId: '${appId}',
        timestamp: '${jsSignature.timestamp?c}',
        nonceStr: '${jsSignature.noncestr}',
        signature: '${jsSignature.signature}',
        jsApiList: [
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ]
    });
	</script>
</#macro>
<#macro wxShare title="" description="" imgUrl="" link="">
	<@wxConfig />
	<script>
		wx.ready(function () {
		    wx.onMenuShareTimeline({
			    title: '${title}', // 分享标题
			    link: '${link}', // 分享链接
			    imgUrl: '${imgUrl}', // 分享图标
			    success: function () {
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () {
			        // 用户取消分享后执行的回调函数
			    }
			});

			// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
			wx.onMenuShareAppMessage({
			    title: '${title}', // 分享标题
			    desc: '${description}', // 分享描述
			    link: '${link}', // 分享链接
			    imgUrl: '${imgUrl}', // 分享图标
			    type: '', // 分享类型,music、video或link，不填默认为link
			    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			    success: function () {
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () {
			        // 用户取消分享后执行的回调函数
			    }
			});

			wx.error(function(res){
			    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
			});
		});
	</script>
</#macro>

<#macro htmlFoot>
		<@loading />
		<input type="hidden" id="isMobilePage" value=${isMobilePage!""} />
		<#if allConfigInPage??>
			<div id="configArea">
			<#list allConfigInPage as config>
			<input type="hidden" id="${config.key}" value="${config.value}" />
			</#list>
			</div>
		</#if>
		<@commonTip/>
		<@baidu/>
		<@cnzz/>
	</body>
	</html>
</#macro>
<#macro baidu>
	<#--
	<script>
		(function(){
		    var bp = document.createElement('script');
		    bp.src = '//push.zhanzhang.baidu.com/push.js';
		    var s = document.getElementsByTagName("script")[0];
		    s.parentNode.insertBefore(bp, s);
		})();
	</script>
	-->
	<script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?<#if isYuanshanbao??&&isYuanshanbao=="true">490bf88a4a637a8b59c4e372065317c4<#elseif isDachuanbao??&&isDachuanbao=="true">e83985ca653bc52de9cf7c6c8d97b104<#else>11dfbf08c34db0db4758d639838fde2c</#if>";
		  var s = document.getElementsByTagName("script")[0];
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
</#macro>
<#macro cnzz>
	<div class="hide">
		<script type="text/javascript">
			var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
			document.write(unescape("%3Cspan id='cnzz_stat_icon_1260887023'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1260887023' type='text/javascript'%3E%3C/script%3E"));
		</script>
	</div>
</#macro>
<#macro loading>
	<div class="loading-window hide">
		<div class="loading-effect">
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		    <span></span>
		</div>
	</div>
</#macro>
<#macro commonHeader insurance="pingan" description="平安保险" headerClass="">
	<div class="header ${headerClass}">
		<div class="header-wrap">
		<#if channel?? && channel.imageUrl?? && (channel.imageUrl?length>0)>
			<img src="${channel.imageUrl}">
		<#else>
			<img src="${cdnUrl}/img/common/logo/${insurance}-logo.png?${cdnFileVersion}">
		</#if>

		<#if hasHeaderCooperateConfig?? && hasHeaderCooperateConfig == "true" && (description?? && description?length > 0)>
			<div class="header-description">
				远山携手${description}
			</div>
		</#if>
		</div>
	</div>
</#macro>

<#macro commonHeaderYangGuang>
	<@commonHeader insurance="yangguang" description="阳光保险"/>
</#macro>

<#macro commonHeaderMetlife>
	<@commonHeader insurance="metlife" description="大都会保险"/>
</#macro>

<#macro commonHeaderBaiNian>
	<@commonHeader insurance="bainian" description="百年人寿"/>
</#macro>

<#macro commonHeaderZhongYing>
	<@commonHeader insurance="zhongying" description="中英人寿"/>
</#macro>

<#macro commonHeaderHuaXia>
	<@commonHeader insurance="huaxia" description="华夏人寿"/>
</#macro>

<#macro commonHeaderTaiKang>
	<@commonHeader insurance="taikang" description="泰康人寿"/>
</#macro>

<#macro commonZengXianForm hideIdentityCard="false">
	<#if hasIdentityCardConfig?? && hasIdentityCardConfig=="false">
	<input type="hidden" id="noIdentityCard" value="true"/>
	<div class="form-item">
		<div class="left field-name">
			您的姓名：
		</div>
		<div class="right input-area">
			<div class="left name-input">
				<div class="field-input">
					<input class="field-input-control test-input" type="text" id="name" name="name" placeholder="请填写您的姓名" <#if code??>value="${code.name}"</#if>/>
				</div>
				<div class="error-tip">
				
				</div>
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
	<div class="form-item">
		<div class="left field-name">
			手机号码：
		</div>
		<div class="right input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	<#else>
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
	<#if hasVerifyCodeConfig?? && hasVerifyCodeConfig=="true">
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
	<#else>
	<div class="form-item">
		<div class="left field-name">
			手机号码：
		</div>
		<div class="right input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="noIdentityCard" value="false"/>
	<div class="form-item <#if hideIdentityCard?? && hideIdentityCard=="true">hide</#if>">
		<div class="left field-name">
			身份证号：
		</div>
		<div class="right input-area">
			<div class="field-input">
				<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="${formIDPlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="${smsId}" value="${smsToken}" />
	<#if hasInviteCodeConfig?? && hasInviteCodeConfig == "true">
	<div class="form-item">
		<div class="left field-name">
			兑换码：
		</div>
		<div class="right input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="inviteCode" name="inviteCode" placeholder="请填写兑换码"<#if inviteCode??> value="${inviteCode}"</#if>/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<#if emailPositionConfig?? && emailPositionConfig == "index">
	<div class="form-item">
		<div class="left field-name">
			邮箱<#if emailOptionConfig?? && emailOptionConfig=="true">(选填)</#if>：
		</div>
		<div class="right input-area">
			<div class="field-input">
				<input class="field-input-control" type="email" id="email" name="email" placeholder="请填写您的邮箱<#if emailOptionConfig?? && emailOptionConfig=="true">(选填)</#if>"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
</#macro>

<#macro noTitleZengXianForm>
	<#if hasIdentityCardConfig?? && hasIdentityCardConfig=="false">
	<input type="hidden" id="noIdentityCard" value="true"/>
	<div class="form-item">
		<div class="input-area clearfix">
			<div class="left name-input">
				<div class="field-input">
					<input class="field-input-control test-input" type="text" id="name" name="name" placeholder="请填写您的姓名" <#if code??>value="${code.name}"</#if>/>
				</div>
				<div class="error-tip">
				
				</div>
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
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="text" id="birthday" name="birthday" placeholder="请填写您的生日"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	<#else>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control test-input" type="text" id="name" name="name" placeholder="请填写您的姓名" <#if code??>value="${code.name}"</#if>/>
			</div>
			<div class="error-tip">
			
			</div>
		</div>
	</div>
	<#if hasVerifyCodeConfig?? && hasVerifyCodeConfig=="true">
	<div class="form-item">
		<div class="input-area">
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
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control test-input" type="text" id="smsCode" name="smsCode" placeholder="请填写验证码"/>
			</div>
			<div class="error-tip">
			
			</div>
			<input type="hidden" id="smsTipContent" value="平安出行保障"/>
		</div>
	</div>
	<#else>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="noIdentityCard" value="false"/>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="${formIDPlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="${smsId}" value="${smsToken}" />
	<#if hasInviteCodeConfig?? && hasInviteCodeConfig == "true">
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="tel" id="inviteCode" name="inviteCode" placeholder="请填写兑换码"<#if inviteCode??> value="${inviteCode}"</#if>/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<#if emailPositionConfig?? && emailPositionConfig == "index">
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<input class="field-input-control" type="email" id="email" name="email" placeholder="请填写您的邮箱<#if emailOptionConfig?? && emailOptionConfig=="true">(选填)</#if>"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
</#macro>

<#macro embededTitleZengXianForm>
	<#if hasIdentityCardConfig?? && hasIdentityCardConfig=="false">
	<input type="hidden" id="noIdentityCard" value="true"/>
	<div class="form-item">
		<div class="input-area clearfix">
			<div class="left name-input">
				<div class="field-input">
					<span class="field-embeded-name">姓名</span>
					<input class="field-input-control short-input" type="text" id="name" name="name" placeholder="请填写姓名" <#if code??>value="${code.name}"</#if>/>
				</div>
				<div class="error-tip">
				
				</div>
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
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">生日</span>
				<input class="field-input-control" type="text" id="birthday" name="birthday" placeholder="请填写您的生日"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">手机</span>
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	<#else>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">姓名</span>
				<input class="field-input-control test-input" type="text" id="name" name="name" placeholder="请填写您的姓名" <#if code??>value="${code.name}"</#if>/>
			</div>
			<div class="error-tip">
			
			</div>
		</div>
	</div>
	<#if hasVerifyCodeConfig?? && hasVerifyCodeConfig=="true">
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
	<#else>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">手机</span>
				<input class="field-input-control" type="tel" id="mobile" name="mobile" placeholder="${formMobilePlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="noIdentityCard" value="false"/>
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">身份证</span>
				<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="${formIDPlaceholderConfig}"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<input type="hidden" id="${smsId}" value="${smsToken}" />
	<#if hasInviteCodeConfig?? && hasInviteCodeConfig == "true">
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">兑换码</span>
				<input class="field-input-control" type="tel" id="inviteCode" name="inviteCode" placeholder="请填写兑换码"<#if inviteCode??> value="${inviteCode}"</#if>/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
	<#if emailPositionConfig?? && emailPositionConfig == "index">
	<div class="form-item">
		<div class="input-area">
			<div class="field-input">
				<span class="field-embeded-name">邮箱</span>
				<input class="field-input-control" type="email" id="email" name="email" placeholder="请填写您的邮箱<#if emailOptionConfig?? && emailOptionConfig=="true">(选填)</#if>"/>
			</div>
			<div class="error-tip">
				
			</div>
		</div>
	</div>
	</#if>
</#macro>

<#macro warmTip>
	<div class="warn-tips">
		*郑重承诺：我们将严格遵守信息保密制度
	</div>

	<div class="warm-tips">
		<span>本人同意中国平安后续致电联系确认赠险相关事宜</span>
	</div>
</#macro>


<#macro warmTipYangGuang>
	<div class="warn-tips">
		*郑重承诺：我们将严格遵守信息保密制度
	</div>

	<div class="warm-tips">
		<span>阳光客服专线"95510"后续将致电以确认免费保险生效事宜</span>
	</div>
</#macro>

<#macro pinganProtocol>
	<div class="protocal">
		<span>
			<img src="${cdnUrl}/img/wap/common/check.png"/>
			我已阅读投保须知及<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>
		</span>
		<@pinganProtocolContent/>
	</div>
</#macro>

<#macro pinganProtocolContent>
	<@commonProtocolContent/>
</#macro>

<#macro commonProtocol merchantName="平安" merchantDomain="www.pingan.com">
	<div class="protocal">
		<span>
			<img src="${cdnUrl}/img/wap/common/check.png"/>
			我已阅读投保须知及<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>
		</span>
		<@commonProtocolContent merchantName="${merchantName}" merchantDomain="${merchantDomain}"/>
	</div>
</#macro>

<#macro commonProtocolContent merchantName="平安" merchantDomain="www.pingan.com">
	<div id="protocolContent" class="hide">
		<p>1. 每一客户受赠保险以1份为限。保险对象为出生${indexRuleAgeConfig!"25-50"}周岁，身体健康、能正常工作和劳动。</p>
		<p>2. 本保险仅提供电子保单，仅限赠送。保单生效后客户会自动收到短信通知，您也可以通过${merchantName}官网查询您的保单信息。</p>
		<p>3. 投保成功后，请将短信保存并将短信上的电子保单记录在适当位置，以方便查询及理赔。</p>
		<p>4. 保险生效日期以被保险人收到短信通知上的保险起止日期为准。对保险起止日期之外发生的保险事故本公司不负给付保险金责任。</p>
		<p>5. 告知义务：依据我国《保险法》的规定，投保人、被保险人应如实告知，否则保险人有权依法解除保险合同，并对于保险合同解除前发生的保险事故不负任何责任。投保人、被保险人在投保时，应填写正确的个人信息，否则保险人有权依法解除保险合同。</p>
		<p>6. 本保险不接受撤保、退保、加保及被保险人更换。</p>
		<p>7. 免费险适用条款以及保单信息可以登录${merchantDomain}网站查询。</p>
	</div>
</#macro>

<#macro yangguangProtocol>
	<@commonProtocol merchantName="阳光" merchantDomain="www.sinosig.com"/>
</#macro>


<#macro insuranceProvider>
	<#if indexProviderContentConfig?? && (indexProviderContentConfig?length>0)>
	<div class="insurance-provider">
		以上保险由<span class="money">${indexProviderContentConfig}</span>联合提供
	</div>
	</#if>
</#macro>

<#macro activityRule defaultInsurance="平安保险" otherInsurance="泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险">
	<div class="activity-rule">
		<h3>活动规则</h3>
		<#assign ruleIndex=1/>
		<#if indexRuleCouponConfig?? && (indexRuleCouponConfig?length>0)>
		<p>${ruleIndex}、${indexRuleCouponConfig}</p>
		<#assign ruleIndex=ruleIndex+1/>
		</#if>
		<p>${ruleIndex}、有效参与用户身体健康，须要在${indexRuleAgeConfig!"25-50"}周岁</p>
		<#assign ruleIndex=ruleIndex+1/>
		<p>${ruleIndex}、每位用户限领1份赠险，多次领取无效</p>
		<#assign ruleIndex=ruleIndex+1/>
		<p>${ruleIndex}、本保险仅提供电子保单，保单生效后，投保用户会自动收到保险平台及保险公司短信通知</p>
		<#assign ruleIndex=ruleIndex+1/>
		<#if (!indexRuleOtherInsuranceConfig?? || indexRuleOtherInsuranceConfig!="false") && otherInsurance?? && (otherInsurance?length>0)>
		<p>${ruleIndex}、因单一保险公司（如${defaultInsurance}）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制${otherInsurance}等）</p>
		<#assign ruleIndex=ruleIndex+1/>
		</#if>
		<p>${ruleIndex}、如有疑问，可致电服务热线：010-64167126或发送Email至服务邮箱：service@yuanshanbao.com</p>
	</div>
</#macro>

<#macro activityRuleYangGuang>
	<@activityRule defaultInsurance="阳光保险" otherInsurance="平安人寿、泰康人寿、中美大都会、百年人寿、华夏保险"/>
</#macro>

<#macro notOriginalMerchantAlert defaultInsurance="平安保险">
	<#if notOriginalMerchant=="true">
	<script type="text/javascript">
		$(function() {
			setTimeout("TipWindow.showTip('#merchantAlertTip')", 100);
			$('#merchantAlertTipButton').click(function(){
				TipWindow.hide('#merchantAlertTip');
			});
			$('.tip-overlay').click(function() {
				if (TipWindow.isShow('#merchantAlertTip')) {
					TipWindow.hide('#merchantAlertTip');
				}
			});
		});
	</script>
	<div id="merchantAlertTip" class="tip-area merchant-alert-tip hide">
		<div class="tip-window center">
			<div class="tip-content">
				<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
				<div class="tip-title">
					温馨提示
				</div>
				<div class="tip-text">
					您领取的${defaultInsurance}因地域年龄等限制，暂时无法领取，我们将赠送您<span class="money">${insurance.fullName}</span>。
				</div>
				<div class="tip-ticket">
					<img src="${cdnUrl}/img/wap/common/ticket-background.png" class="tip-ticket-background">
					<div class="tip-ticket-content">
						<img src="${cdnUrl}/img/common/logo/${merchant.englishName}-logo.png?${cdnFileVersion}">
						<div class="tip-insurance-name money">
							${insurance.fullName}
						</div>
					</div>
				</div>
				<div class="tip-button">
					<a href="javascript:;" id="merchantAlertTipButton" name="tipSingleButton" class="tip-single-button center">
						<div name="tipSingleButton" class="tip-single-button-text">好的</div>
					</a>
				</div>
			</div>
		</div>
	</div>
	</#if>
</#macro>

<#macro policyResultTable hasTime="true">
	<div class="table-area">
		<#-- 成功通知 -->
		<div class="success-notify">
			<#if insurant.policyNumber??>
			<h1>恭喜您</h1>
			<h2>已成功领取${merchant.shortName}保险</h2>
			<#else>
			<h1>信息已成功提交！</h1>
			<h4>${merchant.name}客服后期会致电您确认保单信息等具体事宜。</h4>
			</#if>
		</div>

		<table border="1" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th colspan="2">${insurance.fullName}</th>
				</tr>
			</thead>
			<tr>
				<td>
					<p class="policy-title">投保人信息</p>
				</td>
				<td>
					<p class="policy-title">保险信息</p>
				</td>
			</tr>
			<tr>
				<td>
					
					<p>投保人：${insurant.name}</p>
					<p>联系方式：${insurant.mobile}</p>
					<p>生日：${insurant.birthdayValue}</p>
					<#if insurant.identityCard??>
					<p>身份信息：${insurant.identityCard}</p>
					</#if>
				</td>
				<td>
					<p>保险公司：${merchant.name}</p>
					<p>保险产品：${insurance.name}</p>
					<p>保单号：<#if insurant.policyNumber?? && merchant.englishName!="zhongying">${insurant.policyNumber}<#else>确认信息后1-2个工作日提供</#if></p>
					<#if hasTime=="true">
					<p>保险有效期：即日起至${insurant.endTimeValue}</p>
					</#if>
				</td>
			</tr>
			<tr>
				<td class="policy-title">保险范围</td>
				<td class="policy-title">保险金额</td>
			</tr>
			<#if insurance.detailList??>
				<#list insurance.detailList as detail>
				<tr>
					<td>${detail.cover}</td>
					<td>${detail.amount}</td>
				</tr>
				</#list>
			</#if>
		</table>
	</div>
</#macro>

<#macro policyResultTip>
	<div class="policy-tip">
		<p>${merchant.shortName}客服后期会致电您确认保单信息等事宜；</p>
		<p>保单号稍后会以短信形式发送到您的手机，注意查收。</p>
	</div>
</#macro>

<#macro surveyPageTitle>
	<span class="form-title-content">
		<@promotionTitleContent type="问卷调查"/>
	</span>
</#macro>

<#macro calculatePageTitle>
	<div class="form-title">
		<@promotionTitleContent type="保费试算"/>
	</div>
</#macro>

<#macro promotionTitleContent type="">
	参与${type}<#if surveyPromotionTextConfig?? && (surveyPromotionTextConfig?length>0)>，即送</#if><span class="money">${surveyPromotionTextConfig}</span>
</#macro>


<#macro surveyForm>
	<#-- <div class="form-item">
		<div>1. <span id="question1"><@questionInput question="您认为下列保障哪项最重要？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="人身意外保障" id="option11"/><label for="option11">人身意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="重大疾病保障" id="option12"/><label for="option12">重大疾病保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="养老金保障或其他" id="option13"/><label for="option13">养老金保障或其他</label>
		</div>
	</div>

	<div class="form-item">
		<div>2. <span id="question2"><@questionInput question="您愿意花费家庭收入的多少比例购买保险？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="10%" id="option21"/><label for="option21">10%</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="20%" id="option22"/><label for="option22">20%</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="大于30%" id="option23"/><label for="option23">大于30%</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="您认为家庭成员中谁最需要买保险？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="全家支柱的中青年人" id="option31"/><label for="option31">全家支柱的中青年人</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="老人" id="option32"/><label for="option32">老人</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="小孩" id="option33"/><label for="option33">小孩</label>
		</div>
	</div> -->
	<div class="form-item">
		<div>1. <span id="question1"><@questionInput question="请问您是否已经有子女？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="1个" id="option11"/><label for="option11">1个</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="2个或以上" id="option12"/><label for="option12">2个或以上</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="没有" id="option13"/><label for="option13">没有</label>
		</div>
	</div>

	<div class="form-item">
		<div>2. <span id="question2"><@questionInput question="请问平时您或家人出行多以哪种方式？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="自驾车" id="option21"/><label for="option21">自驾车</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="火车或公车" id="option22"/><label for="option22">火车或公车</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="飞机" id="option23"/><label for="option23">飞机</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="如果让您选择，您会更倾向于购买哪种商业保障？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="意外保障" id="option31"/><label for="option31">意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="重疾保障" id="option32"/><label for="option32">重疾保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="医疗保障" id="option33"/><label for="option33">医疗保障</label>
		</div>
	</div>
	<#if emailPositionConfig?? && emailPositionConfig=="last">
		<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
	</#if>
</#macro>

<#macro questionInput question="" index=""><input type="hidden" name="question${index}" value="${question}" />${question}</#macro>

<#macro surveyFormYangGuang hide="false">
	<div class="form-item">
		<div>1. <span id="question1"><@questionInput question="请问您为自己买过哪些商业保险？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="意外险" id="option11"/><label for="option11">A.意外险</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="重疾险" id="option12"/><label for="option12">B.重疾险</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="年金险" id="option13"/><label for="option13">C.年金险</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="未购买过" id="option14"/><label for="option14">D.未购买过</label>
		</div>
	</div>

	<div class="form-item">
		<div>2. <span id="question2"><@questionInput question="请问您现阶段最关心的是生活中哪部分的保障？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="意外保障" id="option21"/><label for="option21">A.意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="健康保障" id="option22"/><label for="option22">B.健康保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="养老保障" id="option23"/><label for="option23">C.养老保障</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="您可以接受每月花多少钱来获得上述保障？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="300-500元" id="option31"/><label for="option31">A.3OO-5OO元</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="500-1000元" id="option32"/><label for="option32">B.5OO-1OOO元</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="1000元以上" id="option33"/><label for="option33">C.1OOO元以上</label>
		</div>
	</div>
	<#if emailPositionConfig?? && emailPositionConfig=="last">
	<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
	</#if>
</#macro>
<#macro surveyFormZhongYing hide="false">
	<div class="form-item">
		<div>1. <span id="question1"><@questionInput question="请问您是否已经有子女？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="1个" id="option11"/><label for="option11">1个</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="2个或以上" id="option12"/><label for="option12">2个或以上</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="没有" id="option13"/><label for="option13">没有</label>
		</div>
	</div>

	<div class="form-item">
		<div>2. <span id="question2"><@questionInput question="请问平时您或家人出行多以哪种方式？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="自驾车" id="option21"/><label for="option21">自驾车</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="火车或公车" id="option22"/><label for="option22">火车或公车</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="飞机" id="option23"/><label for="option23">飞机</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="如果让您选择，您会更倾向于购买哪种商业保障？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="意外保障" id="option31"/><label for="option31">意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="重疾保障" id="option32"/><label for="option32">重疾保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="医疗保障" id="option33"/><label for="option33">医疗保障</label>
		</div>
	</div>
	<#if emailPositionConfig?? && emailPositionConfig=="last">
	<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
	</#if>
</#macro>
<#macro surveyFormTaiKang hide="false">
	<div class="form-item">
		<div>1. <span id="question1"><@questionInput question="光阴匆匆，现在您是以什么角色去为爱奋斗的？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="为人父母" id="option11"/><label for="option11">为人父母</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="为人妻/夫" id="option12"/><label for="option12">为人妻/夫</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="为人子女" id="option13"/><label for="option13">为人子女</label>
		</div>
	</div>

	<div class="form-item four-item">
		<div>2. <span id="question2"><@questionInput question="在奋斗的光阴里，您有为自己买过哪些保障？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="只买了社保" id="option21"/><label for="option21">只买了社保</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="社保商保都有" id="option22"/><label for="option22">社保商保都有</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="只买了商保" id="option23"/><label for="option23"> 只买了商保</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="社保商保都没买" id="option24"/><label for="option24"> 社保商保都没买</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="在未来的光阴里，您会优先为自己或家人购买哪些保障？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="健康保障" id="option31"/><label for="option31">健康保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="意外保障" id="option32"/><label for="option32">意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="养老保障" id="option33"/><label for="option33">养老保障</label>
		</div>
	</div>
	<#if emailPositionConfig?? && emailPositionConfig=="last">
	<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
	</#if>
</#macro>

<#macro surveyFormTaiPing hide="false">
	<div class="form-item">
		<div>1. <span id="question1"><@questionInput question="如果您购买保险，最需要购买哪类保险产品？" index="1"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="人身意外保障" id="option11"/><label for="option11">人身意外保障</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="重大疾病保险" id="option12"/><label for="option12">重大疾病保险</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="养老保险" id="option13"/><label for="option13">养老保险</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer1" value="住院医疗保险" id="option14"/><label for="option14">住院医疗保险</label>
		</div>
	</div>

	<div class="form-item four-item">
		<div>2. <span id="question2"><@questionInput question="您认为每年投入多少钱用于保险比较合适？" index="2"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="1000元以下" id="option21"/><label for="option21">1OOO元以下</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="1000-3000元" id="option22"/><label for="option22">1OOO-3OOO元</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="3000-5000元" id="option23"/><label for="option23">3OOO-5OOO元</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer2" value="5000元以上" id="option24"/><label for="option24">5OOO元以上</label>
		</div>
	</div>

	<div class="form-item">
		<div>3. <span id="question3"><@questionInput question="您购买保险时最看重什么？" index="3"/></span></div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="保险高低" id="option31"/><label for="option31">保险高低</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="手续便捷" id="option32"/><label for="option32">手续便捷</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="口碑好坏" id="option33"/><label for="option33">口碑好坏</label>
		</div>
		<div class="field-radio">
			<input type="radio" name="answer3" value="赔偿速度" id="option34"/><label for="option34">赔偿速度</label>
		</div>
	</div>
	<#if emailPositionConfig?? && emailPositionConfig=="last">
	<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
	</#if>
</#macro>

<#macro surveyFormByMerchant>
	<#if merchant?? && merchant.englishName?? && merchant.englishName == "yangguang">
	<@surveyFormYangGuang/>
	<#elseif merchant?? && merchant.englishName?? && merchant.englishName == "zhongying">
	<@surveyFormZhongYing/>
	<#elseif merchant?? && merchant.englishName?? && merchant.englishName == "taikang">
	<@surveyFormTaiKang/>
	<#elseif merchant?? && merchant.englishName?? && merchant.englishName == "taiping">
	<@surveyFormTaiPing/>
	<#else>
	<@surveyForm />
	</#if>
</#macro>

<#macro emailPopWindow title="为确保您的保单生效，请补充邮箱信息哦">
	<div id="emailTip" class="tip-area email-tip hide">
		<input type="hidden" id="existEmail" value="<#if insurant?? && insurant.email?? && (insurant.email?length>0)>true</#if>"/>
		<div class="tip-window center">
			<div class="tip-content">
				<#-- <span class="tip-close-icon"><img id="emailTipClose" src="${cdnUrl}/img/wap/common/tip-close.png"/></span> -->
				<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
				<div class="tip-title">
					${title}
				</div>
				<div class="form-item">
					<div class="left field-name">
						邮箱：
					</div>
					<div class="right input-area">
						<div class="field-input">
							<input class="field-input-control" type="email" id="email" name="email" placeholder="请填写真实邮箱"/>
						</div>
						<div class="error-tip">
							
						</div>
					</div>
				</div>
				<div class="tip-button">
					<a href="javascript:;" id="emailTipButton" name="tipSingleButton" class="tip-single-button center">
						<div name="tipSingleButton" class="tip-single-button-text">提交</div>
					</a>
				</div>
			</div>
		</div>
	</div>
</#macro>
<#macro identityCardPopWindow title="身份证号为保单生效和理赔的唯一依据，请务必填写">
	<#if identityCardPopupConfig?? && identityCardPopupConfig!="no">
	<div id="identityCardTip" class="tip-area email-tip identity-card-tip hide">
		<div class="tip-window center">
			<span class="tip-close-icon"><img id="identityCardTipClose" src="${cdnUrl}/img/wap/common/idcard/close.png"/></span>
			<span class="tip-title-icon"><img src="${cdnUrl}/img/wap/common/idcard/id-tip-title.png"/></span>
			<div class="tip-content">
				<div class="tip-title">
					<#-- ${title} -->
				</div>
				<div class="form-item">
					<div class="left field-name">
						身份证：
					</div>
					<div class="right input-area">
						<div class="field-input">
							<input class="field-input-control" type="text" id="identityCard" name="identityCard" placeholder="用于成功投保身份验证"/>
						</div>
						<div class="error-tip">
							
						</div>
					</div>
				</div>
				<div class="tip-button">
					<a href="javascript:;" id="identityCardTipButton" name="tipSingleButton" class="tip-single-button center">
						<div name="tipSingleButton" class="tip-single-button-text">提交</div>
					</a>
				</div>
				<div class="warm-tips">
					在核实您的身份信息后，保险公司将给您下发成功承保的保单信息，请留意短信！
				</div>
			</div>
		</div>
	</div>
	</#if>
</#macro>
<#macro resultPopWindow title="恭喜您领取成功" buttonText="提交" buttonFunction="">
	<div id="resultTip" class="tip-area result-tip hide">
		<script type="text/javascript">
			$(function(){
				$('#resultTipButton').click(function(){
					location.href=getLink();
				});
				$('#resultTipClose').click(function(){
					location.href=getLink();
				});
				$('.tip-overlay').click(function() {
					if (TipWindow.isShow('#resultTip')) {
						location.href=getLink();
					}
				});
			});

			function getLink() {
				<#if calculatePositionConfig?? && calculatePositionConfig=="no">
				return '${surveyPromotionLinkConfig}';
				<#else>
				return getRealPath('/activity{0}/survey.html?gorderId=' + $('#resultGorderId').val());
				</#if>
			}
		</script>
		<div class="tip-window center">
			<div class="tip-content">
				<span class="tip-close-icon"><img id="resultTipClose" src="${cdnUrl}/img/wap/common/tip-close.png"/></span>
				<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
				<div class="result-tip-icon">
					<img src="${cdnUrl}/img/wap/common/tip-result-success.png"/>
				</div>
				<div class="tip-title">
					<#if title?? && (title?length > 0)>
					${title}
					<#else>
					<@resultTitleArea/>
					</#if>
				</div>
				<div class="tip-button">
					<a href="javascript:;" id="resultTipButton" name="tipSingleButton" class="tip-single-button center" onclick="${buttonFunction}">
						<div name="tipSingleButton" class="tip-single-button-text">${buttonText}</div>
					</a>
				</div>
				<input type="hidden" id="resultGorderId" />
				<input type="hidden" id="resultGorderKey" />
			</div>
		</div>
	</div>
</#macro>
<#macro failPopWindow>
	<div id="failTip" class="tip-area result-tip fail-tip hide">
		<script type="text/javascript">
			$(function(){
				$('.tip-overlay').click(function() {
					if (TipWindow.isShow('#failTip')) {
						TipWindow.hide('#failTip');
					}
				});
			});
		</script>
		<div class="tip-window center">
			<div class="tip-content">
				<span class="tip-close-icon"><img id="failTipClose" src="${cdnUrl}/img/wap/common/tip-close.png"/></span>
				<div class="result-area">
					<div class="result-header">
						<img src="${cdnUrl}/img/wap/common/failtip/result-header.png"/>
					</div>
					<div class="result-content">
						<#if insurant??>
						<h1>恭喜您</h1>
						<h2>已成功提交申请<span class="money">${insurance.fullName}</span>。</h2>
						<#else>
						<h1>小遗憾哦</h1>
						<h2>您暂时没有符合的保险可以领</h2>
						</#if>
						<div class="result-content-split"></div>
						<h2>点击下方按钮</h2>
						<h2>领取更多红包</h2>
					</div>
				</div>
				<div class="result-gift-flap">
					<img src="${cdnUrl}/img/wap/common/failtip/result-gift-flap.png"/>
				</div>
				<div class="tip-gift-area">
					<#list failTipLinkConfig?split(",") as link>
					<div class="tip-gift-item clearfix">
						<div class="tip-gift-icon left">
							<img src="${cdnUrl}/img/wap/common/failtip/result-gift.png"/>
						</div>
						<div class="tip-gift-name left">礼包<#if (link_index>0 || failTipLinkConfig?split(",")[link_index+1]??)>${link_index+1}</#if></div>
						<a href="${link}"><div class="tip-gift-button right">领<#if failTipTextConfig?split(",")[link_index]?? && (failTipTextConfig?split(",")[link_index]?length > 0)>${failTipTextConfig?split(",")[link_index]}<#else>取礼包</#if></div></a>
					</div>
					</#list>
				</div>
				<div class="tip-gift-footer">
					<img src="${cdnUrl}/img/wap/common/failtip/result-gift-footer.png"/>
				</div>
			</div>
		</div>
	</div>
</#macro>
<#macro surveyPopWindow title="恭喜您领取成功" buttonText="提交" buttonFunction="" merchant="pingan">
	<div id="surveyTip" class="tip-area result-tip hide">
		<script type="text/javascript">
			$(function(){
				$('#surveyTipForm').attr('action', getRealPath('/activity{0}/survey/result.html'))
				$('#surveyTipButton').click(function(){
					$('#surveyTipForm').submit();
				});
				$('#surveyTipClose').click(function(){
					$('#surveyTipForm').submit();
				});
				$('.tip-overlay').click(function() {
					if (TipWindow.isShow('#surveyTip')) {
						$('#surveyTipForm').submit();
					}
				});
			});
		</script>
		<input type="hidden" id="hasSurveyTip" value="true"/>
		<div class="tip-window center">
			<div class="tip-content">
				<#-- <span class="tip-icon"><img src="${cdnUrl}/img/tip/${type}.png"/></span> -->
				<div class="tip-title">
					<#if title?? && (title?length > 0)>
					${title}
					<#else>
					<@surveyTitleArea/>
					</#if>
				</div>
				<form action="" method="post" id="surveyTipForm" class="survey-tip-form">
				<#if merchant?? && merchant=="yangguang">
					<@surveyFormYangGuang/>
				<#elseif merchant?? && merchant=="zhongying">
					<@surveyFormZhongYing/>
				<#elseif merchant?? && merchant == "taikang">
					<@surveyFormTaiKang/>
				<#elseif merchant?? && merchant == "taiping">
					<@surveyFormTaiKang/>
				<#else>
				<div id="commonSurveyForm">
					<@surveyForm/>
				</div>
				<div id="surveyFormYangGuang">
				</div>
				<input type="hidden" id="surveyFormYangGuangContent" value='<@surveyFormYangGuang/>'/>
				<div id="surveyFormZhongYing">
				</div>
				<input type="hidden" id="surveyFormZhongYingContent" value='<@surveyFormZhongYing/>'/>
				<div id="surveyFormTaiKang">
				</div>
				<input type="hidden" id="surveyFormTaiKangContent" value='<@surveyFormTaiKang/>'/>
				<div id="surveyFormTaiPing">
				</div>
				<input type="hidden" id="surveyFormTaiPingContent" value='<@surveyFormTaiPing/>'/>
				</#if>
					<div class="tip-button">
						<a href="javascript:;" id="surveyTipButton" name="tipSingleButton" class="tip-single-button center" onclick="${buttonFunction}">
							<div name="tipSingleButton" class="tip-single-button-text">${buttonText}</div>
						</a>
					</div>
					<input type="hidden" id="surveyGorderId" name="gorderId" />
					<input type="hidden" id="surveyGorderKey" name="gorderKey" />
				</form>
			</div>
		</div>
	</div>
</#macro>


<#macro calculateDescription>
	<#-- 测保说明 -->
	<div class="calculator-description clearfix">
		<div class="calculator-description-title">
			<p>加入《鸿运随行返还型意外保障》</p>
			<p>为自己，更为家人</p>
		</div>
		<ul>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-1.png">
				<span>
					<p>全方位意外保障</p>
					<p>意外住院、伤残、身故都能保</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-2.png" class="li-right">
				<span class="span-right">
					<p>0-55周岁都可投保</p>
					<p>无门槛，为自己或家人均可投保</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-3.png">
				<span>
					<p>最高200万赔付</p>
					<p>保障额度高，解决实际问题</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-4.png" class="li-right">
				<span class="span-right">
					<p>住院津贴享300元/日</p>
					<p>意外伤害住院均可保障</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-5.png">
				<span>
					<p>保费返还112%</p>
					<p>满期生存返还112%，还能赚钱</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/cal-icon-6.png" class="li-right">
				<span class="span-right">
					<p>保障周期20年</p>
					<p>保障周期长，让您从容不迫</p>
				</span>
			</li>
		</ul>
	</div>
</#macro>
<#macro resultSuccessDetail>
亲爱的${insurant.name}，恭喜您已成功提交申请<span class="money">${insurance.fullName}</span>。${merchant.fullName}工作人员近期会通过<span class="money">${merchant.telephone}</span>给您致电确认保险生效事宜及提供保险服务，为您提供更全面的保障计划，请注意接听。
</#macro>
<#macro simpleCalculateDescription>
	<#-- 测保说明 -->
	<div class="calculator-description clearfix">
		<ul>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/simple-cal-icon-1.png">
				<span>
					<p>保险周期长</p>
					<p>缴费10年保障20年</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/simple-cal-icon-2.png">
				<span>
					<p>住院日额津贴高</p>
					<p>最高300元/天</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/simple-cal-icon-3.png">
				<span>
					<p>意外保障额度高</p>
					<p>最高200万保障</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingan/common/simple-cal-icon-4.png">
				<span>
					<p>保费返还比例高</p>
					<p>满期生存返还112%</p>
				</span>
			</li>
		</ul>
	</div>
</#macro>
<#macro calculateDescriptionYangGuang>
	<#-- 测保说明 -->
	<div class="calculator-description clearfix">
		<div class="calculator-description-title">
			<p>加入《阳光真心守护保障计划》</p>
			<p>为自己，更为家人</p>
		</div>
		<ul>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-1.png">
				<span>
					<#-- <p>赔付最高1000万</p> -->
					<p>因意外身故最高20倍、1000万基本保额赔付，安心出行再无后顾之忧</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-2.png" class="li-right">
				<span class="span-right">
					<#-- <p>保障最全 全方位呵护</p> -->
					<p>意外人身、交通，航空，自驾等事故致伤残、住院、身故全保障</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-3.png">
				<span>
					<#-- <p>保时间最长 4500天</p> -->
					<p>因意外事故住院，期间享受每天最高250元住院津贴，最长4500天</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-4.png" class="li-right">
				<span class="span-right">
					<p>返还高至 118%</p>
					<p>意外与否均可拿回保费的118%</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-5.png">
				<span>
					<p>年龄周期最大 30天-60周岁</p>
					<p>均可享受长达25年的保险保障</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/yangguang/common/cal-icon-6.png" class="li-right">
				<span class="span-right">
					<p>免费提供投保建议</p>
					<p>24小时在线 , 免费指导投保建议</p>
				</span>
			</li>
		</ul>
	</div>
</#macro>


<#macro calculateResult hasButton="false">
	<div class="calculate-result">
		<div class="calculate-table">
			<div class="calculate-title">
				保费测算结果
			</div>
			<div class="calculate-head">
				<#if payWay=="month">
					<p>您首期需支付三个月保费：<span class="money">￥${calculator.threeMonthPremium}元</span></p>
					<p>以后每月支付：<span class="money">￥${calculator.monthPremium}元</span></p>
					<p>折合每天仅：<span class="money">￥${calculator.dayForMonthPremium}元</span></p>
				<#else>
					<p>您每年需支付保费：<span class="money">￥${calculator.yearPremium}元</span></p>
					<p>折合每天仅：<span class="money">￥${calculator.dayForYearPremium}元</span></p>
				</#if>
			</div>
			<ul class="calculate-content">
				<li>
					<span class="title">您可获得以下保障：</span>
				</li>
				<li>
					意外身故保障：<span class="money">${calculator.insuredSum}元</span>
				</li>
				<li>
					意外伤残保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
				</li>
				<li>
					自驾车意外身故特别保障：<span class="money">${calculator.insuredSum}元</span>
				</li>
				<li>
					自驾车意外伤残特别保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
				</li>
				<li>
					公共交通意外身故特别保障：<span class="money">${calculator.insuredSum}元</span>
				</li>
				<li>
					公共交通意外伤残特别保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
				</li>
				<li>
					意外伤害住院日额津贴：<span class="money">${calculator.dailyAllowance}元/天</span>
				</li>
				<li>
					身故保障：所交保费的<span class="money">112%</span>
				</li>
				<li>
					满期生存保险金：所交保费的<span class="money">112%</span>
				</li>
			</ul>
			<#if hasButton=="true">
				<div class="submit-button-area">
				<@commonFooterButton directDraw="true"/>
				</div>
			</#if>
		</div>
	</div>
</#macro>

<#macro calculateResultYangGuang hasButton="false">
	<div class="calculate-result">
		<div class="calculate-table">
			<div class="calculate-title">
				保费测算结果
			</div>
			<div class="calculate-head">
				<#if payWay=="month">
					<p>您首期需支付三个月保费：<span class="money">￥${calculator.threeMonthPremium}元</span></p>
					<p>以后每月支付：<span class="money">￥${calculator.monthPremium}元</span></p>
					<p>折合每天仅：<span class="money">￥${calculator.dayForMonthPremium}元</span></p>
				<#else>
					<p>您每年需支付保费：<span class="money">￥${calculator.yearPremium}元</span></p>
					<p>折合每天仅：<span class="money">￥${calculator.dayForYearPremium}元</span></p>
				</#if>
			</div>
			<#if hasButton=="true">
				<div class="submit-button-area">
				<@commonFooterButton directDraw="true"/>
				</div>
			</#if>
		</div>
	</div>
</#macro>


<#macro commonFooterButton directDraw="false">
	<#if surveyPromotionTextConfig?? && (surveyPromotionTextConfig?length > 0)>
		<#assign surveyPromotionText="即送${surveyPromotionTextConfig}" />
	</#if>
	<#if (notOriginalMerchant?? && notOriginalMerchant=="true") || (noCalculate?? && noCalculate=="true")>
		<#if surveyPositionConfig=="single" && directDraw == "false">
			<@footerButtonArea buttonId="participantButton" buttonText="参与问卷调查${surveyPromotionText!''}" />
		<#else>
			<#if !(promotionConfig?? && promotionConfig=="no")>
				<@emailPopWindow title="为了进一步了解保单详情，请补充邮箱信息哦"/>
				<@footerButtonArea buttonId="promotionButtonInSurvey" buttonText="立即领取${surveyPromotionTextConfig}" />
			</#if>
		</#if>
	<#else>
		<#if calculatePositionConfig=="single" && directDraw == "false">
			<@footerButtonArea buttonId="participantButton" buttonText="参与保费试算${surveyPromotionText!''}" />
		<#else>
			<#if !(promotionConfig?? && promotionConfig=="no")>
				<@footerButtonArea buttonId="promotionButton" buttonText="立即领取${surveyPromotionTextConfig}"/>
			</#if>
		</#if>
	</#if>
</#macro>

<#macro footerButtonArea buttonId="participantButton" buttonText="">
	<#if isMobilePage="true">
	<div class="footer-button-placeholder"></div>
	<div class="footer-button">
	</#if>
		<div class="submit-button-shadow">
			<div class="submit-button" id="${buttonId}">
				${buttonText}
			</div>
		</div>
	<#if isMobilePage="true">
	</div>
	</#if>
</#macro>
