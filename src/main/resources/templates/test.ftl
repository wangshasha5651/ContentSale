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

    <div class="img-container" style="position:relative;">
        <img class="item-img" style="width:200px; height:200px;position:absolute;left:0;top:0;z-index:1;" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368" alt="SICP">
        <div class="layer" style="width: 100px;height: 100px;position:absolute;left:0;top:0;z-index:2;background-color: #737373">
        </div>
    </div>




</@defaultLayout.layout>



