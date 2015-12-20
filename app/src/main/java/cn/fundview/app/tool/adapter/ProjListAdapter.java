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
import cn.fundview.app.model.FundProject;
import cn.fundview.app.tool.StringUtils;
import cn.fundview.app.tool.bitmap.XUtilsImageLoader;

/**
 * 项目适配器
 */
public class ProjListAdapter extends BaseAdapter {

    private List<FundProject> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public ProjListAdapter(Context context, List<FundProject> dataSource) {

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
        FundProject fundProject = dataSource.get(position);
        if (convertView == null || convertView.getTag() != null) {          //只有数据加载完成后才能重新使用
            convertView = inflater.inflate(R.layout.proj_item, null);
//            convertView.setTag(fundProject);
        }

        //logo
        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);   //logo
        XUtilsImageLoader imageLoader = new XUtilsImageLoader(context, R.mipmap.rongzi_default);
        imageLoader.display(logo, fundProject.getLogo());

        //title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        if(!StringUtils.isBlank(fundProject.getProjName())) {

            title.setText(fundProject.getProjName());
        }

        //阶段
        TextView trade =  (TextView) convertView.findViewById(R.id.jd);
        if(!StringUtils.isBlank(fundProject.getJdName())) {

            trade.setText("所属阶段:" + fundProject.getJdName());
        }else {

            trade.setText("所属阶段:暂未填写");
        }

        //所属企业
        TextView compView = (TextView) convertView.findViewById(R.id.ownername);

        if(StringUtils.isBlank(fundProject.getName())) {

            compView.setText("暂未填写");
        }else {

            compView.setText(fundProject.getName());
        }

        //price
        TextView price = (TextView)convertView.findViewById(R.id.price);
        if(fundProject.getInvest() <= 0) {

            price.setText("面议");
        }else {

            price.setText("￥" + fundProject.getInvest() + "万");
        }
        return convertView;
    }

    public void dataChanged(List<FundProject> dataSource) {

        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

}
