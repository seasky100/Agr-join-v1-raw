package cn.fundview.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fundview.app.domain.model.Company;

/**
 * 项目名称：Agr-join-v1-raw
 * 类描述：企业 item view
 * 创建人：lict
 * 创建时间：2015/12/19 0019 下午 6:12
 * 修改人：lict
 * 修改时间：2015/12/19 0019 下午 6:12
 * 修改备注：
 */
public class CompItemView extends LinearLayout {

    private Context context;

    private Company company;

    final List<Map<String, String>> dataSource = new ArrayList<>();  //搜索条件 数据源

    private List<String> imgs = new ArrayList<>();                  //首页滚动图片

    public CompItemView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

}
