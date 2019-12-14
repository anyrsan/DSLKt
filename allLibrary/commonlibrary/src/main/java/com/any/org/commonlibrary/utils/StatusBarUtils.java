package com.any.org.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by
 * 项目中，建议采用这个类进行透明状态栏
 */
public class StatusBarUtils {


    public static int getStatusBarHeight(Resources resources){
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
//        Log.e("msg", "getStatusBarHeight...." + result);
        return result;
    }

    // 获取状态栏高度，一般为 25dp
    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context.getResources());
    }


    public static void setStatusBar(Activity activity, boolean isDark, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0已下的版本不支持更改
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);

//            if (isMIUI()) {
//                Class clazz = window.getClass();
//                try {
//                    int darkModeFlag = 0;
//                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");  //兼容小米
//                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
//                    darkModeFlag = field.getInt(layoutParams);
//                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//                    if (isDark) {
//                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
//                    } else {
//                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }


            if (isDark) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                mChildView.setFitsSystemWindows(false);
                mChildView.requestLayout();
            }
        }
    }


    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    private static final class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            // 读取系统配置信息build.prop类
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public int size() {
            return properties.size();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }


    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

}
