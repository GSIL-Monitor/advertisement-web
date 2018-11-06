<#include "../../common/core.ftl" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>瑞金恒邦</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${cdnUrl}/css/web/site/ysb.css">

    <link rel="stylesheet" href="${cdnUrl}/css/web/site/rjbbx/style.css">
    <#-- <link rel="stylesheet" href="/html/js/plugins/bootstrap/css/bootstrap.min.css" /> -->

    <link type="text/css" href="${cdnUrl}/css/web/site/rjbbx/lrtk.css" rel="stylesheet">

    <style>
        .lanrenzhijia a {
            color: #000;
        }

        .lanrenzhijia a:hover,
        .lanrenzhijia a:focus {
            color: #000;
            text-decoration: none;
            cursor: default;
        }
        .content a:hover {
            text-decoration: none;
        }
    </style>
    <script type="text/javascript" src="${cdnUrl}/js/web/site/rjbbx/terminator2.2.min.js" async="true"></script>
    <script type="text/javascript" src="/html/js/plugins/jquery.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/web/site/rjbbx/koala.min.1.5.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/web/site/rjbbx/loca_domain.js"></script>
    <script type="text/javascript" src="/html/js/plugins/bootstrap/js/bootstrap.min.js"></script>

    <!---意外险-->
    <script type="text/javascript">
        function switchmodTag() {}

        switchmodTag.prototype = {
            st: function (menus, divs, openClass, closeClass) {
                var _this = this;
                if (menus.length != divs.length) {
                    alert("菜单层数量和内容层数量不一样!");
                    return false;
                }
                for (var i = 0; i < menus.length; i++) {
                    _this.$(menus[i]).value = i;
                    _this.$(menus[i]).onmouseover = function () { //如果想把效果变成点击切换，将此行onmouseover 改成onclick即可。

                        for (var j = 0; j < menus.length; j++) {
                            _this.$(menus[j]).className = closeClass;
                            _this.$(divs[j]).style.display = "none";
                        }
                        _this.$(menus[this.value]).className = openClass;
                        _this.$(divs[this.value]).style.display = "block";
                    }
                }
            },
            $: function (oid) {
                if (typeof (oid) == "string")
                    return document.getElementById(oid);
                return oid;
            }
        }
        window.onload = function () {
            var STmodel = new switchmodTag();
            STmodel.st(["a_1", "a_2", "a_3"], ["c1_1", "c1_2", "c1_3"], "st01", "st02"); //第一组动滑轮
            STmodel.st(["b_1", "b_2", "b_3"], ["c2_1", "c2_2", "c2_3"], "st01", "st02"); //第二组动滑轮
            STmodel.st(["c_1", "c_2", "c_3"], ["c3_1", "c3_2", "c3_3"], "st01", "st02"); //第三组动滑轮
            STmodel.st(["d_1", "d_2", "d_3"], ["c4_1", "c4_2", "c4_3"], "st01", "st02"); //第四组动滑轮
            //如需增加滑动门个数，请复制代码，ID命名规则如上即可。
        }
    </script>
    <script>
        $(function () {
            $(".lanrenzhijia ul li .rsp").hide();
            $(".lanrenzhijia ul li").hover(function () {
                    $(this).find(".rsp").stop().fadeTo(500, 0.5)
                    $(this).find(".text").stop().animate({
                        left: '0'
                    }, {
                        duration: 500
                    })
                },
                function () {
                    $(this).find(".rsp").stop().fadeTo(500, 0)
                    $(this).find(".text").stop().animate({
                        left: '300'
                    }, {
                        duration: "fast"
                    })
                    $(this).find(".text").animate({
                        left: '-300'
                    }, {
                        duration: 0
                    })
                });
        });
    </script>

    <!--长期寿险-->
    <script type="text/javascript" src="${cdnUrl}/js/web/site/rjbbx/jquery-1.7.2.min.js"></script>
    <script>
        $(function () {
            var move = -50;
            var zoom = 1.5;
            $(".item4").each(function () {
                var that = this
                $(that).bind({
                    mouseenter: function () {
                        item4Timer = setTimeout(function () {
                            width = $(that).width() * zoom;
                            height = $(that).height() * zoom;
                            $(that).find('img').animate({
                                'width': width,
                                'height': height,
                                'top': move,
                                'left': move
                            }, 500);
                            $(that).find('div.caption').fadeIn(500);
                            $(that).find('.item4-txt').fadeOut(500);
                        }, 200);
                    },
                    mouseleave: function () {
                        clearTimeout(item4Timer);
                        $(that).find('img').animate({
                            'width': $(that).width(),
                            'height': $(that).height(),
                            'top': '0',
                            'left': '0'
                        }, 500);
                        $(that).find('div.caption').fadeOut(500);
                        $(that).find('.item4-txt').fadeIn(500);
                    }
                });
            })
        });
    </script>
