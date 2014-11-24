package com.fgj.urose.remote.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.fgj.urose.remote.sax.entity.Waterfall;
import com.fgj.urose.remote.sax.entity.Waterfalls;


public class UmeiHtmlParse {
	private static String URL = "http://www.umei.cc/p/gaoqing/rihan/index-1.htm";
	private static int pagersize;
	private static int currentpager;
	private static int size;
	
	public static void main(String[] args) {

		ArrayList<Waterfall> waterlist = new ArrayList<Waterfall>();
		
		getTagsContent(URL, waterlist);
		for(int i=2;i<=pagersize;i++){
			getTagsContent("http://www.umei.cc/p/gaoqing/rihan/index-"+i+".htm", waterlist);
		}
		
	   try {
        	StringBuilder content = new StringBuilder();
            content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+"\n");
            content.append("<waterfalls pagersize=\""+pagersize+"\" currentpager=\""+currentpager+"\"  size=\""+size+"\">"+"\n");
            int i = 1;
            for(Waterfall fall:waterlist){
                content.append("    <waterfall id=\""+i+"\">\n");
                content.append("        <title>"+fall.getTitle()+"</title>\n");
                content.append("        <imageName>"+fall.getImageName()+"</imageName>\n");
                content.append("        <url>"+fall.getUrl()+"</url>\n");
                content.append("        <subName>"+""+"</subName>\n");
                content.append("        <subUrl>http://www.umei.cc"+fall.getSubUrl()+"</subUrl>\n");
                content.append("        <subAction></subAction>\n");
                content.append("        <date>"+fall.getDate()+"</date>\n");
                content.append("    </waterfall>\n");
                i++;
            }
            content.append("</waterfalls>");
            File fileName = new File("C:\\waterfalls.xml"); 
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.toString().getBytes("utf-8")); 
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	public Waterfalls getHtmlDetail(String url){
		Waterfalls falls = new Waterfalls();
		ArrayList<Waterfall> waterlist = new ArrayList<Waterfall>();
		getDetailTagsContent(URL, waterlist);
		falls.setCurrentpager(currentpager);
		falls.setPagersize(pagersize);
		falls.setSize(size);
		falls.setFalls(waterlist);
		return falls;
	}
	
