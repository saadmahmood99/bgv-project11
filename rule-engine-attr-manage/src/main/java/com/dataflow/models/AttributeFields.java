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
@Table(name="attr_fields")
public class AttributeFields {
    
    @Id
    @Column(name="ATTRIBUTE_FIELD_CODE")
    private String attributeFieldCode;
    
    @Column(name="ATTRIBUTE_FIELD_NAME")
    private String attributeFieldName;
    
    @Column(name="ATTRIBUTE_FIELD_TYPE")
    private String attributeFieldType;
    
    @Column(name="ATTRIBUTE_FIELD_VALIDATION")
    private String attributeFieldValidation;



   @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ATTRIBUTE_TYPE_CODE")
    private AttributeMetadata attributeTypeCode;




    public AttributeFields(String attributeFieldName, String attributeFieldCode, String attributeFieldType,
            String attributeFieldValidation, AttributeMetadata attributeTypeCode) {
        this.attributeFieldName = attributeFieldName;
        this.attributeFieldCode = attributeFieldCode;
        this.attributeFieldType = attributeFieldType;
        this.attributeFieldValidation = attributeFieldValidation;
        this.attributeTypeCode = attributeTypeCode;
    }




    public String getAttributeFieldName() {
        return attributeFieldName;
    }




    public void setAttributeFieldName(String attributeFieldName) {
        this.attributeFieldName = attributeFieldName;
    }




    public String getAttributeFieldCode() {
        return attributeFieldCode;
    }




    public void setAttributeFieldCode(String attributeFieldCode) {
        this.attributeFieldCode = attributeFieldCode;
    }




    public String getAttributeFieldType() {
        return attributeFieldType;
    }




    public void setAttributeFieldType(String attributeFieldType) {
        this.attributeFieldType = attributeFieldType;
    }




    public String getAttributeFieldValidation() {
        return attributeFieldValidation;
    }




    public void setAttributeFieldValidation(String attributeFieldValidation) {
        this.attributeFieldValidation = attributeFieldValidation;
    }




    public AttributeMetadata getAttributeTypeCode() {
        return attributeTypeCode;
    }




    public void setAttributeTypeCode(AttributeMetadata attributeTypeCode) {
        this.attributeTypeCode = attributeTypeCode;
    }
    
    
    public AttributeFields()
    {



   }
    



}
