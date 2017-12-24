package cn.itcast.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSONObject;

import cn.itcast.core.tools.Constants;
import cn.itcast.core.tools.FastDFSTool;

/**
 * 图片上传到fastdfs的action
 * 
 * @author libin
 *
 */
@RequestMapping()
@Controller
public class UploadController {

	// brand上传图片
	/**
	 * 品牌图片上传
	 * 
	 * @param pic
	 * @param response
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "upload/uploadPic")
	public void uploadPic(MultipartFile pic, HttpServletResponse response) throws Exception, IOException {
		System.out.println(pic.getOriginalFilename());
		// 将文件上传到分布式文件系统，并返回文件的存储路径及名称
		String uploadFile = FastDFSTool.uploadFile(pic.getBytes(), pic.getOriginalFilename());
		String path = Constants.FDFS_SERVER + uploadFile;
		JSONObject jo = new JSONObject();
		jo.put("path", path);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}

	@RequestMapping(value = "upload/uploadFck")
	public void uploadPics(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		// 无敌版接受文件或者图片
		MultipartRequest mr = (MultipartRequest) request;
		// 只有图片或者文件 支持多张
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		// 遍历map
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();
			// 获取文件在 dfs上的路径
			String uploadFile = FastDFSTool.uploadFile(pic.getBytes(), pic.getOriginalFilename());
			// 获取文件的网络路径
			String path = Constants.FDFS_SERVER + uploadFile;
			JSONObject jo = new JSONObject();
			jo.put("url", path);
			jo.put("error", 0);
			// 返回路径
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jo.toString());
		}
	}

	/**
	 * 富文本编辑上传action
	 * 
	 * @param pics
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	@RequestMapping(value = "upload/uploadPics")
	@ResponseBody
	public List<String> uploadFck(@RequestParam MultipartFile[] pics) throws Exception, IOException {
		List<String> list = new ArrayList<>();
		for (MultipartFile pic : pics) {
			String uploadFile = FastDFSTool.uploadFile(pic.getBytes(), pic.getOriginalFilename());
			String path = Constants.FDFS_SERVER + uploadFile;
			list.add(path);
		}
		return list;
	}
}
