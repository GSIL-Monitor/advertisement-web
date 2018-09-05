/* eslint-disable */
(function(){
$(document).ready(function(){
var chances=0;
var Page = (function() {		
	var config = {
			$bookBlock : $( '#bb-bookblock' ),
			$navNext : $( '#bb-nav-next' ),
			$navPrev : $( '#bb-nav-prev' )
		},
		init = function() {
			$('#bb-bookblock').bookblock({
				speed : 500,
				shadowSides : 0.8,
				shadowFlip : 0.7,
				onEndFlip: function(page, isLimit) {
					if(page==0){
						$('#girl1').addClass('leftFly-animation');
						$('#girl2').addClass('rightFly-animation');
						// pageInit();
					}else{
						return false;
					}
					return false;
				},
				onBeforeFlip:function(page){
					$('#girl2').removeClass('rightFly-animation');
					$('#girl1').removeClass('leftFly-animation');
					return false
				}
			});
			initEvents();
		},
		initEvents = function() {
			var $slides = config.$bookBlock.children();
			setTimeout(function(){
				config.$bookBlock.bookblock( 'next' );
				return false
			},2000)
			// add navigation events
			config.$navNext.on( 'click touchstart', function() {
				config.$bookBlock.bookblock( 'next' );
				return false;
			} );
			config.$navPrev.on( 'click touchstart', function() {
				config.$bookBlock.bookblock( 'prev' );
				return false;
			} );
			$slides.on( {
				'swipeleft' : function( event ) {
					config.$bookBlock.bookblock( 'next' );
					return false;
				},
				'swiperight' : function( event ) {
					config.$bookBlock.bookblock( 'prev' );
					return false;
				}
			} );
			// add keyboard events
			$( document ).keydown( function(e) {
				var keyCode = e.keyCode || e.which,
					arrow = {
						left : 37,
						up : 38,
						right : 39,
						down : 40
					};
				switch (keyCode) {
					case arrow.left:
						config.$bookBlock.bookblock( 'prev' );
						e.preventDefault();
						break;
					case arrow.reght:
						config.$bookBlock.bookblock( 'next' );
						e.preventDefault();
						break;
				}
			} );
		};
		return { init : init };
})();

	Page.init();

// $('#cars').on('touchmove', function(event) {
//     event.preventDefault();
// });
// $('#cars').on('swipeup',function(event){
//   $('#cars img:last-child').addClass('leave-animation');
//   setTimeout(function(){
//     if(chances>4){
//       $('#cars img:last-child').remove();
//       $('#cars img:last-child').addClass('loading-animation');
//       $('#cars').append('<img class="car-bg4 loading-animation" src="longdingImg" alt="">');
//     }else if(0<chances<=4){
//       $('#cars img:last-child').remove();
//       $('#cars img:last-child').addClass('loading-animation');
//     }
//   },800)
//   return false;
// })
})
})()
