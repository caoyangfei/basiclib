package com.flyang.util.system;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.SimpleArrayMap;

import com.flyang.util.app.ApplicationUtils;
import com.flyang.util.bean.support.Default;
import com.flyang.util.bean.support.OPPO;
import com.flyang.util.bean.support.ViVo;
import com.flyang.util.interf.ISetting;

/**
 * @author yangfei.cao
 * @ClassName aptlib
 * @date 2019/3/28
 * ------------- Description -------------
 * 权限工具类
 */

public class PermissionUtils {

    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_VIVO = "vivo";

    /**
     * 跳设置界面
     */
    public static void go2Setting() {
        ISetting iSetting;

        switch (Build.MANUFACTURER) {
            case MANUFACTURER_VIVO:
                iSetting = new ViVo(ApplicationUtils.getApp());
                break;
            case MANUFACTURER_OPPO:
                iSetting = new OPPO(ApplicationUtils.getApp());
                break;
            default:
                iSetting = new Default(ApplicationUtils.getApp());
                break;
        }
        if (iSetting.getSetting() == null) return;
        ApplicationUtils.getApp().startActivity(iSetting.getSetting());
    }

    /**
     * 判断是否所有权限都同意了，都同意返回true 否则返回false
     *
     * @param permissions permission list
     * @return return true if all permissions granted else false
     */
    public static boolean hasSelfPermissions(String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断单个权限是否同意
     *
     * @param permission permission
     * @return return true if permission granted
     */
    private static boolean hasSelfPermission(String permission) {
        return ActivityCompat.checkSelfPermission(ApplicationUtils.getApp(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断权限是否存在
     *
     * @param permission permission
     * @return return true if permission exists in SDK version
     */
    private static boolean permissionExists(String permission) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    /**
     * 检查是否都赋予权限
     *
     * @param grantResults grantResults
     * @return 所有都同意返回true 否则返回false
     */
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) return false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查所给权限List是否需要给提示
     *
     * @param activity    Activity
     * @param permissions 权限list
     * @return 如果某个权限需要提示则返回true
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }
}