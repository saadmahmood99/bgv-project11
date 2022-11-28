package com.dataflow.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.dataflow.models.AttributeFieldsValue;
import com.dataflow.models.MetadataHistory;
import com.dataflow.services.AttributeService;

@RestController
@RequestMapping("/api/attributemgmt/v1/")
@CrossOrigin(origins="*")
public class AttrManagementController {

	private static final Logger log = LoggerFactory.getLogger(AttrManagementController.class);

	/***
	 * @author Vaibhav Gupta
	 * 
	 *         API for Attribute Management has to be created
	 *         
	 */
	
	
	@Autowired
    AttributeService attributeService ;
	
	
    @RequestMapping(value = "/create/{attributeType}", method= RequestMethod.POST)
	public HashMap<String,Object>  createAttribute(@PathVariable String attributeType,@RequestBody Map<String, Object> requestBody) {
    	List<AttributeFieldsValue> attributeFieldsValue = new ArrayList<AttributeFieldsValue> ();
    	HashMap<String,Object> response = new HashMap<String,Object>();
    	try
    	{
    		attributeFieldsValue = attributeService.createAttributeData(attributeType,requestBody);
    	}
    	catch(Exception e)
    	{
    		response.put("success", false);	
    		response.put("message", "Error in fetching list by Attribute with " + e.getMessage());	
    	}
    	response.put("success", true);	
    	response.put("data", attributeFieldsValue);
		return response;
	}
    
    
    @RequestMapping(value="/search/{attributeType}" , method= RequestMethod.POST)
 	public HashMap<String,Object> listValuesForAttributes(@PathVariable String attributeType) {
    	List<AttributeFieldsValue> attributeFieldsValue = new ArrayList<AttributeFieldsValue> ();
    	HashMap<String,Object> response = new HashMap<String,Object>();
    	try
    	{
    		attributeFieldsValue = attributeService.getAllAttributeFields(attributeType);
    	}
    	catch(Exception e)
    	{
    		response.put("success", false);	
    		response.put("message", "Error in fetching list by Attribute with " + e.getMessage());	
    	}
    	response.put("success", true);	
    	response.put("data", attributeFieldsValue);
    	return response;
 	}
    
    
    @RequestMapping(value="/update/{attributeType}/{entryId}" , method= RequestMethod.PUT)
    public HashMap<String,Object>  updateAttribute(@PathVariable int entryId,@PathVariable String attributeType, @RequestBody Map<String, Object> requestBody){
    	List<AttributeFieldsValue> attributeFieldsValue = new ArrayList<AttributeFieldsValue> ();
    	HashMap<String,Object> response = new HashMap<String,Object>();
    	try
    	{
    		attributeFieldsValue = attributeService.updateAttributeData(entryId,requestBody,attributeType);
    	}
    	catch(Exception e)
    	{
    		response.put("success", false);	
    		response.put("message", "Error in updating list by Attribute with " + e.getMessage());	
    	}
    	response.put("success", true);	
    	response.put("data", attributeFieldsValue);
    	return response;
    }
    
    
    
    @RequestMapping(value="/delete/{attributeType}/{entryId}" , method= RequestMethod.DELETE)
    public HashMap<String,Object> deleteAttribute(@PathVariable int entryId,@PathVariable String attributeType){
    	List<AttributeFieldsValue> attributeFieldsValue = new ArrayList<AttributeFieldsValue> ();
    	HashMap<String,Object> response = new HashMap<String,Object>();
    	try
    	{
    		attributeFieldsValue = attributeService.deleteAttributeById(entryId,attributeType);
    	}
    	catch(Exception e)
    	{
    		response.put("success", false);	
    		response.put("message", "Error in updating list by Attribute with " + e.getMessage());	
    	}
    	response.put("success", true);	
    	response.put("data", attributeFieldsValue);
    	return response;	
    }
    
