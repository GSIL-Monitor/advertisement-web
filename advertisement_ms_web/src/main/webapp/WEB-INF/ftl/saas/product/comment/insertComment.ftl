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
        <input type="hidden" name="productId" value="${productId}"/>
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
               	    <td style="width:20%;">手机号：</td>
                    <td>
                      <input type="text" name="mobile" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>费率满意度(1-5星，填写数字)：</td>
                    <td>
                      <input type="text" name="rateSatisfaction" style="width:60%;"></td>
                  </tr>
                  
                  
                   <tr>
                    <td>贷款满意度(1-5星，填写数字)：</td>
                    <td>
                      <input type="text" name="advertisementSatisfaction" style="width:60%;"></td>
                  </tr>
                  
                   <tr>
                    <td>流程满意度(1-5星，填写数字)：</td>
                    <td>
                      <input type="text" name="processSatisfaction" style="width:60%;"></td>
                  </tr> 
                  
                  <tr>
                    <td>内容：</td>
                    <td>
                      <textarea name="content" style="width:60%;height:50px;"></textarea></td>
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
<script>
	$(function() {
		timer('#effect');
		timer('#effects');
	});
</script>
<@htmlFoot />