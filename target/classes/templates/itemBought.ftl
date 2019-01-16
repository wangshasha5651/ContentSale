<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div class="container">
    <ul class="nav nav-tabs">
        <span class="bought-text-head">已购买的内容</span>
    </ul>
</div>
    <table class="table-bought table table-striped" >
        <thead>
        <tr class="table-bought-th">
            <th class="td-img">内容图片</th>
            <th class="td-item-name">内容名称</th>
            <th class="td-item-date">购买时间</th>
            <th class="td-item-quantity">购买数量</th>
            <th class="td-item-price">购买价格</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><a href="#"><img class="img-caiwu-small" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368"></a></td>
            <td><a class="hyperlink-bought-title" href="#"><span>测试</span></a></td>
            <td><span>2018-12-29 17:27</span></td>
            <td><span>1</span></td>
            <td><span class="symbolRMB">¥</span> <span>120.88</span></td>
        </tr>
        <tr>
            <td><a href="#"><img class="img-caiwu-small" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368"></a></td>
            <td><a class="hyperlink-bought-title" href="#"><span>SICP</span></a></td>
            <td><span>2018-12-29 17:27</span></td>
            <td><span>1</span></td>
            <td><span class="symbolRMB">¥</span> <span>120.88</span></td>
        </tr>
        <tr>
            <td><a href="#"><img class="img-caiwu-small" src="http://p3.music.126.net/dqR0Cx3V7Eh0a6NX9OwUXg==/109951163338309990.jpg?param=368y368"></a></td>
            <td><a class="hyperlink-bought-title" href="#"><span>测试</span></a></td>
            <td><span>2018-12-29 17:27</span></td>
            <td><span>1</span></td>
            <td><span class="symbolRMB">¥</span> <span>120.88</span></td>
        </tr>
        </tbody>
    </table>
</@defaultLayout.layout>
