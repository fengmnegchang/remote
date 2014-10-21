package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;


public class Waterfall implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String imageName;
	private String url;
	
	private String subName;
	private String subUrl;
	private String subAction;
	
	
	
	public Waterfall(){
		
	}

	@Override
	public Waterfall clone() throws CloneNotSupportedException {
		Waterfall fall = null;
		try {
			fall = (Waterfall) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return fall;
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

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		if(subName!=null){
			this.subName = subName;
		}
	}

	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		if(subUrl!=null){
			this.subUrl = subUrl;
		}
	}

	public String getSubAction() {
		return subAction;
	}

	public void setSubAction(String subAction) {
		if(subAction!=null){
			this.subAction = subAction;
		}
	}

	
}
