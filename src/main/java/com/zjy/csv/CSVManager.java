package com.zjy.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * 
 * @brief CSVManager.java CSV操作类，读写CSV文件
 * @attention 使用注意事项
 * @author zhangjunyong
 * @date 2014年3月28日
 * @note begin modify by 修改人 修改时间 修改内容摘要说明
 */
public class CSVManager {
	static final Logger logger = LoggerFactory.getLogger(CSVManager.class);

	/** 输入CSV文件路径 */
	private static final String CSV_SOURCE = "D:/workspace/Paper_50000.csv";

	/** 输出CSV文件路径 */
	private static final String CSV_DST = "D:/workspace/output2.csv";

	/**
	 * 
	 * \brief 将信息写入到指定的CSV文件
	 * 
	 * @param data
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public static void writeCSV(List<String[]> data) {
		File file = new File(CSV_DST);
		if(file.exists()){
			// 如果文件已存在，先删除
			try {
				Files.delete(file.toPath());
			} catch (IOException e) {
				logger.error(String.format("刪除已存在文件[%s]失败", CSV_DST));
			}
		}
		CSVWriter writer = null;
		try {
			
			logger.info(String.format("将信息追加到指定文件：%s", CSV_DST));
			// 将信息追加到制定文件
			writer = new CSVWriter(new FileWriter(CSV_DST, true));
			writer.writeAll(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * \brief 读取CSV文件全部信息
	 * 
	 * @return
	 * @attention 方法的使用注意事项
	 * @author zhangjunyong
	 * @date 2014年3月28日
	 * @note begin modify by 修改人 修改时间 修改内容摘要说明
	 */
	public static List<String[]> readCSVContentAll() {
		CSVReader reader = null;
		List<String[]> list = null;
		try {
			// 读取指定文件中的信息
			reader = new CSVReader(new FileReader(CSV_SOURCE));
			// 跳过首行header信息
			reader.readNext();
			// 读取剩余所有信息内容
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(String.format("未找到指定输入文件：%s", CSV_SOURCE));
		} catch (IOException e) {
			logger.error(String.format("读取：%s 文件出错;错误信息：%s", CSV_SOURCE,e.toString()));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info(String.format("读取信息完成：%s", CSV_SOURCE));
		return list;
	}
}
