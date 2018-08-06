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
      <a href="#" class="current">修改${functionTitle}</a>
    </div>
    <h1>修改${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="${functionId}" value="${itemEdit.productId?c}"/>
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
                    <td width="20%">标题：</td>
                    <td width="80%">
                      <input type="text" name="title" style="width:60%;" <#if itemEdit.title??>value="${itemEdit.title}"</#if>></td>
                  </tr>
              
                  <tr>
                    <td>公司网址：</td>
                    <td>
                      <input type="text" name="companyUrl" style="width:60%;" <#if itemEdit.companyUrl??>value="${itemEdit.companyUrl}"</#if>></td>
                  </tr>
                  
                  <tr>
                    <td>描述（选填）：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;" <#if itemEdit.description??>value="${itemEdit.description}"</#if>></td>
                  </tr>
                  <tr>
                    <td>上课时间：</td>
                    <td>
                      <input type="text" name="schoolTime" style="width:60%;" <#if itemEdit.schoolTime??>value="${itemEdit.schoolTime}"</#if>></td>
                  </tr>
                  <tr>
                    <td>品牌特色：</td>
                    <td>
                      <input type="text" name="brandFeature" style="width:60%;" <#if itemEdit.brandFeature??>value="${itemEdit.brandFeature}"</#if>></td>
                  </tr>
                  <tr>
                    <td>总价值：</td>
                    <td>
                      <input type="text" name="totalAmount" style="width:60%;" <#if itemEdit.totalAmount??>value="${itemEdit.totalAmount}"</#if>></td>
                  </tr>
                  <tr>
                    <td>折扣价：</td>
                    <td>
                      <input type="text" name="discountAmount" style="width:60%;" <#if itemEdit.discountAmount??>value="${itemEdit.discountAmount}"</#if>></td>
                  </tr>
                  <tr>
                    <td>最小年龄：</td>
                    <td>
                      <input type="text" name="minAge" style="width:60%;" <#if itemEdit.minAge??>value="${itemEdit.minAge}"</#if>></td>
                  </tr>
                  <tr>
                    <td>最大年龄：</td>
                    <td>
                      <input type="text" name="maxAge" style="width:60%;" <#if itemEdit.maxAge??>value="${itemEdit.maxAge}"</#if>></td>
                  </tr>
                  
                  <tr>
                    <td>产品logo：</td>
                    <td>
                      <input type="file" name="image" style="width:90%;">
                    </td>
                  </tr>
                  <tr>
                    <td>推荐页logo：</td>
                    <td>
                      <input type="file" name="bigImage" style="width:90%;">
                    </td>
                  </tr>
                  <tr>
                    <td>详情页banner：</td>
                    <td>
                      <input type="file" name="detailImage" style="width:90%;">
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