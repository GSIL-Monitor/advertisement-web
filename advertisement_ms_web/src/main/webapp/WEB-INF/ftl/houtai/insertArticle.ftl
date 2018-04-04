<#include "core.ftl" />
<@htmlHead />
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="网站内容管理" class="tip-bottom"> <i class="icon-book"></i>
        网站内容管理
      </a>
      <a href="#" class="current">中国金融博物馆文章管理</a>
    </div>
    <h1>添加文章</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form action="" method="post" name="">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <span class="icon"> <i class="icon-th"></i>
              </span>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered table-striped" id="insertArticleTable">
                <tbody>
                  <tr>
                    <td>目录：</td>
                    <td>测试测测试测试测试测试试测试测试</td>
                    <td>标题：</td>
                    <td>测试测试测试测试测试测试测试测试</td>
                  </tr>
                  <tr>
                    <td>地点(出版社)：</td>
                    <td></td>
                    <td>时间：</td>
                    <td></td>
                  </tr>
                  <tr>
                    <td>作者：</td>
                    <td></td>
                    <td>直接链接：</td>
                    <td></td>
                  </tr>
                  <tr>
                    <td>图片标题：</td>
                    <td></td>
                    <td>内容大图：</td>
                    <td></td>
                  </tr>
                  <tr>
                    <td colspan="2">摘要：</td>
                    <td colspan="2"></td>
                  </tr>
                  <tr>
                    <td colspan="2">内容：</td>
                    <td colspan="2">
                      <iframe src="${rc.contextPath}/resources/ueditor/index.html" frameborder="0" width="80%" height="400px"></iframe>
                    </td>
                  </tr>
                  <tr>
                    <td>批量图片：</td>
                    <td>
                      <input type="file" name="picsFile" id="onloadPicFile"></span>
                  </td>
                  <td>
                    <input type="text" name="picTitle" placeholder="请输入图片标题(必填)" id="onloadPicTitle"></td>
                  <td>
                    <input type="submit" name="" value="上传" id="onloadButton" onclick="uploadByForm('picsFile','#onloadPicFile');"></td>
                </tr>
                <tr>
                  <td colspan="4" style="text-align:center">
                    <input type="submit" name="" value="确定" class="houtai-submit btn-red" style="width: 100px;border: 0;" onclick="zForm();"></td>
                </tr>
                <tr class="picGroupTr">
                  <td>
                    <img src="./html/img/demo/av2.jpg"></td>
                  <td>111111111111111111111111111</td>
                  <td>
                    <a href="javascript:;">修改</a>
                  </td>
                  <td>
                    <a href="javascript:;">删除</a>
                  </td>
                </tr>
                <tr class="picGroupTr">
                  <td>
                    <img src="./html/img/demo/demo-image3.jpg"></td>
                  <td>111111111111111111111111111</td>
                  <td>
                    <a href="javascript:;">修改</a>
                  </td>
                  <td>
                    <a href="javascript:;">删除</a>
                  </td>
                </tr>
                <tr class="picGroupTr">
                  <td>
                    <img src="./html/img/demo/av2.jpg"></td>
                  <td>111111111111111111111111111</td>
                  <td>
                    <a href="javascript:;">修改</a>
                  </td>
                  <td>
                    <a href="javascript:;">删除</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div id="tiantu-tip" class="span1">
        <table class="table table-bordered table-striped" id="insertArticleTable">
          <tbody>
            <tr>
              <td colspan="2">
                <input type="file" name="picsFile" id="onloadPicFile2"></td>
            </tr>
            <tr>
              <td colspan="2">
                <input type="text" name="picTitle" placeholder="请输入图片标题(必填)" id="onloadPicTitle2"></td>
            </tr>
            <tr>
              <td>
                <input class="btn btn-green" type="submit" value="上传" name="" onclick="uploadByForm('picsFile','#onloadPicFile2');" id="upLoadPicBtn"></td>
                <td>
                <input class="btn btn-yellow" type="button" value="取消" id="tipCancel"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </div>
</div>
</div>
<@footPart />
<@htmlFoot />