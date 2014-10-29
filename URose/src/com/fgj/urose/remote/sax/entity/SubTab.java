package com.fgj.urose.remote.sax.entity;

import java.io.Serializable;


public class SubTab implements Serializable,Cloneable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title;
	private String url;
	private String urlNight;
	
	private String subName;
	private String subUrl;
	private String subAction;
	
	public SubTab(){
		
	}

	@Override
	public SubTab clone() throws CloneNotSupportedException {
		SubTab tab= null;
		try {
			tab = (SubTab) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return tab;
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

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		if(url!=null){
			this.url = url;
		}
	}

	public String getUrlNight() {
		return urlNight;
	}

	public void setUrlNight(String urlNight) {
		if(urlNight!=null){
			this.urlNight = urlNight;
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
