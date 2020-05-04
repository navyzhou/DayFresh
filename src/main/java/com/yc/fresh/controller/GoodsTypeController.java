package com.yc.fresh.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yc.fresh.entity.GoodsType;
import com.yc.fresh.service.IGoodsTypeService;

@RestController
@RequestMapping("/type")
public class GoodsTypeController {
	@Autowired
	private IGoodsTypeService service;
	
	@RequestMapping("/finds")
	public List<GoodsType> finds() {
		return service.finds();
	}
	
	@RequestMapping("/add")
	public int add(@RequestParam("photo")MultipartFile pic, HttpServletRequest request, String tname) {
		if (pic.isEmpty()) {
			return -1;
		}
		try {
			String savePath = "pics";
			
			String path = request.getServletContext().getRealPath("");
			String temp = request.getServletContext().getInitParameter("uploadPath");
			if (temp != null) {
				savePath = temp;
			}
			
			// 在用户上传的文件名的前面加上时间戳
			savePath += "/" + new Date().getTime() + "_" + pic.getOriginalFilename(); // pics/14384354545_1.jpg
			File dest = new File(new File(path).getParentFile(), savePath);
			// 将本地图片保存到服务器
			pic.transferTo(dest);
			
			GoodsType goodsType = new GoodsType(0, tname, savePath, 1);
			return service.add(goodsType);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
