<#include "core.ftl" />
<@htmlHead />
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="网站内容管理" class="tip-bottom"><i class="icon-book"></i> 网站内容管理</a> <a href="#" class="current">中国金融博物馆文章管理</a> </div>
    <h1>中国金融博物馆文章管理</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <span style="float:right;margin:3px 8px 0 0"><a href="http://localhost/freemarker/dev.html?file=/houtai/insertArticle"><button class="btn btn-yellow">添加文章</button></a></span>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th>测试</th>
                  <th></th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>测试dfgdg</td>
                  <td></td>
                  <td></td>
                  <td></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
<@footPart />
<@htmlFoot />