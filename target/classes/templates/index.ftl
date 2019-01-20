<#-- 引入布局指令的命名空间 -->
<#import "./layout.ftl" as defaultLayout>

	<#-- 调用布局指令 -->
	<@defaultLayout.layout>
		<!--body整体-->
		<div class="container" style="margin-bottom: 30px">
			<!--buyer body公共头-->
			<ul class="nav nav-tabs">
				<#if user??>
					<#if user.getType() == 1>
						<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
						<li role="presentation"><a class="select-tab" href="/?type=1">未购买的内容</a></li>
					<#else>
						<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
					</#if>
				<#else>
					<li role="presentation" class="active"><a class="select-tab" href="#">所有内容</a></li>
				</#if>
			</ul>

			<div class="tb-div">
				<table class="table-item-show">
					<#if itemList?? >
						<#list itemList as item>
							<#if (item_index + 1) % 4 ==1><tr class="tr-item-show"></#if>

								<td class="td-item-show">
									<div class="row" >
										<a class="item-href" href="/showDetail?id=${(item.getId())!}">
											<div class="item-border col-sm-6 col-md-4">

												<div class="thumbnail">
													<div class="img-container">
														<img class="item-img" src="${(item.getImgUrl())!}">
														<#if boughtList?? >
															<#if boughtList?seq_contains(item.getId()) == true>
																<div class="item-triangle">
																	<span class="item-triangle-text">已购买</span>
																</div>
															</#if>
														</#if>
														<#if soldList?? >
															<#if soldList?seq_contains(item.getId()) == true>
																<div class="item-triangle">
																	<span class="item-triangle-text">已售出</span>
																</div>
															</#if>
														</#if>
													</div>
													<div class="div-item-title">
														<span>${item.getTitle()!}<br/></span>
														<span>￥</span> <span class="span-item-price">${item.getPrice()!}</span>
													</div>
												</div>
											</div>
										</a>
									</div>
								</td>
							<#if (item_index + 1) % 4 == 0> </tr></#if>
						</#list>
					</#if>
				</table>
			</div>
			<!--
			<p> <#-- ${(userModel.getName())!}  --></p>
			<p> <#-- ${(CommonReturnType.getStatus())!} --></p>-->



	</div>

	</@defaultLayout.layout>



