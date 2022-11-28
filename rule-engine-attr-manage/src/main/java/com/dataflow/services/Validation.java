package com.dataflow.services;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import com.dataflow.constants.Constants;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.spring.web.plugins.Docket;

@Service
public class Validation 
{

public boolean validation(String fileType) 
{
	
if(fileType.equalsIgnoreCase(Constants.FILE_TYPE))
{
	return true;
}
else
{
   return false;
}
}	

public boolean mandatoryAttribute(JSONObject jsonObject,String key) 
{
	
if(jsonObject.has(key))
{
	return true;
}
else
{
   return false;
}
}	



}
