package com.baizhi.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private String id;

    private String title;

    private String coverImg;

    private Double score;

    private String author;

    private String broadcast;

    private Integer count;

    private String content;

    private String pudDate;
}