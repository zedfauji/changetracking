package com.wizecommerce.cts.utils;

import java.util.Date;

public class Source {
	private int sourceId;
	private String sourceName;
	private int isActive;
	private Date createdDate;
	private Date lastModifiedDatetime;
	
	public Source(){}
	
	public Source(int source_id, String source_name, int is_active, Date created_date, Date last_modified_datetime) {
		this.setSourceId(source_id);
		this.setSourceName(source_name);
		this.setIsActive(is_active);
		this.setCreatedDate(created_date);
		this.setLastModifiedDatetime(last_modified_datetime);
	}

	public String getSourceId() {
		return new Integer(sourceId).toString();
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getIsActive() {
		return new Integer(isActive).toString();
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDatetime() {
		return lastModifiedDatetime;
	}

	public void setLastModifiedDatetime(Date lastModifiedDatetime) {
		this.lastModifiedDatetime = lastModifiedDatetime;
	}
}
