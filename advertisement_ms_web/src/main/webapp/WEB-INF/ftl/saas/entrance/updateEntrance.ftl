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
      	<input type="hidden" name="${functionId}" value="${itemEdit.entranceId?c}"/>
      	<input type="hidden" name="imgUrl" value="${itemEdit.imgUrl}"/>
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
                    <td>标题：</td>
                    <td>
                      <input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
                  </tr>
                  
                  <tr>
                    <td>提示：</td>
                    <td>
                      <input type="text" name="tips" style="width:60%;" value="${itemEdit.tips}"></td>
                  </tr>
                  
                  <tr>
                    <td>位置类型：</td>
                    <td>
                      <input type="text" name="positionType" style="width:60%;" value="${itemEdit.positionType?c}"></td>
                  </tr>
                  
                 <tr>
                    <td>内容链接：</td>
                    <td>
                      <textarea name="contentUrl" style="width:60%;height:60px;">${itemEdit.contentUrl}</textarea>
                  </tr>             
                 
                 <tr>
                    <td>权重：</td>
                    <td>
                      <input type="text" name="weight" style="width:60%;" value="${itemEdit.weight}"></td>
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
                      <select name="isDel" class="selectpicker form-control">
                       <option value="0" <#if itemEdit.isDel == 0>selected</#if>>上线</option>
                       <option value="1" <#if itemEdit.isDel == 1>selected</#if>>下线</option>
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