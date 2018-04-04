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
                    <td style="width:27%;">活动名称：</td>
                    <td>
                     ${itemEdit.name}</td>
                  </tr>
                  
                  <tr>
                    <td>key：</td>
                    <td>${itemEdit.key}</td>
                  </tr> 
                          
                  <tr>
                    <td>入口地址：</td>
                    <td>${itemEdit.entranceUrl}</td>
                  </tr>
                              
                  <tr>
                    <td>bonus：</td>
                    <td>${itemEdit.bonus}</td>
                  </tr>
                  
                  <tr>
                    <td>状态：</td>
                    <td>${itemEdit.statusValue}</td>
                  </tr>
                  
                  <tr>
                    <td>创建时间：</td>
                    <td>${itemEdit.createTimeContent}</td>
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