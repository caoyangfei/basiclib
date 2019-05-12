package com.flyang.util.interf;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/20
 * ------------- Description -------------
 */
public interface OnLocationChangeListener extends LocationListener {
    /**
     * 获取最后一次保留的坐标
     *
     * @param location 坐标
     */
    void getLastKnownLocation(Location location);

    /**
     * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
     *
     * @param location 坐标
     */
    void onLocationChanged(Location location);

    /**
     * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
     *
     * @param provider 提供者
     * @param status   状态
     *                 <li>{@link LocationProvider#AVAILABLE  当前GPS状态为可见状态} </li>
     *                 <li>{@link LocationProvider#OUT_OF_SERVICE  当前GPS状态为服务区外状态} </li>
     *                 <li>{@link LocationProvider#OUT_OF_SERVICE  当前GPS状态为暂停服务状态} </li>
     * @param extras   provider可选包
     */
    void onStatusChanged(String provider, int status, Bundle extras);//位置状态发生改变
}
