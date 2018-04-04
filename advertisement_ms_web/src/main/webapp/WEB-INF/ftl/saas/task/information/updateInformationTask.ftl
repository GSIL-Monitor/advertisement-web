<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@sideBar />
<div id="content">
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">修改${functionTitle}</a>
    </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.informationTaskId?c}"/>
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
                    <td style="width:20%;">项目：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="projectId" class="selectpicker form-control" data-live-search="true">
                        <#list projectList as item>
                        <option value="${item.projectId?c}" <#if itemEdit.projectId == item.projectId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>  
                    
                 <tr>
                    <td>部门：</td>
                    <td>
                      <div>
                      <select name="departmentId" class="selectpicker form-control" data-live-search="true">
                        <#list departmentList as item>
                        <option value="${item.departmentId?c}" <#if itemEdit.departmentId == item.departmentId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  
                  <tr>
                    <td>商家：</td>
                    <td>
                      <div>
                      <select name="supplierKey" class="selectpicker form-control" data-live-search="true">
                        <#list supplierList as item>
                        <option value="${item.key}" <#if itemEdit.supplierKey == item.key>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>                 
              
                 <tr>
                    <td>名称：</td>
                    <td>
                      <input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
                  </tr>
                  
                  <tr>
                    <td>内容：</td>
                    <td>
                      <input type="text" name="content" style="width:60%;" value="${itemEdit.content}"></td>
                  </tr>                 
                  
                  <tr>
                    <td>文件：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;">
                    </td>
                  </tr>
                  
                  <tr>
                    <td>类型：</td>
                    <td>
                     <div style="width:60%;">
                      <select name="type" class="selectpicker form-control">
                        <#list typeList as item>
                        <option value="${item.key}" <#if itemEdit.type == item.key>selected</#if>>${item.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
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
                      <input type="submit" name="" value="提交" class=" btn btn-success" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
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