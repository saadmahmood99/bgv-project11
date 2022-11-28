package com.dataflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.AttributeMetadata;
import com.dataflow.models.S3Properties;
@Repository
public interface S3PropertiesRepository extends JpaRepository<S3Properties, String> {

	

	@Query("select s from S3Properties s where s.bucketType = :bucketType")
	S3Properties findByBucketType(@Param("bucketType") String bucketType);
	
	
}
