package cn.fundview.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import cn.fundview.R;
import cn.fundview.app.service.UpdateService;
import cn.fundview.app.service.UpdateService.UpdataAppBinder;
import cn.fundview.app.tool.AppUtils;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.PreferencesUtils;
import cn.fundview.app.view.HomeTitleBar;
import cn.jpush.android.api.JPushInterface;

public class MainActivity  extends Activity {

    public static final int REQUEST_CODE_MY = 1212;// 从我的页面进行登录的请求码
    public static final int REQUEST_CODE_EXPERT = 1213;// 从关注的专家进行登录的请求码
    public static final int REQUEST_CODE_COMP = 1214;// 从专注的企业进行登录的请求码
    public static final int REQUEST_CODE_AUTO = 12312;//打开应用的时候 自动登录

    private int currIndex = 1; // 标识当前在那个页面 1 项目 2 消息 3 我

    private UpdateService updateService;

    private AppUpdateConn conn = new AppUpdateConn();

    private View activeView;    //当前view

    private HomeTitleBar homeTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //标题栏
        homeTitleBar = (HomeTitleBar) this.findViewById(R.id.titleBar);

        //设置访问信息
        PreferencesUtils.putInt(this, Constants.FIRST_OPEN_TAG, 1);
        // 开启app的更新服务
        Intent updateService = new Intent(this, UpdateService.class);
        this.bindService(updateService, conn, BIND_AUTO_CREATE);
        currIndex = 1;
        showHomeView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 显示首页view
     */
    private void showHomeView() {

        if(activeView != null) {

            activeView.setVisibility(View.GONE);
        }

        activeView = this.findViewById(R.id.homeview);

        activeView.setVisibility(View.VISIBLE);




    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if (currIndex == 1) {
//
//                exitSys();
//            } else {
//
//                showHomePage();
//            }
//            return true;
//        } else {
//
//
//            return super.onKeyDown(keyCode, event);
//        }
//    }



    private void exitSys() {
        new AlertDialog.Builder(MainActivity.this).setTitle("退出")
                .setMessage("你确定要退出吗?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                       // NewFundviewInforObserverMrg.getInstance().clearObserver();

                        // // 停止服务\
                        //清空登录状态
//						PreferencesUtils.putInt(MainActivity.this, Constants.LOGIN_STATUS_KEY, Constants.LOGIN_OUT_STATUS);

                        JPushInterface.stopPush(MainActivity.this);
                        MainActivity.this.finish();

                        // 在进程中移除自己（用于程序自己重新启动）
                        AppUtils.killProcess();

                    }
                }).setNegativeButton("否", null).show();
    }



    private class AppUpdateConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            updateService = ((UpdataAppBinder) service).getService();
            updateService.down(MainActivity.this);
            System.out.println("后台检测app 更新....");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            // 关闭服务

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateService = null;
        unbindService(conn);
    }
}
