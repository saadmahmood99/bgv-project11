package com.dataflow.models;

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
@Table(name="s3_properties")
public class S3Properties {
    
    @Id
    @Column(name="BUCKET_TYPE")
    private String bucketType;
    
    @Column(name="BUCKET_NAME")
    private String bucketName;
    
    @Column(name="REGION")
    private String region;
    
    @Column(name="ACCESS_KEY_ID")
    private String accessKeyId;

    @Column(name="SECRET_ACCESS_KEY")
    private String secretAccessKey;








	public String getBucketType() {
		return bucketType;
	}






	public void setBucketType(String bucketType) {
		this.bucketType = bucketType;
	}






	public String getBucketName() {
		return bucketName;
	}






	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}






	public String getRegion() {
		return region;
	}






	public void setRegion(String region) {
		this.region = region;
	}






	public String getAccessKeyId() {
		return accessKeyId;
	}






	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}






	public String getSecretAccessKey() {
		return secretAccessKey;
	}






	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}






	public S3Properties()
    {



   }
    



}
