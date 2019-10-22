package com.flyang.demo;

import com.flyang.imageloader.ImageLoader;
import com.flyang.imageloader.loader.GlideImageLoaderStrategy;
import com.flyang.network.FlyangHttp;
import com.flyang.network.cache.converter.CacheType;
import com.flyang.progress.ProgressManager;
import com.flyang.util.log.LogUtils;
import com.flyang.util.log.config.LogLevel;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class App extends com.flyang.base.App {
    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.getLogConfig()
                .configAllowLog(true)  // 是否在Logcat显示日志
                .configTagPrefix("LogUtilsDemo") // 配置统一的TAG 前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}") // 首行显示信息(可配置日期，线程等等)
                .configShowBorders(true) // 是否显示边框
                .configLevel(LogLevel.TYPE_VERBOSE); // 配置可展示日志等级

//        // 支持输入日志到文件
//        String filePath = Environment.getExternalStorageDirectory() + "/LogUtils/logs/";
//        LogUtils.getLog2FileConfig()
//                .configLog2FileEnable(true)  // 是否输出日志到文件
//                .configLogFileEngine(new LogFileEngineFactory(this)) // 日志文件引擎实现
//                .configLog2FilePath(filePath)  // 日志路径
//                .configLog2FileNameFormat("App-%d{yyyy-MM-dd-HH}.txt") // 日志文件名称
//                .configLog2FileLevel(LogLevel.TYPE_VERBOSE) // 文件日志等级
//                .configLogFileFilter(new LogFileFilter() {  // 文件日志过滤
//                    @Override
//                    public boolean accept(int level, String tag, String logContent) {
//                        return true;
//                    }
//                }).flushAsync();
        String url = "https://www.apiopen.top";
        FlyangHttp.getInstance()
                .debug(BuildConfig.DEBUG)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setCacheCacheType(CacheType.Serializable)//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setBaseUrl(url)
                .setHostnameVerifier(new UnSafeHostnameVerifier(url))//全局访问规则
                .setCertificates();//信任所有证书

        ProgressManager.getInstance().with(FlyangHttp.getOkHttpClientBuilder());
        ImageLoader.init(new GlideImageLoaderStrategy());
    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            LogUtils.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            LogUtils.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
                return false;
            return true;
        }
    }

}