	private void getDetailTagsContent(String url,
			ArrayList<Waterfall> waterlist) {
		String html = getHtmlString(url);
		try {
			Parser parser = Parser.createParser(html, "GBK");
			// 页数
			NodeFilter pagefilter = new HasAttributeFilter("class", "pages");
			NodeList pageList = parser.extractAllNodesThatMatch(pagefilter);

			Node anode = pageList.elementAt(1);
			NodeList anodeList = anode.getChildren();
			int anodesize = anodeList.size();
			for (int i = 0; i < anodesize; i++) {
				try {
					LinkTag atag = (LinkTag) anodeList.elementAt(i);
					System.out.println(atag.getAttribute("href"));
					System.out.println(atag.toPlainTextString());
					if (i == 0) {
						// 共计个数
						String content = atag.toPlainTextString().replace("上一页", "");
						size = Integer.parseInt(content);
					}

					if (i > 3) {
						// 最大页数
						String content = atag.toPlainTextString().replace(" ","");
						if(content.equals("下一页")){
							pagersize = Integer.parseInt(content);
						}
					}

				} catch (Exception e) {
					// 当前页面
					Node span = (Node) anodeList.elementAt(i);
					String content = span.toPlainTextString().replace(" ","");
					if(!content.endsWith("")){
						currentpager = Integer.parseInt(content);
					}
				}
			}
			// 内容
			Parser contentparser = Parser.createParser(html, "GBK");
			NodeFilter attrfilter = new HasAttributeFilter("class", "image_box");
			NodeList nodeList = contentparser
					.extractAllNodesThatMatch(attrfilter);
			int size = nodeList.size();
			/**
			 * <div class="img_box">
			 * <a href="http://www.umei.cc/pic_l.htm?http://i7.umei.cc//img2012/2014/11/14/012xiezhen/yukata-004.jpg&.htm" target="_blank" title="[(コスプレ写真集)[kibashi(キバシ)]「とある魔術の禁書目録 神裂火織 yukata」[106P]">
			 * <img class="IMG_show" border="0" src="./[(コスプレ写真集)[kibashi(キバシ)]「とある魔術の禁書目録 神裂火織 yukata」[106P]_files/yukata-004.jpg" alt="[(コスプレ写真集)[kibashi(キバシ)]「とある魔術の禁書目録 神裂火織 yukata」[106P]" width="1200" height="1804" style="cursor: url(http://img.baidu.com/img/baike/editor/zoomin.cur), pointer;">
			 * </a>
			 * </div>
			 * <div class="clear"></div>
			 * **/
			Waterfall fall;
			for(int i=0;i<size;i++){
				fall = new Waterfall();
				LinkTag atag = null;
				try {
					Node node = nodeList.elementAt(i);
					atag = (LinkTag) node.getFirstChild();
					System.out.println(atag.getAttribute("href"));
					System.out.println(atag.getAttribute("title"));
					System.out.println(atag.toPlainTextString());
					fall.setTitle(atag.getAttribute("title"));
					fall.setSubUrl(atag.getAttribute("href"));
				} catch (Exception e) {
				}
				try {
					ImageTag img = (ImageTag) atag.getFirstChild();
					System.out.println(img.getAttribute("alt"));
					System.out.println(img.getAttribute("src"));
					fall.setUrl(img.getAttribute("src"));
					fall.setImageName(img.getAttribute("alt"));
				} catch (Exception e) {
				}
				waterlist.add(fall);
			}
		}catch(Exception e){
			
		}	
	}
	public static Waterfalls getHtmlFalls(){
		Waterfalls falls = new Waterfalls();
		ArrayList<Waterfall> waterlist = new ArrayList<Waterfall>();
		try {
			getTagsContent(URL, waterlist);
//			for(int i=2;i<=pagersize;i++){
//				getTagsContent("http://www.umei.cc/p/gaoqing/rihan/index-"+i+".htm", waterlist);
//			}	
			falls.setFalls(waterlist);
		} catch (Exception e) {
		}
		return falls;
	}

