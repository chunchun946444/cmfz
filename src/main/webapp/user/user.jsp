<%@page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        pageInit();
    });
    function pageInit() {
        $("#userTable").jqGrid({
            url:"${path}/user/queryByPage",
            editurl:"${path}/user/edit",
            datatype:"json",
            type:"post",
            rowNum:4,
            rowList:[8,12,16],
            pager:"#user",
            styleUI:"Bootstrap",
            height:"auto",             //高度自适应
            autowidth:true,
            viewrecords:true,
            colNames:["编号","头像","姓名","密码","法名","性别","手机号","状态","注册时间"],
            colModel:[
                {name:"id",width:60,align:"center"},
                {name:"picImg",width:60,align:"center",editable:true,edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<img src='${path}/user/img/"+cellvalue+"' style='width:100px;height:80px;'/>"
                    }
                },
                {name:"name",width:60,editable:true,align:"center"},
                {name:"password",width:60,editable:true,align:"center"},
                {name:"legalName",width:60,editable:true,align:"center"},
                {name:"sex",width:60,editable:true,align:"center"},
                {name:"phone",width:60,editable:true,align:"center"},
                {name:"status",width:60,align:"center",
                    formatter:function (cellvalue,options,rowObject) {
                        if(cellvalue==="正常"){
                            return "<button class='btn btn-success'>正常</button>"
                        }else {
                            return "<button class='btn btn-danger'>冻结</button>"
                        }
                    }
                },
                {name:"regTime",width:60,align:"center"}
            ]
        });
        $("#userTable").jqGrid("navGrid","#user",{edit:true,add:true,del:true},
            {
                closeAfterEdit:true,
            },
            {
                closeAfterAdd:true,
                afterSubmit:function(data){     //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/user/upload",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},   //获取的id
                        fileElementId:"picImg",   //需要上传文件域的id
                        success:function () {
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "haha";
                }
            },
            {
                closeAfterDel:true,
            }
        )
    }
</script>



<div class="panel panel-success">
    <div class="panel-heading">
        <h2>用户信息</h2>
    </div>
    <%--标签页--%>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">用户管理</a></li>
        </ul>
    </div>
    <%--初始化表单--%>
    <table id="userTable"></table>

    <%--分页工具栏--%>
    <div id="user"></div>
</div>