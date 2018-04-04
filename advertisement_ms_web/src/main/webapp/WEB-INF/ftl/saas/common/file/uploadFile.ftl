<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
  <div id="content-header">
    <div id="breadcrumb">
      <a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
        ${functionTitle}管理
      </a>
      <a href="${rc.contextPath}/admin/${functionName}/uploadFileWindow.do" class="current">${functionTitle}管理</a>
    </div>
    <h1>${functionTitle}</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
      <form id="uploadForm" action="" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
                    <td>上传文件：</td>
                    <td>
                      <input id="file" type="file" name="file" style="width:60%;" ></td>
                  </tr>
                 
                  <tr>
                    <td colspan="4" style="text-align:center">
                      <input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" onclick="getMessage('${rc.contextPath}/admin/${functionName}/upload.do');"></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

 <!--微信链接弹窗 不具备通用性，暂时不放在宏内-->
	<div id="overlay"></div>
	<div class="tip" id="pop-tip">
		<div id="succ-tip">
			<span>
				<img src="${cdnUrl}/img/iconfont-duigou.png"></span>
			<h1>上传成功!</h1>
		</div>
		<form id="memOverlayForm">
			<div>
				<span>当前链接 :</span>
				<textarea readonly="readonly" id="needCopyUrl"></textarea>
			</div>
			<div class="tip-pop">
				<input type="button" value="复制" id="copyUrl" class="btn-hover">
				<input type="button" value="关 闭" id="close-mem"  class="btn-hover">
		 	</div>
		</form>
	</div>

<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/uploadFileWindow.do" />
<@footPart />
<@htmlFoot />