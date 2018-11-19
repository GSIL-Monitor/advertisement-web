<#include "../common/core.ftl" />
<@htmlHead title="后台管理系统">
    <@cssFile file=["login/bainian-login.css"] />
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width">
    <@cssFile file=["../js/plugins/bootstrap/css/bootstrap.min.css", "../js/plugins/WOW/css/libs/animate.css"] />
    <@jsFile file=["plugins/jquery.min.js","plugins/bootstrap/js/bootstrap.min.js", "plugins/WOW/dist/wow.js"] />
    <style>
        .loginbox {
            right: 300px;
        }
    </style>
</@htmlHead>

<nav class="navbar navbar-fixed-top" style="background:transparent;">
  <div class="container navbar-top">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand brand" href="#">
        <img class="logo" src="${cdnUrl}/img/login/bainian/logo@2x.png" alt="">
      </a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right my-list"><#-- 
        <li><a href="#home" class="text-center">首页</a></li>
        <li><a href="#service" class="text-center">数据与服务</a></li>
        <li><a href="#about" class="text-center">关于我们</a></li>
        <li><a href="#contact" class="text-center">联系我们</a></li> -->
      </ul>
    </div>
  </div>
</nav>
<div class="container-fluid" style="height: 100%;">
    <div class="row" style="height: 100%;">
        <div class="col-xs-12 title-box">
            <div class="container" style="width: 980px;">
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-10 title-container">
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
    </div>
</div>
<div class="loginbox">
    <form id="loginform" class="login-form" action="${rc.contextPath}/login.do" method="POST">
        <p class="welcome">欢迎使用蚂蚁智投广告平台</p>
        <div class="control-group">
            <i class="user"></i>
            <input type="text" id="username" name="username" placeholder="用户名" />
            <#if msg??>
            <div class="error">${msg}</div>
            </#if>
        </div>
        <div class="control-group">
            <i class="lock"></i>
            <input type="password" id="password" name="password" placeholder="密码" />
        </div>
        <button type="submit" class="submit"></button>
        <p class="tuijian">推荐浏览器：谷歌</p>
    </form>
</div>

<script>
    var wow = new WOW({
        boxClass: 'wow',
        animateClass: 'animated',
        offset: 0,
        mobile: false,
        live: true
    });
    wow.init();
</script>
<@htmlFoot />