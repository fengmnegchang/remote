package com.fgj.urose.remote.sax.hanlder;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fgj.urose.remote.sax.entity.MainAdertise;
import com.fgj.urose.remote.sax.entity.MainMenu;

public class MainAdertiseHandler extends DefaultHandler{
	private static final String SELECT_ADVERT = "advertise";
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String IMAGE_NAME = "imageName";
	private static final String URL = "url";
	
	private ArrayList<MainAdertise> adverts;
	private MainAdertise advert;
	private String content;
	
	private ArrayList<MainMenu> menus;
	private MainMenu menu;
	private static final String SELECT_MENU = "menu";
	private static final String SUB_NAME = "subName";
	private static final String SUB_URL = "subUrl";
	private static final String SUB_ACTION = "subAction";

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
		
		if(advert!=null){
			if(TITLE.equals(localName)){
				advert.setTitle(content);
			}else if(IMAGE_NAME.equals(localName)){
				advert.setImageName(content);
			}else if(URL.equals(localName)){
				advert.setUrl(content);
			}
		}
		
		if (SELECT_ADVERT.equals(localName)) {
			adverts.add(advert);
			advert = null;
		}
		
		if(menu!=null){
			if(TITLE.equals(localName)){
				menu.setTitle(content);
			}else if(IMAGE_NAME.equals(localName)){
				menu.setImageName(content);
			}else if(URL.equals(localName)){
				menu.setUrl(content);
			}else if(SUB_NAME.equals(localName)){
				menu.setSubName(content);
			}else if(SUB_URL.equals(localName)){
				menu.setSubUrl(content);
			}else if(SUB_ACTION.equals(localName)){
				menu.setSubAction(content);
			}
		}
		
		if (SELECT_MENU.equals(localName)) {
			menus.add(menu);
			menu = null;
		}
		content = "";
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		adverts = new ArrayList<MainAdertise>();
		menus = new ArrayList<MainMenu>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if(adverts!=null){
			if (SELECT_ADVERT.equals(localName)) {
				advert = new MainAdertise();
				advert.setId(Integer.parseInt(attributes.getValue(ID)));
	        }
		}
		
		if(menus!=null){
			if (SELECT_MENU.equals(localName)) {
				menu = new MainMenu();
				menu.setId(Integer.parseInt(attributes.getValue(ID)));
	        }
		}
	}

	public ArrayList<MainAdertise> getAdverts() {
		return adverts;
	}

	public void setAdverts(ArrayList<MainAdertise> adverts) {
		this.adverts = adverts;
	}

	public ArrayList<MainMenu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<MainMenu> menus) {
		this.menus = menus;
	}
}
