package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class MainOnline implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<MainAdertise> adverts;
	private ArrayList<MainMenu> menus;
	private int size;
	private int menuSize;
	
	public MainOnline(){
		
	}


	public ArrayList<MainAdertise> getAdverts() {
		return adverts;
	}


	public void setAdverts(ArrayList<MainAdertise> adverts) {
		this.adverts = adverts;
	}


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}


	public ArrayList<MainMenu> getMenus() {
		return menus;
	}


	public void setMenus(ArrayList<MainMenu> menus) {
		this.menus = menus;
	}


	public int getMenuSize() {
		return menuSize;
	}


	public void setMenuSize(int menuSize) {
		this.menuSize = menuSize;
	}
	
}