    @RequestMapping(value="/download/metadeta" , method= RequestMethod.POST)
    public  HashMap<String,Object> downloadMetada(){
    HashMap<String,Object> response = new HashMap<String,Object>();
    ArrayList <HashMap<String,Object>> responseFormatting = new   ArrayList <HashMap<String,Object>> ();
    try
    {
    	responseFormatting = 	attributeService.getDownloadMetadataList();
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in download metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;   
    }
    
    @RequestMapping(value="/search/{attributeType}/{entityId}" , method= RequestMethod.GET)
 	public HashMap<String,Object> listValuesForAttributes(@PathVariable String attributeType, @PathVariable int entityId) {
    	List<AttributeFieldsValue> attributeFieldsValue = new ArrayList<AttributeFieldsValue> ();
    	HashMap<String,Object> response = new HashMap<String,Object>();
    	try
    	{
    		attributeFieldsValue = attributeService.getAllAttributeFieldsByEntityId(attributeType,entityId);
    	}
    	catch(Exception e)
    	{
    		response.put("success", false);	
    		response.put("message", "Error in fetching list by Attribute with " + e.getMessage());	
    	}
    	response.put("success", true);	
    	response.put("data", attributeFieldsValue);
    	return response;
 	}

    @RequestMapping(value="/metadeta" , method= RequestMethod.GET)
    public  HashMap<String,Object> getMetadata(){
    HashMap<String,Object> response = new HashMap<String,Object>();
    ArrayList <HashMap<String,Object>> responseFormatting = new   ArrayList <HashMap<String,Object>> ();
    try
    {
    	responseFormatting = 	attributeService.getMetadataList();
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in download metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;
    }
      
    @RequestMapping(value="/metadataProcessing" , method= RequestMethod.POST)
    public  HashMap<String,Object> metadataProcessing(@RequestParam(value="upload", required=true) MultipartFile file) throws Exception
    {   	
    HashMap<String,Object> response = new HashMap<String,Object>();
    List <MetadataHistory> responseFormatting = new   ArrayList <MetadataHistory> ();
    try
    {
    	responseFormatting = 	attributeService.metadataManagement(file);
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in upload metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;
    } 
    
    @RequestMapping(value="/metadataProcessingHistory" , method= RequestMethod.GET)
    public  HashMap<String,Object> metadataHistory() throws Exception
    {   	
    HashMap<String,Object> response = new HashMap<String,Object>();
    List <MetadataHistory> responseFormatting = new   ArrayList <MetadataHistory> ();
    try
    {
    	responseFormatting = 	attributeService.metadataHistory();
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in upload metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;
    } 
    
    
    @RequestMapping(value="/metadataFileDownload" , method= RequestMethod.POST)
    public  HashMap<String,Object> metadataFileDownload(@RequestBody Map<String, Object> requestBody) throws Exception
    {   	
    HashMap<String,Object> response = new HashMap<String,Object>();
    HashMap<String,String> responseFormatting = new HashMap<String,String>();
    try
    {
    	responseFormatting = 	attributeService.metataFileDownload(requestBody);
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in upload metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;
    } 
    
    
    
    @RequestMapping(value="/currentMetadeta" , method= RequestMethod.GET)
    public  HashMap<String,Object> getCurrentMetada(){
    HashMap<String,Object> response = new HashMap<String,Object>();
    HashMap<String,ArrayList <HashMap<String,Object>>> responseFormatting = new   HashMap<String,ArrayList <HashMap<String,Object>>> ();
    try
    {
    	responseFormatting = 	attributeService.getCurrentMetadata();
    }
    catch(Exception e)
	{
		response.put("success", false);	
		response.put("message", "Error in download metadata " + e.getMessage());	
	}
 		response.put("success", true);	
 		response.put("data", responseFormatting);
        return response;   
    }
    @GetMapping("/metadata")
    public String sayHello()
    {
    	return "Say Hello";
    }
    
    @Autowired
    RestTemplate restTemplate;
    
    @GetMapping("/test")
    public String testResponse()
    {
        return restTemplate.getForObject("http://RULE-ENGINE-MANAGE-2/api/attributemgmt/v1/currentMetadeta", String.class);
       // return restTemplate.getForObject("http://host.docker.internal:8021/RULEENGINEATTRMANAGEMENTS/api/attributemgmt/v1/metadata", String.class);
    }
	}



