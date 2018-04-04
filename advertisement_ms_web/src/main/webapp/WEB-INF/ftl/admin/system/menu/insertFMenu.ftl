<#include "/common/core.ftl" />
<@htmlHead title="添加功能菜单"/>
<@sideBar />
<div id="content">
<@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
      <a href="${rc.contextPath}/admin/menu/adminMenus.do" title="菜单管理" class="tip-bottom">菜单管理</a>
      <a href="#" class="current">添加功能菜单</a>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/menu/insertMenu.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td>菜单名：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  <tr>
        						<td>对应权限：</td>
        						<td>
        							<div style="width:62%;">
        								<select name="right_id" style="width:60%" class="selectpicker form-control" data-live-search="true">
        									<option value="" selected="selected">请选择</option>
        									<#list rights as right>
        									<option value="${right.id}">${right.name}</option>
        									</#list>
        								</select>
        							</div>
        						</td>
        					</tr>
                  <tr>
                    <td>所属栏目：</td>
                    <td>
                      ${categoryName}</td>
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
        <input type="hidden" name="parent_id" value="${parentId!""}" />
        <input type="hidden" name="category_id" value="${categoryId!""}" />
      </form>
    </div>
  </div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/menu/adminMenus.do" />
<@footPart />
<@htmlFoot />