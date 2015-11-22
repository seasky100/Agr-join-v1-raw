package cn.fundview.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fundview.R;

/**
 * 首页标题栏
 */
public class HomeTitleBar extends LinearLayout {

    private TextView searchType;        //左侧
    private EditText searchEditText;    //中间区域
    private ImageView scanBtn;          //右侧区域
    private TitleBarListener listener;

    private Context context;

    public HomeTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.home_title_bar, this, true);
        scanBtn = (ImageView) this.findViewById(R.id.scanBtn);
        searchType = (TextView) this.findViewById(R.id.searchType);
        searchEditText = (EditText) this.findViewById(R.id.editText);
        attachEvents();
    }

    private void attachEvents() {

        // 搜索条件
        searchType.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                listener.onClickLeft();
            }
        });

        // 搜索框
        searchEditText.setKeyListener(null);
        searchEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                listener.onClickMiddle();
            }
        });
        scanBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickRight();
            }
        });
    }

    /**
     * 注册监听
     **/
    public void registerListener(TitleBarListener listener) {

        if(this.listener != null) {

            this.listener = null;
        }

        this.listener = listener;
    }

    public TextView getSearchType() {
        return searchType;
    }

    public EditText getSearchEditText() {
        return searchEditText;
    }
}
