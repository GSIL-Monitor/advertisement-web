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
                    <td style="width:20%;">彩种：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="gameId" class="selectpicker form-control">
                        <#list gameList as list>
                        <option value="${list.gameId?c}">${list.shortName}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td style="width:20%;">合作商：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="platformId" class="selectpicker form-control">
                        <#list platformList as list>
                        <option value="${list.platformId?c}">${list.platformName}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                 <tr>
               	    <td>权重：</td>
                    <td>
                      <input type="text" name="weight" style="width:60%;"></td>
                  </tr>
                  
                  <tr>
                    <td>标记：</td>
                    <td>
                      <input type="text" name="remark" style="width:60%;"></td>
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