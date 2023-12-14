package com.nanke.nanketop.service;

import com.nanke.nanketop.mapper.CarouselMapper;
import com.nanke.nanketop.model.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 获取轮播图列表
     * @return
     */
    public List<Carousel> getCarouselList(){
        return carouselMapper.getCarouselList();
    }
}
