package com.wizecommerce.cts.utils;

import java.util.Date;

import org.w3c.dom.Element;

public class ChangeRecord {
	private int crId;
	private String sourceName;
	private String subSourceName;
	private String description;
	private Date datetime;
	private int sourceId;
	private int subSourceId;
	private String status;
	private int sourceDatetime;
	
	public ChangeRecord(){}
	
	public ChangeRecord(int cr_id, String source_name, String sub_source_name, String description, Date datetime, int source_id, int sub_source_id, String status, int source_datetime) {
		this.crId=cr_id;
		this.sourceName=source_name;
		this.subSourceName=sub_source_name;
		this.description=description;
		this.datetime=datetime;
		this.sourceId=source_id;
		this.subSourceId=sub_source_id;
		this.status=status;
		this.sourceDatetime=source_datetime;
	}
	
	public ChangeRecord(Element commonNode) throws Exception{
		
		this.sourceName		= commonNode.getAttribute("source_name");
		this.subSourceName	= commonNode.getAttribute("sub_source_name");
		this.description	= commonNode.getAttribute("description");
		this.sourceId		= Integer.parseInt(commonNode.getAttribute("source_id"));
		this.subSourceId	= Integer.parseInt(commonNode.getAttribute("sub_source_id"));
		this.status			= commonNode.getAttribute("status");
		
		System.out.println("Date --------- " + commonNode.getAttribute("source_datetime"));
		this.sourceDatetime	= Integer.parseInt(commonNode.getAttribute("source_datetime"));
	}
	
	public int getCrId(){
		return crId;
	}
	public void setCrId(int cr_id){
		this.crId=cr_id;
	}
	public String getSourceName(){
		return sourceName;
	}
	public void setSourceName(String source_name){
		this.sourceName=source_name;
	}
	public String getSubSourceName(){
		return subSourceName;
	}
	public void setSubSourceName(String sub_source_name){
		this.subSourceName=sub_source_name;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public Date getDatetime(){
		return datetime;
	}
	public void setDatetime(Date datetime){
		this.datetime=datetime;
	}
	public int getSourceId(){
		return sourceId;
	}
	public void setSourceId (int source_id){
		this.sourceId=source_id;
	}
	public int getSubSourceId(){
		return subSourceId;
	}
	public void setSubSourceId(int sub_source_id){
		this.subSourceId=sub_source_id;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public int getSourceDatetime(){
		return sourceDatetime;
	}
	
	public String getSourceDatetimeString(){
		return new Integer(sourceDatetime).toString();
	}
	
	public void setSourceDatetime(int source_datetime){
		this.sourceDatetime=source_datetime;
	}
}