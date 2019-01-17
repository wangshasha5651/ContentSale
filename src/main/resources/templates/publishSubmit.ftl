<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>


    <div class="publish-success-panel panel panel-default">

        <div class="table-container" style="position:relative;">

            <div class="div-publish-success">
                <center><h3>发布成功！</h3></center>
            </div>

            <div class="div-publish-next">
                <a class="publish-href" href="#">[查看内容]</a>&nbsp; <a class="publish-href" href="/">[返回首页]</a>
            </div>
        </div>
    </div>



</@defaultLayout.layout>



