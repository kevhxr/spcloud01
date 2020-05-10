package com.xr.xrservice1.controller;

import com.xr.xrservice1.entity.StockInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping(value = "/basic",method = RequestMethod.GET)
    public String getStockBasicInfo(){
        StockInfo stockInfo = StockInfo.builder().stockId(60031).companyName("Intel").build();
        System.out.println(stockInfo);
        return stockInfo.toString();
    }
}
