package com.flyang.base;

import android.app.Application;

import com.flyang.base.view.SmartRefreshLayout;
import com.flyang.base.view.refresh.around.ClassicsFooter;
import com.flyang.base.view.refresh.around.ClassicsHeader;
import com.flyang.basic.R;
import com.flyang.util.app.ApplicationUtils;
import com.flyang.util.data.TimeFormatUtils;


public class App extends Application {

    public void onCreate() {
        super.onCreate();
        //初始化帮助类库
        ApplicationUtils.init(this);

        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white));
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) ->
                new ClassicsHeader(context).setTimeFormat(new TimeFormatUtils("更新于 %s")));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) ->
                new ClassicsFooter(context));
    }
}

