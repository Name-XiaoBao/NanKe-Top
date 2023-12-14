package com.nanke.nanketop.model;

import lombok.Data;

@Data
public class Carousel {
    // 轮播图ID
    private Integer id;
    // 图片链接
    private String img;
    // 跳转链接
    private String url;
}
