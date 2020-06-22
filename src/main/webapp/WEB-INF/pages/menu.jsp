<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="media/jquery.easyui.min.js"/>
<link rel="stylesheet" href="media/easyui.css">
<button class="layui-btn layui-btn-sm" id="addMenu"><i class="layui-icon">&#xe654;</i>添加</button>
<button class="layui-btn layui-btn-sm layui-btn-danger" id="delMenu"><i class="layui-icon">&#xe640;</i>删除</button>
<ul id="menu-tree" class="easyui-tree">

</ul>
<script>
    layui.use(['form'], function () {
        var form = layui.form;//初始化form
        $('#menu-tree').tree({
            url: "sys/menu.html?act=tree&needButton=true",//数据接口
            checkbox: true,
            formatter: function (node) {
                return node.name;//获取节点的内容
            }, onClick: function (node) {
                openidielayer(node)
            }
        })
        $('#addMenu').click(function () {
            openidielayer(null)
        })
        $('#delMenu').click(function () {
            layer.confirm('你确定要删除吗？', {
                btn: ['确定','取消'] //按钮
            }, function(index){
                var nodes = $('#menu-tree').tree('getChecked');
                var ids=new Array();
                for (var i = 0; i <nodes.length ; i++) {
                    ids.push(nodes[i].id);
                }
                $.ajax({
                    url: "/sys/menu.html?act=delete",
                    method: "post",
                    data: "ids="+ids,
                    success: function (res) {
                        if (res.status) {
                            $('#menu-tree').tree('reload');
                        } else {
                            layer.msg(res.message);
                        }
                    }
                })
                layer.close(index)
            })
        })

        //打开弹出层,添加编辑共用
        function openidielayer(data) {//打开后往表单里添加数据
            layer.open({
                type: 1
                , zIndex: 10000
                , area: '500px'
                , content: $("#editFormlayer").html()
                , btn: ['确认', '取消']
                , yes: function (index, layero) {
                    $.ajax({
                        url: "/sys/menu.html?act=edit",
                        method: "post",
                        data: $("#editForm").serialize(),//jquery获取表单内容
                        success: function (res) {
                            if (res.status) {
                                $('#menu-tree').tree('reload');
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
                    if (data != null) {
                        form.val("editForm", data);//获取array的元素渲染表单
                        form.val("editForm", {type: data.type + '',});//数字转字符串才能被渲染
                    }
                    //表单初始化完成后初始化input
                    $('#parentId').combotree({
                        url: 'sys/menu.html?act=tree&needButton=false',
                        required: true
                    });
                }
            });
        }
    })
</script>
<%--添加功能弹出层--%>
<script type="text/html" id="editFormlayer">
    <form class="layui-form" style="width:80%;padding-top: 20px" id="editForm" lay-filter="editForm">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名</label>
            <div class="layui-input-block">
                <input type="text" name="name" required lay-verify="required" placeholder="请输入菜单名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">父节点</label>
            <div class="layui-input-block">
                <input type="text" name="parentId" id="parentId">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">url</label>
            <div class="layui-input-block">
                <input type="text" name="url" placeholder="请输入url" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="0" checked title="目录">
                <input type="radio" name="type" value="1" title="链接">
                <input type="radio" name="type" value="2" title="按钮">
            </div>
        </div>
    </form>
</script>