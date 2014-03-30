package com.zjy.scholar;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	static final Logger logger = LoggerFactory
			.getLogger(GoogleScholarReaderHelper.class);

	/** The Constant GCR_SEARCH_THSIS_URL. */
	private static final String GCR_SEARCH_THSIS_URL = "http://scholar.google.com/scholar?hl=en&q=allintitle:";

	/** The class for the Thsis element in the DOM. **/
	private static final String THSIS_ELEMENT_CLASS = "gs_ri";

	/** The class for the abstract element in the THSIS DOM. **/
	private static final String THSIS_ABSTRACT_CLASS = "gs_rs";

	/** The class for the thsis_citiedby_class element in the THSIS DOM. **/
	private static final String THSIS_CITIEDBY_CLASS = "gs_fl";

	private GoogleScholarReaderHelper() {
		// Utility Class, don't instantiate.
	}

	/**
	 * 
	 * \brief 利用Jsoup请求连接地址 ，为模拟浏览器行为添加Mozilla代理
	 * 
	 * @param url
	 * @return 返回的Document可以直接被Jsoup识别解析
	 * @throws IOException
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public static Document connect(String url){
		logger.info("开启连接");
		// 建立连接
		Connection conn = Jsoup.connect(url);
		Document document = null;
		conn.header("Referer", "http://scholar.google.com/");
		// 设置代理
		conn.userAgent("Mozilla/17.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		conn.timeout(3*1000);
		conn.method(Method.POST);
		try{
			Response response = conn.execute();
			int statusCode = response.statusCode();
			if(statusCode == 200) {
				// 获取返回信息
				document = conn.get();
				logger.info("成功获取文档信息");
			}
			else {
			    System.out.println("received error code : " + statusCode);
			}
		}catch(IOException ioe){
			logger.error(String.format("建立连接失败，错误信息:%s",ioe.toString()));
		}
		
		return document;
	}

	/**
	 * 
	 * \brief searchThsis 根据论文标题搜索相关信息
	 * 
	 * @param thsisTitle
	 * @return
	 * @throws IOException
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public static Thsis searchThsis(Thsis t) throws IOException {
		String encodedThsisTitle = t.getTitle().trim().replace(" ", "+");
		// 拼接URL
		String url = GCR_SEARCH_THSIS_URL + encodedThsisTitle;
		// 得到响应
		Document htmldoc = connect(url);
		if(htmldoc == null){
			return null;
		}
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
		if (thsisElements.size() > 0) {
			logger.warn(String.format("返回多条论文数据，size：", thsisElements.size()));
		}
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
		// 查找文本中包含的数字
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(text);
		String citedBy = "0";
		while (matcher.find()) {
			citedBy = matcher.group(0);
			break;
		}
		return citedBy;
	}
}