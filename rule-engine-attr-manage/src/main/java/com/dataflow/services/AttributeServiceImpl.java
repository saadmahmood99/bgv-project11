package com.dataflow.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dataflow.constants.Constants;
import com.dataflow.models.AttributeFields;
import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.AttributeMetadata;
import com.dataflow.models.MetadataHistory;
import com.dataflow.models.S3Properties;
import com.dataflow.repositories.AttributeFieldValueRepository;
import com.dataflow.repositories.AttributeFieldsRepository;
import com.dataflow.repositories.AttributeMetadataRepository;
import com.dataflow.repositories.MetadataHistoryRepository;
import com.dataflow.repositories.S3PropertiesRepository;
import com.amazonaws.regions.Regions;



@Service
public class AttributeServiceImpl implements AttributeService {
	
	@Autowired
	private AttributeFieldValueRepository attributeFieldsValueRepo;

	
	@Autowired
	private AttributeFieldsRepository attributeFieldsRepo;
	
	
	@Autowired
	private AttributeMetadataRepository attributeMetadataRepo;
	

	@Autowired
	private S3PropertiesRepository s3PropertiesRepo;
	
	@Autowired
	private MetadataHistoryRepository metadataHistoryRepo;
	
	
	
	
	@Autowired
	private Validation validation;
	

	public static final String uploadingdir = System.getProperty("user.dir") + File.separator + "uploadingdir"
			+ File.separator;
	
//	@Override
//	public AttributeFields createnewAttribute(AttributeFields attribute_field, String attrType) {
//		return null;
//	}



	@Override
	public List<AttributeFieldsValue> getAllAttributeFields(String attributeType) {
		List<AttributeFieldsValue> AttributeFieldsValue = attributeFieldsValueRepo.findByAttributeType(attributeType);
		return AttributeFieldsValue;
	}
	
	
	@Override
	public List<AttributeFieldsValue> getAllAttributeFieldsByEntityId(String attributeType,int entityId) {
		List<AttributeFieldsValue> AttributeFieldsValue = attributeFieldsValueRepo.findByAttributeTypeByEntityId(attributeType,entityId);
		return AttributeFieldsValue;
	}
	
	@Override
	public List<AttributeFieldsValue> createAttributeData(String attributeType,Map<String, Object> requestBody) {
		String createdBy = (String) requestBody.get(Constants.CREATED_BY);
		String modifiedBy =(String)  requestBody.get(Constants.MODIFIED_BY);
		ArrayList<AttributeFieldsValue> listAttributeFieldsValue = new ArrayList<AttributeFieldsValue>();
		Integer entryId=attributeFieldsValueRepo.getNextValMySequence();
		for (Map.Entry<String,String> entry : ((Map<String, String> )requestBody.get(Constants.DATA)).entrySet()) 
		{
			AttributeFields attributeFeilds = new AttributeFields();
			attributeFeilds.setAttributeFieldCode(entry.getKey());
			AttributeFieldsValue insertDataAttributeFieldsValue= new AttributeFieldsValue();
			insertDataAttributeFieldsValue.setAttributeFieldValue(entry.getValue());
			insertDataAttributeFieldsValue.setEntryId(entryId);
			String entryUniqueId=  entry.getKey()+"_"+String.valueOf(entryId);
			insertDataAttributeFieldsValue.setEntryUniqueId(entryUniqueId);
			insertDataAttributeFieldsValue.setAttributeFields(attributeFeilds);
			insertDataAttributeFieldsValue.setCreatedOn(new Date());
			insertDataAttributeFieldsValue.setCreatedBy(createdBy);
			insertDataAttributeFieldsValue.setModifiedBy(modifiedBy);
			insertDataAttributeFieldsValue.setStatus(Constants.STATUS_ACTIVE);
			insertDataAttributeFieldsValue.setModifiedOn(new Date());
			listAttributeFieldsValue.add(insertDataAttributeFieldsValue);
			

		}
		attributeFieldsValueRepo.saveAll(listAttributeFieldsValue);
		List<AttributeFieldsValue> AttributeFieldsValue = attributeFieldsValueRepo.findByEntityId(entryId);
		return AttributeFieldsValue;
	}

