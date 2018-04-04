<#include "/common/core.ftl" />
<@htmlHead title="用户管理"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
      <a href="${rc.contextPath}/admin/user/adminUsers.do" title="系统管理" class="tip-bottom">用户管理</a>
      <a href="#" class="current">修改用户</a>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/user/updateUser.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td style="width:30%;">用户名：</td>
                    <td>
                      <input type="text" name="username" value="${userEdit.username}" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td>真实姓名：</td>
                    <td>
                      <input type="text" name="name" value="${userEdit.name}" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td>邮箱地址：</td>
                    <td>
                      <input type="text" name="email" value="${userEdit.email}" style="width:60%;"></td>
                  </tr>

                  <tr>
                    <td>所属部门：</td>
                    <td>
                      <input type="text" name="department" value="${userEdit.department}" style="width:60%;"></td>
                  </tr>
                   <tr>
                    <td>公司id（选填）：</td>
                    <td>
                      <input type="text" name="bindCompanyId" style="width:60%;" <#if userEdit.bindCompanyId??>value="${userEdit.bindCompanyId}"</#if>></td>
                  </tr>
                  <tr>
                    <td>社区小号id（以，分隔）选填：</td>
                    <td>
                      <input type="text" name="bind_user_ids" style="width:60%;" <#if userEdit.bind_user_ids??>value="${userEdit.bind_user_ids}"</#if>></td>
                  </tr>
                  <tr>
                    <td>密码有效期：</td>
                    <td>
                      <input type="text" name="pwd_keep_time" value="${userEdit.pwd_keep_time?c}" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td colspan="4" style="text-align:center">
                      <input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();">
                    </td>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/user/adminUsers.do" />
<@footPart />
<@htmlFoot />