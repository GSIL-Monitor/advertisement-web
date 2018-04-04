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
                    <td style="width:20%;">id：</td>
                    <td>${itemEdit.tagsTypeId?c}</td>
                  </tr> 
                
                
                <tr>
                    <td>名称：</td>
                    <td>${itemEdit.typeName}</td>
                  </tr>
                
                  <tr>
                    <td>描述：</td>
                    <td>${itemEdit.typeDescription}</td>
                  </tr>
           
                  <tr>
                    <td>父类：</td>
                    <td>${itemEdit.parentId?c}</td>
                  </tr>
                  
                   <tr>
                    <td>等级：</td>
                    <td>${itemEdit.level?c}</td>
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