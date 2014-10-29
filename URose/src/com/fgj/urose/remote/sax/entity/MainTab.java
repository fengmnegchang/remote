package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MainTab implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<SubTab> tabs;
	private int size;
	
	
	public ArrayList<SubTab> getTabs() {
		return tabs;
	}
	public void setTabs(ArrayList<SubTab> tabs) {
		this.tabs = tabs;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
