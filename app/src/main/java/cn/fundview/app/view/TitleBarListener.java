package cn.fundview.app.view;

import android.view.View;

/**
 * 项目名称：Agr-join-v1
 * 类名称 TitleBarListener
 * 类描述：标题栏 监听器
 * 创建人：lict
 * 修改人：lict
 * 修改备注：通用标题栏监听器 监听标题栏的左,中,右三个区域
 */
public interface TitleBarListener {

    /**
     * 点击左边区域 主要是返回和首页的搜索条件选择
     */
    void onClickLeft();

    /**
     * 点击标题栏中间区域 触发,主要是搜索框
     */
    void onClickMiddle();

    /**
     * 点击右边的功能按钮 主要有扫描,搜索和保存,分享等
     * void
     */
    void onClickRight();
}