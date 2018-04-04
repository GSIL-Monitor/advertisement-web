<#include "/common/core.ftl" />
<@htmlHead title="修改密码"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="修改密码" class="tip-bottom"><i class="icon-book"></i>修改密码</a> 
    </div>
    <h1>修改密码</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/updateUserPassword.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <span class="icon"> <i class="icon-th"></i>
              </span>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered table-striped" id="">
                <tbody>
                  <tr>
                    <td>新密码：</td>
                    <td>
                      <input type="password" name="password" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td>新密码确认：</td>
                    <td>
                      <input type="password" name="confirmPassword" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td colspan="4" style="text-align:center">
                      <input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/main.do" />
<@footPart />
<@htmlFoot />