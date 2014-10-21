package com.fgj.urose.remote.sax.ksoap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.fgj.urose.remote.sax.entity.MainOnline;
import com.fgj.urose.remote.sax.hanlder.MainAdertiseHandler;

public class MainAdvertiseService {
	private Context context;
	public MainAdvertiseService(Context context){
		this.context = context;
	}
	
	public MainAdvertiseService() {

	}
	
	public MainOnline getMainOnline(){
		MainOnline online = new MainOnline();
		try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            
            MainAdertiseHandler handler = new MainAdertiseHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(context.getAssets().open("main_advertise.xml")));
            online.setAdverts(handler.getAdverts());
            online.setSize(handler.getAdverts().size());
            
            online.setMenus(handler.getMenus());
            online.setMenuSize(handler.getMenus().size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return online;
	}

}
