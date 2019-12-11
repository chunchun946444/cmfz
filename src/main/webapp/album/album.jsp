<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        pageInit();
    });
    function pageInit(){
        $("#albumTable").jqGrid(
            {
                url : "${path}/album/queryByPage",
                editurl:"${path}/album/edit",
                datatype : "json",
                styleUI:"Bootstrap",
                autowidth:true,
                height:"auto",
                rowNum : 5,
                rowList : [ 10, 15, 30 ],
                pager : '#album',
                viewrecords : true,             //是否显示总页数
                multiselect : false,            //是否可以多选
                colNames : [ "编号", "标题", "封面", "评分","作者", "播音", "集数","内容","上传时间" ],
                colModel : [
                    {name : "id",width : 80},
                    {name : "title",width : 90,editable:true},
                    {name : "coverImg",width : 150,editable:true,edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            return "<img src='${path}/album/img/"+cellvalue+"' style='width:150px;height:100px;'/>"
                        }
                    },
                    {name : "score",width : 80,align : "right"},
                    {name : "author",width : 80,align : "right",editable:true},
                    {name : "broadcast",width : 80,align : "right",editable:true},
                    {name : "count",width : 50,editable:true},
                    {name : "content",width : 150,editable:true},
                    {name : "pudDate",width : 150},
                ],

                subGrid : true,     //是否开启子表格
                subGridRowExpanded : function(subgridid, rowid) {
                    addSubGrid(subgridid,rowid);
                }
            });
        $("#albumTable").jqGrid('navGrid', '#album', {add : true, edit : true, del : true},
            {
                //添加之后的操作                   //数据入库
                closeAfterAdd:true,

                afterSubmit:function(data){     //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/album/upload",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},   //获取的id
                        fileElementId:"coverImg",   //需要上传文件域的id(与字段保持一致)
                        success:function () {
                            $("#albumTable").trigger("reloadGrid");
                        }
                    });
                    return "haha";
                }
            },
            {
                //修改之后的额外操作
                closeAfterEdit:true
            }
        );

        /*=============================子=表=操=作==============================*/
        function addSubGrid(subgridId,rowId) {
            var subGridTableId=subgridId+"Table";    //子表格id
            var pagerId="P"+subGridTableId;          //子表格工具栏id

            $("#" + subgridId).html("<table id='" + subGridTableId+ "'></table><div id='"+ pagerId + "'></div>");
            $("#" + subGridTableId).jqGrid(
                {
                    url : "${path}/chapter/queryByPage?albumId="+rowId,
                    editurl:"${path}/chapter/edit?albumId="+rowId,
                    datatype : "json",
                    styleUI:"Bootstrap",
                    autowidth:true,
                    height:"auto",
                    rowNum : 5,
                    rowList : [ 10, 15, 30 ],
                    pager : "#"+pagerId,
                    viewrecords : true,             //是否显示总页数
                    multiselect : false,
                    colNames : [ "章节编号", "章节名称", "资源路径", "时长","文件大小","上传时间","专辑编号" ,"操作"],
                    colModel : [
                        {name : "id",  index : "num",width : 80,key : true,align : "center"},
                        {name : "title",index : "item",  width : 90,align : "center",editable:true},
                        {name : "src",index : "qty",width : 90,align : "center",editable:true,edittype:"file"},
                        {name : "duration",index : "unit",width : 90,align : "center"},
                        {name : "size",index : "total",width : 90,align : "center"},
                        {name : "uploadTime",index : "total",width : 80,align : "center"},
                        {name : "albumId",index : "total",width : 80,align : "center"},
                        {name : "src",index : "total",width : 80,align : "center",
                            formatter:function(cellvalue, options, rowObject){
                                return "<a href='#' onclick='fileDownload(\""+cellvalue+"\")'><span class='glyphicon glyphicon-download'></span></a>&emsp;&emsp;" +
                                    "<a href='#' onclick='filePlayer(\""+cellvalue+"\")'><span class='glyphicon glyphicon-headphones'></span></a>";
                            }
                        }
                    ],
                });
            $("#" + subGridTableId).jqGrid("navGrid","#" +pagerId, { add : true,edit : true, del : true},
                {
                    //修改后的方法
                    //修改之后的额外操作
                    closeAfterEdit:true

                },
                {
                    //文件上传后的操作
                    //添加之后的操作                   //数据入库
                    closeAfterAdd:true,
                    afterSubmit:function(data){     //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/chapter/upload",
                            type:"post",
                            datatype:"json",
                            data:{id:data.responseText},   //获取的id
                            fileElementId:"src",   //需要上传文件域的id(与字段保持一致)
                            success:function () {
                                $("#"+subGridTableId).trigger("reloadGrid");
                            }
                        });
                        return "haha";
                    }
                },
                {

                }
            );
        }
    }
    function fileDownload(data) {
        location.href="${path}/chapter/download?filename="+data;
    }
    function filePlayer(date) {
        //给音频标签设置
        $("#mymusic").attr("src","${path}/album/chapter/"+date);
        //展示模态框
        $("#myModal").modal("show");
    }
</script>


<div class="panel panel-success">
    <div class="panel-heading">
        <h2>专辑信息</h2>
    </div>
    <%--标签页--%>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">专辑展示</a></li>
        </ul>
    </div>
    <%--初始化表单--%>
    <table id="albumTable"></table>
    <%--分页工具栏--%>
    <div id="album"></div>

    <%--在线播放的模态框--%>
    <div class="modal fade" id="myModal" role="dialog" data-target=".bs-example-modal-sm">
        <div class="modal-dialog" role="document">
            <audio id="mymusic" src="" controls />
        </div>
    </div>
</div>