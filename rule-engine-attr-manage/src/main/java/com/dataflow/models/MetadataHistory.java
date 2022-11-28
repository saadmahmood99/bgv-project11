package com.dataflow.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="metadata_history")
public class MetadataHistory {
    
    @Id
    @Column(name="history_id")
    private Integer historyId;
    
    @Column(name="created_by")
    private String createdBy;
    
    @Column(name="created_on")
    private Date created_on;
    
    @Column(name="status")
    private String status;

    @Column(name="file_name")
    private String fileName;

    @Column(name="s3_file_name")
    private String s3FileName;

    @Column(name="s3_url")
    private String s3Url;

    @Column(name="response_file_name")
    private String responseFileName;

    @Column(name="s3_response_file_name")
    private String s3ResponseFileName;

    @Column(name="s3_response_url")
    private String s3ResponseUrl;


	public Integer getHistoryId() {
		return historyId;
	}


	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreated_on() {
		return created_on;
	}


	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getS3FileName() {
		return s3FileName;
	}


	public void setS3FileName(String s3FileName) {
		this.s3FileName = s3FileName;
	}


	public String getS3Url() {
		return s3Url;
	}


	public void setS3Url(String s3Url) {
		this.s3Url = s3Url;
	}


	public String getResponseFileName() {
		return responseFileName;
	}


	public void setResponseFileName(String responseFileName) {
		this.responseFileName = responseFileName;
	}


	public String getS3ResponseFileName() {
		return s3ResponseFileName;
	}


	public void setS3ResponseFileName(String s3ResponseFileName) {
		this.s3ResponseFileName = s3ResponseFileName;
	}


	public String getS3ResponseUrl() {
		return s3ResponseUrl;
	}


	public void setS3ResponseUrl(String s3ResponseUrl) {
		this.s3ResponseUrl = s3ResponseUrl;
	}


	public MetadataHistory()
    {



   }
    



}