		@Override
		public List<AttributeFieldsValue> updateAttributeData(Integer entryId, Map<String, Object> requestBody, String attributeType) {
			String createdBy = (String) requestBody.get(Constants.CREATED_BY);
			String modifiedBy =(String)  requestBody.get(Constants.MODIFIED_BY);
			ArrayList<AttributeFieldsValue> listAttributeFieldsValue = new ArrayList<AttributeFieldsValue>();
			for (Map.Entry<String,String> entry : ((Map<String, String> )requestBody.get(Constants.DATA)).entrySet()) 
			{
				AttributeFields attributeFeilds = new AttributeFields();
				attributeFeilds.setAttributeFieldCode(entry.getKey());
				AttributeFieldsValue insertDataAttributeFieldsValue= new AttributeFieldsValue();
				insertDataAttributeFieldsValue.setAttributeFieldValue(entry.getValue());
				insertDataAttributeFieldsValue.setEntryId(entryId);
				String entryUniqueId=  entry.getKey()+"_"+String.valueOf(entryId);
				insertDataAttributeFieldsValue.setEntryUniqueId(entryUniqueId);
				insertDataAttributeFieldsValue.setAttributeFields(attributeFeilds);
				insertDataAttributeFieldsValue.setCreatedOn(new Date());
				insertDataAttributeFieldsValue.setCreatedBy(createdBy);
				insertDataAttributeFieldsValue.setModifiedBy(modifiedBy);
				insertDataAttributeFieldsValue.setStatus(Constants.STATUS_ACTIVE);
				insertDataAttributeFieldsValue.setModifiedOn(new Date());
				listAttributeFieldsValue.add(insertDataAttributeFieldsValue);

			}
		
			attributeFieldsValueRepo.saveAll(listAttributeFieldsValue);
		List<AttributeFieldsValue> AttributeFieldsValue = attributeFieldsValueRepo.findByEntityId(entryId);
		return AttributeFieldsValue;
	}


 

	@Override
	public List<AttributeFieldsValue> deleteAttributeById(int entryId,String attributeType) {
		List<AttributeFieldsValue> AttributeFieldsValue = attributeFieldsValueRepo.findByEntityId(entryId);
		
		for (AttributeFieldsValue attributeFeildValue : AttributeFieldsValue)
		{
			attributeFeildValue.setStatus(Constants.STATUS_DELETE);
		}
		attributeFieldsValueRepo.saveAll(AttributeFieldsValue);

		return AttributeFieldsValue;
				
	}
	
	
	
	
	
