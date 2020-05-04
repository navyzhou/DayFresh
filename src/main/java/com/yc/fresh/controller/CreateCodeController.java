package com.yc.fresh.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class CreateCodeController{
	
	@RequestMapping("/getcode")
	public void createCode(HttpSession session, HttpServletResponse response) throws IOException{
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		String code = getRandomCode();
		session.setAttribute("validateCode", code);
		
		// 创建验证码的图片
		BufferedImage image = getCodeImage(code, 70, 36);
		
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	/**
	 * 绘制验证码图片
	 * @param code 验证码
	 * @param width 验证码图片的宽度
	 * @param height 验证码图片的高度
	 * @return
	 */
	private BufferedImage getCodeImage(String code, int width, int height) {
		// 创建图片对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// 绘制图片的内容
		// 获取一个图片的绘制工具
		Graphics g = image.getGraphics();
		
		// 设置绘制的颜色
		g.setColor(this.getRandomColor(225, 30));
		
		// 绘制背景
		g.fillRect(0, 0, width, height);
		
		// 绘制干扰线
		Random rd = new Random();
		int x1, y1, x2, y2;
		for (int i = 0; i < 50; i ++) {
			x1 = rd.nextInt(width);
			y1 = rd.nextInt(height);
			x2 = rd.nextInt(width);
			y2 = rd.nextInt(height);
			// 设置干扰线的颜色
			g.setColor(getRandomColor(140, 60));
			
			// 绘制干扰线
			g.drawLine(x1, y1, x2, y2);
		}

		// 设置验证码的字体 
		g.setFont(new Font("Courier New", Font.ITALIC, 22));
		
		// 绘制验证码
		char[] codes = code.toCharArray();
		for (int i = 0, len = codes.length; i < len; i ++) {
			// 设置验证码的颜色
			g.setColor(this.getRandomColor(40, 70));
			
			// 绘制验证码图形
			g.drawString(String.valueOf(codes[i]), 10 + 12*i, 22);
		}
		
		g.dispose();

		return image;
	}

	/**
	 * 获取随机颜色
	 * @param start 颜色的开始值
	 * @param bound 范围
	 * @return
	 */
	private Color getRandomColor(int start, int bound) {
		Random rd = new Random();
		int r = start + rd.nextInt(bound);
		int g = start + rd.nextInt(bound);
		int b = start + rd.nextInt(bound);
		return new Color(r, g, b);
	}
	
	/**
	 * 随机生成验证码的方法
	 * @return
	 */
	private String getRandomCode() {
		Random rd = new Random();
		StringBuffer sbf = new StringBuffer();
		int flag;
		for (int i = 0; i < 4; i ++) {
			flag = rd.nextInt(3);
			switch(flag) {
			case 0: sbf.append(rd.nextInt(10)); break;
			case 1: sbf.append((char)(rd.nextInt(26) + 65)); break;
			case 2: sbf.append((char)(rd.nextInt(26) + 97)); break;
			}
		}
		return sbf.toString();
	}
}
