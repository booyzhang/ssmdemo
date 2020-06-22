<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="media/jquery.easyui.min.js"/>
<link rel="stylesheet" href="media/easyui.css">
<%--顶部搜索--%>
<div class="layui-form-item">
    <form class="layui-form" >
        <div class="layui-inline">
            <label class="layui-form-label">角色名:</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input type="text" name="name" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">别名:</label>
            <div class="layui-input-inline" style="width: 200px;">
                <input type="text" name="remark" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn" lay-submit lay-filter="searchForm">查询</button>
        </div>
    </form>

</div>
<%--动态表格--%>
<table id="roleTable" lay-filter="roleTable"></table>
<%--表格头部工具条--%>
<script type="text/html" id="headtool">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm " lay-event="add"><i class="layui-icon">&#xe654;</i>添加</button>
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="update"><i class="layui-icon">&#xe642;</i>编辑
        </button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon">&#xe640;</i>删除
        </button>
    </div>
</script>
<%--行工具条--%>
<script type="text/html" id="rowtool">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="assign">授权</a>
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
</script>

<%--工具条--%>
<script>
    layui.use(['table', 'form'], function () {
        //初始化table对象，当前页面所有table都归lay管理,以id区分
        var table = layui.table;
        var form = layui.form;

        table.render({
            id: 'roleTable'//设置渲染后的table
            , elem: '#roleTable'//根据id取首次渲染的table
            , toolbar: '#headtool'//头部工具条
            , height: 523
            , url: '/sys/role.html?act=table' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', width: 80}
                , {field: 'name', title: '角色名'}
                , {field: 'remark', title: '别名'}
                , {field: 'status', title: '状态' ,templet: function(d){
                        return d.status==1?'<span class="layui-badge layui-bg-green">正常</span>':'<span class="layui-badge layui-bg-gray">停用</span>'
                    }}
                ,{fixed: 'right', width:200, align:'center', toolbar: '#rowtool'}
            ]]
        });
        //添加搜索渲染
        form.on('submit(searchForm)', function (data) {
            console.log(data.field);//当前容器的全部表单字段，名值对形式：{name: value}
            table.reload('roleTable', {//渲染后的tableid
                where: data.field //设定异步数据接口的额外参数
                //,height: 300
            });
            return false;
        });
        //监听头部工具条事件
        table.on('toolbar(roleTable)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            var data = checkStatus.data;//获取选中行数据
            switch (obj.event) {
                case 'add':
                    if (data.length>0) {
                        return;
                    }
                    openidielayer(data);//null
                    break;
                case 'delete':
                    if (data.length<1) {
                        return;
                    }
                    layer.confirm('你确定要删除吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(index){
                        var ids = new Array();
                        for (var i = 0; i < data.length; i++) {
                            ids.push(data[i].id)
                        }
                        $.ajax({
                            url: "/sys/role.html?act=delete",
                            method: "post",
                            data: "roleIds="+ids,//jquery获取表单内容
                            success: function (res) {
                                if (res.status) {
                                    table.reload('roleTable', {});//删除数据后刷新table
                                } else {
                                    layer.msg(res.message);
                                }
                            }
                        })
                        layer.close(index)
                    })

                    break;
                case 'update':
                    if (data.length != 1) {
                        layer.msg('请选中一行数据');
                        return;
                    }
                    openidielayer(data[0]);
                    break;
            }
            ;
        });
        //监听行工具条
        table.on('tool(roleTable)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            switch (layEvent) {
                case 'del':
                    layer.confirm('你确定要删除吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(index){
                        $.ajax({
                            url: "/sys/role.html?act=delete",
                            method: "post",
                            data: "roleIds="+data.id,//获取当前行的id
                            success: function (res) {
                                if (res.status) {
                                    table.reload('roleTable', {});//删除数据后刷新table
                                } else {
                                    layer.msg(res.message);
                                }
                            }
                        })
                        layer.close(index)
                    })
                    break;
                case 'edit':
                    openidielayer(data);
                    break;
                case 'assign':
                    openMenulayer(data.id);
                    break;
            }
        })
        //打开弹出层,添加编辑共用
        function openidielayer(data) {//打开后往表单里添加数据
            layer.open({
                type: 1
                , area: '500px'
                , content: $("#editFormlayer").html()
                , btn: ['确认', '取消']
                , yes: function (index, layero) {
                    $.ajax({
                        url: "/sys/role.html?act=edit",
                        method: "post",
                        data: $("#editForm").serialize(),//jquery获取表单内容
                        success: function (res) {
                            if (res.status) {
                                table.reload('roleTable', {});//添加数据后刷新table
                                layer.close(index);
                            } else {
                                layer.msg(res.message);
                            }
                        }
                    })
                }
                , btn2: function (index, layero) {
                    layer.close(index)
                }, success: function (layero, index) {
                    console.log(data);
                    form.render();//重新渲染table
                    form.val("editForm",data);//获取array的第0个元素渲染表单
                    form.val("editForm",{status:data.status+''});//数字转字符串才能被渲染
                }
            });
        }

        //授权弹出层
        function openMenulayer(id) {//打开后往表单里添加数据
            layer.open({
                type: 1
                , area: '500px'
                , content: $("#menulayer").html()
                , btn: ['确认', '取消']
                , yes: function (index, layero) {
                    var nodes = $('#menu-tree').tree('getChecked', ['checked','indeterminate']);
                    var menuIds = new Array();
                    for (var i = 0; i < nodes.length; i++) {
                        menuIds.push(nodes[i].id)
                        console.log(nodes[i].id)
                    }

                    $.ajax({
                        url: "/sys/role.html?act=assign",
                        method: "post",
                        data: "roleId="+id+"&menuIds="+menuIds,
                        success: function (res) {
                            if (res.status) {
                                layer.close(index);
                            } else {
                                layer.msg(res.message);
                            }
                        }
                    })
                }
                , btn2: function (index, layero) {
                    layer.close(index)
                }, success: function (layero, index) {
                    $.ajax({
                        url: "sys/role.html?act=menuIds",
                        data:"roleId="+id,
                        success:function (res) {
                            $('#menu-tree').tree({//打开成功后加载树
                                url: "sys/role.html?act=tree",//数据接口
                                checkbox: true,
                                onLoadSuccess:function (node, data) {//选中用户已有数据res
                                    var tree = $('#menu-tree');
                                    $.each(res, function (i,obj) {
                                        var node = tree.tree('find', obj);
                                        if(node!=null&&tree.tree('isLeaf',node.target)){//只选中子节点
                                            tree.tree('check', node.target);
                                        }
                                    })

                                }
                            })
                        }
                    })


                }
            });
        }
    });
</script>
<%--添加功能弹出层--%>
<script type="text/html" id="editFormlayer">
    <form class="layui-form" style="width:80%;padding-top: 20px" id="editForm" lay-filter="editForm">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名：</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" placeholder="请输入角色名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">别名：</label>
            <div class="layui-input-block">
                <input type="text" name="remark" required lay-verify="required" placeholder="请输入别名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="radio" name="status" value="1" title="有效">
                <input type="radio" name="status" value="0" title="无效">
            </div>
        </div>
    </form>
</script>
<%--授权功能弹出层--%>
<script type="text/html" id="menulayer">
    <ul id="menu-tree" class="easyui-tree">
    </ul>
</script>