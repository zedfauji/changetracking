package com.wizecommerce.cts.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubSource {
	private int subSourceId;
	private int sourceId;
	private String subSourceName;
	private int isActive;
	private Date createdDate;
	private Date lastModifiedDatetime;
	private String credentialDetails;
	private Date adminDate;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SubSource() {}
	
	public SubSource(int sub_source_id, int source_id, String sub_source_name, 
			int is_active, Date created_date, Date last_modified_datetime,
			String credential_details, Date admin_date){
		
		this.setSubSourceId(sub_source_id);
		this.setSourceId(source_id);
		this.setSubSourceName(sub_source_name);
		this.setIsActive(is_active);
		this.setCreatedDate(created_date);
		this.setLastModifiedDatetime(last_modified_datetime);
		this.setAdminDate(admin_date);
	}

	public String getSubSourceId() {
		return new Integer(subSourceId).toString();
	}
	
	public String getSourceId() {
		return new Integer(sourceId).toString();
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	
	public void setSubSourceId(int subSourceId) {
		this.subSourceId = subSourceId;
	}

	public String getSubSourceName() {
		return subSourceName;
	}

	public void setSubSourceName(String subSourceName) {
		this.subSourceName = subSourceName;
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

	public String getAdminDate() {
		return df.format(adminDate);
	}

	public void setAdminDate(Date adminDate) {
		this.adminDate = adminDate;
	}

	public String getCredentialDetails() {
		return credentialDetails;
	}

	public void setCredentialDetails(String credentialDetails) {
		this.credentialDetails = credentialDetails;
	}
}
