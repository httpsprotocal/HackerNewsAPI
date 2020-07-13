package com.pyinsider.newsapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class NewsUsers {
    private String id;
    private Integer delay;
    private Long created;
    private Integer karma;
    private String about;
    private List<Integer> submitted;

}
