package cn.fundview.app.service;

import java.io.File;

import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.DeviceConfig;
import cn.fundview.app.tool.PreferencesUtils;
import cn.fundview.app.tool.ToastUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PackageAddEndReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub


        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "安装成功", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "卸载成功", Toast.LENGTH_LONG).show();
            //删除第一次使用的引导标识
            PreferencesUtils.putInt(context, Constants.FIRST_OPEN_TAG, 0);
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "替换成功", Toast.LENGTH_LONG).show();
        }

        // 删除apk文件
        File file = new File(DeviceConfig.getSysPath(context) + Constants.VERSION_PATH);
        if (file.exists()) {

            file.delete();
        }

        File file1 = new File(DeviceConfig.getSysPath(context) + Constants.APK_PATH);
        if (file1.exists()) {

            file1.delete();
        }
    }

}
