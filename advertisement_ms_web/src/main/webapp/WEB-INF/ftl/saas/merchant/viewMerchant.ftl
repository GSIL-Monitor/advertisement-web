<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />

<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="机构管理" class="tip-bottom"><i class="icon-book"></i>机构管理</a>
        <a href="${rc.contextPath}/admin/merchant/list.do" title="机构列表" class="tip-bottom"><i class="icon-book"></i>机构列表</a>
        <a href="#" class="current">机构详情</a>
      </div>
    
  </div>
  <div class="container-fluid">
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
                    <td>${itemEdit.name}</td>
                  </tr> 
                
                  <tr>
                    <td>全称：</td>
                    <td>${itemEdit.fullName}</td>
                  </tr>
                  
                  <tr>
                    <td>简称：</td>
                    <td>${itemEdit.shortName}</td>
                  </tr>
                  
                  <tr>
                    <td>英文名：</td>
                    <td>${itemEdit.englishName}</td>
                  </tr>
                  
                  <tr>
                    <td>手机号：</td>
                    <td>${itemEdit.telephone}</td>
                  </tr>
                  
                   <tr>
                    <td>商家接口：</td>
                    <td>${itemEdit.deliverInterface}</td>
                  </tr>
                  
                  <tr>
                    <td>图片：</td>
                    <td>
                    	<#if itemEdit.imageUrl??>
                    	<image src="${itemEdit.imageUrl}" width="250"></image>
                    	</#if>
                    </td>
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