package com.dataflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.AttributeMetadata;
@Repository
public interface AttributeMetadataRepository extends JpaRepository<AttributeMetadata, String> {

	@Query("select am from AttributeMetadata am where am.attributeTypeCode= :attributeTypeCode")
	AttributeMetadata findByAttributeTypeCode(@Param("attributeTypeCode") String attributeTypeCode);
	
	
}
