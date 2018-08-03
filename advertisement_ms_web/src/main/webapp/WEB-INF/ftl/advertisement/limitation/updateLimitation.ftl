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
      	<input type="hidden" name="${functionId}" value="${itemEdit.limitationId?c}"/>
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
                    <td style="width:20%;">商家：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="merchantId" class="selectpicker form-control" data-live-search="true">
                        <#list merchantList as merchant>
                        <option value="${merchant.merchantId?c}" <#if itemEdit.merchantId == merchant.merchantId>selected</#if>>${merchant.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td style="width:20%;">保险：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="insuranceId" class="selectpicker form-control" data-live-search="true">
                      	<option value="">默认(空)</option>
                        <#list insuranceList as insurance>
                        <option value="${insurance.insuranceId?c}" <#if itemEdit.insuranceId == insurance.insuranceId>selected</#if>>${insurance.fullName}【保险id:${insurance.insuranceId}】【公司:${insurance.merchant.name}】【供应商:${insurance.description}】</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>类型：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="type" class="selectpicker form-control" data-live-search="true">
                        <#list typeList as type>
                        <option value="${type.key}" <#if itemEdit.type == type.key>selected</#if>>${type.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
            
                  <tr>
                    <td>channel：</td>
                    <td>
                      <input type="text" name="channel" style="width:60%;" value="${itemEdit.channel}"></td>
                  </tr>
                 
                  <tr>
                    <td rowspan="2">省市（locationCode）：</td>
                    <td>
                      <input type="text" name="locationCode" style="width:60%;" value="${itemEdit.locationCode}"></td>
                  </tr>
                  
                   <tr>
                  	<td style="color:#FF6347;">请填写数字，非中文省会名称</td>
                  </tr>
                 
                  <tr>
                    <td rowspan="2">quota：</td>
                    <td>
                      <input type="text" name="quota" style="width:60%;" value="${itemEdit.quota?c}"></td>
                  </tr>
                  
                   <tr>
                  	<td style="color:#FF6347;">请填写数字</td>
                  </tr>
                  
                  <tr>
                    <td rowspan="2">库存：</td>
                    <td>
                      <input type="text" name="stock" style="width:60%;" value="${itemEdit.stock?c}"></td>
                  </tr>
                            
                  <tr>
                  	<td style="color:#FF6347;">请填写数字</td>
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