package com.dataflow.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="attr_fields_value")
public class AttributeFieldsValue  {
    
    @Id
    @Column(name="ENTRY_UNIQUE_ID")
    private String entryUniqueId;
    
	@Column(name="ENTRY_ID")
    private Integer entryId;
    

    @ManyToOne(fetch = FetchType.EAGER)  
    @JoinColumn(name = "ATTRIBUTE_FIELD_CODE")  
    private AttributeFields attributeFields;
    
    @Column(name="ATTRIBUTE_FIELD_VALUE")
    private String attributeFieldValue;


    @Column(name="STATUS")
    private String status;
    

   public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




@Column(name="CREATED_ON")
    private Date createdOn;



   @Column(name="MODIFIED_ON")
    private Date modifiedOn;
    
    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="MODIFIED_BY")
    private String modifiedBy;



    public Date getCreatedOn() {
        return createdOn;
    }




    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }




    public Date getModifiedOn() {
        return modifiedOn;
    }




    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }



   public String getCreatedBy() {
        return createdBy;
    }




    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }




    public String getModifiedBy() {
        return modifiedBy;
    }




    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }




    public AttributeFieldsValue(AttributeFields attributeFields, String attributeFieldValue , Date createdOn , Date modifiedOn , String createdBy , String modifiedBy) {
        this.attributeFields = attributeFields;
        this.attributeFieldValue = attributeFieldValue;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
    }
    
    public AttributeFieldsValue()
    {
    }




    public AttributeFields getAttributeFields() {
        return attributeFields;
    }




    public void setAttributeFields(AttributeFields attributeFields) {
        this.attributeFields = attributeFields;
    }




    public String getAttributeFieldValue() {
        return attributeFieldValue;
    }




    public void setAttributeFieldValue(String attributeFieldValue) {
        this.attributeFieldValue = attributeFieldValue;
    }




    @Override
    public String toString() {
        return "AttributeFieldsValue [attributeFields=" + attributeFields + ", attributeFieldValue="
                + attributeFieldValue + "]";
    }




	public String getEntryUniqueId() {
		return entryUniqueId;
	}




	public void setEntryUniqueId(String entryUniqueId) {
		this.entryUniqueId = entryUniqueId;
	}




	public Integer getEntryId() {
		return entryId;
	}




	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
    
    
    
    
    
    
  



}