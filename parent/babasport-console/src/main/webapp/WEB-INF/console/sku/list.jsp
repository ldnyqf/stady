<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-list</title>
<script >
	function updateSku(skuId){
		//修改该库存行的文本框为可用
		$("#m"+skuId).attr("disabled",false);
		$("#p"+skuId).attr("disabled",false);
		$("#s"+skuId).attr("disabled",false);
		$("#u"+skuId).attr("disabled",false);
		$("#d"+skuId).attr("disabled",false);
	}
	function addSku(skuId){
		//修改该库存行的文本框为不可用
		$("#m"+skuId).attr("disabled",true);
		$("#p"+skuId).attr("disabled",true);
		$("#s"+skuId).attr("disabled",true);
		$("#u"+skuId).attr("disabled",true);
		$("#d"+skuId).attr("disabled",true);
		var param={
				"id":skuId,
				"marketPrice":$("#m"+skuId).val(),
				"price":$("#p"+skuId).val(),
				"stock":$("#s"+skuId).val(),
				"upperLimit":$("#u"+skuId).val(),
				"deliveFee":$("#d"+skuId).val()
		};
		$.post(
					"updateSku.do",param,function(data){
						alert(data);
						}
		);
	}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 库存管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">
<form method="post" id="tableForm">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品颜色</th>
			<th>商品尺码</th>
			<th>市场价格</th>
			<th>销售价格</th>
			<th>库       存</th>
			<th>购买限制</th>
			<th>运       费</th>
			<th>是否赠品</th>
			<th>操       作</th>
		</tr>
	</thead>
		<c:forEach items="${skus }" var="sku">
	<tbody class="pn-ltbody">
			<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
				<td><input type="checkbox" name="ids" value="73"/></td>
				<td>${sku.productId }</td>
				<td align="center">${sku.color.name }</td>
				<td align="center">${sku.size }</td>
				<td align="center"><input type="text" id="m${sku.id }" value="${sku.marketPrice }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="p${sku.id }" value="${sku.price }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="s${sku.id }" value="${sku.stock }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="u${sku.id }" value="${sku.upperLimit }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="d${sku.id }" value="${sku.deliveFee }" disabled="disabled" size="10"/></td>
				<td align="center">不是</td>
				<td align="center"><a href="javascript:updateSku(${sku.id })" class="pn-opt">修改</a> | <a href="javascript:addSku(${sku.id })" class="pn-opt">保存</a></td>
			</tr>
	</tbody>
		</c:forEach>
</table>
</form>
</div>
</body>
</html>