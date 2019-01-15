<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>
 
<#-- 调用布局指令 -->
<@defaultLayout.layout>
 
    <#-- 将下面这个main content嵌入到layout指令的nested块中 -->
    <div>
           main content
    </div>

</@defaultLayout.layout>