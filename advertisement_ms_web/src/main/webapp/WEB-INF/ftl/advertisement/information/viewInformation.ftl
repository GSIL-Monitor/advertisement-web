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
                    <td>${itemEdit.name}</td>
                  </tr> 
                
                  <tr>
                    <td>商家：</td>
                    <td>${itemEdit.genderValue}</td>
                  </tr>
                  
                  <tr>
                    <td>身份证号：</td>
                    <td>${itemEdit.identityCard}</td>
                  </tr>
                  
                  <tr>
                    <td>最高学历：</td>
                    <td>${itemEdit.educationValue}</td>
                  </tr>
                  
                  <tr>
                    <td>居住城市：</td>
                    <td>${itemEdit.location}</td>
                  </tr>
                  
                  <tr>
                    <td> 居住详细地址：</td>
                    <td>${itemEdit.address}</td>
                  </tr>
                  
                  <tr>
                    <td>身份类型：</td>
                    <td>${itemEdit.identityTypeValue}</td>
                  </tr>
                  
                   <tr>
                    <td>公司名称：</td>
                    <td>${itemEdit.companyName}</td>
                  </tr>
                  
                   <tr>
                    <td>公司所在城市：</td>
                    <td>${itemEdit.companyLocation}</td>
                  </tr>
                  
                     <tr>
                    <td>公司详细地址：</td>
                    <td>${itemEdit.companyAddress}</td>
                  </tr>
                  
                     <tr>
                    <td>公司类型：</td>
                    <td>${itemEdit.companyType}</td>
                  </tr>
                  
                  <tr>
                    <td>岗位：</td>
                    <td>${itemEdit.position}</td>
                  </tr>
                  
                     <tr>
                    <td>学校名称：</td>
                    <td>${itemEdit.schoolName}</td>
                  </tr>
                  
                     <tr>
                    <td>学制：</td>
                    <td>${itemEdit.educationYears}</td>
                  </tr>
                  
                     <tr>
                    <td>工作收入，月：</td>
                    <td>${itemEdit.salary}</td>
                  </tr>
                  
                     <tr>
                    <td>房产信息：</td>
                    <td>${itemEdit.realtyInfoValue}</td>
                  </tr>
                  
                   <tr>
                    <td>车产信息：</td>
                    <td>${itemEdit.carInfoValue}</td>
                  </tr>
                  
                   <tr>
                    <td>信用卡额度，月额度：</td>
                    <td>${itemEdit.creditLimit}</td>
                  </tr>
                  
                   <tr>
                    <td>贷款用途：</td>
                    <td>${itemEdit.advertisementPurposeValue}</td>
                  </tr>
                  
                   <tr>
                    <td>户口类型：</td>
                    <td>${itemEdit.residenceTypeValue}</td>
                  </tr>
                  
                   <tr>
                    <td>婚姻情况：</td>
                    <td>${itemEdit.marriageValue}</td>
                  </tr>
                  
                   <tr>
                    <td>有无子女：</td>
                    <td>${itemEdit.hasChildValue}</td>
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