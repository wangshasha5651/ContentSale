<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>
    <div class="container">
        <ul class="nav nav-tabs">
            <span class="bought-text-head">已添加到购物车的内容</span>
        </ul>
    </div>
    <table id="cart-table" class="table-bought table table-striped" >
        <thead>
        <tr class="table-bought-th">
            <th style="display: none">id</th>
            <th>内容名称</th>
            <th>数量</th>
            <th>价格</th>
        </tr>
        </thead>
        <tbody>
            <#list cartList as item>
                <tr>
                    <td style="display: none"><span id="id${item_index+1}">${(item.getItemId())!}</span></td>
                    <td><span id="title${item_index+1}">${(item.getItemTitle())!}</td>
                    <td>
                        <button id="lessNum" class="btn-quantity-change" type="button" onclick="change(-1)"><span class="span-quantity-change">-</span></button>
                        <span id="nowNum${item_index+1}">${(item.getQuantity())!}</span>
                        <button id="moreNum" class="btn-quantity-change" type="button" onclick="change(1)"><span class="span-quantity-change">+</span></button>
                    </td>
                    <td><span id="nowPrice${item_index+1}">${(item.getCurrentPrice())!}</span><span id="eachPrice" style="display:none">${(item.getCurrentPrice())!}</span></td>
                </tr>
            </#list>
        </tbody>
    </table>
    <div class="div-settle-btn">
        <button type="button" class="btn-exit btn btn-default" onclick="back()">退&nbsp;出</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn-buy btn btn-default" onclick="buy()">购&nbsp;买</button>
    </div>


    <script>

        var change = function(num){
            //数量的变化
            var span = document.getElementById('nowNum'),
                spanNum = span.innerText*1;

            spanNum += num;

            if(spanNum<1){
                span.innerText = '1';
                alert("商品数量不能再减少！");
            } else {
                span.innerText = ''+spanNum;
                document.getElementById("cart-num").value = spanNum;
            }

            // //总价的变化
            // var spanEachPrice = document.getElementById('eachPrice');
            //
            // if(!spanEachPrice == null){
            //     var eachPrice = spanEachPrice.innerText*1;
            //     var Price;
            //     Price = eachPrice * spanNum;
            //
            //     var spanPrice = document.getElementById('nowPrice');
            //     spanPrice.innerText = '' + Price;
            // }
        }

        function back() {
            window.history.back(-1);
        }
        
        function buy() {
            var table = document.getElementById("cart-table");
            alert($("#title"+1).text());

            var list=[];

            if(table.rows.length != 0){
                for (var i = 1; i < table.rows.length; i++) {
                    var obj={};
                    obj.id = $("#id"+i).text();
                    obj.title = $("#title"+i).text();
                    obj.quantity = $("#nowNum"+i).text();
                    obj.price = $("#nowPrice"+i).text();
                    list.push(obj);

                }
            }


            alert(123);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/buy',
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                traditional: true,
                data: JSON.stringify({cartList: list}),
                success: function(res){
                    alert(res);
                }
            });
        }
    </script>

</@defaultLayout.layout>
