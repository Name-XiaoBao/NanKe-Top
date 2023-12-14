package com.nanke.nanketop.mapper;

import com.nanke.nanketop.model.Carousel;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselMapper {
    /**
     * 获取轮播图列表
     * @return
     */
    @Select("SELECT * FROM carousel")
    List <Carousel> getCarouselList();
}
