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
                    <td style="width:20%;">英文名：</td>
                    <td>${itemEdit.platformEn}</td>
                 </tr> 
                 
                 <tr>
                    <td>名字：</td>
                    <td>${itemEdit.platformName}</td>
                  </tr> 
                
                  <tr>
                    <td>系统在合作商平台id：</td>
                    <td>${itemEdit.platformCode}</td>
                  </tr>
                               
                  <tr>
                    <td>系统在合作商平台的密钥：</td>
                    <td>${itemEdit.platformPwd}</td>
                  </tr>
                  
                 <tr>
                    <td>最大查询数量：</td>
                    <td>${itemEdit.queryNum}</td>
                  </tr>
                  
                  <tr>
                    <td>最大投注数量：</td>
                    <td>${itemEdit.betNum}</td>
                  </tr>
                  
                  <tr>
                    <td>状态：</td>
                    <td>${itemEdit.statusValue}</td>
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