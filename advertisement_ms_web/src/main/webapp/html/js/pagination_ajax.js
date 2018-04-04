$(function() {

	var All_page, //总页数 
		All_record, //总条数 
		Current_page,//当前页
		Request_page, 
		Current_count, //向服务器请求当前页条数
		Current_items, //向服务器请求当前项目数
		items,
		PageCount = 100; //每页显示的条数

	All_page = Math.ceil(All_record / PageCount);

	function getList(Current_page) {
		$.ajax({
			url: '/',
			type: 'POST',
			data: {
				'Current_page': Current_page,
				'Current_count': ,
				'Current_items',
			},
			dataType: 'JSON',
			success: function(Current_count, Current_items, items) {
				//加载数据列表
				for (var j = 0; j < Current_count; j++) {
					var trs = $('<tr></tr>');
					for (var k = 0; k < Current_items; k++) {
						var tds = $('<td></td>');
						tds.html = items[k];
						trs.append(tds);
					}
					$('#list_tbody').append(trs);
				}
			},
			complete: function() {
				//获取分页列表
				getPagination('#list_ul');
			},
			error: function() {
				alert('数据加载失败，请重试');
			}
		});

	}

	//分页列表函数
	function getPagination(list_ul) {
		for (var i = 0; i < All_page; i++) {
			var li = $('<li></li>');
			li.html = i + 1;
			$(list_ul).append(i + 1, li);
			li.index = i;
			li.on('click', function() {
				Request_page = $(this).index;
				getList(Request_page);
			});
		}
	}

	//加载初始页
	getList(0);

	//加载上一页
	$('#page_pre').click(function() {
		if (Current_page > 0) {
			getList(Current_page - 1);
		} else {
			return false;
		}
	});

	//加载下一页
	$('#page_next').click(function() {
		if (Current_page < All_page) {
			getList(Current_page + 1);
		} else {
			return false;
		}
	});
});