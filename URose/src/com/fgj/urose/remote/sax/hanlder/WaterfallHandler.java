package com.fgj.urose.remote.sax.hanlder;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fgj.urose.remote.sax.entity.Waterfall;

public class WaterfallHandler extends DefaultHandler{
	private static final String SELECT_WATER = "waterfall";
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String IMAGE_NAME = "imageName";
	private static final String URL = "url";
	private static final String SUB_NAME = "subName";
	private static final String SUB_URL = "subUrl";
	private static final String SUB_ACTION = "subAction";
	
	
	private ArrayList<Waterfall> waters;
	private Waterfall water;
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
		if(water!=null){
			if(TITLE.equals(localName)){
				water.setTitle(content);
			}else if(IMAGE_NAME.equals(localName)){
				water.setImageName(content);
			}else if(URL.equals(localName)){
				water.setUrl(content);
			}else if(SUB_NAME.equals(localName)){
				water.setSubName(content);
			}else if(SUB_URL.equals(localName)){
				water.setSubUrl(content);
			}else if(SUB_ACTION.equals(localName)){
				water.setSubAction(content);
			}
		}
		if (SELECT_WATER.equals(localName)) {
			waters.add(water);
			water = null;
		}
		 
		content = "";
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		waters = new ArrayList<Waterfall>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(waters!=null){
			if (SELECT_WATER.equals(localName)) {
				water = new Waterfall();
				water.setId(Integer.parseInt(attributes.getValue(ID)));
	        }
		}
	}

	public ArrayList<Waterfall> getWaters() {
		return waters;
	}

	public void setWaters(ArrayList<Waterfall> waters) {
		this.waters = waters;
	}
	
	
 
}
