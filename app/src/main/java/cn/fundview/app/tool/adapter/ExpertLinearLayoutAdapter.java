package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.domain.model.Expert;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * 项目名称：Agr-join-v1-raw
 * 类描述： 专家线性布局 适配器
 * 创建人：lict
 * 创建时间：2015/11/23 0023 下午 6:10
 * 修改人：lict
 * 修改时间：2015/11/23 0023 下午 6:10
 * 修改备注：
 */
public class ExpertLinearLayoutAdapter {

    private List<Expert> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout container;

    public ExpertLinearLayoutAdapter(Context context, List<Expert> dataSource, LinearLayout container) {

        this.context = context;
        this.dataSource = dataSource;
        this.container = container;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);

    }

    public void init() {
        // TODO Auto-generated method stub

        if(dataSource != null && dataSource.size() > 0) {

            for(Expert expert : dataSource) {

                View convertView = inflater.inflate(R.layout.expert_item, null);
                //logo
                ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
                String url = expert.getLogo();
                XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.company_logo);
                imageLoader.display(logo, expert.getLogo());

                //title
                TextView title = (TextView) convertView.findViewById(R.id.title);
                if(!StringUtils.isBlank(expert.getName())) {

                    title.setText(expert.getName());
                }

                //专业职称
                TextView professionalTitleTextView = (TextView) convertView.findViewById(R.id.professionalTitle);
                if(!StringUtils.isBlank(expert.getProfessionalTitle())) {

                    professionalTitleTextView.setText(expert.getProfessionalTitle());
                }

                //生产环节
                TextView trade =  (TextView) convertView.findViewById(R.id.trade);
                if(!StringUtils.isBlank(expert.getTradeName())) {

                    trade.setText("行业:" + expert.getTradeName());
                }else {

                    trade.setText("应用行业:暂未填写");
                }

                //comp
                TextView compView = (TextView) convertView.findViewById(R.id.area);

                if(StringUtils.isBlank(expert.getAreaName())) {

                    compView.setText("暂未填写");
                }else {

                    compView.setText(expert.getAreaName());
                }

                container.addView(convertView);
            }
        }
    }
}
