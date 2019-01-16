<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <div class="container">
        <ul class="nav nav-tabs">
            <span class="bought-text-head">已添加到购物车的内容</span>
        </ul>
    </div>
    <table class="table-bought table table-striped" >
        <thead>
        <tr class="table-bought-th">
            <th>内容名称</th>
            <th>数量</th>
            <th>价格</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><span>测试</span></a></td>
            <td>
                <button id="lessNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">-</span></button>
                <span id="nowNum">1</span>
                <button id="moreNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">+</span></button>
            <td><span id="nowPrice">2</span><span id="eachPrice" style="display:none">2</span></td>
        </tr>
        </tbody>
    </table>
    <div class="div-settle-btn">
        <button type="button" class="btn-exit btn btn-default">退&nbsp;出</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn-buy btn btn-default">购&nbsp;买</button>
    </div>
</@defaultLayout.layout>
