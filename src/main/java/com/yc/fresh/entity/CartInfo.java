package com.yc.fresh.entity;

import java.io.Serializable;

/**
 * 购物车信息
 * 源辰信息
 * @author navy
 * @2019年9月30日
 */
public class CartInfo implements Serializable{
	private static final long serialVersionUID = 4256437176822209042L;
	private String cno;
	private int mno;
	private int gno;
	private int num;
	
	private String gname; // 商品名称
	private double price; // 价格
	private String pics; // 图片
	private String unit; // 单位
	private String weight; // 净重
	
	@Override
	public String toString() {
		return "CartInfo [cno=" + cno + ", mno=" + mno + ", gno=" + gno + ", num=" + num + ", gname=" + gname
				+ ", price=" + price + ", pics=" + pics + ", unit=" + unit + ", weight=" + weight + "]";
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public int getGno() {
		return gno;
	}

	public void setGno(int gno) {
		this.gno = gno;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public CartInfo(String cno, int mno, int gno, int num) {
		super();
		this.cno = cno;
		this.mno = mno;
		this.gno = gno;
		this.num = num;
	}

	public CartInfo() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cno == null) ? 0 : cno.hashCode());
		result = prime * result + gno;
		result = prime * result + mno;
		result = prime * result + num;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartInfo other = (CartInfo) obj;
		if (cno == null) {
			if (other.cno != null)
				return false;
		} else if (!cno.equals(other.cno))
			return false;
		if (gno != other.gno)
			return false;
		if (mno != other.mno)
			return false;
		if (num != other.num)
			return false;
		return true;
	}
}
