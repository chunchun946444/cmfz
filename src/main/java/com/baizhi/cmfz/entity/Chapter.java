package com.baizhi.cmfz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private String id;

    private String title;

    private String src;

    private String duration;

    private String size;

    private String uploadTime;

    private String albumId;
}