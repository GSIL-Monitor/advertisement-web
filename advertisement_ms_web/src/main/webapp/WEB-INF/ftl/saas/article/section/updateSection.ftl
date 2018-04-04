<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}">
	<@jsFile file=["article/script.js"] />
</@htmlHead>
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
      <form action="${rc.contextPath}/admin/article/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
      	<input type="hidden" name="sectionId" value="${section.sectionId?c}"/>
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
                    <td style="width:20%;">标题：</td>
                    <td>
                      <input type="text" name="title" style="width:60%;" value="${section.title?default("")}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td style="width:20%;">次标题：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;" value="${section.description?default("")}">
                    </td>
                  </tr>
                  
                  
                  
                    <tr id="complexDesc">
                    <td>
                     	 内容：
                      <input type="hidden" name="content" id="info" value="${section.content?html}"></td>
                    <td colspan="3">
                      <iframe id="iFrameUeditor" src="${rc.contextPath}/admin/article/section/editor.do?sectionId=${section.sectionId?c}" frameborder="0" ></iframe>
                    </td>
                  </tr>
                  
                  <tr id="simpleDesc" style="display: none;">
                    <td style="width:20%;">内容：</td>
                    <td>
                      <textarea id="textareaValue" name="" style="width:60%; height:160px;">${section.content?default("")}</textarea>
                    </td>
                  </tr>                
                  
                  <tr>
                    <td style="width:20%;">按钮文字：</td>
                    <td>
                      <input type="text" name="buttonLabel" style="width:60%;" value="${section.buttonLabel?default("")}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td style="width:20%;">按钮链接：</td>
                    <td>
                      <input type="text" name="buttonLink" style="width:60%;" value="${section.buttonLink?default("")}">
                    </td>
                  </tr>
                   <tr>
                    <td style="width:20%;">排名(填写整数，数越小，越靠前)：</td>
                    <td>
                      <input type="text" name="sort" style="width:60%;" <#if section.sort??>value="${section.sort}"</#if>>
                    </td>
                  </tr>
                        
                  <tr>
                    <td>按钮样式：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="buttonType" class="selectpicker form-control">
                         <#list typeList as status>
                        <option value="${status.key}" <#if section.buttonType==status.key>selected</#if>>${status.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                 <tr>
                    <td>图片：</td>
                    <td>
                      <input type="file" name="image" style="width:60%;">
                    </td>
                  </tr>
                        
                  <tr>
                    <td>版本：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="contentType" class="selectpicker form-control" id="selectOption">
                         <#list descList as status>
                        <option value="${status.key}" <#if section.contentType==status.key>selected</#if>>${status.value}</option>
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
                        <option value="${status.key}" <#if section.status==status.key>selected</#if>>${status.value}</option>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/period/list.do" />
<@footPart />
<script>
$(function(){
	  function swtichInput() {
		   if($selectOption.val() == 1) {
			    $complexDesc.css('display', 'none');
			    $simpleDesc.css('display', 'table-row');
			    $textareaValue.attr('name', 'desc');
		   }
		   else {
			    $complexDesc.css('display', 'table-row');
			    $simpleDesc.css('display', 'none');
			    $iFrameUeditor.attr('src', '${rc.contextPath}/admin/article/section/editor.do?sectionId=${section.sectionId?c}');
			    $textareaValue.attr('name', '');
			    
		   }
	  }
      var $selectOption = $('#selectOption');
      var $complexDesc = $('#complexDesc');
      var $simpleDesc = $('#simpleDesc');
      var $iFrameUeditor = $('#iFrameUeditor');
      var $textareaValue = $('#textareaValue');
      
      swtichInput();
      $selectOption.change(function() {
      	  swtichInput();
      });
});
</script>   
<@htmlFoot />