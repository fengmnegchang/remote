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
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.util.Log;

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
		for (int i = 2; i <= pagersize; i++) {
			getTagsContent("http://www.umei.cc/p/gaoqing/rihan/index-" + i + ".htm", waterlist);
		}

		try {
			StringBuilder content = new StringBuilder();
			content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
			content.append("<waterfalls pagersize=\"" + pagersize + "\" currentpager=\"" + currentpager + "\"  size=\"" + size + "\">" + "\n");
			int i = 1;
			for (Waterfall fall : waterlist) {
				content.append("    <waterfall id=\"" + i + "\">\n");
				content.append("        <title>" + fall.getTitle() + "</title>\n");
				content.append("        <imageName>" + fall.getImageName() + "</imageName>\n");
				content.append("        <url>" + fall.getUrl() + "</url>\n");
				content.append("        <subName>" + "" + "</subName>\n");
				content.append("        <subUrl>http://www.umei.cc" + fall.getSubUrl() + "</subUrl>\n");
				content.append("        <subAction></subAction>\n");
				content.append("        <date>" + fall.getDate() + "</date>\n");
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

	public Waterfalls getHtmlDetail(String url) {
		Waterfalls falls = new Waterfalls();
		ArrayList<Waterfall> waterlist = new ArrayList<Waterfall>();
		getDetailTagsContent(URL, waterlist);
		falls.setCurrentpager(currentpager);
		falls.setPagersize(pagersize);
		falls.setSize(size);
		falls.setFalls(waterlist);
		return falls;
	}

	private void getDetailTagsContent(String url, ArrayList<Waterfall> waterlist) {
		String html = getHtmlString(url);
		try {
			Parser parser = Parser.createParser(html, "UTF-8");
			// 页数
			NodeFilter pagefilter = new HasAttributeFilter("class", "NewPages");
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
						String content = atag.toPlainTextString().replace(" ", "");
						if (content.equals("下一页")) {
							pagersize = Integer.parseInt(content);
						}
					}

				} catch (Exception e) {
					// 当前页面
					Node span = (Node) anodeList.elementAt(i);
					String content = span.toPlainTextString().replace(" ", "");
					if (!content.endsWith("")) {
						currentpager = Integer.parseInt(content);
					}
				}
			}
			// 内容
			Parser contentparser = Parser.createParser(html, "UTF-8");
			NodeFilter attrfilter = new HasAttributeFilter("class", "TypeList");
			NodeFilter lifilter = new TagNameFilter("li");
			NodeList nodeList = contentparser.extractAllNodesThatMatch(attrfilter).extractAllNodesThatMatch(lifilter);
			int size = nodeList.size();
			/**
			 * <li>
			 * <a href="http://www.umei.cc/p/gaoqing/rihan/16207.htm"
			 * class="TypeBigPics" target="_blank"><img
			 * src="http://i1.umei.cc/uploads/tu/201609/543/c1.jpg" width="180"
			 * height="270" /><div class="ListTit">[RQ-STAR]2015.07.13 Rina Itoh
			 * いとうりな Race Queen</div></a> <div class="TypePicInfos"> <div
			 * class="TypePicTags"><a href='/tags/zhifu.htm' title=制服>制服</a><a
			 * href='/tags/RQ_Star.htm' title=RQ-Star>RQ-Star</a><a
			 * href='/tags/meinv.htm' title=美女>美女</a></div> <div
			 * class="txtInfo gray"><em class="IcoList">查看：111次</em>
			 * <em class="IcoTime">09-14</em></div> </div></li>
			 * **/
			Waterfall fall;
			for (int i = 0; i < size; i++) {
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
		} catch (Exception e) {

		}
	}

	public static Waterfalls getHtmlFalls(String url) {
		Waterfalls falls = new Waterfalls();
		ArrayList<Waterfall> waterlist = new ArrayList<Waterfall>();
		try {
			Log.i("getHtmlFalls", "URL==" + url);
			getTagsContent(url, waterlist);
			// for(int i=2;i<=pagersize;i++){
			// getTagsContent("http://www.umei.cc/p/gaoqing/rihan/index-"+i+".htm",
			// waterlist);
			// }
			falls.setFalls(waterlist);
		} catch (Exception e) {
		}
		return falls;
	}

	public static void getTagsContent(String urlStr, ArrayList<Waterfall> waterlist) {
		String html = getHtmlString(urlStr);
		try {
			// Parser parser = Parser.createParser(html, "UTF-8");

			/**
			 * <div class="pages"> <a
			 * href="http://www.umei.cc/p/gaoqing/rihan/index-1.htm#">共2918个</a>
			 * <a href="http://www.umei.cc/p/gaoqing/rihan/index-1.htm#">上一个</a>
			 * <a href="http://www.umei.cc/p/gaoqing/rihan/index-2.htm">下一个</a>
			 * <span class="current">1</span> <a
			 * href="http://www.umei.cc/p/gaoqing/rihan/index-2.htm">2</a>
			 * **/
			// 页数
			// NodeFilter pagefilter = new HasAttributeFilter("class",
			// "NewPages");
			// NodeList pageList = parser.extractAllNodesThatMatch(pagefilter);
			//
			// Node anode = pageList.elementAt(0);
			// NodeList anodeList = anode.getChildren();
			// int anodesize = anodeList.size();
			// for (int i = 0; i < anodesize; i++) {
			// try {
			// LinkTag atag = (LinkTag) anodeList.elementAt(i);
			// System.out.println(atag.getAttribute("href"));
			// System.out.println(atag.toPlainTextString());
			// if (i == 0) {
			// // 共计个数
			// String content = atag.toPlainTextString().replace("共",
			// "").replace("个", "");
			// size = Integer.parseInt(content);
			// }
			//
			// if (i > 3) {
			// // 最大页数
			// String content = atag.toPlainTextString().replace(" ","");
			// pagersize = Integer.parseInt(content);
			// }
			//
			// } catch (Exception e) {
			// // 当前页面
			// Node span = (Node) anodeList.elementAt(i);
			// String content = span.toPlainTextString().replace(" ","");
			// if(!content.endsWith("")){
			// currentpager = Integer.parseInt(content);
			// }
			// }
			// }

			// 内容
			Parser contentparser = Parser.createParser(html, "UTF-8");
			// NodeFilter attrfilter = new HasAttributeFilter("class", "t");
			NodeFilter attrfilter = new TagNameFilter("li");
			NodeList nodeList = contentparser.extractAllNodesThatMatch(attrfilter);
			int size = nodeList.size();
			/**
			 * 
			<li><a href="http://m.umei.cc/p/gaoqing/rihan/16207.htm" class="New-PL_blank">
			<img alt="[RQ-STAR]2015.07.13 Rina Itoh いとうりな Race Queen" lazysrc="http://i1.umei.cc/uploads/tu/201609/543/c1.jpg">
			<div class="New-PL-tit">[RQ-STAR]2015.07.13 Rina Itoh いとうりな Race Queen</div></a></li>
			 */

			Waterfall fall;
			if (size > 1) {
				for (int i = 1; i < size; i++) {
					fall = new Waterfall();
					Node divnode = nodeList.elementAt(i);
					try {
						LinkTag ahref = (LinkTag) divnode.getChildren().elementAt(0);
						if(ahref!=null){
							// A href
							fall = new Waterfall();
							System.out.println(ahref.getAttribute("href"));
							fall.setUrl(ahref.getAttribute("href"));
							fall.setSubUrl(ahref.getAttribute("href"));
						}
						
						try {
							// IMG
							ImageTag img = (ImageTag) ahref.getChildren().elementAt(0);
							if(img!=null){
								System.out.println(img.getAttribute("lazysrc"));
								fall.setUrl(img.getAttribute("lazysrc"));
							}
							
							try {
								ImageTag img2 = (ImageTag) ahref.getChildren().elementAt(0);
								if(img2!=null){
									System.out.println(img.getAttribute("alt"));
									fall.setTitle(img.getAttribute("alt"));
									waterlist.add(fall);
								}
								
							} catch (Exception e) {
								// TODO: handle exception
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} catch (Exception e) {
					}

					
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
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();

			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			System.out.println("httpsURLConnection.getResponseCode()" + httpsURLConnection.getResponseCode());
			if (httpsURLConnection.getResponseCode() == 200) {
				inputStream = httpsURLConnection.getInputStream();
				// inputStream = context.getAssets().open(
				// "www.umei.cc.htm");
				bw = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = bw.readLine()) != null) {
					sb.append(line);
//					System.out.println(line);
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
