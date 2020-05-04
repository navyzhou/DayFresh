package com.yc.fresh.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yc.fresh.entity.GoodsInfo;
import com.yc.fresh.service.IGoodsInfoService;


@RestController
@RequestMapping("/goods")
public class GoodsInfoController {
	@Autowired
	private IGoodsInfoService service;
	
	
	@RequestMapping("/upload")
	public Map<String, Object> add(@RequestParam("upload")MultipartFile pic, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (pic.isEmpty()) {
			return map;
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
			
			map.put("fileName", pic.getOriginalFilename());
			map.put("uploaded", 1);
			map.put("url", "../../../"+ savePath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/addGood")
	public int addGood(@RequestParam("pic")MultipartFile[] pics, GoodsInfo gf, HttpServletRequest request) {
		if (pics != null && pics.length > 0 && !pics[0].isEmpty()) { // 说明有图片
			try {
				String savePath = "pics";
				
				String basepath = request.getServletContext().getRealPath("");
				String temp = request.getServletContext().getInitParameter("uploadPath");
				if (temp != null) {
					savePath = temp;
				}
				
				String picStr = "";
				File dest = null;
				String path = null;
				
				// 开始图片的循环上传
				for (MultipartFile fl : pics) {
					path = savePath + "/" + new Date().getTime() + "_" + fl.getOriginalFilename(); // pics/14384354545_1.jpg
					dest = new File(new File(basepath).getParentFile(), path);
					fl.transferTo(dest);
					picStr += path + ";";
				}
				
				if (!"".equals(picStr)) {
					picStr = picStr.substring(0, picStr.lastIndexOf(";"));
				}
				gf.setPics(picStr);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return service.add(gf);
	}
	
	@RequestMapping("/finds")
	public Map<String, Object> finds() {
		return service.finds();
	}
	
	@RequestMapping("/findByFirst")
	public Map<String, Object> findByFirst(Integer tno, Integer rows, Integer page) {
		return service.findByFirst(tno, page, rows);
	}
	
	@RequestMapping("/findByPage")
	public List<GoodsInfo> findByPage(Integer tno, Integer rows, Integer page) {
		return service.findByType(tno, page, rows);
	}
	
	@RequestMapping("/detail")
	public GoodsInfo detail(Integer gno) {
		return service.findByGno(gno);
	}
}
