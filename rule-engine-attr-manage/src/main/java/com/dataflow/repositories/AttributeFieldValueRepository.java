package com.dataflow.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataflow.models.AttributeFields;
import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.AttributeMetadata;
@Repository
public interface AttributeFieldValueRepository extends JpaRepository<AttributeFieldsValue, String> {
	
	@Query("select afv from AttributeFieldsValue afv where afv.attributeFields.attributeTypeCode.attributeTypeCode= :attributeType")
	 List<AttributeFieldsValue> findByAttributeType(@Param("attributeType") String attributeType);
	
	@Query("select afv from AttributeFieldsValue afv where afv.attributeFields.attributeTypeCode.attributeTypeCode= :attributeType and afv.entryId= :entryId")
	 List<AttributeFieldsValue> findByAttributeTypeEntryId(@Param("attributeType") String attributeType,@Param("entryId") String entryId);
	
	@Query(value = "SELECT entry_id_sequence.nextval FROM dual", nativeQuery = true)
	    public Integer getNextValMySequence();
	
	@Query("select afv from AttributeFieldsValue afv where afv.entryId= :entryId")
	 List<AttributeFieldsValue> findByEntityId(@Param("entryId") Integer entryId);
	
	@Query("select afv from AttributeFieldsValue afv where afv.attributeFields.attributeTypeCode.attributeTypeCode= :attributeType and afv.entryId= :entityId")
	 List<AttributeFieldsValue> findByAttributeTypeByEntityId(@Param("attributeType") String attributeType,@Param("entityId") int entityId);
	
	
}
