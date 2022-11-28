package com.dataflow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.AttributeMetadata;
import com.dataflow.models.MetadataHistory;
import com.dataflow.models.S3Properties;
@Repository
public interface MetadataHistoryRepository extends JpaRepository<MetadataHistory, String> {

	@Query(value = "SELECT file_id_sequence.nextval FROM dual", nativeQuery = true)
    public Integer getNextValHistorySequence();

	
}
