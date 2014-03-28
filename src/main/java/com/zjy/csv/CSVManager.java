package com.zjy.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
		CSVWriter writer = null;
		try {
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
			reader = new CSVReader(new FileReader(CSV_SOURCE));
			reader.readNext();
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
