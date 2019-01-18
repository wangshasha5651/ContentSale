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


    <form action=""  method="post" enctype="multipart/form-data">
                <input type="file" id="fileId" name="image" value="请上传图片" />
                <input type="hidden" id="hiddId" name="id" value="1"/>
                <input class="btn-login btn btn-default" type="button" id="submit" value="上传" onclick="test()"/>
            </form>



    </form>


</@defaultLayout.layout>



