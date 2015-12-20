package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * 项目名称：Agr-join-v1-raw
 * 类描述： 企业线性布局 适配器
 * 创建人：lict
 * 创建时间：2015/11/23 0023 下午 6:10
 * 修改人：lict
 * 修改时间：2015/11/23 0023 下午 6:10
 * 修改备注：
 */
public class CompLinearLayoutAdapter {

    private List<Company> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout container;

    public CompLinearLayoutAdapter(Context context, List<Company> dataSource, LinearLayout container) {

        this.context = context;
        this.dataSource = dataSource;
        this.container = container;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);

    }

    public void init() {
        // TODO Auto-generated method stub

        if(dataSource != null && dataSource.size() > 0) {

            for(Company company : dataSource) {

                View convertView = inflater.inflate(R.layout.comp_item, null);
                //logo
                ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
                String url = company.getLogo();
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.company_logo);
                imageLoader.display(logo, company.getLogo());

                //title
                TextView title = (TextView) convertView.findViewById(R.id.title);
                if(!StringUtils.isBlank(company.getName())) {

                    title.setText(company.getName());
                }

                //生产环节
                TextView trade =  (TextView) convertView.findViewById(R.id.trade);
                if(!StringUtils.isBlank(company.getTradeName())) {

                    trade.setText("行业:" + company.getTradeName());
                }else {

                    trade.setText("应用行业:暂未填写");
                }

                //comp
                TextView compView = (TextView) convertView.findViewById(R.id.area);

                if(StringUtils.isBlank(company.getAreaName())) {

                    compView.setText("暂未填写");
                }else {

                    compView.setText(company.getAreaName());
                }

                container.addView(convertView);
            }
        }
    }
}
