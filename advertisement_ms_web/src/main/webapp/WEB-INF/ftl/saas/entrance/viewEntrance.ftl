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
                    <td style="width:20%;">标题：</td>
                    <td>${itemEdit.title}</td>
                 </tr> 
                 
                 <tr>
                    <td>提示：</td>
                    <td>${itemEdit.tips}</td>
                  </tr> 
                
                  <tr>
                    <td>位置类型：</td>
                    <td>${itemEdit.positionType}</td>
                  </tr>
                               
                  <tr>
                    <td>json内容：</td>
                    <td>${itemEdit.contentUrl}</td>
                  </tr>
                  
                  <tr>
                    <td>权重：</td>
                    <td>${itemEdit.weight}</td>
                  </tr>
                  
                  <tr>
                    <td>图片：</td>
                    <td>
                   	<#if itemEdit.imgUrl??>
                     <image src="${itemEdit.imgUrl}" width="200">
                     </image>
                    </#if>
                    </td>
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