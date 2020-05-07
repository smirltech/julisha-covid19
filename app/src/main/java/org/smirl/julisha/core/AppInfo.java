package org.smirl.julisha.core;

import android.content.Context;
import android.content.pm.PackageInfo;

public final class AppInfo {
    private PackageInfo pInfo;
    private static AppInfo app;

    private AppInfo(Context var1) {
        try {
            this.pInfo = var1.getPackageManager().getPackageInfo(var1.getPackageName(), 0);
        } catch (Exception var3) {
        }

    }

    public static AppInfo from(Context var0) {
        if (app == null) {
            app = new AppInfo(var0);
        }

        return app;
    }

    public  String getAppVersionName() {
        return app.pInfo.versionName;
    }

    public  int getAppVersionCode() {
        return app.pInfo.versionCode;
    }

    public String toString() {
        StringBuilder var10000 = new StringBuilder();
        AppInfo var10001 = app;
        var10000 = var10000.append(getAppVersionName());
        var10001 = app;
        return var10000.append(getAppVersionCode()).toString();
    }
}
