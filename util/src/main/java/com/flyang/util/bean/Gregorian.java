package com.flyang.util.bean;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/20
 * ------------- Description -------------
 * 公历
 */
public class Gregorian {
    public int gregorYear;
    public int gregorMonth;
    public int gregorDay;

    public Gregorian() {
    }

    public Gregorian(int gregorYear, int gregorMonth, int gregorDay) {
        this.gregorYear = gregorYear;
        this.gregorMonth = gregorMonth;
        this.gregorDay = gregorDay;
    }

    @Override
    public String toString() {
        return "" + gregorYear + ", " + gregorMonth + ", " + gregorDay;
    }

}
