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
                    <td width="20%">名称：</td>
                    <td width="80%">
                      <input type="text" name="name" style="width:60%;" <#if itemEdit.name??>value="${itemEdit.name}"</#if>></td>
                  </tr>
                  <tr>
                    <td width="20%">标题：</td>
                    <td width="80%">
                      <input type="text" name="title" style="width:60%;" <#if itemEdit.title??>value="${itemEdit.title}"</#if>></td>
                  </tr>
                  <tr>
                    <td width="20%">描述：</td>
                    <td width="80%">
                      <input type="text" name="description" style="width:60%;" <#if itemEdit.description??>value="${itemEdit.description}"</#if>></td>
                  </tr>
                  
                  <tr>
					<td>logo：</td>
					<td>
						<input type="file" name="image" style="width:60%;">
					</td>
				  </tr>
                  <tr>
					<td>详情：</td>
					<td>
						<input type="file" name="bigImage" style="width:60%;">
					</td>
				  </tr>
                  <tr>
					<td width="20%">办卡链接：</td>
					<td width="80%">
						<input type="text" name="detailImageUrl" style="width:60%;" <#if itemEdit.detailImageUrl??>value="${itemEdit.detailImageUrl}"</#if>>
					</td>
				  </tr>
                  <tr>
                    <td width="20%">查询链接：</td>
                    <td width="80%">
                      <input type="text" name="queryUrl" style="width:60%;" <#if itemEdit.queryUrl??>value="${itemEdit.queryUrl}"</#if>></td>
                  </tr>
                  <tr>
                    <td width="20%">优惠活动</td>
                    <td width="80%">
                      <input type="text" name="schoolTime" style="width:60%;" <#if itemEdit.schoolTime??>value="${itemEdit.schoolTime}"</#if>></td> 
                  </tr>
                  <tr>
                    <td width="20%">基本权益</td>
                    <td width="80%">
                      <input type="text" name="brandFeature" style="width:60%;" <#if itemEdit.brandFeature??>value="${itemEdit.brandFeature}"</#if>></td>
                  </tr>
                  <tr>
                    <td width="20%">佣金</td>
                    <td width="80%">
                      <input type="text" name="brokerage" style="width:60%;" <#if itemEdit.brokerage??>value="${itemEdit.brokerage}"</#if>></td>
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