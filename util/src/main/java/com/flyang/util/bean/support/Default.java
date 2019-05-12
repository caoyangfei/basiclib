package com.flyang.util.bean.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.flyang.util.interf.ISetting;

/**
 * @author yangfei.cao
 * @ClassName aptlib
 * @date 2019/3/28
 * ------------- Description -------------
 * 默认
 */

public class Default implements ISetting {

    private Context context;

    public Default(Context context) {
        this.context = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }
}
