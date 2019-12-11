<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function(K) {
        window.editor = K.create("#editor_id",{
            width:"800px",
            height:"400px",
            uploadJson:"${path}/editor/uploadPhoto",   //指定文件上传路径
            //filePostName:""     指定文件上传的名，默认是imgFile
            allowFileManager:true,   //显示浏览远程图片的按钮
            fileManagerJson:"${path}/editor/download"  //指定远程访问图片的路径
        });
    });
</script>
<div align="center">
    <textarea id="editor_id" name="content" style="width:700px;height: 300px;"></textarea>
</div>