package com.flyang.demo.ui.view.net;

import android.view.View;
import android.widget.ImageView;

import com.flyang.annotation.apt.BindView;
import com.flyang.annotation.apt.OnClick;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.demo.R;
import com.flyang.imageloader.ImageLoader;
import com.flyang.progress.OnProgressListener;
import com.flyang.progress.model.ProgressInfo;
import com.flyang.util.log.LogUtils;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
public class ImageActivity extends BasePresenterActivity {

    @BindView("img")
    ImageView img;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_img;
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick(value = {"btn1", "btn2"})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                ImageLoader.with(this).url("https://sina.5ime.cn/large/0072Vf1pgy1foxlo645aaj31hc0u07h7").into(img);
                break;
            case R.id.btn2:
                ImageLoader.with(this).url("https://tenapi.cn/bing/").onProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(ProgressInfo progressInfo) {
                        LogUtils.i(progressInfo.toString());
                    }

                    @Override
                    public void onError(long id, Exception e) {

                    }
                }).into(img);
                break;
        }
    }


}
