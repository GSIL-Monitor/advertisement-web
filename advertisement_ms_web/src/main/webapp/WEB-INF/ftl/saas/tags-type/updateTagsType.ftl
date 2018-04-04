<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">添加${functionTitle}</a>
    </div>
    <h1>修改${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.typeId?c}"/>
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
                    <td style="width:20%;">父类：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="parentId" class="selectpicker form-control">
                      	<option value="">默认</option>
                      	<#list typeList as type>
                        <option value="${type.typeId}" <#if itemEdit.parentId == type.typeId>selected</#if>>${type.typeDescription}</option>
                        </#list>
                      </select>
                      </div>
                  </tr>
                  <tr>
                    <td>名称：</td>
                    <td>
                      <input type="text" name="typeName" style="width:60%;" value="${itemEdit.typeName}"></td>
                  </tr>
                  
                   <tr>
                    <td>描述：</td>
                    <td>
                      <input type="text" name="typeDescription" style="width:60%;" value="${itemEdit.typeDescription}"></td>
                  </tr>
                  
                  
                   <tr>
                    <td>等级：</td>
                    <td>
                      <input type="text" name="level" style="width:60%;" value="${itemEdit.level}"></td>
                  </tr>
                  
              
                  <tr>
                    <td>状态：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="status" class="selectpicker form-control">
                        <#list statusList as status>
                        <option value="${status.key}" <#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
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