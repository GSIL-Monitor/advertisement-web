<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="机构管理" class="tip-bottom"><i class="icon-book"></i>机构管理</a>
      <a href="${rc.contextPath}/admin/merchant/list.do" title="机构列表" class="tip-bottom"><i class="icon-book"></i>机构列表</a>
      <a href="#" class="current">修改机构</a>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td style="width:20%;">名字：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  
                <tr>
               	    <td>全名：</td>
                    <td>
                      <input type="text" name="fullName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>简称：</td>
                    <td>
                      <input type="text" name="shortName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>英文名：</td>
                    <td>
                      <input type="text" name="englishName" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>手机号：</td>
                    <td>
                      <input type="text" name="telephone" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>商家接口：</td>
                    <td>
                      <input type="text" name="deliverInterface" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>图片：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;">
                    </td>
                  </tr> 
                  
                 <tr>
                    <td>状态：</td>
                    <td>
                      <div style="width:62%;">
                      <select name="status" class="selectpicker form-control">
                        <#list statusList as status>
                        <option value="${status.key}">${status.value}</option>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />