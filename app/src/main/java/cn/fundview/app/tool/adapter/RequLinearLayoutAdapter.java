package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.domain.model.Achv;
import cn.fundview.app.domain.model.Requ;
import cn.fundview.app.tool.Constants;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.BitmapItem;

/**
 * 项目名称：Agr-join-v1-raw
 * 类描述： 需求线性布局 适配器
 * 创建人：lict
 * 创建时间：2015/11/23 0023 下午 6:10
 * 修改人：lict
 * 修改时间：2015/11/23 0023 下午 6:10
 * 修改备注：
 */
public class RequLinearLayoutAdapter {

    private List<Requ> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private LinearLayout container;

    public RequLinearLayoutAdapter(Context context, List<Requ> dataSource, LinearLayout container) {

        this.context = context;
        this.dataSource = dataSource;
        this.container = container;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);

    }

    public void init() {
        // TODO Auto-generated method stub

        if(dataSource != null && dataSource.size() > 0) {

            for(Requ requ : dataSource) {

                View convertView = inflater.inflate(R.layout.requ_item, null);
                //logo
                ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
                String url = requ.getLogo();
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                Log.d(Constants.TAG, url + "|" + fileName);
                BitmapItem bitmapItem = new BitmapItem(context, logo, requ.getLogo(), Constants.requLogoPath + requ.getId() + "/" + fileName, R.mipmap.company_logo);
                bitmapItem.show();

                //title
                TextView title = (TextView) convertView.findViewById(R.id.title);
                if(!StringUtils.isBlank(requ.getName())) {

                    title.setText(requ.getName());
                }

                //生产环节
                TextView hj =  (TextView) convertView.findViewById(R.id.hj);
                if(!StringUtils.isBlank(requ.getHj())) {

                    hj.setText("生产环节:" + requ.getHj());
                }else {

                    hj.setText("生产环节:暂未填写");
                }

                //comp
                TextView compView = (TextView) convertView.findViewById(R.id.compname);

                if(StringUtils.isBlank(requ.getOwnerName())) {

                    compView.setText("暂未填写");
                }else {

                    compView.setText(requ.getOwnerName());
                }

                //price
                TextView price = (TextView)convertView.findViewById(R.id.price);
                if(requ.getFinPlan() <= 0) {

                    price.setText("面议");
                }else {

                    price.setText("￥" + requ.getFinPlan() + "万");
                }

                container.addView(convertView);
            }
        }
    }
}
