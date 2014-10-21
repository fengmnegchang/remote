package com.fgj.urose.remote.sax.hanlder;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fgj.urose.remote.sax.entity.SelectPager;

public class SelectPagerHandler extends DefaultHandler{
	private static final String SELECT_PAGER = "selectpager";
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String IMAGE_NAME = "imageName";
	private static final String URL = "url";
	
	private ArrayList<SelectPager> pagers;
	private SelectPager pager;
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
		if (SELECT_PAGER.equals(localName)) {
			pagers.add(pager);
			pager = null;
		}
		if(pager!=null){
			if(TITLE.equals(localName)){
				pager.setTitle(content);
			}else if(IMAGE_NAME.equals(localName)){
				pager.setImageName(content);
			}else if(URL.equals(localName)){
				pager.setUrl(content);
			}
		}
		
		content = "";
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		pagers = new ArrayList<SelectPager>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(pagers!=null){
			if (SELECT_PAGER.equals(localName)) {
				pager = new SelectPager();
				pager.setId(Integer.parseInt(attributes.getValue(ID)));
	        }
		}
	}

	public ArrayList<SelectPager> getPagers() {
		return pagers;
	}

	public void setPagers(ArrayList<SelectPager> pagers) {
		this.pagers = pagers;
	}
	
	

}
