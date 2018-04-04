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
                     ${section.title?default("")}</td>
                  </tr>
                  
                  <tr>
                    <td>描述：</td>
                    <td>${section.description?default("")}</td>
                  </tr>
                  
                  <tr>
                    <td>内容：</td>
                    <td>${section.content?default("")}</td>
                  </tr> 
                          
                  <tr>
                    <td>按钮文字：</td>
                    <td>${section.buttonLabel?default("")}</td>
                  </tr>
                              
                  <tr>
                    <td>按钮链接：</td>
                    <td>${section.buttonLink?default("")}</td>
                  </tr>
                  
                  <tr>
                    <td>按钮样式：</td>
                    <td>${section.typeValue?default("")}</td>
                  </tr>
                  <tr>
                    <td>版本：</td>
                    <td>${section.contentTypeValue?default("")}</td>
                  </tr>
         
                  <tr>
                    <td>图片：</td>
                    <td><image src="${section.imageUrl?default("")}" width="250"></image></td>
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