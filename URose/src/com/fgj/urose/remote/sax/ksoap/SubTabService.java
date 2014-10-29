package com.fgj.urose.remote.sax.ksoap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.fgj.urose.remote.sax.entity.MainTab;
import com.fgj.urose.remote.sax.hanlder.SubTabHandler;

public class SubTabService {
	private Context context;
	public SubTabService(Context context){
		this.context = context;
	}
	
	public SubTabService() {

	}
	
	public MainTab getTabs(){
		MainTab tabs = new MainTab();
		try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            
            SubTabHandler handler = new SubTabHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(context.getAssets().open("main_tab.xml")));
            tabs.setTabs(handler.getTabs());
            tabs.setSize(handler.getTabs().size());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return tabs;
	}

}
