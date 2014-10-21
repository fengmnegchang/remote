package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;


public class SelectPager implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String imageName;
	private String url;
	
	
	public SelectPager(){
		
	}

	@Override
	public SelectPager clone() throws CloneNotSupportedException {
		SelectPager pager = null;
		try {
			pager = (SelectPager) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return pager;
	}
	 
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		if(title!=null){
			this.title = title;
		}
	}


	public String getImageName() {
		return imageName;
	}


	public void setImageName(String imageName) {
		if(imageName!=null){
			this.imageName = imageName;
		}
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		if(url!=null){
			this.url = url;
		}
	}

}