	public static void getTagsContent(String urlStr, ArrayList<Waterfall> waterlist) {
		String html = getHtmlString(urlStr);
		try {
			Parser parser = Parser.createParser(html, "GBK");

			/**
			 * <div class="pages"> <a
			 * href="http://www.umei.cc/p/gaoqing/rihan/index-1.htm#">共2918个</a>
			 * <a href="http://www.umei.cc/p/gaoqing/rihan/index-1.htm#">上一个</a>
			 * <a href="http://www.umei.cc/p/gaoqing/rihan/index-2.htm">下一个</a>
			 * <span class="current">1</span> <a
			 * href="http://www.umei.cc/p/gaoqing/rihan/index-2.htm">2</a>
			 * **/
			// 页数
			NodeFilter pagefilter = new HasAttributeFilter("class", "pages");
			NodeList pageList = parser.extractAllNodesThatMatch(pagefilter);

			Node anode = pageList.elementAt(0);
			NodeList anodeList = anode.getChildren();
			int anodesize = anodeList.size();
			for (int i = 0; i < anodesize; i++) {
				try {
					LinkTag atag = (LinkTag) anodeList.elementAt(i);
					System.out.println(atag.getAttribute("href"));
					System.out.println(atag.toPlainTextString());
					if (i == 0) {
						// 共计个数
						String content = atag.toPlainTextString().replace("共", "").replace("个", "");
						size = Integer.parseInt(content);
					}

					if (i > 3) {
						// 最大页数
						String content = atag.toPlainTextString().replace(" ","");
						pagersize = Integer.parseInt(content);
					}

				} catch (Exception e) {
					// 当前页面
					Node span = (Node) anodeList.elementAt(i);
					String content = span.toPlainTextString().replace(" ","");
					if(!content.endsWith("")){
						currentpager = Integer.parseInt(content);
					}
				}
			}

			// 内容
			Parser contentparser = Parser.createParser(html, "GBK");
			NodeFilter attrfilter = new HasAttributeFilter("class", "t");
			NodeList nodeList = contentparser
					.extractAllNodesThatMatch(attrfilter);
			int size = nodeList.size();
			/**
			 * 
			 * <DIV class=t> <DIV class="hover bar"> <DIV class=heartbtn> <A
			 * class="heart nologin"
			 * href="/p/gaoqing/rihan/20141106191522.htm"><SPAN>heart</SPAN></A>
			 * </DIV> <DIV class=title> <A
			 * href="/p/gaoqing/rihan/20141106191522.htm" title=
			 * "[RQ-STAR] NO.00099 Keiko Inagaki 稲垣慶子 Private Dress [99P]"
			 * >[RQ-STAR] NO.00099 Keiko Inagaki 稲垣慶子 Private Dress [99P]</A>
			 * </DIV> <DIV class=info>2014-11-6（99P）</DIV> </DIV> 
			 * <A
			 * href="/p/gaoqing/rihan/20141106191522.htm" title=
			 * "[RQ-STAR] NO.00099 Keiko Inagaki 稲垣慶子 Private Dress [99P]"
			 * target=_blank> <IMG class=img title=
			 * "[RQ-STAR] NO.00099 Keiko Inagaki 稲垣慶子 Private Dress [99P]"
			 * src="http://s.umei.cc/small/files/s4731.jpg" height=300> </A>
			 * </DIV>
			 * 
			 */
			
			Waterfall fall;
			if (size > 1) {
				for (int i = 1; i < size; i++) {
					fall = new Waterfall();
					Node divnode = nodeList.elementAt(i);
					NodeList subchild = divnode.getChildren();

					try {
						// DIV class="hover bar
						Node hover = subchild.elementAt(0);
						NodeList hoverList = hover.getChildren();
						for (int j = 0; j < hoverList.size(); j++) {
							// 0 heartbtn;1 class=title;2 class=info
							Node subNode = hoverList.elementAt(j);
							if (j == 0) {
								LinkTag subhref = (LinkTag) subNode.getChildren().elementAt(0);
								System.out.println(subhref.getAttribute("href"));
							} else if (j == 1) {
								LinkTag subhref = (LinkTag) subNode.getChildren().elementAt(0);
								System.out.println(subhref.getAttribute("href"));
								System.out.println(subhref.getAttribute("title"));
								fall.setSubUrl(subhref.getAttribute("href"));
								fall.setTitle(subhref.getAttribute("title"));
							} else if (j == 2) {
								System.out.println(subNode.toPlainTextString());
								fall.setDate(subNode.toPlainTextString());
							}
						}
					} catch (Exception e) {}

					try {
						// A href
						LinkTag ahref = (LinkTag) subchild.elementAt(1);
						System.out.println(ahref.getAttribute("href"));
						System.out.println(ahref.getAttribute("title"));

						// IMG
						ImageTag img = (ImageTag) ahref.getChildren().elementAt(0);
						System.out.println(img.getAttribute("title"));
						System.out.println(img.getAttribute("src"));
						fall.setUrl(img.getAttribute("src"));
						fall.setImageName(img.getAttribute("title"));
					} catch (Exception e) {}
					
					waterlist.add(fall);
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public static String getHtmlString(String urlpath) {
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader bw = null;
		URL url = null;
		InputStream inputStream;
		try {
			url = new URL(urlpath);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url
					.openConnection();

			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			System.out.println("httpsURLConnection.getResponseCode()"
					+ httpsURLConnection.getResponseCode());
			if (httpsURLConnection.getResponseCode() == 200) {
				inputStream = httpsURLConnection.getInputStream();
				// inputStream = context.getAssets().open(
				// "www.umei.cc.htm");
				bw = new BufferedReader(new InputStreamReader(inputStream,
						"GBK"));
				while ((line = bw.readLine()) != null) {
					sb.append(line);
					System.out.println(line);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
