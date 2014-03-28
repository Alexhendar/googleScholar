package com.zjy.scholar;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zjy.cookie.CookieManager;
import com.zjy.domain.Thsis;

/**
 * 
 * @brief GoogleScholarReaderHelper.java
 * @attention 使用注意事项
 * @author zhangjunyong
 * @date 2014年3月28日
 * @note begin modify by 修改人 修改时间 修改内容摘要说明
 */
public final class GoogleScholarReaderHelper {

	/** The Constant GCR_SEARCH_THSIS_URL. */
	private static final String GCR_SEARCH_THSIS_URL = "http://scholar.google.com/scholar?hl=en&as_occt=title&q=allintitle:";

	/** The class for the Thsis element in the DOM. **/
	private static final String THSIS_ELEMENT_CLASS = "gs_ri";

	/** The class for the abstract element in the THSIS DOM. **/
	private static final String THSIS_ABSTRACT_CLASS = "gs_rs";

	/** The class for the thsis_citiedby_class element in the THSIS DOM. **/
	private static final String THSIS_CITIEDBY_CLASS = "gs_fl";
	/**
	 * SESSIONID 默认为空
	 */
	private static String SESSIONID = "";

	private GoogleScholarReaderHelper() {
		// Utility Class, don't instantiate.
	}

	/**
	 * 
	 * \brief 利用Jsoup请求连接地址，为保持会话需要添加SESSIONID信息 ，为模拟浏览器行为添加Mozilla代理
	 * 
	 * @param url
	 * @return 返回的Document可以直接被Jsoup识别解析
	 * @throws IOException
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public static Document connect(String url) throws IOException {
		Connection conn = Jsoup.connect(url);
		conn.userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		conn.method(Method.POST);
		Document document = conn.get();
		return document;
	}
//	public static Document connect(String url) throws IOException {
//		String sessionID = SESSIONID;
//		Connection conn = null;
//		if (sessionID != null && !"".equals(sessionID)) {
//			conn = Jsoup.connect(url)
//					.cookie(CookieManager.SESSIONID, sessionID);
//		} else {
//			conn = Jsoup.connect(url);
//		}
//		if (conn == null) {
//			throw new IOException("连接失败");
//		}
//		conn.userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
//		conn.method(Method.POST);
//		Response response = conn.execute();
//		Map<String, String> cookies = response.cookies();
//		sessionID = cookies.get(CookieManager.SESSIONID);
//		System.out.println(sessionID);
//		if (!SESSIONID.equalsIgnoreCase(sessionID)) {
//			SESSIONID = sessionID;
//		}
//		Document document = conn.get();
//		return document;
//	}

	/**
	 * 
	 * \brief searchThsis 根据论文标题搜索相关信息
	 * @param thsisTitle
	 * @return
	 * @throws IOException
	 * @attention 方法的使用注意事项 
	 * @author zhangjunyong
	 * @date 2014年3月28日 
	 * @note  begin modify by 修改人 修改时间   修改内容摘要说明
	 */
	public static Thsis searchThsis(Thsis t) throws IOException {
		String encodedThsisTitle = t.getTitle().trim().replace(" ", "+");
		// 拼接URL
		String url = GCR_SEARCH_THSIS_URL + encodedThsisTitle;
		// 得到响应
		Document htmldoc = connect(url);
		// 得到论文信息
		Elements thsisElements = htmldoc
				.getElementsByClass(THSIS_ELEMENT_CLASS);

		for (Element e : thsisElements) {
			t.setAbstractInfo(getAbstract(e));
			t.setCitiedBy(getCitedBy(e));
			// 只要第一个
			break;
		}
		// 得到论文数大于1，日志记录
//		if (thsisElements.size() > 0) {
//			System.out.println(t.getTitle() + "size > 1");
//		}
		return t;
	}

	/**
	 * 
	 * \brief 获取论文摘要信息，getAbstract根据传入的论文Element，解析摘要信息
	 * 
	 * @param e
	 * @return
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	private static String getAbstract(Element e) {
		return e.getElementsByClass(THSIS_ABSTRACT_CLASS).text();
	}

	/**
	 * 
	 * \brief 获取论文引用值，getCitedBy
	 * 
	 * @param e
	 * @return
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	private static String getCitedBy(Element e) {
		Elements fl = e.getElementsByClass(THSIS_CITIEDBY_CLASS);
		String text = fl.select("a[href]").first().text();
		if(text.length() > 8){
			return text.substring(8, text.length());
		}else{
			return "-1";
		}
		
	}
}