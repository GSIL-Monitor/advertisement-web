<#include "../common/core.ftl" />
<@htmlHead title="${companyName}" description="">
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">
	<@cssFile file=["../js/plugins/bootstrap/css/bootstrap.min.css", "web/site/ysb.css", "../js/plugins/WOW/css/libs/animate.css"] />
	<@jsFile file=["plugins/bootstrap/js/bootstrap.min.js", "plugins/WOW/dist/wow.js"] />
</@htmlHead>
<style >
	html{
		font-size: 100px;
	}
</style>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container navbar-top">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand brand" href="#">
		<img class="logo" style="width: 0.35rem;height: 0.52rem;display: inline-block;vertical-align: bottom;" src="${cdnUrl}/img/web/site/myzt/myzt-logo@2x.png" alt="">
		<span style="color: #FFA200;font-size: 0.3rem;vertical-align: super;">${companyName}</span>
      </a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right my-list">
        <li><a href="#home" class="text-center">首页</a></li>
        <li><a href="#service" class="text-center">平台概览</a></li>
        <li><a href="#about" class="text-center">广告资源</a></li>
        <li><a href="#contact" class="text-center">联系我们</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid" id="home">
	<div class="row">
		<div class="col-xs-12 title-box">
			<div class="row">
				<div class="col-xs-offset-2 col-xs-10 title-container">
					<div class="row">
						<div class="col-xs-12">
							<div class="title wow slideInRight" data-wow-duration="2s">大数据驱动的AI投放平台</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="subtitle wow slideInLeft" data-wow-duration="2s">高效实现您的营销目标</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" id="service">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">更合理、透明的费用管理模式 <br>帮您平衡成本和收益</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10 first-field">
			<div class="row">
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s" data-wow-offset="80">
					<div class="list-field">	
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-left@3x.png" alt="">
						<p class="title">免费开户</p>
						<p class="subtitle">免费开户，充值门槛低，广告定向精准 能以较低的成本看到最终的收益</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.4s" data-wow-offset="80">
					<div class="list-field">
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-center@3x.png" alt="">
						<p class="title">成本可控</p>
						<p class="subtitle">您可以为广告账户或者计划设置日预算<br>随时编辑预算或停止投放广告<br>我们的系统会立即为您执行</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.6s" data-wow-offset="80">
					<div class="list-field">
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-right@3x.png" alt="">
						<p class="title">多种计费模式</p>
						<p class="subtitle">根据不同推广目标提供更为合理的计费模式<br>包括CPC、CPM和CPA。为您提供效果自动优化<br>持续提高广告的效率和产出</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 first-container">
			<div class="row">
				<p class="title wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.2s">多样的广告资源 覆盖1.5亿优质用户</p>	
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-6 col-md-offset-3 wow fadeInDown" data-wow-duration="2s" data-wow-delay="0.6s">
					<p class="subtitle">每天有超过1.5亿用户通过支付宝，微信，ofo，抖音等，观看到${companyName}的广告； <br>内容涉及金融类、教育类、母婴育儿、电商类等。我们帮助您与这些受众建立联系，<br> 将优质商业信息推送给真正需要的人。</p>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">释放一方数据价值 提升营销效果</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10 second-field">
			<div class="row">
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s">
					<div class="other-field">	
						<img src="${cdnUrl}/img/web/site/ysb/icon-first@3x.png" alt="">
						<p class="title">依托${companyName}丰富数据基础</p>
						<p class="subtitle">广告主实现自身数据增值</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.4s">
					<div class="other-field">
						<img src="${cdnUrl}/img/web/site/ysb/icon-second@3x.png" alt="">
						<p class="title">利用自身强大技术能力</p>
						<p class="subtitle">安全、专业、开放</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.6s">
					<div class="other-field">
						<img src="${cdnUrl}/img/web/site/ysb/icon-three@3x.png" alt="">
						<p class="title">促进数据增值与流通</p>
						<p class="subtitle">受众管理、洞察挖掘、相似人群拓展</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
	</div>

	<div class="row" id="about">
		<div class="col-xs-12 second-container">
			<div class="row">
				<p class="title wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.2s">关于我们</p>
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-6 col-md-offset-3 wow fadeInDown" data-wow-duration="1s" data-wow-delay="0.2s">
					<p class="subtitle">${companyName}是一个汇集多资源主流媒体和优质广告的DSP平台， <br>覆盖行业广泛、平台流量大、用户质量高，同时通过大数据分析能力，对广告和媒体实现精准营销和智能管理，<br> 提供智能、高效、精准的服务</p>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">合作媒体</div>
	</div>
	<div class="row pattern">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10">
			<div class="row">
				<div class="col-md-offset-1 col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s">
					<img src="${cdnUrl}/img/web/site/myzt/zhifubao@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.2s">
					<img src="${cdnUrl}/img/web/site/myzt/weixin@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.4s">
					<img src="${cdnUrl}/img/web/site/myzt/ofo@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.6s">
					<img src="${cdnUrl}/img/web/site/myzt/douyin@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.8s">
					<img src="${cdnUrl}/img/web/site/myzt/kuaishou@3x.png" alt="">
				</div>
				<div class="col-md-1"></div>
				<div class="row"></div>
				<div class="col-md-1"></div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.3s">
					<img src="${cdnUrl}/img/web/site/myzt/didi@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.5s">
					<img src="${cdnUrl}/img/web/site/myzt/meituan@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.7s">
					<img src="${cdnUrl}/img/web/site/myzt/wps@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.9s">
					<img src="${cdnUrl}/img/web/site/myzt/aiqiyi@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="1s">
					<img src="${cdnUrl}/img/web/site/myzt/jinri@3x.png" alt="">
				</div>
				<div class="col-md-1"></div>
				<div class="row"></div>
				<div class="col-md-1"></div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.3s">
					<img src="${cdnUrl}/img/web/site/myzt/qutoutiao@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.5s">
					<img src="${cdnUrl}/img/web/site/myzt/wangyi@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.7s">
					<img src="${cdnUrl}/img/web/site/myzt/baidu@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.9s">
					<img src="${cdnUrl}/img/web/site/myzt/uc@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="1s">
					<img src="${cdnUrl}/img/web/site/myzt/fenghuang@3x.png" alt="">
				</div>
				<div class="col-md-1"></div>
			</div>
		</div>
		<div class="col-md-1"></div>
	</div>
	
	<div id="contact" class="row contact-us">
		<div class="inner-contact">
			<div class="row">
				<div class="col-xs-10 col-xs-offset-1">
					<div class="footer-title">联系我们</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-5 col-md-offset-1">
					<div class="row contact-form">
						<div class="col-md-6 contact-input">
							<input type="text" class="form-control" id="name" placeholder="姓名">
						</div>
						<div class="col-md-6 contact-input-right">
							<input type="text" class="form-control" id="mobile" placeholder="手机号">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<textarea name="" id="content" rows="5" placeholder="*内容不能为空" class="form-control"></textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<button class="btn btn-block submit-btn" id="submit">立即留言</button>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-md-5">
					<div>
						<p><img src="${cdnUrl}/img/web/site/ysb/icon-addr@3x.png" alt="">${companyName}： 010-64010062 </p>
						<p class="addr">地址：北京市丰台区双营路9号亿达丽泽中心708A</p>
					</div>
					<div>
						<p><img src="${cdnUrl}/img/web/site/ysb/icon-mail@3x.png" alt="">服务邮箱：service@mayi-ai.com</p>
						<p class="addr">客服 QQ：2013780348</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row footer">
		<div class="row first-line">
			<div class="col-md-offset-1 col-xs-12 col-md-2 text-center">${companyName}</div>
		</div>
		<div class="row second-line">
			<div class="col-md-3 col-xs-12 text-center"><a href="http://www.mayi-ai.com">网址：http://www.mayi-ai.com</a></div>
			<div class="col-md-3 col-xs-12 text-center">渠道合作：service@mayi-ai.com</div>
			<div class="col-md-2 col-xs-12 text-center"><@beianNumber /></div>
		</div>
	</div>
</div>


<div class="modal fade" tabindex="-1" id="modal">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">提示</h4>
      </div>
      <div class="modal-body">
        <p>您的留言提交成功！</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
	var wow = new WOW({
	    boxClass: 'wow',
	    animateClass: 'animated',
	    offset: 0,
	    mobile: false,
	    live: true
	});
	wow.init();
	$('#submit').on('click', function() {
		if($('#mobile').val() && $('#name').val() && $('#content').val()) {
			$.ajax({
				url:'/common/feedback/commit.html',
				type: 'POST',
				data: {
					name: $('#name').val(),
					mobile: $('#mobile').val(),
					message: $('#content').val()
				},
				success:function(res) {
					$('#modal').modal('toggle');
				}
			})
		}
	})
</script>

<@htmlFoot/>