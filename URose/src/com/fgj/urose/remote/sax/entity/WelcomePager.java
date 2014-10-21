package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class WelcomePager implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<SelectPager> pagers;
	private int size;
	
	public WelcomePager(){
		
	}

	public ArrayList<SelectPager> getPagers() {
		return pagers;
	}

	public void setPagers(ArrayList<SelectPager> pagers) {
		this.pagers = pagers;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
}
