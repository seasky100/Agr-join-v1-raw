package cn.fundview.app.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.R;
import cn.fundview.app.tool.PopUpWindow;
import cn.fundview.app.tool.adapter.ListViewAdapter;

/**
 * 首页标题栏
 */
public class HomeTitleBar extends LinearLayout {

    private PopUpWindow popupWindow;
    private int selectedIndex = 0;//当前选中的下标索引
    private ListViewAdapter adapter;

    private TextView searchType;        //左侧
    private EditText searchEditText;    //中间区域
    private ImageView scanBtn;          //右侧区域
    private TitleBarListener listener;
    final List<Map<String, String>> dataSource = new ArrayList<>();  //搜索条件 数据源

    private int type;
    private Context context;

    public HomeTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        Map<String, String> expertMap = new HashMap<>();
        expertMap.put("name", "专 家");
        expertMap.put("key", "0");

        Map<String, String> achvMap = new HashMap<>();
        achvMap.put("name", "成 果");
        achvMap.put("key", "1");

        Map<String, String> compMap = new HashMap<>();
        compMap.put("name", "企 业");
        compMap.put("key", "2");

        Map<String, String> requMap = new HashMap<>();
        requMap.put("name", "需 求");
        requMap.put("key", "3");

        Map<String, String> orgMap = new HashMap<>();
        orgMap.put("name", "院 所");
        orgMap.put("key", "4");

        Map<String, String> productMap = new HashMap<>();
        productMap.put("name", "产 品");
        productMap.put("key", "5");

        dataSource.add(compMap);
        dataSource.add(expertMap);
        dataSource.add(requMap);
        dataSource.add(achvMap);
        dataSource.add(productMap);


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
//                listener.onClickLeft();

                if (popupWindow != null) {

                    if (adapter != null) {

                        adapter.setSelectedIndex(selectedIndex);
                    }
                    popupWindow.toggle();

                    return;
                } else {

                    View popupView = ((Activity) context).getLayoutInflater().inflate(R.layout.home_search_condition, null);

                    adapter = new ListViewAdapter(context, dataSource);
                    GridView gridview = (GridView) popupView.findViewById(R.id.home_condition_panel);
                    gridview.setSelector(getResources().getDrawable(R.drawable.search_condition_item));
                    gridview.setFocusable(true);
                    adapter.setSelectedIndex(selectedIndex);
                    gridview.setAdapter(adapter);
                    popupWindow = new PopUpWindow(context, popupView, HomeTitleBar.this);

                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                            // TODO Auto-generated method stub
                            String value = dataSource.get(position).get("name");
                            int type = Integer.parseInt(dataSource.get(position).get("key"));
                            searchType.setText(value);
                            switch (type) {

                                case 0:
                                    searchEditText.setHint("专家搜索");
                                    break;
                                case 1:
                                    searchEditText.setHint("成果搜索");
                                    break;
                                case 2:
                                    searchEditText.setHint("企业搜索");
                                    break;
                                case 3:
                                    searchEditText.setHint("需求搜索");
                                    break;
                                case 5:
                                    searchEditText.setHint("产品搜索");
                                    break;
                            }

                            selectedIndex = position;
                            HomeTitleBar.this.type = type;
                            popupWindow.dismiss();
                        }
                    });
                }
            }
        });

        // 搜索框
        searchEditText.setKeyListener(null);
        searchEditText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//                listener.onClickMiddle();

                //跳转到搜索 历史页面
            }
       });
        scanBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                listener.onClickRight();
                //打开扫描
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

    public int getType() {
        return type;
    }
}
