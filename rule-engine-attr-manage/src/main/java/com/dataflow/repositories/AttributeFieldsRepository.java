package com.dataflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataflow.models.AttributeFields;
import com.dataflow.models.AttributeFieldsValue;
@Repository
public interface AttributeFieldsRepository extends JpaRepository<AttributeFields, String> {

	@Query("select afv from AttributeFields afv where afv.attributeTypeCode.attributeTypeCode = :attributeTypeCode")
	 List<AttributeFields> findByAttributeTypeCode(@Param("attributeTypeCode") String attributeTypeCode);
	
	
}
