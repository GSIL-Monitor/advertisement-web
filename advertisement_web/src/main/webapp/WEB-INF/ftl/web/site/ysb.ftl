<#include "../common/core.ftl" />
<#assign title="远山保科技-金融大数据服务领军者">
<@htmlHead title="${title}" description="">
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">
	<@cssFile file=["../js/plugins/bootstrap/css/bootstrap.min.css", "web/site/ysb.css", "../js/plugins/WOW/css/libs/animate.css"] />
	<@jsFile file=["plugins/bootstrap/js/bootstrap.min.js", "plugins/WOW/dist/wow.js"] />
</@htmlHead>
<@calculatePage/>
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
		<img class="logo" src="${cdnUrl}/img/web/site/ysb/ysb-logo@2x.png" alt="">
      </a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right my-list">
        <li><a href="#home" class="text-center">首页</a></li>
        <li><a href="#service" class="text-center">数据与服务</a></li>
        <li><a href="#about" class="text-center">关于我们</a></li>
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
							<div class="title wow slideInRight" data-wow-duration="2s">金融大数据服务领军者</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<div class="subtitle wow slideInLeft" data-wow-duration="2s">专注于为金融机构提供大数据应用服务</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" id="service">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">数据 - 媒体 - 服务</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10 first-field">
			<div class="row">
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s" data-wow-offset="80">
					<div class="list-field">	
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-left@3x.png" alt="">
						<p class="title">大数据挖掘</p>
						<p class="subtitle">帮助企业通过大数据数据挖掘，构建移动互联网的大数据下深度挖掘核心用户，潜在用户的能力</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.4s" data-wow-offset="80">
					<div class="list-field">
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-center@3x.png" alt="">
						<p class="title">媒体服务全案</p>
						<p class="subtitle">整合媒体全案，整合媒体资源<br> 全媒全案服务用户需求</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.6s" data-wow-offset="80">
					<div class="list-field">
						<img class="mouse-in" src="${cdnUrl}/img/web/site/ysb/icon-right@3x.png" alt="">
						<p class="title">大数据战略支持</p>
						<p class="subtitle">基于大数据挖掘系统<br> 为企业制定系统化的数据应用解决方案</p>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-1"></div>
	</div>

	<div class="row">
		<div class="col-xs-12 first-container">
			<div class="row">
				<p class="title wow fadeInUp" data-wow-duration="1s" data-wow-delay="0.2s">数据创造价值</p>	
			</div>
			<div class="row">
				<div class="col-xs-12 col-md-6 col-md-offset-3 wow fadeInDown" data-wow-duration="2s" data-wow-delay="0.6s">
					<p class="subtitle">远山保科技据应用而生，是金融机构在大数据时代的最佳合作伙伴 <br>聚焦金融大数据营销服务，以领先的产品和解决方案，帮助客户提升业务效率，释放数据红利，<br> 与客户一起创造价值，是远山保科技的使命</p>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">价值与使命</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10 second-field">
			<div class="row">
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s">
					<div class="other-field">	
						<img src="${cdnUrl}/img/web/site/ysb/icon-first@3x.png" alt="">
						<p class="title">大数据架构设计</p>
						<p class="subtitle">完善基础设施构建系统体系</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.4s">
					<div class="other-field">
						<img src="${cdnUrl}/img/web/site/ysb/icon-second@3x.png" alt="">
						<p class="title">大数据技术研发</p>
						<p class="subtitle">提供技术能力支撑</p>
					</div>
				</div>
				<div class="col-md-4 col-sm-12 wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.6s">
					<div class="other-field">
						<img src="${cdnUrl}/img/web/site/ysb/icon-three@3x.png" alt="">
						<p class="title">大数据服务创新</p>
						<p class="subtitle">输出创新营销服务能力</p>
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
					<p class="subtitle">远山保科技是一家专注为保险、银行、券商、基金等金融机构提供大数据服务的高科技公司 <br>致力于依托大数据营销技术，打造一个“大数据技术+应用服务”的大数据应用服务体系 <br>为金融机构提供大数据营销产品及解决方案，让大数据成为企业永续成功的红利</p>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12 list-title wow fadeIn" data-wow-duration="2s" data-wow-delay="0.2s">客户与合作伙伴</div>
	</div>
	<div class="row pattern">
		<div class="col-md-1"></div>
		<div class="col-xs-12 col-md-10">
			<div class="row">
				<div class="col-md-offset-1 col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="2s" data-wow-delay="0.2s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-hxbx@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.2s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-zsxn@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.4s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-bnrs@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.6s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-yg@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.8s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-tk@3x.png" alt="">
				</div>
				<div class="col-md-1"></div>
				<div class="row"></div>
				<div class="col-md-1"></div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.3s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-pfyh@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.5s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-xyyh@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.7s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-msyh@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="0.9s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-zgyh@3x.png" alt="">
				</div>
				<div class="col-xs-6 col-md-2 cooperation wow bounceInUp" data-wow-duration="1s" data-wow-delay="1s">
					<img src="${cdnUrl}/img/web/site/ysb/logo-zsyh@3x.png" alt="">
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
						<p><img src="${cdnUrl}/img/web/site/ysb/icon-addr@3x.png" alt="">远山保险 010-84083556</p>
						<p class="addr">地址：北京市东城区东直门南大街华普花园B座2005室</p>
					</div>
					<div>
						<p><img src="${cdnUrl}/img/web/site/ysb/icon-mail@3x.png" alt="">服务邮箱：service@yuanshanbao.com</p>
						<p class="addr">客服 QQ：2013780348</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row footer">
		<div class="row first-line">
			<div class="col-md-offset-1 col-xs-12 col-md-2 text-center">北京远山保科技有限公司</div>
		</div>
		<div class="row second-line">
			<div class="col-md-offset-1 col-md-3 col-xs-12 text-center">广告主客户：ad@yuanshanbao.com</div>
			<div class="col-md-3 col-xs-12 text-center">渠道合作：service@yuanshanbao.com</div>
			<div class="col-md-3 col-xs-12 text-center"><a href="http://www.yuanshanbao.com">网址：http://www.yuanshanbao.com</a></div>
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