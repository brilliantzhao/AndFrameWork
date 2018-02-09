package com.brilliantzhao.andframework.tinker.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brilliantzhao.andframework.tinker.BaseBuildInfo;
import com.brilliantzhao.andframework.tinker.BuildInfo;
import com.tencent.tinker.lib.library.TinkerLoadLibrary;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

/**
 * description: tinker使用的工具类
 * Date: 2018/2/9 16:48
 * User: BrilliantZhao
 */
public class TinkerHelper {

    /**
     * 展示tinker展示情况
     *
     * @param context
     * @return
     */
    public static boolean showTinkerLoadInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(context);
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BaseBuildInfo.BASE_TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(context)));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BaseBuildInfo.TEST_MESSAGE));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
        return true;
    }

    /**
     * 加载patch
     *
     * @param context
     */
    public static void loadPatch(Context context) {
        TinkerInstaller.onReceiveUpgradePatch(context,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
    }

    /**
     * @param context
     */
    public static void loadLibrary(Context context) {
        // #method 1, hack classloader library path
        TinkerLoadLibrary.installNavitveLibraryABI(context, "armeabi");
        System.loadLibrary("stlport_shared");

        // #method 2, for lib/armeabi, just use TinkerInstaller.loadLibrary
//                TinkerLoadLibrary.loadArmLibrary(getApplicationContext(), "stlport_shared");

        // #method 3, load tinker patch library directly
//                TinkerInstaller.loadLibraryFromTinker(getApplicationContext(), "assets/x86", "stlport_shared");
    }

    /**
     * @param context
     */
    public static void cleanPatch(Context context) {
        Tinker.with(context).cleanPatch();
    }

    /**
     * @param context
     */
    public static void killSelf(Context context) {
        ShareTinkerInternals.killAllOtherProcess(context);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
