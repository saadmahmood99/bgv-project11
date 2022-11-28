package com.dataflow.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.dataflow.models.AttributeFields;
import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.MetadataHistory;

public interface AttributeService {

//	AttributeFields createnewAttribute(AttributeFields attribute_field, String attrType);
	

	public List<AttributeFieldsValue> getAllAttributeFields(String attributeType);
	
	public List<AttributeFieldsValue> getAllAttributeFieldsByEntityId(String attributeType,int entityId);

	public List<AttributeFieldsValue> createAttributeData(String attributeType,Map<String, Object> requestBody);


	public List<AttributeFieldsValue> updateAttributeData(Integer entryId, Map<String, Object> requestBody, String attributeType);

	public List<AttributeFieldsValue> deleteAttributeById(int entryId, String attributeType);
	

	public ArrayList <HashMap<String,Object>> getDownloadMetadataList();
	
	public ArrayList <HashMap<String,Object>> getMetadataList();
	
	public List<MetadataHistory> metadataManagement(MultipartFile file) throws IOException, Exception;

	StringBuilder uploadFile(String keyName, String uploadFilePath, String contentType);

	List<MetadataHistory> metadataHistory() throws Exception;

	HashMap<String, String> metataFileDownload(Map<String, Object> requestBody);

	public HashMap<String, ArrayList<HashMap<String, Object>>> getCurrentMetadata();

	
	
	
}
