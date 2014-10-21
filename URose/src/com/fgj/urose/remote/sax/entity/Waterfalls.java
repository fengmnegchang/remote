package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Waterfalls implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Waterfall> falls;
	private int size;
	
	public Waterfalls(){
		
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Waterfall> getFalls() {
		return falls;
	}

	public void setFalls(ArrayList<Waterfall> falls) {
		this.falls = falls;
	}

	
}
