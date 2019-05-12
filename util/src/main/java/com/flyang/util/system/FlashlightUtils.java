package com.flyang.util.system;

import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import com.flyang.util.app.ApplicationUtils;

import java.io.IOException;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * @author caoyangfei
 * @ClassName FlashlightUtils
 * @date 2019/4/19
 * ------------- Description -------------
 * 闪光灯相关
 */
public final class FlashlightUtils {

    private static Camera mCamera;
    private static SurfaceTexture mSurfaceTexture;

    private FlashlightUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否支持闪光灯
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightEnable() {
        return ApplicationUtils.getApp()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 判断闪光灯是否打开
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightOn() {
        if (!init()) return false;
        Camera.Parameters parameters = mCamera.getParameters();
        return FLASH_MODE_TORCH.equals(parameters.getFlashMode());
    }

    /**
     * 设置闪光灯状态
     *
     * @param isOn True to turn on the flashlight, false otherwise.
     */
    public static void setFlashlightStatus(final boolean isOn) {
        if (!init()) return;
        final Camera.Parameters parameters = mCamera.getParameters();
        if (isOn) {
            if (!FLASH_MODE_TORCH.equals(parameters.getFlashMode())) {
                try {
                    mCamera.setPreviewTexture(mSurfaceTexture);
                    mCamera.startPreview();
                    parameters.setFlashMode(FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                } catch (IOException e) {
                    Log.e("FlashlightUtils", "setFlashlightStatusOn: ", e);
                }
            }
        } else {
            if (!FLASH_MODE_OFF.equals(parameters.getFlashMode())) {
                parameters.setFlashMode(FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (mCamera == null) return;
        mCamera.release();
        mSurfaceTexture = null;
        mCamera = null;
    }

    private static boolean init() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open(0);
                mSurfaceTexture = new SurfaceTexture(0);
            } catch (Throwable t) {
                Log.e("FlashlightUtils", "init failed: ", t);
                return false;
            }
        }
        if (mCamera == null) {
            Log.e("FlashlightUtils", "init failed.");
            return false;
        }
        return true;
    }
}
