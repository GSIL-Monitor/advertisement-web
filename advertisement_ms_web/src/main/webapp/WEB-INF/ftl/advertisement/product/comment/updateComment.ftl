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
      	<input type="hidden" name="${functionId}" value="${itemEdit.commentId?c}"/>
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
                    <td style="width:20%;">产品：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="productId" class="selectpicker form-control" data-live-search="true">
                      	<option value="">默认(空)</option>
                        <#list productList as product>
                        <option value="${product.productId?c}" <#if itemEdit.productId == product.productId>selected</#if>>${product.name}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                 
                  <tr>
                    <td>手机号：</td>
                    <td>
                      <input type="text" name="mobile" style="width:60%;" value="${itemEdit.user.mobile}"></td>
                  </tr>
                 
                 <tr>
                    <td>费率满意度：</td>
                    <td>
                      <input type="text" name="rateSatisfaction" style="width:60%;" value="${itemEdit.rateSatisfaction?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>贷款满意度：</td>
                    <td>
                      <input type="text" name="advertisementSatisfaction" style="width:60%;" value="${itemEdit.advertisementSatisfaction?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>流程满意度：</td>
                    <td>
                      <input type="text" name="processSatisfaction" style="width:60%;" value="${itemEdit.processSatisfaction?c}"></td>
                  </tr>
                  
                  <tr>
                    <td>内容：</td>
                    <td>
                      <textarea name="content" style="width:60%;">${itemEdit.content}</textarea></td>
                  </tr>
                  
                  <tr>
                    <td>申请状态：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="status" class="selectpicker form-control">
                        <#list applyStatusList as status>
                        <option value="${status.key}" <#if itemEdit.applyStatus == status.key>selected</#if>>${status.value}</option>
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