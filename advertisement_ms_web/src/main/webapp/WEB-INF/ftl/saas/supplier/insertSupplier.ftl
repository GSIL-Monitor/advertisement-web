<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
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
                    <td>名称：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>           
                  
                  <tr>
                    <td>key：</td>
                    <td>
                      <input type="text" name="key" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>秘钥：</td>
                    <td>
                      <input type="text" name="encryptKey" style="width:60%;"></td>
                  </tr>
                  
                   <tr>
                    <td>描述（选填）：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;"></td>
                  </tr>
                 
                 
                  <tr>
                    <td>状态：</td>
                    <td>
                      <div style="width:60%;">
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