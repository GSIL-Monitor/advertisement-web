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
                    <td style="width:20%;">名称：</td>
                    <td>${itemEdit.beanName}</td>
                 </tr> 
                 
                 <tr>
                    <td>方法：</td>
                    <td>${itemEdit.beanMethod}</td>
                  </tr> 
                
                  <tr>
                    <td>运行模式：</td>
                    <td>${itemEdit.executeMode}</td>
                  </tr>
                               
                  <tr>
                    <td>cron：</td>
                    <td>${itemEdit.cron}</td>
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