<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <!--body整体-->
    <div class="container" xmlns="http://www.w3.org/1999/html">
        <div style="margin-left: 123px">
            <table>
                <tbody>
                <tr>
                    <td rowspan="6">
                        <div style="width: 250px;height: 250px;background-color: #FFFFFF;border: 1px solid #DBDBDB;"></div>
                        <img src="" style="width: 250px;height: 250px;display: none"/>
                    </td>
                    <td rowspan="6">
                        <div style="width: 30px;"></div>
                    </td>
                    <td><div style="margin-top: -6px"><span style="font-size: 22px;">test</span></div></td>
                </tr>
                <tr>
                    <td><span style="font-size: 13px">test摘要</span></td>
                </tr>
                <tr>
                    <td><span>￥</span> <span class="span-detail-price">12</span></td>
                </tr>
                <tr>
                    <td>
                        <span>购买数量：&nbsp;&nbsp;</span>
                        <button id="lessNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">-</span></button>
                        <span id="nowNum">1</span>
                        <button id="moreNum" class="btn-quantity-change" type="button"><span class="span-quantity-change">+</span></button>
                    </td>
                </tr>
                <tr>
                    <td><button type="button" class="btn-edit btn btn-default btn-buy">编&nbsp;辑</button></td>
                </tr>
                </tbody>



            </table>


        </div>



        <ul class="nav-detail nav nav-tabs">
            <span class="bought-text-head">详细信息</span>
        </ul>
        <div style="margin-left: 123px;margin-top: 30px">
            <span>内容内容内容内容内容内容</span>
        </div>

    </div>






</@defaultLayout.layout>


