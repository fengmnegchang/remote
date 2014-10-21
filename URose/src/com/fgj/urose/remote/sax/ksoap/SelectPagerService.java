package com.fgj.urose.remote.sax.ksoap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.fgj.urose.remote.sax.entity.WelcomePager;
import com.fgj.urose.remote.sax.hanlder.SelectPagerHandler;

public class SelectPagerService {
	private Context context;
	public SelectPagerService(Context context){
		this.context = context;
	}
	
	public SelectPagerService() {

	}
	
	public WelcomePager getWelcomePager(){
		WelcomePager welPager = new WelcomePager();
		try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            
            SelectPagerHandler handler = new SelectPagerHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(context.getAssets().open("welcome_pager.xml")));
            welPager.setPagers(handler.getPagers());
            welPager.setSize(handler.getPagers().size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return welPager;
	}

}
