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
      <form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe" id="ueditorForm">
      	<input type="hidden" name="articleId" value="${article.articleId?c}"/>
      	<input type="hidden" name="weixinUrl" <#if article.weixinUrl??>value="${article.weixinUrl}"</#if> />
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
                      <input type="text" name="title" style="width:60%;" value="${article.title}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td>次标题：</td>
                    <td>
                      <input type="text" name="description" style="width:60%;">
                    </td>
                  </tr>
                   
                   <tr id="complexDesc">
                    <td>
                     	 内容：
                      <input type="hidden" name="content" id="info" value="${article.content?html}"></td>
                    <td colspan="3">
                      <iframe id="iFrameUeditor" src="${rc.contextPath}/admin/article/editor.do?articleId=${article.articleId?c}" frameborder="0" ></iframe>
                    </td>
                  </tr>
                  
                 <tr id="simpleDesc" style="display: none;">
                    <td style="width:20%;">内容：</td>
                    <td>
                      <textarea id="textareaValue" name="" style="width:60%; height:160px;">${article.content}</textarea>
                    </td>
                  </tr>

	              <tr>
                    <td>宣传页链接设置（可不填）：</td>
                    <td>
                      <input type="text" name="linkValue" style="width:60%;" value="${article.linkValue}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td>按钮文字：</td>
                    <td>
                      <input type="text" name="buttonLabel" style="width:60%;" value="${article.buttonLabel}">
                    </td>
                  </tr>
                  
                  <tr>
                    <td>按钮链接：</td>
                    <td>
                      <input type="text" name="buttonLink" style="width:60%;" value="${article.buttonLink}">
                    </td>
                  </tr>
                        
                  <tr>
                    <td>按钮样式：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="buttonType" class="selectpicker form-control">
                         <#list typeList as status>
                        <option value="${status.key}" <#if article.buttonType==status.key>selected</#if>>${status.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                   <tr>
                    <td>微信分享标题：</td>
                    <td>
                      <input type="text" name="weixinShareTitle" style="width:60%;" value="${article.weixinShareTitle}">
                    </td>
                  </tr> 
                  
                  <tr>
                    <td>微信分享描述：</td>
                    <td>
                      <input type="text" name="weixinShareDesc" style="width:60%;" value="${article.weixinShareDesc}">
                    </td>
                  </tr>
                  
                 <tr>
                    <td>图片：</td>
                    <td>
                      <input type="file" name="image" style="width:60%;">
                    </td>
                  </tr>
                  
                   <tr>
                    <td rowspan="2">微信分享图：</td>
                    <td>
                      <input type="file" name="weixin" style="width:60%;">
                    </td>
                  </tr>
                  
                  <tr>
                  	<td style="color:red;">1.微信的宽高标准为：400*400、2.上传图片太大会导致系统错误，请注意图片大小、3.请上传标准的图片</td>
                  </tr>  
                   
                   <tr>
                    <td>版本：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="contentType" class="selectpicker form-control" id="selectOption">
                         <#list descList as status>
                        <option value="${status.key}" <#if article.contentType==status.key>selected</#if>>${status.value}</option>
                        </#list>
                      </select>
                      </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td>类型：</td>
                    <td>
                      <div style="width:60%;">
                      <select name="category" class="selectpicker form-control" id="selectOption">
                         <#list categorysList as category>
                        <option value="${category.key}" <#if article.category==category.key>selected</#if>>${category.value}</option>
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
                        <option value="${status.key}" <#if article.status==status.key>selected</#if>>${status.value}</option>
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
			    $iFrameUeditor.attr('src', '${rc.contextPath}/admin/article/editor.do?articleId=${article.articleId?c}');
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