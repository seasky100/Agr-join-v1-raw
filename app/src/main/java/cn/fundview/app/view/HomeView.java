package cn.fundview.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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
import cn.fundview.app.adapter.SlideImgsAdapter;
import cn.fundview.app.tool.PopUpWindow;
import cn.fundview.app.tool.adapter.GuideViewAdapter;
import cn.fundview.app.tool.adapter.ListViewAdapter;

/**
 * 类名称：HomeView
 * 类描述：首页view  title bar 的观察者
 * 创建人：lict
 * 创建时间：2015年6月8日 下午3:03:34
 * 修改人：lict
 * 修改时间：2015年6月8日 下午3:03:34
 * 修改备注：
 */
@SuppressLint("InflateParams")
public class HomeView extends LinearLayout implements TitleBarListener{

    private Context context;
    private PopUpWindow popupWindow;
    private int selectedIndex = 0;//当前选中的下标索引
    private ListViewAdapter adapter;

    private HomeTitleBar homeTitleBar;
    private int type;

    final List<Map<String, String>> dataSource = new ArrayList<>();  //搜索条件 数据源

    private List<String> imgs = new ArrayList<>();                  //首页滚动图片

    public HomeView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;


        imgs.add("http://static.fundview.cn/thumb/ad/banner1.jpg");
        imgs.add("http://static.fundview.cn/thumb/ad/banner2.jpg");
        imgs.add("http://static.fundview.cn/thumb/ad/banner3.jpg");

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
        LayoutInflater.from(context).inflate(R.layout.home_view, this);

        //加载滚动图片
        final ViewPager viewPager = (ViewPager) this.findViewById(R.id.imgs);
        final LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.indicator_panel);
        for(int i = 0; i<imgs.size(); i++) {

            ImageView dot = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(params);

            if (i == 0) {
                dot.setImageResource(R.drawable.dot_curr);
            } else {

                dot.setImageResource(R.drawable.dot);
            }
            linearLayout.addView(dot);
        }

        viewPager.setAdapter(new SlideImgsAdapter(context, imgs));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(final int position) {

                for (int i = 0; i < imgs.size(); i++) {

                    ImageView dot = (ImageView) linearLayout.getChildAt(i);
                    if (i == position) {
                        dot.setImageResource(R.drawable.dot_curr);
                    } else {
                        dot.setImageResource(R.drawable.dot);
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 覆盖父类中的实现,父类中是关闭窗口
     */
    @Override
    public void onClickLeft() {
        // TODO Auto-generated method stub
        //titleBar
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
            final TextView anchorTextView = homeTitleBar.getSearchType();
            final EditText editText = homeTitleBar.getSearchEditText();
            popupWindow = new PopUpWindow(context, popupView, homeTitleBar);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    // TODO Auto-generated method stub
                    String value = dataSource.get(position).get("name");
                    int type = Integer.parseInt(dataSource.get(position).get("key"));
                    anchorTextView.setText(value);
                    anchorTextView.setText(value);
                    switch (type) {

                        case 0:
                            editText.setHint("专家搜索");
                            break;
                        case 1:
                            editText.setHint("成果搜索");
                            break;
                        case 2:
                            editText.setHint("企业搜索");
                            break;
                        case 3:
                            editText.setHint("需求搜索");
                            break;
                        case 5:
                            editText.setHint("产品搜索");
                            break;
                    }

                    selectedIndex = position;
                    HomeView.this.type = type;
                    popupWindow.dismiss();
                }
            });
        }
    }

    @Override
    public void onClickMiddle() {
        // TODO Auto-generated method stub
        //super.onClickEdit();
//
//        Intent intent = new Intent(context, SearchHistoryActivity.class);
//
//        intent.putExtra("type", Integer.parseInt(dataSource.get(selectedIndex).get("key")));
//        context.startActivity(intent);
    }

    @Override
    public void onClickRight() {

//        Intent intent = new Intent(context, CaptureActivity.class);
//        context.startActivity(intent);
    }
}
