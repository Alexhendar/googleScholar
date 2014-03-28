package com.zjy.domain;

/**
 * 
 * @brief Thsis.java 论文实体类
 * @attention 使用注意事项
 * @author zhangjunyong
 * @date 2014年3月28日
 * @note begin modify by 修改人 修改时间 修改内容摘要说明
 */
public class Thsis {
	// 会议名称
	private String meeting;
	// 论文标题
	private String title;
	// 作者信息
	private String authors;
	// 论文引用
	private String citiedBy;
	// 摘要
	private String abstractInfo;

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getCitiedBy() {
		return citiedBy;
	}

	public void setCitiedBy(String citiedBy) {
		this.citiedBy = citiedBy;
	}

	public String getAbstractInfo() {
		return abstractInfo;
	}

	public void setAbstractInfo(String abstractInfo) {
		this.abstractInfo = abstractInfo;
	}
}
