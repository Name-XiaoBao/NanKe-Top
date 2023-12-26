package com.nanke.nanketop.contoller;

import cn.xbjstd.xiaobao.util.Json;
import com.nanke.nanketop.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarouselContoller {
    @Autowired
    private CarouselService carouselService;

    /**
     * 获取轮播图列表
     * @return
     */
    @PostMapping("/CarouselList")
    public Json getCarouselList(){
        return Json.json(200, "获取成功", carouselService.getCarouselList());
    }
}
