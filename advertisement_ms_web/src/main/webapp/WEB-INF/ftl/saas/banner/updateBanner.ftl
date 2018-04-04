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
      	<input type="hidden" name="${functionId}" value="${itemEdit.bannerId?c}"/>
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
                      <input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
                  </tr>
                 
                  <tr>
                    <td>活动类型：</td>
                    <td>
                      <input type="text" name="actionType" style="width:60%;" value="${itemEdit.actionType}"></td>
                  </tr>
                 
                 <tr>
                    <td>详细链接：</td>
                    <td>
                      <input type="text" name="detailUrl" style="width:60%;" value="${itemEdit.detailUrl}"></td>
                  </tr>
                  
                  <tr>
                    <td>开始时间：</td>
                    <td>
                      <input type="text" id="effects" name="bannerStartTime" style="width:60%;" value="${itemEdit.startTime}">
                   </td>
                  </tr>
                  
                  <tr>
                    <td>结束时间：</td>
                    <td>
                      <input type="text" id="effect" name="bannerEndTime" style="width:60%;" value="${itemEdit.endTime}">
                   </td>
                  </tr>
                  
                  <tr>
                    <td>权重：</td>
                    <td>
                      <input type="text" name="weight" style="width:60%;" value="${itemEdit.weight?c}"></td>
                  </tr>
                  
                   <tr>
                    <td>图片：</td>
                    <td>
                      <input type="file" name="file" style="width:60%;"></td>
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
<script>
	$(function() {
		timer('#effect');
		timer('#effects');
	});
</script>
<@htmlFoot />