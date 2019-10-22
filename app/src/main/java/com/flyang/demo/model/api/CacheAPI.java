package com.flyang.demo.model.api;

import com.flyang.annotation.apt.ContractFactory;

import java.util.List;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
@ContractFactory(entites = {List.class})
public interface CacheAPI {
    void getWeather(String city);
}
