package cn.fundview.app.service;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.fundview.app.tool.NetWorkConfig;
import cn.fundview.app.view.NetWorkChangeListener;

public class NetWorkBroadCastReceiver extends BroadcastReceiver {

    private static List<NetWorkChangeListener> observers;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        System.out.println("NetWorkBroadCastReceiver");
        if (NetWorkConfig.checkNetwork(context)) {

            // 网络可用
            if (observers != null && observers.size() > 0) {

                for (NetWorkChangeListener observer : observers) {
                    System.out.println(observer.getClass());
                    observer.change(true);
                }
            }
        }
    }

    public static void addObserver(NetWorkChangeListener observer) {

        if (observers == null) {

            observers = new ArrayList<NetWorkChangeListener>();
        }
        if (!observers.contains(observer)) {

            observers.add(observer);
        }
    }

    public static void removeAllObserver() {

        if (observers != null) {

            observers.clear();
        }
    }

    public static void removeObserver(NetWorkChangeListener observer) {

        if (observers != null && observers.size() > 0) {

            if (observers.contains(observer)) {

                observers.remove(observer);
            }
        }
    }

}
