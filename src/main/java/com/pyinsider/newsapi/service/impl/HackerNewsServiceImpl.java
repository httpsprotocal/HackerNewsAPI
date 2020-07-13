package com.pyinsider.newsapi.service.impl;

import com.pyinsider.newsapi.adaptor.ApiBridge;
import com.pyinsider.newsapi.model.NewsItems;
import com.pyinsider.newsapi.model.NewsUsers;
import com.pyinsider.newsapi.service.HackerNewsService;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HackerNewsServiceImpl implements HackerNewsService {


    private final ApiBridge apiBridge;


    private final MemcachedClient memcachedClient;

    @Autowired
    public HackerNewsServiceImpl(ApiBridge apiBridge, MemcachedClient memcachedClient) {
        this.apiBridge = apiBridge;
        this.memcachedClient = memcachedClient;
    }

    @Override
    public List<Integer> topStory(boolean forcedCache) {
        if (memcachedClient.get("topStory") != null && !forcedCache) {
            return (List<Integer>) memcachedClient.get("topStory");
        } else {
            List<Integer> topStory = (List<Integer>) apiBridge.callExternalApi("topstories", List.class);
            memcachedClient.set("topStory", 600, topStory);
            return topStory;
        }
    }

    @Override
    public Map<Integer, List<String>> comments(Integer storyId) {
        List<String> comment = new ArrayList<>();
        Map<Integer, List<String>> integerListMap = new HashMap<>();
        NewsItems newsItems = (NewsItems) apiBridge.callExternalApi("item/" + storyId, NewsItems.class);
        totalComment(newsItems, comment);
        comment = comment.stream().limit(10).collect(Collectors.toList());
        integerListMap.put(storyId, comment);
        return integerListMap;
    }

    void totalComment(NewsItems newsItems, List<String> comment) {
        if (newsItems.getType().equalsIgnoreCase("comment")) {
            comment.add(newsItems.getText());
        }
        List<Integer> kids = newsItems.getKids();
        if (kids != null && !kids.isEmpty()) {
            kids.forEach(eachKidsType -> {
                if (comment.size() < 10) {
                    totalComment((NewsItems) apiBridge.callExternalApi("item/" + eachKidsType, NewsItems.class), comment);
                }
                return;
            });
        }
        return;
    }

    @Override
    public List<Integer> pastStories() {
        if (memcachedClient.get("topStory") != null) {
            return (List<Integer>) memcachedClient.get("topStory");
        }
        return topStory(true);
    }

    @Override
    public NewsItems getNewsItem(Integer itemId) {
        return (NewsItems) apiBridge.callExternalApi("item/" + itemId, NewsItems.class);
    }

    @Override
    public NewsUsers getUserDetails(String userId) {
        return (NewsUsers) apiBridge.callExternalApi("user/jl", NewsItems.class);
    }
}
