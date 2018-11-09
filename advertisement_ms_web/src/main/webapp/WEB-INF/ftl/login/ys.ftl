<#include "../common/core.ftl" />
<@htmlHead title="后台管理系统">
    <@cssFile file=["login/bainian-login.css"] />
</@htmlHead>
<div class="login-container">
    <div class="header">
        <div class="logo"></div>
    </div>
    <div class="loginbox">
        <div class="main-bg"></div>
        <div class="title"></div>
        <form id="loginform" class="login-form" action="${rc.contextPath}/login.do" method="POST">
            <p class="welcome">欢迎使用互动广告平台</p>
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
</div>
<@htmlFoot />