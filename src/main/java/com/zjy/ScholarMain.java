package com.zjy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zjy.csv.CSVManager;
import com.zjy.domain.Thsis;
import com.zjy.scholar.GoogleScholarReaderHelper;

/**
 * 
 * @brief ScholarMain.java ScholarMain测试系统功能
 * @attention 使用注意事项
 * @author zhangjunyong
 * @date 2014年3月28日
 * @note begin modify by 修改人 修改时间 修改内容摘要说明
 */
public class ScholarMain {
	public static void main(String[] args) {
		Random random = new Random();
		// 从指定文件读取信息
		List<String[]> inputList = CSVManager.readCSVContentAll();
		List<String[]> outputList = new ArrayList<String[]>();
		for (String[] strArr : inputList) {
			String meeting = strArr[0];
			String title = strArr[1];
			String authors = strArr[2];
			// 解析数据为论文类
			Thsis t = new Thsis();
			t.setMeeting(meeting);
			t.setTitle(title);
			t.setAuthors(authors);

			// 查询论文引用和摘要
			try {
				// 每次查询都间隔随机的时间
				Thread.sleep(random.nextInt(4)*1000);
				t = GoogleScholarReaderHelper.searchThsis(t);
				String[] outArr = new String[5];
				outArr[0] = t.getMeeting();
				outArr[1] = t.getTitle();
				outArr[2] = t.getAuthors();
				outArr[3] = t.getCitiedBy();
				outArr[4] = t.getAbstractInfo();
				outputList.add(outArr);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
			
		}
	}
}
