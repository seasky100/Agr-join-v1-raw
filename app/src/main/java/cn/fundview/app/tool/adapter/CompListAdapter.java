package cn.fundview.app.tool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fundview.R;
import cn.fundview.app.domain.model.Company;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 *企业适配器
 */
public class CompListAdapter extends BaseAdapter {

    private List<Company> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public CompListAdapter(Context context, List<Company> dataSource) {

        this.context = context;
        this.dataSource = dataSource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Company company = dataSource.get(position);
        if (convertView == null || convertView.getTag() != null) {          //只有数据加载完成后才能重新使用
            convertView = inflater.inflate(R.layout.comp_item, null);
            convertView.setTag(company);
        }

        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
        String url = company.getLogo();
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
        return convertView;
    }

    public void dataChanged(List<Company> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
