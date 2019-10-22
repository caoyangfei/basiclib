package com.flyang.demo.presenter;

import com.flyang.demo.model.bean.WeatherEntity;
import com.flyang.demo.model.bean.WeatherResult;
import com.flyang.demo.model.contract.CacheAPIContract;
import com.flyang.network.FlyangHttp;
import com.flyang.network.cache.converter.CacheType;
import com.flyang.network.cache.model.CacheMode;
import com.flyang.network.callback.CallClazzProxy;
import com.flyang.network.callback.SimpleCallBack;
import com.flyang.network.exception.ApiException;
import com.flyang.network.subsciber.CallBackSubsciber;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
public class CachePresenter extends CacheAPIContract.Presenter {
    @Override
    public void getWeather(String city) {
        Observable<List<WeatherEntity>> observable = FlyangHttp.post("/environment/air/cityair")
                .params("city", city)
                .params("key", "7e278289eacfcf0faaf235ca89be0a34")
                .cacheCacheType(CacheType.Serializable)
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .cacheKey(CachePresenter.class.getSimpleName())
                .execute(new CallClazzProxy<WeatherResult<List<WeatherEntity>>, List<WeatherEntity>>(new TypeToken<List<WeatherEntity>>() {
                }.getType()) {
                });
        observable.subscribe(new CallBackSubsciber<>(mContext, new SimpleCallBack<List<WeatherEntity>>() {
            @Override
            public void onError(ApiException e) {
                getView().getWeatherFailed(e.getMessage());
            }

            @Override
            public void onSuccess(List<WeatherEntity> weatherEntities) {
                Objects.requireNonNull(getView()).getWeatherSuccess(weatherEntities);
            }
        }));

//        FlyangHttp.put("/environment/air/cityair")
//                .params("city", city)
//                .params("key", "7e278289eacfcf0faaf235ca89be0a34")
//                .execute(new CallBackProxy<WeatherResult<List<WeatherEntity>>, List<WeatherEntity>>(new SimpleCallBack<List<WeatherEntity>>() {
//                    @Override
//                    public void onError(ApiException e) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<WeatherEntity> weatherEntities) {
//
//                    }
//                }) {
//                });
    }
}
