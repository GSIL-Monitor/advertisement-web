document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';

var deviceWidth = document.documentElement.clientWidth;

if(deviceWidth > 750) deviceWidth = 750;

document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';

$(function (){
	// var rotateTimeOut = function (){
	// 	$('#prizeBg').rotate({
	// 		angle: 0,
	// 		animateTo: 2160,
	// 		duration: 8000,
	// 		callback: function (){
	// 			alert('网络超时，请检查您的网络设置！');
	// 		}
	// 	});
	// };
	var bRotate = false;

	var rotateFn = function (angles, prizeName){
		bRotate = !bRotate;
		$('#prizeBg').stopRotate();
		$('#prizeBg').rotate({
			angle: 0,
			animateTo: angles + 3600,
			duration: 7000,
			callback:function (){
				TipWindow.showTip('#prizeTip');
				bRotate = !bRotate;
			}
		})
	};

	var prizeArrays = [
		{
			index: 0,
			angles: 331,
			name: 'iPhone7 plus',
			key: 'iphone'
		},
		{
			index: 1,
			angles: 29,
			name: '神秘礼包',
			key: 'shenmilibao'
		},
		{
			index: 2,
			angles: 91,
			name: '300元话费',
			key: 'huafei'
		},
		{
			index: 3,
			angles: 149,
			name: '万达IMAX电影票',
			key: 'dianyingquan'
		},
		{
			index: 4,
			angles: 211,
			name: '谢谢参与',
			key: 'xiexie'
		},
		{
			index: 5,
			angles: 269,
			name: '100元滴滴红包',
			key: 'didi'
		}];

	$('#startBtn').click(function (){

		if(bRotate)
			return;

		$.ajax({
			type: 'POST',
			url: '/m/activity/pingan/zhuanpan/luck.html',
			dataType: 'json',
			success: function(data) {
				rotateFn(data.angles, data.name);
				$('#prizeName').text(data.name);
				$('#prizeImage').attr('src', data.imageUrl);
				$('#prizeTip').click(function(){
					location.href = data.link;
				});
			}
		});
		
				// rotateFn(0, 331, 'iPhone7 plus');
			
				
				// $('#awardReceived').css("background", 'url(' + url + ') no-repeat center center / 100% 100%');

			
				// rotateFn(2, 91, '300元话费');
			
				// rotateFn(3, 149, '万达IMAX电影票');
				
				// rotateFn(4, 211, '谢谢参与');
				
				// rotateFn(5, 269, '100元滴滴红包');
	});

	paomadeng();
	scroll();

	$('#readRule').click(function () {
        TipWindow.showTip('#ruleTip');
    });
    $('#ruleBtn').click(function() {
        TipWindow.hide('#ruleTip');
    });
    $('#closeBtn').click(function() {
        TipWindow.hide('#ruleTip');
    });
    $('#prizeBtn').click(function() {
        TipWindow.showTip('#prizeTip');
    });

});

function paomadeng() {
	var $advertisement = $('#paomadeng'),
    url0 = '/html/img/wap/activity/zhuanpan/paomadeng0.png',
    url1 = '/html/img/wap/activity/zhuanpan/paomadeng1.png',
    on = true;
    setInterval(function() {
        if(on) {
            $advertisement.css('background', 'url(' + url0 + ') no-repeat center center / 100% 100%');
            on = false;
        } else {
           $advertisement.css('background', 'url(' + url1 + ') no-repeat center center / 100% 100%');
            on = true;
        }
    }, 1000);
}

function scroll(length, top) {
	var speed = -1;
	var luckyUsersUl = document.getElementById('luckyUsersUl');
	var lis = luckyUsersUl.getElementsByTagName('li');
	luckyUsersUl.innerHTML += luckyUsersUl.innerHTML;
	luckyUsersUl.style.height = lis.length * 35 + 'px';

	setInterval(function() {
		luckyUsersUl.style.top = luckyUsersUl.offsetTop + speed +'px';
		if (luckyUsersUl.offsetTop < -luckyUsersUl.offsetHeight / 2) {
			luckyUsersUl.style.top = "0";
		} else if (luckyUsersUl.offsetTop > 0) {
			luckyUsersUl.style.top = -luckyUsersUl.offsetHeight / 2 + "px";
		}
	}, 40);
}