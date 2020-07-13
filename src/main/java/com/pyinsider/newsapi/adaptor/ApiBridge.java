package com.pyinsider.newsapi.adaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiBridge {
    String BASE_URL = "https://hacker-news.firebaseio.com";
    String API_VERSION = "v0";
    Character character = '/';
    String suffix = ".json?print=pretty";
    String URL = BASE_URL + character + API_VERSION;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    @Autowired
    private RestTemplate restTemplate;

    public Object callExternalApi(String url, Class aClass) {
        ResponseEntity<Object> listResponseEntity = restTemplate.getForEntity(URL + character + url + suffix, aClass);
        if (listResponseEntity != null && listResponseEntity.getBody() != null) {
            return aClass.cast(listResponseEntity.getBody());
        } else {
            logger.debug("Url is no valid {}", URL + character + url);
            return null;
        }
    }

}
