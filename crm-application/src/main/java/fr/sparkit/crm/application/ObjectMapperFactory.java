package fr.sparkit.crm.application;

import java.io.Serializable;

import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectMapperFactory extends Jackson2JsonObjectMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public ObjectMapperFactory() {
        super();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }
}
