package com.lite.blackdream.framework.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author LaineyC
 */
public class JsonObjectMapper extends ObjectMapper{

    public JsonObjectMapper(){
        super();
        Jackson2ObjectMapperBuilder.json().configure(this);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
