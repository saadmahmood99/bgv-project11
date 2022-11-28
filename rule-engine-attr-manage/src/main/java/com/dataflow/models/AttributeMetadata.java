package com.dataflow.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="attr_metadata")
public class AttributeMetadata {
    
    
    @Id
    @Column(name="ATTRIBUTE_TYPE_CODE")
    private String attributeTypeCode;



   
    @Column(name="ATTRIBUTE_TYPE")
    private String attributeType;
    
    




    public AttributeMetadata(String attributeTypeCode, String attributeType) {
        this.attributeTypeCode = attributeTypeCode;
        this.attributeType = attributeType;
    }




    public String getAttributeTypeCode() {
        return attributeTypeCode;
    }




    public void setAttributeTypeCode(String attributeTypeCode) {
        this.attributeTypeCode = attributeTypeCode;
    }




    public String getAttributeType() {
        return attributeType;
    }




    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }



   
    
    public AttributeMetadata()
    {
    }
    
}


