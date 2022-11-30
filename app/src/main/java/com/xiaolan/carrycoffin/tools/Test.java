package com.xiaolan.carrycoffin.tools;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * _      _   _  _  __   __   __
 * | |    /_\ | \| |/ /  / /  / /
 * | |__ / _ \| .` / _ \/ _ \/ _ \
 * |____/_/ \_\_|\_\___/\___/\___/
 *
 * @author By: lan666
 * @version 1.0.0
 * @ClassName Test.java
 * @project carryCoffin
 * @Description TODO
 * @createTime 2022年11月30日 21:51:00
 */
public class Test implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                R$1.getInstance().setLayerGrayType(((Activity) param.thisObject).getWindow().getDecorView());
            }
        });
    }
}

class R$1 {
    private static R$1 mInstance;
    private Paint mGrayPaint;
    private ColorMatrix mGrayMatrix;

    public static R$1 getInstance() {
        if (mInstance == null) {
            synchronized (R$1.class) {
                if (mInstance == null) {
                    mInstance = new R$1();
                }
            }
        }
        return mInstance;
    }

    //初始化
    public void init() {
        mGrayMatrix = new ColorMatrix();
        mGrayPaint = new Paint();
        mGrayMatrix.setSaturation(0);
        mGrayPaint.setColorFilter(new ColorMatrixColorFilter(mGrayMatrix));
    }


    //硬件加速置灰方法
    public void setLayerGrayType(View view) {
        if (mGrayMatrix == null || mGrayPaint == null) {
            init();
        }

        view.setLayerType(View.LAYER_TYPE_HARDWARE, mGrayPaint);
    }
}
