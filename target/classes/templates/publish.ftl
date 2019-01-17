<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.layout>

    <div class="container" xmlns="http://www.w3.org/1999/html">
        <ul class="nav nav-tabs">
            <span class="bought-text-head">内容发布</span>
        </ul>

        <div class="div-pubic">
            <form id="form-public" action="/item/create" method="post" onsubmit="return checkPub(this)">
                <table class="table-publish">
                    <tbody>
                    <tr class="tr-publish">
                        <td class="table-span-title" >
                            <span>标题：</span>
                        </td>
                        <td>
                            <input name="title" class="input-publish" placeholder="2-80字符"/>
                        </td>
                        <td rowspan="3">
                            <div class="img-preshow-publish div-img"></div>

                            <img class="img-preshow-publish" src="http://www.baidu.com/不存在.jpg" style="display: none"/>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>摘要：</span>
                        </td>
                        <td>
                            <input name="summary"  class="input-publish" placeholder="2-140字符" />
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>图片：</span>
                        </td>
                        <td>
                            <form id="form-upload-img" action="/item/uploadImage" method="post">
                                <input type="radio" name="single-radio" value="1" onclick="radioClick();" checked>图片地址&nbsp;
                                <input type="radio" name="single-radio" value="2" onclick="radioClick();">本地上传
                            </form>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span></span>
                        </td>
                        <td>
                            <div id="div-img-url"><input name="imgUrl" class="input-publish" placeholder="图片地址" /></div>
                            <div id="div-btn-file" style="display: none">
                                <button class="btn btn-default">选择文件</button>&nbsp;未选择任何文件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="submit" class="btn-upload btn btn-default btn-buy">上传</button>
                            </div>
                        </td>
                        <td>
                            <span></span>
                        </td>
                    </tr>
                    <tr class="tr-publish">
                        <td>
                            <span>正文：</span>
                        </td>
                        <td colspan="2">
                            <input name="description"  class="input-publish input-long-text" placeholder="2-1000个字符" />
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <span class="title-short-text">价格：</span>
                        </td>
                        <td>
                            <input name="price" class="input-publish input-short-text" />&nbsp;元
                        </td>

                    </tr>
                    <tr>
                        <td>

                        </td>
                        <td>
                            <button id="btnPub" type="submit" class="btn-publish btn btn-default btn-buy">发布</button>
                        </td>

                    </tr>
                    </tbody>
                </table>

            </form>
        </div>
    </div>




</@defaultLayout.layout>
