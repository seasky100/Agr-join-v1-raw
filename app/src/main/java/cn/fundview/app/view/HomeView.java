package cn.fundview.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fundview.R;
import cn.fundview.app.action.home.AchvListAction;
import cn.fundview.app.action.home.CompListAction;
import cn.fundview.app.action.home.ExpertListAction;
import cn.fundview.app.action.home.FundProjListAction;
import cn.fundview.app.action.home.ProductListAction;
import cn.fundview.app.action.home.RequListAction;
import cn.fundview.app.domain.model.Achv;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.domain.model.Expert;
import cn.fundview.app.domain.model.Product;
import cn.fundview.app.domain.model.Requ;
import cn.fundview.app.domain.webservice.util.Constants;
import cn.fundview.app.model.FundProject;
import cn.fundview.app.tool.PopUpWindow;
import cn.fundview.app.tool.adapter.AchvListAdapter;
import cn.fundview.app.tool.adapter.CompListAdapter;
import cn.fundview.app.tool.adapter.ExpertListAdapter;
import cn.fundview.app.tool.adapter.ProductListAdapter;
import cn.fundview.app.tool.adapter.ProjListAdapter;
import cn.fundview.app.tool.adapter.RequAdapter;
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
public class HomeView extends LinearLayout implements AsyncTaskCompleteListener/*, AdapterView.OnItemClickListener*/ {

    private static final String TAG = HomeView.class.getName();
    private Context context;
    private PopUpWindow popupWindow;
    private int selectedIndex = 0;//当前选中的下标索引
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
        new ProductListAction(context, Constants.GET_HOME_PRODUCT_LIST_URL, this);
        new FundProjListAction(context, Constants.GET_FUND_PROJ_LIST_URL, this);
    }


    @Override
    public void complete(int requestCode, int responseCode, Object msg) {
        if (requestCode == 1) {

            //需求列表
            if (msg != null) {
                ListView requListView = (ListView) this.findViewById(R.id.requList);
                List<Requ> list = (List<Requ>) msg;
                if (list.size() > 0) {
                    this.findViewById(R.id.requ).setVisibility(VISIBLE);
                    this.findViewById(R.id.requList).setVisibility(VISIBLE);
                    RequAdapter requAdapter = new RequAdapter(context, list);
                    requListView.setAdapter(requAdapter);
                    ViewGroup.LayoutParams params = requListView.getLayoutParams();
                    View listItem = requAdapter.getView(0, null, requListView);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (requListView.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    requListView.setLayoutParams(params);

                    //绑定事件
//                    requListView.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.requ).setVisibility(GONE);
            this.findViewById(R.id.requList).setVisibility(GONE);
        } else if (requestCode == 2) {
            //成果列表
            if (msg != null) {

                List<Achv> list = (List<Achv>) msg;
                ListView achvListView = (ListView) this.findViewById(R.id.achvList);
                if (list.size() > 0) {
                    this.findViewById(R.id.achv).setVisibility(VISIBLE);
                    this.findViewById(R.id.achvList).setVisibility(VISIBLE);
                    AchvListAdapter achvAdapter = new AchvListAdapter(context, list);

                    achvListView.setAdapter(achvAdapter);
                    ViewGroup.LayoutParams params = achvListView.getLayoutParams();
                    View listItem = achvAdapter.getView(0, null, achvListView);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (achvListView.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    achvListView.setLayoutParams(params);

                    //绑定事件
//                    achvListView.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.achv).setVisibility(GONE);
            this.findViewById(R.id.achvList).setVisibility(GONE);
        } else if (requestCode == 3) {
            //首页企业列表
            if (msg != null) {

                List<Company> list = (List<Company>) msg;
                ListView compListView = (ListView) this.findViewById(R.id.compList);
                if (list.size() > 0) {

                    this.findViewById(R.id.compList).setVisibility(VISIBLE);
                    this.findViewById(R.id.comp).setVisibility(VISIBLE);
                    CompListAdapter compListAdapter = new CompListAdapter(context, list);

                    compListView.setAdapter(compListAdapter);
                    ViewGroup.LayoutParams params = compListView.getLayoutParams();
                    View listItem = compListAdapter.getView(0, null, compListView);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (compListView.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    compListView.setLayoutParams(params);

                    //绑定事件
//                    compListView.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.compList).setVisibility(GONE);
            this.findViewById(R.id.comp).setVisibility(GONE);
        } else if (requestCode == 4) {
            //首页专家列表
            if (msg != null) {

                List<Expert> list = (List<Expert>) msg;
                ListView expertListView = (ListView) this.findViewById(R.id.expertList);
                if (list.size() > 0) {
                    this.findViewById(R.id.expertList).setVisibility(VISIBLE);
                    ExpertListAdapter expertListAdapter = new ExpertListAdapter(context, list);

                    expertListView.setAdapter(expertListAdapter);

                    //修改listview 高度
                    ViewGroup.LayoutParams params = expertListView.getLayoutParams();
                    View listItem = expertListAdapter.getView(0, null, expertListView);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (expertListView.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    expertListView.setLayoutParams(params);

                    //绑定事件
//                    /expertListView.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.expertList).setVisibility(GONE);
            this.findViewById(R.id.expert).setVisibility(GONE);
        } else if (requestCode == 5) {
            //首页产品列表
            if (msg != null) {

                List<Product> list = (List<Product>) msg;
                ListView productListview = (ListView) this.findViewById(R.id.productList);
                if (list.size() > 0) {
                    this.findViewById(R.id.productList).setVisibility(VISIBLE);
                    this.findViewById(R.id.product).setVisibility(VISIBLE);
                    ProductListAdapter productListAdapter = new ProductListAdapter(context, list);

                    productListview.setAdapter(productListAdapter);

                    //修改listview 高度
                    ViewGroup.LayoutParams params = productListview.getLayoutParams();
                    View listItem = productListAdapter.getView(0, null, productListview);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (productListview.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    productListview.setLayoutParams(params);

                    //绑定事件
//                    productListview.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.productList).setVisibility(GONE);
            this.findViewById(R.id.product).setVisibility(GONE);
        } else if (requestCode == 6) {
            //首页项目列表
            if (msg != null) {

                List<FundProject> list = (List<FundProject>) msg;
                ListView projListview = (ListView) this.findViewById(R.id.projList);
                if (list.size() > 0) {
                    this.findViewById(R.id.projList).setVisibility(VISIBLE);
                    this.findViewById(R.id.proj).setVisibility(VISIBLE);
                    ProjListAdapter projListAdapter = new ProjListAdapter(context, list);

                    projListview.setAdapter(projListAdapter);

                    //修改listview 高度
                    ViewGroup.LayoutParams params = projListview.getLayoutParams();
                    View listItem = projListAdapter.getView(0, null, projListview);
                    listItem.measure(0, 0);
                    params.height = listItem.getMeasuredHeight() * list.size() + (projListview.getDividerHeight()) * list.size(); //假设你那“更多”的View高为50
                    projListview.setLayoutParams(params);

                    //绑定事件
//                    projListview.setOnItemClickListener(this);
                    return;
                }
            }
            this.findViewById(R.id.projList).setVisibility(GONE);
            this.findViewById(R.id.proj).setVisibility(GONE);
        }
    }

}
