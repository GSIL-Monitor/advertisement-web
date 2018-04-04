<#include "core.ftl" />
<@htmlHead title="后台管理系统">
    <@cssFile file=["matrix-login.css"] />
</@htmlHead>
<div id="loginbox">
    <form id="loginform" class="form-vertical" action="">
        <div class="control-group normal_text">
            <h3>金融博物馆后台管理系统</h3>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on bg_lg"> <i class="icon-user"></i>
                    </span>
                    <input type="text" id="username" name="username" placeholder="用户名" />
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on bg_ly"> <i class="icon-lock"></i>
                    </span>
                    <input type="password" id="password" name="password" placeholder="密码" />
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <div class="main_input_box">
                    <span class="add-on bg_lg"> <i class="icon-lock"></i>
                    </span>
                    <input type="password" id="password" name="otppassword" placeholder="动态密码" />
                </div>
            </div>
        </div>
        <div class="form-actions" >
            <span class="pull-left">
                <a href="#" class="flip-link btn btn-cyan" id="to-recover">忘记密码?</a>
            </span>
            <span class="pull-right">
                <a type="submit" href="http://localhost/freemarker/dev.html?file=/houtai/welcome" class="btn btn-green" >登录</a>
            </span>
        </div>
    </form>
    <form id="recoverform" action="#" class="form-vertical">
        <p class="normal_text">在下面输入邮箱，然后你会收到一封关于如何找回密码的邮件</p>

        <div class="controls">
            <div class="main_input_box">
                <span class="add-on bg_lo">
                    <i class="icon-envelope"></i>
                </span>
                <input type="text" placeholder="邮箱" />
            </div>
        </div>

        <div class="form-actions">
            <span class="pull-left">
                <a href="#" class="flip-link btn btn-green" id="to-login">&laquo; 返回登录</a>
            </span>
            <span class="pull-right">
                <a class="btn btn-cyan">找回密码</a>
            </span>
        </div>
    </form>
</div>
<@htmlFoot />