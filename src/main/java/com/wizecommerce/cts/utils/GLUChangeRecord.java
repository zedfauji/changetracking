package com.wizecommerce.cts.utils;

import java.util.Date;

import org.w3c.dom.Element;

public class GLUChangeRecord {
	
	private int gluId;
	private int crId;
	private String subSourceName;
	private String details;
	private Date datetime;
	private int sourceId;
	private int subSourceId;
	private String status;
	private Date sourceDatetime;
	
	
	public GLUChangeRecord(){}
	
	public void customChangeRecord(Element customNode) {
		System.out.println("----------IN GLUChangeRecord class------------");
	}

	public int getGluId() {
		return gluId;
	}

	public void setGluId(int gluId) {
		this.gluId = gluId;
	}

	public int getCrId() {
		return crId;
	}

	public void setCrId(int crId) {
		this.crId = crId;
	}

	public String getSubSourceName() {
		return subSourceName;
	}

	public void setSubSourceName(String subSourceName) {
		this.subSourceName = subSourceName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getSubSourceId() {
		return subSourceId;
	}

	public void setSubSourceId(int subSourceId) {
		this.subSourceId = subSourceId;
	}

	public Date getSourceDatetime() {
		return sourceDatetime;
	}

	public void setSourceDatetime(Date sourceDatetime) {
		this.sourceDatetime = sourceDatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
