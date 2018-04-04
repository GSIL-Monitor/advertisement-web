<#include "/common/core.ftl" />
<@htmlHead title="栏目管理"/>
<@topHeaderMenu />
<@sideBar />
<div id="content">
<@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
      <a href="${rc.contextPath}/admin/menucategory/adminMenuCategorys.do" title="系统管理" class="tip-bottom">栏目管理</a>
      <a href="#" class="current">新增栏目</a>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/menucategory/insertMenuCategory.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td>栏目名称：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  <tr>
                    <td>描述：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;"></td>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/menucategory/adminMenuCategorys.do" />
<@footPart />
<@htmlFoot />