</head>

<body>
    <!--header-->
    <div class="header">
        <div class="content">
            <a href="javascript:;">
                <img src="${cdnUrl}/img/web/site/rjbbx/rj_img1.jpg">
            </a>
            <div class="search">
                <a href="javascript:;" onclick="setmodel(1, $(this));" style="outline:medium none;" hidefocus="true" class="on"></a>
                <a href="javascript:;" onclick="setmodel(3, $(this));" style="outline:medium none;" hidefocus="true"></a>
                <a href="javascript:;" onclick="setmodel(2, $(this));" style="outline:medium none;" hidefocus="true"></a>
                <a href="javascript:;" onclick="setmodel(52, $(this));" style="outline:medium none;" hidefocus="true"></a>

                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>

    <!--banner-->
    <div id="fsD1" class="focus">
        <div id="D1pic1" class="fPic">

            <div class="fcon" style="display: none;">
                <a href="javascript:;">
                    <img src="${cdnUrl}/img/web/site/rjbbx/20161102114313931.jpg" style="opacity: 0.55;">
                </a>
                <span class="shadow">
                    <a href="javascript:;"></a>
                </span>
            </div>
            <div class="fcon" style="display: none;">
                <a href="javascript:;">
                    <img src="${cdnUrl}/img/web/site/rjbbx/20161102114502525.jpg" style="opacity: 1;">
                </a>
                <span class="shadow">
                    <a href="javascript:;"></a>
                </span>
            </div>
            <div class="fcon" style="display: block;">
                <a href="javascript:;">
                    <img src="${cdnUrl}/img/web/site/rjbbx/20161102114357369.jpg" style="opacity: 1;">
                </a>
                <span class="shadow">
                    <a href="javascript:;"></a>
                </span>
            </div>
            <div class="fcon" style="display: none;">
                <a href="javascript:;">
                    <img src="${cdnUrl}/img/web/site/rjbbx/20161102114431631.jpg" style="opacity: 0.49;">
                </a>
                <span class="shadow">
                    <a href="javascript:;"></a>
                </span>
            </div>




        </div>
        <div class="fbg">
            <div class="D1fBt" id="D1fBt">

                <a href="javascript:void(0)" hidefocus="true" target="_self" class="">
                    <i>1</i>
                </a>
                <a href="javascript:void(0)" hidefocus="true" target="_self" class="">
                    <i>2</i>
                </a>
                <a href="javascript:void(0)" hidefocus="true" target="_self" class="current">
                    <i>3</i>
                </a>
                <a href="javascript:void(0)" hidefocus="true" target="_self" class="">
                    <i>4</i>
                </a>
            </div>
        </div>

    </div>
    <script type="text/javascript">
        Qfast.add('widgets', {
            path: "http://www.rjhbinsure.com/statics/js/ruijin/terminator2.2.min.js",
            type: "js",
            requires: ['fx']
        });
        Qfast(false, 'widgets', function () {
            K.tabs({
                id: 'fsD1', //焦点图包裹id  
                conId: "D1pic1", //** 大图域包裹id  
                tabId: "D1fBt",
                tabTn: "a",
                conCn: '.fcon', //** 大图域配置class       
                auto: 1, //自动播放 1或0
                effect: 'fade', //效果配置
                eType: 'click', //** 鼠标事件
                pageBt: true, //是否有按钮切换页码
                bns: ['.prev', '.next'], //** 前后按钮配置class                          
                interval: 5000 //** 停顿时间  
            })
        })
    </script>

    <!--nav-->
    <div class="subNav">
        <div class="filter_con" style="width: 223px; background: rgba(255,255,255,0.5)">
            <div class="subNav_con">
                <ul>
                    <li>
                        <span>保险</span>
                        <div>
                            <a index="3" href="#baoxianfuwu">保险服务</a>
                            <div>
                                <a index="1" href="#yiwaixian">意外保险</a>
                            </div>
                            <div>
                                <a index="0" href="#jiankangxian">健康保险</a>
                            </div>
                            <div>
                                <a index="2" href="#hezuojigou">合作机构</a>
                            </div>
                            <div>
                                <a index="2" href="#aboutUs">关于我们</a>
                            </div>
                        </div>
                    </li>
                    <!-- <li>
                        <span>健康</span>
                        <div class="ind_con">

                            <a index="4" href="javascript:;">蚂蚁健康</a>
                            <a index="5" href="javascript:;">健康筛查</a>
                            <a index="6" href="javascript:;">合作医馆</a>
                            <a index="7" href="javascript:;">健康会员</a>


                        </div>
                        </li> -->
                </ul>
            </div>
            <div class="subNav_right">
                <div style="display:none;">
                    <span>险种分类</span>
                    <p>
                        <a href="javascript:;">机动车辆保险</a> |
                        <a href="javascript:;">家庭财产保险</a> |
                        <a href="javascript:;">企业财产保险</a> |
                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>险种分类</span>
                    <p>
                        <a href="javascript:;">儿童意外险</a> |
                        <a href="javascript:;">成人综合意外险</a> |
                        <a href="javascript:;">驾乘意外险</a> |
                        <a href="javascript:;">旅游意外险</a> |
                        <a href="javascript:;">团体意外伤害保险</a> |
                        <a href="javascript:;">校园意外险</a> |
                        <a href="javascript:;">老年人综合意外险</a> |
                        <a href="javascript:;">雇主责任险</a> |
                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |



                    </p>
                </div>




                <div style="display:none;">
                    <span>险种分类</span>
                    <p>



                        <a href="javascript:;">少儿重疾险</a> |
                        <a href="javascript:;">男性重疾险</a> |
                        <a href="javascript:;">防癌险</a> |
                        <a href="javascript:;">牙齿健康险</a> |
                        <a href="javascript:;">医疗保险</a> |
                        <a href="javascript:;">成人重大疾病保险</a> |
                        <a href="javascript:;">孕妇保险</a> |
                        <a href="javascript:;">女性重疾险</a> |

                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>险种分类</span>
                    <p>
                        <a href="javascript:;">教育金储蓄</a> |
                        <a href="javascript:;">重疾险</a> |
                        <a href="javascript:;">养老险</a> |
                        <a href="javascript:;">百万身价</a> |

                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>服务分类</span>
                    <p>
                        <a href="javascript:;">蚂蚁健康介绍</a> |
                        <a href="javascript:;">合作医馆</a> |
                        <a href="javascript:;">会员服务</a> |
                        <a href="javascript:;">在线预约</a> |

                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>服务分类</span>
                    <p>
                        <a href="javascript:;">健康体检</a> |
                        <a href="javascript:;">肿瘤筛查</a> |

                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>服务分类</span>
                    <p>
                        <a href="javascript:;">肿瘤科</a> |
                        <a href="javascript:;">儿科</a> |
                        <a href="javascript:;">内科</a> |
                        <a href="javascript:;">外科</a> |

                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
                <div style="display:none;">
                    <span>服务分类</span>
                    <p>
                        <a href="javascript:;">会员权益</a> |
                        <a href="javascript:;">会员注册</a> |
                    </p>
                    <span>热门产品</span>
                    <p>
                        <a href="javascript:;">福乐安少儿教育保障计划</a> |
                        <a href="javascript:;">康健无忧保障计划</a> |
                        <a href="javascript:;">金禧利C款理财计划</a> |
                        <a href="javascript:;">安行无忧综合意外保险计划</a> |
                    </p>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(function () {
            /*----透明背景宽度-----*/
            // $(".subNav_con li").hover(function(){
            //     $(".subNav_con li").removeClass("current");
            //     $(this).addClass("current");
            // },function(){
            //     $(this).removeClass("current");
            // });
            // var fWidth1 = parseInt($(".subNav_con").width());
            // $(".filter_con").css("width",fWidth1+"px");

            // $(".subNav_con").find("a").mouseover(function(){
            //     var fWidth = parseInt($(".subNav_con").width() + $(".subNav_right").width());
            //     $(".filter_con").css("width",fWidth+"px");
            //     $(".subNav_con").find("a").removeClass("current");
            //     $(this).addClass("current");
            //     $(".subNav_right div").eq($(this).attr("index")).show().siblings("div").hide();
            // });

            // $(".filter_con").mouseleave(function(){
            //     $(".subNav_right div").hide();
            //     var fWidth1 = parseInt($(".subNav_con").width());
            //     $(".filter_con").css("width",fWidth1+"px");
            //     $(".subNav_con").find("a").removeClass("current");

            // });

        })
    </script>

    <!--瑞金健康服务-->
    <div class="shouxian" id="baoxianfuwu">
        <div class="content">
            <div class="sx-title">
                <p>保险服务
                    <span>Insurance Services</span>
                </p>
                <div class="clearfix"></div>
            </div>
            <div class="rjjk">
                <ul class="rjjk01">
                    <li>
                        <div class="rjjk" style="display: block;">
                            <a href="javascript:;">
                                <img src="${cdnUrl}/img/web/site/rjbbx/big-01.png">
                            </a>
                        </div>
                        <div style="display: none;" class="rjjk02">
                            <img src="${cdnUrl}/img/web/site/rjbbx/small-01.png">
                            <p>多种保险产品供您选择，每款产品都经过我们的精心挑选，为您优中选优，性价比超高。</p>

                        </div>
                    </li>
                    <li style="border-left:2px #eee solid;border-right:2px #eee solid;">
                        <div class="rjjk" style="display: block;">
                            <a href="javascript:;">
                                <img src="${cdnUrl}/img/web/site/rjbbx/big-02.png">
                            </a>
                        </div>

                        <div style="display: none;" class="rjjk02">
                            <img src="${cdnUrl}/img/web/site/rjbbx/small-02.png">
                            <p>无论您处在购买公司产品的前期还是处在产品服务的后期，都能感受到我们快速、便捷、专业的服务。
                            </p>
                            <!-- <p class="jieshao-title">健康筛查</p> -->
                        </div>
                    </li>
                    <li>
                        <div class="rjjk">
                            <a href="javascript:;">
                                <img src="${cdnUrl}/img/web/site/rjbbx/big-03.png">
                            </a>
                            <!-- <p>健康医馆</p> -->
                        </div>
                        <div style="display:none;" class="rjjk02">
                            <img src="${cdnUrl}/img/web/site/rjbbx/small-03.png">
                            <p> 在这个保险产品满天飞的时代，我公司在售产品均经过我们的精心挑选，让您省心、省力、省钱。</p>
                        </div>
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
            <script>
                $(function () {
                    $(".rjjk01 li").hover(function () {
                        $(this).children(".rjjk").hide().siblings(".rjjk02").show();
                    }, function () {
                        $(this).children(".rjjk").show().siblings(".rjjk02").hide();
                    });
                })
            </script>
        </div>
    </div>
    <!--瑞金健康服务-->

    <!--意外险-->
    <div class="content">
        <div style="position:relative; margin-top:40px;" class="block">
            <div class="preview" id="yiwaixian">
                <div class="scrolldoorFrame">
                    <ul class="scrollUl">
                        <li class="st02" id="a_1" value="0">
                            <a href="javascript:;">平安E家</a>
                        </li>
                        <li class="st01" id="a_2" value="1">
                            <a href="javascript:;">国寿财C款</a>
                        </li>
                        <li class="st02" id="a_3" value="2">
                            <a href="javascript:;">国内旅行险</a>
                        </li>
                    </ul>
                    <div class="bor03 cont">
                        <div id="c1_1" style="display: none;">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103024224692.png" width="282" height="240">
                                            </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>平安E家少儿综合保险计划</h3>
                                            <a href="javascript:;">
                                                10万意外身故 1万意外医疗 50元意外住院津贴
                                            </a>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/1540380748684.jpg" width="282" height="240">
                                        </div>
                                    </li>
                                    <#--  <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103031404764.png" width="282" height="240">
                                            </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>国寿财个人意外险C款</h3>
                                            <a href="javascript:;">
                                                10万意外身故 1万意外医疗 50元意外住院津贴 </a>

                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103040001113.png" width="282" height="240">
                                            </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>国内普通型旅行险</h3>
                                            <a href="javascript:;">
                                                保额高 保障全 组合灵活 保险期限任选 </a>

                                        </div>
                                    </li>  -->
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div id="c1_2" class="hidden" style="display: block;">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <#--  <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103024224692.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: block; opacity: 0;"></div>
                                        <div class="text" style="left: -300px;">
                                            <h3>平安E家少儿综合保险计划</h3>
                                            <a href="javascript:;">
                                                10万意外身故 1万意外医疗 50元意外住院津贴
                                            </a>

                                        </div>
                                    </li>  -->
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103031404764.png" width="282" height="240">
                                            </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>国寿财个人意外险C款</h3>
                                            <a href="javascript:;">
                                                10万意外身故 1万意外医疗 50元意外住院津贴 </a>

                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/1540380818357.jpg" width="282" height="240">
                                        </div>
                                    </li>
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div id="c1_3" class="hidden" style="display: none;">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <#--  <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103031404764.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>国寿财个人意外险C款</h3>
                                            <a href="javascript:;">
                                                10万意外身故 1万意外医疗 50元意外住院津贴
                                            </a>

                                        </div>
                                    </li>  -->
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103040001113.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>国内普通型旅行险</h3>
                                            <a href="javascript:;">
                                                保额高 保障全 组合灵活 保险期限任选
                                            </a>

                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/1540381047210.jpg" width="282" height="240">
                                        </div>
                                    </li>
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
            <div class="preview" id="jiankangxian">
                <div class="scrolldoorFrame" style=" position:relative">
                    <ul style="background-color:#f8ebe0; border-top:2px solid #faddcd" class="scrollUl">
                        <li class="st01" id="b_1" value="0">
                            <a style="color:#da933f" href="javascript:;">成长卫士</a>
                        </li>
                        <li class="st02" id="b_2" value="1">
                            <a style="color:#da933f" href="javascript:;">福乐安教育</a>
                        </li>
                        <li class="st02" id="b_3" value="2">
                            <a style="color:#da933f" href="javascript:;">牙齿健康险</a>
                        </li>

                    </ul>

                    <div class="bor03 cont">

                        <div id="c2_1">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103103041371.jpg" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>成长卫士</h3>
                                            <a href="javascript:;">
                                                40种类重大疾病 50万元高额保障 每天仅8角2分钱
                                            </a>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/1540432699469.jpg" width="282" height="240">
                                        </div>
                                    </li>
                                    <#--  <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103043124106.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>永安安优一生险</h3>
                                            <a href="javascript:;">
                                                购买防癌险，不是为了生活有所改变，而是为了生活不被改变。
                                            </a>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161104103953921.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>美华种植牙及正畸医疗保险</h3>
                                            <a href="javascript:;">
                                                意外种植牙保障金10万 、非意外种植牙保障金 66万、正畸修复保障金 20万
                                            </a>
                                        </div>
                                    </li>  -->
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div id="c2_2" class="hidden">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <#--  <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/20161103103041371.jpg" width="282" height="240">
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>成长卫士</h3>
                                            <a href="javascript:;">
                                                40种类重大疾病 50万元高额保障 每天仅8角2分钱
                                            </a>
                                        </div>
                                    </li>  -->
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161029045226791.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>福乐安少儿教育保障计划</h3>
                                            <a href="javascript:;">
                                                自由选择，灵活搭配 健康呵护，全面护航 满期转换，权益保证 保单分红，额外惊喜
                                            </a>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="photo">
                                            <img src="${cdnUrl}/img/web/site/rjbbx/1540433204927.jpg" width="460" height="240">
                                        </div>
                                    </li>
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div id="c2_3" class="hidden">
                            <div class="lanrenzhijia">
                                <ul class="clearfix">
                                    <#--  <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161103043124106.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>永安安优一生险</h3>
                                            <a href="javascript:;">
                                                购买防癌险，不是为了生活有所改变，而是为了生活不被改变。 </a>
                                        </div>
                                    </li>  -->
                                    <li>
                                        <div class="photo">
                                            <a href="javascript:;">
                                                <img src="${cdnUrl}/img/web/site/rjbbx/20161104103953921.png" width="282" height="240"> </a>
                                        </div>
                                        <div class="rsp" style="display: none;"></div>
                                        <div class="text">
                                            <h3>美华种植牙及正畸医疗保险</h3>
                                            <a href="javascript:;">
                                                意外种植牙保障金10万 、非意外种植牙保障金 66万、正畸修复保障金 20万 </a>
                                        </div>
                                    </li>

                                    <li>
                                        <div class="photo">
                                            保险金额：意外种植牙10万 、非意外种植牙 66万、正畸修复 20万
                                        </div>
                                    </li>
                                </ul>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <img style=" position:absolute; left:0; top:2px;" src="${cdnUrl}/img/web/site/rjbbx/img01.jpg">
            <img style=" position:absolute; left:0; top:332px;" src="${cdnUrl}/img/web/site/rjbbx/img02.jpg">
        </div>


    </div>


    <!--热点资讯-->
    <!-- <div class="content">
        <div class="hydt" style="margin-top:40px">
            <div class="rdzx-title">
                <p>行业动态</p>
                <a href="javascript:;">更多&gt;&gt;</a>
                <div class="clearfix"></div>
            </div>
            <ul class="rdzx-nr">

                <li>
                    <a href="javascript:;">
                        <span class="zhong">保监会：对涉嫌违规网络互助平台进行调查取证</span>
                        <span class="date">[2016-11-03]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">全国首单巨灾指数保险理赔落地</span>
                        <span class="date">[2016-11-04]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">暴雨来袭 保险都能赔什么？</span>
                        <span class="date">[2017-06-22]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">想找到爸妈 我认得他的手</span>
                        <span class="date">[2017-06-26]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">该奋斗的年龄，不要选择安逸</span>
                        <span class="date">[2017-07-03]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">冰雹砸车保险赔吗？保险公司回复：需要看砸到哪里了</span>
                        <span class="date">[2017-07-11]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">一岁孩子竟被查出患有白血病，悲剧再次提醒家长：给出生28</span>
                        <span class="date">[2017-07-18]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">对“强吻”说不！这些避免追尾的妙招你知道么？</span>
                        <span class="date">[2017-08-22]</span>
                    </a>
                </li>


                <div class="clearfix"></div>
            </ul>
        </div>
        <div class="gsdt" style="margin-top:40px">
            <div class="rdzx-title">
                <p>公司动态</p>
                <div class="clearfix"></div>
            </div>
            <ul class="rdzx-nr">
                <li>
                    <a href="javascript:;">
                        <span class="zhong">在单位交了五险一金，我们还需要买商业保险吗？</span>
                        <span class="date">[2016-11-03]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">细节看保险：手机保险正在发展</span>
                        <span class="date">[2016-11-04]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">如何储备商业养老保险？</span>
                        <span class="date">[2017-06-22]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">意外保险，生活必需品！</span>
                        <span class="date">[2017-06-26]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">他曾紅过刘德华、帅过梁朝伟，为癌妻治病花掉750万，最贵</span>
                        <span class="date">[2017-07-03]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">全国首家质子重离子医院可治疗32种癌症，治愈率高达86%，没</span>
                        <span class="date">[2017-07-11]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">盘点近些年中国校园事故 除了痛心，家长们还能做些什么？</span>
                        <span class="date">[2017-07-18]</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="zhong">做饭1小时=吸半包烟？不是吓唬你哦！</span>
                        <span class="date">[2017-08-22]</span>
                    </a>
                </li>

                <div class="clearfix"></div>
            </ul>
        </div>
        <div class="clearfix"></div>
        </div> -->



    <!--合作机构-->
    <div style="height:180px;" class="shouxian" id="hezuojigou">
        <div class="content">
            <div class="sx-title">
                <p>合作机构
                    <span>Cooperation Agency</span>
                </p>
                <div class="clearfix"></div>
            </div>
            <div class="hezuo">
                <ul>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/thumb_131_45_20161029035909541.png" title="国华人寿">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/thumb_131_45_20161029040208222.png" title="中英人寿">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161104092329106.png" title="长城人寿">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161104092814897.png" title="生命人寿">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161104092939423.png" title="人保">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161104093005682.png" title="平安">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161104093027333.png" title="太平洋">
                    </li>
                    <li>
                        <img src="${cdnUrl}/img/web/site/rjbbx/20161110094735955.png" title="英大财险">
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <ul>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
    </div>
    <div style="height:234px;" class="shouxian" id="aboutUs">
        <div class="content">
            <div class="sx-title">
                <p>关于我们</p>
                <div class="clearfix"></div>
            </div>
            <div class="hezuo">
                <ul>
                    <li>
                        北京瑞金恒邦保险销售服务股份有限公司（简称“瑞金恒邦”）是经中国保险监督管理委员会批准，于2010年在北京成立的全国性保险销售服务公司。公司注册资金6000万元，总部设在北京。业务范围覆盖人寿保险、财产保险、相关理赔勘察及经中国保监会批准销售的其他业务。
                    </li>
                    <li>截止目前，瑞金恒邦已在北京、天津、河北、山西、山东、内蒙、河南、湖南、辽宁、新疆等13个省市自治区及50多个城市开设分支机构，并与中国人寿、中国人保、中国平安、中国太平洋、大地财险、都邦财险、泰康人寿、中英人寿、长城人寿等30多家中外资保险公司建立了长期稳定的合作关系。</li>
                    <li> 瑞金恒邦以保险销售服务为核心，打造保险行业综合性服务平台，为客户提供专业化服务，实现公司的可持续发展。</li>
                    <div class="clearfix"></div>
                </ul>
                <ul>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
    </div>
    <!--dfsfs-->
    <div style="height:646px;" class="shouxian" id="hezuojigou">
        <div class="content">
            <div class="sx-title">
                <p>公司地址</p>
                <div class="clearfix"></div>
            </div>
            <div class="">
                <ul>
                    <li style="margin: 0 auto;">
                        <img style="display: block;margin: 0 auto;" src="${cdnUrl}/img/web/site/rjbbx/131538045606_.pic_hd.jpg" alt="" />
                    </li>
                    <!-- <li style="margin: 0 auto;">
                            <img style="display: block;margin: 0 auto;" src="${cdnUrl}/img/web/site/rjbbx/20161128120929546.jpg" alt="" />
                            </li> -->
                    <div class="clearfix"></div>
                </ul>
                <ul>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
    </div>

    <div id="contact" class="row contact-us">
        <div class="inner-contact" style="background: transparent;">
            <div class="row">
                <div class="col-xs-10 col-xs-offset-1">
                    <div class="footer-title" style="font-size: 20px; margin: 21px auto;">联系我们</div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-md-5 col-md-offset-1" style="margin: 0 274px;">
                    <div class="row contact-form">
                        <div class="col-md-6 contact-input">
                            <input type="text" class="form-control" id="name" placeholder="姓名">
                        </div>
                        <div class="col-md-6 contact-input-right">
                            <input type="text" class="form-control" id="mobile" placeholder="手机号">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <textarea name="" id="content" rows="5" placeholder="*内容不能为空" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <button class="btn btn-block submit-btn" id="submit">立即留言</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" tabindex="-1" id="modal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <p>您的留言提交成功！</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <!--友情链接-->
    <!-- <div class="yq">
            <div class="content">
                <div class="link">
                    <p>友情链接：</p>
                    <a href="http://m.ecpic.com.cn/mcar/pages/carInsurance.html?sourceType=FW&amp;otherSource=90089&amp;sellingChannel=45&amp;agencyId=2B0&amp;agentName=%E5%8C%97%E4%BA%AC%E7%91%9E%E9%87%91%E6%81%92%E9%82%A6%E4%BF%9D%E9%99%A9%E9%94%80%E5%94%AE%E6%9C%8D%E5%8A%A1%E8%82%A1%E4%BB%BD%E6%9">太平洋车险</a>

                    <a href="http://www.ecpic.com.cn/cpiccar/sale/quickQuoteCPIC/quickQuoteEntry?otherSource=994">太平洋车险—辽宁</a>
                </div>
            </div>
            </div> -->

    <!--subnav-->
    <!-- <div class="subnav-box">
                <div class="content">
                    <div class="subnav">
                        <ul>
                            <li>
                                <a class="Subnav" href="http://www.rjhbinsure.com/html/about/">关于瑞金</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/about/gongsijianjie/">公司简介</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/about/qiyewenhua/">企业文化</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/about/fenzhijigou/">分支机构</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/about/jiaruruijin/">加入瑞金</a>
                            </li>

                        </ul>
                        <ul>
                            <li>
                                <a class="Subnav" href="http://www.rjhbinsure.com/html/redianzixun/">热点资讯</a>
                            </li>

                            <li>
                                <a href="http://www.rjhbinsure.com/html/redianzixun/xingyedongtai/">行业动态</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/redianzixun/gongsidongtai/">公司动态</a>
                            </li>

                        </ul>
                        <ul>
                            <li>
                                <a class="Subnav" href="http://www.rjhbinsure.com/html/zizhipaizhao/">资质牌照</a>
                            </li>

                            <li>
                                <a href="http://www.rjhbinsure.com/html/zizhipaizhao/zhengjianzizhi/">证件资质</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/zizhipaizhao/baojianxuke/">保监许可</a>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <a class="Subnav" href="http://www.rjhbinsure.com/html/help/">帮助中心</a>
                            </li>

                            <li>
                                <a href="http://www.rjhbinsure.com/html/help/lianxiwomen/">联系我们</a>
                            </li>
                            <li>
                                <a href="http://www.rjhbinsure.com/html/help/wangzhanshengming/">网站声明</a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="tell">
                        <div class="erweima">
                            <p>关注微信</p>
                            <img src="${cdnUrl}/img/web/site/rjbbx/rj_img16.jpg">
                        </div>
                        <div class="lianxi">
                            <ul>
                                <li>
                                    <img src="${cdnUrl}/img/web/site/rjbbx/dianhua.jpg">客服热线：（周一至周五 9:00-18:00）</li>
                                <li style="color:#a60d0f; font-size:34px;">010-87926990</li>
                                <li>
                                    <img src="${cdnUrl}/img/web/site/rjbbx/duanxin.jpg">客服邮箱：974514483@qq.com</li>
                                <li>
                                    <img src="${cdnUrl}/img/web/site/rjbbx/jiaotan.jpg">
                                    <a href="http://wpa.qq.com/msgrd?v=3&amp;uin=974514483&amp;site=www.cactussoft.cn&amp;menu=yes">
                                        <img src="${cdnUrl}/img/web/site/rjbbx/QQ.jpg">
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                </div> -->
    <!--footer-->
    <!-- </div> -->
    <div class="footer">
        <div class="content">
            <div class="footer-nr">
                <ul>
                    <li style="margin-right:18px; margin-left:160px">北京瑞金恒邦保险销售服务股份有限公司 版权所有</li>
                    <li>地址：北京市朝阳区东三环南路19号院1号楼联合国际大厦乙段1111室</li>
                    <br>
                    <li style="margin-left:110px;">全国订购及服务热线：400-8111-909&nbsp;</li>
                    <li style="margin:0 14px;">|</li>
                    <li style="margin-right:20px;">邮政编码：100000</li>

                    <li style="margin-right:20px;">
                        <@beianNumber />
                    </li>
                    <li style="margin-right:18px;">
                        <a href="http://www.youxiaobao.cn/" target="_blank">有效宝诚信企业</a>
                    </li>
                    <li>技术支持：
                        <a href="http://www.xinwangluo.net/" target="_blank">有度网络</a>
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
    </div>

    <!---返回顶部-->
    <div class="scroll" id="scroll" style="display: none;">
        <p>回到顶部</p>
    </div>

    <script type="text/javascript">
        $(function () {
            $('#submit').on('click', function () {
                if ($('#mobile').val() && $('#name').val() && $('#content').val()) {
                    $('#modal').modal('toggle');

                }
            })
            showScroll();

            function showScroll() {
                $(window).scroll(function () {
                    var scrollValue = $(window).scrollTop();
                    scrollValue > 100 ? $('div[class=scroll]').fadeIn() : $(
                        'div[class=scroll]').fadeOut();
                });
                $('#scroll').click(function () {
                    $("html,body").animate({
                        scrollTop: 0
                    }, 200);
                });
            }
        })
    </script>
    <@htmlFoot/>