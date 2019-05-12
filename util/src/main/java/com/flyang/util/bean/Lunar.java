package com.flyang.util.bean;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/20
 * ------------- Description -------------
 * 阴历
 */
public class Lunar {
    public int lunarYear;
    public int lunarMonth;
    public int lunarDay;
    public boolean isLeap;

    public Lunar() {
    }

    public Lunar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeap) {
        this.lunarYear = lunarYear;
        this.lunarMonth = lunarMonth;
        this.lunarDay = lunarDay;
        this.isLeap = isLeap;
    }

    @Override
    public String toString() {
        return "" + lunarYear + ", " + lunarMonth + ", " + lunarDay + ", " + isLeap;
    }
}
