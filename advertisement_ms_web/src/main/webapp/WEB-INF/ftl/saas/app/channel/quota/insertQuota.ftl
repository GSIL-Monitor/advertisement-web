<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
    </div>
    <h1>添加活动-渠道-${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
        <input type="hidden" name="activityId" value="${activityId}"/>
        <input type="hidden" name="channel" value="${channel}"/>
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
                        <option value="" selected>默认(空)</option>
                        <#list insuranceList as list>
                        <option value="${list.insuranceId?c}">${list.fullName}【保险id:${list.insuranceId}】【公司:${list.merchant.name}】【供应商:${list.description}】</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                 <tr>
               	    <td rowspan="2";>省市（locationCode）：</td>
                    <td>
                      <input type="text" name="locationCode" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                  	<td style="color:#FF6347;">请填写数字，非中文省会名称</td>
                  </tr>
                  
                  <tr>
                    <td rowspan="2">排名：</td>
                    <td>
                      <input type="text" name="sort" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                  	<td style="color:#FF6347;">请填写数字</td>
                  </tr>
                  
                  <tr>
                    <td>描述：</td>
                    <td>
                      <textarea name="description" style="width:60%;height:100px;"></textarea></td>
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