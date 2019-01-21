<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
<div class="container" style="margin-bottom: 30px">
    <ul class="nav nav-tabs">
        <span class="bought-text-head">已购买的内容</span>
    </ul>

    <table class="table-bought table table-striped" style="margin-left: 123px">
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
            <#if financeList?? >
                <#list financeList as item>
                    <tr>
                        <td><a href="/showDetail?id=${(item.getItemId())!}"><img class="img-caiwu-small" src="${(item.getItemImgUrl())!}"></a></td>
                        <td><a class="hyperlink-bought-title" href="#"><span>${(item.getItemName())!}</span></a></td>
                        <td><span>${(item.getPaymentTime())!}</span></td>
                        <td><span>${(item.getQuantity())!}</span></td>
                        <td><span class="symbolRMB">¥</span> <span>${(item.getEachPrice())!}</span></td>
                    </tr>
                </#list>
                <tr style="background-color: #F0F0F0">
                    <td colspan="5"><span style="margin-left: 713px">总计： </span><span class="symbolRMB">¥</span> <span>${(allItemPrice)!}</span></td>
                </tr>
            </#if>
        </tbody>
    </table>
</div>
</@defaultLayout.layout>
