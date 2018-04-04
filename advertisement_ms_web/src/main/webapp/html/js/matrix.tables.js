var dataTableConfig = initConfig();
var dataTableConfig2 = initConfig();
var dataTable = null;
function initConfig() {
	var result = {
			"oLanguage" : { // 汉化
				"sProcessing" : "正在加载数据...",
				"sLengthMenu" : "显示_MENU_条 ",
				"sZeroRecords" : "没有您要搜索的内容",
				"sInfo" : "从_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",
				"sInfoEmpty" : "记录数为0",
				"sInfoFiltered" : "(全部记录数 _MAX_  条)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索",
				"sUrl" : "",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : " 上一页 ",
					"sNext" : " 下一页 ",
					"sLast" : " 末页 ",
					"sGo" : "跳转",
					"sRefresh" : "刷新",
				}
			},
			"bProcessing" : true,
			"bServerSide" : true,
			"sDom" : '<""l>t<"F"fp>',
			"bJQueryUI" : true,
			"bLengthChange" : false,
			"bAutoWidth": false,
			"bFilter" : false,
			"bSort" : false,
			"iDisplayStart" : 0,
			"iDisplayLength" : 10,
			"sPaginationType" : "full_numbers"
		};
	return result;
}

function initDisplayStart() {
	var index = parseInt($('#pageIndex').val());
	if ( index !== undefined && index > 0 ) {
		start = index * dataTableConfig.iDisplayLength;
		dataTableConfig.iDisplayStart = start;
	}
}

$.fn.dataTableExt.sErrMode = function(settings, tn, msg){
		console.log(msg);
	};
$(document).ready(function(){
	$('#dataTable').on('preXhr.dt', function ( e, settings, processing ) {
		if (!isLoading()) {
			showLoading();
		}
	});
	$('#dataTable').on( 'xhr.dt', function () {
		hideLoading();
		setTimeout(function(){
			$('.dataTables_wrapper .btn').each(function(){
				var text = $(this).text();
				if (text.length > 3) {
					$(this).attr('style', 'width: ' + text.length * 0.2 + 'rem;');
				}
			});
		}, 100);
	});
});