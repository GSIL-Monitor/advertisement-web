$(function() {
    $.getJSON('/ms/admin/staff/structureQuery.json', function(data) {
        var Util = G6.Util;
        // 准备布局配置
        var layoutCfg = {
            "direction": "LR",
            "nodeSep": 15,
            "nodeSize": 15,
            "rankSep": 80
        };
        // 自定义树节点
        var DEFAULT_NODE_SIZE = 5;
        G6.registerNode('treeNode', {
            draw(cfg, group) {
                var model = cfg.model;
                var r = layoutCfg.nodeSize ? layoutCfg.nodeSize / 2 : DEFAULT_NODE_SIZE;
                var shapeCfg = {
                    attrs: {
                        x: cfg.x,
                        y: cfg.y,
                        r: r,
                        stroke: '#003380',
                        fill: 'white',
                        fillOpacity: 1,
                    },
                };
                if (model.children && model.children.length) {
                    shapeCfg.class = model.isCollapsed ? 'spreadoutButton' : 'collapseButton';
                    shapeCfg.attrs.fill = '#044A9A';
                    shapeCfg.attrs.stroke = '#003380';
                    shapeCfg.attrs.fillOpacity = 0.4;
                }
                if (model.root) {
                    shapeCfg.attrs.fill = '#044A9A';
                    shapeCfg.attrs.stroke = '#003380';
                    shapeCfg.attrs.fillOpacity = 0.7;
                }
                shapeCfg.attrStash = Util.mix({}, shapeCfg.attrs);
                return group.addShape('circle', shapeCfg);
            },
            afterDraw(cfg, group) {
                var model = cfg.model;
                var r = layoutCfg.nodeSize ? layoutCfg.nodeSize / 2 : DEFAULT_NODE_SIZE;
                var align = model.align;
                var labelAttrs = {
                    text: model.name,
                    fill: '#666',
                    textBaseline: 'middle',
                    fontSize: 14,
                    x: cfg.x + r + DEFAULT_NODE_SIZE,
                    y: cfg.y,
                    textAlign: 'left',
                };
                if (align === 'R') {
                    Util.mix(labelAttrs, {
                        x: cfg.x - r - DEFAULT_NODE_SIZE,
                        y: cfg.y,
                        textAlign: 'right',
                    });
                } else if (align === 'T' || align === 'CH') {
                    Util.mix(labelAttrs, {
                        x: cfg.x,
                        y: cfg.y + r + DEFAULT_NODE_SIZE,
                        textAlign: 'right',
                        rotate: -Math.PI / 2,
                    });
                } else if (align === 'B') {
                    Util.mix(labelAttrs, {
                        x: cfg.x,
                        y: cfg.y - r - DEFAULT_NODE_SIZE,
                        textAlign: 'left',
                        rotate: -Math.PI / 2,
                    });
                }
                var label = group.addShape('text', {
                    attrs: labelAttrs,
                });
                return label;
            }
        });

        // 生成树图实例
        var tree = new G6.Tree({
            id: 'mountNode',                            // 容器ID
            height: window.innerHeight,                         // 画布高
            fitView: 'tc',                 // 自动缩放
            layoutFn: G6.Layouts.LayeredTidyTree, // 布局类型
            layoutCfg: layoutCfg,                // 布局配置
            showButton: false,
        });

        // 加载数据
        tree.source(data);
        tree.node().shape('treeNode');
        tree.edge()
        .shape('smooth')
        .style({
            stroke: '#A9BCD3'  
        });

        // 渲染树图
        tree.render();

        // 添加事件
        tree.on('mouseenter', ev => {
            var shape = ev.shape;
            if (shape && shape.hasClass('Button')) {
                shape.attr('fillOpacity', 0.2);
                shape.attr('strokeOpacity', 0.8);
                tree.refresh();
            }
        });
        tree.on('mouseleave', ev => {
            var shape = ev.shape;
            let attrStash;
            if (shape && shape.hasClass('Button')) {
                attrStash = shape.get('attrStash');
                shape.attr(attrStash);
                tree.refresh();
            }
        });
    });
})

