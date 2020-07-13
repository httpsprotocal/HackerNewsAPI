package com.pyinsider.newsapi.controller;

import com.pyinsider.newsapi.model.NewsItems;
import com.pyinsider.newsapi.service.HackerNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/news/v1")
public class NewsApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private final HackerNewsService hackerNewsService;

    @Autowired
    public NewsApi(HackerNewsService hackerNewsService) {
        this.hackerNewsService = hackerNewsService;
    }


    @RequestMapping(value = "topstories", method = RequestMethod.GET, produces = "application/json")
    public List<Integer> topStores(@RequestParam(value = "forcedCache", defaultValue = "true") boolean forcedCache) {
        List<Integer> topStory = hackerNewsService.topStory(forcedCache);
        if (topStory.isEmpty()) {
            logger.debug("System is going to shut down please check url Top Story Unable to load for the url {}", "topstories");
            System.exit(1);
        }
        return topStory;
    }


    @RequestMapping(method = RequestMethod.GET, value = "allComment/{parentItemId}")
    public Map<Integer, List<String>> comments(@PathVariable(value = "parentItemId") Integer parentItemId) {
        Map<Integer, List<String>> comments = hackerNewsService.comments(parentItemId);
        if (null == comments || comments.isEmpty()) {
            logger.debug("System is going to shout down please check url Top Story Unable to load for the url {}", parentItemId);
        }
        return comments;
    }

    @RequestMapping(method = RequestMethod.GET, value = "pastStories")
    public List<Integer> pastStories() {
        List<Integer> pastStories = hackerNewsService.pastStories();
        if (null == pastStories || pastStories.isEmpty()) {
            logger.debug("Unable to load pastStories from cache");
        }
        return pastStories;
    }

    @RequestMapping(method = RequestMethod.GET, value = "items/{itemId}")
    public NewsItems getItemsDetails(@PathVariable(value = "itemId") Integer itemId) {
        NewsItems newsItems = hackerNewsService.getNewsItem(itemId);
        if (null == newsItems) {
            logger.debug("Unable to call getItemsDetails services");
            return new NewsItems();
        }
        return newsItems;
    }
}
