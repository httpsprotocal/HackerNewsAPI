package com.pyinsider.newsapi.service;

import com.pyinsider.newsapi.model.NewsItems;
import com.pyinsider.newsapi.model.NewsUsers;

import java.util.List;
import java.util.Map;

public interface HackerNewsService {
    List<Integer> topStory(boolean forcedCache);

    Map<Integer, List<String>> comments(Integer storyId);

    List<Integer> pastStories();

    NewsItems getNewsItem(Integer itemId);

    NewsUsers getUserDetails(String userId);

}
