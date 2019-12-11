<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        pageInit();
    });
    function upsta(o) {
        $.ajax({
            url:"${path}/banner/updateStatus",
            data:"id="+o,
            type:"post",
            datatype:"json",
            success:function (data) {
                $("#bannerTable").trigger("reloadGrid");
            }
        })
    }
    function pageInit(){
        $("#bannerTable").jqGrid(
            {
                url : "${path}/banner/queryAll",                  //指定的获取数据的路径
                editurl:"${path}/banner/edit",      //增删改的路径
                datatype : "json",         //返回的数据类型
                rowNum : 4,                //在grid上显示记录条数，这个参数是要被传递到后台
                rowList : [ 4, 8, 12 ],    //指定每页最大的展示数
                pager : '#banner',         //分页工具栏
                //sortname : "id",         //默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
                styleUI:"Bootstrap",       //样式为bootstrap样式
                height:"auto",             //高度自适应
                autowidth:true,            //宽度自适应
                type : "post",            //ajax提交方式。POST或者GET，默认GET
                viewrecords : true,        //是否展示总条数
                //sortorder : "desc",        //排序顺序，升序或者降序（asc or desc）
                // caption : "JSON 实例",      //表格名称
                colNames : [ "编号", "名称", "图片", "状态", "上传时间","描述"],   //每个字段的名称
                colModel : [        //对应的每个字段详细信息
                    {name : "id",index : 'id',align:"center",width : 55},
                    {name : "name",editable:true, index : 'name',align:"center",width : 90},
                    {name : "src",index : 'src',editable:true, width : 100,align:"center",edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            return "<img src='${path}/banner/img/"+cellvalue+"' style='width:100px;height:80px;'/>"
                        }
                    },
                    {name : "status",index : 'status',width : 80,align:"center",
                        formatter:function(cellvalue, options, rowObject){
                            if(cellvalue==1){
                                return "<button class='btn btn-success' onclick='upsta(\""+rowObject.id+"\")'>正常</button>"
                            }else{
                                return "<button class='btn btn-danger'onclick='upsta(\""+rowObject.id+"\")'>冻结</button>"
                            }
                        }
                    },
                    {name : "uploaddate",index : 'uplodadate',width : 80,align:"center"},
                    {name : "describee",editable:true, index : 'describee',width : 80,align:"center",}
                ]
            });

        //分页的菜单栏
        $("#bannerTable ").jqGrid('navGrid', '#banner', {edit : true,add :true,del :true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            {
                //修改之后的额外操作
                closeAfterEdit:true
            },
            {
                //添加之后的操作                   //数据入库
                closeAfterAdd:true,
                afterSubmit:function(data){     //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/banner/upload",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},   //获取的id
                        fileElementId:"src",   //需要上传文件域的id
                        success:function () {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    return "haha";
                }
            },
            {
                //删除后的额外操作
                closeAfterDel:true
            }
        )
    }

</script>
<div class="panel panel-success">
    <div class="panel-heading">
        <h2>轮播图信息</h2>
    </div>
    <%--标签页--%>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">轮播图信息</a></li>
        </ul>
    </div>
    <%--初始化表单--%>
    <table id="bannerTable"></table>

    <%--分页工具栏--%>
    <div id="banner"></div>
</div>