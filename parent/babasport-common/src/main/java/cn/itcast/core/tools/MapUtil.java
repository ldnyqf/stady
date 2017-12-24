package cn.itcast.core.tools;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MapUtil {
	// 传入经纬度坐标集合，计算出距离和时间
	// 参数是起始点坐标对象数组，和目的地坐标对象数组
	public static JSONObject getRouteResult(String origins, String destinations) {
		String url = "http://api.map.baidu.com/routematrix/v2/driving";
		String params = "?output=json&origins=" + origins + "&destinations=" + destinations
				+ "&ak=lujf5hDLW4nGHMCjwUtGQIn6LjxUVmXc";
		HttpClient httpClient = new DefaultHttpClient();
		System.out.println(url + params);
		JSONObject jsonObject = HttpRequestUtils.httpGet(url + params);
		return jsonObject;
	}

	public static void main(String[] args) {
		String origins = "40.45,116.34|40.54,116.35";
		String originse = URLEncoder.encode(origins);
		String destinations = "40.34,116.45|40.35,116.46";
		String destinationse = URLEncoder.encode(destinations);
		JSONObject routeResult = MapUtil.getRouteResult(originse, destinationse);
		JSONArray jsonArray = routeResult.getJSONArray("result");
		System.out.println(jsonArray.toJSONString());
	}
}
