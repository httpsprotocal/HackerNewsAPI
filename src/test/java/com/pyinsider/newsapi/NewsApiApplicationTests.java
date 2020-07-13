package com.pyinsider.newsapi;

import com.pyinsider.newsapi.controller.NewsApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest
class NewsApiApplicationTests {
    @Autowired
    private NewsApi controller;

    @Test
    void contextLoads() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        System.out.println(controller.topStores(true));
        ;
        System.out.println(controller.pastStories());
        ;
        System.out.println(controller.getItemsDetails(23811604));
        ;

    }


}
