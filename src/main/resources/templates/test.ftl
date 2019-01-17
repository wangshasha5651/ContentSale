<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <!--body整体-->
    <div class="container" xmlns="http://www.w3.org/1999/html">
        <!--buyer body公共头-->
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
            <li role="presentation"><a class="select-tab" href="#">未购买的内容</a></li>
        </ul>

    </div>



    <label>图片</label>
    <div class="upload-img">
        <input id="img-upload" type="file" name="img" accept="image/*">
        <img id="img-show" src="" class="img-polaroid" width="170" height="170" alt="上传图片" title="上传图片">
    </div>



</@defaultLayout.layout>



