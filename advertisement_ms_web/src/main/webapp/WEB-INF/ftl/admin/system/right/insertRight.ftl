<#include "/common/core.ftl" />
<@htmlHead title="权限管理"/>
<@sideBar />
<div id="content">
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
      <a href="${rc.contextPath}/admin/right/adminRights.do" title="权限管理" class="tip-bottom">权限管理</a>
      <a href="#" class="current">添加权限</a> 
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/right/insertRight.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td>权限名称：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td>描述：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;"></td>
                  </tr>
                  <tr>
						<td>权限URL：</td>
						<td>
							<div style="width:62%;">
								<select name="url" class="selectpicker form-control" data-live-search="true">
									<option value="" selected="selected">请选择</option>
									<#list urls as right>
									<option value="${right.url}">${right.url}</option>
									</#list>
								</select>
							</div>
						</td>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/right/adminRights.do" />
<@footPart />
<@htmlFoot />