<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />

<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="#" class="current">${functionTitle}详情</a>
    </div>
    
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
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
                    <td style="width:27%;">标题：</td>
                    <td>
                     ${article.title}</td>
                  </tr>
                  
                  <tr>
                    <td>描述：</td>
                    <td>${article.description}</td>
                  </tr>
                  
                  <tr>
                    <td>内容：</td>
                    <td>${article.content}</td>
                  </tr>
                  
                  <tr>
                    <td>宣传页链接设置（可不填）：</td>
                    <td>${article.linkValue}</td>
                  </tr>  
                          
                  <tr>
                    <td>按钮文字：</td>
                    <td>${article.buttonLabel}</td>
                  </tr>
                              
                  <tr>
                    <td>按钮链接：</td>
                    <td>${article.buttonLink}</td>
                  </tr>
                  
                  <tr>
                    <td>按钮样式：</td>
                    <td>${article.typeValue}</td>
                  </tr>
                  
                  <tr>
                    <td>微信分享标题：</td>
                    <td>${article.weixinShareTitle}</td>
                  </tr>
                  
                   <tr>
                    <td>微信分享描述：</td>
                    <td>${article.weixinShareDesc}</td>
                  </tr>
         
        		  <tr>
                    <td>按钮样式：</td>
                    <td>${article.contentType}</td>
                  </tr>
                  
                   <tr>
                    <td>类型：</td>
                    <td>${article.categoryValue?default("")}</td>
                  </tr>
         
                  <tr>
                    <td>图片：</td>
                    <td>
                    	<#if article.imageUrl??><image src="${article.imageUrl}" width="250"></image>
                    	</#if>
                    </td>
                  </tr>
                  
                   <tr>
                    <td>微信图片：</td>
                    <td>
                    	<#if article.weixinUrl??>
                    	<image src="${article.weixinUrl}" width="250"></image>
                    	</#if>
                    </td>
                  </tr>
                  
              
                   <tr>
                    <td>状态：</td>
                    <td>${article.statusValue}</td>
                  </tr>
                  
                </tbody>
              </table>
            </div>
          </div>
        </div>

    </div>
  </div>
</div>

<@footPart />
<@htmlFoot />