	public ArrayList <HashMap<String,Object>> getDownloadMetadataList()
	{
		
	    ArrayList <HashMap<String,Object>> responseFormatting = new   ArrayList <HashMap<String,Object>> ();

	    
	    List<AttributeFields> listAttributeFields = attributeFieldsRepo.findAll();
	    
	    List<AttributeMetadata>  listAttributeMetadata = attributeMetadataRepo.findAll();
	    
	    for (AttributeMetadata attributeMetadata : listAttributeMetadata)
	    {
	        HashMap<String,Object> mappingColumn = new HashMap<String,Object>();
	    	String codeAttrMetadata= attributeMetadata.getAttributeTypeCode();
	    	String outputColumns="";
	    	mappingColumn.put("attribute", attributeMetadata.getAttributeType());
	    for (AttributeFields attributeFields : listAttributeFields)
	    {
	    	
	    	if(codeAttrMetadata.equals(attributeFields.getAttributeTypeCode().getAttributeTypeCode()))
	    	{
	    		
	    		outputColumns = outputColumns + attributeFields.getAttributeFieldName() +',';
	    		
	    	}

	    	
	    	
	    	
	    }
    	mappingColumn.put("Columns", outputColumns);
    	
    	responseFormatting.add(mappingColumn);

	    
	    
	    }
		return responseFormatting;
	}
	
	
	public ArrayList <HashMap<String,Object>> getMetadataList()
	{
		
	    ArrayList <HashMap<String,Object>> responseFormatting = new   ArrayList <HashMap<String,Object>> ();

	    
	    List<AttributeFields> listAttributeFields = attributeFieldsRepo.findAll();
	    
	    List<AttributeMetadata>  listAttributeMetadata = attributeMetadataRepo.findAll();
	    
	    for (AttributeMetadata attributeMetadata : listAttributeMetadata)
	    {
	        HashMap<String,Object> mappingColumn = new HashMap<String,Object>();
	    	String codeAttrMetadata= attributeMetadata.getAttributeTypeCode();
	    	ArrayList <HashMap<String,String>> outputData = new ArrayList <HashMap<String,String>>();
	    	mappingColumn.put("attributeName", attributeMetadata.getAttributeType());
	    	mappingColumn.put("attributeCode", attributeMetadata.getAttributeTypeCode());
	    for (AttributeFields attributeFields : listAttributeFields)
	    {
	    	HashMap<String,String> columnsMapping = new HashMap<String,String>();
	    	if(codeAttrMetadata.equals(attributeFields.getAttributeTypeCode().getAttributeTypeCode()))
	    	{
	    		
	    		columnsMapping.put("columnName", attributeFields.getAttributeFieldName());
	    		columnsMapping.put("columnCode", attributeFields.getAttributeFieldCode());
	    		columnsMapping.put("columnType", attributeFields.getAttributeFieldType());
	    		columnsMapping.put("columnValidation", attributeFields.getAttributeFieldValidation());
	    		outputData.add(columnsMapping);
	    		
	    	}

	    	
	    	
	    	
	    }
    	mappingColumn.put("columns", outputData);
    	
    	responseFormatting.add(mappingColumn);

	    
	    
	    }
		return responseFormatting;
	}
	
	@Override
	public List<MetadataHistory> metadataManagement(MultipartFile file) throws Exception {
	
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());

    	if(!validation.validation(extension))
    	{
    		throw new Exception("File Format is incorrect only JSON files are allowed");   
    	}
    	
		
		byte[] fileupload = file.getBytes();
		JSONObject jsonData;
		try 
		{
    	jsonData=new JSONObject(new String(fileupload));
		}
		catch(Exception e)
		{
    		throw new Exception("File uploaded do not contain valid json");   
		}
		
		
		StringBuilder code = new StringBuilder(generateTaskCode());
		code.append(Long.valueOf(System.currentTimeMillis()).toString());
		File theDir = new File(uploadingdir);

		// // if the directory does not exist, create it
		if (!theDir.exists()) {
			try {
				theDir.mkdir();
			} catch (SecurityException se) {
			}
		} 

		String[] fileNameSplits = file.getOriginalFilename().split("\\.");
		int extensionIndex = fileNameSplits.length - 1;
		String newName = code.toString() + "." + fileNameSplits[extensionIndex];
		File filedata = new File(uploadingdir + file.getOriginalFilename());
		file.transferTo(filedata);
		StringBuilder s3url = uploadFile(newName,
				uploadingdir + file.getOriginalFilename(), file.getContentType());
		
		FileUtils.cleanDirectory(theDir);

		
		MetadataHistory metadataHistory= new MetadataHistory();
		
		metadataHistory.setCreatedBy("user");
		metadataHistory.setCreated_on(new Date());
		metadataHistory.setFileName(file.getOriginalFilename());
		metadataHistory.setS3FileName(newName);
		metadataHistory.setS3Url(s3url.toString());
		metadataHistory.setStatus("SUCCESS");
	
    	JSONArray dataUser = jsonData.getJSONArray("metadata");
    	
