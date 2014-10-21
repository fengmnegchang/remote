package com.fgj.urose.remote.sax.ksoap;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;

import com.fgj.urose.remote.sax.entity.Waterfalls;
import com.fgj.urose.remote.sax.hanlder.WaterfallHandler;

public class WaterfallService {
	private Context context;
	public WaterfallService(Context context){
		this.context = context;
	}
	
	public WaterfallService() {

	}
	
	public Waterfalls getWaters(){
		Waterfalls falls = new Waterfalls();
		try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            
            WaterfallHandler handler = new WaterfallHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(context.getAssets().open("waterfalls.xml")));
            falls.setFalls(handler.getWaters());;
            falls.setSize(handler.getWaters().size());
            
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return falls;
	}

}
