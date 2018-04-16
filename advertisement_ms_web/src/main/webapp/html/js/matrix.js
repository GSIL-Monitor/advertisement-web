
$(document).ready(function(){

	
	
	// === Sidebar navigation === //
	
	$('.submenu > a').click(function(e)
	{
		e.preventDefault();
		var submenu = $(this).siblings('ul');
		var li = $(this).parents('li');
		var submenus = $('#sidebar li.submenu ul');
		var submenus_parents = $('#sidebar li.submenu');
		if(li.hasClass('open'))
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				submenu.slideUp();
			} else {
				submenu.fadeOut(250);
			}
			li.removeClass('open');
		} else 
		{
			if(($(window).width() > 768) || ($(window).width() < 479)) {
				submenus.slideUp();			
				submenu.slideDown();
			} else {
				submenus.fadeOut(250);			
				submenu.fadeIn(250);
			}
			submenus_parents.removeClass('open');		
			li.addClass('open');	
		}
	});
	
	var ul = $('#sidebar > ul');
	
	$('#sidebar > a').click(function(e)
	{
		e.preventDefault();
		var sidebar = $('#sidebar');
		if(sidebar.hasClass('open'))
		{
			sidebar.removeClass('open');
			ul.slideUp(250);
		} else 
		{
			sidebar.addClass('open');
			ul.slideDown(250);
		}
	});
	
	// === Resize window related === //
	$(window).resize(function()
	{
		if($(window).width() > 479)
		{
			ul.css({'display':'block'});	
			$('#content-header .btn-group').css({width:'auto'});		
		}
		if($(window).width() < 479)
		{
			ul.css({'display':'none'});
			fix_position();
		}
		if($(window).width() > 768)
		{
			$('#user-nav > ul').css({width:'auto',margin:'0'});
            $('#content-header .btn-group').css({width:'auto'});
		}
	});
	
	if($(window).width() < 468)
	{
		ul.css({'display':'none'});
		fix_position();
	}
	
	if($(window).width() > 479)
	{
	   $('#content-header .btn-group').css({width:'auto'});
		ul.css({'display':'block'});
	}
	
	// === Tooltips === //
	$('.tip').tooltip();	
	$('.tip-left').tooltip({ placement: 'left' });	
	$('.tip-right').tooltip({ placement: 'right' });	
	$('.tip-top').tooltip({ placement: 'top' });	
	$('.tip-bottom').tooltip({ placement: 'bottom' });	
	
	// === Search input typeahead === //
	$('#search input[type=text]').typeahead({
		source: ['Dashboard','Form elements','Common Elements','Validation','Wizard','Buttons','Icons','Interface elements','Support','Calendar','Gallery','Reports','Charts','Graphs','Widgets'],
		items: 4
	});
	
	// === Fixes the position of buttons group in content header and top user navigation === //
	function fix_position()
	{
		var uwidth = $('#user-nav > ul').width();
		$('#user-nav > ul').css({width:uwidth,'margin-left':'-' + uwidth / 2 + 'px'});
        
        var cwidth = $('#content-header .btn-group').width();
        $('#content-header .btn-group').css({width:cwidth,'margin-left':'-' + uwidth / 2 + 'px'});
	}
	
	// === Style switcher === //
	$('#style-switcher i').click(function()
	{
		if($(this).hasClass('open'))
		{
			$(this).parent().animate({marginRight:'-=190'});
			$(this).removeClass('open');
		} else 
		{
			$(this).parent().animate({marginRight:'+=190'});
			$(this).addClass('open');
		}
		$(this).toggleClass('icon-arrow-left');
		$(this).toggleClass('icon-arrow-right');
	});
	
	$('#style-switcher a').click(function()
	{
		var style = $(this).attr('href').replace('#','');
		$('.skin-color').attr('href','css/maruti.'+style+'.css');
		$(this).siblings('a').css({'border-color':'transparent'});
		$(this).css({'border-color':'#aaaaaa'});
	});
	
	$('.lightbox_trigger').click(function(e) {
		
		e.preventDefault();
		
		var image_href = $(this).attr("href");
		
		if ($('#lightbox').length > 0) {
			
			$('#imgbox').html('<img src="' + image_href + '" /><p><i class="icon-remove icon-white"></i></p>');
		   	
			$('#lightbox').slideDown(500);
		}
		
		else { 
			var lightbox = 
			'<div id="lightbox" style="display:none;">' +
				'<div id="imgbox"><img src="' + image_href +'" />' + 
					'<p><i class="icon-remove icon-white"></i></p>' +
				'</div>' +	
			'</div>';
				
			$('body').append(lightbox);
			$('#lightbox').slideDown(500);
		}
		
	});
	

	$('#lightbox').live('click', function() { 
		$('#lightbox').hide(200);
	});
	
	$('#queryReset').click(function(){
		location.reload();
	});

	$('.sorting').click(function(){
		var clickValue = $(this).attr('value');
		$('.sorting').each(function(){
			if (clickValue == $(this).attr('value')) {
				var sort = $(this).attr('sort');
				if (sort == 'asc') {
					$(this).removeClass('sorting_asc');
					$(this).addClass('sorting_desc');
					$(this).attr('sort', 'desc');
				} else {
					$(this).removeClass('sorting_desc');
					$(this).addClass('sorting_asc');
					$(this).attr('sort', 'asc')
				}
				search(null, $(this).attr('value') + ' ' + $(this).attr('sort'));
			} else {
				$(this).attr(sort, '');
				$(this).removeClass('sorting_asc');
				$(this).removeClass('sorting_desc');
			}
		});
	});
