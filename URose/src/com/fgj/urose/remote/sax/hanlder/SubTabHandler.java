package com.fgj.urose.remote.sax.hanlder;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fgj.urose.remote.sax.entity.SubTab;

public class SubTabHandler extends DefaultHandler{
	private static final String SELECT_TAB = "tab";
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String URL = "url";
	private static final String URL_NIGHT = "urlNight";
	
	
	private static final String SUB_NAME = "subName";
	private static final String SUB_URL = "subUrl";
	private static final String SUB_ACTION = "subAction";
	
	private ArrayList<SubTab> tabs;
	private SubTab tab;
	private String content;
	
	

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		content = new String(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if(tab!=null){
			if(TITLE.equals(localName)){
				tab.setTitle(content);
			}else if(URL_NIGHT.equals(localName)){
				tab.setUrlNight(content);
			}else if(URL.equals(localName)){
				tab.setUrl(content);
			}else if(SUB_NAME.equals(localName)){
				tab.setSubName(content);
			}else if(SUB_URL.equals(localName)){
				tab.setSubUrl(content);
			}else if(SUB_ACTION.equals(localName)){
				tab.setSubAction(content);
			} 
		}
		
		if (SELECT_TAB.equals(localName)) {
			tabs.add(tab);
			tab = null;
		}
		content = "";
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		tabs = new ArrayList<SubTab>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(tabs!=null){
			if (SELECT_TAB.equals(localName)) {
				tab = new SubTab();
				tab.setId(Integer.parseInt(attributes.getValue(ID)));
	        }
		}
	}

	public ArrayList<SubTab> getTabs() {
		return tabs;
	}

	public void setTabs(ArrayList<SubTab> tabs) {
		this.tabs = tabs;
	}
	
	
	 
}
