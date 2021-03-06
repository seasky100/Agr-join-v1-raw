package cn.fundview.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.R;
import cn.fundview.app.action.home.AchvListAction;
import cn.fundview.app.action.home.CompListAction;
import cn.fundview.app.action.home.ExpertListAction;
import cn.fundview.app.action.home.RequListAction;
import cn.fundview.app.domain.model.Achv;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.domain.model.Expert;
import cn.fundview.app.domain.model.Requ;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.tool.PopUpWindow;
import cn.fundview.app.tool.adapter.AchvAdapter;
import cn.fundview.app.tool.adapter.AchvLinearLayoutAdapter;
import cn.fundview.app.tool.adapter.CompLinearLayoutAdapter;
import cn.fundview.app.tool.adapter.ExpertLinearLayoutAdapter;
import cn.fundview.app.tool.adapter.ListViewAdapter;
import cn.fundview.app.tool.adapter.RequAdapter;
import cn.fundview.app.tool.adapter.RequLinearLayoutAdapter;
import cn.fundview.app.tool.adapter.SlideImgsAdapter;

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
public class HomeView extends LinearLayout implements  AsyncTaskCompleteListener {

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
        for (int i = 0; i < imgs.size(); i++) {

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

        viewPager.setAdapter(new SlideImgsAdapter(((FragmentActivity) context).getSupportFragmentManager(), imgs));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

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

        //加载需求
        new RequListAction(context, Constants.GET_HOME_REQU_LIST_URL, this);
        new AchvListAction(context, Constants.GET_HOME_ACHV_LIST_URL, this);
        new CompListAction(context, Constants.GET_HOME_COMPANY_LIST_URL, this);
        new ExpertListAction(context, Constants.GET_HOME_EXPERT_LIST_URL, this);
    }


    @Override
    public void complete(int requestCode, int responseCode, Object msg) {
        if (requestCode == 1) {

            //需求列表
            if (msg != null) {
                LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.requlist);
                List<Requ> list = (List<Requ>) msg;
                if (list.size() > 0) {
                    this.findViewById(R.id.requ).setVisibility(VISIBLE);
                    RequAdapter adapter = new RequAdapter(context, list);
                    RequLinearLayoutAdapter requLinearLayoutAdapter = new RequLinearLayoutAdapter(context,list,linearLayout);
                    requLinearLayoutAdapter.init();

                } else {

                    this.findViewById(R.id.requ).setVisibility(GONE);
                }
            }
        } else if (requestCode == 2) {
            //成果列表
            if (msg != null) {

                List<Achv> list = (List<Achv>) msg;
                LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.achvlist);
                if (list.size() > 0) {
                    this.findViewById(R.id.achv).setVisibility(VISIBLE);
                    AchvAdapter adapter = new AchvAdapter(context, list);
                    AchvLinearLayoutAdapter achvLinearLayoutAdapter = new AchvLinearLayoutAdapter(context, list,linearLayout);
                    achvLinearLayoutAdapter.init();
                } else {

                    this.findViewById(R.id.achv).setVisibility(GONE);
                }
            }
        }else if (requestCode == 3) {
            //首页企业列表
            if (msg != null) {

                List<Company> list = (List<Company>) msg;
                LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.compList);
                if (list.size() > 0) {
                    this.findViewById(R.id.compList).setVisibility(VISIBLE);
                    CompLinearLayoutAdapter compLinearLayoutAdapter = new CompLinearLayoutAdapter(context, list,linearLayout);
                    compLinearLayoutAdapter.init();
                } else {

                    this.findViewById(R.id.compList).setVisibility(GONE);
                }
            }
        }else if (requestCode == 4) {
            //首页专家列表
            if (msg != null) {

                List<Expert> list = (List<Expert>) msg;
                LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.expertList);
                if (list.size() > 0) {
                    this.findViewById(R.id.expertList).setVisibility(VISIBLE);
                    ExpertLinearLayoutAdapter expertLinearLayoutAdapter = new ExpertLinearLayoutAdapter(context, list,linearLayout);
                    expertLinearLayoutAdapter.init();
                } else {

                    this.findViewById(R.id.expertList).setVisibility(GONE);
                }
            }
        }
    }
}