//	queryDanmaku();
});

function queryDanmaku() {
	var danmakuList = [];
	var query = function() {
		$.ajax({
			type: 'POST',
			url: $('#absolutePathPrefix').val() + '/admin/danmaku/show.do',
			data: {},
			success:function(data,status){
				if (data.retCode == 200) {
					var danmakuListUl = document.getElementById('danmakuList');
					var liList = danmakuListUl.getElementsByTagName('li');
					// if (danmakuList.length == data.danmakuList.length) {
					// 	for (var i in data.danmakuList) {
					// 		liList[i].innerHTML = data.danmakuList[i].content;
					// 		liList[i + data.danmakuList.length].innerHTML = data.danmakuList[i].content;
					// 	}
					// } else {
					var htmlContent = '';
					for (var i in data.danmakuList) {
						htmlContent += '<li class="danmaku-item">' + data.danmakuList[i].content +'</li>';
					}
					danmakuListUl.innerHTML = '';
					danmakuListUl.innerHTML = htmlContent;
					scroll();
					// }
					danmakuList = data.danmakuList;
				}
			},
			error : function(data) {
				console.log("queryDanmaku error...");
			}
		});
	};
	query();
	setInterval(query, 60000);
}

var initOffsetLeft = 0;
var intervalScroll;
function scroll(length, top) {
	clearInterval(intervalScroll);
	var speed = -1;
	var luckyUsersUl = document.getElementById('danmakuList');
	var lis = luckyUsersUl.getElementsByTagName('li');
	var length = 0;
	for (var i in lis) {
		if (isNumber(lis[i].offsetWidth)) {
			length += lis[i].offsetWidth + 100;
		}
	}
	luckyUsersUl.innerHTML += luckyUsersUl.innerHTML;
	if (initOffsetLeft == 0) {
		initOffsetLeft = luckyUsersUl.offsetLeft;
		if (initOffsetLeft == 0) {
			initOffsetLeft = 1;
		}
	}
	intervalScroll = setInterval(function() {
		if (!isNumber(luckyUsersUl.style.left.replace('px', ''))) {
			luckyUsersUl.style.left = "0";
		}
		luckyUsersUl.style.left = getPix(luckyUsersUl.style.left) + speed +'px';
		if (getPix(luckyUsersUl.style.left) < -luckyUsersUl.offsetWidth / 2) {
			luckyUsersUl.style.left = "0";
		}
		//切换到第一段，需要再增加一个长度，否则会有跳跃感，经测试为length的1/90
		if (getPix(luckyUsersUl.style.left) < -length) {
			luckyUsersUl.style.left = getPix(luckyUsersUl.style.left) + length +'px';
		}
	}, 40);
}

function getPix(length) {
	var str = length.replace('px', '');
	return parseInt(str);
}

