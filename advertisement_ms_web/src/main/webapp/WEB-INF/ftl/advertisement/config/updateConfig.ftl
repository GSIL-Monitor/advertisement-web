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
      	<input type="hidden" name="${functionId}" value="${itemEdit.configId?c}"/>
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
                    <td style="width:20%;">保险名称：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="insuranceId" class="selectpicker form-control" data-live-search="true">
                       <option value="">默认(空)</option>
                        <#list merchantList as item>
                        <option value="${item.insuranceId?c}" <#if itemEdit.insuranceId == item.insuranceId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>保险公司：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="merchantId" class="selectpicker form-control" data-live-search="true">
                        <option value="">默认(空)</option>
                        <#list merchantList as item>
                        <option value="${item.merchantId?c}" <#if itemEdit.merchantId == item.merchantId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>活动名称：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="activityId" class="selectpicker form-control" data-live-search="true">
                        <option value="">默认(空)</option>
                        <#list activityList as item>
                        <option value="${item.activityId?c}" <#if itemEdit.activityId == item.activityId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>渠道名称：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="channel" class="selectpicker form-control" data-live-search="true">
                       <option value="">默认(空)</option>
                        <#list channelList as item>
                        <option value="${item.key}" <#if itemEdit.key == item.key>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                 <tr>
                    <td>function：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="functionId" class="selectpicker form-control" data-live-search="true">
                        <option value="">默认(空)</option>
                        <#list functionList as item>
                        <option value="${item.functionId?c}" <#if itemEdit.functionId == item.functionId>selected</#if>>${item.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  
                  <tr>
                    <td>action：</td>
                    <td>
                      <input type="text" name="action" style="width:60%;" value="${itemEdit.action}"></td>
                  </tr>
                  
                   <tr>
                    <td rowspan="2">排序等级：</td>
                    <td>
                     <input type="text" name="sort" style="width:60%;" value="${itemEdit.sort}"></td>
                  </tr>
                  
                  <tr>
                  	<td style="color:#FF6347;">请填写数字</td>
                  <tr>
                 
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