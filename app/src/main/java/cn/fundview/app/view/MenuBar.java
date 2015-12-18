package cn.fundview.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import cn.fundview.R;

/**
 * 选项栏
 * <p/>
 * 观察 丰景资讯推送(数量大于1 显示红点)
 */
public class MenuBar extends LinearLayout {

    private MenuBarListener listener;

    private Context context;

    private int selectIndex;
    private MenuItem[] menuItems = new MenuItem[3];

    public MenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.menu_bar, this, true);

        menuItems[0] = (MenuItem) this.findViewById(R.id.home_item);
        menuItems[1] = (MenuItem) this.findViewById(R.id.msg_item);
        menuItems[2] = (MenuItem) this.findViewById(R.id.my_item);

        for(MenuItem item : menuItems) {

            item.setClickable(true);
            item.setOnClickListener(itemClicklistener);
        }

//        NewFundviewInforObserverMrg.getInstance().addObserver(this);//添加消息数量观察者
    }

    public void setListener(MenuBarListener listener) {

        this.listener = listener;
    }

    private OnClickListener itemClicklistener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            if(view instanceof MenuItem) {

                MenuItem item =  (MenuItem)view;
                int tag = Integer.parseInt(item.getTag().toString());

                if(tag != selectIndex) {

                    //跳转到指定的activity

                }
            }

//            listener.onMenuItemClick(flag);
        }
    };

    /**
     * 设置是否用未读消息提醒
     *
     * @param count
     */
    public void msgNotice(int count) {

        if (count > 0) {

            menuItems[1].showMsgNotice();
        } else {

            menuItems[1].hideMsgNotice();
        }
    }

    public static interface MenuBarListener {

        void onMenuItemClick(int flag);
    }

}
