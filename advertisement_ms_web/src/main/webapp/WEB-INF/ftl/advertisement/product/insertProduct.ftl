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
    <h1>添加${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
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
                    <td width="20%">产品名称：</td>
                    <td width="80%">
                      <input type="text" name="name" style="width:60%;"></td>
                  </tr>
                  <tr>
                  
                    <td>所属活动：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="activityId" class="selectpicker form-control">
                        <#list activityList as activity>
                        	<option value="${activity.activityId}">${activity.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td width="20%">标题：</td>
                    <td width="80%">
                      <input type="text" name="title" style="width:60%;"></td>
                  </tr>           
                  <tr>
                    <td>描述文案：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;"></td>
                  </tr>
           
                  <tr>
                    <td>logo：</td>
                    <td>
                      <input type="file" name="image" style="width:90%;">
                    </td>
                  </tr>
                  <tr>
                    <td>详情页logo：</td>
                    <td>
                      <input type="file" name="bigImage" style="width:90%;">
                    </td>
                  </tr>
                  <tr>
                    <td>办卡链接：</td>
                    <td>
                      <input type="text" name="detailImageUrl" style="width:90%;">
                    </td>
                  </tr>
          		  <tr>
                    <td>查询链接：</td>
                    <td>
                      <input type="text" name="stock" style="width:90%;">
                    </td>
                  </tr>
                  <tr>
                    <td>优惠活动：</td>
                    <td>
                      <input type="text" name="brandFeature" style="width:90%;">
                    </td>
                  </tr>
                   <tr>
                    <td>基本权益：</td>
                    <td>
                      <input type="text" name="schoolTime" style="width:90%;">
                    </td>
                  </tr>
                    <tr>
                    <td>佣金：</td>
                    <td>
                      <input type="text" name="brokerage" style="width:90%;">
                    </td>
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