    	for (int i= 0;i<dataUser.length();i++)
    	{	   
    		Boolean flagExecution = true;

    		JSONObject objects = dataUser.getJSONObject(i);
    		
    		if(!validation.mandatoryAttribute(objects,"actionAttribute"))
    		{ 
    			flagExecution=false;
				objects.put("responseAttribute", "actionAttribute is mandatory parameter at attribute level");
    		}
    		
    		
    		if(!validation.mandatoryAttribute(objects,"attributeCode"))
    		{
    			flagExecution=false;
				objects.put("responseAttribute", "attributeCode is mandatory parameter at attribute level");
    		}
    		
    		
    		
    		
    		String actionAttribute = objects.getString("actionAttribute");
    		
    		String attributeName =null;
    		
    		if(actionAttribute.equalsIgnoreCase("update") || actionAttribute.equalsIgnoreCase("add"))
    		{
    			if(!validation.mandatoryAttribute(objects,"attributeName"))
        		{

        			flagExecution=false;
    				objects.put("responseAttribute","attributeName is mandatory parameter at attribute level for action update,add");
        		}
    		}
    		
    		if(validation.mandatoryAttribute(objects, "attributeName"))
    		{
			attributeName = objects.getString("attributeName");
    		}

    		
    		String codeMetadata= objects.getString("attributeCode");
    	
 
            
    			
    		List<AttributeFields> listAttributeFields = attributeFieldsRepo.findByAttributeTypeCode(codeMetadata);

    		AttributeMetadata attributeMetada = attributeMetadataRepo.findByAttributeTypeCode(codeMetadata);

    		JSONArray  attributeColumns= objects.getJSONArray("attributeColumns");
    		if(flagExecution)
    		{
    		switch(actionAttribute.toUpperCase()) 
    		{
    		case "ADD":
    			    if(attributeMetada==null || attributeMetada.getAttributeType()==null)
    			    {
    				AttributeMetadata attributeMetadata = new AttributeMetadata();
    				
    				attributeMetadata.setAttributeType(attributeName);
    				attributeMetadata.setAttributeTypeCode(codeMetadata);
    				
    				attributeMetadataRepo.save(attributeMetadata);
    				
    				objects.put("responseAttribute", attributeName+" created successfully");
    				
    				for(int k=0; k < attributeColumns.length() ;k++)
    				{
     					JSONObject attributeColumnObject = (JSONObject) attributeColumns.get(k);
     					
     					if(!validation.mandatoryAttribute(attributeColumnObject,"columnCode"))
    	        		{
     						flagExecution = false;
          					attributeColumnObject.put("reponseColumn","columnCode is mandatory parameter at columnCode level");

    	        		}
    					
    					if(!validation.mandatoryAttribute(attributeColumnObject,"actionColumn"))
    	        		{
     						flagExecution = false;
          					attributeColumnObject.put("reponseColumn","actionColumn is mandatory parameter at columnCode level");

    	        		}
    					
    					
    					String columnCode = attributeColumnObject.getString("columnCode");
    					
    					String columnAction = attributeColumnObject.getString("actionColumn");
    					
    					
    					
    		    		if(columnAction.equalsIgnoreCase("update"))
    		    		{
    		    			if(! (validation.mandatoryAttribute(attributeColumnObject,"columnName") || validation.mandatoryAttribute(attributeColumnObject,"columnValidation") || validation.mandatoryAttribute(attributeColumnObject,"columnType")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn","one of columnName,columnValidation,columnType column level attribute is mandatory for column action update");

    		        		}
    		    		}

     					String columnName = null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnName"))
     					{
     						flagExecution = false;
     						columnName= attributeColumnObject.getString("columnName");
     					}
     					
                       
     					String columnType= null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnType"))
		        		{
         					columnType = attributeColumnObject.getString("columnType");

     						if(!(columnType.equalsIgnoreCase("int") || columnType.equalsIgnoreCase("string") || columnType.equalsIgnoreCase("date")  || columnType.equalsIgnoreCase("boolean")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn","columnType can only be INT,STRING,BOOLEAN,DATE");

    		        		}
     					}
     					
     					String columnValidation = null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnValidation"))
		        		{
     						columnValidation = attributeColumnObject.getString("columnValidation");

     						if(!(columnValidation.equalsIgnoreCase("NONE")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn", "columnValidation can only be NONE" );
    		        		}
     					}
     					if(flagExecution)
     					{
     						switch(columnAction.toUpperCase()) {
  						  case "NONE":
  							attributeColumnObject.put("reponseColumn", "No Changes in Column" );
  						    break;
  						  case "ADD":
  							boolean existing =false;
  							for(AttributeFields attributeList : listAttributeFields)
  							{
  								 if(attributeList.getAttributeFieldCode().equals(columnCode))
  								 {
  									 existing= true; 
  								 }
  								
  							}
  							if(existing)
  							{
      						attributeColumnObject.put("reponseColumn", "Column already existing" );
  							}
  							else
  							{
  							AttributeFields attributefield = new AttributeFields();
          					attributeColumnObject.put("reponseColumn", "Column created Successfully" );
          					attributefield.setAttributeFieldCode(columnCode);
          					attributefield.setAttributeFieldName(columnName);
          					attributefield.setAttributeFieldType(columnType);
          					attributefield.setAttributeFieldValidation(columnValidation);
          					attributefield.setAttributeTypeCode(attributeMetadata);
          					attributeFieldsRepo.save(attributefield);
  							}
  						    break;
  						  case "DELETE":
  							  boolean existing1 =false;
    							for(AttributeFields attributeList : listAttributeFields)
    							{
    								 if(attributeList.getAttributeFieldCode().equals(columnCode))
    								 {
    									existing1= true; 
    								 }
    								
    							}
    							if(existing1)
    							{
    								AttributeFields attributefield = new AttributeFields();
              					attributefield.setAttributeFieldCode(columnCode);
              					attributefield.setAttributeFieldName(columnName);
              					attributefield.setAttributeFieldType(columnType);
              					attributefield.setAttributeFieldValidation(columnValidation);
              					attributefield.setAttributeTypeCode(attributeMetadata);
        						attributeColumnObject.put("reponseColumn", "Column deleted Successfully" );
        						attributeFieldsRepo.delete(attributefield);
    							}
    							else
    							{
            					attributeColumnObject.put("reponseColumn", "Column Not Existing" );
    							}
    						    break; 
  						  case "UPDATE": 
  							  boolean existing2 =false;
      							for(AttributeFields attributeList : listAttributeFields)
      							{
      								 if(attributeList.getAttributeFieldCode().equals(columnCode))
      								 {
      									 existing2= true; 
      								 }
      								
      							}
      							if(existing2)
      							{
      							AttributeFields attributefield = new AttributeFields();
                					attributefield.setAttributeFieldCode(columnCode);
                					attributefield.setAttributeFieldName(columnName);
                					attributefield.setAttributeFieldType(columnType);
                					attributefield.setAttributeFieldValidation(columnValidation);
                					attributefield.setAttributeTypeCode(attributeMetadata);
          						attributeColumnObject.put("reponseColumn", "Column Updated Successfully" );
          						attributeFieldsRepo.save(attributefield);
      							}
      							else
      							{
              					attributeColumnObject.put("reponseColumn", "Column Not Existing" );
      							}
      						    break; 
  						  default:
        						attributeColumnObject.put("reponseColumn", "Action against column can only be NONE,ADD,UPDATE,DELETE" );

  						} 
    				}

     				}
    			    }
    			    else
    			    {
            		objects.put("responseAttribute", attributeName+" already existing");
    			    }
    			break;
    		case "DELETE" :
    			 if(attributeMetada==null || attributeMetada.getAttributeType()==null)
    			 {
         			objects.put("responseAttribute", attributeName+" does not exist");
    			 }
    			 else
    			 {

    				 
    				 if(listAttributeFields!=null && listAttributeFields.size()>0)
    						 {
    					 attributeFieldsRepo.deleteAll(listAttributeFields);
    						 }

      				AttributeMetadata attributeMetadata = new AttributeMetadata();
      				
      				attributeMetadata.setAttributeType(attributeName);
      				attributeMetadata.setAttributeTypeCode(codeMetadata);
      				
      				attributeMetadataRepo.delete(attributeMetadata);
      				
      				objects.put("responseAttribute", attributeName+" attribute and corresponding columns deleted successfully");
 

    			 }
    			break;
    		case "UPDATE" :
    			 if(attributeMetada==null || attributeMetada.getAttributeType()==null)
    			 {
         			objects.put("responseAttribute", attributeName+" does not exist");
    			 }
    			 else
    			 {

     				AttributeMetadata attributeMetadata = new AttributeMetadata();
     				
     				attributeMetadata.setAttributeType(attributeName);
     				attributeMetadata.setAttributeTypeCode(codeMetadata);
     				
     				attributeMetadataRepo.save(attributeMetadata);
     				
     				objects.put("responseAttribute", attributeName+" updated successfully");
     				
     				for(int k=0; k < attributeColumns.length() ;k++)
     				{
     					JSONObject attributeColumnObject = (JSONObject) attributeColumns.get(k);
     					
     					if(!validation.mandatoryAttribute(attributeColumnObject,"columnCode"))
    	        		{
     						flagExecution = false;
          					attributeColumnObject.put("reponseColumn","columnCode is mandatory parameter at columnCode level");

    	        		}
    					
    					if(!validation.mandatoryAttribute(attributeColumnObject,"actionColumn"))
    	        		{
     						flagExecution = false;
          					attributeColumnObject.put("reponseColumn","actionColumn is mandatory parameter at columnCode level");

    	        		}
    					
    					
    					String columnCode = attributeColumnObject.getString("columnCode");
    					
    					String columnAction = attributeColumnObject.getString("actionColumn");
    					
    					
    					
    		    		if(columnAction.equalsIgnoreCase("update"))
    		    		{
    		    			if(! (validation.mandatoryAttribute(attributeColumnObject,"columnName") || validation.mandatoryAttribute(attributeColumnObject,"columnValidation") || validation.mandatoryAttribute(attributeColumnObject,"columnType")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn","one of columnName,columnValidation,columnType column level attribute is mandatory for column action update");

    		        		}
    		    		}

     					String columnName = null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnName"))
     					{
     						flagExecution = false;
     						columnName= attributeColumnObject.getString("columnName");
     					}
     					
                       
     					String columnType= null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnType"))
		        		{
         					columnType = attributeColumnObject.getString("columnType");

     						if(!(columnType.equalsIgnoreCase("int") || columnType.equalsIgnoreCase("string") || columnType.equalsIgnoreCase("date")  || columnType.equalsIgnoreCase("boolean")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn","columnType can only be INT,STRING,BOOLEAN,DATE");

    		        		}
     					}
     					
     					String columnValidation = null;
     					
     					if(validation.mandatoryAttribute(attributeColumnObject,"columnValidation"))
		        		{
     						columnValidation = attributeColumnObject.getString("columnValidation");

     						if(!(columnValidation.equalsIgnoreCase("NONE")))
    		        		{
         						flagExecution = false;
              					attributeColumnObject.put("reponseColumn", "columnValidation can only be NONE" );
    		        		}
     					}
     					if(flagExecution)
     					{
     						switch(columnAction.toUpperCase()) {
  						  case "NONE":
  							attributeColumnObject.put("reponseColumn", "No Changes in Column" );
  						    break;
  						  case "ADD":
  							boolean existing =false;
  							for(AttributeFields attributeList : listAttributeFields)
  							{
  								 if(attributeList.getAttributeFieldCode().equals(columnCode))
  								 {
  									 existing= true; 
  								 }
  								
  							}
  							if(existing)
  							{
      						attributeColumnObject.put("reponseColumn", "Column already existing" );
  							}
  							else
  							{
  							AttributeFields attributefield = new AttributeFields();
          					attributeColumnObject.put("reponseColumn", "Column created Successfully" );
          					attributefield.setAttributeFieldCode(columnCode);
          					attributefield.setAttributeFieldName(columnName);
          					attributefield.setAttributeFieldType(columnType);
          					attributefield.setAttributeFieldValidation(columnValidation);
          					attributefield.setAttributeTypeCode(attributeMetadata);
          					attributeFieldsRepo.save(attributefield);
  							}
  						    break;
  						  case "DELETE":
  							  boolean existing1 =false;
    							for(AttributeFields attributeList : listAttributeFields)
    							{
    								 if(attributeList.getAttributeFieldCode().equals(columnCode))
    								 {
    									existing1= true; 
    								 }
    								
    							}
    							if(existing1)
    							{
    								AttributeFields attributefield = new AttributeFields();
              					attributefield.setAttributeFieldCode(columnCode);
              					attributefield.setAttributeFieldName(columnName);
              					attributefield.setAttributeFieldType(columnType);
              					attributefield.setAttributeFieldValidation(columnValidation);
              					attributefield.setAttributeTypeCode(attributeMetadata);
        						attributeColumnObject.put("reponseColumn", "Column deleted Successfully" );
        						attributeFieldsRepo.delete(attributefield);
    							}
    							else
    							{
            					attributeColumnObject.put("reponseColumn", "Column Not Existing" );
    							}
    						    break; 
  						  case "UPDATE": 
  							  boolean existing2 =false;
      							for(AttributeFields attributeList : listAttributeFields)
      							{
      								 if(attributeList.getAttributeFieldCode().equals(columnCode))
      								 {
      									 existing2= true; 
      								 }
      								
      							}
      							if(existing2)
      							{
      							AttributeFields attributefield = new AttributeFields();
                					attributefield.setAttributeFieldCode(columnCode);
                					attributefield.setAttributeFieldName(columnName);
                					attributefield.setAttributeFieldType(columnType);
                					attributefield.setAttributeFieldValidation(columnValidation);
                					attributefield.setAttributeTypeCode(attributeMetadata);
          						attributeColumnObject.put("reponseColumn", "Column Updated Successfully" );
          						attributeFieldsRepo.save(attributefield);
      							}
      							else
      							{
              					attributeColumnObject.put("reponseColumn", "Column Not Existing" );
      							}
      						    break; 
  						  default:
        						attributeColumnObject.put("reponseColumn", "Action against column can only be NONE,ADD,UPDATE,DELETE" );

  						} 
    				}

     				}
     			    
    			 }
    			break;
    		case "NONE" :
				objects.put("responseAttribute","No cahnge in " + attributeName );
    			break;
    		default:
    			objects.put("responseAttribute", "Action against attribute can only be NONE,ADD,UPDATE,DELETE" );

    		}
    	}
    		

    	}
    	
    	System.out.println(jsonData);
    	FileWriter fileOutput = new FileWriter(uploadingdir+"output.json");
    	fileOutput.write(jsonData.toString());
    	fileOutput.close();
    	
		String[] fileNameSplitsOutput = "output.json".split("\\.");
		int extensionIndexOutput = fileNameSplitsOutput.length - 1;
		code = new StringBuilder(generateTaskCode());
		code.append(Long.valueOf(System.currentTimeMillis()+1).toString());
		String newNameOutput = code.toString() + "." + fileNameSplitsOutput[extensionIndexOutput];
		StringBuilder s3urlOutput = uploadFile(newNameOutput,
				uploadingdir + "output.json", file.getContentType());
		
		metadataHistory.setResponseFileName("output.json");
		metadataHistory.setS3ResponseFileName(newNameOutput);
		metadataHistory.setS3ResponseUrl(s3urlOutput.toString());
		metadataHistory.setHistoryId(metadataHistoryRepo.getNextValHistorySequence());
		metadataHistoryRepo.save(metadataHistory);
		
		
		return metadataHistoryRepo.findAll();		
	}
	
public static String generateTaskCode() {
		
		return ""+randomSeriesForCharacter()+randomSeriesForCharacter()+randomSeriesForCharacter()+randomSeriesForCharacter()+(String.valueOf(getRandomIntegerBetweenRange(10000,99999))).substring(0, 5);
	}

public static char randomSeriesForCharacter() {
    Random r = new Random();
    return (char) (r.nextInt(26) + 'A');
}

public static double getRandomIntegerBetweenRange(double min, double max){
    double x = (int)(Math.random()*((max-min)+1))+min;
    return x;
}

@Override
public StringBuilder uploadFile(String keyName, String uploadFilePath, String contentType) {
	StringBuilder s3Url = new StringBuilder();
	S3Properties s3Properties = s3PropertiesRepo.findByBucketType("UPLOAD_METADATA");
	final ObjectMetadata objectMetadata = new ObjectMetadata();
	try {
		objectMetadata.setContentType(contentType);
        File file = new File(uploadFilePath);
		try {
			InputStream targetStream = new FileInputStream(file);
			s3client(s3Properties).putObject(new PutObjectRequest(s3Properties.getBucketName(),keyName.toString(),targetStream,objectMetadata));
	        s3Url.append(s3client(s3Properties).getUrl(s3Properties.getBucketName(), keyName).toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (AmazonServiceException ase) {
		
		System.out.println(ase.getErrorMessage());

	} catch (AmazonClientException ace) {

	}
	return s3Url;
}


public AmazonS3 s3client(S3Properties s3Properties) {
	BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3Properties.getAccessKeyId(),
			s3Properties.getSecretAccessKey());
	return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(s3Properties.getRegion()))
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
}



@Override
public List<MetadataHistory> metadataHistory() throws Exception 
{
	return metadataHistoryRepo.findAll();	
}

@Override
public HashMap<String,String> metataFileDownload(Map<String, Object> requestBody)
{
	
	String s3FileName = (String) requestBody.get("s3FileName");
	S3Properties s3Properties = s3PropertiesRepo.findByBucketType("UPLOAD_METADATA");

	BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3Properties.getAccessKeyId(),
			s3Properties.getSecretAccessKey());
	AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(s3Properties.getRegion())
			.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

	// Set the signed URL to expire after one hour.
	java.util.Date expiration = new java.util.Date();
	long expTimeMillis = expiration.getTime();
	expTimeMillis += 1000 * 60 * 60;
	expiration.setTime(expTimeMillis);

	// Generate the signed URL.
	GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
			s3Properties.getBucketName(), s3FileName).withMethod(HttpMethod.GET).withExpiration(expiration);
	URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

	HashMap<String, String> responseMap = new HashMap<>();

	responseMap.put("origionalName",(String) requestBody.get("fileName"));
	responseMap.put("s3FileName", (String) requestBody.get("s3FileName"));
	responseMap.put("s3url", url.toString());
return 	responseMap;
}


public HashMap<String,ArrayList <HashMap<String,Object>>> getCurrentMetadata()
{
	
    HashMap<String,ArrayList <HashMap<String,Object>>> responseFormatting = new    HashMap<String,ArrayList <HashMap<String,Object>>> ();

    
    
    List<AttributeMetadata>  listAttributeMetadata = attributeMetadataRepo.findAll();
    
	ArrayList <HashMap<String,Object>> metdata = new ArrayList <HashMap<String,Object>>();

    for (AttributeMetadata attributeMetadata : listAttributeMetadata)
    {  
        List<AttributeFields> listAttributeFields = attributeFieldsRepo.findByAttributeTypeCode(attributeMetadata.getAttributeTypeCode());

        HashMap<String,Object> mappingColumn = new HashMap<String,Object>();
    	String codeAttrMetadata= attributeMetadata.getAttributeTypeCode();
    	String outputColumns="";
    	mappingColumn.put("attributeCode", attributeMetadata.getAttributeTypeCode());
    	mappingColumn.put("attributeName", attributeMetadata.getAttributeType());
    	mappingColumn.put("actionAttribute", "NONE");
    	ArrayList <HashMap<String,String>> attributeColumns  = new ArrayList <HashMap<String,String>>();
    for (AttributeFields attributeFields : listAttributeFields)
    {
    	HashMap<String,String> dataColumn = new HashMap<String,String>();
    	dataColumn.put("columnCode", attributeFields.getAttributeFieldCode());
    	dataColumn.put("columnName", attributeFields.getAttributeFieldName());
    	dataColumn.put("columnType", attributeFields.getAttributeFieldType());
    	dataColumn.put("columnValidation", attributeFields.getAttributeFieldValidation());
    	dataColumn.put("actionColumn", "NONE");

    	attributeColumns.add(dataColumn);
	
    	
    }
	mappingColumn.put("attributeColumns", attributeColumns);
	
	metdata.add(mappingColumn);
	responseFormatting.put("metadata", metdata);
	
    }
	return responseFormatting;
}

}
